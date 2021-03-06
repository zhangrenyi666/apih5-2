import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal, message as Msg, Spin } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
            lockID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId && lockProject?.projectId !== 'all') ? lockProject.projectId : null,
            orgID: (curCompany?.ext1 === '1' || curCompany?.ext1 === '2') ? curCompany?.companyId : curCompany?.projectId,
            lockProject,
            visible: false,
            loading: false,
            filter:[]
        }
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
        const { departmentId, ext1, lockID, orgID, lockProject, loading } = this.state;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
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
                                    type: "component",
                                    field: "component3",
                                    Component: obj => {
                                        return (
                                            <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                {departmentName}??????????????????????????????
                                            </div>
                                        );
                                    }
                                },
                                {
                                    label: '????????????',
                                    field: 'companyIdSearch',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: { departmentId }
                                    },
                                    placeholder: '?????????',
                                    span: 8,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    label: '????????????',
                                    field: 'projectIdSearch',
                                    type: 'select',
                                    showSearch: true,
                                    hide: () => {
                                        if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all') {
                                            return false
                                        }
                                        return true
                                    },
                                    required: true,
                                    dependencies: ['companyIdSearch'],
                                    dependenciesReRender: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProjectList',
                                        otherParams: (obj) => {
                                            if (ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all') {
                                                return {
                                                    companyId: obj?.form?.getFieldValue('companyIdSearch')
                                                }
                                            }
                                            return {
                                                companyId: departmentId
                                            }
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '??????????????????',
                                    field: 'orgCertificate',
                                    span: 8
                                },
                                {
                                    label: '??????????????????',
                                    field: 'customerName',
                                    type: 'string',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    type: 'select',
                                    field: 'catID',
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxCrProjectEvaluationListCatName',
                                    },
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'period',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    required: true,
                                    showSearch: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'qiCi'
                                        },
                                    },
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' && !value.companyIdSearch) {
                                                    Msg.warn("?????????????????????")
                                                    return
                                                }
                                                if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all' && !value.projectIdSearch) {
                                                    Msg.warn("?????????????????????")
                                                    return
                                                }
                                                if (!value.period) {
                                                    Msg.warn("?????????????????????")
                                                    return
                                                }
                                                this.table.refresh();
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            !lockID && !value.projectIdSearch && filter.push('&orgID=' + orgID)
                                                            !value.projectIdSearch && lockID && filter.push('&projectId=' + lockID)
                                                            value.projectIdSearch && filter.push('&projectIdSearch=' + value.projectIdSearch)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.customerName && filter.push('&customerName=' + value.customerName)
                                                            value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).format('YYYYMM'))
                                                            var URL = `${ureport}excel?_u=minio:zxCrProjectEvaluationReports.ureport.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=??????????????????????????????`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    !lockID && !value.projectIdSearch && filter.push('&orgID=' + orgID)
                                                    !value.projectIdSearch && lockID && filter.push('&projectId=' + lockID)
                                                    value.projectIdSearch && filter.push('&projectIdSearch=' + value.projectIdSearch)
                                                    value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                    value.customerName && filter.push('&customerName=' + value.customerName)
                                                    value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                    value.catID && filter.push('&catID=' + value.catID)
                                                    value.period && filter.push('&period=' + moment(value.period).format('YYYYMM'))
                                                    this.setState({
                                                        visible: true,
                                                        loading: true,
                                                        filter: filter
                                                    })
                                                }}>??????</Button>
                                            </div>
                                        );
                                    },
                                    span: 8
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
                    pageSize={9999}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCrProjectEvaluationReports',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : selectData?.projectIdSearch ? null : orgID,
                                projectId: selectData?.projectIdSearch ? null : lockID,
                                projectIdSearch: selectData?.projectIdSearch,
                                companyIdSearch: selectData?.companyIdSearch,
                                catID: selectData?.catID,
                                customerName: selectData?.customerName,
                                orgCertificate: selectData?.orgCertificate,
                                period: selectData?.period ? moment(selectData.period).format('YYYYMM') : null
                            }
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
                                title: '??????????????????',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'orgCertificate',
                                key: 'orgCertificate',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'chargeMan',
                                key: 'chargeMan',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'catName',
                                key: 'catName',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 130,
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD',
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 130,
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format: 'YYYY-MM-DD',
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????(??????)',
                                width: 170,
                                dataIndex: 'contractAmt',
                                key: 'contractAmt'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'totalScore',
                                key: 'totalScore'
                            }
                        },
                        {
                            table: {
                                title: '???????????????D??????',
                                width: 150,
                                dataIndex: 'isDropToD',
                                key: 'isDropToD',
                                render: (h) => {
                                    if (h === '1') {
                                        return '???'
                                    } else {
                                        return '???'
                                    }
                                }
                            },
                            isInForm: false
                        }
                    ]}
                />
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="??????"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    {
                        <Spin spinning={loading}>
                            <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={() => {
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:zxCrProjectEvaluationReports.ureport.xml&_t=1,6&access_token=${access_token}&delFlag=0${this.state.filter.join('')}
                            `}></iframe>
                        </Spin>
                    }

                </Modal>
            </div>
        );
    }
}

export default index;