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
                        if(params.paramsData.tabIndex === '4'){
                            return {
                                apiName: 'getZxEqEWorkList',
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
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '使用单位',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: "detail",
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '机械编号',
                                dataIndex: 'equipNo',
                                key: 'equipNo'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '机械名称',
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '技术规格',
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '填写日期',
                                dataIndex: 'bizDate',
                                key: 'bizDate',
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
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                render: (data) => {
                                    if (!data) {
                                        return '运行中';
                                    } else {
                                        return data;
                                    }
                                }
                            },
                            form: {
                                hide: true,
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
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
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
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