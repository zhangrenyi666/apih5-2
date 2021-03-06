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
        width: '70%'
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
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            companyName: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectName : curCompany?.companyName) : curCompany?.projectName,
        }
    }
    render() {
        const { departmentId,companyName } = this.state;
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
                    method={{
                        onClickFunAdd: (obj) => {
                            obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                        },
                        hideFunAddCancel: (obj) => {
                            var index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1"|| obj?.btnCallbackFn?.form?.getFieldValue?.('zxSfSpecialEmpId')) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        hideFunAddSavehide: (obj) => {
                            var index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1"|| obj?.btnCallbackFn?.form?.getFieldValue?.('zxSfSpecialEmpId')) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunAddSave: (obj) => {
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
                        },
                        onClickFunEdit: (obj) => {
                            obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                            this.table.clearSelectedRows();
                        },
                        disabledFunEdit: (obj) => {
                            let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                            if (SelectedRowsData.length !== 1) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        hideFunEditCanel: (obj) => {
                            var index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        hideFunEditSave: (obj) => {
                            var index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunEditSave: (obj) => {
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
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '??????',
                    //         onClick: 'bind:onClickFunAdd',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //                 hide: 'bind:hideFunAddCancel'
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 field: 'diySubmit',
                    //                 label: '??????',
                    //                 hide: 'bind:hideFunAddSavehide',
                    //                 onClick: 'bind:onClickFunAddSave'
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: "edit",
                    //         label: "??????",
                    //         field: "edit",
                    //         type: "primary",
                    //         onClick: 'bind:onClickFunEdit',
                    //         disabled: 'bind:disabledFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //                 hide: 'bind:hideFunEditCanel'
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 field: 'diySubmit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 hide: 'bind:hideFunEditSave',
                    //                 onClick: 'bind:onClickFunEditSave'
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         field: "del",
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZxSfSpecialEmp'
                    //         }
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            //???????????????????????? 
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "SpecialOperationsList"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '????????????',
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
                                title: '???????????????',
                                dataIndex: 'manager',
                                key: 'manager',
                                fixed: 'left',
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
                        {
                            table: {
                                title: '?????????',
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
                                title: '????????????',
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
                                title: '??????',
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
                            title: "????????????",
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
                                        label: '????????????',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        initialValue: companyName
                                    },
                                    {
                                        label: '???????????????',
                                        field: "manager",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12,
                                    },
                                    {
                                        label: '?????????',
                                        field: "creator",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12,
                                        disabled: true,
                                        initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                    },
                                    {
                                        label: '????????????',
                                        field: "createDate",
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 12,
                                        initialValue: new Date()
                                    },
                                    {
                                        label: '??????',
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '?????????',
                                        span: 12,
                                        autoSize: {
                                            minRows: 1,
                                            maxRows: 3
                                        },
                                    },
                                    {
                                        label: '??????',
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
                            title: "??????",
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