import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import moment from 'moment';

class index extends Component { 
    render() {
        return (
            <QnnTable
                history={this.props.history}
                match={this.props.match}
                fetch={this.props.myFetch}
                upload={this.props.myUpload}
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                wrappedComponentRef={(me) => {
                    this.table = me;
                }}
                {...window.zjProZcQs}
                formConfig={[
                    {
                        isInTable: false,
                        form: {
                            type: "string",
                            field:'qsId',
                            hide: true
                        }
                    },    
                    {
                        isInForm: false,
                        table: {
                            width: 80,
                            align: 'center',
                            fixed:'left',
                            title: '序号', //表头标题
                            dataIndex: 'no', //表格里面的字段
                            key: 'no',//表格的唯一key    
                            render: (data, rows, index) => {
                                return index + 1;
                            }
                        },
                    },
                    {
                        table: {
                            title: '问题性质',
                            dataIndex: 'problemNature',
                            fixed:'left',
                            type: 'select'
                        },
                        form: {
                            type: 'select',
                            field: 'problemNature',
                            required: true,
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: "wenTiXingZhi"
                                }
                            },
                            optionConfig: {
                                label: "itemName",
                                value: "itemId"
                            },
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '问题等级',
                            fixed:'left',
                            dataIndex: 'problemLevel',
                            type: 'select'
                        },
                        form: {
                            type: 'select',
                            field: 'problemLevel',
                            required: true,
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: "wenTiDengJi"
                                }
                            },
                            optionConfig: {
                                label: "itemName",
                                value: "itemId"
                            },
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '问题说明',
                            width: 200,
                            tooltip:23,
                            dataIndex: 'problemDesc',
                            key:'problemDesc'
                        },
                        form: {
                            type: 'textarea',
                            required: true,
                            field: 'problemDesc',
                            autoSize: {
                                minRows: 1,
                                maxRows: 6
                            }
                        }
                    },
                    {
                        table: {
                            title: '发现时间',
                            onClick:'detail',
                            dataIndex: 'findTime',
                            key: 'findTime',
                            render: (data) => {
                                return moment(data).format('YYYY-MM-DD')
                            }
                        },
                        form: {
                            type: 'date',
                            required: true,
                            field: 'findTime',
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        isInTable: false,
                        form: {
                            label:'整改时限',
                            type: 'date',
                            required: true,
                            field: 'finalTime',
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '整改要求',
                            width: 200,
                            tooltip: 23,
                            dataIndex: 'reformRequest',
                            key:'reformRequest'
                        },
                        form: {
                            type: 'textarea',
                            required: true,
                            field: 'reformRequest',
                            autoSize: {
                                minRows: 1,
                                maxRows: 6
                            }
                        }
                    },
                    {
                        table: {
                            title: '整改时限',
                            format:'YYYY-MM-DD',
                            dataIndex: 'finalTime',
                            key:'finalTime'
                        },
                        isInForm:false
                    },
                    {
                        table: {
                            title: '整改说明',
                            width: 200,
                            tooltip: 23,
                            dataIndex: 'reformDesc',
                            key:'reformDesc'
                        },
                        form: {
                            type: 'textarea',
                            field: 'reformDesc',
                            autoSize: {
                                minRows: 1,
                                maxRows: 6
                            }
                        }
                    },
                    {
                        table: {
                            title: '完成时间',
                            format:'YYYY-MM-DD',
                            dataIndex: 'reformFinishTime',
                            key:'reformFinishTime'
                        },
                        form: {
                            type: 'date',
                            field: 'reformFinishTime'
                        }
                    },
                    {
                        table: {
                            title: '是否展示',
                            dataIndex: 'showFlag',
                            key: 'showFlag',
                            render: (data) => {
                                if (data === '0') {
                                    return '是'
                                } else if (data === '1'){
                                    return '否'
                                }
                            }
                        },
                        form: {
                            type: 'radio',
                            required: true,
                            field: 'showFlag',
                            optionData: [
                                {
                                    label: '是',
                                    value:'0'
                                },
                                {
                                    label: '否',
                                    value:'1'
                                }
                            ],
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '是否完成',
                            dataIndex: 'finishFlag',
                            key: 'finishFlag',
                            render: (data) => {
                                if (data === '0') {
                                    return '未完成'
                                } else if (data === '1'){
                                    return '完成'
                                }
                            }
                        },
                        form: {
                            type: 'radio',
                            required: true,
                            field: 'finishFlag',
                            optionData: [
                                {
                                    label: '未完成',
                                    value:'0'
                                },
                                {
                                    label: '完成',
                                    value:'1'
                                }
                            ],
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        isInTable: false,
                        form:{
                            type: 'images',
                            label: '问题照片',
                            field: 'beforeFileList',
                            wrapperStyle:{},
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                            },
                            accept: 'image/jpeg',
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    },
                    {
                        isInTable: false,
                        form:{
                            type: 'images',
                            label: '整改后照片',
                            field: 'afterFileList',
                            wrapperStyle:{},
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                            },
                            accept: 'image/jpeg',
                            spanForm:12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            }
                        }
                    }
                ]}
                actionBtns={[
                    {
                        name: 'add',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增',
                        field: 'addOutBtn',
                        formBtns: [
                            {
                                name: 'cancel',
                                type: 'dashed',
                                label: '取消',
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '保存',
                                fetchConfig: {
                                    apiName: 'addZjProZcQs'
                                }
                            }
                        ]
                    },
                    {
                        name: 'edit',
                        type: 'primary',
                        label: '修改',
                        editDisabled:false,
                        onClick: (obj) => {
                            this.table.clearSelectedRows();
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
                                label: '保存',
                                fetchConfig: {
                                    apiName: 'updateZjProZcQs'
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
                            apiName: 'batchDeleteUpdateZjProZcQs'
                        },
                    }
                ]}
            />
        )
    }
}

export default index