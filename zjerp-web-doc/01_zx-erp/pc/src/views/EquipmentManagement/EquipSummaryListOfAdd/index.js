import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxEqEquipLimitPriceId',
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
            useOrgID: (curCompany?.ext1 === '1' || curCompany?.ext1 === '2') ? curCompany?.companyId : curCompany?.projectId,
            lockProject
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockID, useOrgID, lockProject } = this.state;
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
                                    label: '??????',
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
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    label: '??????',
                                    field: 'purDate',
                                    type: 'year',
                                    placeholder: '?????????',
                                    span: 8,
                                    initialValue: new Date()
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                this.table.refresh();
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            !lockID  && filter.push('&useOrgID=' + useOrgID)
                                                            lockID && filter.push('&useOrgIDLock=' + lockID)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:addZxEqEquipTotalList.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=?????????????????????`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>??????</Button></div>
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
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    pageSize={9999}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqEquipListForReport',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                useOrgID: lockID ? null : useOrgID,
                                useOrgIDLock: lockID ? lockID : null,
                                companyIdSearch: selectData?.companyIdSearch,
                                purDate: selectData?.purDate ? moment(selectData.purDate).valueOf() : null,
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
                                title: '???',
                                dataIndex: 'abcTypeName',
                                key: 'abcTypeName',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 170,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'model',
                                key: 'model',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 170,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate',
                                width: 120,
                                format: 'YYYY-MM-DD',

                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'mainpower',
                                key: 'mainpower',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                width: 150,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'purDate',
                                key: 'purDate',
                                format: 'YYYY-MM-DD',

                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'depreciation',
                                key: 'depreciation',
                                render: (h) => {
                                    if (h === '5') {
                                        return '5???'
                                    } else if (h === '10') {
                                        return '10???'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 200,
                                dataIndex: 'remark',
                                key: 'remark'
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