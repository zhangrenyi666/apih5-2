import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import Operation from './operation';
import { message as Msg, Button, Table } from 'antd';
const config = {
    fetchConfig: {
        apiName: 'getZxHwZlTroubleList'
    },
    antd: {
        rowKey: 'troubleId',
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    // isShowRowSelect: true,
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
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}

                    actionBtns={[
                        {
                            field: 'exportZlzgd',
                            name: 'exportZlzgd',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '导出质量整改单',
                            disabled: 'bind:isOnly',
                            onClick: (obj) => {
                                // 1、质量整改回复（exportZjLzehQualityRectificatReply） 参数（troubleTitle、opinionField2、opinionField3）
                                // 2、质量整改单（exportZjLzehQualityRectificatSheet） 参数（troubleTitle、troubleContent、troubleRequire）

                                const {
                                    troubleTitle,
                                    troubleContent,
                                    troubleRequire,
                                    attachmentList1,
                                    troubleId
                                } = obj.selectedRows[0]

                                // const {
                                //     troubleTitle,
                                //     troubleContent,
                                //     troubleRequire
                                // } = obj.qnnTableInstance.getSelectedRows()[0]

                                this.props.myFetch('exportZjLzehQualityRectificatSheet', {
                                    troubleId,
                                    troubleTitle,
                                    troubleContent,
                                    troubleRequire,
                                    fileList:attachmentList1
                                }).then(({ success, data }) => {
                                    if (success) {
                                        window.open(data)
                                        // obj.qnnTableInstance.download(data)
                                    }
                                })
                            }
                        },
                        {
                            field: 'exportZghfd',
                            name: 'exportZghfd',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '导出整改回复单',
                            disabled: 'bind:isOnly',
                            onClick: (obj) => {

                                // 1、质量整改回复（exportZjLzehQualityRectificatReply） 参数（troubleTitle、opinionField2、opinionField3）
                                // 2、质量整改单（exportZjLzehQualityRectificatSheet） 参数（troubleTitle、troubleContent、troubleRequire）

                                const {
                                    troubleTitle,
                                    opinionField2,
                                    opinionField3,
                                    troubleId
                                } = obj.selectedRows[0]

                                this.props.myFetch('exportZjLzehQualityRectificatReply', {
                                    troubleTitle,
                                    opinionField2,
                                    opinionField3
                                }).then(({ success, data }) => {
                                    if (success) {
                                        window.open(data)
                                        // obj.qnnTableInstance.download(data)
                                    }
                                })
                            }
                        },
                    ]}
                    method={{
                        isOnly: (obj) => {
                            const rowData = obj.btnCallbackFn.getSelectedRows()
                            if (rowData.length && rowData.length === 1 && rowData[0].apih5FlowStatus === '2') {
                                return false
                            } else {
                                return true
                            }
                        }
                    }}
                    {...config}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        label: '位置',
                                        field: 'levelNameAll',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '具体段落和部位',
                                        field: 'troubleTitle',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '检查时间',
                                        field: 'modifyTime',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 12,
                                        formItemLayout: {
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
                                    {
                                        label: '整改期限',
                                        field: 'deadline',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 12,
                                        formItemLayout: {
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
                                    {
                                        label: '问题描述',
                                        field: 'troubleContent',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '整改要求',
                                        field: 'troubleRequire',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '问题级别',
                                        field: 'troubleLevel',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '一般',
                                                value: '0'
                                            },
                                            {
                                                label: '严重',
                                                value: '1'
                                            },
                                            {
                                                label: '紧要',
                                                value: '2'
                                            }
                                        ],
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        },
                                        placeholder: '请选择'
                                    },
                                    {
                                        label: '问题类型',
                                        field: 'troubleType',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '外观',
                                                value: '100'
                                            },
                                            {
                                                label: '尺寸',
                                                value: '101'
                                            },
                                            {
                                                label: '坐标',
                                                value: '102'
                                            },
                                            {
                                                label: '工序',
                                                value: '103'
                                            },
                                            {
                                                label: '工艺',
                                                value: '104'
                                            },
                                            {
                                                label: '其它',
                                                value: '105'
                                            }
                                        ],
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        },
                                        placeholder: '请选择'
                                    },
                                    {
                                        label: '整改回复',
                                        field: 'opinionField2',
                                        type: 'textarea',
                                        formatter: (val) => {
                                            if (val) {
                                                if (val.indexOf('<br/>') !== -1) {
                                                    return val.replace("<br/>", "\r\n");
                                                }
                                            }
                                        },
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '复核情况',
                                        field: 'opinionField3',
                                        type: 'textarea',
                                        formatter: (val) => {
                                            if (val) {
                                                if (val.indexOf('<br/>') !== -1) {
                                                    return val.replace("<br/>", "\r\n");
                                                }
                                            }
                                        },
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '检查照片',
                                        field: 'attachmentList1',
                                        type: 'images',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload'
                                        },
                                        span: 12,
                                        formItemLayout: {
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
                                    {
                                        label: '整改照片',
                                        field: 'attachmentList2',
                                        type: 'images',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload'
                                        },
                                        span: 12,
                                        formItemLayout: {
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
                                ]
                            }
                        },
                        {
                            field: "diy",
                            name: "diy",
                            title: "操作历史",
                            content: props => {
                                return <Operation apiName={'openFlowByReport'} {...props} />;
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'troubleId',
                                hide: true,
                            },
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                align: 'center',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '位置',
                                dataIndex: 'levelNameAll',
                                key: 'levelNameAll',
                                onClick: 'detail',
                                fixed: 'left',
                                width: 300
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '具体段落和部位',
                                dataIndex: 'troubleTitle',
                                key: 'troubleTitle',
                                // width: 150,
                                // tooltip: 13
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '检查时间',
                                field: 'modifyTime',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayout: {
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
                                label: '整改期限',
                                field: 'deadline',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayout: {
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
                                title: '问题描述',
                                dataIndex: 'troubleContent',
                                key: 'troubleContent',
                                // width: 150,
                                // tooltip: 13
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '整改要求',
                                dataIndex: 'troubleRequire',
                                key: 'troubleRequire',
                                // width: 150,
                                // tooltip: 13
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '问题级别',
                                dataIndex: 'troubleLevel',
                                key: 'troubleLevel',
                                type: 'select',
                                width: 100
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '一般',
                                        value: '0'
                                    },
                                    {
                                        label: '严重',
                                        value: '1'
                                    },
                                    {
                                        label: '紧要',
                                        value: '2'
                                    }
                                ],
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '问题类型',
                                dataIndex: 'troubleType',
                                key: 'troubleType',
                                type: 'select',
                                width: 100
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '外观',
                                        value: '100'
                                    },
                                    {
                                        label: '尺寸',
                                        value: '101'
                                    },
                                    {
                                        label: '坐标',
                                        value: '102'
                                    },
                                    {
                                        label: '工序',
                                        value: '103'
                                    },
                                    {
                                        label: '工艺',
                                        value: '104'
                                    },
                                    {
                                        label: '其它',
                                        value: '105'
                                    }
                                ],
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '检查时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '整改期限',
                                dataIndex: 'deadline',
                                key: 'deadline',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '整改回复',
                                field: 'opinionField2',
                                type: 'textarea',
                                formatter: (val) => {
                                    if (val) {
                                        if (val.indexOf('<br/>') !== -1) {
                                            return val.replace("<br/>", "\r\n");
                                        }
                                    }
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '复核情况',
                                field: 'opinionField3',
                                type: 'textarea',
                                formatter: (val) => {
                                    if (val) {
                                        if (val.indexOf('<br/>') !== -1) {
                                            return val.replace("<br/>", "\r\n");
                                        }
                                    }
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '检查照片',
                                field: 'attachmentList1',
                                type: 'images',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                },
                                spanForm: 12,
                                formItemLayout: {
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
                                label: '整改照片',
                                field: 'attachmentList2',
                                type: 'images',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                },
                                spanForm: 12,
                                formItemLayout: {
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
                            isInForm: false,
                            table: {
                                title: '流程状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                type: 'select',
                                fixed: 'right',
                                filter: true
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '发起审核',
                                        value: '0'
                                    },
                                    {
                                        label: '审核中',
                                        value: '1'
                                    },
                                    {
                                        label: '完成',
                                        value: '2'
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;