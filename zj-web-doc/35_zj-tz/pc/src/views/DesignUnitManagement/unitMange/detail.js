import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { push } from "react-router-redux";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.designAdvistoryUnitRecordId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            designAdvistoryUnitId: props.match.params.designAdvistoryUnitId || '',
            proNameVal: props.match.params.projectName || '',
            proNameId: props.match.params.projectId || '',
            subprojectInfoId: props.match.params.subprojectInfoId || '',
            visibleSend: false,
            loadingSend: false,
            designAdvistoryUnitRecordId: ''
        }
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { designAdvistoryUnitId, proNameVal, proNameId, subprojectInfoId, visibleSend, loadingSend, designAdvistoryUnitRecordId } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzDesignAdvistoryUnitRecordList',
                        otherParams: {
                            designAdvistoryUnitId: designAdvistoryUnitId,
                            typeId: '1',
                            projectId: proNameId
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableHGF = me;
                    }}
                    method={{
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}unitMange`)
                            )
                        },
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].releaseId === '0' || obj.selectedRows[0].releaseId === '2') {
                                    this.tableHGF.clearSelectedRows();
                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                    this.tableHGF.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('?????????????????????');
                            }
                        },
                        pingJia: (obj) => {//???????????????????????????
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].releaseId === '1') {
                                    this.setState({
                                        visibleSend: true,
                                        designAdvistoryUnitRecordId: obj.selectedRows[0].designAdvistoryUnitRecordId
                                    })
                                } else {
                                    Msg.warn('???????????????????????????')
                                }
                            } else {
                                Msg.warn('???????????????????????????')
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch, myPublic: { appInfo: { mainModule } } } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '0' || obj.selectedRows[j].releaseId === '2') { } else {
                                        arry.push(obj.selectedRows[j])
                                    }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('?????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzDesignAdvistoryUnitRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        if (obj.state.data.length === obj.selectedRows.length) {
                                                            obj.props.dispatch(
                                                                push(`${mainModule}unitMange`)
                                                            )
                                                        } else {
                                                            this.tableHGF.refresh();
                                                            this.tableHGF.clearSelectedRows();
                                                        }

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
                        },
                        shangBao1: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '1') {
                                        arry.push(obj.selectedRows[j])
                                    } else { }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzDesignAdvistoryUnitRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableHGF.refresh();
                                                        this.tableHGF.clearSelectedRows();

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
                        },
                        shangBao2: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '1' || obj.selectedRows[j].releaseId === '3') {
                                        arry.push(obj.selectedRows[j])
                                    } else { }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzDesignAdvistoryUnitRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableHGF.refresh();
                                                        this.tableHGF.clearSelectedRows();

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
                        },
                        shenHe: (obj) => {
                            this.tableHGF.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "??????????????????????",
                                    okText: "??????",
                                    cancelText: "??????",
                                    onOk: () => {
                                        myFetch('checkAndFinishZjTzDesignAdvistoryUnitRecord', obj.selectedRows[0]).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableHGF.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                });
                            } else {
                                Msg.warn('????????????????????????');
                            }
                        },
                        tuiHui: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '2' || obj.selectedRows[j].releaseId === '0') {
                                        arry.push(obj.selectedRows[j])
                                    } else { }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('?????????/????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzDesignAdvistoryUnitRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableHGF.refresh();
                                                        this.tableHGF.clearSelectedRows();

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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designAdvistoryUnitId',
                                type: 'string',
                                initialValue: designAdvistoryUnitId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'designAdvistoryUnitRecordId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         field: 'projectId',
                        //         type: 'string',
                        //         hide: true,
                        //         initialValue:proNameId
                        //     }
                        // },
                        // {   
                        //     isInTable:false,
                        //     form: {
                        //         label:'????????????',
                        //         field: 'projectName',
                        //         required: true,
                        //         addDisabled: true,
                        //         disabled: true,
                        //         editDisabled:true,
                        //         type: 'string',
                        //         placeholder: '?????????',
                        //         initialValue:proNameVal
                        //     },
                        // },
                        {
                            isInTable: ext1 === '4' ? true : false,
                            table: {
                                title: '????????????',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '3' ? true : false,
                            table: {
                                title: '????????????',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '2' ? true : false,
                            table: {
                                title: '????????????',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '1' ? true : false,
                            table: {
                                title: '????????????',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                fixed: 'left',
                                type: 'select',
                                filter: true,
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                initialValue: proNameId,
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '?????????',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '???????????????',
                                filter: true,
                                dataIndex: 'subprojectInfoId',
                                key: 'subprojectInfoId',
                                type:'select'
                            },
                            form: {
                                type: "select",
                                showSearch: true,
                                placeholder: "?????????",
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: subprojectInfoId,
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: proNameId
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                filter: true,
                                onClick: 'detail',
                                dataIndex: 'designStageName',
                                key: 'designStageName'
                            },
                            form: {
                                label: '????????????',
                                field: 'designStageId',
                                type: 'select',
                                placeholder: '?????????',
                                required: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'sheJiJieDuan'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'unitName',
                                key: 'unitName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'selectModeName',
                                key: 'selectModeName'
                            },
                            form: {
                                label: '????????????',
                                field: 'selectModeId',
                                type: 'select',
                                placeholder: '?????????',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'xuanDiFangShi'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                width: 300,
                                tooltip: 30,
                                dataIndex: 'content',
                                key: 'content'
                            },
                            form: {
                                label: '????????????',
                                field: 'content',
                                type: 'textarea',
                                placeholder: '?????????',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '??????????????????/??????(??????)',
                                dataIndex: 'amount1',
                                key: 'amount1',
                                // render: "bind:_divide::10000::2",
                            },
                            form: {
                                label: '??????????????????/??????',
                                field: 'amount1',
                                type: 'number',
                                // suffix: '(??????)',
                                formatter: value => value ? `${value.replace ? value.replace(/(???|???)/ig, '') : value}??????` : value,
                                parser: value => value ? value.replace(/(???|???)/ig, '') : value,
                                placeholder: '?????????',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '?????????(??????)',
                                dataIndex: 'amount2',
                                key: 'amount2',
                                // render: "bind:_divide::10000::2",
                            },
                            form: {
                                label: '?????????',
                                field: 'amount2',
                                type: 'number',
                                // suffix: '(??????)',
                                formatter: value => value ? `${value.replace ? value.replace(/(???|???)/ig, '') : value}??????` : value,
                                parser: value => value ? value.replace(/(???|???)/ig, '') : value,
                                placeholder: '?????????',
                                // required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'designAdvistoryUnitStandardId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                optionConfig: {
                                    label: 'unitName',
                                    value: 'designAdvistoryUnitStandardId',
                                    linkageFields: {
                                        "orgCode": "orgCode",
                                        "zjTzQualityList": "zjTzQualityList"
                                    }
                                },
                                fetchConfig: {
                                    apiName: "getZjTzDesignAdvistoryUnitStandardList"
                                },
                                onChange: (val, rowData) => {
                                    if (this.formIn) {
                                        this.formIn.refresh();
                                    }
                                    this.setState({
                                        InformData: rowData.itemdata.zjTzQualityList
                                    }, () => {
                                        // this.table.qnnForm.refresh();   
                                    })

                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'orgCode',
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '?????????',
                                required: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'typeId',
                                type: 'select',
                                placeholder: '?????????',
                                required: true,
                                initialValue: '1',
                                hide: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'danWeiLeiXing'
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ??????????????????
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zjTzQualityList',
                                colStyle: {
                                    paddingLeft: 12
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'typeId',
                                        size: 'small'
                                    },
                                    drawerConfig: {
                                        width: '1000px'
                                    },
                                    limit: 999,
                                    curPage: 1,
                                    paginationConfig: false,
                                    firstRowIsSearch: false,
                                    isShowRowSelect: false,
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
                                        this.tables = me;
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'typeId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'majorTypeId',
                                                key: 'majorTypeId',
                                                tdEdit: false,
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'majorTypeId',
                                                placeholder: '?????????',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'zhuanYeLeiXing'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'correspondQualityId',
                                                key: 'correspondQualityId',
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'correspondQualityId',
                                                placeholder: '?????????',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'duiYingZiZhi'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'zjTzFileList',
                                                key: 'zjTzFileList',
                                                tdEdit: false,
                                                render: (data, rowData) => {
                                                    if (data) {
                                                        if (rowData.zjTzFileList.length > 0) {
                                                            return rowData.zjTzFileList[0].name
                                                        } else {
                                                            return ''
                                                        }
                                                    } else {
                                                        return ''
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    if (obj.rowData && obj.rowData.zjTzFileList && obj.rowData.zjTzFileList[0] && obj.rowData.zjTzFileList[0].url) {
                                                        window.open(obj.rowData.zjTzFileList[0].url)
                                                    }
                                                }
                                            },
                                            form: {
                                                field: 'zjTzFileList',
                                                type: 'files',
                                                max: 1,
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
                                        }
                                    ],
                                    actionBtns: []
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????????????????',
                                field: 'proLeader',
                                type: 'string',
                                placeholder: '?????????',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????????????????',
                                field: 'proLeaderTel',
                                type: 'phone',
                                placeholder: '?????????',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                filter: true,
                                dataIndex: 'releaseId',
                                key: 'releaseId',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'releaseId',
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
                                    },
                                    {
                                        label: "??????????????????",
                                        value: "3"
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'evaluateOrderName',
                                key: 'evaluateOrderName'
                            },
                            form: {
                                label: '????????????',
                                field: 'evaluateOrderId',
                                type: 'select',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'pingJiaDengJi'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'textarea',
                                label: '??????',
                                field: 'remarks',
                                placeholder: '?????????',
                                required: false,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createTime',
                                type: 'date',
                                label: '????????????',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createUserName',
                                type: 'string',
                                label: '????????????',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
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
                            },
                        }
                    ]}
                />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="??????"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
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
                                    field: 'designAdvistoryUnitId',
                                    type: 'string',
                                    initialValue: designAdvistoryUnitId,
                                    hide: true,
                                },
                                {
                                    field: 'designAdvistoryUnitRecordId',
                                    type: 'string',
                                    initialValue: designAdvistoryUnitRecordId,
                                    hide: true,
                                },
                                {
                                    label: '????????????',
                                    field: 'evaluateOrderId',
                                    type: 'select',
                                    addDisabled: true,
                                    editDisabled: true,
                                    placeholder: '?????????',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pingJiaDengJi'
                                        }
                                    }
                                }
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
                                        this.props.myFetch('evaluateZjTzDesignAdvistoryUnitRecord', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableHGF.refresh();
                                                    this.tableHGF.clearSelectedRows();
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