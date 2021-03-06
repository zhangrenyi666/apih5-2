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
                                        label: '????????????',
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
                                        Msg.warn('????????????????????????')
                                    }
                                }}>????????????</Button>
                                <Button style={{ marginLeft: '20px' }} type="primary" onClick={() => {
                                    const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                    let value = this.formHasTicket.form.getFieldsValue();
                                    if (value.period) {
                                        var URL = `${ureport}excel?_u=file:zjTzXmtzqk.xml&url=${domain}&period=${new Date(value.period._d).getTime()}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=???????????????????????????_${moment(value.period).format('YYYYMM')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
                                        window.open(URL);
                                    } else {
                                        Msg.warn('????????????????????????')
                                    }
                                }}>??????</Button>
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
                                    title: '??????',
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
                                    title: "????????????",
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
                                    title: "????????????",
                                    dataIndex: 'proName',
                                    width: 260,
                                    tooltip: 23,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'proCategory',
                                    width: 180
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'ztze',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????(%)",
                                    dataIndex: 'szgq',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "?????????????????????",
                                    width: 130,
                                    dataIndex: 'ygjsgfe',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'jafMoney',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "??????????????????",
                                    dataIndex: 'ygjjtjaf',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'zzcf',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'pfname',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'tzwcbq',
                                            title: "??????",
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'tzwcbj',
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'tzwcbn',
                                            width: 130
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'tzwckl',
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'tzwcbl',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "??????????????????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            dataIndex: 'qytzwcbq',
                                            title: "??????",
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'qytzwcbj',
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'qytzwcbn',
                                            width: 130
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'qytzwckl',
                                            width: 110
                                        },
                                        {
                                            title: '??????',
                                            dataIndex: 'qytzwcbl',
                                            width: 110
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "??????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '???????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'wcjafbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'xmztwcjlbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'xmztwcjlbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'xmztwcjlbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'xmztwcjlkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'xmztwcjlbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '???????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'zcjafbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtwcjafbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjafbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '???????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtwcjlbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjlbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjlbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjlkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtwcjlbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '?????????????????????????????????',
                                            children: [
                                                {
                                                    dataIndex: 'ygjjtzcjafbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtzcjafbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtzcjafbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ygjjtzcjafkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
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
                                    title: "??????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '???????????????',
                                            children: [
                                                {
                                                    dataIndex: 'wczcfbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wczcfbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wczcfbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wczcfkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'wczcfbl',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '???????????????',
                                            children: [
                                                {
                                                    dataIndex: 'zczcfbq',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zczcfbj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zczcfbn',
                                                    width: 130
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zczcfkl',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
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
                                    title: '??????',
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