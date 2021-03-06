import React, { Component } from "react";
import { push } from "react-router-redux";
import s from "./style.less";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Checkbox, Tag, Spin } from "antd";
import HasTicketMoneyForm from './hasTicketMoneyForm';
const confirm = Modal.confirm;

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            actionBtnsVal: [],
            designFlowId: props.match.params.designFlowId || '',
            loadingOutSend: false,
            partManageId: '',
            fujianLook: [],
            rowDataList: []
        }
    }
    componentDidMount() {
        var props1 = this.props;
        let curRouteData = props1.routerInfo.routeData[props1.routerInfo.curKey];
        props1.myFetch("getSysMenuBtn", {
            menuParentId: curRouteData._value,
            tableField: "projectInfo"
        }).then(({ success, data, message }) => {
            if (success) {
                this.setState({
                    actionBtnsVal: data
                })
            } else {
            }
        })
    }
    onChangeRadio = (data, rowData, e) => {
        let aa = this.tableGM.form.getFieldsValue().zjTzPartManageList;
        for (let m = 0; m < aa.length; m++) {
            if (rowData.partManageId === aa[m].partManageId) {
                if (e.target.checked) {
                    aa[m].bidPartId = '1';
                } else {
                    aa[m].bidPartId = '0';
                }
            } else {
                aa[m].bidPartId = '0';
            }
        }
        this.tableGM.qnnSetState({
            data: aa
        })



    }
    onChangeCheckBox = (data, rowData, e) => {
        let aa = this.tableGM.form.getFieldsValue().zjTzPartManageList;
        for (let m = 0; m < aa.length; m++) {
            if (rowData.partManageId === aa[m].partManageId) {
                if (e.target.checked) {
                    aa[m].buId = '1';
                } else {
                    aa[m].buId = '0';
                }
            }
        }
        this.tableGM.qnnSetState({
            data: aa
        })
    }
    render() {
        const { designFlowId, actionBtnsVal } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <Spin spinning={this.state.loadingOutSend}>
                    <QnnForm
                        {...this.props}
                        fetchConfig={{
                            apiName: 'getZjTzPartManageList',
                            otherParams: {
                                designFlowId: designFlowId
                            }
                        }}
                        upload={this.props.myUpload}
                        fetch={this.props.myFetch}
                        wrappedComponentRef={(me) => {
                            this.tableGM = me;
                        }}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '??????id',
                                field: 'codePid',
                                hide: true
                            },
                            {
                                label: '????????????id',
                                field: 'designFlowId',
                                hide: true
                            },
                            {
                                type: 'qnnTable',
                                field: 'zjTzPartManageList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'partManageId',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.65
                                        }
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
                                    componentsKey: {
                                        HasTicketMoneyForm: (obj) => {
                                            return <HasTicketMoneyForm refreshOut={this.tableGM.refresh} {...obj} {...this.props} designFlowId={designFlowId} />
                                        }
                                    },
                                    formConfig: [
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
                                                dataIndex: 'no',
                                                key: 'no',
                                                width: 60,
                                                align: 'center',
                                                fixed: 'left',
                                                render: (data, rows, index) => {
                                                    if (rows.colorFlag === 'white') {
                                                        return index + 1
                                                    } else {
                                                        return (
                                                            <Tag style={{ width: '100%', textAlign: 'center' }} color={rows.colorFlag} key={index + 1}>
                                                                {index + 1}
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
                                                width: 120,
                                                key: 'bidPartName',
                                                tdEdit: false,
                                                render: (data, rowData) => {
                                                    if (ext1 === '1') {
                                                        // ?????????????????????
                                                        if (rowData.bidPartId === '0') {
                                                            return <Checkbox disabled checked={false}></Checkbox>
                                                        } else {
                                                            return <Checkbox disabled checked={true}></Checkbox>
                                                        }
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
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'buName',
                                                key: 'buName',
                                                width: 120,
                                                align: 'center',
                                                tdEdit: false,
                                                render: (data, rowData) => {
                                                    // if (ext1 === '1') {
                                                    // ?????????????????????-----9/17????????????????????????????????????????????????
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
                                                    // } else {
                                                    //     // ??????????????????
                                                    //     if (rowData.buId === '0') {
                                                    //         return <Checkbox disabled checked={false}></Checkbox>
                                                    //     } else {
                                                    //         return <Checkbox disabled checked={true}></Checkbox>
                                                    //     }
                                                    // }


                                                }
                                            },
                                            isInForm: false
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
                                                key: 'actualDate',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
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
                                                // tdEdit:ext1 === '1' ? false : true,
                                                tdEdit: true
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
                                                            return '<a>??????</a>';
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
                                    ],
                                    method: {
                                        save: () => {
                                            let importantLvel = 'unOk';
                                            let value = this.tableGM.form.getFieldsValue();
                                            let partManageList = this.tableGM.form.getFieldsValue().zjTzPartManageList;
                                            let canSaveArry = [];
                                            for (let n = 0; n < partManageList.length; n++) {
                                                if (partManageList[n].actualDate && partManageList[n].zjTzFileList.length <= 0) {//??????????????????????????????????????????
                                                    canSaveArry.push(partManageList[n]);
                                                }
                                                if (partManageList[n].buId === '1') {
                                                    importantLvel = 'ok';
                                                }
                                            }
                                            if (canSaveArry.length > 0) {
                                                Msg.warn('???????????????')
                                            } else {
                                                if (importantLvel === 'unOk') {
                                                    Msg.warn('????????????????????????')
                                                } else {
                                                    this.setState({
                                                        loadingOutSend: true
                                                    })
                                                    this.props.myFetch("saveZjTzPartManageAllList", value).then(({ success, data, message }) => {
                                                        if (success) {
                                                            Msg.success('????????????')
                                                            this.setState({
                                                                loadingOutSend: false
                                                            })
                                                            this.tableGM.refresh();
                                                        } else {
                                                            Msg.error('????????????')
                                                        }
                                                    })
                                                }
                                            }

                                        },
                                        goBack: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            this.props.dispatch(
                                                push(`${mainModule}DesignProcessManagement`)
                                            )
                                        },
                                        lockClick: (obj) => {
                                            const { myFetch } = this.props;
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
                                                                        // this.tableGM.clearSelectedRows();
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
                                            const { myFetch } = this.props;
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
                                                                            // this.tableGM.clearSelectedRows();
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
                                    },
                                    actionBtns: actionBtnsVal
                                }
                            }
                        ]}
                        btns={[

                        ]}
                    />

                </Spin>
            </div>
        );
    }
}

export default index;