import React, { Component } from "react";
import { flushSync } from "react-dom";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.putId
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
                        apiName: 'getZjConsumablePutList'
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '提交',
                                    fetchConfig: {
                                        apiName: 'addZjConsumablePut',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'plus',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                console.log(obj.selectedRows[0]);
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
                                    label: '提交',
                                    fetchConfig: {
                                        apiName: 'updateZjConsumablePut',
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
                                apiName: 'batchDeleteUpdateZjConsumablePut',
                            },
                        }
                    ]}
                    searchFormColNum={3}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'putId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            isInForm: false,
                            form: {
                                label:'类别',
                                spanSearch: 8,
                                spanForm: 12,
                                type: 'string',
                                field:'categoryName'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type:'string',
                                field: 'categoryName',
                                hide:true
                            }
                        },
                        {
                            table: {
                                title: '类别',
                                dataIndex: 'category',
                                key: 'category',
                                type:'select'
                            },
                            form: {
                                spanSearch: 8,
                                spanForm: 12,
                                required: true,
                                editDisabled: true,
                                type: 'select',
                                placeholder: '请选择...',
                                allowClear: false,
                                optionConfig: {
                                    label: 'category',
                                    value: 'setId',
                                    children:'childrenList',
                                    linkageFields: {
                                      
                                        categoryName:'category'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZjConsumableSetList',
                                    otherParams: {
                                        codePid: '0',
                                        useState:'0'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'setId',
                                hide: true
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            isInForm: false,
                            form: {
                                label:'品牌',
                                spanSearch: 8,
                                spanForm: 12,
                                type: 'string',
                                field:'brandName'
                            }
                        },
                        {
                            table: {
                                title: '品牌',
                                dataIndex: 'brandName',
                                key: 'brandName'
                            },
                            form: {
                                type: 'select',
                                field:'brand',
                                spanSearch: 8,
                                spanForm: 12,
                                parent:'category',
                                required: true,
                                editDisabled: true,
                                placeholder: '请选择...',
                                allowClear: false,
                                optionConfig: {
                                    label: 'brand',
                                    value: 'setId',
                                    linkageFields: {
                                        brandName:'brand'
                                    },
                                    children:'childrenList'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type:'string',
                                field: 'brandName',
                                hide:true
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            isInForm: false,
                            form: {
                                label:'型号',
                                type: 'string',
                                field:'modelName'
                            }
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'modelName',
                                key: 'modelName'
                            },
                            form: {
                                type: 'select',
                                field:'model',
                                spanSearch: 8,
                                parent:'brand',
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true,
                                placeholder: '请选择...',
                                allowClear: false,
                                optionConfig: {
                                    label: 'model',
                                    value: 'setId',
                                    linkageFields: {
                                        modelName:'model'
                                    },
                                    children:'childrenList'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type:'string',
                                field: 'modelName',
                                hide:true
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            isInForm: false,
                            form: {
                                label:'颜色',
                                type: 'string',
                                field:'colourName'
                            }
                        },
                        {
                            table: {
                                title: '颜色',
                                dataIndex: 'colourName',
                                key: 'colourName'
                            },
                            form: {
                                type: 'select',
                                spanSearch: 8,
                                field:'colour',
                                parent:'model',
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'colour',
                                    value: 'setId',
                                    linkageFields: {
                                        setId: 'setId',
                                        colourName:'colour'
                                    },
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type:'string',
                                field: 'colourName',
                                hide:true
                            }
                        },
                        {
                            table: {
                                title: '本次增加数量',
                                dataIndex: 'thisAddNum',
                                key: 'thisAddNum'
                            },
                            form: {
                                type: 'integer',
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            isInForm: false,
                            form: {
                                label: '入库时间',
                                field: 'putTimeSearch',
                                spanSearch: 8,
                                type: 'rangeDate',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '入库时间',
                                dataIndex: 'putTime',
                                key: 'putTime',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入',
                                span: 12,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: new Date()
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark',
                                // tooltip: 23,
                                width: 300
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
                                autoSize: {
                                    minRows: 3,
                                    maxRows: 7
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;