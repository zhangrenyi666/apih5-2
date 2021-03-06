import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col } from "antd";
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
            lockProject
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockID, orgID, lockProject } = this.state;
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
                                    field: 'comIDSearch',
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
                                    label: '????????????',
                                    field: 'orgIDSearch',
                                    type: 'select',
                                    showSearch: true,
                                    hide: () => {
                                        if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all') {
                                            return false
                                        }
                                        return true
                                    },
                                    dependencies: ['comIDSearch'],
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
                                                    companyId: obj?.form?.getFieldValue('comIDSearch')
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
                                    label: '????????????',
                                    type: 'select',
                                    field: 'eduLevel',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: "??????",
                                            value: "0"
                                        },
                                        {
                                            label: "??????",
                                            value: "1"
                                        },
                                        {
                                            label: "??????",
                                            value: "2"
                                        },
                                        {
                                            label: "??????",
                                            value: "3"
                                        },
                                        {
                                            label: "??????",
                                            value: "4"
                                        },
                                        {
                                            label: "??????",
                                            value: "5"
                                        },
                                        {
                                            label: "??????",
                                            value: "6"
                                        },
                                        {
                                            label: "??????",
                                            value: "7"
                                        }
                                    ],
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '??????',
                                    type: 'select',
                                    field: 'title',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'resCode'
                                    },
                                    optionData: [
                                        {
                                            label: '???',
                                            resCode: '01'
                                        },
                                        {
                                            label: '??????',
                                            resCode: '02'
                                        },
                                        {
                                            label: '??????',
                                            resCode: '03'
                                        },
                                        {
                                            label: '??????',
                                            resCode: '04'
                                        }
                                    ],
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '??????',
                                    field: 'name',
                                    span: 8
                                },
                                {
                                    type: 'select',
                                    label: '??????',
                                    field: 'pos',
                                    span: 8,
                                    optionConfig: {
                                        label: 'jobName',
                                        value: 'jobName'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqMachineJobsList',
                                    },
                                },
                                {
                                    type: 'date',
                                    label: '??????????????????',
                                    field: 'bzDateStart',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '??????????????????',
                                    field: 'bzDateEnd',
                                    span: 8
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
                                                            !lockID && !value.orgIDSearch && filter.push('&orgID=' + orgID)
                                                            !value.orgIDSearch && lockID && filter.push('&orgIDLock=' + lockID)
                                                            value.orgIDSearch && filter.push('&orgIDSearch=' + value.orgIDSearch)
                                                            value.comIDSearch && filter.push('&comIDSearch=' + value.comIDSearch)
                                                            value.title && filter.push('&title=' + value.title)
                                                            value.bzDateStart && filter.push('&bzDateStart=' + new Date(value.bzDateStart._d).getTime())
                                                            value.bzDateEnd && filter.push('&bzDateEnd=' + new Date(value.bzDateEnd._d).getTime())
                                                            value.name && filter.push('&name=' + value.name)
                                                            value.pos && filter.push('&pos=' + value.pos)
                                                            value.eduLevel && filter.push('&eduLevel=' + value.eduLevel)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEemployeeList.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=?????????????????????`;
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
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    pageSize={9999}
                    {...config}
                    fetchConfig={{
                        apiName: 'ureportZxEqEemployeeListIdle',
                        otherParams: async () => {
                            let selectData = await this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : selectData?.orgIDSearch ? null : orgID,
                                orgIDLock: selectData?.orgIDSearch ? null : lockID,
                                orgIDSearch: selectData?.orgIDSearch,
                                comIDSearch: selectData?.comIDSearch,
                                title: selectData?.title,
                                name: selectData?.name,
                                pos: selectData?.pos,
                                eduLevel: selectData?.eduLevel,
                                bzDateStart: selectData?.bzDateStart ? moment(selectData.bzDateStart).valueOf() : null,
                                bzDateEnd: selectData?.bzDateEnd ? moment(selectData.bzDateEnd).valueOf() : null,
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
                                title: '????????????',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'name',
                                width: 200,
                                key: 'name'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
                                dataIndex: 'age',
                                key: 'age'
                            },
                            isInForm: false
                        },  
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'eduLevel',
                                key: 'eduLevel',
                                render: (h) => {
                                    if (h === '0') {
                                        return '??????'
                                    } else if (h === '1') {
                                        return '??????'
                                    } else if (h === '2') {
                                        return '??????'
                                    } else if (h === '3') {
                                        return '??????'
                                    } else if (h === '4') {
                                        return '??????'
                                    } else if (h === '5') {
                                        return '??????'
                                    } else if (h === '6') {
                                        return '??????'
                                    } else if (h === '7') {
                                        return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'title',
                                key: 'title',
                                render: (h) => {
                                    if (h === '01') {
                                        return '???'
                                    } else if (h === '02') {
                                        return '??????'
                                    } else if (h === '03') {
                                        return '??????'
                                    } else if (h === '04') {
                                        return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'pos',
                                key: 'pos'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'posAge',
                                key: 'posAge'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '??????',
                                width: 120,
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