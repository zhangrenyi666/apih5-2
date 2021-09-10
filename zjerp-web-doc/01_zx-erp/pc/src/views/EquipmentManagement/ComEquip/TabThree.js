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
        width: '70%'
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
                        if(params.paramsData.tabIndex === '2'){
                            return {
                                apiName: 'getZxEqEquipDepreciationItemListForTab',
                                otherParams: {
                                    equipID: params.paramsData.id
                                }
                            }
                        }
                        
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '计提单位',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject'
                                },
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '计提日期',
                                dataIndex: 'depreDate',
                                key: 'depreDate',
                                format: 'YYYY-MM-DD'
                            }
                        },
                        {
                            table: {
                                title: '计提月份',
                                dataIndex: 'depreperiodDate',
                                key: 'depreperiodDate',
                                format: 'YYYY-MM'
                            }
                        },
                        {
                            table: {
                                title: '使用部门',
                                dataIndex: 'useOrgID',
                                key: 'useOrgID',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject'
                                },
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '本月折旧',
                                dataIndex: 'depreamout',
                                key: 'depreamout'
                            }
                        },
                        {
                            table: {
                                title: '原值',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                            }
                        },
                        {
                            table: {
                                title: '净值',
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;