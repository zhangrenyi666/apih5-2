import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { goBack } from "connected-react-router";
import { Button, message as Msg, Row, Col, Spin } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
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
    isShowRowSelect: false
};

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            invProId: props.match.params.invProId || '',
            periodValue: props.match.params.periodValue || '',
            projectNameParams: props.match.params.projectNameParams || '',
            dataTop:null,
            dataBottom:null
        }
    }
    componentDidMount() {
        const { invProId, periodValue } = this.state;
        const { myFetch } = this.props;
        myFetch('getZjTzInvXmhgqkMonthlyReportListBasicDataDetails', {
            proId: invProId,
            periodValue: periodValue
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.setState({
                        dataTop: data.children,
                        dataBottom: data.children2
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { invProId, periodValue, projectNameParams } = this.state;
        return (
            <div>
                <Row>
                    <Col span={4}><Button type='dashed' onClick={() => {
                        const { dispatch } = this.props;
                        dispatch(goBack());
                    }}>返回</Button></Col>
                    <Col span={20}><div style={{ width: '100%', marginLeft: '15%', fontSize: '18px', fontWeight: 'bold' }}>{projectNameParams} 项目回购情况表</div></Col>
                </Row>
                <QnnForm
                    style={{ paddingRight: '10px', paddingLeft: '10px' }}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzInvXmhgqkMonthlyReportListBasicDataDetails',
                        otherParams: {
                            proId: invProId,
                            periodValue: periodValue
                        }
                    }}
                    drawerConfig={{
                        width: '600px'
                    }}
                    wrappedComponentRef={(me) => {
                        this.formHasTicket = me;
                    }}
                    formConfig={[
                        {
                            type: 'string',
                            label: '项目编号',
                            disabled: true,
                            field: 'proNum',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '项目名称',
                            disabled: true,
                            field: 'proName',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '管理单位',
                            disabled: true,
                            field: 'comname',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '股权比例',
                            disabled: true,
                            field: 'szgq',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '项目类别',
                            disabled: true,
                            field: 'categoryName',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '项目类型',
                            disabled: true,
                            field: 'typeName',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            label: '预计回购总额',
                            disabled: true,
                            field: 'hgxyMoney',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '合同回购期（年）',
                            field: 'hgq',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'date',
                            disabled: true,
                            label: '回购起始日',
                            field: 'hgxyDate',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '累计回购期（月）',
                            field: 'ljhgq',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '回购比例（%）',
                            field: 'hgjeHgbl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'date',
                            disabled: true,
                            label: '填报时间',
                            field: 'createDate',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'textarea',
                            disabled: true,
                            label: '备注',
                            field: 'remarks',
                            autoSize: {
                                minRows: 1,
                                maxRows: 3
                            },
                            span: 16,
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
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '报表年月',
                            field: 'periodValue',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        }
                    ]}
                />

                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    data={this.state.dataTop}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'id',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '',
                                width:160,
                                dataIndex: 'workName',
                                key: 'workName'
                            }
                        },
                        {
                            table: {
                                title: '本期',
                                dataIndex: 'bq',
                                key: 'bq'
                            }
                        },
                        {
                            table: {
                                title: '本年',
                                dataIndex: 'bn',
                                key: 'bn'
                            }
                        },
                        {
                            table: {
                                title: '开累',
                                dataIndex: 'kl',
                                key: 'kl'
                            }
                        }
                    ]}
                />
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    data={this.state.dataBottom}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'id',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '',
                                width:160,
                                dataIndex: 'workName',
                                key: 'workName'
                            }
                        },
                        {
                            table: {
                                title: '偿还银行贷款',
                                dataIndex: 'chyhdk',
                                key: 'chyhdk'
                            }
                        },
                        {
                            table: {
                                title: '偿还贷款利息',
                                dataIndex: 'chdklx',
                                key: 'chdklx'
                            }
                        },
                        {
                            table: {
                                title: '偿还资本金',
                                dataIndex: 'chzbj',
                                key: 'chzbj'
                            }
                        },
                        {
                            table: {
                                title: '资金集中',
                                dataIndex: 'zjjz',
                                key: 'zjjz'
                            }
                        },
                        {
                            table: {
                                title: '其他',
                                dataIndex: 'qt',
                                key: 'qt'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;