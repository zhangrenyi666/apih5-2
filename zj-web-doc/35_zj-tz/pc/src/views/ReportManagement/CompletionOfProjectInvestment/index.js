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
                                        var URL = `${ureport}excel?_u=file:zjTzXmtzqk.xml&url=${domain}&period=${new Date(value.period._d).getTime()}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=项目投资完成情况表_${moment(value.period).format('YYYYMM')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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
                            apiName: 'getZjTzInvXmtzqkMonthlyReportList',
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
                                    dataIndex: 'no',//???
                                    key: 'no',
                                    width: 60,
                                    align: 'center',
                                    render: (data, rows, index) => {
                                        return index + 1
                                    }
                                }
                            },
                            {
                                // isInTable
                                table: {
                                    title: "单位名称",
                                    dataIndex: 'gldw',
                                    width: 260,
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
                                    width: 260,
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
                                    title: "一公局施工份额",
                                    width: 130,
                                    dataIndex: 'ygjsgfe',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "总建安费",
                                    dataIndex: 'jafMoney',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "一公局建安费",
                                    dataIndex: 'ygjjtjaf',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "总征拆费",
                                    dataIndex: 'zzcf',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "批复类型",
                                    dataIndex: 'pfname',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "投资完成",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'tzwcbq',
                                            title: "本期",
                                            width: 110
                                        },
                                        {
                                            title: '本季',
                                            dataIndex: 'tzwcbj',
                                            width: 110
                                        },
                                        {
                                            title: '本年',
                                            dataIndex: 'tzwcbn',
                                            width: 130
                                        },
                                        {
                                            title: '开累',
                                            dataIndex: 'tzwckl',
                                            width: 110
                                        },
                                        {
                                            title: '比例',
                                            dataIndex: 'tzwcbl',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "权益投资完成",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'qytzwcbq',
                                            title: "本期",
                                            width: 110
                                        },
                                        {
                                            title: '本季',
                                            dataIndex: 'qytzwcbj',
                                            width: 110
                                        },
                                        {
                                            title: '本年',
                                            dataIndex: 'qytzwcbn',
                                            width: 130
                                        },
                                        {
                                            title: '开累',
                                            dataIndex: 'qytzwckl',
                                            width: 110
                                        },
                                        {
                                            title: '比例',
                                            dataIndex: 'qytzwcbl',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "建安",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '项目整体完成建安费',
                                            children: [
                                                {
                                                    dataIndex: 'wcjafbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'wcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'wcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'wcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'wcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '项目整体完成计量',
                                            children: [
                                                {
                                                    dataIndex: 'xmztwcjlbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'xmztwcjlbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'xmztwcjlbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'xmztwcjlkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'xmztwcjlbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '项目整体支出建安费',
                                            children: [
                                                {
                                                    dataIndex: 'zcjafbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'zcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'zcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'zcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'zcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '其中一公局完成建安费',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtwcjafbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'ygjjtwcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'ygjjtwcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'ygjjtwcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'ygjjtwcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '其中一公局完成计量',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtwcjlbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'ygjjtwcjlbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'ygjjtwcjlbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'ygjjtwcjlkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'ygjjtwcjlbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '其中对一公局支出建安费',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtzcjafbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'ygjjtzcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'ygjjtzcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'ygjjtzcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'ygjjtzcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "征拆",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '完成征拆费',
                                            children: [
                                                {
                                                    dataIndex: 'wczcfbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'wczcfbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'wczcfbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'wczcfkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'wczcfbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '支出征拆费',
                                            children: [
                                                {
                                                    dataIndex: 'zczcfbq',
                                                    title: "本期",
                                                    width: 110
                                                },
                                                {
                                                    title: '本季',
                                                    dataIndex: 'zczcfbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '本年',
                                                    dataIndex: 'zczcfbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '开累',
                                                    dataIndex: 'zczcfkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '比例',
                                                    dataIndex: 'zczcfbl',
                                                    width: 110
                                                }
                                            ]
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
                                    tooltip: 23,
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