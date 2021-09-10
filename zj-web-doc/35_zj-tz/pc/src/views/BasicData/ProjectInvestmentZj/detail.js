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
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.4
        }
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
            dataTop: null,
            dataBottom: null
        }
    }
    componentDidMount() {
        const { invProId, periodValue } = this.state;
        const { myFetch } = this.props;
        myFetch('getZjTzInvXmzjqkMonthlyReportListBasicDataDetails', {
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
                    <Col span={20}><div style={{ width: '100%', marginLeft: '10%', fontSize: '18px', fontWeight: 'bold' }}>{projectNameParams} 项目资金情况表</div></Col>
                </Row>
                <QnnForm
                    style={{ paddingRight: '10px', paddingLeft: '10px' }}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzInvXmzjqkMonthlyReportListBasicDataDetails',
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            label: '批复类型',
                            disabled: true,
                            field: 'pfname',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '投资总额',
                            field: 'ztze',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '资本金比例(%)',
                            field: 'zbjbl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '可占用局资本金',
                            field: 'kzyjzbj',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '应回流资金',
                            field: 'yhlzj',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '资本金占用考核指标（%）',
                            field: 'zbjzykhzb',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 12 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '实际投入自有资金占完成权益投资比例（%）',
                            field: 'sjtrzyzjZwcqytzbl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '实际投入自有资金占投资总额比例（%）',
                            field: 'sjtrzyzjZtzzebl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 17 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 7 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '尚需回流的资金',
                            field: 'sxhlzj',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'string',
                            disabled: true,
                            label: '融资是否落实',
                            field: 'sfrzls',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
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
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: 'textarea',
                            disabled: true,
                            label: '备注',
                            field: 'bz',
                            autoSize: {
                                minRows: 1,
                                maxRows: 3
                            },
                            span: 24,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            }
                        },

                    ]}
                />
                <QnnTable
                     {...this.props}
                     fetch={this.props.myFetch}
                     upload={this.props.myUpload}
                     headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.tableA = me;
                    }}
                    data={this.state.dataTop}
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
                                title: '资本金',
                                children: [
                                    {
                                        dataIndex: 'zbjtotal',
                                        title: "小计",
                                        width: 110
                                    },
                                    {
                                        title: '自有资金',
                                        dataIndex: 'zbjzyzj',
                                        width: 110
                                    },
                                    {
                                        title: '基金',
                                        dataIndex: 'zbjjj',
                                        width: 130
                                    },
                                    {
                                        title: '一公局认购基金',
                                        dataIndex: 'zbjygjrgjj',
                                        width: 110
                                    },
                                    {
                                        title: '其他股东等',
                                        dataIndex: 'zbjqtgd',
                                        width: 110
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '融资',
                                children: [
                                    {
                                        dataIndex: 'rztotal',
                                        title: "小计",
                                        width: 110
                                    },
                                    {
                                        title: '银行贷款',
                                        dataIndex: 'rzyhjk',
                                        width: 110
                                    },
                                    {
                                        title: '基金',
                                        dataIndex: 'rzjj',
                                        width: 130
                                    },
                                    {
                                        title: '一公局认购基金',
                                        dataIndex: 'rzygjrgjj',
                                        width: 110
                                    },
                                    {
                                        title: '信托',
                                        dataIndex: 'rzxt',
                                        width: 110
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '政府补贴资金',
                                width: 150,
                                dataIndex: 'zfbt',
                                key: 'zfbt'
                            }
                        },
                        {
                            table: {
                                title: '其他资金',
                                width: 150,
                                dataIndex: 'qtzj',
                                key: 'qtzj'
                            }
                        }
                    ]}
                />
                <div style={{marginTop:'10px'}}>
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        
                        {...config}
                        data={this.state.dataBottom}
                        wrappedComponentRef={(me) => {
                            this.tableB = me;
                        }}
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
                                    title: '本季',
                                    dataIndex: 'bj',
                                    key: 'bj'
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
                </div>

            </div>
        );
    }
}

export default index;