import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.managementId
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
            xs: { span: 5 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 19 },
            sm: { span: 19 }
        }
    },
    isShowRowSelect:false
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
                    fetchConfig={{
                        apiName: 'getZjXmJxQuarterlyWeightManagementList',
                        otherParams: {
                            isClosed:'0'
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'managementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                field:'isClosed',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目类型',
                                dataIndex: 'projectTypeName',
                                key: 'projectTypeName',
                                width:150,
                                fixed:'left',
                                onClick:'edit',
                                btns:[
                                    {
                                        name: 'cancel', //关闭右边抽屉
                                        type: 'dashed',//类型  默认 primary
                                        label: '取消',
                                    },
                                    {
                                        name: 'submit',//内置add del
                                        type: 'primary',//类型  默认 primary
                                        label: '保存',//提交数据并且关闭右边抽屉 
                                        fetchConfig: {//ajax配置
                                            apiName: 'singleUpdateZjXmJxQuarterlyWeightManagement',
                                        }
                                    }
                                ]
                            },
                            form: {
                                field:'projectType',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData:[
                                    {
                                        label: '全部类型',
                                        value: '0'
                                    },
                                    {
                                        label: '路桥类型',
                                        value: '1'
                                    },
                                    {
                                        label: '房建类型',
                                        value: '2'
                                    },
                                    {
                                        label: '轨道类型',
                                        value: '3'
                                    }
                                ],
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '路桥事业部权重',
                                dataIndex: 'roadBridgeWeight',
                                key: 'roadBridgeWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '城市房建事业部权重',
                                dataIndex: 'housingWeight',
                                key: 'housingWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '铁路轨道事业部权重',
                                dataIndex: 'trackWeight',
                                key: 'trackWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '技术质量部权重',
                                dataIndex: 'technicalWeight',
                                key: 'technicalWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '安全监督部权重',
                                dataIndex: 'safetyWeight',
                                key: 'safetyWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '经营考核部权重',
                                dataIndex: 'businessWeight',
                                key: 'businessWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '财务部权重',
                                dataIndex: 'financeWeight',
                                key: 'financeWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '物资设备部权重',
                                dataIndex: 'materialsWeight',
                                key: 'materialsWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '人力资源部权重',
                                dataIndex: 'humanResourcesWeight',
                                key: 'humanResourcesWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '法律部权重',
                                dataIndex: 'legalWeight',
                                key: 'legalWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '办公室权重',
                                dataIndex: 'officeWeight',
                                key: 'officeWeight',
                                width:150
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '经营考核部供应链管理部权重',
                                dataIndex: 'supplyChainWeight',
                                key: 'supplyChainWeight',
                                width:200
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '经营考核部收尾中心权重',
                                dataIndex: 'closingCenterWeight',
                                key: 'closingCenterWeight',
                                width:180
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        // {
                        //     table: {
                        //         title: '备注',
                        //         dataIndex: 'remarks',
                        //         key: 'remarks',
                        //         width:150
                        //     },
                        //     form: {
                        //         type: 'textarea',
                        //         placeholder: '请输入'
                        //     }
                        // }
                    ]}
                />
            </div>
        );
    }
}

export default index;