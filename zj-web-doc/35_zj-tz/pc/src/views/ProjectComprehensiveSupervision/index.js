import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import s from "./style.less";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.comprehensiveSupId
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
            visibleSend: false,
            loadingSend: false,
            comprehensiveSupId: '',
            flag: 0
        }
    }
    componentDidMount() {

    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { visibleSend, loadingSend, comprehensiveSupId } = this.state;
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
                        apiName: 'getZjTzComprehensiveSupList',
                        otherParams: {
                            projectId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'comprehensiveSupId',
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
                            isInSearch: false,
                            table: {
                                title: '??????',
                                dataIndex: 'title',
                                key: 'title',
                                width: 300,
                                tooltip: 23,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '?????????',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                type: 'date',
                                required: true,
                                field: 'checkDate',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'trusteeCompanyId',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'trusteeCompanyName',
                                key: 'trusteeCompanyName'
                            },
                            form: {
                                field: 'trusteeCompanyName',
                                type: 'string',
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                required: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId',
                                    linkageFields:{
                                        trusteeCompanyName:"companyName",
                                        trusteeCompanyId:'companyId',
                                        companyId:'companyId'
                                    }
                                },
                                fetchConfig: {
                                    apiName: "getZjTzPermissionListByProject",
                                    otherParams: {
                                        allFlag: 0
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'checkDate',
                                width: 120,
                                format: 'YYYY-MM-DD',
                                key: 'checkDate',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'date',
                                field: 'checkDate'
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'inspectDeptAndPerson',
                                key: 'inspectDeptAndPerson'
                            },
                            form: {
                                field: 'inspectDeptAndPerson',
                                type: 'string',
                                required: true
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'correctiveId',
                                key: 'correctiveId',
                                width: 120,
                                type: 'select',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'correctiveId',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
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
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ??????????????????????????????????????????????????????????????????????????????????????????
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zjTzSupContentList',
                                colStyle: {
                                    paddingLeft: 12
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'supContentId',
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
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                    wrappedComponentRef: (me) => {
                                        this.zjTzSupContentListTable = me;
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'supContentId',
                                                hide: true
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
                                                title: '??????',
                                                dataIndex: 'typeId',
                                                key: 'typeId',
                                                tdEdit: true,
                                                type: 'select',
                                            },
                                            form: {
                                                label: '??????',
                                                type: 'select',
                                                field: 'typeId',
                                                placeholder: '?????????',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'leiXing'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 240,
                                                tooltip: 23,
                                                dataIndex: 'detail',
                                                key: 'detail',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 4
                                                },
                                                field: 'detail',
                                                placeholder: '?????????',

                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'needCorrectiveId',
                                                key: 'needCorrectiveId',
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: 'select',
                                                    field: 'needCorrectiveId',
                                                    optionData: [
                                                        {
                                                            label: "???",
                                                            value: "1"
                                                        },
                                                        {
                                                            label: "???",
                                                            value: "0"
                                                        }
                                                    ],
                                                    disabled: ({ record }) => {
                                                        if (record.typeId === "1") {
                                                            return true
                                                        }
                                                        return false;
                                                    }
                                                },
                                                type: 'select'
                                            },
                                            form: {
                                                field: 'needCorrectiveId',
                                                type: 'select',
                                                placeholder: '?????????',
                                                optionData: [
                                                    {
                                                        label: "???",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "???",
                                                        value: "0"
                                                    }
                                                ]
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'correctiveDate',
                                                key: 'correctiveDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: false,
                                                fieldsConfig: {
                                                    field: 'correctiveDate',
                                                    type: 'date',
                                                    placeholder: '?????????',
                                                    disabled: ({ record }) => {
                                                        if (record.typeId === "1") {
                                                            return true
                                                        }
                                                        return false;
                                                    }
                                                }
                                            },
                                            form: {
                                                field: 'correctiveDate',
                                                type: 'date',
                                                placeholder: '?????????'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                width: 200,
                                                tooltip: 23,
                                                dataIndex: 'correctiveCase',
                                                key: 'correctiveCase',
                                                tdEdit: false,
                                                fieldsConfig: {
                                                    field: 'correctiveCase',
                                                    type: 'textarea',
                                                    placeholder: '?????????',
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    },
                                                    disabled: ({ record }) => {
                                                        if (record.typeId === "1") {
                                                            return true
                                                        }
                                                        return false;
                                                    }
                                                }
                                            },
                                            form: {
                                                label: '??????????????????',
                                                field: 'correctiveCase',
                                                type: 'textarea',
                                                placeholder: '?????????',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 4
                                                }
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "?????????"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '??????'
                                        }
                                    ]
                                }
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                label: '???????????????',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????????????????'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????',
                                field: 'replyFileList',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                addDisabled: true,
                                editDisabled: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????????????????'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].correctiveId === '0') {

                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('?????????????????????');
                            }
                        },
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].correctiveId === '0' || obj.selectedRows[i].correctiveId === '2') {
                                        aa.push(obj.selectedRows[i].correctiveId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('???????????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzComprehensiveSup', obj.selectedRows).then(
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
                        zhengGaiXiaDa: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].correctiveId === '0' || obj.selectedRows[0].correctiveId === '2') {
                                        this.setState({
                                            visibleSend: true,
                                            comprehensiveSupId: obj.selectedRows[0].comprehensiveSupId
                                        });
                                    } else {
                                        Msg.warn('?????????????????????????????????')
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
                                        myFetch('batchExportZjTzComprehensiveSupFile', obj.selectedRows).then(
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
                            let bb = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].correctiveId === '1') {
                                        bb.push(obj.selectedRows[i].correctiveId);
                                    }
                                }
                                if (bb.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzComprehensiveSup', obj.selectedRows).then(
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
                    fieldsValueChange={({ form, name }, changedFields, allValues) => {
                        if (changedFields && changedFields[0] && changedFields[0].name[0] === 'zjTzSupContentList') {
                            if (allValues && allValues.zjTzSupContentList) {
                                let contList = allValues.zjTzSupContentList;
                                for (let m = 0; m < contList.length; m++) {
                                    if (contList[m].typeId === '1') {
                                        contList[m].needCorrectiveId = '0';
                                    }
                                }
                                this.zjTzSupContentListTable.qnnSetState({
                                    data: allValues.zjTzSupContentList
                                })
                            }

                        }
                    }}
                />
                <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="??????"
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
                            formConfig={[
                                {
                                    type: 'string',
                                    field: "comprehensiveSupId",
                                    initialValue: comprehensiveSupId,
                                    hide: true
                                },
                                {
                                    field: 'contentDesc',
                                    type: 'textarea',
                                    label: "????????????",
                                    required: true,
                                    placeholder: '?????????'
                                },
                                // {
                                //     label: '?????????',
                                //     field: 'personList',
                                //     type: 'treeSelect',
                                //     // required: true,
                                //     treeSelectOption: {
                                //         help: true,
                                //         maxNumber: 1,
                                //         selectType: '2',
                                //         fetchConfig: {
                                //             apiName: 'getSysDepartmentUserAllTree',
                                //         },
                                //         search: true,
                                //         useCollect:true,
                                //         collectApi:"appGetSysFrequentContactsList",  //??????????????????     ??????????????????[{xx:xxx,...}]
                                //         collectApiByAdd:"appAddSysFrequentContacts", //??????????????????   ?????????????????????[{xx:xxx,...}]
                                //         collectApiByDel:"appRemoveSysFrequentContacts", //??????????????????
                                //         searchPlaceholder: '????????????????????????',
                                //         searchParamsKey: 'search',
                                //         searchOtherParams: { pageSize: 999 }
                                //     }
                                // },
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '??????',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('correctiveZjTzComprehensiveSup', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
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