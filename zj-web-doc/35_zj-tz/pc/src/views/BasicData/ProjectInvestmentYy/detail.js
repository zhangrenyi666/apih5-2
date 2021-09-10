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
            dataBottom: null
        }
    }
    componentDidMount() {
        const { invProId, periodValue } = this.state;
        const { myFetch } = this.props;
        myFetch('getZjTzInvXmyyqkMonthlyReportListBasicDataDetail', {
            proId: invProId,
            periodValue: periodValue
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    Msg.success(message);
                    console.log(data.children);
                    this.setState({
                        dataBottom: data.children
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
                    <Col span={20}><div style={{ width: '100%', marginLeft: '10%', fontSize: '18px', fontWeight: 'bold' }}>{projectNameParams} 项目运营情况表</div></Col>
                </Row>
                <QnnForm
                    style={{ paddingRight: '10px', paddingLeft: '10px' }}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzInvXmyyqkMonthlyReportListBasicDataDetail',
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
                            type: 'date',
                            label: '运营起始日',//???
                            disabled: true,
                            field: 'yyksrq',
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
                            label: '累计经营期数(月)',//???
                            field: 'ljjyqs',
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
                            type: 'number',
                            disabled: true,
                            label: '投评月均收入',
                            field: 'tpyjsr',
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
                            label: '累计运营时间(日)',//???
                            field: 'ljyxsj',
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
                            type: 'number',
                            disabled: true,
                            label: '期末资产总额',//???
                            field: 'ljZcze',
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
                            type: 'number',
                            disabled: true,
                            label: '期末负债总额',
                            field: 'ljFzze',
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
                            type: 'number',
                            disabled: true,
                            label: '投评月均车辆通行数量（辆）',//???
                            field: 'tpyjcltxsl',
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
                            type: 'number',
                            disabled: true,
                            label: '本月车辆通行数量（辆）',
                            field: 'cltxqkBysl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            disabled: true,
                            label: '本年车辆通行数量（辆）',//???
                            field: 'cltxqkBnsl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            disabled: true,
                            label: '累计车辆通行数量（辆）',//???
                            field: 'ljCltxsl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            disabled: true,
                            label: '日均车辆通行数量（辆）',//???
                            field: 'rjcltxsl',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            disabled: true,
                            label: '当年日均运营车辆数量预测值（辆）',//???
                            field: 'dnrjyyclslycz',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'number',
                            disabled: true,
                            label: '当年日均运营车辆数量实际值（辆）',//???
                            field: 'dnrjyyclslsjz',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                }
                            }
                        },
                        {
                            type: 'date',
                            disabled: true,
                            label: '填报时间',//???
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
                            type: 'date',
                            disabled: true,
                            label: '填报年月',//???
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