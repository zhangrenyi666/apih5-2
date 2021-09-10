import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["设备租赁限价采集"], //标题字段
        apiNameByAdd: "updateZxEqEquipLimitPrice",
        apiNameByUpdate: "updateZxEqEquipLimitPrice",
        apiNameByGet: "getZxEqEquipLimitPriceDetails",
        flowId: 'zxEqEquipLimitPrice',
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    parameterName:'orgID'
};
class index extends Component {
    render() {
        const { isInQnnTable, flowData } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    formConfig={[
                        {
                            field: 'id',
                            type: 'string',
                            initialValue: flowData?.id,
                            hide: true,
                        },
                        {
                            field: 'workId',
                            type: 'string',
                            initialValue: flowData?.workId,
                            hide: true,
                        },
                        {
                            field: 'orgID',
                            initialValue:flowData?.orgID,
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'comID',
                            initialValue:flowData?.comID,
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'orgName',
                            initialValue:flowData?.orgName,
                            type: 'string',
                            hide: true,
                        },
                        {
                            label: '评审编号',
                            field: "applyNo",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.applyNo,
                            placeholder: '请输入',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '公司名称',
                            field: "comName",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.comName,
                            placeholder: '请输入',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '数据期次',
                            field: "periodDate",
                            type: 'halfYear',
                            // optionConfig: {
                            //     label: 'itemName',
                            //     value: 'itemId',
                            // },
                            // fetchConfig: {
                            //     apiName: 'getBaseCodeSelect',
                            //     otherParams: {
                            //         itemId: 'eqManPriceQiCi'
                            //     }
                            // },
                            qnnDisabled: true,
                            initialValue: flowData?.periodDate,
                            placeholder: '请选择',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '填报人',
                            field: "reporter",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.reporter,
                            placeholder: '请输入',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '填报日期',
                            field: "reportDate",
                            type: 'date',
                            qnnDisabled: true,
                            initialValue: flowData?.reportDate,
                            placeholder: '请输入',
                            span: 8,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'qnnTable',
                            field: 'itemList',
                            incToForm: true,
                            initialValue: flowData?.itemList,
                            qnnTableConfig: {
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '机种分类',
                                            dataIndex: 'resCatalogID',
                                            key: 'resCatalogID',
                                            width: 150,
                                            fixed: 'left',
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: "<div>设备编码<span style='color: #ff4d4f'>*</span></div>",
                                            dataIndex: 'equipID',
                                            key: 'equipID',
                                            width: 150,
                                            fixed: 'left',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {//下拉选项配置
                                                label: 'catName',
                                                value: 'id'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxEqResCategoryItemList'
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            required: true,
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '设备编码',
                                            dataIndex: 'equipNo',
                                            key: 'equipNo',
                                            width: 150,
                                            fixed: 'left',
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '所在省份',
                                            dataIndex: 'province',
                                            key: 'province',
                                            width: 150,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId'
                                            },
                                            fetchConfig:{
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'xingzhengquhuadaima'
                                                }
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '厂家',
                                            dataIndex: 'factory',
                                            key: 'factory',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '规格型号',
                                            dataIndex: 'spec',
                                            key: 'spec',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '工作时间',
                                            dataIndex: 'workTime',
                                            key: 'workTime',
                                            width: 150,
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
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '租赁情况',
                                            dataIndex: 'rentContent',
                                            key: 'rentContent',
                                            width: 150,
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
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '租赁限价(元/台.月)',
                                            dataIndex: 'rentPrice',
                                            key: 'rentPrice',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '是否含司机',
                                            dataIndex: 'isDriver',
                                            key: 'isDriver',
                                            width: 150,
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
                                            ],
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remark',
                                            key: 'remark',
                                            width: 150
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                ]
                            }
                        },
                        {
                            label: '备注',
                            field: "remark",
                            type: 'textarea',
                            qnnDisabled: true,
                            initialValue: flowData?.remark,
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
                        },
                        {
                            type: "textarea",
                            label: "物设部负责人",
                            field: "opinionField1",
                            opinionField: true,
                            addShow: false,
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
                        },
                        {
                            type: "textarea",
                            label: "设备主管领导",
                            field: "opinionField2",
                            opinionField: true,
                            addShow: false,
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
                        },
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                />
            </div>
        );
    }
}
export default index;
