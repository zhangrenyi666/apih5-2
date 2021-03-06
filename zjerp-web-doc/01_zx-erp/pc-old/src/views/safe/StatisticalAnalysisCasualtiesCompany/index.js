import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Table } from "antd";
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
    isShowRowSelect: false
};


class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            companyId:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
            year: null,
            a1: '',
            a2: '',
            a3: '',
            a4: '',
            a5: '',
            a6: '',
            a7: '',
            a8: '',
            a9: '',
            a10: '',
            a11: '',
            a12: '',
            a13: '',
            a14: '',
            a15: '',
            a16: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { companyId, year } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div>
                <Row>
                    <Col span={24}>
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
                                    sm: { span: 7 }
                                },
                                wrapperCol: {
                                    xs: { span: 17 },
                                    sm: { span: 17 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '????????????????????????',
                                    field: 'companyId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect'
                                    },
                                    placeholder: '?????????',
                                    span: 6
                                },
                                {
                                    type: 'month',
                                    label: '??????',
                                    field: 'year', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
                                    format: "YYYY-MM",
                                    showTime: false, //???????????????
                                    scope: false, //?????????????????????
                                    span: 6
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.companyId) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            companyId: value.companyId ? value.companyId : null,
                                                            year: value.year ? value.year : '',
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('????????????????????????')
                                                }
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&companyId=${value.companyId ? value.companyId : null}&year=${value.year ? value.year : ''}`;
                                                    if (value.companyId) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('????????????????????????')
                                                    }
                                                }}>??????</Button></div>
                                        );
                                    },
                                    span: 24
                                }
                            ]}
                        />
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSfAccidentItemFormComList',
                        otherParams: {
                            companyId: companyId,
                            year: year
                        }
                    }}
                    antd={
                        {
                            rowKey: (row) => {
                                return row.documentId
                            },
                            summary: (pageData) => {
                                let totalA8 = 0
                                let totalA9 = 0
                                let totalA10 = 0
                                let totalA11 = 0
                                let totalA12 = 0
                                let totalA13 = 0
                                let totalA14 = 0
                                let totalA15 = 0

                                pageData.forEach(({ a8, a9,a10,a11,a12,a13,a14,a15}) => {
                                    totalA8 += +a8;
                                    totalA9 += +a9;
                                    totalA10 += +a10;
                                    totalA11 += +a11;
                                    totalA12 += +a12;
                                    totalA13 += +a13;
                                    totalA14 += +a14;
                                    totalA15 += +a15;
                                });

                                return (
                                    <Table.Summary.Row>

                                        <Table.Summary.Cell index={0} colSpan={3}>??????</Table.Summary.Cell>
                                        {/* ??????????????????????????? */}

                                        <Table.Summary.Cell index={3} align={'center'} ></Table.Summary.Cell>
                                        <Table.Summary.Cell index={4} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={5} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={6} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={7} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={8} align={'center'}>{totalA8}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={9} align={'center'}>{totalA9}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={10} align={'center'}>{totalA10}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={11} align={'center'}>{totalA11}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={12} align={'center'}>{totalA12}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={13} align={'center'}>{totalA13}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={14} align={'center'}>{totalA14}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={15} align={'center'}>{totalA15}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={16} align={'center'}></Table.Summary.Cell>
                                    </Table.Summary.Row>
                                )
                            }
                        }
                    }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfAccidentItemId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'index',
                                align:'center',
                                key: 'index',
                                width: 100,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'a1',
                                align:'center',
                                key: 'a1',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'a2',
                                key: 'a2',
                                align:'center',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'a3',
                                align:'center',
                                key: 'a3'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????????????????',
                                width: 120,
                                dataIndex: 'a4',
                                align:'center',
                                key: 'a4'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'a5',
                                align:'center',
                                key: 'a5'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'a6',
                                align:'center',
                                key: 'a6'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'swrs',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '??????????????????',
                                        dataIndex: 'a7',
                                        align:'center',
                                        key: 'a7',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a8',
                                        align:'center',
                                        key: 'a8',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a9',
                                        align:'center',
                                        key: 'a9',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a10',
                                        align:'center',
                                        key: 'a10',
                                        width: 130,
                                    },
                                    {
                                        title: '??????????????????',
                                        dataIndex: 'fbqyry',
                                        align:'center',
                                        key: 'fbqyry',
                                        width: 130,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'a11',
                                                align:'center',
                                                key: 'a11',
                                                width: 130,
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'a12',
                                                align:'center',
                                                key: 'a12',
                                                width: 130,
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'a13',
                                                align:'center',
                                                key: 'a13',
                                                width: 130,
                                            },
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                dataIndex: 'a14',
                                align:'center',
                                key: 'a14',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'a15',
                                align:'center',
                                key: 'a15'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'a16',
                                align:'center',
                                key: 'a16'
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;