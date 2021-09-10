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
                                label: '工程项目',
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
                                placeholder: '请选择',
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
                                label: '仓库',
                                field: 'whOrgID',
                                type: 'select',
                                allowClear: true,
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id',
                                },
                                // parent: 'orgID',//表单内的联动：一直闪，注释了，联动竟然也好用
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseByParentOrgIDList',
                                    params: {
                                        parentOrgID: 'orgID'
                                    }
                                },
                                placeholder: '请选择',
                                span: 8,
                                onChange: (val, rowData) => {
                                    this.formHasTicket.form.setFieldsValue({
                                        resourceId: ''
                                    })
                                }
                            },
                            {
                                label: '物资大类',
                                field: 'resourceId',
                                type: 'select',
                                allowClear: true,
                                optionConfig: {
                                    label: 'resourceName',
                                    value: 'resourceId',
                                },
                                dependenciesReRender: true,//多个依赖-配置
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
                                placeholder: '请选择',
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
                                    Msg.warn('请选择仓库！')
                                }
                            } else {
                                Msg.warn('请选择工程项目！')
                            }
                        }}>查询</Button></div></Col>
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
                                    title: '物资编号',
                                    width: 260,
                                    dataIndex: 'resCode',
                                    key: 'resCode',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '资源名称',
                                    width: 260,
                                    dataIndex: 'resName',
                                    key: 'resName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '规格型号',
                                    dataIndex: 'spec',
                                    key: 'spec'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },

                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '库存数量',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '库存单价',
                                    dataIndex: 'stockPrice',
                                    key: 'stockPrice'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '库存金额',
                                    dataIndex: 'stockAmt',
                                    key: 'stockAmt'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
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