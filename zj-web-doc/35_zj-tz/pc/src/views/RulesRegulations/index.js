import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import s from "./style.less";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSend: false,
            loadingSend: false,
            rulesId: ''
        }
    }
    componentDidMount() {

    }
    render() {
        const { visibleSend, loadingSend, rulesId } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                    {...window.RulesRegulationsPage}
                    desc={ ext1 === '1' ? '发布：使数据全平台用户可见；广而告之：将已发布的数据展示至首页' : null}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'rulesId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            isInSearch: false,
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                filter: true,
                                onClick: 'detail',
                                tooltip: 25,
                                width: 300,
                                key: 'title',
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            isInSearch: false,
                            isInTable: false,
                            // table: {
                            //     title: '文号',
                            //     dataIndex: 'symbolNo',
                            //     key: 'symbolNo',
                            //     width:100,
                            //     filter:true,
                            // },
                            form: {
                                field: 'symbolNo',
                                type: 'string',
                                label: '文号',
                                required: true,
                                placeholder: '请输入',
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
                                title: '发文层级',
                                dataIndex: 'releaseRankName',
                                width: 100,
                                key: 'releaseRankName',
                                filter: true
                            },
                            form: {
                                field: 'releaseRankId',
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'faWenCengJi'
                                    }
                                },
                                placeholder: '请输入',
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
                                title: '是否有效文件',
                                dataIndex: 'effectiveId',
                                key: 'effectiveId',
                                width: 125,
                                filter: true,
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
                                type: "radio",
                                required: true,
                                label: "是否有效",
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
                            }
                        },
                        {
                            table: {
                                title: '发文部门',
                                dataIndex: 'departmentName',
                                width: 100,
                                tooltip: 23,
                                key: 'departmentName',
                                filter: true,
                            },
                            isInForm: false,
                            form: {
                                field: 'departmentName',
                                type: 'string',
                                label: '发文部门',
                                required: true,
                                placeholder: '请输入',
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'departmentName',
                                type: 'string',
                                label: '发文部门',
                                required: true,
                                placeholder: '请输入',
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
                        }, {
                            isInTable: false,
                            form: {
                                type: "radio",
                                required: true,
                                label: "是否有效",
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
                                ],
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
                                title: '发布日期',
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                                filter: true,
                            },
                            isInForm: false,
                            form: {
                                field: 'releaseDate',
                                type: 'date',
                                required: true,
                                placeholder: '请输入',
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
                                title: '由此废除的相关文件号',
                                dataIndex: 'abolishSymbolNo',
                                key: 'abolishSymbolNo',
                                width: 180,
                                tooltip: 23,
                                filter: true,
                            },
                            isInForm: false,
                            form: {
                                field: 'abolishSymbolNo',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'abolishSymbolNo',
                                label: '由此废除的相关文件号',
                                // required:true,
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'releaseDate',
                                label: '发文日期',
                                type: 'date',
                                required: true,
                                placeholder: '请输入',
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
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remarks',
                                type: 'textarea',
                                placeholder: '请输入',
                                autoSize: {
                                    minRows: 2,
                                    // maxRows: 10
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registeredTime',
                                type: 'date',
                                label: '登记日期',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                field: 'registeredUser',
                                type: 'string',
                                label: '登记用户',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'releaseName',
                                key: 'releaseName',
                                filter: true,
                                width: 100,
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                hide: true,
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
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '规章制度'
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('已发布的不能修改!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        faBuClick: (obj) => {//发布
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
                                    Msg.warn('已发布的消息不能发布！');
                                } else {
                                    confirm({
                                        title: "确定发布么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteReleaseZjTzRules', obj.selectedRows).then(
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
                                    if (obj.selectedRows[i].releaseId === '0') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未发布的消息不能撤回！');
                                } else {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteRecallZjTzRules', obj.selectedRows).then(
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
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {
                                  
                                        confirm({
                                            title: "确定广而告之到首页么？",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                this.props.myFetch('toHomeShowZjTzRules', obj.selectedRows[0]).then(
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
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
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
                                    Msg.warn('已发布的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzRules', obj.selectedRows).then(
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
                                Msg.warn('请选择数据!');
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
                                        myFetch('batchExportZjTzRulesFile', obj.selectedRows).then(
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
            </div>
        );
    }
}

export default index;