import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '70%'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 7 },
            sm: { span: 7 }
        },
        wrapperCol: {
            xs: { span: 17 },
            sm: { span: 17 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    componentDidMount() {

    }
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
                    fetchConfig={{
                        apiName: 'getZxEqEquipLimitPriceList'
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            isInSearch:true,
                            table: {
                                title: '评审编号',
                                dataIndex: 'applyNo',
                                key: 'applyNo',
                                width: 180,
                                tooltip: 20,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            isInSearch:true,
                            table: {
                                title: '公司名称',
                                dataIndex: 'orgName',
                                width: 200,
                                tooltip: 20,
                                key: 'orgName'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            isInSearch:true,
                            table: {
                                title: '数据期次',
                                width: 120,
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY') + `${moment(data).month() < 6 ? '/上半年' : '/下半年'}`;
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            form: {
                                type: 'halfYear',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 120,
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核完成';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    }
                                }
                            },
                            form: {
                                type: 'select',
                                spanForm: 8,
                                optionData: [
                                    { label: '待提交', value: '0' },
                                    { label: '审核中', value: '1' },
                                    { label: '审核完成', value: '2' },
                                    { label: '退回', value: '3' },
                                    { label: '未审核', value: '-1' },
                                ],
                                optionConfig: { label: 'label', value: 'value' }
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                width: 120,
                                dataIndex: 'reporter',
                                key: 'reporter'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '填报日期',
                                field: 'reportDate',
                                type: 'date',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
                                tooltip: 20,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'itemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: {
                                        position: 'bottom'
                                    },
                                    wrappedComponentRef: (me) => {
                                        this.tableOne = me;
                                    },
                                    isShowRowSelect:false,
                                    formConfig: [
                                        {
                                            table: {
                                                title: '机种分类',
                                                dataIndex: 'resCatalogID',
                                                key: 'resCatalogID',
                                                width: 150,
                                            },
                                        },
                                        {
                                            table: {
                                                title: '设备编码',
                                                dataIndex: 'equipNo',
                                                key: 'equipNo',
                                                width: 150
                                            },
                                        },
                                        {
                                            table: {
                                                title: '设备名称',
                                                dataIndex: 'equipName',
                                                key: 'equipName',
                                                width: 150
                                            },
                                        },
                                        {
                                            table: {
                                                title: '所在省份',
                                                dataIndex: 'province',
                                                key: 'province',
                                                width: 150,
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'shengfenjianchengdaima'
                                                    }
                                                },
                                            },
                                        },
                                        {
                                            table: {
                                                title: '厂家',
                                                dataIndex: 'factory',
                                                key: 'factory',
                                                width: 150,
                                            },
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150,
                                            },
                                        },
                                        {
                                            table: {
                                                title: '工作时间',
                                                dataIndex: 'workTime',
                                                key: 'workTime',
                                                width: 150,
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '单班',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '双班',
                                                        value: '1'
                                                    }
                                                ],
                                            },
                                        },
                                        {
                                            table: {
                                                title: '租赁情况',
                                                dataIndex: 'rentContent',
                                                key: 'rentContent',
                                                width: 150,
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '六个月以下',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '六个月及以上',
                                                        value: '1'
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '燃油情况',
                                                dataIndex: 'ranyouQingkuang',
                                                key: 'ranyouQingkuang',
                                                width: 150,
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '甲方承担',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '乙方承担',
                                                        value: '1'
                                                    }
                                                ],
                                            },
                                        },
                                        {
                                            table: {
                                                title: '租赁限价(元/台.月)',
                                                dataIndex: 'rentPrice',
                                                key: 'rentPrice',
                                                width: 150,
                                                type:'number'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否含司机',
                                                dataIndex: 'isDriver',
                                                key: 'isDriver',
                                                width: 150,
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ]
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                            },
                                        },
                                    ],
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