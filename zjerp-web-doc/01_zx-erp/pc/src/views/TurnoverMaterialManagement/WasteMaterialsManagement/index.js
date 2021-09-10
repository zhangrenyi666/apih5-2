import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
import PurchaeApplicatin from './form';
import DeetailForm from './deetailForm';
import Operation from '../../MaterialsManagement/ImportPriceLimitChange/operation';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxSkWornOutId',
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
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() { }
    // 单据编号
    getDjbhFun(val) {
        const { myFetch } = this.props;
        myFetch('getZxSkWornOutNo', {
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
    getTotalFunc(rowData) {//计算表格内的总值，显示在表单上

        let ruzhangAll = 0;
        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.zxSkWornOutItemList.map((item) => {
            if (item.zxSkWornOutItemId === rowData.zxSkWornOutItemId) {
                ruzhangAll += Number(rowData.amt ? rowData.amt : 0);
            } else {
                ruzhangAll += Number(item.amt ? item.amt : 0);
            }
            return item;
        })

        this.table.qnnForm.form.setFieldsValue({
            purchaseAmt: Number(ruzhangAll)
        })
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
    render() {
        const { ext1, departmentId, companyName, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;

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
                        apiName: 'getZxSkWornOutList',
                        otherParams: {
                            orgID: jurisdiction
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkWornOutId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'comName',
                                initialValue: companyName,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'workId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'comID',
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
                                width: 200,
                                render: (data, rowData) => {
                                    // 审核状态为未审核或者空的标红,如果单据明细中有预收的标蓝,
                                    // 收料单如果单据明细中有预收的并且没有冲账完的，则标蓝。这个规则可以放到做完冲账后再做
                                    let color = '';
                                    if (rowData.apih5FlowStatus !== '-1') {
                                        color = '';
                                    } else {
                                        color = 'red';
                                    }
                                    return <a style={{ color: color }}>{data}</a>
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
                                }
                            },
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 160,
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
                                    if (this.table?.qnnForm?.form) {
                                        this.table.qnnForm.form.setFieldsValue({
                                            orgName: rowData.itemData.orgName
                                        });
                                        this.getDjbhFun(val);
                                    }

                                }
                            },
                        },
                        {
                            table: {
                                title: '申请单位',
                                dataIndex: 'applyOrgID',
                                key: 'applyOrgID',
                                width: 200,
                                type: 'select'
                            },
                            form: {
                                field: 'applyOrgID',
                                type: 'selectByPaging',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        applyOrgName: 'departmentName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
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
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'applyOrgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '拟处理金额(万元)',
                                width: 100,
                                dataIndex: 'purchaseAmt',
                                key: 'purchaseAmt',
                            },
                            form: {
                                type: 'number',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'purchaseAmt',
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
                                title: '填报日期',
                                width: 120,
                                dataIndex: 'reportDate',
                                format: 'YYYY-MM-DD',
                                key: 'reportDate'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'reportDate',
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
                                    if (val) {
                                        this.dateChange(val);
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '废旧物资处理说明',
                                width: 300,
                                tooltip: 23,
                                dataIndex: 'approval',
                                key: 'approval',
                            },
                            form: {
                                type: 'textarea',
                                field: 'approval',
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
                                field: 'zxSkWornOutItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zxSkWornOutItemId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    wrappedComponentRef: (me) => {
                                        this.tableItem = me;
                                    },
                                    // tableTdEdit: true,
                                    // tableTdEditSaveCb: function (newRowData, oldRowData, props) {

                                    // },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zxSkWornOutItemId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName',
                                                required: true
                                            }
                                        },

                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 80,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'unit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '数量',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                width: 80,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'qty',
                                                precision: 3,
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '总金额(万元)',
                                                dataIndex: 'amt',
                                                key: 'amt',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'amt',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        // 计算合计
                                                        this.getTotalFunc(rowData);
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...rowData
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
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
                                                },
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
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                if (this.table && this.table.qnnForm) {
                                                    let allData = this.table.qnnForm.form.getFieldsValue().zxSkWornOutItemList;
                                                    let selectDa = obj.selectedRows;
                                                    let selectRowData = [];
                                                    confirm({
                                                        content: '确定删除选中的数据吗?',
                                                        onOk: () => {
                                                            let obj = {};
                                                            selectDa.forEach(function (item, index) {
                                                                obj[item.zxSkWornOutItemId] = true //将数组arr2中的元素值作为obj 中的键，值为true；
                                                            })
                                                            allData.forEach(function (item, index) {
                                                                if (!obj[item.zxSkWornOutItemId]) {
                                                                    selectRowData.push(item)
                                                                } else {
                                                                }
                                                            })

                                                            if (this.table && this.table.qnnForm) {
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    zxSkWornOutItemList: selectRowData
                                                                });
                                                                let zongjiaAll = 0;
                                                                selectRowData.map((item) => {
                                                                    zongjiaAll += Number(item.amt ? item.amt : 0);
                                                                    return item;
                                                                })
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    purchaseAmt: Number(zongjiaAll.toFixed(2))
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
                            table: {
                                title: '审核',
                                width: 100,
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '评审通过';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'apih5FlowStatus',
                                hide: true
                            }
                        },
                    ]}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            console.log(data);
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2' || data[0].zxSkWornOutId.length > 32) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择未审核的数据!');
                                return false
                            } else {

                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunShenPi: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus === '-1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunJinDu: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus !== '-1' && data[0].zxSkWornOutId.length <= 32)) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].apih5FlowStatus === '1' || data[m].apih5FlowStatus === '2' || data[m].zxSkWornOutId.length > 32) {
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
                                if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2' || obj.selectedRows[0].zxSkWornOutId.length > 32) {
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].apih5FlowStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未审核的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkWornOut', obj.selectedRows).then(
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
                        }
                    }}
                    componentsKey={{
                        PurchaeApplicatin: (obj) => {
                            return <PurchaeApplicatin {...obj} flowData={obj.selectedRows[0]} />
                        },
                        DeetailForm: (obj) => {
                            return <DeetailForm {...obj} flowData={obj.selectedRows[0]} />
                        },
                        Operation: (props) => {
                            return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkWornOutDetail'} flowId={'zxSkWornOutWorkId'} {...props} />
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "WasteMaterialsManagement"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;