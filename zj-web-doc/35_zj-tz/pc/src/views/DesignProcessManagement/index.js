import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    
    antd: {
        rowKey: function (row) {
            return row.designFlowId
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
const red = require('./red.png');
const grey = require('./grey.png');
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName :'',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : ''
        }
    }
    componentDidMount() {}
    render() {
        const { proNameVal, proNameId } = this.state;
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
                    fetchConfig={{
                        apiName: 'getZjTzDesignFlowList',
                        otherParams: {
                            projectId:proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designFlowId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                hide: true,
                                initialValue:proNameId
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
                                title: '预警',
                                dataIndex: 'a',
                                key: 'a',
                                width:70,
                                fixed: 'left',
                                align:'center',
                                onClick: (obj) => {
                                    const { mainModule } = obj.props.myPublic.appInfo;
                                    const { designFlowId } = obj.rowData;
                                    obj.props.dispatch(
                                        push(`${mainModule}DesignProcessManagementYujingDetail/${designFlowId}`)
                                    )
                                },
                                render: (data, rowData) => {
                                    if(rowData.warnFlag === 'red'){
                                        return '<img style="height:19px" alt="" src=' + red + ' />'
                                    } else {
                                        return '<img style="height:19px" alt="" src=' + grey + ' />'
                                    }
                                }
                            },
                            isInForm:false,
                            form: {
                                field: 'a',
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {   
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                tooltip: 18,
                                width:200,
                                fixed: 'left'
                            },
                            form: {
                                field: 'projectName',
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled:true,
                                initialValue: () => {
                                    return proNameVal
                                },
                                placeholder: '请输入'
                            },
                        },
                        {   
                            table: {
                                title: '子项目名称',
                                width: 200,
                                tooltip: 18,
                                dataIndex: 'subprojectName',
                                key: 'subprojectName'
                            },
                            form: {
                                field: 'subprojectInfoId',
                                type: 'select',
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName:"getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: proNameId
                                    }
                                }
                            },
                        },
                        {   
                            table: {
                                title: '工程类别',
                                dataIndex: 'skillTypeName',
                                key: 'skillTypeName',
                                tooltip: 18,
                                width:200,
                            },
                            form: {
                                label:'专业技术类别',
                                field: 'skillTypeId',
                                type: 'select',
                                showSearch: true,
                                multiple: true,
                                required: true,
                                pullJoin:true,
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName:"getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'ZhuanYeJiShuLeiBie'
                                    }
                                }
                            },
                        },
                        {   
                            table: {
                                title: '中标时状态',
                                dataIndex: 'designPartName',
                                width: 160,
                                tooltip:23,
                                key: 'designPartName'
                            },
                            form: {
                                field: 'designPartName',
                                type: 'string',
                                addDisabled: true,
                                editDisabled:true,
                                placeholder: '请输入'
                            },
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
                            form:{
                                field: 'createTime',
                                type: 'date',
                                label:'创建日期',
                                addDisabled: true,
                                editDisabled:true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 6  }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
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
                            form:{
                                field: 'createUserName',
                                type: 'string',
                                label:'创建用户',
                                addDisabled: true,
                                editDisabled:true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 6  }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                filter:true,
                                dataIndex: 'releaseId',
                                key: 'releaseId',
                                type: 'select',
                                width:100,
                            },
                            isInForm:false,
                            form: {
                                type: 'select',
                                field:'releaseId',
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
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>环节管理</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { designFlowId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}DesignProcessManagementDetail/${designFlowId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (proNameId) {
                                if (proNameId === 'all') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                                } else {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                            }
                        },
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].releaseId === '0' || obj.selectedRows[0].releaseId === '2') {
                                    this.table.clearSelectedRows();
                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已上报的不能修改!');
                                    this.table.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('请选择一条数据');
                            } 
                        },
                        shangBao1: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++){
                                    if (obj.selectedRows[j].releaseId === '1') {
                                        arry.push(obj.selectedRows[j])
                                    } else {}
                                }
                                if (arry.length>0) {
                                    Msg.warn('已上报的数据不允许上报！')
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzDesignFlow',obj.selectedRows).then(
                                                ({ success,message }) => {
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
                        shangBao2: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++){
                                    if (obj.selectedRows[j].releaseId === '1' || obj.selectedRows[j].releaseId === '3') {
                                        arry.push(obj.selectedRows[j])
                                    } else {}
                                }
                                if (arry.length>0) {
                                    Msg.warn('已上报的数据不允许上报！')
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzDesignFlow',obj.selectedRows).then(
                                                ({ success,message }) => {
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
                        shenHe: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "确定审查通过么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('checkAndFinishZjTzDesignFlow', obj.selectedRows[0]).then(
                                            ({ data, success, message }) => {
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
                            } else {
                                Msg.warn('请选择一条数据！');
                            }
                        },
                        tuiHui: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++){
                                    if (obj.selectedRows[j].releaseId === '2' || obj.selectedRows[j].releaseId === '0') {
                                        arry.push(obj.selectedRows[j])
                                    } else {}
                                }
                                if (arry.length>0) {
                                    Msg.warn('已上报/被退回的数据不允许退回！')
                                } else {
                                    confirm({
                                        title: "确定退回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzDesignFlow',obj.selectedRows).then(
                                                ({ success,message }) => {
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
                        delClick: (obj) => {
                            const { myFetch, myPublic: { appInfo: { mainModule } } } = obj.props;
                            let arry = [];
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length;j++){
                                    if (obj.selectedRows[j].releaseId === '0' || obj.selectedRows[j].releaseId === '2') {} else {
                                        arry.push(obj.selectedRows[j])
                                    }
                                }
                                if (arry.length>0) {
                                    Msg.warn('未上报的数据允许删除！')
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzDesignFlow',obj.selectedRows).then(
                                                ({ success,message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        if (obj.state.data.length === obj.selectedRows.length) {
                                                            obj.props.dispatch(
                                                                push(`${mainModule}ConsultingUnitManage`)
                                                            )
                                                        } else {
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
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