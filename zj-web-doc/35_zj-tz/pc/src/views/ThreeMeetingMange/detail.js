import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { goBack } from "connected-react-router";
const config = {
    antd: {
        rowKey: function (row) {
            return row.threeShareholderBillId
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
            threeShareholderId: props.match.params.threeShareholderId || '',
            apih5FlowStatus: props.match.params.apih5FlowStatus || '',
            isWorkId:props.match.params.isWorkId || ''
        }
    }
    componentDidMount() {
        
    }
    render() {
        const { threeShareholderId,apih5FlowStatus } = this.state;
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
                        apiName: 'getZjTzThreeShareholderBillList',
                        otherParams: () => {
                            if (this.state.isWorkId === '1') {
                                return  {
                                    workId:threeShareholderId
                                }
                            } else {
                                return {
                                    threeShareholderId:threeShareholderId
                                }
                            }
                        }
                    }}
                    formConfig={[
                       {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'threeShareholderBillId',
                                hide: true
                            }
                        }, 
                        {
                            isInTable: false,
                            form: this.state.isWorkId === '1'?{
                                label: '主键id',
                                field: 'workId',
                                initialValue:threeShareholderId,
                                hide: true
                            }:{
                                label: '主键id',
                                field: 'threeShareholderId',
                                initialValue:threeShareholderId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '议案名称',
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
                                title: '决议结果',
                                dataIndex: 'resultName',
                                key: 'resultName'
                            },
                            form: {
                                type: 'select',
                                required:true,
                                field: 'resultId',
                                optionData: [
                                    {
                                        label:'同意',
                                        value:'1'
                                    },
                                    {
                                        label:'不同意',
                                        value:'2'
                                    },
                                    {
                                        label:'其他',
                                        value:'3'
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '会议提出的其他要求',
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
                                title: '决议执行情况具体描述',
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
                                title: '决议完成情况',
                                dataIndex: 'completeName',
                                key: 'completeName'
                            },
                            form: {
                                type:'select',
                                field: 'completeId',
                                optionData: [
                                    {
                                        label:'完成',
                                        value:'1'
                                    },
                                    {
                                        label:'未完成',
                                        value:'2'
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '备注',
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
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
                        },
                        goBack: (obj) => {
                            obj.props.dispatch(goBack())
                        },
                        hideClick: () => {
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