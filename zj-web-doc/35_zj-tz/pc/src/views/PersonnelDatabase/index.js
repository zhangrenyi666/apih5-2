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
                                title: '序号',
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
                                title: '所属项目名称',
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
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '20%',
                                // tooltip: 23,
                                title: '单位名称',
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
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '20%',
                                // tooltip: 23,
                                title: '人事关系所在单位',
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
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '部门名称',
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
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '调入本项目时间',
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
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: '10%',
                                // tooltip: 23,
                                title: '姓名',
                                align: 'center',
                                dataIndex: 'employeeName',
                                Key: 'employeeName',
                                onClick: 'detail',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                    ]}
                    method={{
                        export: (obj) => {
                            const { myFetch } = obj.props;
                            confirm({
                                title: "确定导出选择的人员数据么?",
                                okText: "确认",
                                cancelText: "取消",
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
                    //         label: '新增',
                    //         field: 'addCancelBtn',
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
                    //                     apiName: 'addZjTzEmployeeInfo',
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         editDisabled: false,
                    //         onClick: "bind:editClick",
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
                    //                     apiName: 'updateZjTzEmployeeInfo'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '离职',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZjTzEmployeeInfo',
                    //         },
                    //     },
                    //     {
                    //         name: 'exporttt',
                    //         type: 'primary',
                    //         label: '导出',
                    //         "disabled": "bind:_actionBtnNoSelected",
                    //         "onClick": "bind:export",
                    //     },
                    // ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "员工基本情况",
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
                                        label: "主键ID",
                                        field: "employeeInfoId",
                                        hide: true
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyZY',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>个人申明: 下述信息已经本人核实无误,信息均真实有效,本人愿意承担由于填写信息不真实所造成的法律责任.</strong>
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
                                        label: "所属项目名称",
                                        field: "projectName",
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: () => {
                                            return '人才储备库'
                                        },
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "单位名称",
                                        field: "companyName",
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "人事关系所在单位",
                                        field: "personnelUnit",
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "部门名称",
                                        field: "departmentName",
                                        placeholder: "请输入",
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
                                        label: "调入本项目时间",
                                        field: "joinTime",
                                        placeholder: "请输入",
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
                                        label: "姓名",
                                        field: "employeeName",
                                        placeholder: "请输入",
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
                                        label: "身份证号码",
                                        field: "idCard",
                                        placeholder: "请输入",
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
                                        label: "出生日期",
                                        field: "birthDate",
                                        placeholder: "请输入",
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
                                        label: "籍贯",
                                        field: "nativePlace",
                                        placeholder: "请输入",
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
                                        label: "性别",
                                        field: "sex",
                                        placeholder: "请选择",
                                        required: true,
                                        span: 12,
                                        optionData: [
                                            {
                                                label: "男",
                                                value: "男"
                                            },
                                            {
                                                label: "女",
                                                value: "女"
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
                                        label: "民族",
                                        field: "nation",
                                        placeholder: "请输入",
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
                                        label: "参加工作时间",
                                        field: "jobTime",
                                        placeholder: "请输入",
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
                                        label: "到本企业时间",
                                        field: "enterPriseTime",
                                        placeholder: "请输入",
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
                                        label: "婚姻状况",
                                        field: "marital",
                                        required: true,
                                        placeholder: "请选择",
                                        span: 12,
                                        optionData: [
                                            {
                                                label: "已婚",
                                                value: "已婚"
                                            },
                                            {
                                                label: "未婚",
                                                value: "未婚"
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
                                        label: "健康状况",
                                        field: "health",
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "户口所在地",
                                        required: true,
                                        field: "registeredResidence",
                                        placeholder: "请输入",
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
                                        label: "岗位职务",
                                        field: "post",
                                        placeholder: "请输入",
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
                                        label: "人才类别",
                                        required: true,
                                        field: "employeeTypeId",
                                        placeholder: "请选择",
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
                                        label: "领导班子类别",
                                        field: "leaderCategoryId",
                                        placeholder: "请选择",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "领导班子类别",
                                        field: "leaderCategoryId",
                                        placeholder: "请选择",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "用工类型",
                                        field: "laborTypeId",
                                        placeholder: "请选择",
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
                                        label: "派往单位",
                                        field: "sentUnit",
                                        placeholder: "请输入",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "派往单位",
                                        field: "sentUnit",
                                        placeholder: "请输入",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "移动电话",
                                        field: "phone",
                                        placeholder: "请输入",
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
                                        label: "电子邮箱",
                                        field: "mailBox",
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "政治面貌",
                                        field: "politicalId",
                                        placeholder: "请选择",
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
                                        label: "参加党派时间",
                                        field: "attendPartyTime",
                                        placeholder: "请输入",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "参加党派时间",
                                        field: "attendPartyTime",
                                        placeholder: "请输入",
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
                                            if (obj.Pstate.drawerDetailTitle === "新增") {
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
                                        label: "合同性质",
                                        field: "contractCharacterId",
                                        placeholder: "请选择",
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
                                        label: '附件',
                                        field: 'zjTzFileList',
                                        type: 'files',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '人员'
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
                            title: "其他信息",
                            content: {
                                formConfig: [
                                    {
                                        type: 'component',
                                        field: 'diyZY',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>职(执)业资格列表↓(从最先获得的执业资格开始填写)</strong>
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
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no', //表格里面的字段
                                                        key: 'no',//表格的唯一key,
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "职(执)业资格名称",
                                                        dataIndex: 'qualificationName',
                                                        Key: 'qualificationName',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "qualificationName",
                                                        label: "职(执)业资格名称",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "所属专业",
                                                        dataIndex: 'major',
                                                        Key: 'major',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "major",
                                                        label: "所属专业",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "获取时间",
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
                                                        label: "获取时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "证书号",
                                                        dataIndex: 'certificateNumber',
                                                        Key: 'certificateNumber',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "certificateNumber",
                                                        label: "证书号",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "注册单位",
                                                        dataIndex: 'registeredUnit',
                                                        Key: 'registeredUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "registeredUnit",
                                                        label: "注册单位",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "颁发单位",
                                                        dataIndex: 'issuedUnit',
                                                        Key: 'issuedUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "issuedUnit",
                                                        label: "颁发单位",
                                                        placeholder: '请输入',
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ]
                                        }
                                    },
                                    //职称情况列表↓(从最先获得的职称开始填写)
                                    {
                                        type: 'component',
                                        field: 'diyZC',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>职称情况列表↓(从最先获得的职称开始填写)</strong>
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
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no', //表格里面的字段
                                                        key: 'no',//表格的唯一key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "专业技术资格名称",
                                                        dataIndex: 'professionalNameId',
                                                        Key: 'professionalNameId',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "professionalNameId",
                                                        label: "专业技术资格名称",
                                                        placeholder: '请选择',
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
                                                        title: "职称等级",
                                                        dataIndex: 'professionalLevelId',
                                                        Key: 'professionalLevelId',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: 'professionalLevelId',
                                                        label: "职称等级",
                                                        placeholder: '请选择',
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
                                                        title: "取得资格时间",
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
                                                        label: "取得资格时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "取得资格途径",
                                                        dataIndex: 'getWayId',
                                                        Key: 'getWayId',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "getWayId",
                                                        label: "取得资格途径",
                                                        placeholder: '请选择',
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
                                                        title: "资格审批单位",
                                                        dataIndex: 'appUnit',
                                                        Key: 'appUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "appUnit",
                                                        label: "资格审批单位",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "聘任专业技术职务名称",
                                                        dataIndex: 'engageProfessionalName',
                                                        Key: 'engageProfessionalName',
                                                        width: 250,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "engageProfessionalName",
                                                        label: "聘任专业技术职务名称",
                                                        placeholder: '请选择',
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
                                                        title: "聘任起始时间",
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
                                                        label: "聘任起始时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "聘任终止时间",
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
                                                        label: "聘任终止时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "是否具有职业资格",
                                                        dataIndex: 'professionalFlag',
                                                        Key: 'professionalFlag',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "professionalFlag",
                                                        label: "是否具有职业资格",
                                                        placeholder: '请输入',
                                                        optionData: [
                                                            {
                                                                label: "是",
                                                                value: "是"
                                                            },
                                                            {
                                                                label: "否",
                                                                value: "否"
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
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ]
                                        }
                                    },
                                    //工作经历列表↓(从刚参加的工作开始填写)
                                    {
                                        type: 'component',
                                        field: 'diyGZ',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>工作经历列表↓(从刚参加的工作开始填写)</strong>
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
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no', //表格里面的字段
                                                        key: 'no',//表格的唯一key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "起始时间",
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
                                                        label: "起始时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "截止时间",
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
                                                        label: "截止时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "工作单位",
                                                        dataIndex: 'workUnit',
                                                        Key: 'workUnit',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "workUnit",
                                                        label: "工作单位",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "职务(岗位)",
                                                        dataIndex: 'duty',
                                                        Key: 'duty',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "duty",
                                                        label: "职务(岗位)",
                                                        placeholder: '请输入',
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
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
                                                    <strong>学历学位列表↓(从高中开始填写)</strong>
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
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no', //表格里面的字段
                                                        key: 'no',//表格的唯一key 
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "入学时间",
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
                                                        label: "入学时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "毕业时间",
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
                                                        label: "毕业时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "毕业学校",
                                                        dataIndex: 'graduatSchool',
                                                        Key: 'graduatSchool',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "graduatSchool",
                                                        label: "毕业学校",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "学习形式",
                                                        dataIndex: 'studyForm',
                                                        Key: 'studyForm',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "studyForm",
                                                        label: "学习形式",
                                                        placeholder: '请选择',
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
                                                        title: "所学专业",
                                                        dataIndex: 'majorIn',
                                                        Key: 'majorIn',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "majorIn",
                                                        label: "所学专业",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        type: 'select',
                                                        title: "学历",
                                                        dataIndex: 'education',
                                                        Key: 'education',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "education",
                                                        label: "学历",
                                                        placeholder: '请选择',
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
                                                        title: "学位",
                                                        dataIndex: 'degree',
                                                        Key: 'degree',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: "degree",
                                                        label: "学位",
                                                        placeholder: '请选择',
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
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ]
                                        }
                                    },
                                    //奖励情况列表↓(按获得奖励的先后顺序填写)
                                    {
                                        type: 'component',
                                        field: 'diyJL',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    <strong>奖励情况列表↓(按获得奖励的先后顺序填写)</strong>
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
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no', //表格里面的字段
                                                        key: 'no',//表格的唯一key    
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "奖励时间",
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
                                                        label: "奖励时间",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "奖励级别",
                                                        dataIndex: 'awardRank',
                                                        Key: 'awardRank',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: 'awardRank',
                                                        label: "奖励级别",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "奖励名称",
                                                        dataIndex: 'awardName',
                                                        Key: 'awardName',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardName",
                                                        label: "奖励名称",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "奖励原因",
                                                        dataIndex: 'awardReason',
                                                        Key: 'awardReason',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardReason",
                                                        label: "奖励原因",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "奖励批准单位",
                                                        dataIndex: 'awardAppUnit',
                                                        Key: 'awardAppUnit',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: "awardAppUnit",
                                                        label: "奖励批准单位",
                                                        placeholder: '请输入',
                                                    }
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ]
                                        }
                                    },
                                    //语言情况列表↓
                                    // {
                                    //     type: 'component',
                                    //     field: 'diyYY',
                                    //     Component: obj => {
                                    //         return (
                                    //             <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                    //                 <strong>语言情况列表↓</strong>
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
                                    //                     title: '序号', //表头标题
                                    //                     dataIndex: 'no', //表格里面的字段
                                    //                     key: 'no',//表格的唯一key   
                                    //                     render: (data, rows, index) => {
                                    //                         return index + 1;
                                    //                     }
                                    //                 },
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "语种",
                                    //                     dataIndex: 'language',
                                    //                     Key: 'language',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: "language",
                                    //                     label: "语种",
                                    //                     placeholder: '请输入',
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "语言熟练程度",
                                    //                     dataIndex: 'languageProficiency',
                                    //                     Key: 'languageProficiency',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: 'languageProficiency',
                                    //                     label: "语言熟练程度",
                                    //                     placeholder: '请输入',
                                    //                     isCanSubmit:true
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "证书名称",
                                    //                     dataIndex: 'certificateName',
                                    //                     Key: 'certificateName',
                                    //                     tdEdit: true
                                    //                 },
                                    //                 form: {
                                    //                     type: 'string',
                                    //                     field: "certificateName",
                                    //                     label: "证书名称",
                                    //                     placeholder: '请输入',
                                    //                 }
                                    //             },
                                    //             {
                                    //                 table: {
                                    //                     title: "获证日期",
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
                                    //                     label: "获证日期",
                                    //                     placeholder: '请输入',
                                    //                 }
                                    //             },
                                    //         ],
                                    //         actionBtns: [
                                    //             {
                                    //                 name: 'addRow',
                                    //                 icon: 'plus',
                                    //                 type: 'primary',
                                    //                 label: '新增',
                                    //             },
                                    //             {
                                    //                 name: 'del',
                                    //                 icon: 'delete',
                                    //                 type: 'danger',
                                    //                 label: '删除',
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