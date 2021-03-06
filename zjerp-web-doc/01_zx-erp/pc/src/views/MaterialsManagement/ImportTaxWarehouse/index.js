import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin } from "antd";
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
        this.state = {
            orgID: '',
            whOrgID: '',
            loading: false,
            loadingSearch: false,
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() { }
    render() {
        const { orgID, whOrgID, resourceId, lockProjectId } = this.state;
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else {

            }
        }
        return (
            <div>
                <Spin tip="Loading..." spinning={this.state.loading}>
                    <Spin spinning={this.state.loadingSearch}><Row><Col span={18}><QnnForm
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
                                label: '????????????',
                                field: 'orgID',
                                type: 'select',
                                allowClear: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '?????????',
                                span: 8,
                                children: [
                                    {
                                        field: 'whOrgID'
                                    },
                                    {
                                        field: 'resourceId'
                                    },
                                ],
                                onChange: (val, rowData) => {
                                    this.formHasTicket.form.setFieldsValue({
                                        whOrgID: '',
                                        resourceId: ''
                                    })
                                }
                            },
                            {
                                label: '??????',
                                field: 'whOrgID',
                                type: 'select',
                                allowClear: true,
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id',
                                },
                                // parent: 'orgID',//??????????????????????????????????????????????????????????????????
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseByParentOrgIDList',
                                    params: {
                                        parentOrgID: 'orgID'
                                    }
                                },
                                placeholder: '?????????',
                                span: 8,
                                onChange: (val, rowData) => {
                                    this.formHasTicket.form.setFieldsValue({
                                        resourceId: ''
                                    })
                                }
                            },
                            {
                                label: '????????????',
                                field: 'resourceId',
                                type: 'select',
                                allowClear: true,
                                optionConfig: {
                                    label: 'resourceName',
                                    value: 'resourceId',
                                },
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['whOrgID', 'orgID'],
                                fetchConfig: {
                                    apiName: 'getZxSkStockResCategoryDataList',
                                    otherParams: (val) => {
                                        let whOrgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (this.formHasTicket?.form) {
                                            let aa = this.formHasTicket.form.getFieldsValue();
                                            whOrgIDVal = aa.whOrgID;
                                            outOrgIDVal = aa.orgID;
                                        } else {
                                            whOrgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            whOrgID: whOrgIDVal,
                                            orgID: outOrgIDVal,
                                        }
                                    }
                                },
                                placeholder: '?????????',
                                span: 8
                            }
                        ]}
                    /></Col>
                        <Col span={6}><div style={{ padding: '11px 11px 11px 71px' }}><Button type="primary" onClick={() => {
                            let value = this.formHasTicket.form.getFieldsValue();
                            if (value.orgID) {
                                if (value.whOrgID) {
                                    // this.setState({
                                    //     orgID: '',
                                    //     whOrgID: '',
                                    //     resourceId:''
                                    // }, () => {
                                    this.table.setPaging({
                                        page: 1,
                                        limit: 10
                                    })
                                    this.setState({
                                        orgID: value.orgID,
                                        whOrgID: value.whOrgID,
                                        resourceId: value.resourceId
                                    }, () => {
                                        this.table.refresh();
                                    })
                                    // })
                                } else {
                                    Msg.warn('??????????????????')
                                }
                            } else {
                                Msg.warn('????????????????????????')
                            }
                        }}>??????</Button></div></Col>
                    </Row></Spin>

                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={orgID && whOrgID ? {
                            apiName: 'getZxSkStockDataList',
                            otherParams: {
                                // companyID:companyId,
                                orgID: orgID,
                                whOrgID: whOrgID,
                                resourceId: resourceId
                            }
                        } : {}}
                        actionBtns={[]}
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
                                isInTable: false,
                                form: {
                                    field: 'cbsType',
                                    type: 'string',
                                    initialValue: '4',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'orgID',
                                    type: 'string',
                                    initialValue: orgID,
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    width: 260,
                                    dataIndex: 'resCode',
                                    key: 'resCode',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    width: 260,
                                    dataIndex: 'resName',
                                    key: 'resName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'spec',
                                    key: 'spec'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },

                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'stockPrice',
                                    key: 'stockPrice'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'stockAmt',
                                    key: 'stockAmt'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            }
                        ]}
                    />
                </Spin>
            </div>
        );
    }
}

export default index;