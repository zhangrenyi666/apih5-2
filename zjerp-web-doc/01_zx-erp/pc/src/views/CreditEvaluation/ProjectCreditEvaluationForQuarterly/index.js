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
                                                {departmentName}项目协作单位评价台账
                                            </div>
                                        );
                                    }
                                },
                                {
                                    label: '公司名称',
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
                                    placeholder: '请选择',
                                    span: 8,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    label: '项目名称',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '组织机构代码',
                                    field: 'orgCertificate',
                                    span: 8
                                },
                                {
                                    label: '协作单位名称',
                                    field: 'customerName',
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
                                    span: 8
                                },
                                {
                                    label: '考核期次',
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
                                                    Msg.warn("请选择公司名称")
                                                    return
                                                }
                                                if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all' && !value.projectIdSearch) {
                                                    Msg.warn("请选择项目名称")
                                                    return
                                                }
                                                if (!value.period) {
                                                    Msg.warn("请选择考核期次")
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
                                                            !lockID && !value.projectIdSearch && filter.push('&orgID=' + orgID)
                                                            !value.projectIdSearch && lockID && filter.push('&projectId=' + lockID)
                                                            value.projectIdSearch && filter.push('&projectIdSearch=' + value.projectIdSearch)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.customerName && filter.push('&customerName=' + value.customerName)
                                                            value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).format('YYYYMM'))
                                                            var URL = `${ureport}excel?_u=minio:zxCrProjectEvaluationReports.ureport.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=项目季度信用评价报表`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button>
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
                                title: '进场日期',
                                width: 130,
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD',
                            }
                        },
                        {
                            table: {
                                title: '退场日期',
                                width: 130,
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format: 'YYYY-MM-DD',
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
                                title: '项目经理信用评价打分',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'totalScore',
                                key: 'totalScore'
                            }
                        },
                        {
                            table: {
                                title: '有无直接降D行为',
                                width: 150,
                                dataIndex: 'isDropToD',
                                key: 'isDropToD',
                                render: (h) => {
                                    if (h === '1') {
                                        return '有'
                                    } else {
                                        return '无'
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