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
                <h4 style={{ color: 'red' }}>????????????????????????????????????????????????</h4>
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
                                title: '??????', //????????????
                                dataIndex: 'no', //?????????????????????
                                key: 'no',//???????????????key    
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
                                title: '????????????',
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
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '?????????',
                                dataIndex: 'registerUser',
                                key: 'registerUser',
                                align: 'center',
                                filter: true,
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
                                title: '????????????',
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
                                placeholder: '?????????',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '??????',
                                dataIndex: 'employeeNumber',
                                key: 'employeeNumber',
                                align: 'center',
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                align: 'center',
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                placeholder: '?????????',
                                hide: true,
                                optionData: [
                                    {
                                        label: "?????????",
                                        value: "?????????"
                                    },
                                    {
                                        label: "?????????",
                                        value: "?????????"
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            //?????????????????????
                            if (companyName == '????????????') {
                                obj.btnCallbackFn.closeDrawer();
                                return obj.btnCallbackFn.msg.warning('????????????????????????!');
                            }
                            obj.btnCallbackFn.setActiveKey('0');
                            //???????????????
                            obj.btnCallbackFn.setBtnsDisabled('remove', 'adddiySubmit');
                            this.setState({
                                projectEmployeeId: '',
                                employeeNumber: 0
                            })
                        },
                        editClick: (obj) => {
                            //?????????????????????
                            if (companyName == '????????????') {
                                obj.btnCallbackFn.closeDrawer();
                                return obj.btnCallbackFn.msg.warning('????????????????????????!');
                            }
                            if (obj.selectedRows[0].auditStatus === '?????????') {
                                Msg.warn('?????????????????????????????????')
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
                                    //???????????????????????????
                                    obj.btnCallbackFn.form.setFieldsValue({ projectEmployeeId: data.projectEmployeeId });
                                    obj.btnCallbackFn.setBtnsDisabled('add', 'adddiySubmit');
                                    obj.btnCallbackFn.msg.success(message);
                                    obj.btnCallbackFn.setActiveKey('1');
                                } else {
                                    obj.btnCallbackFn.msg.error(message);
                                }
                            })
                        },
                        faBuClick: (obj) => {//??????
                            //???????????????
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                //????????????????????????????????????
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].auditStatus === '?????????') {
                                        aa.push(obj.selectedRows[i].auditStatus);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('???????????????????????????????????????');
                                } else {
                                    //??????:???????????????
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????');
                            }
                        },
                        cheHuiClick: (obj) => {//??????
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].auditStatus === '?????????') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????');
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
                    //         label: '??????',
                    //         field: 'addCancelBtn',
                    //         onClick: "bind:addClick",
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 field: 'adddiySubmit',
                    //                 onClick: "bind:addSaveClick"
                    //             },
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '??????',
                    //         onClick: "bind:editClick",
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'diySubmit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 onClick: "bind:editSaveClick"
                    //             },
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZjTzProjectEmployee'
                    //         }
                    //     },
                    //     {
                    //         name: 'Check',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: "bind:faBuClick",
                    //     },
                    //     {
                    //         name: 'Withdraw',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: "bind:cheHuiClick",
                    //     }
                    // ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
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
                                        label: '?????????',
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
                                        label: '????????????',
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
                                        label: '????????????',
                                        field: 'registerUnits',
                                        initialValue: companyName,
                                        addDisabled: true,
                                        editDisabled: true
                                    },
                                    {
                                        type: 'textarea',
                                        label: '??????',
                                        field: 'remarks',
                                    },
                                    {
                                        label: '??????',
                                        showDownloadIcon: true,//????????????????????????
                                        onPreview: "bind:_docFilesByOfficeUrl",//365??????

                                        field: 'zjTzFileListProjectEmployee',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '????????????'
                                            }
                                        },
                                    },
                                    {
                                        field: 'auditStatus',
                                        type: "select",
                                        hide: true,
                                        initialValue: "?????????",
                                    }
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "????????????",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("projectEmployeeId"))
                            },
                            style: {
                                paddingBottom: "20px"
                            },
                            content: {

                                formConfig: [
                                    {
                                        label: '??????', //????????????
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
                                                        title: '??????', //????????????
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
                                                        title: '????????????',
                                                        dataIndex: 'employeeName',
                                                        onClick: 'detail',
                                                        key: "employeeName",
                                                        align: 'center',
                                                        filter: true
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
                                                        title: "????????????",
                                                        dataIndex: "idCard",
                                                        key: "idCard",
                                                        align: "center",
                                                        filter: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                        hide: true
                                                    }
                                                },

                                            ],
                                            tabs: [
                                                {
                                                    field: "form3",
                                                    name: "qnnForm",
                                                    title: "??????????????????",
                                                    content: {
                                                        formConfig: [
                                                            {
                                                                type: "string",
                                                                label: "??????ID",
                                                                field: "employeeInfoId", //?????????????????? ***??????
                                                                hide: true
                                                            },
                                                            //????????????: ????????????????????????????????????,?????????????????????,?????????????????????????????????????????????????????????????????????.
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
                                                                    return projectId
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
                                                                    return projectName
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
                                                                        <div style={{ height: '60px', lineHeight: '56px', marginLeft: '20px' }}><Button type='primary' disabled={obj.Pstate.drawerDetailTitle === '??????'? false:true} onClick={() => {
                                                                            // ??????6?????????????????????
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
                                                                                                        okText: "??????",
                                                                                                        content: (<p>???????????????<br />{data.employeeName}{data.idCard}</p>),
                                                                                                        cancelText: "??????",
                                                                                                        onOk: () => {
                                                                                                            let employeeInfoId = data.employeeInfoId;
                                                                                                            this.props.myFetch('importProjectZjTzEmployeeInfo', {
                                                                                                                //????????????id
                                                                                                                projectEmployeeId: this.state.projectEmployeeId,
                                                                                                                //??????id
                                                                                                                employeeInfoId: employeeInfoId,
                                                                                                                //??????id
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
                                                                                Msg.warn('????????????????????????')
                                                                            }

                                                                        }}>????????????</Button></div>
                                                                    );
                                                                },
                                                                // hide: (obj) => {
                                                                //     if (obj.Pstate.drawerDetailTitle === '??????' || obj.Pstate.drawerDetailTitle === '??????') {
                                                                //         return true
                                                                //     } else {
                                                                //         return false
                                                                //     }

                                                                // }
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
                                                            //         if(obj.Pstate.drawerDetailTitle === '??????' && obj.initialValues.person.employeeTypeId === "10"){
                                                            //             return 'noEdit'
                                                            //         }else {
                                                            //             return 'canEdit'
                                                            //         }
                                                            //     }
                                                            // },
                                                            //??????:??????????????????
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
                                                                        this.tableRYXX.qnnForm.form.setFieldsValue({ sentUnit: '' })
                                                                    }
                                                                    this.tableRYXX.btnCallbackFn.refresh();
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
                                                            //??????: ????????????
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
                                                            //         if(obj.Pstate.drawerDetailTitle === '??????' && (obj.initialValues.person.politicalId === "2" || obj.initialValues.person.politicalId === "5")){
                                                            //             return 'noEdit'
                                                            //         }else {
                                                            //             return 'canEdit'
                                                            //         }
                                                            //     }
                                                            // },
                                                            //??????: ??????????????????(??????????????????????????????:2?????????????????????:5???????????????)
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
                                                                label: "??????????????????",
                                                                field: "attendPartyTime",
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
                                                            //??????:????????????
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
                                                    field: "form4",
                                                    name: "qnnForm",
                                                    title: "????????????",
                                                    content: {
                                                        formConfig: [
                                                            //???(???)??????????????????(??????????????????????????????????????????)
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
                                                            //?????????????????????
                                                            // {
                                                            //     type: 'component',
                                                            //     field: 'diyZZ',
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
                                                            //                     title: "????????????",
                                                            //                     dataIndex: 'politicsStatus',
                                                            //                     Key: 'politicsStatus',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'string',
                                                            //                     field: "politicsStatus",
                                                            //                     label: "????????????",
                                                            //                     placeholder: '?????????',
                                                            //                 }
                                                            //             },
                                                            //             {
                                                            //                 table: {
                                                            //                     title: "??????????????????",
                                                            //                     dataIndex: 'joinPartTime',
                                                            //                     Key: 'joinPartTime',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'date',
                                                            //                     field: 'joinPartTime',
                                                            //                     label: "??????????????????",
                                                            //                     placeholder: '?????????',
                                                            //                 }
                                                            //             },
                                                            //             {
                                                            //                 table: {
                                                            //                     title: "???????????????????????????",
                                                            //                     dataIndex: 'joinPartUnit',
                                                            //                     Key: 'joinPartUnit',
                                                            //                     tdEdit: true
                                                            //                 },
                                                            //                 form: {
                                                            //                     type: 'string',
                                                            //                     field: "joinPartUnit",
                                                            //                     label: "???????????????????????????",
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
                                                            //?????????????????????(?????????????????????)
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
                                                            //                     tdEdit: true
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
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'add',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '????????????',
                                                    formBtns: [
                                                        {
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '??????',
                                                        },
                                                        {
                                                            name: 'submit',
                                                            type: 'primary',
                                                            label: '??????',
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
                                                    label: '??????',
                                                    formBtns: [
                                                        {
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '??????',
                                                        },
                                                        {
                                                            name: 'submit',
                                                            type: 'primary',
                                                            label: '??????',
                                                            fetchConfig: {
                                                                apiName: 'updateZjTzEmployeeInfo',
                                                            },
                                                            onClick: (obj) => {
                                                                if (this.table) {
                                                                    this.table.refresh();
                                                                }
                                                                // ???????????????
                                                                // this.table.clearSelectedRows();
                                                            },
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'Reference',
                                                    type: 'primary',
                                                    label: '??????',
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
                                                    label: '??????',
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
                                                    label: '??????',
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
                    title="?????????????????????????????????"
                    footer={[
                        <Button key="back" onClick={() => {
                            this.setState({
                                visibleDel: false
                            })
                        }}>
                            ??????
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
                            ??????
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
                    title="????????????"
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
                                    label: '????????????',
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
                                        searchPlaceholder: '????????????????????????',
                                        searchApi: 'getZjTzEmployeeInfoListSelectPerson',  //??????????????????api  [string]
                                        searchParamsKey: 'idCard',
                                        searchOtherParams: { pageSize: 999, limit: 10, page: 1 }//????????????????????????  [object]
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'no',
                                    label: '??????',
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
                                    label: '??????',
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
                                                    // ????????????
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
                                                        okText: "??????",
                                                        content: (<p>{aa}</p>),
                                                        cancelText: "??????",
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
                    title="??????"
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
                                                    title: '??????',
                                                    width: 100,
                                                    dataIndex: 'employeeName',
                                                    key: 'employeeName'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    width: 120,
                                                    dataIndex: 'idCard',
                                                    key: 'idCard'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'joinTime',
                                                    key: 'joinTime',
                                                    tdEdit: true,
                                                    format: 'YYYY-MM-DD'
                                                },
                                                form: {
                                                    type: 'date',
                                                    required: true,
                                                    field: 'joinTime',
                                                    placeholder: '?????????'
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
                                    label: '??????',
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
                                    label: '??????',
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
                                            Msg.error('???????????????????????????????????????');

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


