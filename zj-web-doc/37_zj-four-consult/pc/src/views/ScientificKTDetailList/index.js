import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Tabs } from 'antd';
import s from './style.less';
import moment from 'moment';
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: function (row) {
            return row.topicId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline'
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
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjSjConsultScientificResearchTopicList'
                    }}
                    {...config}
                    actionBtns={[
                        {
                            name: 'export',
                            type: 'primary',
                            label: '导出',
                            onClick: (obj) => {
                                if(obj.selectedRows.length){
                                    obj.btnCallbackFn.fetchByCb('exportZjSjConsultScientificResearchTopic', obj.selectedRows, ({ data, success, message }) => {
                                        if (success) {
                                            window.location.href = data;
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }else{
                                    Msg.error('请选择导出数据！');
                                }
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'topicId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '课题编号',
                                dataIndex: 'topicNo',
                                key: 'topicNo',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '课题名称',
                                dataIndex: 'topicName',
                                key: 'topicName',
                                width: 150,
                                tooltip: 200,
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '课题名称',
                                field: 'topicName',
                                type: "string",
                                required: true,
                                placeholder: "请输入",
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
                            isInTable: false,
                            form: {
                                label: '课题编号',
                                field: 'topicNo',
                                type: "string",
                                required: true,
                                placeholder: "请输入",
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
                                label: '课题类型',
                                field: 'topicTypeId',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'keTiLeiXing'
                                    }
                                },
                                required: true,
                                placeholder: '请输入',
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
                            isInSearch: true,
                            isInForm:false,
                            table: {
                                title: '承担单位',
                                dataIndex: 'undertakingUnitName',
                                key: 'undertakingUnitName',
                                width: 150,
                                tooltip: 200,
                                fieldsConfig: {
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                            },
                            form:{
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label: '承担单位',
                                field: 'undertakUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'chengDanDanWei'
                                    }
                                },
                                required: true,
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
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '参与单位',
                                dataIndex: 'joinUnitName',
                                key: 'joinUnitName',
                                width: 100,
                                tooltip: 200,
                            },
                            form:{
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                label: '参与单位',
                                field: 'joinUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'canYuDanWei'
                                    }
                                },
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
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '立项批准单位',
                                dataIndex: 'approvalUnitName',
                                key: 'approvalUnitName',
                                width: 150,
                                tooltip: 200,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '立项批准单位',
                                field: 'approvalUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'liXiangPiZhunDanWei'
                                    }
                                },
                                initialValue: [],
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
                                }
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '负责人',
                                dataIndex: 'responsiblePerson',
                                key: 'responsiblePerson',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
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
                                label: '联系方式',
                                field: 'contactMode',
                                type: 'phone',
                                required: true,
                                placeholder: '请输入',
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
                                label: '参与人员',
                                field: 'joinPersonList',
                                type: 'select',
                                required: true,
                                mode: "tags",
                                optionData: [{ label: '请手动输入', value:'请手动输入' }],
                                initialValue: [],
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
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '课题类型',
                                dataIndex: 'topicTypeName',
                                key: 'topicTypeName',
                                width: 100,
                                tooltip: 200,
                            },
                        },
                        {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                                title: '研发计划时间',
                                dataIndex: 'planTime',
                                key: 'planTime',
                                width: 200,
                                render: (data) => {
                                    if (data && data.length) {
                                        return moment(data[0]).format('YYYY-MM-DD') + '~' + moment(data[1]).format('YYYY-MM-DD')
                                    }
                                    return '';
                                }
                            },
                            form: {
                                type: 'rangeDate',
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '经费预算(万元)',
                                dataIndex: 'budget',
                                key: 'budget',
                                width: 130
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                spanSearch: 6,
                                formItemLayoutSearch: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
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
                            isInSearch: true,
                            isInForm: false,
                            isInTable: false,
                            form: {
                                field:'symbol',
                                type: 'select',
                                placeholder: '请输入',
                                spanSearch: 2,
                                optionData: [//默认选项数据//可为function (props)=>array
                                    {
                                        label: '>',
                                        value: '0'
                                    },
                                    {
                                        label: '<',
                                        value: '1'
                                    },
                                    {
                                        label: '=',
                                        value: '2'
                                    },
                                    {
                                        label: '>=',
                                        value: '3'
                                    },
                                    {
                                        label: '<=',
                                        value: '4'
                                    }
                                ],
                                formItemLayoutSearch: {
                                    labelCol: {
                                        xs: { span: 0 },
                                        sm: { span: 0 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '局拨款金额(万元)',
                                field: 'bureauAppropriationAmount',
                                type: 'number',
                                placeholder: '请输入',
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
                                label: '研发计划时间',
                                field: 'planTime',
                                type: 'rangeDate',
                                format:"YYYY-MM-DD",
                                placeholder: '请选择',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 9 },
                                        sm: { span: 9 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "qnnForm",
                                field: "progressList",
                                label: "进度执行情况",
                                textObj: {
                                    add: "添加",
                                    del: "删除"
                                },
                                canAddForm: true,
                                qnnFormConfig: {
                                    formConfig: [
                                        {
                                            label: '报表期次',
                                            type: 'select',
                                            field: 'yearId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'year'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 6,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 13 },
                                                    sm: { span: 13 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 11 },
                                                    sm: { span: 11 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'yearS',
                                            Component: obj => {
                                                return (
                                                    <div style={{height:'32px',lineHeight:'32px',margin:'12px 0'}}>
                                                        年
                                                    </div>
                                                );
                                            },
                                            span: 1,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'select',
                                            field: 'quarterId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'quarter'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 3,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'quarterS',
                                            Component: obj => {
                                                return (
                                                    <div style={{height:'32px',lineHeight:'32px',margin:'12px 0'}}>
                                                        季度
                                                    </div>
                                                );
                                            },
                                            span: 14,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            label: '本季度计划',
                                            type: 'textarea',
                                            field: 'planForTheQuarter',
                                            placeholder: '请输入',
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
                                        },
                                        {
                                            label: '本季度完成',
                                            type: 'textarea',
                                            field: 'completedThisQuarter',
                                            placeholder: '请输入',
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
                                        },
                                        {
                                            label: '未完成原因',
                                            type: 'textarea',
                                            field: 'unfinishedReason',
                                            placeholder: '请输入',
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
                                        },
                                        {
                                            label: '下季度计划',
                                            type: 'textarea',
                                            field: 'nextQuarterPlan',
                                            placeholder: '请输入',
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
                                        }
                                    ]
                                }
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;