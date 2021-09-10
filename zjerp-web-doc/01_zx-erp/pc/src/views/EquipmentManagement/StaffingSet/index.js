import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect:true
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            departmentName:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectName : curCompany?.companyName) : curCompany?.projectName,
            companyId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
            companyName:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyName : curCompany?.companyName) : curCompany?.companyName
        }
    }
    render() {
        const { departmentId, departmentName, companyId, companyName } = this.state;
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
                        apiName: 'getZxEqEmployeeNumList',
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
                                tableField: "StaffingSet"
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
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                initialValue:companyId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                initialValue:companyName,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                initialValue:departmentId,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick:'detail',
                                filter:true
                            },
                            form: {
                                type: 'string',
                                addDisabled:true,
                                editDisabled:true,
                                initialValue:() => {
                                    return departmentName;
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '年份',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                filter: true,
                                format:"YYYY"
                            },
                            form: {
                                type: 'year',
                                required: true,
                                editDisabled: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '年末职工数（个）',
                                dataIndex: 'employeenum',
                                key: 'employeenum'
                            },
                            form: {
                                type: 'number',
                                min:0,
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '其中工人数(个）',
                                dataIndex: 'labournum',
                                key: 'labournum'
                            },
                            form: {
                                type: 'number',
                                required: true,
                                min:0,
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