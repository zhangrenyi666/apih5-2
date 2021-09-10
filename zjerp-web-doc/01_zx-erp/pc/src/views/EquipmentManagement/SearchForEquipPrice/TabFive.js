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
    isShowRowSelect: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
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
                        if(params.paramsData.tabIndex === '4'){
                            return {
                                apiName: 'getZxEqAssetSellItemList',
                                otherParams: {
                                    equipID: params.paramsData.id
                                }
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '调出方',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                onClick:'detail'
                            },
                            form: {
                                type:'string',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '接收方名称',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName',
                            },
                            form: {
                                type:'string',
                                spanForm:12
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
                                type:'date',
                                spanForm:12
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
                                type:'date',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '资产原值',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            form: {
                                type:'number',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '已提折旧',
                                dataIndex: 'deprevalue',
                                key: 'deprevalue'
                            },
                            form: {
                                type:'string',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '资产净值',
                                dataIndex: 'leftvalue',
                                key: 'leftvalue'
                            },
                            form: {
                                type:'number',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '转让价值',
                                dataIndex: 'sellValue',
                                key: 'sellValue'
                            },
                            form: {
                                type:'number',
                                spanForm:12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: window.configs.domain + "upload"
                                },
                                spanForm: 21,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;