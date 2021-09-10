import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: window.screen.width * 0.7
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }
    render() {
        let params = this.props
        return (
            <div style={{padding:15}}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={() => {
                        if(params.paramsData.tabIndex === '3'){
                            return {
                                apiName: 'getZxEqMoveSupervisorItemListForTab',
                                otherParams: {
                                    equipID: params.paramsData.id
                                }
                            }
                        }
                        
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单据号',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '调出方',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '调入方',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '调拨日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '签收日期',
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;