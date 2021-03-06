import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { Button, Row, Col, Modal } from "antd";
const confirm = Modal.confirm;
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
                                    type: 'string',
                                    label: '????????????',
                                    field: 'equipName',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '??????',
                                    field: 'spec',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '????????????',
                                    field: 'placeName',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'outDate',
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
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.spec && filter.push('&spec=' + value.spec)
                                                            value.placeName && filter.push('&placeName=' + value.placeName)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:?????????????????????????????????(??????).xml&access_token=${access_token}&delFlag=0&ureportFlag=1${filter.join('')}&_n=?????????????????????????????????`;
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
                    antd={{
                        rowKey: 'id',
                        size: 'small'
                    }}
                    paginationConfig={false}
                    isShowRowSelect={false}
                    fetchConfig={{
                        apiName: 'getZxEqOuterEquipListForCar',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : selectData?.orgIDSearch ? null : orgID,
                                orgIDLock: selectData?.orgIDSearch ? null : lockID,
                                orgIDSearch: selectData?.orgIDSearch,
                                comIDSearch: selectData?.comIDSearch,
                                equipName: selectData?.equipName,
                                spec: selectData?.spec,
                                placeName: selectData?.placeName,
                                inDate: selectData?.inDate ? moment(selectData.inDate).valueOf() : null,
                                outDate: selectData?.outDate ? moment(selectData?.outDate).valueOf() : null,
                                ureportFlag: '1'
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
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'startEndDate',
                                width: 150,
                                key: 'startEndDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                dataIndex: 'outfactory',
                                key: 'outfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'outfactoryDate',
                                key: 'outfactoryDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                            },
                            isInForm: false
                        },
                        // {
                        //     table: {
                        //         title: '????????????',
                        //         width: 120,
                        //         dataIndex: 'placeName',
                        //         key: 'placeName', 
                        //         type: 'select',
                        //     },
                        //     form: {
                        //         type: 'select',
                        //         optionConfig: {
                        //             label: 'itemName',
                        //             value: 'itemId'
                        //         },
                        //         fetchConfig: {
                        //             apiName: 'getBaseCodeSelect',
                        //             otherParams: {
                        //                 itemId: 'xingzhengquhuadaima'
                        //             }
                        //         }
                        //     }
                        // },
                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????/??????',
                                width: 150,
                                dataIndex: 'leaseprice',
                                key: 'leaseprice',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????/????????????',
                                width: 180,
                                dataIndex: 'supOperator',
                                key: 'supOperator'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;