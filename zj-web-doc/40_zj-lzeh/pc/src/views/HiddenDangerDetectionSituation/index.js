import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import Operation from './operation';
const config = {
    fetchConfig: {
        apiName: 'getZxHwAqHiddenDangerList'
    },
    antd: {
        rowKey: 'dangerId',
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    // isShowRowSelect: false,
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
                    {...config}

                    actionBtns={[
                        {
                            field: 'exportYhzgzls',
                            name: 'exportYhzgzls',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '导出隐患整改指令书',
                            disabled: 'bind:isOnly',
                            onClick: (obj) => {
                                // 3、隐患整改回复单（exportZjLzehHiddenDangerRectificatReply） 参数（dangerTitle）
                                // 4、隐患整改指令书（exportZjLzehHiddenDangerRectificatInstructBook） 参数（dangerTitle、dangerContent、dangerRequire）
                                const {
                                    dangerTitle,
                                    dangerContent,
                                    dangerRequire,
                                    attachmentList1
                                } = obj.selectedRows[0]

                                // const {
                                //     dangerTitle,
                                //     dangerContent,
                                //     dangerRequire
                                // } = obj.qnnTableInstance.getSelectedRows()[0]

                                this.props.myFetch('exportZjLzehHiddenDangerRectificatInstructBook', {
                                    dangerTitle,
                                    dangerContent,
                                    dangerRequire,
                                    fileList: attachmentList1
                                }).then(({ success, data }) => {
                                    if (success) {
                                        window.open(data)
                                        // obj.qnnTableInstance.download(data)
                                    }
                                })
                            }
                        },
                        {
                            field: 'exportYhzghfd',
                            name: 'exportYhzghfd',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '导出隐患整改回复单',
                            disabled: 'bind:isOnly',
                            onClick: (obj) => {

                                // 3、隐患整改回复单（exportZjLzehHiddenDangerRectificatReply） 参数（dangerTitle）
                                // 4、隐患整改指令书（exportZjLzehHiddenDangerRectificatInstructBook） 参数（dangerTitle、dangerContent、dangerRequire）
                                const {
                                    dangerTitle,
                                    modifyTime,
                                    levelNameAll,
                                    dangerContent,
                                    attachmentList1
                                } = obj.selectedRows[0]

                                this.props.myFetch('exportZjLzehHiddenDangerRectificatReply', {
                                    dangerTitle,
                                    modifyTime,
                                    dangerContent,
                                    dangerRequire: levelNameAll,
                                    fileList: attachmentList1
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
                                        field: 'dangerTitle',
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
                                        label: '隐患描述',
                                        field: 'dangerContent',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '整改要求',
                                        field: 'dangerRequire',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '隐患级别',
                                        field: 'dangerLevel',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '轻微',
                                                value: '0'
                                            },
                                            {
                                                label: '可接受',
                                                value: '1'
                                            },
                                            {
                                                label: '较大',
                                                value: '2'
                                            },
                                            {
                                                label: '重大',
                                                value: '3'
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
                                        label: '隐患类型',
                                        field: 'dangerType',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '安全管理',
                                                value: '100'
                                            },
                                            {
                                                label: '文明施工',
                                                value: '101'
                                            },
                                            {
                                                label: '临边防护',
                                                value: '102'
                                            },
                                            {
                                                label: '高处作业',
                                                value: '103'
                                            },
                                            {
                                                label: '基坑支护',
                                                value: '104'
                                            },
                                            {
                                                label: '模板工程',
                                                value: '105'
                                            },
                                            {
                                                label: '施工机具',
                                                value: '106'
                                            },
                                            {
                                                label: '脚手架',
                                                value: '107'
                                            },
                                            {
                                                label: '交通安全',
                                                value: '108'
                                            },
                                            {
                                                label: '个体防护',
                                                value: '109'
                                            },
                                            {
                                                label: '起重吊装',
                                                value: '110'
                                            },
                                            {
                                                label: '施工用电',
                                                value: '111'
                                            },
                                            {
                                                label: '消防防火',
                                                value: '112'
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
                                field: 'dangerId',
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
                                dataIndex: 'dangerTitle',
                                key: 'dangerTitle',
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
                                title: '隐患描述',
                                dataIndex: 'dangerContent',
                                key: 'dangerContent',
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
                                dataIndex: 'dangerRequire',
                                key: 'dangerRequire',
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
                                title: '隐患级别',
                                dataIndex: 'dangerLevel',
                                key: 'dangerLevel',
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
                                        label: '轻微',
                                        value: '0'
                                    },
                                    {
                                        label: '可接受',
                                        value: '1'
                                    },
                                    {
                                        label: '较大',
                                        value: '2'
                                    },
                                    {
                                        label: '重大',
                                        value: '3'
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
                                title: '隐患类型',
                                dataIndex: 'dangerType',
                                key: 'dangerType',
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
                                        label: '安全管理',
                                        value: '100'
                                    },
                                    {
                                        label: '文明施工',
                                        value: '101'
                                    },
                                    {
                                        label: '临边防护',
                                        value: '102'
                                    },
                                    {
                                        label: '高处作业',
                                        value: '103'
                                    },
                                    {
                                        label: '基坑支护',
                                        value: '104'
                                    },
                                    {
                                        label: '模板工程',
                                        value: '105'
                                    },
                                    {
                                        label: '施工机具',
                                        value: '106'
                                    },
                                    {
                                        label: '脚手架',
                                        value: '107'
                                    },
                                    {
                                        label: '交通安全',
                                        value: '108'
                                    },
                                    {
                                        label: '个体防护',
                                        value: '109'
                                    },
                                    {
                                        label: '起重吊装',
                                        value: '110'
                                    },
                                    {
                                        label: '施工用电',
                                        value: '111'
                                    },
                                    {
                                        label: '消防防火',
                                        value: '112'
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