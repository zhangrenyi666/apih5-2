import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["物资合同评审","contractName","contractNo"], //标题字段
        apiNameByAdd: "addZxCtSuppliesContrApplyFlow",
        apiNameByUpdate: "updateZxCtSuppliesContrApplyFlow",
        apiNameByGet: "getZxCtSuppliesContrApplyFlowDetail",
        flowId: 'ZxCtSuppliesContrApply',
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    parameterName:'orgID',
    isHaveDoc: true,
    docFieldLable:"公文正文",
	docFieldName: "applyFileList",
    docFieldIsRequired: true,
    docIsReadOnly:false,
    docFormFormItemLayout:{
        labelCol: {
            xs: { span: 4 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 20 },
            sm: { span: 20 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {  
            
        }
    }
    render() {
        const { isInQnnTable,flowData } = this.props;
        return (
            <div style={{height: isInQnnTable ? "" : "100vh"}}>
                <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    formConfig={[
                        {
                            field: 'applyId',
                            type: 'string',
                            initialValue: flowData?.applyId,
                            hide: true,
                        },
                        {
                            field: 'workId',
                            type: 'string',
                            initialValue: flowData?.workId,
                            hide: true,
                        },
                        {
                            field: 'apih5FlowStatus',
                            type: 'string',
                            initialValue:flowData?.apih5FlowStatus,
                            hide: true,
                        },
                        {
                            field: 'firstName',
                            type: 'string',
                            initialValue:flowData?.firstName,
                            hide: true,
                        },
                        {
                            field: 'beginPer',
                            type: 'string',
                            initialValue: flowData?.beginPer,
                            hide: true,
                        },
                        {
                            label: '合同编号',
                            field: "contractNo",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractNo,
                            placeholder: '请输入',
                            span: 12,
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
                            label: '合同名称',
                            field: "contractName",
                            type: 'string',
                            required: true,
                            qnnDisabled: true,
                            initialValue: flowData?.contractName,
                            placeholder: '请输入',
                            span: 12,
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
                            label: '合同类型',
                            field: "contractType",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractType,
                            placeholder: '请输入',
                            span: 12,
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
                            field: 'comID',
                            type: 'string',
                            initialValue: flowData?.comID,
                            hide: true,
                        },
                        {
                            field: 'comName',
                            type: 'string',
                            initialValue: flowData?.comName,
                            hide: true,
                        },
                        {
                            field: 'orgID',
                            type: 'string',
                            initialValue: flowData?.orgID,
                            hide: true,
                        },
                        {
                            field: 'orgName',
                            type: 'string',
                            initialValue: flowData?.orgName,
                            hide: true,
                        },
                        {
                            label: '甲方名称',
                            field: 'firstName',
                            type: 'string',
                            initialValue:flowData?.firstName,
                            required: true,
                            qnnDisabled: true,
                            span: 12,
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
                            label: '甲方名称',
                            type:'string',
                            field: "firstID",
                            initialValue:flowData?.firstID,
                            hide: true,
                        },
                        {
                            label: '乙方名称',
                            field: 'secondName',
                            type: 'string',
                            initialValue:flowData?.secondName,
                            required: true,
                            qnnDisabled: true,
                            span: 12,
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
                            label: '乙方名称',
                            field: "secondID", 
                            type:'string',
                            initialValue:flowData?.secondID,  
                            hide: true
                        },
                        {
                            label: '合同签订人',
                            field: "agent",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.agent,
                            placeholder: '请输入',
                            span: 12,
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
                            label: '含税合同金额(万元)',
                            field: "contractCost",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCost,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
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
                            label: '不含税合同金额(万元)',
                            field: "contractCostNoTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCostNoTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
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
                            label: '合同税额(万元)',
                            field: "contractCostTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCostTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
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
                            label: '是否抵扣',
                            field: "isDeduct",
                            type: 'checkbox',
                            optionData: [
                                {
                                    value: '1'
                                }
                            ],
                            qnnDisabled: true,
                            span: 12,
                            initialValue: flowData?.isDeduct,
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
                            label: '合同工期',
                            field: "csTimeLimit",
                            type: 'string',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            span: 12,
                            initialValue: flowData?.csTimeLimit,
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
                            label: '合同类别',
                            field: "code7",
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'category_contr_type_CL'
                                }
                            },
                            initialValue: flowData?.code7,
                            placeholder: '请选择',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            },
                            qnnDisabled: true,
                            required: true
                        },
                        {
                            label: '物资来源',
                            field: "materialSource",
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'category_contract_materialSource'
                                }
                            },
                            condition: [
                                {
                                    regex: { 'apiBody.code7': 'WZ' },
                                    action: ['required', 'unDisabled'],
                                },
                                {
                                    regex: { 'apiBody.code7': ['!', 'WZ'] },
                                    action: ['unRequired', 'disabled'],
                                }
                            ],
                            initialValue: flowData?.materialSource,
                            qnnDisabled: true,
                            placeholder: '请选择',
                            span: 12,
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
                        // {
                        //     label: '采购方式',
                        //     field: "shopWay",
                        //     type: 'select',
                        //     optionConfig: {
                        //         label: 'label', //默认 label
                        //         value: 'value'
                        //     },
                        //     optionData: [
                        //         {
                        //             label: '招标采购',
                        //             value: '0'
                        //         },
                        //         {
                        //             label: '云电商采购',
                        //             value: '1'
                        //         },
                        //         {
                        //             label: '其他采购',
                        //             value: '2'
                        //         }
                        //     ],
                        //     initialValue: flowData?.shopWay,
                        //     placeholder: '请选择',
                        //     span: 12,
                        //     qnnDisabled: true,
                        //     required: true,
                        //     formItemLayout: {
                        //         labelCol: {
                        //             xs: { span: 8 },
                        //             sm: { span: 8 }
                        //         },
                        //         wrapperCol: {
                        //             xs: { span: 16 },
                        //             sm: { span: 16 }
                        //         }
                        //     }
                        // },
                        // {
                        //     label: '采购编号',
                        //     field: "shopNumber",
                        //     type: 'string',
                        //     condition: [
                        //         {
                        //             regex: { 'apiBody.shopWay': '2' },
                        //             action: ['unRequired', 'disabled'],
                        //         },
                        //         {
                        //             regex: { 'apiBody.shopWay': ['!', '2'] },
                        //             action: ['required', 'unDisabled'],
                        //         }
                        //     ],
                        //     qnnDisabled: true,
                        //     initialValue: flowData?.shopNumber,
                        //     placeholder: '请输入',
                        //     span: 12,
                        //     formItemLayout: {
                        //         labelCol: {
                        //             xs: { span: 8 },
                        //             sm: { span: 8 }
                        //         },
                        //         wrapperCol: {
                        //             xs: { span: 16 },
                        //             sm: { span: 16 }
                        //         }
                        //     }
                        // },
                        {
                            field: 'resCategoryName',
                            type: 'string',
                            initialValue: flowData?.resCategoryName,
                            hide: true,
                        },
                        {
                            label: '物资类别',
                            field: "resCategoryID",
                            type: 'select',
                            optionConfig: {
                                label: 'catName', //默认 label
                                value: 'id'
                            },
                            fetchConfig: {
                                apiName: 'getZxSkResCategoryMaterialsList',
                                otherParams: {
                                    parentID: '0002'
                                }
                            },
                            qnnDisabled: true,
                            initialValue: flowData?.resCategoryID,
                            placeholder: '请选择',
                            span: 12,
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
                            label: '合同内容',
                            field: 'content',
                            type: 'textarea',
                            initialValue: flowData?.content,
                            placeholder: '请输入',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '备注',
                            field: 'remarks',
                            type: 'textarea',
                            initialValue: flowData?.remarks,
                            placeholder: '请输入',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: 'qnnTable',
                            field: 'qnnTableList',
                            qnnTableConfig: {
                                fetchConfig: (obj) => {
                                    if(flowData?.code7 && flowData?.code7 !== 'WL'){
                                        return {
                                            apiName:'getZxCtSuppliesContrApplyShopListList',
                                            otherParams: {
                                                pp5: flowData?.applyId
                                            }
                                        }
                                    }else if(flowData?.code7 === 'WL'){
                                        return {
                                            apiName:'getZxCtSuppliesContrApplyLeaseListList',
                                            otherParams: {
                                                pp5: flowData?.applyId
                                            }
                                        }
                                    }else if(obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.code7 && obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.code7 !== 'WL'){
                                        return {
                                            apiName:'getZxCtSuppliesContrApplyShopListList',
                                            otherParams: {
                                                pp5: obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.applyId
                                            }
                                        }
                                    }else if(obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.code7 && obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.code7 === 'WL'){
                                        return {
                                            apiName:'getZxCtSuppliesContrApplyLeaseListList',
                                            otherParams: {
                                                pp5: obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.applyId
                                            }
                                        }
                                    }
                                    // return {};
                                    // apiName: flowData?.code7 !== 'WL' ? 'getZxCtSuppliesContrApplyShopListList' : 'getZxCtSuppliesContrApplyLeaseListList',
                                    
                                },
                                antd: {
                                    size: "small",
                                    rowKey: (rowData) => {
                                        return rowData.applyLeaseListId ? rowData.applyLeaseListId : rowData.applyShopListId;
                                    }
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'applyLeaseListId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'applyShopListId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'pp5',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'workID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'workType',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资类别',
                                            dataIndex: 'workTypeID',
                                            key: 'workTypeID',
                                            width: 150,
                                            type: 'select',
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'catName', //默认 label
                                                value: 'id'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxSkResCategoryMaterialsList',
                                                otherParams: {
                                                    parentID: '0002'
                                                }
                                            },
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资编码',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 150,
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            width: 150
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资规格',
                                            dataIndex: 'spec',
                                            key: 'spec',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否网价',
                                            dataIndex: 'isNetPrice',
                                            key: 'isNetPrice',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '1') {
                                                    return '是';
                                                } else {
                                                    return '否';
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'checkbox',
                                            optionData: [
                                                {
                                                    value: "1"
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        isInTable: (obj) => {
                                            let rowData = obj.qnnFormProps.form.getFieldsValue();
                                            if(rowData.apiBody.code7 === 'WL'){
                                                return true;
                                            }else{
                                                return false
                                            }
                                        },
                                        table: {
                                            title: '租期单位',
                                            dataIndex: 'rentUnit',
                                            key: 'rentUnit',
                                            width: 150
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        isInTable: (obj) => {
                                            let rowData = obj.qnnFormProps.form.getFieldsValue();
                                            if(rowData.apiBody.code7 === 'WL'){
                                                return true;
                                            }else{
                                                return false
                                            }
                                        },
                                        table: {
                                            title: '租期',
                                            dataIndex: 'contrTrrm',
                                            key: 'contrTrrm',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '数量',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            width: 100,
                                            render:(data,rowData) => {
                                                if(rowData.qty || rowData.qty === 0){
                                                    return rowData.qty.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同单价',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 120,
                                            render:(data,rowData) => {
                                                if(rowData.price || rowData.price === 0){
                                                    return rowData.price.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同金额',
                                            dataIndex: 'contractSum',
                                            key: 'contractSum',
                                            width: 120,
                                            render:(data,rowData) => {
                                                if(rowData.contractSum || rowData.contractSum === 0){
                                                    return rowData.contractSum.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '不含税合同单价',
                                            dataIndex: 'priceNoTax',
                                            key: 'priceNoTax',
                                            width: 120,
                                            render:(data,rowData) => {
                                                if(rowData.priceNoTax || rowData.priceNoTax === 0){
                                                    return rowData.priceNoTax.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '不含税合同金额',
                                            dataIndex: 'contractSumNoTax',
                                            key: 'contractSumNoTax',
                                            width: 120,
                                            render:(data,rowData) => {
                                                if(rowData.contractSumNoTax || rowData.contractSumNoTax === 0){
                                                    return rowData.contractSumNoTax.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税额',
                                            dataIndex: 'contractSumTax',
                                            key: 'contractSumTax',
                                            width: 100,
                                            render:(data,rowData) => {
                                                if(rowData.contractSumTax || rowData.contractSumTax === 0){
                                                    return rowData.contractSumTax.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税率(%)',
                                            dataIndex: 'taxRate',
                                            key: 'taxRate',
                                            width: 100,
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
                                                    itemId: 'shuiLV'
                                                }
                                            },
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否抵扣',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '1') {
                                                    return '是';
                                                } else {
                                                    return '否';
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            type: "textarea",
                            label: "审批意见",
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
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                />
            </div>
        );
    }
}
export default index;
