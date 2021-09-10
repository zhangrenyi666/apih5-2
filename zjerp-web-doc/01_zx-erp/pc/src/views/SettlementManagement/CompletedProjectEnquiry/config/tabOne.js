

const shortLabel = {
    labelCol: {
        sm: { span: 3 }
    },
    wrapperCol: {
        sm: { span: 21 }
    }
}

export default {
    fetchConfig: {
        apiName: 'getZxGcsgCtContractDetails',
        params: {
            ctContractId: "ctContractId"
        },
        otherParams: {},
    },
    formConfig: [
        {
            type: 'string',
            label: 'ctContrApplyId',
            field: 'ctContrApplyId',
            hide: true
        },
        {
            type: 'string',
            label: 'ctContractId',
            field: 'ctContractId',
            hide: true
        },
        {
            type: 'Component',
            label: '评审记录查看',
            field: 'psjlck',
            span: 24,
            Component: "PsjlckBtn"
        },
        {
            type: 'string',
            label: '合同编号',
            field: 'contractNo',
            placeholder: '请输入',
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
            field: 'contractManageType',
            optionData: [
                { label: "内部分包", value: "0" },
                { label: "专业分包", value: "1" },
                { label: "劳务分包", value: "2" },
                { label: "总体分包", value: "3" },
            ],
            span: 8
        },
        {
            type: 'selectByPaging',
            label: '合同甲方',
            field: 'firstId',
            placeholder: '请输入',
            required: true,
            span: 8,
            optionConfig: {
                label: "orgName",
                value: "orgID",
            },
            fetchConfig: {
                apiName: "getZxCtContractList",
            },
            onChange: "bind:firstIDChange",
            disabled: true
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
        },
        {
            type: 'number',
            label: '含税合同金额（万元）',
            field: 'contractCost',
            placeholder: '请输入',
            disabled: true,
            span: 8,
            initialValue: 0
        },
        {
            type: 'number',
            label: '不含税合同金额（万元）',
            field: 'contractCostNoTax',
            placeholder: '请输入',
            disabled: true,
            span: 8,
            initialValue: 0
        },
        {
            type: 'number',
            label: '合同税额（万元）',
            field: 'contractCostTax',
            disabled: true,
            span: 8,
            initialValue: 0
        },
        {
            type: 'number',
            label: '变更后含税合同金额（万元）',
            field: 'alterContractSum',
            disabled: true,
            span: 8,
            initialValue: 0
        },
        {
            type: 'number',
            label: '变更后不含税合同金额（万元）',
            field: 'alterContractSumNoTax',
            disabled: true,
            span: 8,
            initialValue: 0
        },
        {
            type: 'number',
            label: '变更后合同税额（万元）',
            field: 'alterContractSumTax',
            disabled: true,
            span: 8,
            initialValue: 0
        },

        {
            type: 'radio',
            label: '是否抵扣',
            field: 'isDeduct',
            disabled: true,
            span: 8,
            optionData: [
                { label: "否", value: "0" },
                { label: "是", value: "1" }
            ]
        },
        {
            type: 'date',
            label: '签订日期',
            field: 'signatureDate',
            required: true,
            span: 8,
        },

        {
            type: 'string',
            label: '项目经理',
            field: 'projectManager',
            placeholder: '请输入',
            span: 8,
        },
        {
            type: 'string',
            label: '乙方代表',
            field: 'secondPrincipal',
            placeholder: '请输入',
            span: 8,
        },
        {
            type: 'string',
            label: '甲方联系电话',
            field: 'firstUnitTel',
            span: 8,
        },
        {
            type: 'string',
            label: '乙方（固话）',
            field: 'secondUnitTel',
            span: 8,
        },
        {
            type: 'string',
            label: '乙方（手机）',
            field: 'secondPartyMobile',
            span: 8,
        },
        {
            type: 'string',
            label: '乙方（传真）',
            field: 'secondPartyFax',
            span: 8,
        },

        {
            type: 'select',
            label: '乙方性质',
            field: 'agent',
            required: true,
            span: 8,
            optionData: [
                { label: "单位", value: "0" },
                { label: "个人", value: "1" },
            ]
        },
        {
            type: 'date',
            label: '合同开工日期',
            field: 'planStartDate',
            span: 8,
        },
        {
            type: 'date',
            label: '合同竣工日期',
            field: 'planEndDate',
            span: 8,
        },
        {
            // type: 'string',
            label: '合同录入类型',
            field: 'auditStatus',
            disabled: true,
            span: 8,
            initialValue: "auditPassed",
            type: 'select',
            optionData: [
                { label: "评审通过", value: "auditPassed" },
            ]
        },
        {
            type: 'select',
            label: '合同状态',
            field: 'contractStatus',
            span: 8,
            disabled: true,
            optionData: [
                { label: "执行中", value: "1" },
                { label: "终止", value: "3" },
            ]
        },
        {
            // type: 'select',
            type: 'string',
            label: '结算情况',
            field: 'settleType',
            span: 8,
            required: true,
            disabled: true,
            optionData: [
                { label: "集中结算前终止", value: "0" },
                { label: "实际无结算", value: "1" },
                { label: "结算开始执行", value: "2" },
                { label: "以最终结算", value: "3" },
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

        {
            type: 'qnnTable',
            label: '保证金明细',
            field: 'coContractAmtRateList',
            incToForm: true,
            formItemStyle: {
                marginLeft: 4
            },
            formItemLayout: shortLabel,
            qnnTableConfig: {
                // tableTdEdit: true,
                antd: {
                    rowKey: "ctCoContractAmtRateId",
                    size: 'small',
                },
                actionBtns: [
                    {
                        field: 'addRow',
                        name: 'addRow',//【add, addRow,  del, edit, detail, Component, form】
                        icon: 'plus',
                        type: 'primary',
                        label: '新增',
                    },
                    {
                        field: 'del',
                        name: 'del',//【add, addRow,  del, edit, detail, Component, form】
                        icon: 'del',
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        table: {
                            title: `保证金<span style="color:red">*</span>`,
                            dataIndex: 'statisticsName',
                            width: 120,
                            tdEdit: true
                        }
                        , form: {
                            type: 'string',
                            required: true,
                            //默认生产的三个数据不让编辑
                            disabled: ({ record }) => {
                                return record?.statisticsNo;
                            }
                        }
                    },
                    {
                        table: {
                            title: '扣除比例(%)',
                            dataIndex: 'statisticsRate',
                            width: 120,
                            tdEdit: true
                        }
                        , form: {
                            type: 'number',
                        }
                    },
                    {
                        table: {
                            title: '期限',
                            dataIndex: 'timeLimit',
                            width: 120,
                            tdEdit: true
                        }
                        , form: {
                            type: 'string',
                        }
                    },
                    {
                        table: {
                            title: '返回条件',
                            dataIndex: 'backCondition',
                            tdEdit: true
                        }
                        , form: {
                            type: 'string',
                        }
                    },
                ],
            }
        },
        {
            type: 'string',
            label: '授权委托人姓名',
            field: 'wtrName',
            span: 8,
        },
        {
            type: 'identity',
            label: '授权委托人身份证号',
            field: 'wtrPhone',
            span: 8,
        },
        {
            type: 'string',
            label: '推荐人姓名',
            field: 'referenceName',
            span: 8,
        },
        {
            type: 'string',
            label: '推荐人职务',
            field: 'referencePost',
            span: 8,
        },
        {
            type: 'string',
            label: '推荐人电话',
            field: 'referencePhone',
            span: 8,
        },
        {
            type: 'files',
            label: '附件上传',
            field: 'commonAttachmentList',
            required: true,
            fetchConfig: { apiName: 'upload' },
            max: 999,
            span: 24,
            formItemLayout: shortLabel,
            formItemStyle: {
                marginLeft: 4
            },
        },

    ]
}