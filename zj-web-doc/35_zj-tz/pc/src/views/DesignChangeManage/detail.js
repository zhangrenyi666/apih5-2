import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.designChangeRecordId
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
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName,
            designChangeId: props.match.params.designChangeId || '',
            proNameVal: props.match.params.projectName || '',
            proNameId: props.match.params.projectId || '',
            subprojectInfoId: props.match.params.subprojectInfoId || '',
        }
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { designChangeId, subprojectInfoId, realName, proNameId, proNameVal } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzDesignChangeRecordList',
                        otherParams: {
                            designChangeId: designChangeId
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableBGGL = me;
                    }}
                    method={{
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}DesignChangeManage`)
                            )
                        },
                        editClick: (obj) => {
                            if (obj.selectedRows[0].statusId === '1' || obj.selectedRows[0].statusId === '3') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('请选择未上报/被退回的数据!');
                                this.tableBGGL.clearSelectedRows();
                            } else {
                                this.tableBGGL.clearSelectedRows();
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch, myPublic: { appInfo: { mainModule } } } = obj.props;
                            let statusIdArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1' || obj.selectedRows[i].statusId === '3') {
                                        statusIdArry.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (statusIdArry.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未上报/被退回的数据!');
                                    this.tableBGGL.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzDesignChangeRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        if (obj.state.data.length === obj.selectedRows.length) {
                                                            obj.props.dispatch(
                                                                push(`${mainModule}DesignChangeManage`)
                                                            )
                                                        } else {
                                                            this.tableBGGL.refresh();
                                                            this.tableBGGL.clearSelectedRows();
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
                        // 上报---
                        shangBaoClick1: (obj) => {
                            this.tableBGGL.clearSelectedRows();
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
                                            myFetch('batchReleaseZjTzDesignChangeRecord', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableBGGL.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据！');
                            }
                        },
                        // 上报---
                        shangBaoClick2: (obj) => {
                            this.tableBGGL.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1' || obj.selectedRows[i].statusId === '3') {
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
                                            myFetch('batchReleaseZjTzDesignChangeRecord', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableBGGL.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据！');
                            }
                        },
                        shenHe: (obj) => {
                            this.tableBGGL.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "确定审查通过么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('checkAndFinishZjTzDesignChangeRecord', obj.selectedRows[0]).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableBGGL.refresh();
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
                        // 退回
                        tuiHuiClick: (obj) => {
                            this.tableBGGL.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // 未上报/被退回
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未上报/被退回的消息不能退回！');
                                } else {
                                    confirm({
                                        title: "确定退回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzDesignChangeRecord', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableBGGL.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }

                            } else {
                                Msg.warn('请选择数据！');
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
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designChangeRecordId',
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
                            isInTable: false,
                            form: {
                                field: 'designChangeId',
                                type: 'string',
                                hide: true,
                                initialValue: designChangeId
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
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                label: '项目名称',
                                field: 'projectName',
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '请输入',
                                initialValue: () => {
                                    return proNameVal
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '子项目名称',
                                filter: true,
                                dataIndex: 'subprojectName',
                                key: 'subprojectName'
                            },
                            form: {
                                type: "select",
                                label: "子项目名称",
                                field: "subprojectInfoId",
                                placeholder: "请输入",
                                initialValue: () => {
                                    return subprojectInfoId
                                },
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: proNameId
                                    }
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '设计变更名称',
                                width: 120,
                                tooltip: 23,
                                dataIndex: 'designChangeName',
                                key: 'designChangeName',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                field: 'designChangeName',
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },

                        {
                            table: {
                                title: '设计变更编号',
                                width: 120,
                                dataIndex: 'designChangeNumber',
                                key: 'designChangeNumber'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '变更等级',
                                dataIndex: 'changeLevelName',
                                key: 'changeLevelName',
                                filter:true
                            },
                            isInForm: false,
                            form:{
                                label: '变更等级',
                                field: 'changeLevelId',
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
                                        itemId: 'bianGengDengJi'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '<div>变更金额<br>（万元）</div>',
                                width: 100,
                                dataIndex: 'changeAmount',
                                key: 'changeAmount'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '变更时间',
                                width: 100,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'changeDate',
                                key: 'changeDate'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
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
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更等级',
                                field: 'changeLevelId',
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
                                        itemId: 'bianGengDengJi'
                                    }
                                },
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
                                label: '是否有动态设计机制',
                                field: 'dynamicId',
                                required: true,
                                type: 'select',
                                optionData: [
                                    {
                                        label: "是",
                                        value: "1"
                                    },
                                    {
                                        label: "否",
                                        value: "0"
                                    }
                                ],
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 7 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 17 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更性质',
                                field: 'changeNatureId',
                                type: 'select',
                                placeholder: '请输入',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'bianGengXingZhi'
                                    }
                                },
                                // hide: (obj) => {
                                //     var salaryType = obj.form.getFieldValue('changeLevelId');
                                //     if (salaryType) {
                                //         if (salaryType === '1' || salaryType === '2') {
                                //             return true 
                                //         } else {
                                //             return false
                                //         }
                                //     } else {
                                //         return true
                                //     }

                                // },
                                // dependencies: ["changeLevelId"],
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设计变更名称',
                                field: 'designChangeName',
                                type: 'textarea',
                                placeholder: '请输入',
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
                                label: '设计变更编号',
                                field: 'designChangeNumber',
                                type: 'string',
                                placeholder: '请输入',
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
                                label: '变更增减金额',
                                field: 'changeAmount',
                                type: 'number',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                                placeholder: '请输入',
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
                                label: '变更时间',
                                field: 'changeDate',
                                type: 'date',
                                placeholder: '请选择',
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
                            table: {
                                title: '设计变更内容简要描述',
                                dataIndex: 'content',
                                tooltip: 23,
                                key: 'content'
                            },
                            form: {
                                label: '设计内容简要描述',
                                field: 'content',
                                type: 'textarea',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '有无动态设计机制',
                                width: 140,
                                dataIndex: 'dynamicName',
                                key: 'dynamicName'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'radio',
                                label: '是否完成内部审查流程',
                                field: 'innerCheckId',
                                optionData:[
                                    {
                                        label:'否',
                                        value:'2'
                                    },
                                    {
                                        label:'是',
                                        value:'1'
                                    }
                                ],
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'radio',
                                label: '是否完成合法合规流程',
                                field: 'legalId',
                                optionData:[
                                    {
                                        label:'否',
                                        value:'2'
                                    },
                                    {
                                        label:'是',
                                        value:'1'
                                    }
                                ],
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 16 }
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
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
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
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
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
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: realName
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                filter: true,
                                width: 120,
                                fixed: 'right',
                                dataIndex: 'statusId',
                                key: 'statusId',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'statusId',
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
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '设计变更管理'
                                    }
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                onChange: (val, rowData) => {
                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                        Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                        setTimeout(() => {
                                            this.tableBGGL.qnnForm.form.setFieldsValue({
                                                zjTzFileList: []
                                            })
                                        }, 200)
                                    }
                                }
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;