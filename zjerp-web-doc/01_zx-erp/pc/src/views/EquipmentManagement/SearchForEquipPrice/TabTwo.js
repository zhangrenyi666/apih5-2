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
            <div style={{ padding: 15 }}>
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
                        if(params.paramsData.tabIndex === '1'){
                            return {
                                apiName: 'getZxEqMoveUseOrgItemListForTab',
                                otherParams: {
                                    equipID: params.paramsData.id
                                }
                            }
                        }
                        
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '调令号',
                                dataIndex: 'transferNo',
                                key: 'transferNo',
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
                                title: '调令日期',
                                dataIndex: 'movedate',
                                key: 'movedate',
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
                                title: '接收方',
                                dataIndex: 'acceptOrgName',
                                key: 'acceptOrgName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '原值',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            form: {
                                type: 'number',
                                min: 0,
                                precision: 2,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '净值',
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            form: {
                                type: 'number',
                                min: 0,
                                precision: 2,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '调拨依据',
                                field: 'reason',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 20,
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
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '注意事项',
                                field: 'careinfo',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主管领导',
                                field: 'adminLeader',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '机械主管',
                                field: 'equipAdmin',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '财务科',
                                field: 'finance',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '制表人',
                                field: 'lister',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '请输入',
                                spanForm: 20,
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