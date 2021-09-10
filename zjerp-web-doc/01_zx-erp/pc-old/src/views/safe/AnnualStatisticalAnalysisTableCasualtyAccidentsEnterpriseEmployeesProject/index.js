import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col,Table } from "antd";
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
            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
            year: null,
        }
    }
    componentDidMount() {

    }

    render() {
        const { orgID, year, } = this.state;
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
                                    label: '填表单位（公章）',
                                    field: 'projectID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 6
                                },
                                {
                                    type: 'year',
                                    label: '年份',
                                    field: 'year', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.orgID) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            orgID: value.orgID ? value.orgID : null,
                                                            year: value.year ? value.year : '',
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&year=${value.year ? value.year : ''}`;
                                                    if (value.orgID) {
                                                        if (value.period) {
                                                            window.open(URL);
                                                        } else {
                                                            Msg.warn('请选择期次！')
                                                        }
                                                    } else {
                                                        Msg.warn('请选择单位名称！')
                                                    }
                                                }}>导出</Button></div>
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
                    fetchConfig={orgID ? {
                        apiName: 'getZxSfAccidentItemUReportFormYearList',
                        otherParams: {
                            orgID: orgID,
                            year: year
                        }
                    } : {}}
                    actionBtns={[]}
                    antd={
                        {
                            rowKey: (row) => {
                                return row.documentId
                            },
                            summary: (pageData) => {
                                let totalA1 = 0
                                let totalA2 = 0
                                let totalA3 = 0
                                let totalA4 = 0
                                let totalA5 = 0
                                let totalA6 = 0
                                let totalA7 = 0

                                pageData.forEach(({ a1, a2,a3,a4,a5,a6,a7}) => {
                                    totalA1 += +a1;
                                    totalA2 += +a2;
                                    totalA3 += +a3;
                                    totalA4 += +a4;
                                    totalA5 += +a5;
                                    totalA6 += +a6;
                                    totalA7 += +a7;
                                });

                                return (
                                    <Table.Summary.Row>
                                        <Table.Summary.Cell index={0} align={'center'}>合计</Table.Summary.Cell>
                                        <Table.Summary.Cell index={1} align={'center'}>{totalA1}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={2} align={'center'}>{totalA2}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={3} align={'center'}>{totalA3}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={4} align={'center'}>{totalA4}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={5} align={'center'}>{totalA5}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={6} align={'center'}>{totalA6}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={7} align={'center'}>{totalA7}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={8} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={9} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={10} align={'center'}></Table.Summary.Cell>
                                    </Table.Summary.Row>
                                )
                            }
                        }
                    }
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfEAccidentItemId ',
                                type: 'string',
                                hide: true,
                            }
                        },
                        // {
                        //     table: {
                        //         title: '序号',
                        //         dataIndex: 'index',
                        //         key: 'index',
                        //         width: 50,
                        //         fixed: 'left',
                        //         render: (data, rowData, index) => {
                        //             return index + 1;
                        //         }
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '单位',
                                dataIndex: 'orgName',
                                align:'center',
                                key: 'orgName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '职工平均人数（人）',
                                dataIndex: 'a1',
                                align:'center',
                                key: 'a1',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '伤亡人数（人）',
                                width: 120,
                                dataIndex: 'swrs',
                                align:'center',
                                key: 'swrs',
                                children:[
                                    {
                                        title: '合计',
                                        dataIndex: 'a2',
                                        align:'center',
                                        key: 'a2',
                                        width: 130,
                                    },
                                    {
                                        title: '死亡',
                                        dataIndex: 'a3',
                                        align:'center',
                                        key: 'a3',
                                        width: 130,
                                    },
                                    {
                                        title: '重伤',
                                        dataIndex: 'a4',
                                        align:'center',
                                        key: 'a4',
                                        width: 130,
                                    },
                                    {
                                        title: '轻伤',
                                        dataIndex: 'a5',
                                        align:'center',
                                        key: 'a5',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '直接经济损失（万元）',
                                width: 120,
                                dataIndex: 'a6',
                                align:'center',
                                key: 'a6'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '损失作日（工日）',
                                width: 120,
                                dataIndex: 'a7',
                                align:'center',
                                key: 'a7'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '死亡重伤频率（%）',
                                width: 120,
                                dataIndex: 'swzspl',
                                align:'center',
                                key: 'swzspl',
                                children:[
                                    {
                                        title: '死亡',
                                        dataIndex: 'a8',
                                        align:'center',
                                        key: 'a8',
                                        width: 130,
                                    },
                                    {
                                        title: '重伤',
                                        dataIndex: 'a9',
                                        align:'center',
                                        key: 'a9',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '月均负伤频率（%）',
                                dataIndex: 'a10',
                                align:'center',
                                key: 'a10',
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