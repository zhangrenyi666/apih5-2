import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.securityId
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
        const { projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjTzSecurityManagementSystemList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                obj.btnCallbackFn.clearSelectedRows();
                            } else {
                                obj.btnCallbackFn.msg.error('请选择一条数据!');
                                obj.btnCallbackFn.clearSelectedRows();
                            }
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
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'securityId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue: projectId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return projectName
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '子项目名称',
                                dataIndex: 'subprojectsName',
                                key: 'subprojectsName'
                            },
                            form: {
                                field: 'subprojectsId',
                                type: 'select',
                                editDisabled: true,
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: projectId
                                    }
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '标题',
                                width: 300,
                                tooltip: 53,
                                dataIndex: 'securityTitle',
                                key: 'securityTitle',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '内容简介',
                                dataIndex: 'securityContent',
                                width: 200,
                                tooltip: 23,
                                key: 'securityContent'
                            },
                            form: {
                                type: 'textarea',
                                autoSize: {
                                    minRows: 2,
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '登记日期',
                                dataIndex: 'securityRegisterDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                                key: 'securityRegisterDate'
                            },
                            form: {
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '请选择',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '登记用户',
                                width: 100,
                                dataIndex: 'securityRegisterUser',
                                key: 'securityRegisterUser'
                            },
                            form: {
                                type: 'string',
                                initialValue: realName,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'securityFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                initialValue: [],
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '安全管理制度'
                                    }
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;