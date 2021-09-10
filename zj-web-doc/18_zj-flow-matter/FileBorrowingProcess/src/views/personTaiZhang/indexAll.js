import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import ProgressCheck from './formJu';
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.archivesLibraryId
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
    },
    isShowRowSelect: false
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
                        apiName: 'getZjFlowArchivesLibraryList'
                    }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'archivesLibraryId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '单号',
                                dataIndex: 'autoNum',
                                key: 'autoNum',
                                onClick: "Component",
                                Component: (obj) => {
                                    console.log(obj.rowData);
                                    return <ProgressCheck {...obj} flowData={obj.rowData.archivesLibraryId} clickAction={'detail'} />
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '日期',
                                dataIndex: 'createTime',
                                width: 100,
                                format: 'YYYY-MM-DD',
                                key: 'createTime'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '份数',
                                width: 100,
                                dataIndex: 'borrowCopy',
                                key: 'borrowCopy'
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'returnStatus',
                                key: 'returnStatus',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: '未归还', value: '0' },
                                    { label: '已归还', value: '1' },
                                    { label: '结束', value: '2' }
                                ],
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",//bubble--tile
                                width: 120,
                                btns: [
                                    {
                                        name: 'guihuan',
                                        type: 'primary',
                                        label: '确认',
                                        render: (rowData) => {
                                            if (rowData.rowData.returnStatus === '1') {
                                                return '<a>确认</a>';
                                            } else {
                                                return ''
                                            }
                                        },
                                        onClick: (obj) => {
                                            const { myFetch } = this.props;
                                            confirm({
                                                content: '是否确认?',
                                                onOk: () => {
                                                    myFetch('returnZjFlowArchivesLibraryForTotal', { archivesLibraryId: obj.rowData.archivesLibraryId }).then(
                                                        ({ success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                this.table.refresh();
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );

                                                }
                                            });

                                        }
                                    },
                                    {
                                        name: 'tuihui',
                                        type: 'primary',
                                        label: '退回',
                                        render: (rowData) => {
                                            if (rowData.rowData.returnStatus === '1') { 
                                                return '<a>退回</a>';
                                            }else {
                                                return ''
                                            }
                                        },
                                        onClick: (obj) => {
                                            const { myFetch } = this.props;
                                            confirm({
                                                content: '是否退回?',
                                                onOk: () => {
                                                    myFetch('backZjFlowArchivesLibraryForTotal', { archivesLibraryId: obj.rowData.archivesLibraryId }).then(
                                                        ({ success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                this.table.refresh();
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );

                                                }
                                            });

                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;