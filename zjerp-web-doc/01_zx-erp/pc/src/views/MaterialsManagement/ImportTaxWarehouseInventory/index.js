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
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSendData: false,
            loadingSendData: false,
            id: 0,
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
    handleCancelSend = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    getItemList(obj, plmm, whOrgID, itemList, name) {
        const { myFetch } = this.props;
        const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;

        if (plmm === '4') {
            // 二次盘点
            if (name === 'detailDrawer') {
                obj.qnnTableInstance.btnAction({
                    btnConfig: {
                        name: "detail",
                        field: 'detail',
                        drawerTitle: "详情"
                    },
                    attrBindInfo: {
                        rowData: {
                            ...obj.rowData,
                            zxSkMakeItemList: itemList
                        }
                    }
                })
            } else {
                obj.qnnTableInstance.setDeawerValues({
                    ...obj.rowData,
                    zxSkMakeItemList: itemList
                });
            }

        } else {
            // 首次盘点

            let params = {
                whOrgID: whOrgID,
                companyId: companyId
            };
            myFetch('startZxSkMake', params).then(({ success, message, data }) => {
                if (success) {
                    for (let k = 0; k < data.length; k++) {
                        data[k].makeInvQty = 0;//盘点数量
                        data[k].makeInvAmt = 0;//盘点金额

                        data[k].resUnit = data[k].unit;//单位
                        data[k].accountsQty = data[k].stockQty;//账面数量
                        data[k].accountsAmt = data[k].stockAmt;//账面金额

                        data[k].diffQty = data[k].makeInvQty - data[k].accountsQty;//盈亏数量
                        data[k].diffAmt = Number(
                            (Number(this.FloatMulTwo(data[k].stockPrice, data[k].makeInvQty)) - data[k].accountsAmt).toFixed(2)
                        );//盈亏金额
                    }
                    if (name === 'detailDrawer') {
                        obj.qnnTableInstance.btnAction({
                            btnConfig: {
                                name: "detail",
                                field: 'detail',
                                drawerTitle: "详情"
                            },
                            attrBindInfo: {
                                rowData: {
                                    ...obj.rowData,
                                    zxSkMakeItemList: data
                                }
                            }
                        })
                    } else {
                        obj.qnnTableInstance.setDeawerValues({
                            ...obj.rowData,
                            zxSkMakeItemList: data
                        });
                    }

                } else {
                    Msg.error(message);
                }
            });
        }
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData, lockProjectId, loading, idPreview, name } = this.state;
        const { ext1, departmentId, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSkMakeList',
                        otherParams: {
                            projectId: jurisdiction
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
                                field: 'projectId',
                                initialValue: projectId,
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
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                initialValue: projectName,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '盘点仓库',
                                dataIndex: 'whOrgID',
                                key: 'whOrgID',
                                width: 160,
                                type: 'select',
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
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseList',
                                    otherParams: {
                                        parentOrgID: jurisdiction
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 12,
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
                                title: '盘点日期',
                                width: 120,
                                dataIndex: 'makeInvDate',
                                key: 'makeInvDate',
                                // onClick: 'detail',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                },
                                onClick: (obj) => {
                                    // obj.qnnTableInstance.openDrawer(() => {
                                    this.getItemList(obj, obj.rowData.plmm, obj.rowData.whOrgID, obj.rowData.zxSkMakeItemList, 'detailDrawer');
                                    // })
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'makeInvDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 12,
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
                                title: '经手人',
                                filter: true,
                                dataIndex: 'handler',

                                key: 'handler',
                            },
                            form: {
                                type: 'string',
                                field: 'handler',
                                spanForm: 12,
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
                                title: '负责人',
                                filter: true,
                                dataIndex: 'principal',
                                key: 'principal',
                            },
                            form: {
                                type: 'string',
                                field: 'principal',
                                spanForm: 12,
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
                                title: '状态',
                                width: 100,
                                dataIndex: 'status',
                                key: 'status',
                                render: (data) => {
                                    let x = '';
                                    switch (data) {
                                        case '0': x = "盘点中";
                                            break;
                                        case '1': x = "已盘点";
                                            break;
                                        default:
                                            x = '';
                                    }
                                    return x
                                }
                            },
                            form: {
                                type: 'select',
                                field: 'status',
                                hide: true,
                                optionData: [
                                    {
                                        label: '盘点中',
                                        value: '0'
                                    },
                                    {
                                        label: '已盘点',
                                        value: '1'
                                    }
                                ]
                            }
                        },
                        {
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
                                spanForm: 12,
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
                                type: 'qnnTable',
                                field: 'zxSkMakeItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'resID',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'resID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                width: 200,
                                                dataIndex: 'resCode',
                                                key: 'resCode'
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                field: 'resCode',
                                            }
                                        },

                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 150,
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
                                                width: 80
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '单位',
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
                                                title: '单价',
                                                dataIndex: 'stockPrice',
                                                key: 'stockPrice',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockPrice'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '账面数量',
                                                width: 100,
                                                dataIndex: 'accountsQty',
                                                key: 'accountsQty',
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'accountsQty'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '账面金额',
                                                width: 120,
                                                dataIndex: 'accountsAmt',
                                                key: 'accountsAmt',
                                                render: (data, rowData) => {
                                                    let color = 'black';
                                                    if (data < 0) {
                                                        color = 'red';
                                                    } else if (data > 0) {
                                                        color = 'green';
                                                    }
                                                    return <a style={{ color: color }}>{data}</a>
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'accountsAmt'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '盘点数量',
                                                width: 120,
                                                dataIndex: 'makeInvQty',
                                                key: 'makeInvQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'makeInvQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.accountsQty) {
                                                            newRowData.diffQty = colVal - rowData.accountsQty;//盈亏数量
                                                        }
                                                        if (rowData.stockPrice) {
                                                            newRowData.makeInvAmt = Number(this.FloatMulTwo(rowData.stockPrice, colVal).toFixed(2));//盘点金额
                                                        }
                                                        if (rowData.accountsAmt) {
                                                            newRowData.diffAmt = Number(
                                                                (Number(this.FloatMulTwo(rowData.stockPrice, colVal)) - rowData.accountsAmt).toFixed(2)
                                                            );//盈亏金额
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
                                                title: '盘点金额',
                                                width: 120,
                                                dataIndex: 'makeInvAmt',
                                                key: 'makeInvAmt',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                disabled: (obj, rows) => {
                                                    if (obj.record.stockPrice === 0) {
                                                        return false
                                                    } else {
                                                        return true
                                                    }
                                                },
                                                field: 'makeInvAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '盈亏数量',
                                                width: 120,
                                                dataIndex: 'diffQty',
                                                key: 'diffQty',
                                                render: (data, rowData) => {
                                                    let color = 'black';
                                                    if (data < 0) {
                                                        color = 'red';
                                                    } else if (data > 0) {
                                                        color = 'green';
                                                    }
                                                    return <a style={{ color: color }}>{data}</a>
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'diffQty',
                                                precision: 3
                                            }
                                        },
                                        {
                                            table: {
                                                title: '盈亏金额',
                                                width: 120,
                                                dataIndex: 'diffAmt',
                                                key: 'diffAmt',
                                                render: (data, rowData) => {
                                                    let color = 'black';
                                                    if (data < 0) {
                                                        color = 'red';
                                                    } else if (data > 0) {
                                                        color = 'green';
                                                    }
                                                    return <a style={{ color: color }}>{data}</a>
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'diffAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                width: 120,
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
                                    actionBtns: []
                                },
                                hide: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '新增') {
                                        return true
                                    } else {
                                        return false
                                    }

                                }
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
                                spanForm: 12,
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
                                field: 'plmm',
                                initialValue: '3',
                                hide: true
                            }
                        }
                    ]}
                    method={{
                        willExecuteFunAdd: (val) => {
                            val.btnCallbackFn.setBtnsDisabled('remove', 'addsubmit');
                            val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                        },
                        onClickFunAdd: (val) => {
                            const { myFetch } = this.props;
                            val.btnCallbackFn.setBtnsDisabled('add', 'addsubmit');
                            val.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                            myFetch('addZxSkMake', val._formData).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        Msg.success(message);
                                        // -------------------------
                                        let params = {
                                            whOrgID: data.whOrgID,
                                            // orgID: data.orgID,//暂时不传
                                            companyId: companyId
                                        };
                                        myFetch('startZxSkMake', params).then(({ success, message, data }) => {
                                            if (success) {
                                                let dataItem = data;
                                                if (dataItem) {
                                                    for (let k = 0; k < dataItem.length; k++) {
                                                        dataItem[k].resUnit = dataItem[k].unit;//单位
                                                        dataItem[k].accountsQty = dataItem[k].stockQty;//账面数量
                                                        dataItem[k].accountsAmt = dataItem[k].stockAmt;//账面金额
                                                    }
                                                    // this.setState({
                                                    //     data: dataItem
                                                    // })
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        zxSkMakeItemList: data
                                                    });
                                                }

                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                        // ----------------------------
                                        this.table.refresh();
                                        this.table.closeDrawer();
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                        },
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
                                Msg.warn('已盘点的不能开始盘点!');
                                return false
                            } else { }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        onClickFunEdit: (obj) => {
                            this.getItemList(obj, obj.selectedRows[0].plmm, obj.selectedRows[0].whOrgID, obj.selectedRows[0].zxSkMakeItemList, 'editDrawer');
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
                        disabledFunOver: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunOver: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('已结束盘点的不能盘点!');
                                } else {
                                    // obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '确定盘点选中的数据吗?',
                                        onOk: () => {
                                            myFetch('checkZxSkMake', obj.selectedRows[0]).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.table.refresh();
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });

                                }
                            } else {
                                Msg.warn('只能盘点一条数据！')
                            }
                        },
                        onClickFunPrint: (obj) => {

                        },
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
                        onClickFundDel: (obj) => {
                            const { myFetch } = this.props;
                            let arry = [];
                            for (let m = 0; m < obj.selectedRows.length; m++) {
                                if (obj.selectedRows[m].status === '1') {
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].status);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未盘点的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkMake', obj.selectedRows).then(
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
                                name: obj.selectedRows[0].projectName
                            })
                        }

                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '新增',
                    //         willExecute: 'bind:willExecuteFunAdd',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'diysubmit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 field: 'addsubmit',
                    //                 onClick: 'bind:onClickFunAdd'
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '开始盘点',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         onClick: 'bind:onClickFunEdit',
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
                    //                     apiName: 'updateZxSkMake'
                    //                 }
                    //             }
                    //         ]
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
                    //         label: '结束盘点',
                    //         disabled: 'bind:disabledFunOver',
                    //         onClick: 'bind:onClickFunOver'
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
                    //         onClick: 'bind:onClickFundDel'
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportTaxWarehouseInventory"
                            }
                        }
                    }}
                />
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
                            }} src={`${ureport}preview?_u=minio:ZxSkMake.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}`}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="修改盘点日期"
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
                                    field: 'makeInvDate',
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
                                        values.makeInvDate = obj.values.makeInvDate;

                                        this.props.myFetch('updateZxSkMake', values).then(
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
            </div>
        );
    }
}

export default index;