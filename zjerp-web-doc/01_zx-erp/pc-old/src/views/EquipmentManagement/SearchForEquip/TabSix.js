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
                        if (params.paramsData.tabIndex === '5') {
                            return {
                                apiName: 'getZxEqEquipScrapeList',
                                otherParams: {
                                    equipID: params.paramsData.id
                                }
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '批文号',
                                dataIndex: 'approvalNo',
                                key: 'approvalNo',
                                onClick: "detail",
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'orgName',
                                key: 'orgName'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '申请年月',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                format: 'YYYY-MM'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '资产名称',
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
                                title: '资产编号',
                                dataIndex: 'financeNo',
                                key: 'financeNo'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '规格形式',
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
                                title: '制造厂家',
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '规定使用年限',
                                dataIndex: 'requireYear',
                                key: 'requireYear',
                                width:130
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '实际使用年限',
                                dataIndex: 'actualYear',
                                key: 'actualYear',
                                width:130
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '资产原值',
                                dataIndex: 'orginalValue',
                                key: '已提折旧'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '已提折旧',
                                dataIndex: 'deprevalue',
                                key: 'deprevalue'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '资产净值',
                                dataIndex: 'leftvalue',
                                key: 'leftvalue'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '报废后初步处理意见',
                                dataIndex: 'handleway',
                                key: 'handleway',
                                width: 200,
                                tooltip: 20,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                spanForm: 8,
                                optionConfig: { label: 'label', value: 'value', },
                                optionData: [
                                    { label: "留用", value: "0" },
                                    { label: "就近回收", value: "1" },
                                    { label: "委托局回收", value: "2" },
                                    { label: "转让本单位", value: "3" },
                                    { label: "转让外单位", value: "4" }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '报废日期',
                                dataIndex: 'scrapeDate',
                                key: 'scrapeDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: { label: 'label', value: 'value', },
                                optionData: [
                                    { label: '未审核', value: '0' },
                                    { label: '审核通过', value: '1' },
                                    { label: '已上报', value: '2' },
                                    { label: '审核未通过', value: '3' }
                                ],
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