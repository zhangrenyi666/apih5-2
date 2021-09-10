import React, { Component } from 'react';
import QnnTable from '../modules/qnn-table';
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
const { confirm } = Modal;
const config = {
    fetchConfig: {
        apiName: "getZjDailyProFillList"
    },
    antd: {
        rowKey: "proFillId",
        size: "small"
    },
    drawerConfig: {
        width: 1000
    },
    firstRowIsSearch: false,
}
class Index extends Component {
    render() {
        const { userKey } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div style={{ height: '100%', overflow: "hidden" }}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.tables = me;
                    }}
                    actionBtns = {[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            drawerTitle: '新增',
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
                                        apiName: 'addZjDailyProFill'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'pass',
                            type: 'primary',
                            label: '通过',
                            icon:'check',
                            onClick:(obj) => {
                                if (obj.selectedRows.length) {
                                    for(let i = 0; i < obj.selectedRows.length; i++){
                                        if(obj.selectedRows[i].approveStatus === '1'){
                                            Msg.error('审核通过数据无法操作！');
                                            break;
                                        }else if(i === obj.selectedRows.length - 1){
                                            confirm({
                                                title: "是否确定通过数据？",
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    this.props.myFetch('approveAgreeZjDailyProFill', [ ...obj.selectedRows ]).then(({ success, data, message }) => {
                                                        if (success) {
                                                            this.tables.refresh();
                                                            this.tables.clearSelectedRows();
                                                            Msg.success(message);
                                                        } else {
                                                            this.tables.clearSelectedRows();
                                                            Msg.error(message);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }               
                                } else {
                                    Msg.error("请能选择数据！");
                                }
                            }
                        },
                        {
                            name: 'reject',
                            type: 'danger',
                            label: '驳回',
                            icon:'close',
                            onClick:(obj) => {
                                if (obj.selectedRows.length) {
                                    for(let i = 0; i < obj.selectedRows.length; i++){
                                        if(obj.selectedRows[i].approveStatus === '1'){
                                            Msg.error('审核通过数据无法操作！');
                                            break;
                                        }else if(i === obj.selectedRows.length - 1){
                                            confirm({
                                                title: "是否确定驳回数据？",
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    this.props.myFetch('approveRejectZjDailyProFill', [ ...obj.selectedRows ]).then(({ success, data, message }) => {
                                                        if (success) {
                                                            this.tables.refresh();
                                                            this.tables.clearSelectedRows();
                                                            Msg.success(message);
                                                        } else {
                                                            this.tables.clearSelectedRows();
                                                            Msg.error(message);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }                                                   
                                } else {
                                    Msg.error("请能选择数据！");
                                }
                            }
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            isCanSubmit: ({params},callback) => {
                                for(let i = 0; i < params.length; i++){
                                    if(params[i].approveStatus === '1'){
                                        Msg.error('审核通过数据不允许删除！');
                                        callback(false);
                                        break;
                                    }else if(i === params.length - 1){
                                        callback(true);
                                    }
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjDailyProFill'
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'proFillId',
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'proName',
                                key: 'proName',
                                width: 200,
                                tooltip: 12,
                                onClick: 'detail',
                                fixed: "left"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '收费站',
                                dataIndex: 'tollStation',
                                key: 'tollStation',
                                width: 100,
                                tooltip: 6,
                                fixed: "left"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '<center>填报日期</center>',
                                dataIndex: 'fillDate',
                                key: 'fillDate',
                                width: 100,
                                // fieldsConfig: {
                                //     field: "fillDate",
                                //     type: "date",
                                // },
                                fixed: "left",
                                noHaveSearchInput: true,
                                render: (data) => {
                                    return moment(data).format('YYYY-MM-DD')
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'linkage',
                                fetchConfig: {//只有模式为0才写到这
                                    apiName: 'getZjDailyProInfoListByUserKey',
                                    otherParams: {
                                        userKey: userKey
                                    }
                                },
                                children: {//所有配置见qnn-form
                                    form: {
                                        label: '项目名称',
                                        field: 'proInfoId',
                                        type: 'select',
                                        required: true,
                                        placeholder: '请选择',
                                        optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                                            value: 'proInfoId',
                                            label: 'proName',
                                            children: 'proDetailList'
                                        },
                                    },
                                    children: {
                                        form: {
                                            label: '收费站',
                                            field: 'proDetailId',
                                            placeholder: '请选择',
                                            required: true,
                                            type: 'select',
                                            optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                                                value: 'proDetailId',
                                                label: 'tollStation'
                                            },
                                        }
                                    }
                                },
                                formItemLayoutForm: {
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
                            isInTable: false,
                            form: {
                                label: '填报日期',
                                field: 'fillDate',
                                type: 'date',
                                placeholder: '请选择',
                                required: true,
                                formItemLayoutForm: {
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
                                title: '门架ETC计费金额',
                                dataIndex: 'cash1',
                                key: 'cash1',
                                width:100
                            },
                            form: {
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                title: '门架CPC计费金额',
                                dataIndex: 'cash2',
                                key: 'cash2',
                                width:100
                            },
                            form: {
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                title: '合计金额',
                                dataIndex: 'cashAllTotal',
                                key: 'cashAllTotal',
                                width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                        // {
                        //     table: {
                        //         title: '工可日收入',
                        //         dataIndex: 'ext1',
                        //         key: 'ext1',
                        //         // width:100
                        //     },
                        //     form: {
                        //         type: 'number',
                        //         placeholder: '请输入',
                        //         required: true,
                        //         formItemLayoutForm: {
                        //             labelCol: {
                        //                 xs: { span: 3 },
                        //                 sm: { span: 3 }
                        //             },
                        //             wrapperCol: {
                        //                 xs: { span: 21 },
                        //                 sm: { span: 21 }
                        //             }
                        //         }
                        //     }
                        // },
                        {
                            table: {
                                title: '入口车次',
                                dataIndex: '入口车次',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'entr1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'entr2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '入口车次客车',
                                field: 'entr1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                label: '入口车次货车',
                                field: 'entr2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                title: '入口车流量合计',
                                dataIndex: 'entrTotal',
                                key: 'entrTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                                title: '出口车次',
                                dataIndex: '出口车次',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '客车',
                                        dataIndex: 'export1',
                                    },
                                    {
                                        width: 80,
                                        title: '货车',
                                        dataIndex: 'export2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '出口车次客车',
                                field: 'export1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                label: '出口车次货车',
                                field: 'export2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                title: '出口车流量合计',
                                dataIndex: 'exportTotal',
                                key: 'exportTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                                title: '绿通车',
                                dataIndex: '绿通车',
                                noHaveSearchInput: true,
                                width: 160,
                                children: [
                                    {
                                        width: 80,
                                        title: '车次',
                                        dataIndex: 'green1',
                                    },
                                    {
                                        width: 80,
                                        title: '减免金额',
                                        dataIndex: 'green2',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '绿通车车次',
                                field: 'green1',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                label: '绿通车减免金额',
                                field: 'green2',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
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
                                title: '合计车流量',
                                dataIndex: 'allTotal',
                                key: 'allTotal',
                                // width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
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
                        // {
                        //     table: {
                        //         title: '发送状态',
                        //         dataIndex: 'sendFlag',
                        //         width: 80,
                        //         fixed: "right",
                        //         render: (data) => {
                        //             if (data === '0') {
                        //                 return '未发送';
                        //             } else if (data === '1') {
                        //                 return '已发送';
                        //             } else {
                        //                 return '-';
                        //             }
                        //         }
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'approveStatusName',
                                width: 80,
                                fixed: "right"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width:100,
                                tooltip:6,
                                fixed: "right"
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
                                formItemLayoutForm: {
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
                            isInForm: false,
                            table: {
                                fixed: "right",
                                width: 80,
                                title: '操作',
                                showType: "tile",
                                dataIndex: 'action',
                                btns: [
                                    {
                                        name: "edit",
                                        render: function (obj) {
                                            if (obj.rowData.approveStatus === '1') {
                                                return '';
                                            }else{
                                                return '修改';
                                            }
                                        },
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
                                                fetchConfig: {//ajax配置  ---可为func
                                                    apiName: 'updateZjDailyProFill',
                                                }
                                            }
                                        ],
                                    }
                                ]
                            }
                        }
                    ]}
                    fieldsValueChange={(props, changedValues, allValues) => {
                        if (!changedValues.proInfoId && !changedValues.proDetailId && !changedValues.fillDate && !changedValues.ext1 && !changedValues.green1 && !changedValues.green2) {
                            let cash1 = allValues.cash1 || 0;
                            let cash2 = allValues.cash2 || 0;
                            let entr1 = allValues.entr1 || 0;
                            let entr2 = allValues.entr2 || 0;
                            let export1 = allValues.export1 || 0;
                            let export2 = allValues.export2 || 0;
                            let cashAllTotal = changedValues.cashAllTotal || 0;
                            let entrTotal = changedValues.entrTotal || 0;
                            let exportTotal = changedValues.exportTotal || 0;
                            let allTotal = changedValues.allTotal || 0;
                            if (cashAllTotal !== allValues.cashAllTotal || entrTotal !== allValues.entrTotal || exportTotal !== allValues.exportTotal || allTotal !== allValues.allTotal) {
                                props.form.setFieldsValue({ cashAllTotal: cash1 + cash2, entrTotal: entr1 + entr2, exportTotal: export1 + export2, allTotal: entr1 + entr2 + export1 + export2 });
                            }
                        }
                    }}
                />
            </div>)
    }
}
export default Index;