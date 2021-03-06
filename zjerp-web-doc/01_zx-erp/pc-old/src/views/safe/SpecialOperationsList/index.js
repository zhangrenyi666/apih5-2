import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import TabTwoList from './TabTwo';
import moment from 'moment';
const config = {
    antd: {
        rowKey: 'zxSfSpecialEmpId',
        size: 'small'
    },
    drawerConfig: {
        width: window.screen.width * 0.7
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }
    render() {
        const { departmentId, departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSfSpecialEmpList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                                {
                                    name: 'diySubmit',//内置add del
                                    field: 'diySubmit',
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                        this.props.myFetch('addZxSfSpecialEmp', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.refresh();
                                                    obj.btnCallbackFn.form.setFieldsValue(data);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                } else {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: "edit",
                            label: "修改",
                            field: "edit",
                            type: "primary",
                            onClick: (obj) => {
                                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                                this.table.clearSelectedRows();
                            },
                            disabled: (obj) => {
                                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                                if (SelectedRowsData.length !== 1) {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                                {
                                    name: 'diySubmit',
                                    field: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                        obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                        this.props.myFetch('updateZxSfSpecialEmp', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.refresh();
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                } else {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            field: "del",
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxSfSpecialEmp',
                            },
                        },
                    ]}
                    formConfig={[
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail',
                                tooltip: 15,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.checkDateMark === '0') {
                                        color = '';
                                    } else if (rowData.checkDateMark === '1') {
                                        color = 'red';
                                    }
                                    return <div style={{ color: color }}>{data}</div>
                                }
                            }
                        },
                        {
                            table: {
                                title: '单位负责人',
                                dataIndex: 'manager',
                                key: 'manager',
                                fixed: 'left',
                                width: 150,
                                render: (data, rowData) => {
                                    console.log(rowData);
                                    let color = '';
                                    if (rowData.checkDateMark === '0') {
                                        color = '';
                                    } else if (rowData.checkDateMark === '1') {
                                        color = 'red';
                                    }
                                    return <div style={{ color: color }}>{data}</div>
                                }
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                dataIndex: 'creator',
                                key: 'creator',
                                width: 100,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.checkDateMark === '0') {
                                        color = '';
                                    } else if (rowData.checkDateMark === '1') {
                                        color = 'red';
                                    }
                                    return <div style={{ color: color }}>{data}</div>
                                }
                            }
                        },
                        {
                            table: {
                                title: '填报日期',
                                dataIndex: 'createDate',
                                key: 'createDate',
                                width: 100,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.checkDateMark === '0') {
                                        color = '';
                                    } else if (rowData.checkDateMark === '1') {
                                        color = 'red';
                                    }
                                    return <div style={{ color: color }}>{moment(data).format('YYYY-MM-DD')}</div>
                                }

                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.checkDateMark === '0') {
                                        color = '';
                                    } else if (rowData.checkDateMark === '1') {
                                        color = 'red';
                                    }
                                    return <div style={{ color: color }}>{data}</div>
                                }
                            }
                        },
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'zxSfSpecialEmpId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        hide: true,
                                        initialValue: departmentId
                                    },
                                    {
                                        label: '所在项目',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        initialValue: departmentName
                                    },
                                    {
                                        label: '单位负责人',
                                        field: "manager",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '填报人',
                                        field: "creator",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12,
                                        disabled: true,
                                        initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                    },
                                    {
                                        label: '填报日期',
                                        field: "createDate",
                                        type: 'date',
                                        required: true,
                                        placeholder: '请选择',
                                        span: 12,
                                        initialValue: new Date()
                                    },
                                    {
                                        label: '备注',
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                        span: 12,
                                        autoSize: {
                                            minRows: 1,
                                            maxRows: 3
                                        },
                                    },
                                    {
                                        label: '附件',
                                        spanForm: 12,
                                        field: 'fileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 4 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            }
                                        },
                                    }
                                ]
                            }
                        },
                        {
                            name: "diyComponent",
                            field: "TabTwoList",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfSpecialEmpId"))
                            },
                            title: "清单",
                            content: obj => {
                                let params = { ...this.props }
                                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                                let paramsData = {}
                                paramsData.clickName = obj.clickCb.rowInfo.name
                                paramsData.zxSfSpecialEmpId = rowData.zxSfSpecialEmpId ? rowData.zxSfSpecialEmpId : ''
                                paramsData.orgID = rowData.orgID ? rowData.orgID : ''
                                paramsData.orgName = rowData.orgName ? rowData.orgName : ''
                                paramsData.tabIndex = obj.tableFns.getActiveKey()
                                params.paramsData = paramsData
                                return <TabTwoList {...params} />;
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;