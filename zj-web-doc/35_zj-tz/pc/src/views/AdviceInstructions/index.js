import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjTzNoteInstructSugList',
    },
    antd: {
        rowKey: function (row) {
            return row.noteInstructSugId
        },
        size: 'small'
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
            visibleDirectional: false,
            noteInstructSugId: '',
            companyId: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,
            companyName: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyName,
            loadingDirectional: false
        }
    }
    componentDidMount() {

    }
    handleCancelDirectional = () => {
        this.setState({ visibleDirectional: false, loadingDirectional: false });
    }
    render() {
        const { visibleDirectional, loadingDirectional,noteInstructSugId, companyId } = this.state;
        const { realName,ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                    {...config}
                    desc={ ext1 === '1' ? '?????????????????????????????????????????????????????????????????????????????????????????????' : null}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'noteInstructSugId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                                initialValue: companyId
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                tooltip: 80,
                                onClick: 'detail',
                                dataIndex: 'title',
                                key: 'title'
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '?????????',
                                required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 130,
                                dataIndex: 'publisher',
                                key: 'publisher'
                            },
                            form: {
                                field: 'publisher',
                                type: 'string',
                                label: '?????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                },
                                initialValue: realName
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 130,
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                field: 'releaseDate',
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '?????????',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "textarea",
                                label: "????????????",
                                field: "registerPerson",
                                placeholder: '?????????...'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                width: 100,
                                dataIndex: 'releaseName',
                                key: 'releaseName'
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                hide: true,
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
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????-???????????????'
                                    }
                                },
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
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
                                        name: 'AdviceInstructionsDetail',
                                        render: (rowData) => {
                                            return '<a>????????????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { noteInstructSugId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}AdviceInstructionsDetail/${noteInstructSugId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('????????????????????????!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        faBuClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {

                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
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
                                            myFetch('batchReleaseZjTzNoteInstructSug', obj.selectedRows).then(
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
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '0') {
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
                                            myFetch('batchRecallZjTzNoteInstructSug', obj.selectedRows).then(
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
                        dingXiangTuiSong: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {
                                        this.setState({
                                            visibleDirectional: true,
                                            noteInstructSugId: obj.selectedRows[0].noteInstructSugId
                                        });
                                    } else {
                                        Msg.warn('???????????????????????????????????????')
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
                                    if (obj.selectedRows[0].releaseId === '1') {
                                   
                                        confirm({
                                            title: "?????????????????????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
                                            onOk: () => {
                                                this.props.myFetch('toHomeShowZjTzNoteInstructSug', obj.selectedRows[0]).then(
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
                                        myFetch('batchExportZjTzNoteInstructSugFile', obj.selectedRows).then(
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
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
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
                                            myFetch('batchDeleteUpdateZjTzNoteInstructSug', obj.selectedRows).then(
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
                            } else {
                                Msg.warn('???????????????!');
                            }

                        }
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
                />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="????????????"
                    visible={visibleDirectional}
                    footer={null}
                    onCancel={this.handleCancelDirectional}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelDirectional}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingDirectional}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    type: 'string',
                                    field: "noteInstructSugId",
                                    initialValue: noteInstructSugId,
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    field: "noteInstructSugRecordId",
                                    hide: true
                                },
                                {
                                    field: 'contentDesc',
                                    type: 'textarea',
                                    label: "????????????",
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '?????????',
                                    field: 'personList',
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
                                        searchParamsKey: 'search',
                                        searchOtherParams: { pageSize: 999 }
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
                                            visibleDirectional: false,
                                            loadingDirectional: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '??????',//???
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingDirectional: true
                                        })
                                        this.props.myFetch('addZjTzNoteInstructSugRecord', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleDirectional: false,
                                                        loadingDirectional: false
                                                    })
                                                } else {
                                                    Msg.error(message)
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
            </div>
        );
    }
}

export default index;