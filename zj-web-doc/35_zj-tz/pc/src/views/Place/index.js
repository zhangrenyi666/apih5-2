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
                                Msg.warn('???????????????????????????')
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].statusId === '1') {
                                Msg.warn('?????????????????????????????????')
                                obj.btnCallbackFn.closeDrawer();
                            } else {
                                if (obj.selectedRows[0].releaseId === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    this.table.clearSelectedRows();
                                }
                            }
                        },
                        faBuClick: (obj) => {//??????

                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                // ????????????
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
                                    Msg.warn('?????????/?????????????????????????????????');
                                } else {
                                    if (aa.length > 0) {
                                        Msg.warn('?????????????????????????????????');
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                                Msg.warn('???????????????');
                            }
                        },
                        cheHuiClick: (obj) => {//??????
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
                                    Msg.warn('??????????????????????????????');
                                } else {

                                    if (aa.length > 0) {
                                        Msg.warn('?????????????????????????????????');
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                                Msg.warn('???????????????');
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
                                            Msg.warn('?????????????????????????????????')
                                        }
                                    } else {
                                        Msg.warn('?????????/?????????????????????????????????')
                                    }
                                } else {
                                    Msg.warn('?????????????????????');
                                }
                            } else {
                                Msg.warn('???????????????');
                            }
                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].statusId === '1') {
                                        if (obj.selectedRows[0].releaseId === '1') {
                                           
                                            confirm({
                                                title: "?????????????????????????????????",
                                                okText: "??????",
                                                cancelText: "??????",
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
                                            Msg.warn('???????????????????????????????????????')
                                        }
                                    } else {
                                        Msg.warn('?????????/?????????????????????????????????')
                                    }
                                } else {
                                    Msg.warn('?????????????????????');
                                }
                            } else {
                                Msg.warn('???????????????');
                            }
                        },
                        filestExport: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                confirm({
                                    title: "??????????????????????",
                                    okText: "??????",
                                    cancelText: "??????",
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
                                Msg.warn('???????????????');
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
                                    Msg.warn('??????????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    if (aa.length > 0) {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('????????????????????????!');
                                        this.table.clearSelectedRows();
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                                Msg.warn('???????????????!');
                            }
                        },
                        // ??????---?????????????????????????????????
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
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????');
                            }
                        },
                        // ??????
                        tuiHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                let bb = [];

                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // ?????????/?????????
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                    // ??????
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        bb.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (bb.length > 0) {
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    if (aa.length > 0) {
                                        Msg.warn('?????????/?????????????????????????????????');
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                    {...config}
                    desc={ ext1 === '1' ? '?????????????????????????????????????????????????????????????????????????????????????????????' : null}
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
                                label: '????????????',
                                field: 'projectShortName',
                                type: 'string',
                                initialValue: projectShortName,
                                placeholder: '?????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true
                            }
                        }, {
                            table: {
                                title: '??????',
                                dataIndex: 'title',
                                key: 'title',
                                width: 300,
                                onClick: 'detail',
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                required: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                type: 'string',
                                label: '??????',
                                field: 'symbolNo',
                                required: true,
                                placeholder: '?????????',
                            },
                        }, {
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????'
                            },
                        }, {
                            isInTable: false,
                            form: {
                                type: 'date',
                                label: '??????????????????',
                                field: 'sysDate',
                                required: true,
                                initialValue: new Date(),
                                placeholder: '?????????',
                            },
                        }, {
                            table: {
                                title: '????????????',
                                filter: true,
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                width: 120,
                                tooltip: 23,
                            },
                            form: {
                                field: 'departmentName',
                                label: '????????????',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                filter: true,
                                dataIndex: 'effectDate',
                                key: 'effectDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                required: true,
                                type: 'date',
                                placeholder: '?????????',
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                filter: true,
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                required: true,
                                type: 'date',
                                placeholder: '?????????',
                            },
                        },

                        {
                            isInTable: false,
                            form: {
                                required: true,
                                type: 'string',
                                label: '????????????',
                                field: 'registerUser',
                                initialValue: realName,
                                placeholder: '?????????',
                                addDisabled: true,
                                editDisabled: true
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                filter: true,
                                dataIndex: 'effectiveId',
                                key: 'effectiveId',
                                width: 120,
                                type: 'select'
                            },
                            form: {
                                type: "select",
                                required: true,
                                label: "??????????????????",
                                field: "effectiveId",
                                initialValue: '1',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "0"
                                    },
                                    {
                                        label: "???",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'textarea',
                                label: '????????????',
                                field: 'report',
                                required: true,
                                autoSize: {
                                    minRows: 2,
                                    // maxRows: 10
                                },
                                placeholder: '?????????',
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '?????????/?????????',
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
                                title: '???????????????????????????',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '???'
                                    } else {
                                        return '???'
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
                                        label: "???",
                                        value: "0"
                                    },
                                    {
                                        label: "???",
                                        value: "1"
                                    }
                                ]
                            }
                        },
                      
                        {
                            table: {
                                title: '????????????',
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
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "??????",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????',
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
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    },
                                    {
                                        label: "?????????",
                                        value: "2"
                                    }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                // 
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '????????????'
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "??????",
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
                                            return '<a>????????????</a>';
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
                        title="????????????"
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
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                //     label: "??????",
                                //     field: "title",
                                //     initialValue:PlansToPushData.length ? PlansToPushData[0].title : '',
                                //     disabled:true,
                                //     placeholder: "?????????",
                                // },
                                // {
                                //     type: "string",
                                //     label: "??????",
                                //     field: "symbolNo",
                                //     initialValue:PlansToPushData.length ? PlansToPushData[0].symbolNo : '',
                                //     disabled:true,
                                //     placeholder: "?????????",
                                // },
                                {
                                    label: '?????????',
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
                                        collectApi: "appGetSysFrequentContactsList",  //??????????????????     ??????????????????[{xx:xxx,...}]
                                        collectApiByAdd: "appAddSysFrequentContacts", //??????????????????   ?????????????????????[{xx:xxx,...}]
                                        collectApiByDel: "appRemoveSysFrequentContacts", //??????????????????
                                        search: true,
                                        searchPlaceholder: '????????????????????????',
                                        searchParamsKey: 'search',//???????????????K ?????????'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//????????????????????????  [object]
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
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