import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Checkbox, Tag, Spin } from "antd";
import HasTicketMoneyForm from './hasTicketMoneyForm';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.partManageId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    limit: 999999,
    curPage: 1,
    paginationConfig: false,
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
            designFlowId: props.match.params.designFlowId || '',
            visibleSend: false,
            loadingSend: false,
            partManageId: '',
            fujianLook: [],
            rowDataList: []
        }
    }
    onChangeRadio = (data, rowData, e) => {
        if (e.target.checked) {
            rowData.bidPartId = '1';
        } else {
            rowData.bidPartId = '0';
        }
        this.props.myFetch('updateZjTzPartManage', rowData).then(
            ({ success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.tableGM.refresh();
                } else {
                    Msg.error(message);
                    this.tableGM.refresh();
                }
            }
        );

    }
    onChangeCheckBox = (data, rowData, e) => {
        if (e.target.checked) {
            rowData.buId = '1';
        } else {
            rowData.buId = '0';
        }
        this.props.myFetch('updateZjTzPartManage', rowData).then(
            ({ success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.tableGM.refresh();
                } else {
                    Msg.error(message);
                    this.tableGM.refresh();
                }
            }
        );
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { designFlowId, visibleSend, loadingSend } = this.state;
        let me = this;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzPartManageList',
                        otherParams: {
                            designFlowId: designFlowId
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                    componentsKey={{
                        HasTicketMoneyForm: (obj) => {
                            let delAttr = ["id"];
                            return <HasTicketMoneyForm {...obj} {...this.props} designFlowId={designFlowId} />
                        }
                    }}
                    method={{
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}DesignProcessManagement`)
                            )
                        },
                        lockClick: (obj) => {
                            const { myFetch } = obj.props;
                            let unSameKindArry = [];
                            let SameKindArry = [];
                            let name = '';
                            let apiName = '';
                            if (obj.selectedRows.length > 0) {
                                for (var j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].lockFlag === '0') {
                                        unSameKindArry.push(obj.selectedRows[j]);

                                    } else {
                                        SameKindArry.push(obj.selectedRows[j]);
                                    }
                                }
                                if (unSameKindArry.length > 0) {
                                    name = '??????????????????';
                                    apiName = 'batchLockUpdateZjTzPartManage';
                                }
                                if (SameKindArry.length > 0) {
                                    name = '??????????????????';
                                    apiName = 'batchClearUpdateZjTzPartManage';
                                }
                                if ((obj.selectedRows.length === unSameKindArry.length) || (obj.selectedRows.length === SameKindArry.length)) {
                                    confirm({
                                        title: name,
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch(apiName, obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                        this.tableGM.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
                                } else {
                                    Msg.warn('??????????????????????????????!');
                                }
                            } else {
                                Msg.warn('???????????????!');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].lockFlag === '0') {
                                        // ?????????
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
                                            onOk: () => {
                                                myFetch('batchDeleteUpdateZjTzPartManage', obj.selectedRows).then(
                                                    ({ success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.tableGM.refresh();
                                                            this.tableGM.clearSelectedRows();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })
                                    } else {
                                        Msg.warn('??????????????????????????????!');
                                    }

                                } else {
                                    Msg.warn('????????????????????????!');
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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'partManageId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'designFlowId',
                                type: 'string',
                                initialValue: designFlowId,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'orderNum',
                                key: 'orderNum',
                                width: 60,
                                align: 'center',
                                defaultSortOrder: 'descend',
                                fixed: 'left',
                                render: (data, rowData) => {
                                    if (rowData.colorFlag === 'white') {
                                        return data
                                    } else {
                                        return (
                                            <Tag style={{ width: '100%', textAlign: 'center' }} color={rowData.colorFlag} key={data}>
                                                {data}
                                            </Tag>
                                        )
                                    }

                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                onClick: 'detail',
                                dataIndex: 'designPartName',
                                key: 'designPartName',
                                tdEdit: true,
                                fieldsConfig: {
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: true,
                                    disabled: ({ record }) => {
                                        if (ext1 === '1') {
                                            return true
                                        } else {
                                            if (record.lockFlag === "1") {
                                                return true
                                            }
                                            return false;
                                        }

                                    }
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.designPartName = obj.newRowData.designPartName;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'bidPartName',
                                align: 'center',
                                width: 130,
                                key: 'bidPartName',
                                tdEdit: false,
                                render: (data, rowData) => {
                                    if (ext1 === '1') {
                                        // ?????????????????????
                                        return <Checkbox disabled checked={false}></Checkbox>
                                    } else {
                                        // ??????????????????
                                        if (rowData.lockFlag === '1') {
                                            if (rowData.bidPartId === '0') {
                                                return <Checkbox disabled checked={false}></Checkbox>
                                            } else {
                                                return <Checkbox disabled checked={true}></Checkbox>
                                            }
                                        } else {
                                            if (rowData.bidPartId === '0') {
                                                return <Checkbox checked={false} onChange={this.onChangeRadio.bind(this, data, rowData)}></Checkbox>
                                            } else {
                                                return <Checkbox checked={true} onChange={this.onChangeRadio.bind(this, data, rowData)}></Checkbox>
                                            }
                                        }
                                    }

                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'radio',
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                field: 'bidPartId',
                                placeholder: '?????????',
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
                            table: {
                                title: '???????????????',
                                dataIndex: 'buName',
                                key: 'buName',
                                width: 130,
                                align: 'center',
                                tdEdit: false,
                                render: (data, rowData) => {
                                    if (ext1 === '1') {
                                        // ?????????????????????
                                        if (rowData.lockFlag === '1') {
                                            if (rowData.buId === '0') {
                                                return <Checkbox disabled checked={false}></Checkbox>
                                            } else {
                                                return <Checkbox disabled checked={true}></Checkbox>
                                            }
                                        } else {
                                            if (rowData.buId === '0') {
                                                return <Checkbox checked={false} onChange={this.onChangeCheckBox.bind(this, data, rowData)}></Checkbox>
                                            } else {
                                                return <Checkbox checked={true} onChange={this.onChangeCheckBox.bind(this, data, rowData)}></Checkbox>
                                            }
                                        }
                                    } else {
                                        // ??????????????????
                                        return <Checkbox disabled checked={false}></Checkbox>
                                    }


                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'radio',
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                field: 'buId',
                                placeholder: '?????????',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "0"
                                    },
                                    {
                                        label: "???",
                                        value: "1"
                                    }
                                ],
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'planDate',
                                key: 'planDate',
                                tdEdit: true,
                                width: 140,
                                format: 'YYYY-MM-DD',
                                fieldsConfig: {
                                    field: 'planDate',
                                    type: 'date',
                                    label: '????????????',
                                    disabled: ({ record }) => {
                                        if (ext1 === '1') {
                                            // ?????????????????????
                                            return true
                                        } else {
                                            // ??????????????????
                                            if (record.lockFlag === "1") {
                                                return true
                                            }
                                            return false;
                                        }

                                    }
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.planDate = obj.newRowData.planDate;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'planDate',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'actualDate',
                                width: 140,
                                key: 'actualDate'
                            },
                            form: {
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'actualDate',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'lockFlag',
                                width: 100,
                                key: 'lockFlag',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????'
                                    } else {
                                        return '??????'
                                    }
                                }
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'fileFlag',
                                width: 140,
                                key: 'fileFlag',
                                tdEdit: ext1 === '1' ? false : true,
                                render: (data) => {
                                    if (data) {
                                        if (data === '0') {
                                            return '?????????'
                                        } else {
                                            return '?????????'
                                        }
                                    } else {
                                        return ''
                                    }
                                },
                                onClick: (obj) => {
                                    this.setState({
                                        partManageId: obj.rowData.partManageId,
                                        fujianLook: obj.rowData.zjTzFileList,
                                        rowDataList: obj.rowData
                                    }, () => {
                                        if (obj.rowData.zjTzFileList.length > 0) {
                                            this.setState({
                                                visibleSend: true,
                                            })
                                        } else {
                                            Msg.warn('?????????????????????????????????')
                                        }
                                    })
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.zjTzFileList = obj.newRowData.zjTzFileList;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????????????????'
                                    }
                                },
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                onChange: (val, rowData) => {
                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                        Msg.warn('???????????????rar???zip???7z??????????????????')
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                zjTzFileList: []
                                            })
                                        }, 200)
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            isInTable: ext1 === '1' ? false : true,
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
                                        name: 'crDetail',
                                        render: (rowData) => {
                                            if (rowData.rowData.lockFlag === '1') {
                                                return '';
                                            } else {
                                                return '<a>??????</a>';
                                            }

                                        },
                                        onClick: (obj) => {
                                            this.props.myFetch('insertZjTzPartManage', { partManageId: obj.rowData.partManageId }).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                    } else {
                                                        Msg.error(message);
                                                        this.tableGM.refresh();
                                                    }
                                                }
                                            );
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                <Modal
                    width='800px'
                    style={{ top: '0' }}
                    title="????????????"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '800px' }}
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
                                    type: "qnnTable",
                                    field: "qnnTableKP",
                                    qnnTableConfig: {
                                        data: me.state.fujianLook,
                                        drawerConfig: {
                                            width: "800px"
                                        },
                                        actionBtnsPosition: "top",
                                        antd: {
                                            rowKey: function (row) {
                                                return row.partManageId;
                                            },
                                            size: 'small'
                                        },
                                        paginationConfig: {
                                            position: 'bottom'
                                        },
                                        firstRowIsSearch: false,
                                        isShowRowSelect: false,
                                        wrappedComponentRef: (me) => {
                                            this.tableFJ = me;
                                        },
                                        actionBtns: [],
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: "partManageId",
                                                    initialValue: me.state.partManageId,
                                                    hide: true
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '?????????????????????',
                                                    dataIndex: 'name',
                                                    key: 'name'
                                                },
                                                isInForm: false,
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'url',
                                                    key: 'url',
                                                    render: (data) => {
                                                        return <div onClick={() => {
                                                            // window.location.href = data
                                                        }}><a target={'_blank'} href={data}>????????????</a></div>
                                                    }
                                                },
                                                isInForm: false,
                                            },
                                            {
                                                isInForm: false,
                                                isInTable: ext1 === '1' ? false : true,
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
                                                            name: 'dityDel',
                                                            field: 'dellBtn',
                                                            render: function (rowData) {
                                                                return '<a>??????</a>';
                                                            },
                                                            onClick: (obj) => {
                                                                // debugger
                                                                let arry = me.state.fujianLook;
                                                                let arryNew = [];
                                                                for (var i = 0; i < arry.length; i++) {
                                                                    if (arry[i].name === obj.rowData.name) {

                                                                    } else {
                                                                        arryNew.push(arry[i]);
                                                                    }
                                                                }
                                                                me.state.rowDataList.zjTzFileList = arryNew;
                                                                this.props.myFetch('updateZjTzPartManage', me.state.rowDataList).then(
                                                                    ({ success, message }) => {
                                                                        if (success) {
                                                                            Msg.success(message);
                                                                            this.tableGM.refresh();
                                                                            this.setState({
                                                                                visibleSend: false
                                                                            })
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }
                                }
                            ]}
                            btns={[]}
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