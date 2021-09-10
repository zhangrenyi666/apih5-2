import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Tooltip, Modal } from "antd";
import s from "./style.less";
import downLoad from "../modules/download";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.proEventId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
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
    constructor(props) {
        super(props);
        this.state = {
            projectId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,
            projectName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName,
        }
    }
    componentDidMount() {

    }
    render() {
        const { projectId } = this.state;
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
                        apiName: 'getZjTzProEventList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    {...config}
                    desc={'填报：控制性工程以及全线工程的开工、合龙、贯通、交工、竣工等重要节点以及局主要领导和中国交建主要领导带队检查和当地政府省部级领导检查等事宜'}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'proEventId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '管理单位',
                                // filter: true,
                                dataIndex: 'companyName',
                                key: 'companyName',
                                tooltip: 15,
                                width: 100,
                            },
                            isInForm:false,
                            form: {
                                field: 'companyId',
                                type: 'select',
                                showSearch:true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'guanLiDanWei'
                                    }
                                },
                                placeholder: '请选择',
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
                            },
                        },
                        {
                            table: {
                                title: '项目名称',
                                filter: true,
                                onClick: 'detail',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                tooltip: 30,
                                width: 200,
                            },
                            form: {
                                label: '项目名称',
                                field: 'projectId',
                                type: 'select',
                                required: true,
                                initialValue: projectId,
                                addDisabled: true,
                                editDisabled: true,
                                showSearch:true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
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
                            },
                        },
                        {
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                filter: true,
                                tooltip: 30,
                                width: 200,
                                key: 'title',
                            },
                            form: {
                                label: '标题',
                                type: 'string',
                                field: 'title',
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
                            }
                        },
                        {
                            table: {
                                title: '发生时间',
                                dataIndex: 'happenTime',
                                key: 'happenTime',
                                width: 100,
                                format:'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required:true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '主要内容',
                                dataIndex: 'content',
                                width: 200,
                                key: 'content',
                                render: (data) => {
                                    if (data) {
                                        data = data.replace(/(\n)/g, "");
                                        data = data.replace(/(\t)/g, "");
                                        data = data.replace(/(\r)/g, "");
                                        data = data.replace(/<\/?[^>]*>/g, "");
                                        data = data.replace(/\s*/g, "");
                                        data = data.replace(/&nbsp;/g, "");
                                        return <div>
                                            <Tooltip title={data}>
                                                <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                            </Tooltip>

                                        </div>
                                    } else {
                                        return ''
                                    }
                                }
                            },
                            form: {
                                type: "richtext",
                                fetchConfig: {
                                    uploadUrl: window.configs.domain + 'upload'
                                },
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
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '项目大事记'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '登记日期',
                                width: 100,
                                dataIndex: 'registerDate',
                                format: 'YYYY-MM-DD',
                                key: 'registerDate'
                            },
                            form: {
                                field: 'registerDate',
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
                            table: {
                                title: '登记用户',
                                width: 100,
                                dataIndex: 'registerPerson',
                                key: 'registerPerson'
                            },
                            form: {
                                field: 'registerPerson',
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
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        },
                        {
                            table: {
                                title: '是否在首页广而告之',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '否'
                                    } else if (data === '1') {
                                        return '是'
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "homeShow",
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ],
                                initialValue: () => {
                                    return '0';
                                }
                            }
                        },
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].homeShow === '0') {
                                    confirm({
                                        title: "确定广而告之到首页么？",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('toHomeShowZjTzProEvent', obj.selectedRows[0]).then(
                                                ({ success, message, data }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
                                } else {
                                    Msg.warn('已在广而告之中的数据不可再进行广而告之！')
                                }
                            } else {
                                Msg.warn('请选择一条数据');
                            }
                        },
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].homeShow === '1') {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('batchRecallZjTzProEvent', obj.selectedRows).then(
                                                ({ success, message, data }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
                                } else {
                                    Msg.warn('未在广而告之中的数据不可撤回！')
                                }
                            } else {
                                Msg.warn('请选择一条数据');
                            }
                        },
                        exportOnclick:(obj) => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let body = {
                                fileName: '一公局集团投资项目大事记统计表',
                                projectId:projectId
                            }
                            let URL = `${domain + "exportZjTzProEventList"}`;
                            confirm({
                                content: '确定导出数据吗?',
                                centered: true,
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
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