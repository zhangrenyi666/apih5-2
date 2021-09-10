import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"
import moment from "moment";

class Page extends Apih5 {
    state = {};
    componentDidMount() {
        this.resize();
        window.addEventListener("resize", this.resize, false);

    }
    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
    }
    resize = () => {
        this.setState({
            tableHeight: window.innerHeight - 200
        })
    };
    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    质量安全信息
                </div>
                <QnnTable
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { this.qnnTable = me }}
                    method={{}}
                    componentsKey={{}}
                    fetchConfig={{
                        apiName: "getZjProZcQsList"
                    }}
                    antd={{
                        rowKey: "qsId",
                        size: "small",
                        scroll: {
                            y: this.state.tableHeight
                        }
                    }}
                    isShowRowSelect={false}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                type: "string",
                                field: 'qsId',
                                hide: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: 50,
                                align: 'center',
                                fixed: 'left',
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
                                align: 'center',
                                width: 80,
                                title: '问题性质',
                                dataIndex: 'problemNature',
                                fixed: 'left',
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
                                align: 'center',
                                width: 80,
                                title: '问题等级',
                                fixed: 'left',
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
                                align: 'center',
                                title: '问题说明',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'problemDesc',
                                key: 'problemDesc',
                            },
                            form: {
                                type: 'textarea',
                                required: true,
                                field: 'problemDesc',
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 6
                                },
                                formItemLayout:{
                                   labelCol: {
                                       xs: { span: 24 },
                                       sm: { span: 3 }
                                   },
                                   wrapperCol: {
                                       xs: { span: 24 },
                                       sm: { span: 21 }
                                   }
                                },
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '发现时间',
                                dataIndex: 'findTime',
                                key: 'findTime',
                                format: "YYYY-MM-DD",
                                render: (data) => {
                                    return moment(data).format('YYYY-MM-DD')
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'findTime',
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
                            isInTable: false,
                            form: {
                                label: '整改时限',
                                type: 'date',
                                required: true,
                                field: 'finalTime',
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
                                align: 'center',
                                title: '整改要求',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'reformRequest',
                                key: 'reformRequest'
                            },
                            form: {
                                type: 'textarea',
                                required: true,
                                field: 'reformRequest',
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 6
                                },
                                formItemLayout:{
                                   labelCol: {
                                       xs: { span: 24 },
                                       sm: { span: 3 }
                                   },
                                   wrapperCol: {
                                       xs: { span: 24 },
                                       sm: { span: 21 }
                                   }
                                },
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '整改时限',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'finalTime',
                                key: 'finalTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                align: 'center',
                                title: '整改说明',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'reformDesc',
                                key: 'reformDesc'
                            },
                            form: {
                                type: 'textarea',
                                field: 'reformDesc',
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 6
                                },
                                
                                formItemLayout:{
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                 },
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '完成时间',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'reformFinishTime',
                                key: 'reformFinishTime'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'reformFinishTime',
                                
                                formItemLayout:{
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                 },
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '是否展示',
                                dataIndex: 'showFlag',
                                key: 'showFlag',
                                render: (data) => {
                                    if (data === '0') {
                                        return '是'
                                    } else if (data === '1') {
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
                                        value: '0'
                                    },
                                    {
                                        label: '否',
                                        value: '1'
                                    }
                                ],
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
                                align: 'center',
                                title: '是否完成',
                                dataIndex: 'finishFlag',
                                key: 'finishFlag',
                                render: (data) => {
                                    if (data === '0') {
                                        return '未完成'
                                    } else if (data === '1') {
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
                                        value: '0'
                                    },
                                    {
                                        label: '完成',
                                        value: '1'
                                    }
                                ],
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
                            isInTable: false,
                            form: {
                                type: 'images',
                                label: '问题照片',
                                field: 'beforeFileList',
                                wrapperStyle: {},
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                },
                                accept: 'image/jpeg',
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
                            isInTable: false,
                            form: {
                                type: 'images',
                                label: '整改后照片',
                                field: 'afterFileList',
                                wrapperStyle: {},
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                },
                                accept: 'image/jpeg',
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
                            "isInForm": false,
                            "table": {
                                align: 'center',
                                // fixed: "right",
                                "dataIndex": "action",
                                "key": "action",
                                showType: "tile", 
                                "btns": [
                                    {
                                        label: "详情",
                                        "name": "detail",
                                        formBtns:[]
                                    }
                                ],
                            }
                        }
                    ]}
                />
            </div>)
    }
}
export default Page;