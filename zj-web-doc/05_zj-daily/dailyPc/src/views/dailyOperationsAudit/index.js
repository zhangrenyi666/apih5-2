import React, { Component } from 'react';
import QnnTable from 'apih5/modules/qnn-table';
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
const { confirm } = Modal;
const config = {
    fetchConfig: {
        apiName: "getZjDailyProFillList"
    },
    antd: {
        rowKey: "proFillId",
        size: "small"
    },
    drawerConfig: {
        width: 1000
    },
    firstRowIsSearch: false,
}
class Index extends Component {
    render() {
        const { userKey } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div style={{ height: '100%', overflow: "hidden" }}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.tables = me;
                    }}
                    actionBtns = {[
                        {
                            name: 'pass',
                            type: 'primary',
                            label: '通过',
                            onClick:(obj) => {
                                if (obj.selectedRows.length) {
                                    confirm({
                                        title: "是否确定通过数据？",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('approveAgreeZjDailyProFill', [ ...obj.selectedRows ]).then(({ success, data, message }) => {
                                                if (success) {
                                                    this.tables.refresh();
                                                    this.tables.clearSelectedRows();
                                                    Msg.success(message);
                                                } else {
                                                    this.tables.clearSelectedRows();
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    });
                
                                } else {
                                    Msg.error("请能选择数据！");
                                }
                            }
                        },
                        {
                            name: 'reject',
                            type: 'danger',
                            label: '驳回',
                            onClick:(obj) => {
                                if (obj.selectedRows.length) {
                                    confirm({
                                        title: "是否确定驳回数据？",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('approveRejectZjDailyProFill', [ ...obj.selectedRows ]).then(({ success, data, message }) => {
                                                if (success) {
                                                    this.tables.refresh();
                                                    this.tables.clearSelectedRows();
                                                    Msg.success(message);
                                                } else {
                                                    this.tables.clearSelectedRows();
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    });
                
                                } else {
                                    Msg.error("请能选择数据！");
                                }
                            }
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'proFillId',
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'proName',
                                key: 'proName',
                                width: 200,
                                tooltip: 13,
                                onClick: 'detail',
                                fixed: "left"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '收费站',
                                dataIndex: 'tollStation',
                                key: 'tollStation',
                                width: 150,
                                tooltip: 10,
                                fixed: "left"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '<center>填报日期</center>',
                                dataIndex: 'fillDate',
                                key: 'fillDate',
                                width: 150,
                                // fieldsConfig: {
                                //     field: "fillDate",
                                //     type: "date",
                                // },
                                noHaveSearchInput: true,
                                render: (data) => {
                                    return moment(data).format('YYYY-MM-DD')
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'linkage',
                                fetchConfig: {//只有模式为0才写到这
                                    apiName: 'getZjDailyProInfoListByUserKey',
                                    otherParams: {
                                        userKey: userKey
                                    }
                                },
                                children: {//所有配置见qnn-form
                                    form: {
                                        label: '项目名称',
                                        field: 'proInfoId',
                                        type: 'select',
                                        required: true,
                                        placeholder: '请选择',
                                        optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                                            value: 'proInfoId',
                                            label: 'proName',
                                            children: 'proDetailList'
                                        },
                                    },
                                    children: {
                                        form: {
                                            label: '收费站',
                                            field: 'proDetailId',
                                            placeholder: '请选择',
                                            required: true,
                                            type: 'select',
                                            optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                                                value: 'proDetailId',
                                                label: 'tollStation'
                                            },
                                        }
                                    }
                                },
                                formItemLayoutForm: {
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
                                label: '填报日期',
                                field: 'fillDate',
                                type: 'date',
                                placeholder: '请选择',
                                required: true,
                                formItemLayoutForm: {
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
                            table: {
                                title: '现金',
                                dataIndex: '现金',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'cash1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'cash2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '现金客车',
                                field: 'cash1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '现金货车',
                                field: 'cash2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '现金合计',
                                dataIndex: 'cashTotal',
                                key: 'cashTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '非现金',
                                dataIndex: 'unCash',
                                key: 'unCash',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                min: 0,
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '合计金额',
                                dataIndex: 'cashAllTotal',
                                key: 'cashAllTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                            table: {
                                title: '入口车次',
                                dataIndex: '入口车次',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'entr1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'entr2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '入口车次客车',
                                field: 'entr1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '入口车次货车',
                                field: 'entr2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '入口车流量合计',
                                dataIndex: 'entrTotal',
                                key: 'entrTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                            table: {
                                title: '出口车次',
                                dataIndex: '出口车次',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'export1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'export2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '出口车次客车',
                                field: 'export1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '出口车次货车',
                                field: 'export2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '出口车流量合计',
                                dataIndex: 'exportTotal',
                                key: 'exportTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                            table: {
                                title: '绿通车',
                                dataIndex: '绿通车',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'green1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'green2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '绿通车客车',
                                field: 'green1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '绿通车货车',
                                field: 'green2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '合计车流量',
                                dataIndex: 'allTotal',
                                key: 'allTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                        // {
                        //     table: {
                        //         title: '发送状态',
                        //         dataIndex: 'sendFlag',
                        //         width: 80,
                        //         fixed: "right",
                        //         render: (data) => {
                        //             if (data === '0') {
                        //                 return '未发送';
                        //             } else if (data === '1') {
                        //                 return '已发送';
                        //             } else {
                        //                 return '-';
                        //             }
                        //         }
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'approveStatusName',
                                width: 80,
                                fixed: "right"
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>)
    }
}
export default Index;