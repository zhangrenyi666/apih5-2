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
    isShowRowSelect: true,
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
                            typeId: '2',
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
                                push(`${mainModule}ConsultingUnitManage`)
                            )
                        },
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].releaseId === '0' || obj.selectedRows[0].releaseId === '2') {
                                    this.tableHGF.clearSelectedRows();
                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已上报的不能修改!');
                                    this.tableHGF.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('请选择一条数据');
                            }
                        },
                        pingJia: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].releaseId === '1') {
                                    this.setState({
                                        visibleSend: true,
                                        designAdvistoryUnitRecordId: obj.selectedRows[0].designAdvistoryUnitRecordId
                                    })
                                } else {
                                    Msg.warn('请选择已上报数据！')
                                }
                            } else {
                                Msg.warn('只能选择一条数据')
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
                                    Msg.warn('未上报的数据允许删除！')
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzDesignAdvistoryUnitRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        if (obj.state.data.length === obj.selectedRows.length) {
                                                            obj.props.dispatch(
                                                                push(`${mainModule}ConsultingUnitManage`)
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
                                Msg.warn('请选择数据!');
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
                                    Msg.warn('已上报的数据不允许上报！')
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据!');
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
                                    Msg.warn('已上报的数据不允许上报！')
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据!');
                            }
                        },
                        shenHe: (obj) => {
                            this.tableHGF.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "确定审查通过么?",
                                    okText: "确认",
                                    cancelText: "取消",
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
                                Msg.warn('请选择一条数据！');
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
                                    Msg.warn('已上报/被退回的数据不允许退回！')
                                } else {
                                    confirm({
                                        title: "确定退回么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据!');
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
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                hide: true,
                                initialValue: proNameId
                            }
                        },
                        {
                            isInTable: ext1 === '4' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
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
                                title: '是否更新',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
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
                                title: '是否更新',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
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
                                title: '是否更新',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                onClick: 'detail',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            form: {
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '请输入',
                                initialValue:() => {
                                    return proNameVal
                                }
                            },
                        },
                        {
                            table: {
                                title: '子项目名称',
                                filter: true,
                                dataIndex: 'subprojectInfoId',
                                key: 'subprojectInfoId',
                                type:'select'
                            },
                            form: {
                                type: "select",
                                showSearch: true,
                                placeholder: "请输入",
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
                            isInTable: false,
                            form: {
                                label: '单位类型',
                                field: 'typeId',
                                type: 'select',
                                placeholder: '请选择',
                                required: true,
                                initialValue: '2',
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
                            table: {
                                title: '设计阶段',
                                filter: true,
                                dataIndex: 'designStageName',
                                key: 'designStageName'
                            },
                            form: {
                                label: '设计阶段',
                                field: 'designStageId',
                                type: 'select',
                                placeholder: '请选择',
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
                                title: '咨询单位全称',
                                dataIndex: 'unitName',
                                key: 'unitName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '咨询单位选定方式',
                                dataIndex: 'selectModeName',
                                key: 'selectModeName'
                            },
                            form: {
                                label: '选定方式',
                                field: 'selectModeId',
                                type: 'select',
                                placeholder: '请选择',
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
                                title: '对应设计工作内容',
                                width: 300,
                                tooltip: 30,
                                dataIndex: 'content',
                                key: 'content'
                            },
                            form: {
                                label: '工作内容',
                                field: 'content',
                                type: 'textarea',
                                placeholder: '请输入',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '预估成效(万元)',
                                dataIndex: 'amount1',
                                key: 'amount1',
                                // render: "bind:_divide::10000::2",
                            },
                            form: {
                                label: '预估成效',
                                field: 'amount1',
                                type: 'number',
                                suffix: '(万元)',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                                placeholder: '请输入',
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
                                title: '咨询费(万元)',
                                dataIndex: 'amount2',
                                key: 'amount2',
                                // render: "bind:_divide::10000::2",
                            },
                            form: {
                                label: '咨询费',
                                field: 'amount2',
                                type: 'number',
                                suffix: '(万元)',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                                placeholder: '请输入',
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
                                label: '咨询单位名称',
                                field: 'designAdvistoryUnitStandardId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                placeholder: '请输入',
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
                                label: '组织机构代码',
                                field: 'orgCode',
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '请输入',
                                required: true
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            咨询单位资质
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
                                                label: '主键id',
                                                field: 'typeId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '专业类型',
                                                dataIndex: 'majorTypeId',
                                                key: 'majorTypeId',
                                                tdEdit: false,
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'majorTypeId',
                                                placeholder: '请选择',
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
                                                title: '对应资质',
                                                dataIndex: 'correspondQualityId',
                                                key: 'correspondQualityId',
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'correspondQualityId',
                                                placeholder: '请选择',
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
                                                title: '资质附件',
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
                                                        name: '咨询单位管理'
                                                    }
                                                },
                                                showDownloadIcon: true,//是否显示下载按钮
                                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                                
                                                onChange: (val, rowData) => {
                                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                        Msg.warn('不允许上传rar、zip、7z格式的文件！')
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
                                    actionBtns: [
                                        // {
                                        //     name: "addRow",
                                        //     icon: "plus",
                                        //     type: "primary",
                                        //     label: "新增行"
                                        // },
                                        // {
                                        //     name: 'del',
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: '删除'
                                        // }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '咨询单位项目负责人',
                                field: 'proLeader',
                                type: 'string',
                                placeholder: '请输入',
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
                                label: '咨询单位负责人电话',
                                field: 'proLeaderTel',
                                type: 'phone',
                                placeholder: '请输入',
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
                                title: '状态',
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
                                    },
                                    {
                                        label: "托管项目上报",
                                        value: "3"
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '评价等级',
                                dataIndex: 'evaluateOrderName',
                                key: 'evaluateOrderName'
                            },
                            form: {
                                label: '评价等级',
                                field: 'evaluateOrderId',
                                type: 'select',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                label: '备注',
                                field: 'remarks',
                                placeholder: '请输入',
                                required: false,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createTime',
                                type: 'date',
                                label: '创建日期',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                label: '创建用户',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '咨询单位管理'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                onChange: (val, rowData) => {
                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                        Msg.warn('不允许上传rar、zip、7z格式的文件！')
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
                    title="评价"
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
                                    label: '评价等级',
                                    field: 'evaluateOrderId',
                                    type: 'select',
                                    addDisabled: true,
                                    editDisabled: true,
                                    placeholder: '请输入',
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
                                    label: '取消',
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
                                    label: '上报',
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