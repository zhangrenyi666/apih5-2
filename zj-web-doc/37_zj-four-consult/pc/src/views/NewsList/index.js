import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    fetchConfig: {
        apiName: 'getZjSjConsultNewsList',
    },
    antd: {
        rowKey: function (row) {
            return row.newsId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline',
    actionBtns: [
        {
            name: 'add',
            icon: 'plus',
            type: 'primary',
            label: '新增',
            formBtns: [
                {
                    name: 'cancel',
                    type: 'dashed',
                    label: '取消',
                },
                {
                    name: 'submit',
                    type: 'primary',
                    label: '提交',
                    fetchConfig: {
                        apiName: 'addZjSjConsultNews',
                    }
                }
            ]
        },
        {
            name: 'edit',
            type: 'primary',
            label: '修改',
            onClick: (obj) => {
                obj.btnCallbackFn.clearSelectedRows();
            },
            formBtns: [
                {
                    name: 'cancel',
                    type: 'dashed',
                    label: '取消',
                },
                {
                    name: 'submit',
                    type: 'primary',
                    label: '提交',
                    fetchConfig: {
                        apiName: 'updateZjSjConsultNews',
                    }
                }
            ]
        },
        {
            name: 'del',
            icon: 'delete',
            type: 'danger',
            label: '删除',
            fetchConfig: {
                apiName: 'batchDeleteUpdateZjSjConsultNews',
            },
        }
    ],
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'newsId',
                type: 'string',
                hide: true,
            }
        },
        {
            isInSearch: true,
            table: {
                title: '标题',
                dataIndex: 'title',
                key: 'title',
                onClick: 'detail',
                width: 400,
                tooltip: 200,
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                formItemLayout: {
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
            isInTable: false,
            form: {
                label: '内容',
                field: 'content',
                type: 'richtext',
                fetchConfig: {
                    //必须配置  上传图片地址
                    uploadUrl: window.configs.domain + 'upload' //***必传
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
                },
            }
        },
        {
            table: {
                title: '发布日期',
                dataIndex: 'releaseTime',
                key: 'releaseTime',
                format: 'YYYY-MM-DD HH:mm:ss',
                width: 200,
            },
            form: {
                label: '发布日期',
                type: 'date',
                format: 'YYYY-MM-DD HH:mm:ss',
                required: true,
                placeholder: '请选择',
                formItemLayout: {
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
            isInTable: false,
            form: {
                label: '附件',
                field: 'attachmentList',
                type: 'files',
                fetchConfig: {
                    apiName: window.configs.domain + 'upload'
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
            }
        },
        {
            table: {
                title: '创建者',
                dataIndex: 'modifyUserName',
                key: 'modifyUserName',
                width: 200,
            },
            isInForm: false
        },
        {
            table: {
                title: '创建时间',
                dataIndex: 'modifyTime',
                key: 'modifyTime',
                format: 'YYYY-MM-DD',
                width: 200,
            },
            isInForm: false
        }
    ]
}
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
                />
            </div>
        );
    }
}

export default index;