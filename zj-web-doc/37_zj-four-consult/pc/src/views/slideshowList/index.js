import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    fetchConfig: {
        apiName: 'getZjSjConsultBannerList',
    },
    antd: {
        rowKey: function (row) {
            return row.bannerId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
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
                        apiName: 'addZjSjConsultBanner',
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
                        apiName: 'updateZjSjConsultBanner',
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
                apiName: 'batchDeleteUpdateZjSjConsultBanner',
            },
        }
    ],
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'bannerId',
                type: 'string',
                hide: true,
            }
        },
        {
            table: {
                title: '图片名称',
                dataIndex: 'attachmentList',
                key: 'attachmentList',
                onClick:'detail',
                render:(data) => {
                    return data.length ? data[0].name : '';
                }
            },
            form: {
                label: '图片',
                field: 'attachmentList',
                type: 'images',
                accept: 'image/*',
                max: 1,
                required:true,
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
                },
            }
        },
        {
            table: {
                title: '图片描述',
                dataIndex: 'imgDescribe',
                key: 'imgDescribe'
            },
            form: {
                type: 'textarea',
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
            table: {
                title: '点击链接',
                dataIndex: 'clickLink',
                key: 'clickLink'
            },
            form: {
                type: 'url',
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
            table: {
                title: '创建者',
                dataIndex: 'modifyUserName',
                key: 'modifyUserName'
            },
            isInForm:false
        },
        {
            table: {
                title: '创建时间',
                dataIndex: 'modifyTime',
                key: 'modifyTime',
                format:'YYYY-MM-DD'
            },
            isInForm:false
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