import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import moment from 'moment';
import { message as Msg, Modal, Button, Tooltip } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.employeeInfoId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
    },

    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    componentDidMount() { }
    render() {
        const { projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzEmployeeInfoList'
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'employeeInfoId',
                                type: 'string',
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '5%',
                                // tooltip: 23,
                                align: 'center',
                                title: '??????',
                                dataIndex: 'no',
                                key: 'no',
                                render: (data, rows, index) => {
                                    return index + 1;
                                }
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '20%',
                                // tooltip: 23,
                                title: '??????????????????',
                                align: 'center',
                                dataIndex: 'projectName',
                                Key: 'projectName',
                                filter: true,
                                render:(data)=>{
                                    return <div>
                                        <Tooltip title={data}>
                                            <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                        </Tooltip>
                                    </div>
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '20%',
                                // tooltip: 23,
                                title: '????????????',
                                align: 'center',
                                dataIndex: 'companyName',
                                Key: 'companyName',
                                filter: true,
                                render:(data)=>{
                                    return <div>
                                        <Tooltip title={data}>
                                            <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                        </Tooltip>
                                    </div>
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '20%',
                                // tooltip: 23,
                                title: '????????????????????????',
                                align: 'center',
                                dataIndex: 'personnelUnit',
                                Key: 'personnelUnit',
                                filter: true,
                                render:(data)=>{
                                    return <div>
                                        <Tooltip title={data}>
                                            <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                        </Tooltip>
                                    </div>
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '????????????',
                                align: 'center',
                                dataIndex: 'departmentName',
                                Key: 'departmentName',
                                filter: true,
                                render:(data)=>{
                                    return <div>
                                        <Tooltip title={data}>
                                            <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                        </Tooltip>
                                    </div>
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '?????????????????????',
                                align: 'center',
                                dataIndex: 'joinTime',
                                Key: 'joinTime',
                                filter: true,
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY-MM-DD')
                                    } else {
                                        return ''
                                    }
                                },
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                }
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '??????',
                                align: 'center',
                                dataIndex: 'employeeName',
                                Key: 'employeeName',
                                onClick: 'detail',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                    ]}
                    method={{
                        export: (obj) => {
                            const { myFetch } = obj.props;
                            confirm({
                                title: "?????????????????????????????????????",
                                okText: "??????",
                                cancelText: "??????",
                                onOk: () => {
                                    myFetch('printZjTzEmployeeInfo', obj.selectedRows).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                window.open(data);
                                                Msg.success(message);
                                                this.table.refresh();

                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                        },
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '??????',
                    //         field: 'addCancelBtn',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'addZjTzEmployeeInfo',
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '??????',
                    //         editDisabled: false,
                    //         onClick: "bind:editClick",
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZjTzEmployeeInfo'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZjTzEmployeeInfo',
                    //         },
                    //     },
                    //     {
                    //         name: 'exporttt',
                    //         type: 'primary',
                    //         label: '??????',
                    //         "disabled": "bind:_actionBtnNoSelected",
                    //         "onClick": "bind:export",
                    //     },
                    // ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "??????????????????",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZjTzEmployeeInfoDetails',
                                            otherParams: {
                                                employeeInfoId: obj.clickCb.rowData.employeeInfoId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "??????ID",
                                        field: "employeeInfoId",
                                        hide: true
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyZY',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>????????????: ????????????????????????????????????,?????????????????????,?????????????????????????????????????????????????????????????????????.</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "",
                                        field: "projectId",
                                        initialValue: () => {
                                            return '000000'
                                        },
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "??????????????????",
                                        field: "projectName",
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: () => {
                                            return '???????????????'
                                        },
                                        required: true,
                                        placeholder: "?????????",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 21 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "companyName",
                                        required: true,
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "????????????????????????",
                                        field: "personnelUnit",
                                        required: true,
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "departmentName",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "?????????????????????",
                                        field: "joinTime",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "??????",
                                        field: "employeeName",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "identity",
                                        label: "???????????????",
                                        field: "idCard",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },

                                    {
                                        type: "date",
                                        label: "????????????",
                                        field: "birthDate",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "??????",
                                        field: "nativePlace",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "??????",
                                        field: "sex",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        optionData: [
                                            {
                                                label: "???",
                                                value: "???"
                                            },
                                            {
                                                label: "???",
                                                value: "???"
                                            }
                                        ],
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "??????",
                                        field: "nation",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "??????????????????",
                                        field: "jobTime",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "??????????????????",
                                        field: "enterPriseTime",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "????????????",
                                        field: "marital",
                                        required: true,
                                        placeholder: "?????????",
                                        span: 12,
                                        optionData: [
                                            {
                                                label: "??????",
                                                value: "??????"
                                            },
                                            {
                                                label: "??????",
                                                value: "??????"
                                            }
                                        ],
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "health",
                                        required: true,
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "???????????????",
                                        required: true,
                                        field: "registeredResidence",
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "post",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "????????????",
                                        required: true,
                                        field: "employeeTypeId",
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'renCaiLeiBie'
                                            }
                                        },
                                        onChange: (obj) => {
                                            if (obj === "10") {
                                            } else {
                                                this.table.qnnForm.form.setFieldsValue({ leaderCategoryId: '' })
                                            }
                                            this.table.btnCallbackFn.refresh();
                                        }
                                    },
                                    {
                                        type: "select",
                                        label: "??????????????????",
                                        field: "leaderCategoryId",
                                        placeholder: "?????????",
                                        span: 12,
                                        disabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'lingDaoBanZiLeiBie'
                                            }
                                        },
                                        dependencies: ['employeeTypeId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return false
                                            }
                                            var val = obj.form.getFieldsValue()?.employeeTypeId || obj.clickCb?.rowData?.employeeTypeId || obj.clickCb?.selectedRows[0]?.employeeTypeId;
                                            if (val === '10') {
                                                return true
                                            } else {
                                                return false
                                            }
                                        }
                                    },
                                    {
                                        type: "select",
                                        label: "??????????????????",
                                        field: "leaderCategoryId",
                                        placeholder: "?????????",
                                        span: 12,
                                        required: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'lingDaoBanZiLeiBie'
                                            }
                                        },
                                        dependencies: ['employeeTypeId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return true
                                            }
                                            var val = obj.form.getFieldsValue()?.employeeTypeId || obj.clickCb?.rowData?.employeeTypeId || obj.clickCb?.selectedRows[0]?.employeeTypeId;
                                            if (val === '10') {
                                                return false
                                            } else {
                                                return true;
                                            }
                                        }
                                    },
                                    {
                                        type: "select",
                                        label: "????????????",
                                        field: "laborTypeId",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }, optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'yongGongLeiXing'
                                            }
                                        },
                                        onChange: (obj) => {
                                            if (obj === "3") {

                                            } else {
                                                this.table.qnnForm.form.setFieldsValue({ sentUnit: '' })

                                            }
                                            this.table.btnCallbackFn.refresh();
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "sentUnit",
                                        placeholder: "?????????",
                                        span: 12,
                                        disabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        dependencies: ['laborTypeId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return false
                                            }
                                            var val = obj.form.getFieldsValue()?.laborTypeId || obj.clickCb?.rowData?.laborTypeId || obj.clickCb?.selectedRows[0]?.laborTypeId;
                                            if (val === '3') {
                                                return true
                                            } else {
                                                return false;
                                            }
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "sentUnit",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        dependencies: ['laborTypeId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return true
                                            }
                                            var val = obj.form.getFieldsValue()?.laborTypeId || obj.clickCb?.rowData?.laborTypeId || obj.clickCb?.selectedRows[0]?.laborTypeId;
                                            if (val === '3') {
                                                return false
                                            } else {
                                                return true;
                                            }
                                        }
                                    },
                                    {
                                        type: "phone",
                                        label: "????????????",
                                        field: "phone",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "email",
                                        label: "????????????",
                                        field: "mailBox",
                                        required: true,
                                        placeholder: "?????????",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "????????????",
                                        field: "politicalId",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }, optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'zhengZhiMianMao'
                                            }
                                        },
                                        onChange: (obj) => {
                                            if (obj === '2' || obj === '5') {

                                            } else {
                                                this.table.qnnForm.form.setFieldsValue({ attendPartyTime: '' })
                                            }
                                            this.table.btnCallbackFn.refresh();
                                        }
                                    },
                                    {
                                        type: "date",
                                        label: "??????????????????",
                                        field: "attendPartyTime",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        dependencies: ['politicalId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return true
                                            }
                                            var val = obj.form.getFieldsValue()?.politicalId || obj.clickCb?.rowData?.politicalId || obj.clickCb?.selectedRows[0]?.politicalId;
                                            if (val === '2' || val === '5') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }
                                    },
                                    {
                                        type: "date",
                                        label: "??????????????????",
                                        field: "attendPartyTime",
                                        placeholder: "?????????",
                                        disabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        dependencies: ['politicalId'],
                                        hide: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === "??????") {
                                                return false
                                            }
                                            var val = obj.form.getFieldsValue()?.politicalId || obj.clickCb?.rowData?.politicalId || obj.clickCb?.selectedRows[0]?.politicalId;
                                            if (val === '2' || val === '5') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        }
                                    },
                                    {
                                        type: "select",
                                        label: "????????????",
                                        field: "contractCharacterId",
                                        placeholder: "?????????",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }, optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'heTongXingZhi'
                                            }
                                        },
                                    },
                                    {
                                        label: '??????',
                                        field: 'zjTzFileList',
                                        type: 'files',
                                        showDownloadIcon: true,//????????????????????????
                                        onPreview: "bind:_docFilesByOfficeUrl",//365??????

                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '??????'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    },
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        type: 'component',
                                        field: 'diyZY',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>???(???)??????????????????(??????????????????????????????????????????)</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: "qualificationList",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'qualificationId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: true,
                                            wrappedComponentRef: (me) => this.Table = me,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'string',
                                                        field: 'qualificationId',
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '??????', //????????????
                                                        dataIndex: 'no', //?????????????????????
                                                        key: 'no',//???????????????key,
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "???(???)???????????????",
                                                        dataIndex: 'qualificationName',
                                                        Key: 'qualificationName',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "qualificationName",
                                                        label: "???(???)???????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'major',
                                                        Key: 'major',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "major",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'getTime',
                                                        Key: 'getTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "getTime",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "?????????",
                                                        dataIndex: 'certificateNumber',
                                                        Key: 'certificateNumber',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "certificateNumber",
                                                        label: "?????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'registeredUnit',
                                                        Key: 'registeredUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "registeredUnit",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'issuedUnit',
                                                        Key: 'issuedUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "issuedUnit",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ]
                                        }
                                    },
                                    //?????????????????????(????????????????????????????????????)
                                    {
                                        type: 'component',
                                        field: 'diyZC',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>?????????????????????(????????????????????????????????????)</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: "professionalList",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'professionalId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: true,
                                            wrappedComponentRef: (me) => this.Table = me,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'string',
                                                        field: 'professionalId',
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '??????', //????????????
                                                        dataIndex: 'no', //?????????????????????
                                                        key: 'no',//???????????????key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "????????????????????????",
                                                        dataIndex: 'professionalNameId',
                                                        Key: 'professionalNameId',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "professionalNameId",
                                                        label: "????????????????????????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhuanYeJiShuZiGeMingCheng'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "????????????",
                                                        dataIndex: 'professionalLevelId',
                                                        Key: 'professionalLevelId',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: 'professionalLevelId',
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'ZhiChengDengJi'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????????????????",
                                                        dataIndex: 'getTime',
                                                        Key: 'getTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "getTime",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "??????????????????",
                                                        dataIndex: 'getWayId',
                                                        Key: 'getWayId',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "getWayId",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'quDeZiGeTuJing'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????????????????",
                                                        dataIndex: 'appUnit',
                                                        Key: 'appUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "appUnit",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "??????????????????????????????",
                                                        dataIndex: 'engageProfessionalName',
                                                        Key: 'engageProfessionalName',
                                                        width: 250,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "engageProfessionalName",
                                                        label: "??????????????????????????????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'pinRenZhuanYeJiShuZhiWuMingCheng'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????????????????",
                                                        dataIndex: 'engageStartTime',
                                                        Key: 'engageStartTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "engageStartTime",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????????????????",
                                                        dataIndex: 'engageEndTime',
                                                        Key: 'engageEndTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "engageEndTime",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????????????????",
                                                        dataIndex: 'professionalFlag',
                                                        Key: 'professionalFlag',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "professionalFlag",
                                                        label: "????????????????????????",
                                                        placeholder: '?????????',
                                                        optionData: [
                                                            {
                                                                label: "???",
                                                                value: "???"
                                                            },
                                                            {
                                                                label: "???",
                                                                value: "???"
                                                            }
                                                        ],
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ]
                                        }
                                    },
                                    //?????????????????????(?????????????????????????????????)
                                    {
                                        type: 'component',
                                        field: 'diyGZ',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>?????????????????????(?????????????????????????????????)</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: "workList",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'workId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: true,
                                            wrappedComponentRef: (me) => this.Table = me,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'string',
                                                        field: 'workId',
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '??????', //????????????
                                                        dataIndex: 'no', //?????????????????????
                                                        key: 'no',//???????????????key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'startTime',
                                                        Key: 'startTime',
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "startTime",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'endTime',
                                                        Key: 'endTime',
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: 'endTime',
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'workUnit',
                                                        Key: 'workUnit',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "workUnit",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????(??????)",
                                                        dataIndex: 'duty',
                                                        Key: 'duty',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "duty",
                                                        label: "??????(??????)",
                                                        placeholder: '?????????',
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ]
                                        }
                                    },

                                    {
                                        type: 'component',
                                        field: 'diyXL',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>?????????????????????(?????????????????????)</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: "educationList",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'educationId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: true,
                                            wrappedComponentRef: (me) => this.Table = me,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'string',
                                                        field: 'educationId',
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '??????', //????????????
                                                        dataIndex: 'no', //?????????????????????
                                                        key: 'no',//???????????????key 
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'stratTime',
                                                        Key: 'stratTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "stratTime",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'graduateTime',
                                                        Key: 'graduateTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: 'graduateTime',
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'graduatSchool',
                                                        Key: 'graduatSchool',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "graduatSchool",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "????????????",
                                                        dataIndex: 'studyForm',
                                                        Key: 'studyForm',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "studyForm",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'xueXiXingShi'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'majorIn',
                                                        Key: 'majorIn',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "majorIn",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "??????",
                                                        dataIndex: 'education',
                                                        Key: 'education',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "education",
                                                        label: "??????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'xueLi'
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "??????",
                                                        dataIndex: 'degree',
                                                        Key: 'degree',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "degree",
                                                        label: "??????",
                                                        placeholder: '?????????',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'xueWei'
                                                            }
                                                        },
                                                    }
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ]
                                        }
                                    },
                                    //?????????????????????(????????????????????????????????????)
                                    {
                                        type: 'component',
                                        field: 'diyJL',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>?????????????????????(????????????????????????????????????)</strong>
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: "awardList",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'awardId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            limit: 999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            firstRowIsSearch: false,
                                            isShowRowSelect: true,
                                            wrappedComponentRef: (me) => this.Table = me,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'string',
                                                        field: 'awardId',
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '??????', //????????????
                                                        dataIndex: 'no', //?????????????????????
                                                        key: 'no',//???????????????key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'awardTime',
                                                        Key: 'awardTime',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        field: "awardTime",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'awardRank',
                                                        Key: 'awardRank',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: 'awardRank',
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'awardName',
                                                        Key: 'awardName',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardName",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "????????????",
                                                        dataIndex: 'awardReason',
                                                        Key: 'awardReason',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardReason",
                                                        label: "????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "??????????????????",
                                                        dataIndex: 'awardAppUnit',
                                                        Key: 'awardAppUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardAppUnit",
                                                        label: "??????????????????",
                                                        placeholder: '?????????',
                                                    }
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ]
                                        }
                                    },
                                    //?????????????????????
                                    // {
                                    //     type: 'component',
                                    //     field: 'diyYY',
                                    //     Component: obj => {
                                    //         return (
                                    //             <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                    //                 <strong>?????????????????????</strong>
                                    //             </div>
                                    //         );
                                    //     }
                                    // },
                                    // {
                                    //     type: 'qnnTable',
                                    //     field: "languageList",
                                    //     incToForm: true,
                                    //     qnnTableConfig: {
                                    //         actionBtnsPosition: "top",
                                    //         antd: {
                                    //             rowKey: 'languageId',
                                    //             size: 'small'
                                    //         },
                                    //         drawerConfig: {
                                    //             width: '1000px'
                                    //         },
                                    //         limit: 999,
                                    //         curPage: 1,
                                    //         paginationConfig: false,
                                    //         firstRowIsSearch: false,
                                    //         isShowRowSelect: true,
                                    //         wrappedComponentRef: (me) => this.Table = me,
                                    //         formConfig: [
                                    //             {
                                    //                 isInTable: false,
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: 'languageId',
                                    //                 }
                                    //             },
                                    //             {
                                    //                 isInForm: false,
                                    //                 table: {
                                    //                     width: 80,
                                    //                     align: 'center',
                                    //                     title: '??????', //????????????
                                    //                     dataIndex: 'no', //?????????????????????
                                    //                     key: 'no',//???????????????key   
                                    //                     render: (data, rows, index) => {
                                    //                         return index + 1;
                                    //                     }
                                    //                 },
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "??????",
                                    //                     dataIndex: 'language',
                                    //                     Key: 'language',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: "language",
                                    //                     label: "??????",
                                    //                     placeholder: '?????????',
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "??????????????????",
                                    //                     dataIndex: 'languageProficiency',
                                    //                     Key: 'languageProficiency',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: 'languageProficiency',
                                    //                     label: "??????????????????",
                                    //                     placeholder: '?????????',
                                    //                     isCanSubmit:true
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "????????????",
                                    //                     dataIndex: 'certificateName',
                                    //                     Key: 'certificateName',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: "certificateName",
                                    //                     label: "????????????",
                                    //                     placeholder: '?????????',
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "????????????",
                                    //                     dataIndex: 'getTime',
                                    //                     Key: 'getTime',
                                    //                     tdEdit: true,
                                    //                     render: (data) => {
                                    //                         if (data) {
                                    //                             return moment(data).format('YYYY-MM-DD')
                                    //                         } else {
                                    //                             return ''
                                    //                         }
                                    //                     }
                                    //                 },
                                    //                 form: {
                                    //                     type: 'date',
                                    //                     field: "getTime",
                                    //                     label: "????????????",
                                    //                     placeholder: '?????????',
                                    //                 }
                                    //             },
                                    //         ],
                                    //         actionBtns: [
                                    //             {
                                    //                 name: 'addRow',
                                    //                 icon: 'plus',
                                    //                 type: 'primary',
                                    //                 label: '??????',
                                    //             },
                                    //             {
                                    //                 name: 'del',
                                    //                 icon: 'delete',
                                    //                 type: 'danger',
                                    //                 label: '??????',
                                    //             }
                                    //         ]
                                    //     }
                                    // },
                                ]
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;