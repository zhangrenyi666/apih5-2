import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table';
import { push } from "react-router-redux";
const config = {
    fetchConfig: {
        apiName: "getZjTzCountAqList"
    },
    antd: {
        rowKey: "countAqId",
        size: "small"
    },
    drawerConfig: {
        width: 1000
    },
    firstRowIsSearch: false,
    formItemLayout: {
        labelCol: {
            sm: { span: 3 }
        },
        wrapperCol: {
            sm: { span: 21 }
        }
    }
}
class index extends Component {
    render() {
        return (
            <QnnTable
                myPublic={this.props.myPublic}
                dispatch={this.props.dispatch}
                history={this.props.history}
                match={this.props.match}
                fetch={this.props.myFetch}
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                wrappedComponentRef={(me) => {
                    this.table = me;
                }}
                {...config}
                formConfig={[
                    {
                        isInTable: false,
                        form: {
                            type: "string",
                            field: 'countAqId',
                            hide: true
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 80,
                            align: 'center',
                            title: '序号', //表头标题
                            dataIndex: 'no', //表格里面的字段
                            key: 'no',//表格的唯一key    
                            render: (data,rows,index) => {
                                return index + 1;
                            }
                        },
                    },
                    {
                        table: {
                            title: '管理单位',
                            dataIndex: 'managerUnit'
                        },
                        form: {
                            type: 'string',
                            field: 'managerUnit',
                            editDisabled: true,
                            spanForm: 12,
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
                            title: '项目名称',
                            onClick: 'detail',
                            dataIndex: 'projectName'
                        },
                        form: {
                            type: 'string',
                            required: true,
                            field: 'projectName',
                            editDisabled: true,
                            spanForm: 12,
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
                            title: '项目单位名称',
                            dataIndex: 'proUnitName'
                        },
                        form: {
                            type: 'string',
                            required: true,
                            field: 'proUnitName'
                        }
                    },
                    {
                        table: {
                            title: '累计检查数量(个)',
                            dataIndex: 'totalNum'
                        },
                        form: {
                            type: 'number',
                            required: true,
                            field: 'totalNum'
                        }
                    },
                    {
                        table: {
                            title: '已解决数量(个)',
                            dataIndex: 'finishNum',
                            key: 'finishNum',
                        },
                        form: {
                            type: 'number',
                            required: true,
                            field: 'finishNum',
                        }
                    },
                    {
                        table: {
                            title: '未解决数量(个)',
                            dataIndex: 'unfinishNum',
                            key: 'unfinishNum',
                        },
                        form: {
                            type: 'number',
                            required: true,
                            field: 'unfinishNum',
                        }
                    },
                    {
                        table: {
                            title: '已解决百分比(%)',
                            dataIndex: 'finishRate',
                            key: 'finishRate',
                        },
                        form: {
                            type: 'string',
                            required: true,
                            field: 'finishRate',
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
                            showType: "tile",
                            width: 80,
                            btns: [
                                {
                                    name: 'CountSqDetail',
                                    render: (rowData) => {
                                        return '<a>详情</a>';
                                    },
                                    onClick: (obj) => {
                                        const { mainModule } = obj.props.myPublic.appInfo;
                                        const { projectId } = obj.rowData;
                                        obj.props.dispatch(
                                            push(`${mainModule}CountSqDetail/${projectId}`)
                                        )
                                    },
                                }
                            ]
                        }
                    }
                ]}
            /* actionBtns={[
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
                                apiName: 'addZjProZcStretchDraw'
                            }
                        }
                    ]
                }
            ]} */
            />
        )
    }
}

export default index