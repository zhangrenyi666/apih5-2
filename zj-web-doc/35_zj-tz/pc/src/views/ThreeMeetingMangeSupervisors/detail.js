import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { goBack } from "connected-react-router";
const config = {
    antd: {
        rowKey: function (row) {
            return row.threeSupervisorBillId
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
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    }
};

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            threeSupervisorId:props.match.params.threeSupervisorId || '',
            apih5FlowStatus: props.match.params.apih5FlowStatus || '',
            isWorkId:props.match.params.isWorkId || ''
        }
    }
    componentDidMount() {
        
    }
    render() {
        const { threeSupervisorId,apih5FlowStatus } = this.state;
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
                        apiName: 'getZjTzThreeSupervisorBillList',
                        otherParams: () => {
                            if (this.state.isWorkId === '1') {
                                return  {
                                    workId:threeSupervisorId
                                }
                            } else {
                                return {
                                    threeSupervisorId:threeSupervisorId
                                }
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '??????id',
                                field: 'threeSupervisorBillId',
                                hide: true
                            }
                        }, 
                        {
                            isInTable: false,
                            form: this.state.isWorkId === '1'?{
                                label: '??????id',
                                field: 'workId',
                                initialValue:threeSupervisorId,
                                hide: true
                            }:{
                                label: '??????id',
                                field: 'threeSupervisorId',
                                initialValue:threeSupervisorId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'billName',
                                key: 'billName'
                            },
                            form: {
                                type: 'string',
                                required:true,
                                field: 'billName'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resultName',
                                key: 'resultName'
                            },
                            form: {
                                type:'select',
                                field: 'resultId',
                                required:true,
                                optionData: [
                                    {
                                        label:'??????',
                                        value:'1'
                                    },
                                    {
                                        label:'?????????',
                                        value:'2'
                                    },
                                    {
                                        label:'??????',
                                        value:'3'
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                width: 200,
                                tooltip:23,
                                dataIndex: 'otherRequire',
                                key: 'otherRequire'
                            },
                            form: {
                                type:'textarea',
                                field: 'otherRequire'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                width: 200,
                                tooltip:23,
                                dataIndex: 'specificDesc',
                                key: 'specificDesc'
                            },
                            form: {
                                type:'textarea',
                                field: 'specificDesc'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 140,
                                tooltip:23,
                                dataIndex: 'completeName',
                                key: 'completeName'
                            },
                            form: {
                                type:'select',
                                field: 'completeId',
                                optionData: [
                                    {
                                        label:'??????',
                                        value:'1'
                                    },
                                    {
                                        label:'?????????',
                                        value:'2'
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remarks',
                                key: 'remarks'
                            },
                            form: {
                                type:'textarea',
                                field: 'remarks'
                            }
                        },
                       
                    ]}
                    method={{
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                        },
                        goBack:(obj) => {
                            obj.props.dispatch(goBack())
                        },
                        hideClick:() => {
                            if (apih5FlowStatus === '1'||apih5FlowStatus === '2') {
                                return true
                            } else {
                                return false
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