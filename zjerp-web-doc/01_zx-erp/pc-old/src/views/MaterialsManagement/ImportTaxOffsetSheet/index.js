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
            visibleSend: false,
            loadingSend: false,
            visibleSendData: false,
            loadingSendData: false,
            data: [],
            id: 0,
            idModal: 0,
            abcVal: '',
            selectedRowsData: {},
            outOrgIDData: '',
            whOrgIDData: '',
            resourceIDData: '',
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
    getDjbhFun(makeoutOrgID, makeoutDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkInvoiceNo', {
            orgID: makeoutOrgID,
            makeoutDate: makeoutDate ? new Date(makeoutDate._d).getTime() : null
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.table.qnnForm.form.setFieldsValue({
                        receNo: data
                    })
                } else {
                }
            }
        );
    }
    handleCancelSendData = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    handleCancelBjdh = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }

    getTotalFunc(rowData) {//计算表格内的总值，显示在表单上
        let ruzhangAll = 0;
        let zongjiaAll = 0;
        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.zxSkInvoiceItemList.map((item) => {
            if (item.id === rowData.id) {
                ruzhangAll += Number(rowData.amt ? rowData.amt : 0);
                zongjiaAll += Number(rowData.amtTotal ? rowData.amtTotal : 0);
            } else {
                ruzhangAll += Number(item.amt ? item.amt : 0);
                zongjiaAll += Number(item.amtTotal ? item.amtTotal : 0);
            }
            return item;
        })
        this.table.qnnForm.form.setFieldsValue({
            amt: Number(ruzhangAll.toFixed(2)),
            amtTotal: Number(zongjiaAll.toFixed(2))
        });

    }
    //日期控制
    dateChange(val) {
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
                    makeoutDate: '',
                    receNo: null
                })
            }
        } else {//当前日期大于等于26号
            if (time1 < time3) {
                Msg.warn('不能补录本月26号零点前的数据！');
                this.table.qnnForm.form.setFieldsValue({
                    makeoutDate: '',
                    receNo: null
                })
            }
        }
        let makeoutOrgID = this.table.qnnForm.form.getFieldsValue().makeoutOrgID;
        let makeoutDate = this.table.qnnForm.form.getFieldsValue().makeoutDate;
        if (makeoutOrgID) {
            this.getDjbhFun(makeoutOrgID, makeoutDate);
        }
    }
    render() {
        const { visibleBjdh, loadingBjdh, visibleSend, loadingSend, idModal, visibleSendData, loadingSendData, selectedRowsData } = this.state;
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
                        apiName: 'getZxSkInvoiceList',
                        otherParams: {
                            makeoutOrgID: departmentId
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
                                field: 'companyName',
                                initialValue: companyName,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                initialValue: companyId,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单据编号',
                                onClick: 'detail',
                                dataIndex: 'receNo',
                                key: 'receNo',
                                width: 200,
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
                                field: 'receNo',
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
                                dataIndex: 'makeoutDate',
                                format: 'YYYY-MM-DD',
                                key: 'makeoutDate',
                                filter: true
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'makeoutDate',
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
                                                receNo: null
                                            });
                                        }

                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'ysdID',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'makeoutOrgID',
                                key: 'makeoutOrgID',
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
                                field: 'makeoutOrgID',
                                required: true,
                                type: 'select',
                                editDisabled: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        makeoutOrgName: 'orgName',
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
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                                children: [
                                    {
                                        field: 'whOrgID',
                                    },
                                    {
                                        field: 'outOrgID',
                                    },
                                    {
                                        field: 'resourceID'
                                    }
                                ],
                                onChange: (val, obj) => {

                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkInvoiceItemList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            ysdNo: '',
                                        })
                                        let makeoutDate = this.table.qnnForm.form.getFieldsValue().makeoutDate;
                                        if (makeoutDate || val) {
                                            this.getDjbhFun(val, makeoutDate);
                                        } else {
                                            this.table.qnnForm.form.setFieldsValue({
                                                receNo: null
                                            });
                                        }
                                    }
                                }
                            },
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         type: 'string',
                        //         field: 'companyId',
                        //         hide: true
                        //     }
                        // },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         type: 'string',
                        //         field: 'companyName',
                        //         hide: true
                        //     }
                        // },
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
                                        value: 'id',
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
                                type: 'select',
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id',
                                    linkageFields: {
                                        "warehouseName": "warehouseName"
                                    }
                                },
                                parent: 'makeoutOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseByParentOrgIDList',
                                    params: {
                                        parentOrgID: 'makeoutOrgID'
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
                                onChange: (val, obj) => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        ysdNo: '',
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkInvoiceItemList']);
                                    }
                                }
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
                            table: {
                                title: '供方名称',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                filter: true,
                                fieldsConfig: {
                                    field: 'outOrgName',
                                    type: 'string',
                                }
                            },
                            form: {
                                field: 'outOrgID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'outOrgName',
                                    value: 'outOrgID',
                                    linkageFields: {
                                        "outOrgName": "outOrgName"
                                    }
                                },
                                parent: 'makeoutOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkInvoiceOutOrg',
                                    params: {
                                        makeoutOrgID: 'makeoutOrgID'
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
                                onChange: (val, obj) => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        ysdNo: '',
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkInvoiceItemList']);
                                    }
                                }
                            },
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
                                        label: 'resourceName',
                                        value: 'resourceID',
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkInvoiceResource',
                                        otherParams: {
                                            parentOrgID: "1"
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'resourceID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'resourceName',
                                    value: 'resourceID',
                                    linkageFields: {
                                        "resourceName": 'resourceName'
                                    }
                                },
                                parent: 'makeoutOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkInvoiceResource',
                                    otherParams: {
                                        companyID: companyId,
                                    },
                                    params: {
                                        makeoutOrgID: 'makeoutOrgID'
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
                                onChange: (val, obj) => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        ysdNo: ''
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkInvoiceItemList']);
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
                                type: 'select',
                                filter:true
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
                                    let whOrgIDData = this.table.qnnForm.form.getFieldsValue().whOrgID;
                                    let resourceIDData = this.table.qnnForm.form.getFieldsValue().resourceID;
                                    if (outOrgIDData && whOrgIDData && resourceIDData && this.state.companyIDData) {
                                        this.setState({
                                            visibleBjdh: true,
                                            outOrgIDData: outOrgIDData,
                                            whOrgIDData: whOrgIDData,
                                            resourceIDData: resourceIDData
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
                                key: 'purchType',
                                type: 'select'
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
                                hide:true,
                                field: 'contractName',
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName'
                            },
                            form: {
                                type: 'select',
                                allowClear: false,
                                field: 'contractID',
                                optionConfig: {
                                    label: 'contractName',
                                    value: 'zxCtSuppliesContractId',
                                    linkageFields: {
                                        contractNo: 'contractNo',
                                        isDeduct: 'isDeduct',
                                        contractName:'contractName'
                                    }
                                },
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['makeoutOrgID', 'outOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxCtSuppliesContractListByFirstId',
                                    otherParams: (val) => {
                                        let makeoutOrgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            makeoutOrgIDVal = aa.makeoutOrgID;
                                            outOrgIDVal = aa.outOrgID;
                                        } else {
                                            makeoutOrgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            firstId: makeoutOrgIDVal,
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
                            }
                        },
                        {
                            table: {
                                title: '入账金额',
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
                            isInTable: false,
                            table: {
                                title: '总金额',
                                width: 100,
                                dataIndex: 'amtTotal',
                                key: 'amtTotal',
                            },
                            form: {
                                type: 'number',
                                field: 'amtTotal',
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
                                type: 'qnnTable',
                                field: 'zxSkInvoiceItemList',
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
                                                title: '冲账数量',//校验预收数量
                                                width: 120,
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'qty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            let shuilv = 0;
                                                            if (colVal > rowData.oldQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.qty = rowData.oldQty;
                                                            } else {
                                                                if (rowData.taxRate) {
                                                                    shuilv = Number((rowData.taxRate) * 0.01);
                                                                }
                                                                newRowData.ysFeeTotal = rowData.ysFee ? Number(rowData.ysFee).toFixed(2) : 0;
                                                                newRowData.otherExpenseTotal = rowData.otherExpense ? Number(rowData.otherExpense).toFixed(2) : 0;
                                                                if (rowData.unitPrice) {
                                                                    newRowData.stockAmtNoTax = ((this.FloatMulTwo(colVal, rowData.unitPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                    newRowData.unitPriceNoTax = (rowData.unitPrice / Number(1 + shuilv)).toFixed(2);//不含税单价
                                                                    newRowData.stockAmt = this.FloatMulTwo(colVal, rowData.unitPrice).toFixed(2);//含税金额
                                                                    newRowData.amtTotal = parseFloat(Number(this.FloatMulTwo(colVal, rowData.unitPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.otherExpenseTotal)).toFixed(2);//含税总价
                                                                    if (rowData.isDeduct === '0') {//参与抵扣-0否   1是
                                                                        newRowData.amt = parseFloat(Number(newRowData.stockAmt) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                    } else {
                                                                        newRowData.amt = parseFloat(Number(newRowData.stockAmtNoTax) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                    }
                                                                    if (newRowData.stockAmt && newRowData.stockAmtNoTax) {
                                                                        newRowData.stockAmtTax = (newRowData.stockAmt - newRowData.stockAmtNoTax).toFixed(2);//税额
                                                                    }
                                                                }
                                                                if (rowData.unitPrice) {
                                                                    newRowData.unitPriceNoTax = (rowData.unitPrice / Number(1 + shuilv)).toFixed(2);//不含税单价
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
                                            table: {
                                                title: '不含税单价',
                                                width: 120,
                                                dataIndex: 'unitPriceNoTax',
                                                key: 'unitPriceNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'unitPriceNoTax',
                                                precision: 6,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税金额',
                                                width: 120,
                                                dataIndex: 'stockAmtNoTax',
                                                key: 'stockAmtNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockAmtNoTax',
                                                precision: 2,
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
                                                title: '税额',
                                                width: 120,
                                                dataIndex: 'stockAmtTax',
                                                key: 'stockAmtTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockAmtTax',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税单价',
                                                width: 120,
                                                dataIndex: 'unitPrice',
                                                key: 'unitPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'unitPrice',
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

                                                            if (rowData.taxRate) {
                                                                shuilv = Number((rowData.taxRate) * 0.01);
                                                            }
                                                            newRowData.ysFeeTotal = rowData.ysFee ? Number(rowData.ysFee).toFixed(2) : 0;
                                                            newRowData.otherExpenseTotal = rowData.otherExpense ? Number(rowData.otherExpense).toFixed(2) : 0;
                                                            if (rowData.qty) {
                                                                newRowData.stockAmtNoTax = ((this.FloatMulTwo(rowData.qty, colVal)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.stockAmt = this.FloatMulTwo(rowData.qty, colVal).toFixed(2);//含税金额
                                                                newRowData.amtTotal = parseFloat(Number(this.FloatMulTwo(rowData.qty, colVal)) + Number(newRowData.ysFeeTotal) + Number(newRowData.otherExpenseTotal)).toFixed(2);//含税总价
                                                                if (rowData.isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmt) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                } else {
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmtNoTax) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                }
                                                                if (newRowData.stockAmt && newRowData.stockAmtNoTax) {
                                                                    newRowData.stockAmtTax = (newRowData.stockAmt - newRowData.stockAmtNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.unitPriceNoTax = (colVal / Number(1 + shuilv)).toFixed(2);//不含税单价
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
                                                title: '含税金额',
                                                width: 120,
                                                dataIndex: 'stockAmt',
                                                key: 'stockAmt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '入账其他费',
                                                width: 200,
                                                dataIndex: 'otherExpense',
                                                key: 'otherExpense',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'otherExpense',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            let shuilv = 0;
                                                            if (rowData.taxRate) {
                                                                shuilv = Number((rowData.taxRate) * 0.01);
                                                            }
                                                            newRowData.otherExpenseTotal = colVal.toFixed(2);
                                                            if (newRowData.ysFeeTotal) {

                                                            } else {
                                                                newRowData.ysFeeTotal = 0;
                                                            }
                                                            if (rowData.qty) {
                                                                newRowData.stockAmtNoTax = ((this.FloatMulTwo(rowData.qty, rowData.unitPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.stockAmt = this.FloatMulTwo(rowData.qty, rowData.unitPrice).toFixed(2);//含税金额
                                                                newRowData.amtTotal = parseFloat(Number(this.FloatMulTwo(rowData.qty, rowData.unitPrice)) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//含税总价 

                                                                if (rowData.isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmt) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                } else {
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmtNoTax) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                }

                                                                if (newRowData.stockAmt && newRowData.stockAmtNoTax) {
                                                                    newRowData.stockAmtTax = (newRowData.stockAmt - newRowData.stockAmtNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.unitPriceNoTax = (rowData.unitPrice / Number(1 + shuilv)).toFixed(2);//不含税单价
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
                                        // {
                                        //     isInTable: (obj) => {
                                        //         if (obj.from.getFieldsValue().purchType === '有合同') {
                                        //             return true
                                        //         } else {
                                        //             return false
                                        //         }
                                        //     },
                                        //     table: {
                                        //         title: '操作',
                                        //         dataIndex: 'warnFlag',
                                        //         key: 'warnFlag',
                                        //         align: 'center',
                                        //         width: 50,
                                        //         render: (data) => {
                                        //             return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                        //         },
                                        //         onClick: (obj) => {
                                        //             this.setState({
                                        //                 visibleSend: true,
                                        //                 idModal: obj.rowData.id
                                        //             });
                                        //         }
                                        //     },
                                        //     isInForm: false
                                        // },
                                        {
                                            table: {
                                                title: '其他费总价',
                                                width: 120,
                                                dataIndex: 'otherExpenseTotal',
                                                key: 'otherExpenseTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'otherExpenseTotal',
                                                precision: 2,
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
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            let shuilv = 0;
                                                            if (rowData.taxRate) {
                                                                shuilv = Number((rowData.taxRate) * 0.01);
                                                            }
                                                            newRowData.ysFeeTotal = Number(colVal).toFixed(2);
                                                            if (rowData.otherExpenseTotal) {

                                                            } else {
                                                                newRowData.otherExpenseTotal = 0;
                                                            }
                                                            if (rowData.qty) {
                                                                newRowData.stockAmtNoTax = ((this.FloatMulTwo(rowData.qty, rowData.unitPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.stockAmt = this.FloatMulTwo(rowData.qty, rowData.unitPrice).toFixed(2);//含税金额
                                                                newRowData.amtTotal = parseFloat(Number(this.FloatMulTwo(rowData.qty, rowData.unitPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.otherExpenseTotal)).toFixed(2);//含税总价 
                                                                if (rowData.isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmt) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                } else {
                                                                    newRowData.amt = parseFloat(Number(newRowData.stockAmtNoTax) + Number(newRowData.otherExpenseTotal) + Number(newRowData.ysFeeTotal)).toFixed(2);//入账-金额
                                                                }
                                                                if (newRowData.stockAmt && newRowData.stockAmtNoTax) {
                                                                    newRowData.stockAmtTax = (newRowData.stockAmt - newRowData.stockAmtNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.unitPriceNoTax = (rowData.unitPrice / Number(1 + shuilv)).toFixed(2);//不含税单价
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
                                        // {
                                        //     isInTable: (obj) => {
                                        //         if (obj.from.getFieldsValue().purchType === '有合同') {
                                        //             return true
                                        //         } else {
                                        //             return false
                                        //         }
                                        //     },
                                        //     table: {
                                        //         title: '操作',
                                        //         dataIndex: 'warnFlag',
                                        //         key: 'warnFlag',
                                        //         align: 'center',
                                        //         width: 50,
                                        //         render: (data) => {
                                        //             return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                        //         },
                                        //         onClick: (obj) => {
                                        //             this.setState({
                                        //                 visibleSend: true,
                                        //                 idModal: obj.rowData.id
                                        //             });
                                        //         }
                                        //     },
                                        //     isInForm: false
                                        // },
                                        {
                                            table: {
                                                title: '运输费总价',
                                                width: 120,
                                                dataIndex: 'ysFeeTotal',
                                                key: 'ysFeeTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'ysFeeTotal',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '总价',
                                                width: 120,
                                                dataIndex: 'amtTotal',
                                                key: 'amtTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'amtTotal',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '入账金额',
                                                width: 120,
                                                dataIndex: 'amt',
                                                key: 'amt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'amt',
                                                precision: 2
                                            }
                                        }
                                    ],
                                    actionBtns: [

                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                if (this.table && this.table.qnnForm) {
                                                    let allData = this.table.qnnForm.form.getFieldsValue().zxSkInvoiceItemList;
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
                                                                    zxSkInvoiceItemList: selectRowData
                                                                });
                                                                let ruzhangAll = 0;
                                                                let zongjiaAll = 0;
                                                                let ysdbhNewArry = [];
                                                                selectRowData.map((item) => {
                                                                    ruzhangAll += Number(item.amt ? item.amt : 0);
                                                                    zongjiaAll += Number(item.amtTotal ? item.amtTotal : 0);
                                                                    ysdbhNewArry.push(item.stockTransBillNo);
                                                                    return item;
                                                                })
                                                                function unique(ysdbhNewArry) {
                                                                    let arry = Array.from(new Set(ysdbhNewArry));
                                                                    // let arry = ysdbhNewArry.filter((x, index, self) => self.indexOf(x) === index);
                                                                    let ysdbhNew = '';
                                                                    arry.map((item) => {
                                                                        ysdbhNew += item + ',';
                                                                    })
                                                                    return ysdbhNew
                                                                }
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    amt: Number(ruzhangAll.toFixed(2)),
                                                                    amtTotal: Number(zongjiaAll.toFixed(2)),
                                                                    ysdNo: unique(ysdbhNewArry)
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
                                initialValue: 11,//
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单据状态',
                                width: 100,
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
                                    isCanSubmit: (obj, callback) => {
                                        let formData = obj._formData.zxSkInvoiceItemList;
                                        let sameShui = [];
                                        let sameDeduct = [];
                                        for (let i = 0; i < formData.length; i++) {
                                            if (formData[i].taxRate !== formData[0].taxRate) {
                                                sameShui.push(formData[i].taxRate);
                                            } else { }
                                            if (formData[i].isDeduct !== formData[0].isDeduct) {
                                                sameDeduct.push(formData[i].isDeduct);
                                            } else {

                                            }
                                        }
                                        if (sameShui.length > 0) {
                                            Msg.error('明细的税率不相同，不可以保存！');
                                            callback(false);
                                        } else {
                                            if (sameDeduct.length > 0) {
                                                Msg.error('明细的是否抵扣不相同，不可以保存！');
                                                callback(false);
                                            } else {
                                                callback(true);
                                            }
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'addZxSkInvoice'
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
                                    isCanSubmit: (obj, callback) => {
                                        let formData = obj._formData.zxSkInvoiceItemList;
                                        let sameShui = [];
                                        let sameDeduct = [];
                                        for (let i = 0; i < formData.length; i++) {
                                            if (formData[i].taxRate !== formData[0].taxRate) {
                                                sameShui.push(formData[i].taxRate);
                                            } else { }
                                            if (formData[i].isDeduct !== formData[0].isDeduct) {
                                                sameDeduct.push(formData[i].isDeduct);
                                            } else {

                                            }
                                        }
                                        if (sameShui.length > 0) {
                                            Msg.error('明细的税率不相同，不可以保存！');
                                            callback(false);
                                        } else {
                                            if (sameDeduct.length > 0) {
                                                Msg.error('明细的是否抵扣不相同，不可以保存！');
                                                callback(false);
                                            } else {
                                                callback(true);
                                            }
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'updateZxSkInvoice'
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
                                        // obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkInvoice', { id: obj.selectedRows[0].id }).then(
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
                                            myFetch('batchDeleteUpdateZxSkInvoice', obj.selectedRows).then(
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
                    title="入账其他费"
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
                            formConfig={[
                                {
                                    type: 'string',
                                    field: "???",
                                    initialValue: idModal,
                                    hide: true
                                },

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
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('toHomeShowZjTzBrandResultShow', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
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

                                        this.props.myFetch('updateZxSkInvoice', values).then(
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
                                        width: 200,
                                        tooltip: 23,
                                        filter: true
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '收料单位',
                                        dataIndex: 'inOrgID',
                                        key: 'inOrgID',
                                        width: 200,
                                        type: 'select'
                                    },
                                    form: {
                                        field: 'inOrgID',
                                        type: 'select',
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
                                    },
                                },
                                {
                                    table: {
                                        title: '仓库',
                                        dataIndex: 'warehouseName',
                                        key: 'warehouseName',
                                        width: 160
                                    },
                                    form: {
                                        field: 'whOrgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'warehouseName',
                                            value: 'id'
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxSkWarehouseList',
                                            params: {
                                                parentOrgID: 'inOrgID'
                                            }
                                        }
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
                                    table: {
                                        title: '物资类别',
                                        dataIndex: 'resourceName',
                                        key: 'resourceName',
                                        width: 160
                                    },
                                    form: {
                                        field: 'resourceID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'catName',
                                            value: 'id',
                                            linkageFields: {
                                                "resourceName": 'catName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxSkResCategoryMaterialsListResource'
                                        }
                                    },
                                },
                                {
                                    table: {
                                        title: '供货单位',
                                        dataIndex: 'outOrgName',
                                        key: 'outOrgName',
                                        width: 200
                                    },
                                    form: {
                                        field: 'outOrgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'customerName',
                                            value: 'zxCrCustomerNewId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrCustomerNewList',
                                            otherParams: {
                                                limit: 999,
                                                page: 1
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
                                    },
                                    isInForm: false
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
                                        }
                                    }
                                }
                            ]}
                            antd={{
                                rowKey: 'stockTransID',
                                size: 'small'
                            }}
                            fetchConfig={{
                                apiName: 'getZxSkInvoiceReceivableOrder',
                                otherParams: {
                                    companyID: this.state.companyIDData,
                                    outOrgID: this.state.outOrgIDData,
                                    whOrgID: this.state.whOrgIDData,
                                    resourceID: this.state.resourceIDData
                                }
                            }}
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
                                            let newInList = inList[k].zxSkStockTransItemReceivingList;
                                            for (let m = 0; m < newInList.length; m++) {
                                                inListArry.push(newInList[m]);
                                                if (m === 0) {
                                                    ysdPush += inList[k].billNo + ',';//预收单拼接
                                                    ysdIdPush += inList[k].id + ',';//id拼接
                                                }
                                                newInList[m].ysdNo = inList[k].billNo;
                                                // 后台修改后-可以注释
                                                newInList[m].otherExpense = newInList[m].inFee ? newInList[m].inFee : 0;
                                                newInList[m].otherExpenseTotal = newInList[m].inFeeTotal ? newInList[m].inFeeTotal : 0;
                                                newInList[m].isDeduct = inList[k].isDeduct;
                                                // 后台修改后-可以注释

                                                // 含税金额-重新计算
                                                newInList[m].stockAmt = Number((this.FloatMulTwo(newInList[m].qty, newInList[m].unitPrice)).toFixed(2));
                                                // 入账-金额-重新计算
                                                if (inList[k].isDeduct === '0') {//参与抵扣-0否   1是
                                                    newInList[m].amt = Number(parseFloat(Number(newInList[m].stockAmt) + Number(newInList[m].ysFeeTotal ? newInList[m].ysFeeTotal : 0) + Number(newInList[m].otherExpenseTotal ? newInList[m].otherExpenseTotal : 0)).toFixed(2));
                                                } else {
                                                    newInList[m].amt = Number(parseFloat(Number(newInList[m].stockAmtNoTax) + Number(newInList[m].ysFeeTotal ? newInList[m].ysFeeTotal : 0) + Number(newInList[m].otherExpenseTotal ? newInList[m].otherExpenseTotal : 0)).toFixed(2));
                                                }
                                                // 总价-重新计算-
                                                newInList[m].amtTotal = Number(parseFloat(Number(this.FloatMulTwo(newInList[m].qty, newInList[m].unitPrice)) + Number(newInList[m].ysFeeTotal ? newInList[m].ysFeeTotal : 0) + Number(newInList[m].otherExpenseTotal ? newInList[m].otherExpenseTotal : 0)).toFixed(2));//含税总价 
                                                zongjiaAll += newInList[m].amtTotal;
                                                ruzhangAll += newInList[m].amt;
                                                // 不含税金额-重新计算
                                                newInList[m].stockAmtNoTax = Number(((this.FloatMulTwo(newInList[m].qty, newInList[m].unitPrice)) * (Number(1 - (newInList[m].taxRate) * 0.01))).toFixed(2));//不含税金额
                                            }
                                        }
                                        this.setState({
                                            visibleBjdh: false,
                                            loadingBjdh: false
                                        }, () => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                zxSkInvoiceItemList: inListArry,
                                                ysdID: ysdIdPush,//拼接id
                                                ysdNo: ysdPush,//拼接-预收单编号
                                                amt: Number(Number(ruzhangAll).toFixed(2)),
                                                amtTotal: Number(Number(zongjiaAll).toFixed(2))
                                            })
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