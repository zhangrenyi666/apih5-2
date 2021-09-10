import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'filterOrgId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render() {
        const { departmentId } = this.state;
        return (
            <div>
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
                        apiName: 'getZxCmFilterOrgList',
                        otherParams:{
                           orgID:departmentId 
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ProjectBusinessFiltering"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'filterOrgId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                onClick:'detail'
                            },
                            form: {
                                field:'orgID',
                                type: 'select',
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields:{
                                        companyId:"companyId",
                                        companyName:"companyName",
                                        orgName:'orgName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractListByStatus',
                                    otherParams:{
                                        orgID:departmentId
                                    }
                                },
                                required: true,
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '业务类型',
                                dataIndex: 'businessType',
                                key: 'businessType',
                                filter: true,
                                type:"select"
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '工程类合同',
                                        value: '0'
                                    },
                                    {
                                        label: '工程类中期结算',
                                        value: '1'
                                    },
                                    {
                                        label: '工程类最终结算',
                                        value: '2'
                                    },
                                    {
                                        label: '物资采购类中期结算',
                                        value: '3'
                                    },
                                    {
                                        label: '物资采购类最终结算',
                                        value: '4'
                                    },
                                    {
                                        label: '工程类补充协议',
                                        value: '5'
                                    },
                                    {
                                        label: '开放工程类合同400万发起云电商限制',
                                        value: '6'
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;