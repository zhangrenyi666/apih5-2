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
                                        var URL = `${ureport}excel?_u=file:zjTzXmzjqk.xml&url=${domain}&period=${new Date(value.period._d).getTime()}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=项目资金情况表_${moment(value.period).format('YYYYMM')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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
                                    title: "项目类型",
                                    dataIndex: 'proInvCategory',
                                    width: 180
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
                                    title: "投资总额",
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
                                    title: "资本金比例(%)",
                                    width: 130,
                                    dataIndex: 'zbjbl',
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: "资金结构情况",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '资本金',
                                            children: [
                                                {
                                                    dataIndex: 'zbjtotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '自有资金',
                                                    dataIndex: 'zjxqqkZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'zjxqqkQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'zjxqqkZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '其他股东等',
                                                    dataIndex: 'zjxqqkQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '融资',
                                            children: [
                                                {
                                                    dataIndex: 'rztotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '银行贷款',
                                                    dataIndex: 'zjxqqkYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'zjxqqkJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'zjxqqkYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '信托',
                                                    dataIndex: 'zjxqqkXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '政府补贴资金',
                                            width: 150,
                                            dataIndex: 'zjxqqkZfbt',
                                            key: 'zjxqqkZfbt'
                                        },
                                        {
                                            title: '其他资金',
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
                                    title: "本期到位资金",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '资本金',
                                            children: [
                                                {
                                                    dataIndex: 'bqzbjtotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '自有资金',
                                                    dataIndex: 'bqdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bqdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bqdwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '股东资本金',
                                                    dataIndex: 'bqdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '融资',
                                            children: [
                                                {
                                                    dataIndex: 'bqrztotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '银行借款',
                                                    dataIndex: 'bqdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bqdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bqdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '信托等其他',
                                                    dataIndex: 'bqdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '政府补贴资金',
                                            width: 150,
                                            dataIndex: 'bqdwzjZfbt',
                                            key: 'bqdwzjZfbt'
                                        },
                                        {
                                            title: '其他资金',
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
                                    title: "本季到位资金",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '资本金',
                                            children: [
                                                {
                                                    dataIndex: 'bjzbjtotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '自有资金',
                                                    dataIndex: 'bjdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bjdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bjdwzjzbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '股东资本金',
                                                    dataIndex: 'bjdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '融资',
                                            children: [
                                                {
                                                    dataIndex: 'bjrztotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '银行借款',
                                                    dataIndex: 'bjdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bjdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bjdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '信托等其他',
                                                    dataIndex: 'bjdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '政府补贴资金',
                                            width: 150,
                                            dataIndex: 'bjdwzjZfbt',
                                            key: 'bjdwzjZfbt'
                                        },
                                        {
                                            title: '其他资金',
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
                                    title: "本年到位资金",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '资本金',
                                            children: [
                                                {
                                                    dataIndex: 'bnzbjtotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '自有资金',
                                                    dataIndex: 'bndwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bndwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bndwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '股东资本金',
                                                    dataIndex: 'bndwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '融资',
                                            children: [
                                                {
                                                    dataIndex: 'bnrztotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '银行借款',
                                                    dataIndex: 'bndwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'bndwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'bndwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '信托等其他',
                                                    dataIndex: 'bndwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '政府补贴资金',
                                            width: 150,
                                            dataIndex: 'bndwzjZfbt',
                                            key: 'bndwzjZfbt'
                                        },
                                        {
                                            title: '其他资金',
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
                                    title: "累计到位资金",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '资本金',
                                            children: [
                                                {
                                                    dataIndex: 'ljzbjtotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '自有资金',
                                                    dataIndex: 'ljdwzjZyzj',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'ljdwzjQzjj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'ljdwzjZbjygjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '股东资本金',
                                                    dataIndex: 'ljdwzjQtgdzbj',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '融资',
                                            children: [
                                                {
                                                    dataIndex: 'ljrztotal',
                                                    title: "小计",
                                                    width: 110
                                                },
                                                {
                                                    title: '银行借款',
                                                    dataIndex: 'ljdwzjYhjk',
                                                    width: 110
                                                },
                                                {
                                                    title: '基金',
                                                    dataIndex: 'ljdwzjJj',
                                                    width: 130
                                                },
                                                {
                                                    title: '一公局认购基金',
                                                    dataIndex: 'ljdwzjYgjrgjj',
                                                    width: 110
                                                },
                                                {
                                                    title: '信托等其他',
                                                    dataIndex: 'ljdwzjXt',
                                                    width: 110
                                                }
                                            ]
                                        },
                                        {
                                            title: '政府补贴资金',
                                            width: 150,
                                            dataIndex: 'ljdwzjZfbt',
                                            key: 'ljdwzjZfbt'
                                        },
                                        {
                                            title: '其他资金',
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
                                    title: "建设期的资本运作（回流资金）",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '本期',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbqmJsqzjhb',
                                            key: 'jsqzbyzJzbqmJsqzjhb'
                                        },
                                        {
                                            title: '本季',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbjmJsqzjhb',
                                            key: 'jsqzbyzJzbjmJsqzjhb'
                                        },
                                        {
                                            title: '本年',
                                            width: 100,
                                            dataIndex: 'jsqzbyzJzbnmJsqzjhb',
                                            key: 'jsqzbyzJzbnmJsqzjhb'
                                        },
                                        {
                                            title: '开累',
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
                                    title: "实际投入自有资金",
                                    noHaveSearchInput: true,
                                    children: [
                                        {
                                            title: '本期',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbqm',
                                            key: 'sjtrzyzjJzbqm'
                                        },
                                        {
                                            title: '本季',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbjm',
                                            key: 'sjtrzyzjJzbjm'
                                        },
                                        {
                                            title: '本年',
                                            width: 100,
                                            dataIndex: 'sjtrzyzjJzbnm',
                                            key: 'sjtrzyzjJzbnm'
                                        },
                                        {
                                            title: '开累',
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
                                    title: '实际投入自有资金占完成权益投资比例（%）',
                                    width: 300,
                                    dataIndex: 'sjtrzyzjZwcqytzbl'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '实际投入自有资金占投资总额比例（%）',
                                    width: 300,
                                    dataIndex: 'sjtrzyzjZtzzebl'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '资本金占用考核指标（%）',
                                    width: 300,
                                    dataIndex: 'zbjzykhzb'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '可占用局资本金',
                                    width: 300,
                                    dataIndex: 'kzyjzbj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '应回流资金',
                                    width: 300,
                                    dataIndex: 'yhlzj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '尚需回流的资金',
                                    width: 300,
                                    dataIndex: 'sxhlzj'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '融资是否落实',
                                    width: 300,
                                    dataIndex: 'sfrzls'
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '备注',
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