import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { goBack } from "connected-react-router";
import { Button, message as Msg, Row, Col, Spin } from "antd";
import s from './style.less';
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight -590
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
            treeData: null
        }
    }
    componentDidMount() {
        const { invProId, periodValue } = this.state;
        const { myFetch } = this.props;
        myFetch('getZjTzInvXmtzqkMonthlyReportListBasicDataDetails', {
            proId: invProId,
            periodValue: periodValue
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.setState({
                        treeData: data.children //不用展开
                    }, () => {
                        if (data.children && data.children.length) {
                            let expandedRowKeys = [];
                            let loopFn = (data) => {
                              for (var i = 0; i < data.length; i++) {
                                expandedRowKeys.push(data[i].id);
                                if (data[i].children) {
                                  loopFn(data[i].children)
                                }
                              }
                              return expandedRowKeys;
                            }
                            expandedRowKeys = loopFn(data.children);
                            let setState = this.table.btnCallbackFn.setState;
                            setState({
                              expandedRowKeys: [],
                            }, () => {
                              setState({
                                expandedRowKeys: expandedRowKeys
                              })
                            })
                          }
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
            <div className={s.root}>
                <Row>
                    <Col span={4}><Button type='dashed' onClick={() => {
                        const { dispatch } = this.props;
                        dispatch(goBack());
                    }}>返回</Button></Col>
                    <Col span={20}><div style={{ width: '100%', marginLeft: '18%', fontSize: '18px', fontWeight: 'bold' }}>{projectNameParams} 项目投资情况表</div></Col>
                </Row>
                <QnnForm
                    style={{ paddingRight: '10px', paddingLeft: '10px' }}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzInvXmtzqkMonthlyReportListBasicDataDetails',
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
                            label: '一公局施工份额',
                            disabled: true,
                            field: 'ygjsgfe',
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
                            label: '总投资额',
                            field: 'ztze',
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
                            label: '一公局建安费',
                            field: 'ygjjtjaf',
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
                            label: '总建安费',
                            field: 'jafMoney',
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
                            label: '批复类型',
                            field: 'pfname',
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
                            label: '总征拆费',
                            field: 'zcfMoney',
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
                            field: 'bz',
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
                            type: 'date',
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
                    {...config}
                   
                    data={this.state.treeData}
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
                                dataIndex: 'workName',
                                key: 'workName',
                                width: 300
                            }
                        },
                        {
                            table: {
                                title: '本期',
                                dataIndex: 'period',
                                key: 'period'
                            }
                        },
                        {
                            table: {
                                title: '本季',
                                dataIndex: 'season',
                                key: 'season'
                            }
                        },
                        {
                            table: {
                                title: '本年',
                                dataIndex: 'year',
                                key: 'year'
                            }
                        },
                        {
                            table: {
                                title: '开累',
                                dataIndex: 'total',
                                key: 'total'
                            }
                        },
                        {
                            table: {
                                title: '比例',
                                dataIndex: 'rate',
                                key: 'rate'
                            }
                        },

                    ]}
                />

            </div>
        );
    }
}

export default index;