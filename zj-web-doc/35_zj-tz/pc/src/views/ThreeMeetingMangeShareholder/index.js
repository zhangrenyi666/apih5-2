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
            return row.threeDirectorId
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
            threeDirectorId: '',
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
                        apiName: 'getZjTzThreeDirectorList',
                        otherParams: {
                            projectId:proNameId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'threeDirectorId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '??????',
                                width: 160,
                                onClick:'detail',
                                dataIndex: 'meetNumberName',
                                key: 'meetNumberName',
                            },
                            form: {
                                type: 'select',
                                required:true,
                                field: 'periodId',
                                editDisabled: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName:"getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'huiYiJieCi'
                                    }
                                },
                                placeholder: '?????????',
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
                                        itemId: 'huiYiQiCiOther'
                                    }
                                },
                                placeholder: '?????????',
                                spanForm:6,
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
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '????????????',
                                filter:true,
                                dataIndex: 'meetTypeId',
                                key: 'meetTypeId',
                                type:'select',
                            },
                            form: {
                                label: '????????????',
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
                                title: '??????????????????',
                                width:120,
                                filter:true,
                                dataIndex: 'meetVoteId',
                                key: 'meetVoteId',
                                type:'select',
                            },
                            form: {
                                label: '??????????????????',
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
                                title: '??????????????????',
                                filter: true,
                                width:120,
                                dataIndex: 'originalId',
                                key: 'originalId',
                                type:'select',
                            },
                            form: {
                                type:'select',
                                field: 'originalId',
                                optionData: [
                                    {
                                        label:'???',
                                        value:'1'
                                    },
                                    {
                                        label:'???',
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
                                title: '????????????',
                                width: 130,
                                tooltip:23,
                                dataIndex: 'meetPlace',
                                key: 'meetPlace'
                            },
                            form: {
                                label: '????????????',
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
                        //         title: '????????????',
                        //         dataIndex: 'apih5FlowStatus',
                        //         key: 'apih5FlowStatus',
                        //         width: 100,
                        //         render: (data) => {
                        //             if (data === '0') {
                        //                 return '?????????';
                        //             } else if (data === '1') {
                        //                 return '?????????';
                        //             } else if (data === '2') {
                        //                 return '????????????';
                        //             } else if (data === '3') {
                        //                 return '??????';
                        //             } else if (data === '') {
                        //                 return '?????????';
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
                                label: '??????????????????',
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
                                label: '????????????',
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
                                label: '?????????????????????',
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
                                title: "??????",
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
                                            return '<a>??????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { threeDirectorId,apih5FlowStatus } = obj.rowData;
                                            let apiFlow = '';
                                            if (apih5FlowStatus === '') {
                                                apiFlow = '99';
                                            } else {
                                                apiFlow = apih5FlowStatus;
                                            }
                                            obj.props.dispatch(
                                                push(`${mainModule}ThreeMeetingMangeShareholderDetail/${threeDirectorId}/${apiFlow}/${0}`)
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
                            //????????????????????????
                            let selectedRows = obj.selectedRows;
                            if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('????????????????????????');
                                return <div />
                            }
                            if (obj.selectedRows[0].workId != "") {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('????????????????????????????????????');
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
                                Msg.warn('???????????????????????????')
                            }
                        },
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].apih5FlowStatus === '1') {
                                Msg.warn('?????????????????????????????????')
                                obj.btnCallbackFn.closeDrawer();
                            } else if(obj.selectedRows[0].apih5FlowStatus === '2'){
                                Msg.warn('????????????????????????????????????')
                                obj.btnCallbackFn.closeDrawer();
                            } else {
                                
                            }
                        },
                        delClick: (obj) => {
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
                                    Msg.warn('???????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzThreeDirector',obj.selectedRows).then(
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