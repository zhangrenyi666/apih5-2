import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
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
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getProInvBasicList'
                    }}
                    method={{
                        editClick: () => {
                            this.table.clearSelectedRows();
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         onClick:'bind:editClick',
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
                    //                     apiName: 'updateProInvBasic'
                    //                 }
                    //             },
                    //         ]
                    //     },
                    // ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: 80,
                                align: 'center',
                                title: '序号',
                                dataIndex: 'no',
                                key: 'no',   
                                render: (data, rows, index) => {
                                    return index + 1;
                                }
                            },
                        },
                        {
                            table: {
                                title: '集采项目名称',
                                dataIndex: 'proName',
                                key: 'proName',
                                width:'50%',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                field:'proName',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '本项目名称',
                                filter: true,
                                width:'50%',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch:true,
                                required: false,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                    // apiName: "getZjTzProManageListNotUpdated"
                                },
                                placeholder: '请选择',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;