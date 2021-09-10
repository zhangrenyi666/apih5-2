import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'costRatioId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect:true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZxSaCostRatioList',
                        otherParams: {
                            // orgID: departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZxSaCostRatio',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '2')) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxSaCostRatio',
                                    },
                                    onClick:(obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'audit',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '审核',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '2')) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定审核该条数据吗?',
                                    onOk: () => {
                                        obj.selectedRows[0].auditStatus = '1';
                                        this.props.myFetch('updateZxSaCostRatio', ...obj.selectedRows).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.refresh();
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                } else {
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                        },
                        {
                            name: 'auditee',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '反审核',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].auditStatus === '1') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定反审核该条数据吗?',
                                    onOk: () => {
                                        obj.selectedRows[0].auditStatus = '2';
                                        this.props.myFetch('updateZxSaCostRatio', ...obj.selectedRows).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.refresh();
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                } else {
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length && !data.filter(item => item.auditStatus === '1').length) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxSaCostRatio',
                            },
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'costRatioId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '机构名称',
                                dataIndex: 'companyName',
                                key: 'companyName',
                                filter: true,
                                width:150,
                                fixed:'left'
                            },
                            form: {
                                type: 'string',
                                addShow:false
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                onClick: 'detail',
                                width:150,
                                fixed:'left'
                            },
                            form: {
                                type: 'string',
                                addShow:false
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label:'项目名称',
                                field:'orgID',
                                type: 'select',
                                showSearch: true,
                                allowClear: false,
                                editShow:false,
                                detailShow:false,
                                required:true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        orgName: 'departmentName',
                                        companyId:'companyId',
                                        companyName:"companyName"
                                    }
                                },
                                onChange: (val, obj) => {
                                    let rowData = obj.form.getFieldsValue();
                                    if (val && rowData.periodTime) {
                                        this.props.myFetch('getZxSaCostRatioTotalAmt', { orgID: val, periodTime: moment(rowData.periodTime).valueOf() }).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.form.setFieldsValue({
                                                        totalAmt: data?.totalAmt || 0
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '结算期次',
                                dataIndex: 'period',
                                key: 'period',
                                width:100,
                            },
                            form: {
                                field:'periodTime',
                                type: 'month',
                                required: true,
                                onChange: (val, obj) => {
                                    let rowData = obj.form.getFieldsValue();
                                    console.log(rowData);
                                    if (val && rowData.orgID) {
                                        this.props.myFetch('getZxSaCostRatioTotalAmt', { orgID: rowData.orgID, periodTime: moment(val).valueOf() }).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.form.setFieldsValue({
                                                        totalAmt: data?.totalAmt || 0
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                dataIndex: 'reporter',
                                key: 'reporter',
                                width:100,
                            },
                            form: {
                                type: 'string',
                                initialValue:realName
                            }
                        },
                        {
                            table: {
                                title: '填报时间',
                                dataIndex: 'reporterTime',
                                key: 'reporterTime',
                                format:'YYYY-MM-DD',
                                width:100,
                            },
                            form: {
                                type: 'date',
                                initialValue:new Date()
                            }
                        },
                        {
                            table: {
                                title: '开累结算计入成本金额(万元)',
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                width:180,
                            },
                            form: {
                                type: 'number',
                                min:0,
                                initialValue:0,
                                addDisabled:true,
                                editDisabled:true
                            }
                        },
                        {
                            table: {
                                title: '库存金额(万元)',
                                dataIndex: 'stockAmt',
                                key: 'stockAmt',
                                width:100,
                            },
                            form: {
                                type: 'number',
                                min:0,
                                initialValue:0,
                            }
                        },
                        {
                            table: {
                                title: '财务账面成本(万元)',
                                dataIndex: 'bookAmt',
                                key: 'bookAmt',
                                width:120,
                            },
                            form: {
                                type: 'number',
                                min:0,
                                initialValue:0,
                            }
                        },
                        {
                            table: {
                                title: '已发生未入账的成本(万元)',
                                dataIndex: 'unrecordAmt',
                                key: 'unrecordAmt',
                                width:160,
                            },
                            form: {
                                type: 'number',
                                min:0,
                                initialValue:0,
                            }
                        },
                        {
                            table: {
                                title: '研发支出费用(万元)',
                                dataIndex: 'rdAmt',
                                key: 'rdAmt',
                                width:120,
                            },
                            form: {
                                type: 'number',
                                min:0,
                                initialValue:0,
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width:100,
                                fixed:'right',
                                type:'select'
                            },
                            form: {
                                type: 'select',
                                initialValue:'0',
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
                                        label: "已反审核",
                                        value: "2"
                                    }
                                ],
                                addShow:false,
                                addDisabled:true,
                                editDisabled:true
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;