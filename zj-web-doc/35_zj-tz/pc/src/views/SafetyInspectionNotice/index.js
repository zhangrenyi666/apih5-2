import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import s from "./style.less";

const config = {

    antd: {
        rowKey: function (row) {
            return row.checkBulletinId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName : '',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName
        }
    }
    componentDidMount() { }
    render() {
        const { realName, proNameId, proNameVal } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzSafetyQualityCheckBulletinList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'checkBulletinId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                hide: true,
                                initialValue: proNameId
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                field: 'projectName',
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '请输入',
                                initialValue: () => {
                                    return proNameVal
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },

                        {
                            table: {
                                title: '子项目名称',
                                filter: true,
                                dataIndex: 'subprojectsName',
                                key: 'subprojectsName'
                            },
                            form: {
                                type: "select",
                                label: "子项目名称",
                                editDisabled: true,
                                field: "subprojectsId",
                                placeholder: "请输入",
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: proNameId
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '标题',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'checkBulletinTitle',
                                key: 'checkBulletinTitle',
                            },
                            form: {
                                label: '标题',
                                type: 'string',
                                field: 'checkBulletinTitle',
                                placeholder: '请输入',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '检查单位',
                                width: 120,
                                tooltip: 10,
                                dataIndex: 'checkUnit',
                                key: 'checkUnit',
                            },
                            form: {
                                label: '检查单位',
                                type: 'string',
                                field: 'checkUnit',
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '检查日期',
                                dataIndex: 'checkDate',
                                width: 100,
                                key: 'checkDate',
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY-MM')
                                    }
                                    return ''
                                }
                            },
                            form: {
                                type: 'date',
                                field: 'checkDate',
                                placeholder: '请输入',
                                required: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '检查通报内容',
                                dataIndex: 'checkBulletinContent',
                                width: 180,
                                tooltip: 50,
                                key: 'checkBulletinContent',
                            },
                            form: {
                                label: '检查通报内容',
                                type: 'textarea',
                                field: 'checkBulletinContent',
                                placeholder: '请输入',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '整改落实情况',
                                dataIndex: 'checkRectificationImplementation',
                                width: 180,
                                tooltip: 50,
                                key: 'checkRectificationImplementation',
                            },
                            form: {
                                label: '整改落实情况',
                                type: 'textarea',
                                field: 'checkRectificationImplementation',
                                placeholder: '请输入',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'checkBulletinFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '安全质量检查通报'
                                    }
                                },
                                initialValue: [],
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'checkBulletinDate',
                                type: 'date',
                                label: '登记日期',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'checkBulletinUser',
                                type: 'string',
                                label: '登记用户',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: realName
                            },
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (proNameId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
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
                />
            </div>
        );
    }
}

export default index;