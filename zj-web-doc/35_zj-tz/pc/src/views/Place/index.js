import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
import s from "./style.less";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.policyId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        },
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },

    firstRowIsSearch: false,
    formItemLayout: {
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
class index extends Component {
    constructor() {
        super();
        this.state = {
            visible: false,
            PlansToPushData: []
        }
    }
    render() {
        const { PlansToPushData,visible } = this.state;
        const { realName,ext1, curCompany: { projectShortName, projectId } } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZjTzPolicyLocalList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].statusId === '1') {
                                Msg.warn('已上报的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else {
                                if (obj.selectedRows[0].releaseId === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已发布的不能修改!');
                                    this.table.clearSelectedRows();
                                } else {
                                    this.table.clearSelectedRows();
                                }
                            }
                        },
                        faBuClick: (obj) => {//发布

                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                // 发布情况
                                let aa = [];
                                let flowStaus = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                    if (obj.selectedRows[i].statusId === '2' || obj.selectedRows[i].statusId === '0') {
                                        console.log(obj.selectedRows[i].statusId);
                                        flowStaus.push(obj.selectedRows[i].statusId)
                                    }
                                }
                                if (flowStaus.length > 0) {
                                    Msg.warn('未上报/被退回的数据不能发布！');
                                } else {
                                    if (aa.length > 0) {
                                        Msg.warn('已发布的消息不能发布！');
                                    } else {
                                        confirm({
                                            title: "确定发布么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchDeleteReleaseZjTzPolicyLocal', obj.selectedRows).then(
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
                                let flowStaus = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '0') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        flowStaus.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (flowStaus.length > 0) {
                                    Msg.warn('请选择已发布的数据！');
                                } else {

                                    if (aa.length > 0) {
                                        Msg.warn('未发布的消息不能撤回！');
                                    } else {
                                        confirm({
                                            title: "确定撤回么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchDeleteRecallZjTzPolicyLocal', obj.selectedRows).then(
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
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        fenXiBaoGao: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].statusId === '1') {
                                        if (obj.selectedRows[0].releaseId === '1') {
                                            this.setState({
                                                PlansToPushData: obj.selectedRows,
                                                visible: true
                                            });
                                        } else {
                                            Msg.warn('未发布的消息不能推送！')
                                        }
                                    } else {
                                        Msg.warn('未上报/被退回的消息不能推送！')
                                    }
                                } else {
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].statusId === '1') {
                                        if (obj.selectedRows[0].releaseId === '1') {
                                           
                                            confirm({
                                                title: "确定广而告之到首页么？",
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    this.props.myFetch('updateZjTzPolicyLocalHomeShow', obj.selectedRows[0]).then(
                                                        ({ success, message, data }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                this.table.refresh();
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        } else {
                                            Msg.warn('未发布的消息不能广而告之！')
                                        }
                                    } else {
                                        Msg.warn('未上报/被退回的消息不能推送！')
                                    }
                                } else {
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        filestExport: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                confirm({
                                    title: "确定导出附件么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('batchExportZjTzPolicyLocalFile', obj.selectedRows).then(
                                            ({ success, message, data }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    window.location.href = data;
                                                    this.table.refresh();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                })
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let aa = [];
                            let apih5FlowStatusArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                for (var n = 0; n < obj.selectedRows.length; n++) {
                                    if (obj.selectedRows[n].statusId === '1') {
                                        apih5FlowStatusArry.push(obj.selectedRows[n].statusId);
                                    }
                                }
                                if (apih5FlowStatusArry.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已上报的数据不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    if (aa.length > 0) {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('已发布的不能删除!');
                                        this.table.clearSelectedRows();
                                    } else {
                                        confirm({
                                            title: "确定删除么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchDeleteUpdateZjTzPolicyLocal', obj.selectedRows).then(
                                                    ({ success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })

                                    }
                                }

                            } else {
                                Msg.warn('请选择数据!');
                            }
                        },
                        // 上报---上报后，发布，广而告之
                        shangBaoClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已上报的消息不能上报！');
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchReportZjTzPolicyLocal', obj.selectedRows).then(
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
                        // 退回
                        tuiHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                let bb = [];

                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // 未上报/被退回
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                    // 发布
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        bb.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (bb.length > 0) {
                                    Msg.warn('已发布的消息不能退回！');
                                } else {
                                    if (aa.length > 0) {
                                        Msg.warn('未上报/被退回的消息不能退回！');
                                    } else {
                                        confirm({
                                            title: "确定退回么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchReturnZjTzPolicyLocal', obj.selectedRows).then(
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
                    {...config}
                    desc={ ext1 === '1' ? '发布：使数据全平台用户可见；广而告之：将已发布的数据展示至首页' : null}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'policyId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue: projectId,
                                hide: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                label: '登记项目',
                                field: 'projectShortName',
                                type: 'string',
                                initialValue: projectShortName,
                                placeholder: '请输入',
                                required: true,
                                addDisabled: true,
                                editDisabled: true
                            }
                        }, {
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                key: 'title',
                                width: 300,
                                onClick: 'detail',
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                type: 'string',
                                label: '文号',
                                field: 'symbolNo',
                                required: true,
                                placeholder: '请输入',
                            },
                        }, {
                            table: {
                                title: '所属省份',
                                dataIndex: 'provinceId',
                                key: 'provinceId',
                                width: 100,
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                },
                                placeholder: '请输入'
                            },
                        }, {
                            isInTable: false,
                            form: {
                                type: 'date',
                                label: '系统发布日期',
                                field: 'sysDate',
                                required: true,
                                initialValue: new Date(),
                                placeholder: '请选择',
                            },
                        }, {
                            table: {
                                title: '发文部门',
                                filter: true,
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                width: 120,
                                tooltip: 23,
                            },
                            form: {
                                field: 'departmentName',
                                label: '发文部门',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '原文生效日期',
                                filter: true,
                                dataIndex: 'effectDate',
                                key: 'effectDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                required: true,
                                type: 'date',
                                placeholder: '请选择',
                            },
                        },
                        {
                            table: {
                                title: '原文发布日期',
                                filter: true,
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                required: true,
                                type: 'date',
                                placeholder: '请选择',
                            },
                        },

                        {
                            isInTable: false,
                            form: {
                                required: true,
                                type: 'string',
                                label: '登记用户',
                                field: 'registerUser',
                                initialValue: realName,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true
                            },
                        },
                        {
                            table: {
                                title: '是否有效文件',
                                filter: true,
                                dataIndex: 'effectiveId',
                                key: 'effectiveId',
                                width: 120,
                                type: 'select'
                            },
                            form: {
                                type: "select",
                                required: true,
                                label: "是否有效文件",
                                field: "effectiveId",
                                initialValue: '1',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'textarea',
                                label: '分析报告',
                                field: 'report',
                                required: true,
                                autoSize: {
                                    minRows: 2,
                                    // maxRows: 10
                                },
                                placeholder: '请输入',
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '回复数/推送数',
                                dataIndex: 'pushInfoReply',
                                key: 'pushInfoReply',
                                width: 120,
                                render: (data, rowData) => {
                                    return <a>{rowData.pushInfoReply}/{rowData.pushInfoAll}</a>;
                                },
                                onClick: (obj) => {
                                    const { mainModule } = obj.props.myPublic.appInfo;
                                    const { policyId } = obj.rowData;
                                    obj.props.dispatch(
                                        push(`${mainModule}PlaceDetail/${policyId}`)
                                    )
                                }
                            }
                        },
                        {
                            table: {
                                title: '是否在首页广而告之',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '否'
                                    } else {
                                        return '是'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "homeShow",
                                hide: true,
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            }
                        },
                      
                        {
                            table: {
                                title: '发布状态',
                                dataIndex: 'releaseName',
                                key: 'releaseName',
                                filter: true,
                                width: 100,
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                // hide: true,
                                optionData: [
                                    {
                                        label: "未发布",
                                        value: "0"
                                    },
                                    {
                                        label: "发布",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '上报状态',
                                width: 120,
                                dataIndex: 'statusId',
                                key: 'statusId',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "statusId",
                                optionData: [
                                    {
                                        label: "未上报",
                                        value: "0"
                                    },
                                    {
                                        label: "已上报",
                                        value: "1"
                                    },
                                    {
                                        label: "被退回",
                                        value: "2"
                                    }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                // 
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '地方政策'
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 80,
                                btns: [
                                    {
                                        name: 'PlaceDetail',
                                        render: (rowData) => {
                                            return '<a>预案详情</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { policyId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}PlaceDetail/${policyId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="预案推送"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'PlansToPush'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 7 },
                                    sm: { span: 7 }
                                },
                                wrapperCol: {
                                    xs: { span: 17 },
                                    sm: { span: 17 }
                                }
                            }}
                            formConfig={[
                                // {
                                //     type: "string",
                                //     label: "标题",
                                //     field: "title",
                                //     initialValue:PlansToPushData.length ? PlansToPushData[0].title : '',
                                //     disabled:true,
                                //     placeholder: "请输入",
                                // },
                                // {
                                //     type: "string",
                                //     label: "文号",
                                //     field: "symbolNo",
                                //     initialValue:PlansToPushData.length ? PlansToPushData[0].symbolNo : '',
                                //     disabled:true,
                                //     placeholder: "请输入",
                                // },
                                {
                                    label: '推送给',
                                    field: 'zjTzPolicyLocalReplyList',
                                    type: 'treeSelect',
                                    required: true,
                                    treeSelectOption: {
                                        help: true,
                                        selectType: '2',
                                        fetchConfig: {
                                            apiName: 'getSysDepartmentUserAllTree',
                                        },
                                        useCollect: true,
                                        collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                        collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                        collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                        search: true,
                                        searchPlaceholder: '姓名、账号、电话',
                                        searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        PlansToPushData[0].zjTzPolicyLocalReplyList = obj.values.zjTzPolicyLocalReplyList;
                                        obj.btnfns.fetchByCb('updateZjTzPolicyLocalPush', ...PlansToPushData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visible: false });
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            
            </div>
        );
    }
}

export default index;