import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg,Modal} from "antd";
import FlowByMonthlyReport from './form';
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjTzDebtList',
    },
    antd: {
        rowKey: function (row) {
            return row.debtId
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
        this.state = {}
    }
    componentDidMount() {

    }
    render() {
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
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'debtId',
                                type: 'string',
                                hide: true,
                            }
                        },
                       
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                onClick:'detail',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            form: {
                                label:'项目名称',
                                field: 'projectId',
                                type: 'select',
                                required: true,
                                editDisabled:true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonthDate',
                                filter:true,
                                width:160,
                                format:'YYYY-MM',
                                key: 'yearMonthDate',
                            },
                            form: {
                                type: 'month',
                                editDisabled:true,
                                field: 'yearMonthDate',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '资产总计（元）',
                                width:220,
                                dataIndex: 'total1',
                                key: 'total1'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '负债和所有者权益总计（元）',
                                dataIndex: 'total2',
                                width:220,
                                key: 'total2'
                            },
                            isInForm:false
                        },
                        {
                            isInTable:false,
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                label: '登记日期',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            isInTable:false,
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '登记用户',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        },
                        {   
                            table: {
                                title: '审核状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '0') {
                                        return '已发起';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核结束';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '') {
                                        return '未审核';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            isInForm: false
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
                                            return '<a>详情</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { debtId,apih5FlowStatus } = obj.rowData;
                                            let apiFlow = '';
                                            if (apih5FlowStatus === '') {
                                                apiFlow = '99';
                                            } else {
                                                apiFlow = apih5FlowStatus;
                                            }
                                            obj.props.dispatch(
                                                push(`${mainModule}MBalanceSheetDetail/${debtId}/${apiFlow}/${0}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    componentsKey={{
                        FlowByMonthlyReport: (obj) => {
                            this.table.clearSelectedRows();
                            //判断是否选中数据
                            let selectedRows = obj.selectedRows;
                            if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('请选择一条数据！');
                                return <div />
                            }
                            if (obj.selectedRows[0].workId != "") {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                return <div />
                            }
                            return <FlowByMonthlyReport {...obj} flowData={selectedRows[0]} />
                        }
                    }}
                    method={{
                        editClick:(obj) => {
                            if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('请选择未审核的数据!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch, myPublic: { appInfo: { mainModule } } } = obj.props;
                            let apih5FlowStatusArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length;i++){
                                    if (obj.selectedRows[i].apih5FlowStatus === '1' || obj.selectedRows[i].apih5FlowStatus === '2') {
                                        apih5FlowStatusArry.push(obj.selectedRows[i].apih5FlowStatus);
                                    }
                                }
                                if (apih5FlowStatusArry.length>0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未审核的数据!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzDebt',obj.selectedRows).then(
                                                ({ success,message }) => {
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
            </div>
        );
    }
}

export default index;