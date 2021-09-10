import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal, message as Msg, Spin } from "antd";
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
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
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
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId, loading } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
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
                                    field: 'companyId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: {
                                            jurisdiction
                                        }
                                    },
                                    hide: (lockProjectId !== 'all' && lockProjectId !== '') || ext1 !== '1' ? true : false,
                                    placeholder: '请选择',
                                    required: true,
                                    span: 8
                                },
                                {
                                    label: '项目名称',
                                    field: 'projectId',
                                    type: 'select',
                                    showSearch: true,
                                    hide: (lockProjectId !== 'all' && lockProjectId !== '') || ext1 === '3' || ext1 === '4' ? true : false,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    dependencies: ['companyId'],
                                    dependenciesReRender: true,
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProjectList',
                                        otherParams: (obj) => {
                                            return {
                                                companyId: ext1 === '1' ? obj?.form?.getFieldValue('companyId') : jurisdiction
                                            }
                                        }
                                    },
                                    placeholder: '请选择',
                                    required: true,
                                    span: 8
                                },
                                {
                                    label: '组织机构代码',
                                    field: 'orgCertificate',
                                    type: 'string',
                                    span: 8
                                },
                                {
                                    label: '协作单位名称',
                                    field: 'customerId',
                                    type: 'string',
                                    span: 8
                                },
                                {
                                    label: '合同编号',
                                    type: 'string',
                                    field: 'contractNo',
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
                                                if (!value.companyId) {
                                                    Msg.warn("请选择所属公司")
                                                    return
                                                }
                                                if (!value.projectId) {
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
                                                            value.contractNo && filter.push('&contractNo=' + value.contractNo)
                                                            value.customerId && filter.push('&customerId=' + value.customerId)
                                                            value.companyId && filter.push('&companyId=' + value.companyId)
                                                            value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                            value.projectId && filter.push('&projectId=' + value.projectId)
                                                            value.period && filter.push('&period=' + value.period)
                                                            var URL = `${ureport}excel?_u=minio:UnCheckCreditEvaItemReports.ureport.xml&access_token=${access_token}${filter.join('')}&_n=季末未评价单位名录`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    value.contractNo && filter.push('&contractNo=' + value.contractNo)
                                                    value.customerId && filter.push('&customerId=' + value.customerId)
                                                    value.companyId && filter.push('&companyId=' + value.companyId)
                                                    value.orgCertificate && filter.push('&orgCertificate=' + value.orgCertificate)
                                                    value.projectId && filter.push('&projectId=' + value.projectId)
                                                    value.period && filter.push('&period=' + value.period)
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
                        apiName: 'getUnCheckCreditEvaItemReports',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: jurisdiction,
                                contractNo: selectData?.contractNo,
                                projectId: selectData?.projectId,
                                companyId: selectData?.companyId,
                                customerId: selectData?.customerId,
                                orgCertificate: selectData?.orgCertificate,
                                period: selectData?.period ? selectData.period : null
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
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '所在项目',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                        },
                        {
                            table: {
                                title: '项目经理姓名',
                                dataIndex: 'firstPrincipal',
                                key: 'firstPrincipal',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '所在单位',
                                dataIndex: 'companyId',
                                key: 'companyId',
                                type: 'select',
                                width: 200,
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject',
                                }
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
                            }} src={`${ureport}preview?_u=minio:UnCheckCreditEvaItemReports.ureport.xml&_t=1,6&access_token=${access_token}${this.state.filter.join('')}
                            `}></iframe>
                        </Spin>
                    }

                </Modal>
            </div>
        );
    }
}

export default index;