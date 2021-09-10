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
                                    label: '所属公司',
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
                                    placeholder: '请选择',
                                    span: 8,
                                    required: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? true : false,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    type: 'string',
                                    label: '组织机构代码',
                                    field: 'orgCertificate',
                                    span: 8
                                },
                                {
                                    label: '协作单位名称',
                                    field: 'customerId',
                                    type: 'string',
                                    span: 8
                                },
                                {
                                    label: '专业类别',
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
                                    label: '考核期次',
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
                                                    Msg.warn('请选择所属公司')
                                                    return
                                                }
                                                if (!formData.catID) {
                                                    Msg.warn('请选择专业类别')
                                                    return
                                                }
                                                if (!formData.period) {
                                                    Msg.warn('请选择考核期次')
                                                    return
                                                }
                                                this.table.refresh();
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            !value.companyIdSearch && filter.push('&companyId=' + departmentId)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.customerId && filter.push('&customerId=' + value.customerId)
                                                            value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:zxCrHalfYearCreditEvaItemReports.ureport.xml&access_token=${access_token}${filter.join('')}&_n=公司半年信用评价报表`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button>
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
                                                }}>打印</Button>
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
                                title: '协作单位名称',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '组织机构代码',
                                dataIndex: 'orgCertificate',
                                key: 'orgCertificate',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '协作单位负责人',
                                dataIndex: 'chargeMan',
                                key: 'chargeMan',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '专业类别',
                                dataIndex: 'catName',
                                key: 'catName',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '工程项目所属个数',
                                dataIndex: 'projectNum',
                                key: 'projectNum',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '项目评审次数',
                                dataIndex: 'checkNum',
                                key: 'checkNum',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '承建工程合同额(万元)',
                                width: 170,
                                dataIndex: 'contractAmt',
                                key: 'contractAmt'
                            }
                        },
                        {
                            table: {
                                title: '有无直接降D行为',
                                width: 150,
                                dataIndex: 'dlevel',
                                key: 'dlevel',
                                render: (h) => {
                                    if (h === '1') {
                                        return '有'
                                    } else {
                                        return '无'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '公司考核得分',
                                width: 170,
                                dataIndex: 'companyScore',
                                key: 'companyScore'
                            }
                        },
                        {
                            table: {
                                title: '公司信用评价等级',
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
                    title="打印"
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