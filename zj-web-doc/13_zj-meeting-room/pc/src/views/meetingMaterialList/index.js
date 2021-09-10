import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjMeetingFileInfoAddFileList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.fileInfoId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
};
class Index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjMeetingFileInfoAddFile',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diy',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '发送消息',
                            onClick: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    obj.btnCallbackFn.confirm({
                                        title: "确认发送消息?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        centered: true,
                                        onOk: () => {
                                            this.props.myFetch('zjMeetingFileSendMsg', {title:obj.selectedRows[0].title}).then(({ success, data, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    });
                                }else{
                                    Msg.error('请选择一条数据发送！');
                                }
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjMeetingFileInfo',
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'fileInfoId',
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '标题', //表头标题
                                dataIndex: 'title', //表格里面的字段
                                key: 'title',//表格的唯一key
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjMeetingFileEnclosureList',
                                type: 'files',
                                required: true,
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                }
                            }
                        },
                        {
                            table: {
                                title: '创建者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '创建日期',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default Index;