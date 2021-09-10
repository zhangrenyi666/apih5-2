import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin } from "antd";
import moment from 'moment';
const config = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    // paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            period: '',
        }
    }
    componentDidMount() { }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        const { period } = this.state;
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <Spin tip="Loading..." spinning={this.state.loading}>
                    <Row>
                        <Col span={8}>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => {
                                    this.formHasTicket = me;
                                }}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 7 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 17 },
                                        sm: { span: 18 }
                                    }
                                }}
                                formConfig={[
                                    {
                                        label: '报表周期',
                                        field: 'period',
                                        type: 'month',
                                        span: 24
                                    }
                                ]}
                            />
                        </Col>
                        <Col span={16}>
                            <div style={{ padding: '11px 11px 11px 71px' }}>
                                <Button type="primary" onClick={() => {
                                    let value = this.formHasTicket.form.getFieldsValue();
                                    if (value.period) {
                                        this.setState({
                                            period: new Date(value.period._d).getTime()
                                        }, () => {
                                            this.table.refresh();
                                        })
                                    } else {
                                        Msg.warn('请选择报表周期！')
                                    }
                                }}>生成报表</Button>
                                <Button style={{ marginLeft: '20px' }} type="primary" onClick={() => {
                                    const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                    let value = this.formHasTicket.form.getFieldsValue();
                                    if (value.period) {
                                        var URL = `${ureport}excel?_u=file:zjTzXmhgqk.xml&url=${domain}&period=${new Date(value.period._d).getTime()}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=项目采购（回购）情况表_${moment(value.period).format('YYYYMM')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
                                        window.open(URL);
                                    } else {
                                        Msg.warn('请选择报表周期！')
                                    }

                                }}>导出</Button>
                            </div>
                        </Col>
                    </Row>
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        fetchConfig={period ? {
                            apiName: 'getZjTzInvXmhgqkMonthlyReportList',
                            otherParams: {
                                period: period,
                                projectId:projectId
                            }
                        } : {}}
                        {...config}
                        antd={{
                            rowKey: 'id',
                            size: 'small',
                            scroll: {
                                y: document.documentElement.clientHeight * 0.55
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
                                table: {
                                    title: '序号',
                                    dataIndex: 'no',
                                    key: 'no',
                                    width: 60,
                                    align: 'center',
                                    render: (data, rows, index) => {
                                        return index + 1
                                    }
                                }
                            },
                            {
                                table: {
                                    title: "单位名称",
                                    dataIndex: 'gldw',
                                    width: 180,
                                    tooltip: 23,
                                    render: (text, rowData, index) => {
                                        return {
                                            children: <div>{text}</div>,
                                            props: {
                                                rowSpan: Number(rowData.count)
                                            },
                                        };
                                    },
                                },
                                isInForm: false
                            },

                            {
                                table: {
                                    title: "项目名称",
                                    dataIndex: 'proName',
                                    width: 180,
                                    tooltip: 23,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "项目类别",
                                    dataIndex: 'proCategory',
                                    width: 180
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "总投资额",
                                    dataIndex: 'ztze',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "股权比例(%)",
                                    dataIndex: 'szgq',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "预计回购总额",
                                    width: 130,
                                    dataIndex: 'hgxyMoney',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "合同回购期(年)",
                                    dataIndex: 'hgq'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "回购起始日",
                                    dataIndex: 'hgxyDate',
                                    format: 'YYYY-MM-DD'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "累计回购期(月)",
                                    dataIndex: 'ljhgq'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "回购金额",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'hgjeBq',
                                            title: "本期",
                                            width: 110
                                        },
                                        {
                                            title: '本年',
                                            dataIndex: 'bnlj',
                                            width: 130
                                        },
                                        {
                                            title: '开累',
                                            dataIndex: 'sjhgje',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '回购比例(%)',
                                    width: 130,
                                    dataIndex: 'hgjeHgbl'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "回购款累计使用情况",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'chyhdk',
                                            title: "偿还银行贷款",
                                            width: 110
                                        },
                                        {
                                            title: '偿还贷款利息',
                                            dataIndex: 'chdklx',
                                            width: 130
                                        },
                                        {
                                            title: '偿还资本金',
                                            dataIndex: 'chzbj',
                                            width: 110
                                        },
                                        {
                                            title: '资金集中',
                                            dataIndex: 'zjjz',
                                            width: 110
                                        },
                                        {
                                            title: '其他',
                                            dataIndex: 'qt',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '备注',
                                    width: 130,
                                    dataIndex: 'bz',
                                    tooltip: 23
                                },
                                isInForm: false
                            },
                        ]}
                    />
                </Spin>
            </div>
        );
    }
}

export default index;