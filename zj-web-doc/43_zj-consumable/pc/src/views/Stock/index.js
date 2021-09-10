import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.inventoryId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 19 }
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjConsumableInventoryList'
                    }}
                    actionBtns={[]}
                    searchFormColNum={3}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'inventoryId',
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
                                type: 'string',
                                spanSearch: 8,
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
                                type: 'string',
                                spanSearch: 8,
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
                                spanSearch: 8
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
                                spanSearch: 8
                            }
                        },
                       
                        {
                            isInSearch: true,
                            isInTable: false,
                            table: {
                                title: '日期',//???
                                dataIndex: 'createTime',
                                key: 'createTime'
                            },
                            form: {
                                type: 'date',
                                spanSearch: 8
                            }
                        },
                        {
                            table: {
                                title: '当前库存量',
                                dataIndex: 'nowNum',
                                key: 'nowNum'
                            },
                            form: {
                                type: 'number',
                                spanSearch: 8
                            }
                        },
                        {
                            table: {
                                title: '总入库量',
                                dataIndex: 'totalInNum',
                                key: 'totalInNum'
                            },
                            form: {
                                type: 'number',
                                spanSearch: 8
                            }
                        },
                        {
                            table: {
                                title: '总领用量',
                                dataIndex: 'totalUseNum',
                                key: 'totalUseNum'
                            },
                            form: {
                                type: 'number',
                                spanSearch: 8
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;