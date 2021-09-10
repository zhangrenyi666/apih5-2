import React from "react"
const logLabel = {
    labelCol: {
        sm: { span: 13 }
    },
    wrapperCol: {
        sm: { span: 11 }
    }
}

const shortLabel = {
    labelCol: {
        sm: { span: 3 }
    },
    wrapperCol: {
        sm: { span: 21 }
    }
}

export default {
    formConfig: [
        {
            type: 'string',
            label: 'ctContrApplyId',
            field: 'ctContrApplyId',
            hide: true
        },
        {
            type: 'string',
            label: '合同编号',
            field: 'contractNo',
            placeholder: '请输入',
            required: false,
            disabled: true,
            span: 8
        },
        {
            type: 'string',
            label: '合同名称',
            field: 'contractName',
            placeholder: '请输入',
            required: true,
            span: 8
        },
        {
            type: 'select',
            label: '合同类型',
            field: 'contractType',
            placeholder: '请输入',
            disabled: true,
            initialValue: "P2",
            span: 8,
            optionData: [
                { label: "工程施工类", value: "P2" }
            ]
        },
        {
            type: 'selectByPaging',
            label: '甲方名称',
            field: 'firstID',
            placeholder: '请输入',
            required: true,
            span: 8,
            optionConfig: {
                label: "orgName",
                value: "orgID",
            }, 
            condition: [
                {
                    regex: { ctContrApplyId: ["!", undefined], },
                    action: 'disabled',  
                }, 
            ],
            fetchConfig: {
                apiName: "getZxCtContractListByStatus",
                otherParams: "bind:firstIDOtherParams"
            },
            onChange: "bind:firstIDChange"
        },
        {
            type: 'selectByPaging',
            // type: 'select',
            label: '乙方名称',
            field: 'secondID',
            placeholder: '请输入', 
            required: true,
            span: 8,
            fetchConfig: {
                apiName: "getZxCrCustomerExtAttrEngineeringList",
                searchKey:'customerName'
            },
            optionConfig: {
                label: "customerName",
                value: "zxCrCustomerExtAttrId",
            },
            pagination: {
                pageSizeOptions: [
                    10, 20
                ]
            }
        },
        {
            type: 'string',
            label: '合同签订人',
            field: 'agent',
            placeholder: '请输入',
            span: 8
        },
        {
            type: 'number',
            label: '含税合同金额（万元）',
            field: 'contractCost',
            placeholder: '请输入',
            disabled: true,
            span: 8,
            initialValue: 0
            // formItemLayout: logLabel
        },
        {
            type: 'number',
            label: '不含税合同金额（万元）',
            field: 'contractCostNoTax',
            placeholder: '请输入',
            disabled: true,
            span: 8,
            initialValue: 0
            // formItemLayout: logLabel
        },
        {
            type: 'number',
            label: '合同税额（万元）',
            field: 'contractCostTax',
            disabled: true,
            span: 8,
            initialValue: 0
            // formItemLayout: logLabel
        },
        {
            type: 'radio',
            label: '是否抵扣',
            field: 'isDeduct',
            span: 8,
            initialValue:"0",
            optionData: [
                { label: "否", value: "0" },
                { label: "是", value: "1" }
            ]
        },
        {
            type: 'date',
            label: '开工日期',
            field: 'startDate',
            placeholder: '请选择',
            span: 8,
        },
        {
            type: 'date',
            label: '竣工日期',
            field: 'endDate',
            placeholder: '请选择',
            span: 8,
        },
        {
            type: 'string',
            label: '现场负责人',
            field: 'legalPerson',
            placeholder: '请输入',
            required: true,
            span: 8,
        },
        {
            type: 'string',
            label: '委托代理人',
            field: 'agentPerson',
            placeholder: '请输入',
            required: true,
            span: 8,
        },
        {
            type: 'string',
            label: '法定代表人',
            field: 'chargePerson',
            placeholder: '请输入',
            required: true,
            span: 8,
        },
        {
            type: 'radio',
            label: '是否通过云电商进行招标',
            field: 'isBiddedOnCloud',
            initialValue:"0",
            required: true,
            span: 8,
            optionData: [
                { label: "否", value: "0" },
                { label: "是", value: "1" }
            ],
            formItemLayout: logLabel
        },
        {
            type: 'select',
            label: '招标方式',
            field: 'bidType',
            required: true,
            span: 8,
            optionData: [
                { label: "公开招标", value: "0" },
                { label: "邀请招标", value: "1" },
                { label: "其他", value: "2" }
            ]
        },
        {
            type: 'string',
            label: '云电商招标方案编号',
            field: 'cloudBidNo',
            disabled: true,
            span: 8,
        },
        {
            type: 'select',
            label: '组织招标主体',
            field: 'bidOrgType',
            required: true,
            span: 8,
            optionData: [
                { label: "公司", value: "0" },
                { label: "总承包部", value: "1" },
                { label: "项目部", value: "2" }
            ]
        },
        {
            type: 'radio',
            label: '各单位主馆部门是否参与',
            field: 'isAllDepartJoin',
            initialValue:"0",
            required: true,
            span: 8,
            optionData: [
                { label: "否", value: "0" },
                { label: "是", value: "1" }
            ],
            formItemLayout: logLabel
        },
        {
            label: '包件编号',
            field: 'cloudEcoID',
            span: 8,
            condition: [
                {
                    regex: { isBiddedOnCloud: "0", },
                    action: ['disabled', 'unRequired'],
                },
                {
                    regex: { isBiddedOnCloud: "1", },
                    action: ['unDisabled', "required"],
                },
            ], 
            type: "selectByQnnTable",
            onChange: "bind:packageNoChange",
            optionConfig: {
                label: "packageName",
                value: "id",
            },
            formItemLayout: {
                labelCol: {
                    sm: { span: 6 }
                },
                wrapperCol: {
                    sm: { span: 18 }
                }
            },
            dropdownMatchSelectWidth: 800,
            qnnTableConfig: {
                antd: {
                    rowKey: "id",
                    scroll: {
                        x: 800
                    }
                },
                desc: <a>如果包件编号未完全匹配将无法进行选择！请在oa系统中将该包件编号对应的投标单位全部进行入库。</a>,
                getRowSelection: function (obj) {
                    return {
                        columnWidth: 30,
                        //设置某行为禁止选中
                        getCheckboxProps: record => ({
                            disabled: (+record.isRela) === 0,
                        }),
                    }
                },
                selectType: "radio", //默认radio单选  值为 string 
                searchBtnsStyle: "inline",
                fetchConfig: {
                    apiName: "gcsgGetZxCtCloudEcoSelect",
                },
                formConfig: [
                    {
                        isInSearch: true,
                        table: {
                            dataIndex: "isRela",
                            title: "是否完全匹配",
                            render: (data) => +data === 0 ? "未完全匹配" : "已完全匹配",
                            width: 120
                        },
                        form: {
                            type: "select",
                            optionData: [
                                { label: "未完全匹配", value: "0" },
                                { label: "已完全匹配", value: "1" }
                            ]
                        }
                    },
                    {
                        isInSearch: true,
                        table: {
                            dataIndex: "winningUnit",
                            title: "投标单位",
                            width: 170
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        table: {
                            dataIndex: "packageNo",
                            title: "包件编号",
                            width: 170
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        table: {
                            dataIndex: "packageName",
                            title: "包件名称",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        table: {
                            dataIndex: "schemeNo",
                            title: "云电商招标方案编号",
                            width: 150
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        table: {
                            dataIndex: "schemeName",
                            title: "方案名称",
                            width: 220
                        },
                        form: {
                            type: "string"
                        }
                    },
                ]
            }
            // fetchConfig: {
            //     apiName: "gcsgGetZxCtCloudEcoSelect",
            // },
        },
        {
            type: 'select',
            label: '参与方式',
            field: 'joinType',
            required: true,
            span: 8,
            optionData: [
                { label: "现场", value: "0" },
                { label: "网上", value: "1" },
                { label: "未参与", value: "2" }
            ]
        },
        {
            type: 'radio',
            label: '各单位主管部门是否参与评标',
            field: 'isDepartJoinBid',
            required: true,
            initialValue:"0",
            span: 8,
            optionData: [
                { label: "否", value: "0" },
                { label: "是", value: "1" }
            ],
            formItemLayout: logLabel
        },
        {
            type: 'select',
            label: '合同所属事业部',
            field: 'contractDepart',
            required: true,
            span: 8,
            optionData: [
                { label: "公路市政事业部", value: "glsz" },
                { label: "铁路轨道事业部", value: "tlgd" },
                { label: "城市房建事业部", value: "csfj" }
            ]
        },
        {
            type: 'textarea',
            label: '合同内容',
            field: 'content',
            span: 24,
            formItemLayout: shortLabel,
            formItemStyle: {
                marginLeft: 4
            },
        },
        {
            type: 'textarea',
            label: '备注',
            field: 'remarks',
            span: 24,
            formItemLayout: shortLabel,
            formItemStyle: {
                marginLeft: 4
            },
        },
    ]
}