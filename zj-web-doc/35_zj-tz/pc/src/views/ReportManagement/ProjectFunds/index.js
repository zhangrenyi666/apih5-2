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
                                        var URL = `${ureport}excel?_u=file:zjTzXmzjqk.xml&url=${domain}&period=${new Date(value.period._d).getTime()}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=?????????????????????_${moment(value.period).format('YYYYMM')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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
                            apiName: 'getZjTzInvXmzjqkMonthlyReportList',
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
                            },

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
                                table: {
                                    title: "????????????",
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
                                    title: "????????????",
                                    dataIndex: 'proName',
                                    width: 180,
                                    tooltip: 23,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????",
                                    dataIndex: 'proInvCategory',
                                    width: 180
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
                                    title: "???????????????(%)",
                                    width: 130,
                                    dataIndex: 'zbjbl',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "??????????????????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '?????????',
                                            children: [
                                                {
                                                    dataIndex: 'zbjtotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'zjxqqkZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zjxqqkQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'zjxqqkZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'zjxqqkQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????',
                                            children: [
                                                {
                                                    dataIndex: 'rztotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'zjxqqkYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zjxqqkJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'zjxqqkYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'zjxqqkXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????',
                                            width: 150,
                                            dataIndex: 'zjxqqkZfbt',
                                            key: 'zjxqqkZfbt'
                                        },
                                        {
                                            title: '????????????',
                                            width: 150,
                                            dataIndex: 'zjxqqkQtzj',
                                            key: 'zjxqqkQtzj'
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
                                            title: '?????????',
                                            children: [
                                                {
                                                    dataIndex: 'bqzbjtotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bqdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bqdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bqdwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bqdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????',
                                            children: [
                                                {
                                                    dataIndex: 'bqrztotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bqdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bqdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bqdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bqdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????',
                                            width: 150,
                                            dataIndex: 'bqdwzjZfbt',
                                            key: 'bqdwzjZfbt'
                                        },
                                        {
                                            title: '????????????',
                                            width: 150,
                                            dataIndex: 'bqdwzjQtzj',
                                            key: 'bqdwzjQtzj'
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
                                            title: '?????????',
                                            children: [
                                                {
                                                    dataIndex: 'bjzbjtotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bjdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bjdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bjdwzjzbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bjdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????',
                                            children: [
                                                {
                                                    dataIndex: 'bjrztotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bjdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bjdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bjdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bjdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????',
                                            width: 150,
                                            dataIndex: 'bjdwzjZfbt',
                                            key: 'bjdwzjZfbt'
                                        },
                                        {
                                            title: '????????????',
                                            width: 150,
                                            dataIndex: 'bjdwzjQtzj',
                                            key: 'bjdwzjQtzj'
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
                                            title: '?????????',
                                            children: [
                                                {
                                                    dataIndex: 'bnzbjtotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bndwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bndwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bndwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bndwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????',
                                            children: [
                                                {
                                                    dataIndex: 'bnrztotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'bndwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'bndwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'bndwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'bndwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????',
                                            width: 150,
                                            dataIndex: 'bndwzjZfbt',
                                            key: 'bndwzjZfbt'
                                        },
                                        {
                                            title: '????????????',
                                            width: 150,
                                            dataIndex: 'bndwzjQtzj',
                                            key: 'bndwzjQtzj'
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
                                            title: '?????????',
                                            children: [
                                                {
                                                    dataIndex: 'ljzbjtotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'ljdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ljdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'ljdwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'ljdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????',
                                            children: [
                                                {
                                                    dataIndex: 'ljrztotal',
                                                    title: "??????",
                                                    width: 110
                                                },
                                                {
                                                    title: '????????????',
                                                    dataIndex: 'ljdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '??????',
                                                    dataIndex: 'ljdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '?????????????????????',
                                                    dataIndex: 'ljdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '???????????????',
                                                    dataIndex: 'ljdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '??????????????????',
                                            width: 150,
                                            dataIndex: 'ljdwzjZfbt',
                                            key: 'ljdwzjZfbt'
                                        },
                                        {
                                            title: '????????????',
                                            width: 150,
                                            dataIndex: 'ljdwzjQtzj',
                                            key: 'ljdwzjQtzj'
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "??????????????????????????????????????????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbqmJsqzjhb',
                                            key: 'jsqzbyzJzbqmJsqzjhb'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbjmJsqzjhb',
                                            key: 'jsqzbyzJzbjmJsqzjhb'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbnmJsqzjhb',
                                            key: 'jsqzbyzJzbnmJsqzjhb'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'jsqzbyzXmklJsqzjhb',
                                            key: 'jsqzbyzXmklJsqzjhb'
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "????????????????????????",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbqm',
                                            key: 'sjtrzyzjJzbqm'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbjm',
                                            key: 'sjtrzyzjJzbjm'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbnm',
                                            key: 'sjtrzyzjJzbnm'
                                        },
                                        {
                                            title: '??????',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjXmkl',
                                            key: 'sjtrzyzjXmkl'
                                        }
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '??????????????????????????????????????????????????????%???',
                                    width: 300,
                                    dataIndex: 'sjtrzyzjZwcqytzbl'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '????????????????????????????????????????????????%???',
                                    width: 300,
                                    dataIndex: 'sjtrzyzjZtzzebl'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '??????????????????????????????%???',
                                    width: 300,
                                    dataIndex: 'zbjzykhzb'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '?????????????????????',
                                    width: 300,
                                    dataIndex: 'kzyjzbj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    width: 300,
                                    dataIndex: 'yhlzj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '?????????????????????',
                                    width: 300,
                                    dataIndex: 'sxhlzj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    width: 300,
                                    dataIndex: 'sfrzls'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '??????',
                                    width: 300,
                                    tooltip: 23,
                                    dataIndex: 'bz'
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