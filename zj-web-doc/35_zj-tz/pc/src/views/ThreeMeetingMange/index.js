import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
import FlowByWorkPlan from './form';
const confirm = Modal.confirm;
const config = {
    
    antd: {
        rowKey: function (row) {
            return row.threeShareholderId
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
            threeShareholderId: '',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
        }
    }
    componentDidMount() {
        
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { proNameId } = this.state;
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
                    fetchConfig={{
                        apiName: 'getZjTzThreeShareholderList',
                        otherParams: {
                            projectId:proNameId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'threeShareholderId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                width:300,
                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                                fieldsConfig: {
                                    field: 'projectId',
                                    type: 'select', 
                                    showSearch: true, 
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: "getZjTzProManageList"
                                    },
                                }
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch: true, 
                                addDisabled: true,
                                disabled: true,
                                editDisabled:true, 
                                initialValue: () => {
                                    return proNameId
                                },
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                                spanForm:12,
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
                            isInSearch: false,
                            table: {
                                title: '期次',
                                onClick: 'detail',
                                width: 160,
                                dataIndex: 'meetNumberName',
                                key: 'meetNumberName',
                                fieldsConfig: {
                                    type: 'string',
                                    field:'meetNumberName'
                                }
                            },
                            isInForm: false,
                            
                        },
                        {
                            isInTable: false,
                            form: {
                                label:'期次',
                                type: 'year',
                                required:true,
                                field: 'yearDate',
                                editDisabled:true,
                                placeholder: '请输入',
                                spanForm:6,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '年',
                                field:'aa',
                                spanForm:1,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 0 },
                                        sm: { span: 0 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label:'',
                                type: 'select',
                                field: 'meetNumberId',
                                editDisabled: true,
                                required:true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName:"getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'huiYiQiCi'
                                    }
                                },
                                placeholder: '请输入',
                                spanForm:5,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 0 },
                                        sm: { span: 0 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                                
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '会议时间',
                                filter:true,
                                format:'YYYY-MM-DD',
                                dataIndex: 'meetDate',
                                key: 'meetDate',
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field:'meetDateList'
                                }
                            },
                            form: {
                                type: 'date',
                                field: 'meetDate',
                                placeholder: '请输入',
                                required: true,
                                spanForm:12,
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
                            }
                        },
                        
                        {
                            table: {
                                title: '会议类型',
                                filter:true,
                                dataIndex: 'meetTypeId',
                                key: 'meetTypeId',
                                type: 'select'
                            },
                            form: {
                                label: '会议类型',
                                type:'select',
                                field: 'meetTypeId',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName:"getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'huiYiLeiXing'
                                    }
                                },
                                spanForm:12,
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
                            }
                        },
                        {
                            table: {
                                title: '会议表决方式',
                                filter:true,
                                dataIndex: 'meetVoteId',
                                key: 'meetVoteId',
                                type:'select',
                            },
                            form: {
                                label: '会议表决方式',
                                type: 'select',
                                field: 'meetVoteId',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName:"getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'huiYiBiaoJueFangShi'
                                    }
                                },
                                spanForm:12,
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
                            }
                        },
                        {
                            table: {
                                title: '原件是否备案',
                                filter: true,
                                width:120,
                                dataIndex: 'originalId',
                                key: 'originalId',
                                type:'select'
                            },
                            form: {
                                type:'select',
                                field: 'originalId',
                                optionData: [
                                    {
                                        label:'是',
                                        value:'1'
                                    },
                                    {
                                        label:'否',
                                        value:'0'
                                    }
                                ],
                                spanForm:12,
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
                            }
                        },
                        {
                            table: {
                                title: '会议地点',
                                width: 150,
                                tooltip:18,
                                dataIndex: 'meetPlace',
                                key: 'meetPlace'
                            },
                            form: {
                                label: '会议地点',
                                type:'textarea',
                                field: 'meetPlace',
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
                        // {
                        //     table: {
                        //         title: '审核状态',
                        //         dataIndex: 'apih5FlowStatus',
                        //         key: 'apih5FlowStatus',
                        //         width: 100,
                        //         render: (data) => {
                        //             if (data === '0') {
                        //                 return '已发起';
                        //             } else if (data === '1') {
                        //                 return '审核中';
                        //             } else if (data === '2') {
                        //                 return '审核结束';
                        //             } else if (data === '3') {
                        //                 return '退回';
                        //             } else if (data === '') {
                        //                 return '未审核';
                        //             } else {
                        //                 return '--';
                        //             }
                        //         }
                        //     },
                        //     isInForm: false
                        // },
                       
                        {
                            isInTable: false,
                            form: {
                                label: '会议通知文件',
                                field: 'zjTzFileList1',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '议案资料',
                                field: 'zjTzFileList2',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '决议及会议纪要',
                                field: 'zjTzFileList3',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
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
                                width: 120,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>明细</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { threeShareholderId, apih5FlowStatus } = obj.rowData;
                                            let apiFlow = '';
                                            if (apih5FlowStatus === '') {
                                                apiFlow = '99';
                                            } else {
                                                apiFlow = apih5FlowStatus;
                                            }
                                            obj.props.dispatch(
                                                push(`${mainModule}ThreeMeetingMangeDetail/${threeShareholderId}/${apiFlow}/${0}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    componentsKey={{
                        FlowByWorkPlan: (obj) => {
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
                            return <FlowByWorkPlan {...obj} flowData={selectedRows[0]} />
                        }
                    }}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (proNameId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].apih5FlowStatus === '1') {
                                Msg.warn('审核中的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else if(obj.selectedRows[0].apih5FlowStatus === '2'){
                                Msg.warn('审核结束的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else {
                                
                            }
                        },
                        delClick:(obj) => {
                            const { myFetch } = obj.props;
                            let apih5FlowStatusArry = [];
                            if (obj.selectedRows.length>0) {
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
                                            myFetch('batchDeleteUpdateZjTzThreeShareholder',obj.selectedRows).then(
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