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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            orgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            period: null,
        }
    }
    componentDidMount() {

    }

    render() {
        const { orgID, period } = this.state;
        // let { myPublic: {  appInfo: {  } } } = this.props;
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
                                    field: 'orgID',
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
                                    type: 'month',
                                    label: '年份 ',
                                    field: 'period', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM",
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
                                                            period: value.period ? value.period : '',
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let { myPublic: {  appInfo: { ureport,  } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;

                                                    var URL = `${ureport}excel?_u=minio:ZxSfSWAccident.ureport.xml&access_token=${access_token}&_n=船舶水上交通事故统计表（项目）.xlsx&delFlag=0&orgID=${value.orgID ? value.orgID : null}&period=${value.period ? new Date().getTime(value.period) : ''}`;
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
                        apiName: 'getZxSfSWAccidentUReportFormList',
                        otherParams: {
                            orgID: orgID,
                            period: period
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
                                let totalA8 = 0
                                let totalA9 = 0
                                let totalA10 = 0
                                let totalA11 = 0
                                let totalA12 = 0
                                let totalA13 = 0
                                let totalA14 = 0
                                let totalA15 = 0
                                let totalA16 = 0
                                let totalA17 = 0
                                let totalA18 = 0
                                let totalA19 = 0
                                let totalA20 = 0
                                let totalA21 = 0
                                let totalA22 = 0
                                let totalA23 = 0

                                pageData.forEach(({ a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23 }) => {
                                    totalA1 += +a1;
                                    totalA2 += +a2;
                                    totalA3 += +a3;
                                    totalA4 += +a4;
                                    totalA5 += +a5;
                                    totalA6 += +a6;
                                    totalA7 += +a7;
                                    totalA8 += +a8;
                                    totalA9 += +a9;
                                    totalA10 += +a10;
                                    totalA11 += +a11;
                                    totalA12 += +a12;
                                    totalA13 += +a13;
                                    totalA14 += +a14;
                                    totalA15 += +a15;
                                    totalA16 += +a16;
                                    totalA17 += +a17;
                                    totalA18 += +a18;
                                    totalA19 += +a19;
                                    totalA20 += +a20;
                                    totalA21 += +a21;
                                    totalA22 += +a22;
                                    totalA23 += +a23;
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
                                        <Table.Summary.Cell index={8} align={'center'}>{totalA8}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={9} align={'center'}>{totalA9}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={10} align={'center'}>{totalA10}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={11} align={'center'}>{totalA11}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={12} align={'center'}>{totalA12}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={13} align={'center'}>{totalA13}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={14} align={'center'}>{totalA14}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={15} align={'center'}>{totalA15}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={16} align={'center'}>{totalA16}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={17} align={'center'}>{totalA17}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={18} align={'center'}>{totalA18}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={19} align={'center'}>{totalA19}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={20} align={'center'}>{totalA20}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={21} align={'center'}>{totalA21}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={22} align={'center'}>{totalA22}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={23} align={'center'}>{totalA23}</Table.Summary.Cell>
                                    </Table.Summary.Row>
                                )
                            }
                        }
                    }
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfSWAccidentItemId',
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
                                title: '船舶种类',
                                dataIndex: 'a1,',
                                align: 'center',
                                key: 'a1,',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '统计期内船舶总数（艘）',
                                dataIndex: 'a2',
                                align: 'center',
                                key: 'a2',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故件数（件）',
                                width: 120,
                                dataIndex: 'sgjs',
                                align: 'center',
                                key: 'sgjs',
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'a3',
                                        align: 'center',
                                        key: 'a3',
                                        width: 130,
                                    },
                                    {
                                        title: '其中',
                                        dataIndex: 'qz',
                                        align: 'center',
                                        key: 'qz',
                                        width: 130,
                                        children: [
                                            {
                                                title: '重大',
                                                dataIndex: 'a4',
                                                align: 'center',
                                                key: 'a4',
                                                width: 130,
                                            },
                                            {
                                                title: '大',
                                                dataIndex: 'a5',
                                                align: 'center',
                                                key: 'a5',
                                                width: 130,
                                            },
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '事故分类（件）',
                                width: 120,
                                dataIndex: 'sgfl',
                                align: 'center',
                                key: 'sgfl',
                                children: [
                                    {
                                        title: '碰撞',
                                        dataIndex: 'a6',
                                        align: 'center',
                                        key: 'a6',
                                        width: 130,
                                    },
                                    {
                                        title: '搁浅',
                                        dataIndex: 'a7',
                                        align: 'center',
                                        key: 'a7',
                                        width: 130,
                                    },
                                    {
                                        title: '触礁',
                                        dataIndex: 'a8',
                                        align: 'center',
                                        key: 'a8',
                                        width: 130,
                                    },
                                    {
                                        title: '触损',
                                        dataIndex: 'a9',
                                        align: 'center',
                                        key: 'a9',
                                        width: 130,
                                    },
                                    {
                                        title: '浪损',
                                        dataIndex: 'a10',
                                        align: 'center',
                                        key: 'a10',
                                        width: 130,
                                    },
                                    {
                                        title: '火灾爆炸',
                                        dataIndex: 'a11',
                                        align: 'center',
                                        key: 'a11',
                                        width: 130,
                                    },
                                    {
                                        title: '风灾',
                                        dataIndex: 'a12',
                                        align: 'center',
                                        key: 'a12',
                                        width: 130,
                                    },
                                    {
                                        title: '自沉',
                                        dataIndex: 'a13',
                                        align: 'center',
                                        key: 'a13',
                                        width: 130,
                                    },
                                    {
                                        title: '其他',
                                        dataIndex: 'a14',
                                        align: 'center',
                                        key: 'a14',
                                        width: 130,
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '伤亡人数（人）',
                                width: 120,
                                dataIndex: 'swrs',
                                align: 'center',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '受伤',
                                        dataIndex: 'a15',
                                        align: 'center',
                                        key: '15',
                                        width: 130,
                                    },
                                    {
                                        title: '死亡失踪',
                                        dataIndex: 'a16',
                                        align: 'center',
                                        key: 'a16',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '船舶沉没或全损',
                                width: 120,
                                dataIndex: 'cbcmhqs',
                                align: 'center',
                                key: 'cbcmhqs',
                                children: [
                                    {
                                        title: '总艘数（艘）',
                                        dataIndex: 'a17',
                                        align: 'center',
                                        key: 'a17',
                                        width: 130,
                                    },
                                    {
                                        title: '总吨位（吨）',
                                        dataIndex: 'a18',
                                        align: 'center',
                                        key: 'a18',
                                        width: 130,
                                    },
                                    {
                                        title: '功率（千瓦）',
                                        dataIndex: 'a19',
                                        align: 'center',
                                        key: 'a19',
                                        width: 130,
                                    },
                                    {
                                        title: '机动船',
                                        dataIndex: 'jdc',
                                        align: 'center',
                                        key: 'jdc',
                                        width: 130,
                                        children: [
                                            {
                                                title: '艘数（艘）',
                                                dataIndex: 'a20',
                                                align: 'center',
                                                key: 'a20',
                                                width: 130,
                                            },
                                            {
                                                title: '总吨位（吨）',
                                                dataIndex: 'a21',
                                                align: 'center',
                                                key: 'a21',
                                                width: 130,
                                            },
                                        ]
                                    },
                                    {
                                        title: '非机动船',
                                        dataIndex: 'fjdc',
                                        align: 'center',
                                        key: 'fjdc',
                                        width: 130,
                                        children: [
                                            {
                                                title: '艘数（艘）',
                                                dataIndex: 'a22',
                                                align: 'center',
                                                key: 'a22',
                                                width: 130,
                                            },
                                            {
                                                title: '总吨位（吨）',
                                                dataIndex: 'a23',
                                                align: 'center',
                                                key: 'a23',
                                                width: 130,
                                            },
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '事故直接经济损失（万元)',
                                dataIndex: 'a24',
                                align: 'center',
                                key: 'a24',
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