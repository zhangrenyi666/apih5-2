import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.superiorUnitQualityId
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
                        apiName: 'getZjTzSuperiorUnitQualityDutyList',
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
                                field: 'superiorUnitQualityId',
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
                                dataIndex: 'superiorUnitQualityTitle',
                                key: 'superiorUnitQualityTitle',
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
                                title: '备注',
                                width: 300,
                                tooltip: 80,
                                dataIndex: 'superiorUnitQualityContent',
                                key: 'superiorUnitQualityContent'
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
                                width: 100,
                                dataIndex: 'superiorUnitQualityDate',
                                format: 'YYYY-MM-DD',
                                key: 'superiorUnitQualityDate'
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
                                dataIndex: 'superiorUnitQualityUser',
                                key: 'superiorUnitQualityUser'
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
                                field: 'unitQualityFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                initialValue: [],
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '与上级单位签订的质量责任书'
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