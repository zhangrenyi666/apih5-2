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
                                title: '??????',
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
                                placeholder: '?????????'
                            },
                        },
                        {   
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????'
                            },
                        },
                        {   
                            table: {
                                title: '???????????????',
                                width: 200,
                                tooltip: 18,
                                dataIndex: 'subprojectName',
                                key: 'subprojectName'
                            },
                            form: {
                                field: 'subprojectInfoId',
                                type: 'select',
                                placeholder: '?????????',
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
                                title: '????????????',
                                dataIndex: 'skillTypeName',
                                key: 'skillTypeName',
                                tooltip: 18,
                                width:200,
                            },
                            form: {
                                label:'??????????????????',
                                field: 'skillTypeId',
                                type: 'select',
                                showSearch: true,
                                multiple: true,
                                required: true,
                                pullJoin:true,
                                placeholder: '?????????',
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
                                title: '???????????????',
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
                                placeholder: '?????????'
                            },
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
                            form:{
                                field: 'createTime',
                                type: 'date',
                                label:'????????????',
                                addDisabled: true,
                                editDisabled:true,
                                placeholder: '?????????',
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
                                label:'????????????',
                                addDisabled: true,
                                editDisabled:true,
                                placeholder: '?????????',
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
                                title: '??????',
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
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>????????????</a>';
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
                                    obj.btnCallbackFn.msg.warning('???????????????????????????');
                                } else {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.warning('???????????????????????????');
                            }
                        },
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].releaseId === '0' || obj.selectedRows[0].releaseId === '2') {
                                    this.table.clearSelectedRows();
                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('?????????????????????');
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
                                    Msg.warn('????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
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
                                    Msg.warn('????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
                            }
                        },
                        shenHe: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "??????????????????????",
                                    okText: "??????",
                                    cancelText: "??????",
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
                                Msg.warn('????????????????????????');
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
                                    Msg.warn('?????????/????????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
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
                                    Msg.warn('?????????????????????????????????')
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
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