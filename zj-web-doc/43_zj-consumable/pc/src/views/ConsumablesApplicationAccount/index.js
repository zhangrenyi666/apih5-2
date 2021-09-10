import React, { Component } from "react";
import { flushSync } from "react-dom";
import QnnTable from "../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.applyBookId
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
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 }
        }
    },
    isShowRowSelect: false
};
class index extends Component {
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        let { domain } = this.props.myPublic;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjConsumableApplyBookList'
                    }}
                    actionBtns={[]}
                    searchFormColNum={3}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                if (val) {
                                    let valueFetch = val.searchData;
                                    valueFetch.then((data) => {
                                        let value = data;
                                        // var URL = `${ureport}excel?_u=file:zjConsumableApplyBookList.xml&_n=耗材申领台账&deptName=${value.deptName ? value.deptName : ''}&name=${value.name ? value.name : ''}&category=${value.category ? value.category : ''}&brand=${value.brand ? value.brand : ''}&model=${value.model ? value.model : ''}&colour=${value.colour ? value.colour : ''}`;
                                        let URL = `${ureport}excel?_u=file:zjConsumableApplyBookList.xml&url=${domain}&delFlag=0`;
                                        confirm({
                                            content: '确定导出报表吗?',
                                            onOk: () => {
                                                window.open(URL);
                                            }
                                        });
                                    })
                                }
                            },
                            type: "primary",
                            icon: <ExportOutlined />
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'applyBookId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'note',
                                key: 'note',
                                width: 60,
                                align: 'center',
                                render: (val, rowData, index) => {
                                    return index + 1
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '申请部门',
                                width: 140,
                                dataIndex: 'deptName',
                                key: 'deptName'
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field: 'deptName'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '申请人',
                                dataIndex: 'name',
                                key: 'name'
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field: 'name'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '申请时间',
                                dataIndex: 'appDate',
                                key: 'appDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false,
                            form: {
                                type: 'rangeDate',
                                field: 'appDateSearch'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '类别',
                                dataIndex: 'category',
                                key: 'category'
                            },
                            isInForm: false,
                            form: {
                                spanSearch: 8,
                                spanForm: 12,
                                type: 'string',
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '品牌',
                                dataIndex: 'brand',
                                key: 'brand'
                            },
                            isInForm: false,
                            form: {
                                type: 'string'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '型号',
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '颜色',
                                dataIndex: 'colour',
                                key: 'colour'
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                            }
                        },

                        {
                            table: {
                                title: '申领数量',
                                dataIndex: 'applyNum',
                                key: 'applyNum'
                            },
                            isInForm: false
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '审批状态',
                                dataIndex: 'status',
                                key: 'status',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                optionConfig: {
                                    value: 'value',
                                    label: 'label'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        label: '未审批'
                                    },
                                    {
                                        value: '1',
                                        label: '审批通过'
                                    },
                                    {
                                        value: '2',
                                        label: '驳回'
                                    },
                                ]
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '审批时间',
                                dataIndex: 'applyDate',
                                key: 'applyDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false,
                            form: {
                                field: 'applyDateSearch',
                                spanSearch: 8,
                                type: 'rangeDate',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '领用状态',
                                dataIndex: 'useStatus',
                                key: 'useStatus',
                                type: 'select',
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                optionConfig: {
                                    value: 'value',
                                    label: 'label'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        label: '未领用'
                                    },
                                    {
                                        value: '1',
                                        label: '已领用'
                                    }
                                ],
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '领用时间',
                                dataIndex: 'useDate',
                                key: 'useDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false,
                            form: {
                                field: 'useDateSearch',
                                spanSearch: 8,
                                type: 'rangeDate',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;