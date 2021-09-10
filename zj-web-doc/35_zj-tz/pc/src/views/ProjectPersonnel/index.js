import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-form";
import s from "./style.less";
import moment from 'moment';
import { message as Msg, Modal, Spin, Button } from 'antd';
import { Fab } from "@material-ui/core";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.projectEmployeeId
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
const configMJodal = {

    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            companyName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName : '',
            projectEmployeeId: '',
            employeeNumber: 0,
            visibleSend: false,
            loadingSend: false,
            projectAndEmployeeList: [],
            selectRowsData: [],
            visibleSendDiaoChu: false,
            loadingSendDiaoChu: false,
            visibleDel: false,
            loadingDel: false,
            contHtml: null
        }
    }

    componentDidMount() { }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    handleCancelDiaoChu = () => {
        this.setState({ visibleSendDiaoChu: false });
    }
    componentWillUnmount() {
        this.setState = () => false;
    }
    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { proNameId, companyName, visibleSend, loadingSend, selectRowsData, visibleSendDiaoChu, loadingSendDiaoChu, visibleDel, loadingDel, contHtml } = this.state;

        return (
            <div className={s.root}>
                <h4 style={{ color: 'red' }}>请调整填报时间筛选项查看历史记录</h4>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzProjectEmployeeList',
                        otherParams: {
                            projectId: proNameId,
                            time:new Date().getTime()
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'projectEmployeeId',
                                type: 'string',
                                hide: true,
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
                            isInForm: false,
                            table: {
                                width: 400,
                                tooltip: 30,
                                align: 'center',
                                title: '单位名称',
                                dataIndex: 'registerUnits',
                                key: 'registerUnits',
                                filter: true,
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    this.setState({
                                        projectEmployeeId: obj.rowData.projectEmployeeId
                                    })
                                },
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
                                title: '填报人',
                                dataIndex: 'registerUser',
                                key: 'registerUser',
                                align: 'center',
                                filter: true,
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
                                title: '填报时间',
                                dataIndex: 'registerTime',
                                key: 'registerTime',
                                align: 'center',
                                filter: true,
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY-MM-DD')
                                    }
                                    return ''
                                },
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field:'timeList'
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
                                title: '人数',
                                dataIndex: 'employeeNumber',
                                key: 'employeeNumber',
                                align: 'center',
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '审核状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                align: 'center',
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                placeholder: '请输入',
                                hide: true,
                                optionData: [
                                    {
                                        label: "已审核",
                                        value: "已审核"
                                    },
                                    {
                                        label: "未审核",
                                        value: "未审核"
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            //判断是什么项目
                            if (companyName == '所有项目') {
                                obj.btnCallbackFn.closeDrawer();
                                return obj.btnCallbackFn.msg.warning('请选择右上角项目!');
                            }
                            obj.btnCallbackFn.setActiveKey('0');
                            //删除掉禁用
                            obj.btnCallbackFn.setBtnsDisabled('remove', 'adddiySubmit');
                            this.setState({
                                projectEmployeeId: '',
                                employeeNumber: 0
                            })
                        },
                        editClick: (obj) => {
                            //判断是什么项目
                            if (companyName == '所有项目') {
                                obj.btnCallbackFn.closeDrawer();
                                return obj.btnCallbackFn.msg.warning('请选择右上角项目!');
                            }
                            if (obj.selectedRows[0].auditStatus === '已审核') {
                                Msg.warn('审核中的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                                this.table.clearSelectedRows();
                            } else {
                                this.setState({
                                    projectEmployeeId: obj.selectedRows[0].projectEmployeeId,
                                    employeeNumber: obj.selectedRows[0].employeeNumber,
                                })
                                obj.btnCallbackFn.setActiveKey('0');
                                this.table.clearSelectedRows();
                            }
                        },
                        editSaveClick: (obj) => {
                            obj.btnCallbackFn.fetch('updateZjTzProjectEmployee', obj._formData, ({ data, success, message }) => {
                                if (success) {
                                    this.setState({
                                        projectEmployeeId: data.projectEmployeeId,
                                        employeeNumber: data.employeeNumber,
                                    })
                                    if (this.table) {
                                        this.table.refresh();
                                    }
                                    obj.btnCallbackFn.msg.success(message);
                                    if (obj.btnCallbackFn.getActiveKey() === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                    } else {
                                        obj.btnCallbackFn.setActiveKey('1');
                                    }
                                } else {
                                    obj.btnCallbackFn.msg.error(message);
                                }
                            })
                        },
                        addSaveClick:(obj) => {
                            obj.btnCallbackFn.fetch('addZjTzProjectEmployee', obj._formData, ({ data, success, message }) => {
                                if (success) {
                                    this.setState({
                                        projectEmployeeId: data.projectEmployeeId,
                                        employeeNumber: data.employeeNumber
                                    })
                                    if (this.table) {
                                        this.table.refresh();
                                    }
                                    //提交后禁用保存按钮
                                    obj.btnCallbackFn.form.setFieldsValue({ projectEmployeeId: data.projectEmployeeId });
                                    obj.btnCallbackFn.setBtnsDisabled('add', 'adddiySubmit');
                                    obj.btnCallbackFn.msg.success(message);
                                    obj.btnCallbackFn.setActiveKey('1');
                                } else {
                                    obj.btnCallbackFn.msg.error(message);
                                }
                            })
                        },
                        faBuClick: (obj) => {//审核
                            //置空已选项
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                //判断选择的行有没有已审核
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].auditStatus === '已审核') {
                                        aa.push(obj.selectedRows[i].auditStatus);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已审核的消息不能再次审核！');
                                } else {
                                    //组件:气泡确认框
                                    confirm({
                                        title: "确定审核么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('checkZjTzProjectEmployee', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        cheHuiClick: (obj) => {//撤回
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].auditStatus === '未审核') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未审核的消息不能撤回！');
                                } else {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('recallZjTzProjectEmployee', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }

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
                    //         onClick: "bind:addClick",
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 field: 'adddiySubmit',
                    //                 onClick: "bind:addSaveClick"
                    //             },
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         onClick: "bind:editClick",
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 onClick: "bind:editSaveClick"
                    //             },
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZjTzProjectEmployee'
                    //         }
                    //     },
                    //     {
                    //         name: 'Check',
                    //         type: 'primary',
                    //         label: '审核',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: "bind:faBuClick",
                    //     },
                    //     {
                    //         name: 'Withdraw',
                    //         type: 'primary',
                    //         label: '撤回',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: "bind:cheHuiClick",
                    //     }
                    // ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZjTzProjectEmployeeDetails',
                                            otherParams: {
                                                projectEmployeeId: obj.clickCb.rowData.projectEmployeeId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [
                                    {
                                        type: 'string',
                                        field: 'projectEmployeeId',
                                        hide: true
                                    },
                                    {
                                        type: 'string',
                                        field: "projectId",
                                        initialValue: proNameId,
                                        hide: true
                                    },

                                    {
                                        type: 'string',
                                        label: '填报人',
                                        field: 'registerUser',
                                        initialValue: realName,
                                        addDisabled: true,
                                        editDisabled: true,
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
                                        type: 'date',
                                        label: '填报时间',
                                        field: 'registerTime',
                                        initialValue: () => {
                                            return new Date()
                                        },
                                        addDisabled: true,
                                        editDisabled: true,
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
                                        type: 'string',
                                        label: '填报单位',
                                        field: 'registerUnits',
                                        initialValue: companyName,
                                        addDisabled: true,
                                        editDisabled: true
                                    },
                                    {
                                        type: 'textarea',
                                        label: '备注',
                                        field: 'remarks',
                                    },
                                    {
                                        label: '附件',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        field: 'zjTzFileListProjectEmployee',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '项目人员'
                                            }
                                        },
                                    },
                                    {
                                        field: 'auditStatus',
                                        type: "select",
                                        hide: true,
                                        initialValue: "未审核",
                                    }
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "人员信息",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("projectEmployeeId"))
                            },
                            style: {
                                paddingBottom: "20px"
                            },
                            content: {

                                formConfig: [
                                    {
                                        label: '人数', //表头标题
                                        field: 'employeeNumber',
                                        type: 'number',
                                        disabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 2 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 6 }
                                            }
                                        },
                                        initialValue: () => {
                                            return this.state.employeeNumber
                                        },
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableRYXX",
                                        hide: (obj) => {
                                            return ((obj.tabsActiveKey === "1" && this.state.projectEmployeeId != "") ? false : true)
                                        },
                                        qnnTableConfig: {
                                            fetchConfig: {
                                                apiName: 'getZjTzProjectAndEmployeeList',
                                                otherParams: () => {
                                                    return {
                                                        projectEmployeeId: this.state.projectEmployeeId
                                                    }
                                                },
                                                success: (res) => {
                                                    if (res.success) {
                                                        if (this.table && this.table.btnCallbackFn.form) {
                                                            this.table.btnCallbackFn.form.setFieldsValue({ employeeNumber: res.totalNumber })
                                                        }
                                                    }
                                                }
                                            },
                                            antd: {
                                                rowKey: 'projectAndEmployeeId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1000px'
                                            },
                                            paginationConfig: {
                                                position: "bottom"
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableRYXX = me;
                                            },
                                            drawerShowToggle: (obj) => {
                                                setTimeout(() => {
                                                    let { drawerIsShow } = obj;
                                                    let name = obj?.btnCallbackFn?.qnnForm?.props?.clickCb?.rowInfo?.name;
                                                    let rowData = obj?.btnCallbackFn?.qnnForm?.props?.clickCb?.rowData ? obj?.btnCallbackFn?.qnnForm?.props?.clickCb?.rowData : this.tableRYXX.getSelectedRows()[0];
                                                    if (drawerIsShow && (name === 'detail' || name === 'edit')) {
                                                        let person = rowData?.person || {};
                                                        obj.btnCallbackFn.qnnForm.form.setFieldsValue({ ...person })
                                                    }
                                                }, 0);
                                            },
                                            actionBtnsPosition: 'top',
                                            isShowRowSelect: true,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'projectAndEmployeeId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 80,
                                                        align: 'center',
                                                        title: '序号', //表头标题
                                                        dataIndex: 'no',
                                                        key: "no",
                                                        render: (data, rows, index) => {
                                                            return index + 1;
                                                        }
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '人员姓名',
                                                        dataIndex: 'employeeName',
                                                        onClick: 'detail',
                                                        key: "employeeName",
                                                        align: 'center',
                                                        filter: true
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
                                                        title: "身份证号",
                                                        dataIndex: "idCard",
                                                        key: "idCard",
                                                        align: "center",
                                                        filter: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        hide: true
                                                    }
                                                },

                                            ],
                                            tabs: [
                                                {
                                                    field: "form3",
                                                    name: "qnnForm",
                                                    title: "员工基本情况",
                                                    content: {
                                                        formConfig: [
                                                            {
                                                                type: "string",
                                                                label: "主键ID",
                                                                field: "employeeInfoId", //唯一的字段名 ***必传
                                                                hide: true
                                                            },
                                                            //个人申明: 下述信息已经本人核实无误,信息均真实有效,本人愿意承担由于填写信息不真实所造成的法律责任.
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
                                                                    return projectId
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
                                                                    return projectName
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
                                                                span: 9,
                                                                formItemLayout: {
                                                                    labelCol: {
                                                                        xs: { span: 24 },
                                                                        sm: { span: 11 }
                                                                    },
                                                                    wrapperCol: {
                                                                        xs: { span: 24 },
                                                                        sm: { span: 13 }
                                                                    }
                                                                },
                                                            },
                                                            {
                                                                type: 'component',
                                                                label: '',
                                                                field: 'add',
                                                                span: 3,
                                                                Component: obj => {
                                                                    console.log(obj.Pstate.drawerDetailTitle);
                                                                    return (
                                                                        <div style={{ height: '60px', lineHeight: '56px', marginLeft: '20px' }}><Button type='primary' disabled={obj.Pstate.drawerDetailTitle === '新增'? false:true} onClick={() => {
                                                                            // 总共6个节点需要判断
                                                                            let idCardBal = this.tableRYXX.qnnForm.form.getFieldsValue().idCard;
                                                                            if (idCardBal) {
                                                                                this.props.myFetch('getZjTzEmployeeInfoByIdCard', {
                                                                                    idCard: idCardBal,
                                                                                    projectId: projectId
                                                                                }).then(
                                                                                    ({ data, success, message }) => {
                                                                                        if (success) {

                                                                                            if (this.tableRYXX && this.tableRYXX.qnnForm) {
                                                                                                if (data.type === 1) {
                                                                                                    confirm({
                                                                                                        title: message,
                                                                                                        okText: "确认",
                                                                                                        content: (<p>人才储备库<br />{data.employeeName}{data.idCard}</p>),
                                                                                                        cancelText: "取消",
                                                                                                        onOk: () => {
                                                                                                            let employeeInfoId = data.employeeInfoId;
                                                                                                            this.props.myFetch('importProjectZjTzEmployeeInfo', {
                                                                                                                //项目人员id
                                                                                                                projectEmployeeId: this.state.projectEmployeeId,
                                                                                                                //人员id
                                                                                                                employeeInfoId: employeeInfoId,
                                                                                                                //项目id
                                                                                                                projectId: projectId,
                                                                                                                projectName: projectName
                                                                                                            }).then(
                                                                                                                ({ success, message }) => {
                                                                                                                    if (success) {
                                                                                                                        Msg.success(message);
                                                                                                                        this.table.refresh();
                                                                                                                        this.tableRYXX.closeDrawer();
                                                                                                                    } else {
                                                                                                                        Msg.error(message)
                                                                                                                    }
                                                                                                                }
                                                                                                            );

                                                                                                        }
                                                                                                    });
                                                                                                } else if (data.type === 2) {
                                                                                                    Msg.success(message);
                                                                                                } else {

                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            if (this.tableRYXX && this.tableRYXX.qnnForm) {
                                                                                                if (data.type === 3) {
                                                                                                    Msg.warn(message);
                                                                                                    this.tableRYXX.qnnForm.clearValues(['idCard']);
                                                                                                } else if (data.type === 4) {
                                                                                                    Msg.warn(message);
                                                                                                    this.tableRYXX.qnnForm.clearValues(['idCard']);
                                                                                                } else if (data.type === 5) {
                                                                                                    Msg.warn(message);
                                                                                                    this.tableRYXX.qnnForm.clearValues(['idCard']);
                                                                                                } else {

                                                                                                }

                                                                                                // this.tableRYXX.closeDrawer();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                );
                                                                            } else {
                                                                                Msg.warn('请输入身份证号！')
                                                                            }

                                                                        }}>检查重复</Button></div>
                                                                    );
                                                                },
                                                                // hide: (obj) => {
                                                                //     if (obj.Pstate.drawerDetailTitle === '详情' || obj.Pstate.drawerDetailTitle === '编辑') {
                                                                //         return true
                                                                //     } else {
                                                                //         return false
                                                                //     }

                                                                // }
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
                                                                        // this.tableRYXX.qnnForm.form.setFieldsValue({ abc: 'noEdit' })
                                                                    } else {
                                                                        this.tableRYXX.qnnForm.form.setFieldsValue({ leaderCategoryId: '' })
                                                                        // this.tableRYXX.qnnForm.form.setFieldsValue({ abc: 'canEdit' })
                                                                    }
                                                                    this.tableRYXX.btnCallbackFn.refresh();
                                                                }
                                                            },
                                                            // {
                                                            //     type:'string',
                                                            //     field:'abc',
                                                            //     hide:true,
                                                            //     initialValue:(obj)=>{
                                                            //         if(obj.Pstate.drawerDetailTitle === '编辑' && obj.initialValues.person.employeeTypeId === "10"){
                                                            //             return 'noEdit'
                                                            //         }else {
                                                            //             return 'canEdit'
                                                            //         }
                                                            //     }
                                                            // },
                                                            //新增:领导班子类别
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
                                                                // condition: [
                                                                //     {
                                                                //         regex: {
                                                                //             abc: 'canEdit'
                                                                //         },
                                                                //         action:'disabled',
                                                                //     }
                                                                // ],
                                                                fetchConfig: {
                                                                    apiName: "getBaseCodeSelect",
                                                                    otherParams: {
                                                                        itemId: 'lingDaoBanZiLeiBie'
                                                                    }
                                                                },
                                                                dependencies: ['employeeTypeId'],
                                                                hide: (obj) => {
                                                                    var val = obj.form.getFieldsValue()?.employeeTypeId || obj.clickCb?.rowData?.employeeTypeId || obj.clickCb?.selectedRows[0]?.employeeTypeId;
                                                                    // if(val == null){
                                                                    //     return 
                                                                    // }
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
                                                                    var val = obj.form.getFieldsValue()?.employeeTypeId || obj.clickCb?.rowData?.employeeTypeId || obj.clickCb?.selectedRows[0]?.employeeTypeId;
                                                                    // if(val == null){
                                                                    //     return 
                                                                    // }
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
                                                                        this.tableRYXX.qnnForm.form.setFieldsValue({ sentUnit: '' })
                                                                    }
                                                                    this.tableRYXX.btnCallbackFn.refresh();
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
                                                                    var val = obj.form.getFieldsValue()?.laborTypeId || obj.clickCb?.rowData?.laborTypeId || obj.clickCb?.selectedRows[0]?.laborTypeId;
                                                                    // if(val == null){
                                                                    //     return 
                                                                    // }
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
                                                                    var val = obj.form.getFieldsValue()?.laborTypeId || obj.clickCb?.rowData?.laborTypeId || obj.clickCb?.selectedRows[0]?.laborTypeId;
                                                                    // if(val == null){
                                                                    //     return 
                                                                    // }
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
                                                            //新增: 政治面貌
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
                                                                        this.tableRYXX.qnnForm.form.setFieldsValue({ attendPartyTime: '' })
                                                                    }
                                                                    this.tableRYXX.btnCallbackFn.refresh();
                                                                }
                                                            },
                                                            // {
                                                            //     type:'string',
                                                            //     field:'abcd',
                                                            //     hide:true,
                                                            //     initialValue:(obj)=>{
                                                            //         if(obj.Pstate.drawerDetailTitle === '编辑' && (obj.initialValues.person.politicalId === "2" || obj.initialValues.person.politicalId === "5")){
                                                            //             return 'noEdit'
                                                            //         }else {
                                                            //             return 'canEdit'
                                                            //         }
                                                            //     }
                                                            // },
                                                            //新增: 参加党派时间(当政治面貌为中共党员:2或其他民主党派:5时为必填项)
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
                                                                    var val = obj.form.getFieldsValue()?.politicalId || obj.clickCb?.rowData?.politicalId || obj.clickCb?.selectedRows[0]?.politicalId;
                                                                    // if(val == null){
                                                                    //     return 
                                                                    // }
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
                                                                dependencies: ['politicalId'],
                                                                hide: (obj) => {
                                                                    var val = obj.form.getFieldsValue()?.politicalId || obj.clickCb?.rowData?.politicalId || obj.clickCb?.selectedRows[0]?.politicalId;
                                                                    // if(val == null){
                                                                    //     return
                                                                    // }
                                                                    if (val === '2' || val === '5') {
                                                                        return true;
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                }
                                                            },
                                                            //新增:合同性质
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
                                                    field: "form4",
                                                    name: "qnnForm",
                                                    title: "其他信息",
                                                    content: {
                                                        formConfig: [
                                                            //职(执)业资格列表↓(从最先获得的执业资格开始填写)
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
                                                            //政治经历列表↓
                                                            // {
                                                            //     type: 'component',
                                                            //     field: 'diyZZ',
                                                            //     Component: obj => {
                                                            //         return (
                                                            //             <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                            //                 <strong>政治经历列表↓</strong>
                                                            //             </div>
                                                            //         );
                                                            //     }
                                                            // },
                                                            // {
                                                            //     type: 'qnnTable',
                                                            //     field: "politicsList",
                                                            //     incToForm: true,
                                                            //     qnnTableConfig: {
                                                            //         actionBtnsPosition: "top",
                                                            //         antd: {
                                                            //             rowKey: 'politicsId',
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
                                                            //                     field: 'politicsId',
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
                                                            //                     title: "政治面貌",
                                                            //                     dataIndex: 'politicsStatus',
                                                            //                     Key: 'politicsStatus',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'string',
                                                            //                     field: "politicsStatus",
                                                            //                     label: "政治面貌",
                                                            //                     placeholder: '请输入',
                                                            //                 }
                                                            //             },
                                                            //             {
                                                            //                 table: {
                                                            //                     title: "参加党派日期",
                                                            //                     dataIndex: 'joinPartTime',
                                                            //                     Key: 'joinPartTime',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'date',
                                                            //                     field: 'joinPartTime',
                                                            //                     label: "参加党派日期",
                                                            //                     placeholder: '请输入',
                                                            //                 }
                                                            //             },
                                                            //             {
                                                            //                 table: {
                                                            //                     title: "参加党派时所在单位",
                                                            //                     dataIndex: 'joinPartUnit',
                                                            //                     Key: 'joinPartUnit',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'string',
                                                            //                     field: "joinPartUnit",
                                                            //                     label: "参加党派时所在单位",
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
                                                            //学历学位列表↓(从高中开始填写)
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
                                                            //                     tdEdit: true
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
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'add',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增人员',
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
                                                            // isValidate: false,
                                                            fetchConfig: {
                                                                apiName: 'addZjTzEmployeeInfo',
                                                                otherParams: () => {
                                                                    return {
                                                                        projectEmployeeId: this.state.projectEmployeeId,
                                                                        projectId: this.state.proNameId
                                                                    }
                                                                }
                                                            },
                                                            onClick: (obj) => {
                                                                if (this.table) {
                                                                    this.table.refresh();
                                                                }
                                                            }
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'edit',
                                                    type: 'primary',
                                                    label: '修改',
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
                                                                apiName: 'updateZjTzEmployeeInfo',
                                                            },
                                                            onClick: (obj) => {
                                                                if (this.table) {
                                                                    this.table.refresh();
                                                                }
                                                                // 置空已选项
                                                                // this.table.clearSelectedRows();
                                                            },
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'Reference',
                                                    type: 'primary',
                                                    label: '调入',
                                                    onClick: (obj) => {
                                                        this.setState({
                                                            visibleSend: true,
                                                            projectAndEmployeeList: obj.state.data
                                                        });
                                                    },
                                                },
                                                {
                                                    name: 'Reference',
                                                    type: 'primary',
                                                    label: '调出',
                                                    "disabled": "bind:_actionBtnNoSelected",
                                                    onClick: (obj) => {
                                                        this.setState({
                                                            visibleSendDiaoChu: true,
                                                            selectRowsData: obj.selectedRows
                                                        });
                                                    },
                                                },
                                                {
                                                    name: 'Diudel',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '离职',
                                                    "disabled": "bind:_actionBtnNoSelected",
                                                    onClick: (obj) => {
                                                        if (obj.selectedRows.length > 0) {
                                                            this.setState({
                                                                visibleDel: true,
                                                                contHtml: obj.selectedRows
                                                            })
                                                        }
                                                    }
                                                },

                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
                <Modal
                    visible={visibleDel}
                    title="是否确认离职以下人员？"
                    footer={[
                        <Button key="back" onClick={() => {
                            this.setState({
                                visibleDel: false
                            })
                        }}>
                            取消
                        </Button>,
                        <Button key="submit" type="primary" loading={loadingDel} onClick={() => {
                            this.setState({
                                loadingDel: true
                            })
                            this.props.myFetch('leaveOfficeZjTzEmployeeInfoList', contHtml).then(
                                ({ success, message, data }) => {
                                    if (success) {
                                        this.setState({
                                            loadingDel: false,
                                            visibleDel: false
                                        })
                                        Msg.success(message);
                                        this.table.refresh();
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );

                        }}>
                            确定
                        </Button>,
                    ]}
                >
                    <div>{
                        contHtml ? contHtml.map((item) => {
                            return <p>{item.employeeName}&nbsp;&nbsp;&nbsp;{item.idCard}<br /></p>
                        })
                            : null}</div>
                </Modal>
                <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="选择人员"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '600px' }}
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
                            wrappedComponentRef={(qnnForm) => this.qnnFormModal = qnnForm}
                            formConfig={[
                                {
                                    field: "projectEmployeeId",
                                    hide: true,
                                    initialValue: this.state.projectEmployeeId,
                                },
                                {
                                    field: "projectId",
                                    hide: true,
                                    initialValue: this.state.proNameId,
                                },

                                {
                                    label: '添加人员',
                                    field: 'projectAndEmployeeList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        fetchConfig: {
                                            apiName: 'getZjTzEmployeeInfoListSelectPerson',
                                            params: {
                                                zjTzEmployeeInfoList: this.state.projectAndEmployeeList
                                            }
                                        },
                                        k: {
                                            value: 'employeeInfoId',
                                            label: 'employeeName',
                                        },
                                        help: true,
                                        search: true,
                                        searchPlaceholder: '请输入身份证号码',
                                        searchApi: 'getZjTzEmployeeInfoListSelectPerson',  //搜索时调用的api  [string]
                                        searchParamsKey: 'idCard',
                                        searchOtherParams: { pageSize: 999, limit: 10, page: 1 }//搜索时的其他参数  [object]
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'no',
                                    label: '取消',
                                    // isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false,
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    field: 'no',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.props.myFetch('importProjectZjTzEmployeeInfoList', obj.values.projectAndEmployeeList.map((item) => {
                                            item.projectEmployeeId = obj.values.projectEmployeeId;
                                            item.projectId = projectId;
                                            item.projectName = projectName;
                                            return item
                                        })).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.warn(message);
                                                    // 统计人数
                                                    // let s = obj.values.projectAndEmployeeList
                                                    // let Persons = []
                                                    // let projectAndEmployeeList = this.state.projectAndEmployeeList
                                                    // for (let i = 0; i < s.length; i++) {
                                                    //     Persons.push(s[i])
                                                    // }
                                                    // for (let i = 0; i < projectAndEmployeeList.length; i++) {
                                                    //     Persons.push(projectAndEmployeeList[i])
                                                    // }
                                                    // this.setState({
                                                    //     loadingSend: true
                                                    // })
                                                    // this.props.myFetch('recallAddZjTzProjectEmployee', {
                                                    //     projectId: this.state.proNameId,
                                                    //     projectEmployeeId: this.state.projectEmployeeId,
                                                    //     projectAndEmployeeList: Persons
                                                    // }).then(
                                                    //     ({ success, message }) => {
                                                    //         if (success) {
                                                    //             Msg.success(message);
                                                    //             this.table.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        // loadingSend: false,
                                                    })
                                                    //         } else {
                                                    //             obj.form.setFieldsValue({ projectAndEmployeeList: s })
                                                    //             Msg.error(message)
                                                    //             this.setState({
                                                    //                 loadingSend: false,
                                                    //             })
                                                    //         }
                                                    //     }
                                                    // );

                                                } else {
                                                    let aa = data.map((item) => {
                                                        return item.employeeName + item.idCard + '  '
                                                    })
                                                    confirm({
                                                        title: message,
                                                        okText: "确认",
                                                        content: (<p>{aa}</p>),
                                                        cancelText: "取消",
                                                        onOk: () => {
                                                            // this.qnnFormModal.form.setFieldsValue({
                                                            //     projectAndEmployeeList: []
                                                            // });
                                                        }
                                                    });
                                                }
                                            }
                                        );

                                    }
                                }
                            ]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
                <Modal
                    width='800px'
                    style={{ top: '0' }}
                    title="调出"
                    visible={visibleSendDiaoChu}
                    footer={null}
                    onCancel={this.handleCancelDiaoChu}
                    bodyStyle={{ width: '800px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelDiaoChu}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendDiaoChu}>


                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    type: 'qnnTable',
                                    field: 'zxList',
                                    incToForm: true,
                                    initialValue: this.state.selectRowsData,
                                    qnnTableConfig: {
                                        antd: {
                                            rowKey: 'employeeInfoId',
                                            size: 'small'
                                        },
                                        ...configMJodal,
                                        tableTdEdit: true,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'employeeInfoId',
                                                    type: 'string',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '姓名',
                                                    width: 100,
                                                    dataIndex: 'employeeName',
                                                    key: 'employeeName'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '身份证号',
                                                    width: 120,
                                                    dataIndex: 'idCard',
                                                    key: 'idCard'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '调出时间',
                                                    dataIndex: 'joinTime',
                                                    key: 'joinTime',
                                                    tdEdit: true,
                                                    format: 'YYYY-MM-DD'
                                                },
                                                form: {
                                                    type: 'date',
                                                    required: true,
                                                    field: 'joinTime',
                                                    placeholder: '请选择'
                                                }
                                            }
                                        ],
                                        actionBtns: []
                                    }
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
                                            visibleSendDiaoChu: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        let arry = obj.values.zxList;
                                        let submitFlag = 'ok';
                                        for (let i = 0; i < arry.length; i++) {
                                            if (arry[i].joinTime) { } else {
                                                submitFlag = 'unOk';
                                                break;
                                            }
                                        }
                                        if (submitFlag === 'ok') {
                                            this.setState({
                                                loadingSendDiaoChu: true
                                            })
                                            this.props.myFetch('batchDeleteUpdateZjTzProjectAndEmployee', obj.values.zxList).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                        this.setState({
                                                            visibleSendDiaoChu: false,
                                                            loadingSendDiaoChu: false
                                                        })
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        } else {
                                            Msg.error('调出时间未填写，不能保存！');

                                        }
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

export default index


