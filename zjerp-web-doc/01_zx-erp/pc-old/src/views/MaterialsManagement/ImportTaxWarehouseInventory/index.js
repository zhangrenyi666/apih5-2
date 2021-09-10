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
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSendData: false,
            loadingSendData: false,
            id: 0,
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
    handleCancelSend = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    getItemList(plmm, whOrgID, itemList) {
        const { myFetch } = this.props;
        const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        if (plmm === '4') {
            // 二次盘点
            this.table.qnnForm.form.setFieldsValue({
                zxSkMakeItemList: itemList
            });
        } else {
            // 首次盘点
            let params = {
                whOrgID: whOrgID,
                companyID: companyId
            };
            myFetch('startZxSkMake', params).then(({ success, message, data }) => {
                if (success) {
                    for (let k = 0; k < data.length; k++) {
                        data[k].resUnit = data[k].unit;//单位
                        data[k].accountsQty = data[k].stockQty;//账面数量
                        data[k].accountsAmt = data[k].stockAmt;//账面金额
                    }
                    this.table.qnnForm.form.setFieldsValue({
                        zxSkMakeItemList: data
                    });
                } else {
                    Msg.error(message);
                }
            });
        }
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData } = this.state;
        const { departmentId, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                            projectId: departmentId
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
                                            parentOrgID: departmentId
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
                                        parentOrgID: departmentId
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
                                // format: 'YYYY-MM-DD',
                                key: 'makeInvDate',
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                },
                                willExecute: (obj)=> {
                                    this.getItemList(obj.rowData.plmm, obj.rowData.whOrgID,obj.rowData.zxSkMakeItemList);
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
                                type: 'select'
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
                                    tableTdEdit: true,
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
                                                tooltip: 23,
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
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
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
                                                        }, 600)
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
                                                tooltip: 23,
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
                                    apiName:'upload'
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
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            willExecute: (val) => {

                                val.btnCallbackFn.setBtnsDisabled('remove', 'addsubmit');
                                val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    onClick: (val) => {
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
                                                        companyID: companyId
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
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '开始盘点',
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已盘点的不能开始盘点!');
                                } else {
                                    this.getItemList(obj.selectedRows[0].plmm, obj.selectedRows[0].whOrgID,obj.selectedRows[0].zxSkMakeItemList);
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
                                        apiName: 'updateZxSkMake'
                                    }
                                }
                            ]
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
                            label: '结束盘点',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
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
                                                            this.table.clearSelectedRows();
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
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '打印',//???
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
                                            myFetch('batchDeleteUpdateZxSkMake', obj.selectedRows).then(
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
                                        values.makeInvDate = obj.values.makeInvDate;

                                        this.props.myFetch('updateZxSkMake', values).then(
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