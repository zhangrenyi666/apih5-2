import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'middleItemId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 21 }
        }
    },
};
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            lockProjectName: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectName : '',
        }
    }
    render() {
        const { ext1, departmentId, departmentName, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId, lockProjectName } = this.state;
        let jurisdiction = departmentId;
        let jurisdictionName = departmentName;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
            jurisdictionName = lockProjectName;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
                jurisdictionName = companyName;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
                jurisdictionName = projectName;
            } else { }
        }
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
                        apiName: 'getZxStMiddleItemList',
                        otherParams: {
                            balanceType: '1',
                            orgID: jurisdiction
                        }
                    }}
                    method={{
                        onClickFunEdit: (obj) => {
                            this.table.clearSelectedRows();
                        },
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '??????',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 field: 'addsubmit',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxStMiddleItem'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '??????',
                    //         onClick: 'bind:onClickFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxStMiddleItem'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         field: "del",
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZxStMiddleItem',
                    //         },
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "InterimPaymentItemSettings"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'middleItemId',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'orgName',
                                initialValue: jurisdictionName,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'orgID',
                                initialValue: jurisdiction,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyId',
                                initialValue: companyId,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyName',
                                initialValue: companyName,
                                hide: true
                            }
                        },
                        // {
                        //     isInForm:false,
                        //     table: {
                        //         title: '????????????',
                        //         dataIndex: 'companyName',
                        //         key: 'companyName',
                        //         onClick: 'detail'
                        //     }
                        // },
                        {
                            isInForm: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                render: (data, rowData) => {
                                    if (data) {
                                        return data;
                                    } else {
                                        return rowData.companyName;
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'middleCode',
                                key: 'middleCode'
                            },
                            form: {
                                type: 'string',
                                field: 'middleCode',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'middleName',
                                key: 'middleName',
                            },
                            form: {
                                type: 'string',
                                field: 'middleName',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'balanceType',
                                key: 'balanceType',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'balanceType',
                                initialValue: '1',
                                addDisabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: "?????????????????????", value: "3" },
                                    { label: "?????????????????????", value: "1" },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'isDeduction',
                                key: 'isDeduction',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'isDeduction',
                                initialValue: '0',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: "???", value: "0" },
                                    { label: "???", value: "1" },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'status',
                                key: 'status',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'status',
                                initialValue: '0',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: "??????", value: "0" },
                                    { label: "??????", value: "1" },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                field: 'remark'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;