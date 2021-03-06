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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
            ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
            lockProject,
            visible: false,
            loading: false,
            filter: []
        }
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockProject, loading } = this.state;
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
                                    label: '????????????',
                                    field: 'companyIdSearch',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: { departmentId }
                                    },
                                    placeholder: '?????????',
                                    span: 8,
                                    required: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? true : false,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    type: 'string',
                                    label: '??????????????????',
                                    field: 'orgCertificate',
                                    span: 8
                                },
                                {
                                    label: '??????????????????',
                                    field: 'customerId',
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
                                    required: true,
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'period',
                                    type: 'halfYear',
                                    required: true,
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let formData = this.formHasTicket.form.getFieldsValue()
                                                if (ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' && !formData.companyIdSearch) {
                                                    Msg.warn('?????????????????????')
                                                    return
                                                }
                                                if (!formData.catID) {
                                                    Msg.warn('?????????????????????')
                                                    return
                                                }
                                                if (!formData.period) {
                                                    Msg.warn('?????????????????????')
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
                                                            !value.companyIdSearch && filter.push('&companyId=' + departmentId)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.customerId && filter.push('&customerId=' + value.customerId)
                                                            value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:zxCrHalfYearCreditEvaItemReports.ureport.xml&access_token=${access_token}${filter.join('')}&_n=??????????????????????????????`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    !value.companyIdSearch && filter.push('&companyId=' + departmentId)
                                                    value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                    value.customerId && filter.push('&customerId=' + value.customerId)
                                                    value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                    value.catID && filter.push('&catID=' + value.catID)
                                                    value.period && filter.push('&period=' + moment(value.period).valueOf())
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCrHalfYearCreditEvaItemReports',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                companyId: departmentId,
                                companyIdSearch: selectData?.companyIdSearch,
                                catID: selectData?.catID,
                                customerId: selectData?.customerId,
                                orgCertificate: selectData?.orgCertificate,
                                period: selectData?.period ? moment(selectData.period).valueOf() : null
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
                                title: '????????????????????????',
                                dataIndex: 'projectNum',
                                key: 'projectNum',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'checkNum',
                                key: 'checkNum',
                                width: 120,
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
                                title: '???????????????D??????',
                                width: 150,
                                dataIndex: 'dlevel',
                                key: 'dlevel',
                                render: (h) => {
                                    if (h === '1') {
                                        return '???'
                                    } else {
                                        return '???'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 170,
                                dataIndex: 'companyScore',
                                key: 'companyScore'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                width: 170,
                                dataIndex: 'lastLevel',
                                key: 'lastLevel'
                            }
                        },
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
                            }} src={`${ureport}preview?_u=minio:zxCrHalfYearCreditEvaItemReports.ureport.xml&_t=1,6&access_token=${access_token}&delFlag=0${this.state.filter.join('')}
                            `}></iframe>
                        </Spin>
                    }

                </Modal>
            </div>
        );
    }
}

export default index;