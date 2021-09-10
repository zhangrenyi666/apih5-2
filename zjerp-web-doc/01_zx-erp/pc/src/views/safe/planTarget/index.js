import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'zxSfPlanTargetId',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSfPlanTargetList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '新增',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSfPlanTarget',
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSfPlanTarget',
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZxSfPlanTarget',
                    //         }
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "planTarget"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
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
                                field: 'departmentId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfPlanTargetId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '年份',
                                dataIndex: 'year',
                                key: 'year',
                                filter: true,
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                type: "select",
                            },
                            form: {
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "2020",
                                        value: "0"
                                    },
                                    {
                                        label: "2019",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                filter: true,
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                type: "select",
                            },
                            form: {
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        orgName: 'departmentName',
                                        departmentId: 'departmentId',
                                        companyId: 'companyId'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject',
                                    otherParams: { departmentId }
                                },
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '因工死亡率(‰)',
                                dataIndex: 'deadRate',
                                key: 'deadRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'deadRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                            }
                        },
                        {
                            table: {
                                title: '重伤率(‰)',
                                dataIndex: 'injuresRate',
                                key: 'injuresRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'injuresRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                            }
                        },
                        {
                            table: {
                                title: '轻伤率(‰)',
                                dataIndex: 'slightlyRate',
                                key: 'slightlyRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'slightlyRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                            }
                        },
                        {
                            table: {
                                title: '隐患整改率(%)',
                                dataIndex: 'hidChageRate',
                                key: 'hidChageRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'hidChageRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "备注",
                                field: 'remarks',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8,
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;