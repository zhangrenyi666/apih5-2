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
            year: null,
        }
    }
    componentDidMount() {

    }

    render() {
        const { orgID, year, } = this.state;
        // let { myPublic: { domain, appInfo: { ureport } } } = this.props;
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
                                    placeholder: '?????????',
                                    span: 6
                                },
                                {
                                    type: 'year',
                                    label: '??????',
                                    field: 'year', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
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
                                                if (value.orgID) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            orgID: value.orgID ? value.orgID : null,
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
                                                    let { myPublic: {  appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    console.log(value)
                                                    var URL = `${ureport}excel?_u=minio:ZxSfAccidentItemYear.ureport.xml&access_token=${access_token}&_n=?????????????????????????????????????????????????????????.xlsx&delFlag=0&orgID=${value.projectID ? value.projectID : null}&year=${value.year ? new Date().getTime(value.year) : ''}`;
                                                    if (value.projectID) {
                                                        if (value.year) {
                                                            window.open(URL);
                                                        } else {
                                                            Msg.warn('??????????????????')
                                                        }
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

                                pageData.forEach(({ a1, a2, a3, a4, a5, a6, a7 }) => {
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
                                        <Table.Summary.Cell index={0} align={'center'}>??????</Table.Summary.Cell>
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
                        //         title: '??????',
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
                                title: '??????',
                                dataIndex: 'orgName',
                                align: 'center',
                                key: 'orgName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'a1',
                                align: 'center',
                                key: 'a1',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                width: 120,
                                dataIndex: 'swrs',
                                align: 'center',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'a2',
                                        align: 'center',
                                        key: 'a2',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a3',
                                        align: 'center',
                                        key: 'a3',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a4',
                                        align: 'center',
                                        key: 'a4',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a5',
                                        align: 'center',
                                        key: 'a5',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                width: 120,
                                dataIndex: 'a6',
                                align: 'center',
                                key: 'a6'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                width: 120,
                                dataIndex: 'a7',
                                align: 'center',
                                key: 'a7'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????%???',
                                width: 120,
                                dataIndex: 'swzspl',
                                align: 'center',
                                key: 'swzspl',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'a8',
                                        align: 'center',
                                        key: 'a8',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'a9',
                                        align: 'center',
                                        key: 'a9',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '?????????????????????%???',
                                dataIndex: 'a10',
                                align: 'center',
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