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
    getDjbhFun(val, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkStockTransferTransferOrderNo', {
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
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData, lockProjectId, loading, idPreview,name } = this.state;
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
            } else { }
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
                        apiName: 'getZxSkStockTransferTransferOrderList',
                        otherParams: {
                            outOrgID: jurisdiction
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
                                filter: true,
                                width: 320,
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
                                title: '调出单位',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                // type: 'select',
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
                                field: 'outOrgID',
                                type: 'select',
                                required: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        "outOrgName": "orgName",
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
                                children: [
                                    {
                                        field: 'whOrgID'
                                    }
                                ],
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
                                        // 000004546问题二---20210401修改
                                        // let inOrgID = this.table.qnnForm.form.getFieldsValue().inOrgID;
                                        // if (val === inOrgID) {
                                        //     Msg.warn('调出单位与接收单位相同,请重新选择！');
                                        //     setTimeout(() => {
                                        //         this.table.qnnForm.form.setFieldsValue({
                                        //             outOrgID: '',
                                        //             whOrgID: '',
                                        //         })
                                        //     }, 200)

                                        // } else {
                                        //     this.table.qnnForm.form.setFieldsValue({
                                        //         whOrgID: '',
                                        //     })
                                        // }
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemTransferOrderList']);
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
                                field: 'outOrgName',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'warehouseName',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '调出仓库',
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

                                    let inWhOrgID = this.table.qnnForm.form.getFieldsValue().inWhOrgID;
                                    if (val === inWhOrgID) {
                                        Msg.warn('调出仓库与接收仓库相同,请重新选择！');
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                whOrgID: ''
                                            })
                                        }, 200)
                                    }
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemTransferOrderList']);
                                        // this.table.qnnForm.qnnSetState({
                                        //     id:null
                                        // })
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '对内/对外',
                                dataIndex: 'inOrOut',
                                key: 'inOrOut',
                                width: 200,
                                type: 'select'
                            },
                            form: {
                                field: 'inOrOut',
                                type: 'select',
                                required: true,
                                editDisabled: true,
                                initialValue: '1',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    // {
                                    //     label: "对内调出",
                                    //     value: "0"
                                    // },
                                    {
                                        label: "对外调出",
                                        value: "1"
                                    }
                                ],
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
                                onChange: () => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        inOrgID: '',
                                        inOrgName: ''
                                    })
                                }
                            },
                        },
                        {
                            table: {
                                title: '接收单位',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName',
                                width: 200
                            },
                            isInTable: false,
                            form: {
                                field: 'inOrgName',
                                required: true,
                                type: 'string',
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
                                dependencies: ['inOrOut'],
                                hide: (obj) => {
                                    var inOrOut = obj.form.getFieldsValue()?.inOrOut || obj.clickCb?.rowData?.inOrOut || obj.clickCb?.selectedRows[0]?.inOrOut;
                                    if (inOrOut === '0') {
                                        return true
                                    }
                                    return false
                                }
                            },
                        },
                        {

                            isInTable: false,
                            form: {
                                type: 'number',
                                field: 'billType',
                                initialValue: 16,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '接收单位',
                                dataIndex: 'inOrgID',
                                key: 'inOrgID',
                                width: 200
                            },
                            isInTable: false,
                            form: {
                                field: 'inOrgID',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        "inOrgName": "orgName"
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkStockTransferOrderReceiveOrg',
                                    otherParams: {
                                        //2021.06.21 张启明修
                                        // contrStatus: "1",
                                        orgID: jurisdiction
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
                                },
                                dependencies: ['inOrOut'],
                                hide: (obj) => {
                                    var inOrOut = obj.form.getFieldsValue()?.inOrOut || obj.clickCb?.rowData?.inOrOut || obj.clickCb?.selectedRows[0]?.inOrOut;
                                    if (inOrOut) {
                                        if (inOrOut === '1') {
                                            return true
                                        }
                                        return false
                                    } else {
                                        return true
                                    }

                                },
                                onChange: (val, obj) => {
                                    let outOrgID = this.table.qnnForm.form.getFieldsValue().outOrgID;
                                    if (val === outOrgID) {
                                        Msg.warn('调出单位与接收单位相同,请重新选择！');
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                inOrgID: '',
                                                inWhOrgID: '',
                                            })
                                        }, 200)
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({
                                            inWhOrgID: '',
                                        })
                                    }
                                    this.table.qnnForm.form.setFieldsValue({
                                        inWhOrgID: ''
                                    })
                                },
                                children: [
                                    {
                                        field: 'inWhOrgID'
                                    }
                                ],
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '接收仓库',
                                field: 'inWhOrgID',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id'
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                dependencies: ['inOrOut'],
                                hide: (obj) => {
                                    var inOrOut = obj.form.getFieldsValue()?.inOrOut || obj.clickCb?.rowData?.inOrOut || obj.clickCb?.selectedRows[0]?.inOrOut;
                                    if (inOrOut) {
                                        if (inOrOut === '1') {
                                            return true
                                        }
                                        return false
                                    } else {
                                        return true
                                    }
                                },
                                onChange: (val) => {
                                    let whOrgID = this.table.qnnForm.form.getFieldsValue().whOrgID;
                                    if (val === whOrgID) {
                                        Msg.warn('调出仓库与接收仓库相同,请重新选择！');
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                // inOrgID: '',
                                                inWhOrgID: ''
                                            })
                                        }, 200)
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
                                        value: 'id'
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
                                type: 'select',
                                editDisabled: true,
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
                                            whOrgIDVal = aa.whOrgID;
                                            outOrgIDVal = aa.outOrgID;
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
                                    this.table.qnnForm.form.setFieldsValue({
                                        cantEdit: 'can'
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemTransferOrderList']);
                                        // this.table.qnnForm.qnnSetState({
                                        //     id:null
                                        // })
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
                                        let outOrgID = this.table.qnnForm.form.getFieldsValue().outOrgID;
                                        if (outOrgID || val) {
                                            this.getDjbhFun(outOrgID, val);
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
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockTransItemTransferOrderList',
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
                                                allowClear: false,
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
                                                                limit: 5,
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
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        // 设置编辑行值方法
                                                        let all = Number(this.FloatMulTwo(itemData.itemData.stockPrice, itemData.itemData.stockQty).toFixed(2));
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            resUnit: itemData.itemData.unit,
                                                            spec: itemData.itemData.spec,
                                                            stdPrice: itemData.itemData.stockPrice,
                                                            stockQtyNu: itemData.itemData.stockQty,
                                                            outQty: itemData.itemData.stockQty,
                                                            resID: itemData.itemData.resID,
                                                            outAmt: all,
                                                            outPrice: 0,
                                                            transOutAmt: 0
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
                                                                outQty: 0,
                                                                outAmt: 0,
                                                                stdPrice: 0,
                                                                outPrice: 0,
                                                                transOutAmt: 0
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
                                                title: '实发数量',
                                                width: 120,
                                                dataIndex: 'outQty',
                                                key: 'outQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                field: 'outQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }

                                                        if (rowData.outQty > rowData.stockQtyNu) {
                                                            Msg.warn('超出库存数量！');
                                                            newRowData.outQty = Number(rowData.stockQtyNu);
                                                        } else {
                                                            if (rowData.outPrice) {
                                                                newRowData.transOutAmt = Number(this.FloatMulTwo(rowData.outQty, rowData.outPrice).toFixed(2));//拨让总价
                                                            }
                                                            if (rowData.stdPrice) {
                                                                newRowData.outAmt = Number(this.FloatMulTwo(rowData.outQty, rowData.stdPrice).toFixed(2));//金额
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
                                                title: '平均单价',
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
                                                title: '拨让单价',
                                                width: 100,
                                                dataIndex: 'outPrice',
                                                key: 'outPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outPrice',
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        newRowData.outPrice = Number(rowData.outPrice.toFixed(2));
                                                        if (rowData.outQty) {
                                                            newRowData.transOutAmt = Number(this.FloatMulTwo(rowData.outPrice, rowData.outQty).toFixed(2));//拨让总价
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
                                                title: '金额',
                                                width: 100,
                                                dataIndex: 'outAmt',
                                                key: 'outAmt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outAmt',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '拨让总价',
                                                width: 100,
                                                dataIndex: 'transOutAmt',
                                                key: 'transOutAmt',

                                            },
                                            form: {
                                                type: 'number',
                                                field: 'transOutAmt',
                                                precision: 2,
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
                                title: '单据状态',
                                width: 100,
                                fixed: 'right',
                                dataIndex: 'billStatus',
                                key: 'billStatus',
                                render: (data) => {
                                    let x = '';
                                    if (data === '0') {
                                        x = "未审核";
                                    } else if (data === '1') {
                                        x = "已审核";
                                    } else if (data === '2') {
                                        x = "已收料";
                                    } else if (data === '3') {
                                        x = "已退回";
                                    } else {
                                        
                                    }
                                    
                                    // switch (data) {
                                    //     case '0': x = "未审核";
                                    //         break;
                                    //     case '1': x = "已审核";
                                    //         break;
                                    //     case '2': x = "已收料";
                                    //         break;
                                    //     case '3': x = "已退回";
                                    //         break;
                                    // }
                                    return x
                                }
                            },
                            form: {
                                type: 'select',
                                field: 'billStatus',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "未审核",
                                        value: "0"
                                    },
                                    {
                                        label: "已审核",
                                        value: "1"
                                    },
                                    {
                                        label: "已收料",
                                        value: "2"
                                    },
                                    {
                                        label: "已退回",
                                        value: "3"
                                    }
                                ],
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {//???
                                return true;
                            } else {
                                if (data[0].inOrOut === '0') {
                                    if (data[0].companyId === companyId) {
                                        if (data[0].outOrgID === jurisdiction) {
                                            if (data[0].billStatus === '1' || data[0].billStatus === '2' || data[0].billStatus === '3') {
                                                return true
                                            } else {
                                                return false
                                            }
                                        } else {
                                            return true
                                        }
                                    } else {
                                        return true
                                    }
                                } else {
                                    if (data[0].billStatus === '1' || data[0].billStatus === '2' || data[0].billStatus === '3') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            // 校验权限
                            if (obj.selectedRows[0].inOrOut === '0') {
                                if (obj.selectedRows[0].companyId === companyId) {
                                    if (obj.selectedRows[0].outOrgID === jurisdiction) {
                                        if (obj.selectedRows[0].billStatus === '1' || obj.selectedRows[0].billStatus === '2' || obj.selectedRows[0].billStatus === '3') {
                                            obj.btnCallbackFn.clearSelectedRows();
                                            Msg.warn('请选择未审核的数据!');
                                            return false
                                        } else {

                                        }
                                    } else {
                                        obj.btnCallbackFn.clearSelectedRows();
                                        Msg.warn('当前用户没有权限修改!');
                                        return false
                                    }
                                } else {
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('当前用户没有权限修改!');
                                    return false
                                }
                            } else {
                                if (obj.selectedRows[0].billStatus === '1' || obj.selectedRows[0].billStatus === '2' || obj.selectedRows[0].billStatus === '3') {
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('请选择未审核的数据!');
                                    return false
                                } else { }
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunShenPi: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {//???
                                return true;
                            } else {
                                if (data[0].inOrOut === '0') {
                                    if (data[0].companyId === companyId) {
                                        if (data[0].outOrgID === jurisdiction) {
                                            if (data[0].billStatus === '0') {
                                                return false
                                            } else {
                                                return true
                                            }
                                        } else {
                                            return true
                                        }
                                    } else {
                                        return true
                                    }
                                } else {
                                    if (data[0].billStatus === '0') {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        },
                        onClickFunShenPi: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].inOrOut === '0') {
                                    if (obj.selectedRows[0].companyId === companyId) {
                                        if (obj.selectedRows[0].outOrgID === jurisdiction) {
                                            if (obj.selectedRows[0].billStatus === '0') {
                                                confirm({
                                                    content: '确定审核选中的数据吗?',
                                                    onOk: () => {
                                                        // obj.selectedRows[0].companyID = '1';
                                                        myFetch('checkZxSkStockTransferTransferOrder', { id: obj.selectedRows[0].id }).then(
                                                            ({ data, success, message }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    this.table.refresh();
                                                                } else {
                                                                    Msg.warn(message)
                                                                    this.table.refresh();
                                                                }
                                                            }
                                                        );
                                                    }
                                                });
                                            } else {
                                                obj.btnCallbackFn.closeDrawer();
                                                Msg.warn('请选择未审核的数据!');

                                            }
                                        } else {
                                            obj.btnCallbackFn.closeDrawer();
                                            Msg.warn('当前用户没有权限审核!');
                                        }
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('当前用户没有权限审核!');
                                    }
                                } else {
                                    if (obj.selectedRows[0].billStatus === '0') {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                // obj.selectedRows[0].companyID = '1';
                                                myFetch('checkZxSkStockTransferTransferOrder', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.warn(message)
                                                            this.table.refresh();
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('请选择未审核的数据!');

                                    }
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
                        // onClickFunPrint: (obj) => {

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
                                        myFetch('batchDeleteUpdateZxSkStockTransferTransferOrder', obj.selectedRows).then(
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
                        disabledFunIn: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {//???
                                return true;
                            } else {
                                if (data[0].inOrOut === '0') {
                                    if (data[0].companyId === companyId) {
                                        if (data[0].outOrgID === jurisdiction) {
                                            if (data[0].billStatus === '1') {
                                                return false
                                            } else {
                                                return true
                                            }
                                        } else {
                                            return true
                                        }
                                    } else {
                                        return true
                                    }
                                } else {
                                    if (data[0].billStatus === '1') {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        },
                        onClickFunIn: (obj) => {//需要传-账号的项目信息
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].inOrOut === '0') {
                                    if (obj.selectedRows[0].companyId === companyId) {
                                        if (obj.selectedRows[0].inOrgID === jurisdiction) {
                                            if (obj.selectedRows[0].billStatus === '1') {
                                                confirm({
                                                    content: '确定收料选中的数据吗?',
                                                    onOk: () => {
                                                        // obj.selectedRows[0].companyID = '1';
                                                        myFetch('checkZxSkStockTransferTransferOrderIn', { id: obj.selectedRows[0].id }).then(
                                                            ({ data, success, message }) => {
                                                                if (success) {
                                                                    this.table.refresh();
                                                                } else {
                                                                    Msg.warn(message)
                                                                    this.table.refresh();
                                                                }
                                                            }
                                                        );
                                                    }
                                                });
                                            } else {
                                                obj.btnCallbackFn.closeDrawer();
                                                Msg.warn('请选择已审核的数据!');
                                            }
                                        } else {
                                            obj.btnCallbackFn.closeDrawer();
                                            Msg.warn('没有收料权限!');
                                        }
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('没有收料权限!');
                                    }
                                } else {
                                    if (obj.selectedRows[0].billStatus === '1') {
                                        confirm({
                                            content: '确定收料选中的数据吗?',
                                            onOk: () => {
                                                // obj.selectedRows[0].companyID = '1';
                                                myFetch('checkZxSkStockTransferTransferOrderIn', { id: obj.selectedRows[0].id }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.warn(message)
                                                            this.table.refresh();
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('请选择已审核的数据!');
                                    }
                                }
                            } else {
                                Msg.warn('只能收料一条数据！')
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunOut: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {//???
                                return true;
                            } else {
                                if (data[0].inOrOut === '0') {
                                    if (data[0].companyId === companyId) {
                                        if (data[0].outOrgID === jurisdiction) {
                                            if (data[0].billStatus === '1') {
                                                return false
                                            } else {
                                                return true
                                            }
                                        } else {
                                            return true
                                        }
                                    } else {
                                        return true
                                    }
                                } else {
                                    if (data[0].billStatus === '1') {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        },
                        onClickFunOut: (obj) => {//需要传-账号的项目信息
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].inOrOut === '0') {
                                    if (obj.selectedRows[0].companyId === companyId) {
                                        if (obj.selectedRows[0].inOrgID === jurisdiction) {
                                            if (obj.selectedRows[0].billStatus === '1') {
                                                confirm({
                                                    content: '确定退回选中的数据吗?',
                                                    onOk: () => {
                                                        // obj.selectedRows[0].companyID = '1';
                                                        myFetch('checkZxSkStockTransferTransferOrderOut', { id: obj.selectedRows[0].id }).then(
                                                            ({ data, success, message }) => {
                                                                if (success) {
                                                                    this.table.refresh();
                                                                } else {
                                                                    Msg.warn(message)
                                                                    this.table.refresh();
                                                                }
                                                            }
                                                        );
                                                    }
                                                });
                                            } else {
                                                obj.btnCallbackFn.closeDrawer();
                                                Msg.warn('请选择已审核的数据!');

                                            }
                                        } else {
                                            obj.btnCallbackFn.closeDrawer();
                                            Msg.warn('没有收料权限!');
                                        }
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('没有收料权限!');
                                    }
                                } else {
                                    if (obj.selectedRows[0].billStatus === '1') {
                                        confirm({
                                            content: '确定退回选中的数据吗?',
                                            onOk: () => {
                                                // obj.selectedRows[0].companyID = '1';
                                                myFetch('checkZxSkStockTransferTransferOrderOut', { id: obj.selectedRows[0].id }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.warn(message)
                                                            this.table.refresh();
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    } else {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('请选择已审核的数据!');
                                    }
                                }
                            } else {
                                Msg.warn('只能退回一条数据！')
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
                    //                     apiName: 'addZxSkStockTransferTransferOrder'
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
                    //                     apiName: 'updateZxSkStockTransferTransferOrder'
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
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '调入方收料',
                    //         disabled: 'bind:disabledFunIn',
                    //         onClick: 'bind:onClickFunIn'
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '调入方退回',
                    //         disabled: 'bind:disabledFunOut',
                    //         onClick: 'bind:onClickFunOut'
                    //     },
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportTaxTransferOrder"
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

                                        this.props.myFetch('updateZxSkStockTransferTransferOrder', values).then(
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
                            <iframe  width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:ZxSkStockTransferTransferOrder.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}
                            `}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;