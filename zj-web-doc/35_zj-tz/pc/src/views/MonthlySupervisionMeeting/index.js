import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import s from "./style.less";

const config = {

    antd: {
        rowKey: function (row) {
            return row.monthlyMeetingId
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
                        apiName: 'getZjTzSupervisorMonthlyMeetingList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'monthlyMeetingId',
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
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '???????????????',
                                filter: true,
                                dataIndex: 'subprojectsName',
                                key: 'subprojectsName'
                            },
                            form: {
                                type: "select",
                                label: "???????????????",
                                editDisabled: true,
                                field: "subprojectsId",
                                placeholder: "?????????",
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
                            isInSearch: false,
                            table: {
                                title: '??????',
                                dataIndex: 'monthlyMeetingDate',
                                width: 120,
                                // format:'YYYY-MM',
                                key: 'monthlyMeetingDate',
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY-MM')
                                    }
                                    return ''
                                }
                            },
                            form: {
                                type: 'month',
                                field: 'monthlyMeetingDate',
                                placeholder: '?????????',
                                required: true,
                                editDisabled: true,
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
                                title: '????????????',
                                dataIndex: 'monthlyMeetingContent',
                                width: 300,
                                tooltip: 80,
                                key: 'result',
                            },
                            form: {
                                label: '????????????',
                                type: 'textarea',
                                field: 'monthlyMeetingContent',
                                placeholder: '?????????',
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
                                label: '??????',
                                field: 'monthlyMeetingFileList',
                                initialValue: [],
                                type: 'files',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????????????????'
                                    }
                                },
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
                                title: '????????????',
                                width: 100,
                                dataIndex: 'createTime',
                                key: 'createTime',
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY-MM-DD')
                                    }
                                    return ''
                                }
                            },
                            form: {
                                field: 'createTime',
                                type: 'date',
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'monthlyMeetingUser',
                                key: 'monthlyMeetingUser',
                            },
                            form: {
                                field: 'monthlyMeetingUser',
                                type: 'string',
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                obj.btnCallbackFn.msg.warn('???????????????????????????')
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