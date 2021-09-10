// 财务信息
import React from "react"

export default {
    fetchConfig: {
        apiName: 'getZxGcsgCtContractDetails',
        params: {
            ctContractId: "ctContractId"
        },
    },
    formConfig: [
        {
            type: "component",
            field: "component2",
            Component: obj => {
                return (
                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                        业务信息
                    </div>
                );
            }
        },
        {
            label: '合同名称',
            field: 'contractNames',
            type: 'string',
            disabled: true,
            placeholder: '请输入',
            span: 8
        },
        {
            label: '合同编号',
            field: 'contractNos',
            type: 'string',
            disabled: true,
            span: 8,
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
        },
        {
            label: '合同乙方',
            field: "secondNames",
            type: 'string',
            span: 8,
            disabled: true,
        },
        {
            label: '变更后含税合同金额',
            field: 'alterContractSums',
            type: 'number',
            placeholder: '请输入',
            disabled: true,
            span: 8,
            precision: 6,
            initialValue: 0,
        },
        {
            type: "component",
            field: "component3",
            Component: obj => {
                return (
                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                        财务信息
                    </div>
                );
            }
        },
        {
            label: '往来单位编号',
            field: 'secondIDCode',
            type: 'selectByQnnTable',
            optionConfig: {
                label: 'lswldwWldwbh',
                value: 'lswldwWldwbh',
                linkageFields: {
                    secondIDCodeName: 'lswldwDwmc',
                    orgCertificate: 'lswldwSh',
                }
            },
            onChange: (val, obj) => {
                if (!val) {
                    obj.form.setFieldsValue({
                        secondIDCodeName: null,
                        orgCertificate: null,
                    })
                } else {
                    obj.form.setFieldsValue({
                        zjxmhtbBh: null,
                        accPaymentAmount: null,
                        pp4_pp5: null,
                        accountUnitName: null,
                        accountUnitCode: null,
                        fiCtrState: null,
                        invoiceType: null,
                        ctrNature: null,
                        revenueDir: null,
                        keyCtr: null,
                        staCtr: null,
                        advanceRate: null,
                        progressRate: null,
                        completionRate: null,
                        zjgcxmXmmc: null,
                        taxCountWay: null,
                    })
                }
            },
            required: true,
            dropdownMatchSelectWidth: 800,
            qnnTableConfig: {
                antd: {
                    rowKey: "lswldwWldwbh"
                },
                fetchConfig: {
                    apiName: "getZxSaFiWldwList",
                    params: {
                        lswldwDwmc: "secondName"
                    },
                    otherParams: 'bind:getZxSaFiWldwListParams'
                },
                searchBtnsStyle: "inline",
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "lswldwDwmc",
                            title: "往来单位名称",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "lswldwSh",
                            title: "统一社会信用代码",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "lswldwWldwbh",
                            title: "往来单位编号",
                        },
                        form: {
                            type: "string"
                        }
                    },
                ]
            },
            placeholder: '请选择',
            span: 8
        },
        {
            label: '往来单位名称',
            field: 'secondIDCodeName',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '统一社会信用代码',
            field: 'orgCertificate',
            type: 'string',
            placeholder: '请输入',
            disabled: true,
            span: 8
        },
        {
            label: '财务合同名称',
            field: "zjxmhtbMc",
            type: 'selectByQnnTable',
            span: 8,
            required: true,
            dependencies: ['secondIDCode'],
            dependenciesReRender: true,
            parent: 'secondIDCode',
            allowClear: false,
            optionConfig: {
                label: 'zjxmhtbHtmc',
                value: 'zjxmhtbHtmc',
                linkageFields: {
                    zjxmhtbBh: 'zjxmhtbBh',//财务合同编号
                    accPaymentAmount: 'klzfje',//开累支付金额
                    accountUnitName: 'zjxmhtbDwmc',//核算单位名称
                    accountUnitCode: 'zjxmhtbDwbh',//核算单位编号
                    fiCtrState: 'htzt',//财务合同状态
                    invoiceType: 'fplx',//发票类型
                    ctrNature: 'htxz',//合同性质
                    revenueDir: 'szfx',//收支方向
                    keyCtr: 'sfzd',//重点合同
                    staCtr: 'sfzs',//制式合同
                    advanceRate: 'yfkbl',//预付款
                    progressRate: 'jdkbl',//进度
                    completionRate: 'jgkbl',//竣工
                    zjgcxmXmmc: 'zjgcxmXmmc',//财务项目
                    taxCountWay: 'jsff'//计税方法
                }
            },
            onChange: (val, obj) => {
                let DrawerData = obj.form.getFieldsValue()
                obj.form.setFieldsValue({
                    pp4_pp5: DrawerData.accSettlementAmount - DrawerData.accPaymentAmount >= 0 ? DrawerData.accSettlementAmount - DrawerData.accPaymentAmount : 0
                })
            },
            dropdownMatchSelectWidth: 800,
            qnnTableConfig: {
                antd: {
                    rowKey: "zjxmhtbNm"
                },
                fetchConfig: (obj) => {
                    let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                    return {
                        apiName: "getZxSaFiZjxmhtbInGcHookup",
                        otherParams: {
                            zjxmhtbKhnm: pageData?.secondIDCode,
                            zjxmhtbBh: pageData?.contractNo,
                            contractID: pageData?.ctContractId,
                        }
                    }

                },
                searchBtnsStyle: "inline",
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "zjxmhtbBh",
                            title: "财务合同编号",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "zjxmhtbHtmc",
                            title: "财务合同名称",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "klzfje",
                            title: "开累支付金额",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "zjxmhtbDwmc",
                            title: "核算单位名称",
                        },
                        form: {
                            type: "string"
                        }
                    },
                ]
            },
        },
        {
            label: '财务合同编号',
            field: 'zjxmhtbBh',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '开累结算金额(PM)',
            field: 'accSettlementAmount',
            type: 'number',
            disabled: true,
            span: 8,
            initialValue:0
        },
        {
            label: '核算单位',
            field: 'accountUnitName',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '核算单位编号',
            field: 'accountUnitCode',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '开累支付金额(GS)',
            field: 'accPaymentAmount',
            type: 'number',
            disabled: true,
            span: 8
        },
        {
            label: '核算部门',
            field: "accountDepName",
            type: 'selectByQnnTable',
            span: 8,
            required: true,
            dependencies: ['zjxmhtbMc'],
            dependenciesReRender: true,
            parent: 'zjxmhtbMc',
            optionConfig: {
                label: 'lsbmzdDwmc',
                value: 'lsbmzdDwmc',
                linkageFields: {
                    accountDepCode: 'lsbmzdBmbh'//核算部门编号
                }
            },
            onChange: (val, obj) => {
                if (!val) {
                    obj.form.setFieldsValue({
                        accountDepCode: null,
                    })
                }
            },
            dropdownMatchSelectWidth: 800,
            qnnTableConfig: {
                antd: {
                    rowKey: "lsbmzdDwmc"
                },
                fetchConfig: (obj) => {
                    let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                    return {
                        apiName: "getZxSaFiBmzdList",
                        otherParams: {
                            lsbmzdDwbh: pageData?.accountUnitCode,
                        }
                    }

                },
                searchBtnsStyle: "inline",
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "lsbmzdDwmc",
                            title: "核算部门名称",
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "lsbmzdBmbh",
                            title: "核算部门编号",
                        },
                        form: {
                            type: "string"
                        }
                    },
                ]
            },
        },
        {
            label: '核算部门编号',
            field: 'accountDepCode',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '开累未支付金额',
            field: 'pp4_pp5',
            type: 'number',
            disabled: true,
            span: 8,
        },
        {
            label: '财务项目',
            field: 'zjgcxmXmmc',
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            label: '计税方法',
            field: 'taxCountWay',
            type: 'select',
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            required: true,
            optionData: [
                { label: '一般计税方法', value: '1' },
                { label: '简易计税方法', value: '2' }
            ],
            placeholder: '请选择',
            span: 8,
            onChange: (val, obj) => {
                if (val) {
                    obj.form.setFieldsValue({
                        taxRate: null,
                    })
                }
            },
        },
        {
            label: '税率%',
            field: 'taxRate',
            type: 'select',
            required: true,
            placeholder: '请选择',
            span: 8,
            optionConfig: {
                label: 'value',
                value: 'value',
            },
            dependencies: ['taxCountWay', 'invoiceType'],
            dependenciesReRender: true,
            disabled: (obj) => {
                let objData = obj?.form?.getFieldsValue()
                if (!objData.taxCountWay || !objData.invoiceType) return true
                return false
            },
            optionData: (obj) => {
                let objData = obj?.form?.getFieldsValue()
                if (objData?.invoiceType === '1') {
                    return [{ value: '0' }]
                }
                if (objData.taxCountWay === '2') {
                    return [{ value: '0' }, { value: '3' }, { value: '5' }]
                }
                if (objData.taxCountWay === '1') {
                    return [
                        { value: '0' }, { value: '1' }, { value: '1.5' },
                        { value: '3' }, { value: '5' }, { value: '6' },
                        { value: '9' }, { value: '10' }, { value: '11' },
                        { value: '13' }, { value: '16' }, { value: '17' },
                    ]
                }
            },
        },
        {
            label: '财务合同状态',
            field: 'fiCtrState',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '录入', value: '1' },
                { label: '生效', value: '2' },
                { label: '终止', value: '3' },
            ],
            span: 8
        },
        {
            label: '发票类型',
            field: 'invoiceType',
            type: 'select',
            required: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '增值税专用发票', value: '0' },
                { label: '增值税普通发票', value: '1' }
            ],
            span: 8,
            onChange: (val, obj) => {
                if (val) {
                    obj.form.setFieldsValue({
                        taxRate: null,
                    })
                }
            },
        },
        {
            label: '币种',
            field: 'bz',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [{
                label: '人民币',
                value: '1',
            }],
            initialValue: '1',
            span: 8
        },
        {
            label: '合同性质',
            field: 'ctrNature',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '工程合同', value: '1' },
                { label: '双包', value: '2' },
                { label: '清包', value: '3' },
                { label: '采购', value: '4' },
                { label: '加工', value: '5' },
                { label: '服务', value: '6' },
                { label: '租赁', value: '4' },
                { label: '其他', value: '5' },
                { label: '集中采购', value: '6' },
            ],
            span: 8
        },
        {
            label: '收支方向',
            field: 'revenueDir',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '收入', value: '01' },
                { label: '支出', value: '02' },
            ],
            span: 8
        },
        {
            label: '重点合同',
            field: 'keyCtr',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '否', value: '0' },
                { label: '是', value: '1' }
            ],
            span: 4
        },
        {
            label: '制式合同',
            field: 'staCtr',
            type: 'select',
            disabled: true,
            optionConfig: {
                label: 'label',
                value: 'value',
            },
            optionData: [
                { label: '否', value: '0' },
                { label: '是', value: '1' }
            ],
            span: 4
        },
        {
            label: '预付款比例(%)',
            field: 'advanceRate',
            type: 'number',
            disabled: true,
            span: 8
        },
        {
            label: '进度款比例(%)',
            field: 'progressRate',
            type: 'number',
            disabled: true,
            span: 8
        },
        {
            label: '竣工款比例(%)',
            field: 'completionRate',
            type: 'number',
            disabled: true,
            span: 8
        },

        {
            type: "component",
            field: "component4",
            Component: obj => {
                return (
                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                        其他
                    </div>
                );
            }
        },
        {
            label: '录入人',
            field: 'writer',
            type: 'string',
            placeholder: '请输入',
            required: true,
            span: 6,
            initialValue: 'bind:getWriter'
        },
        {
            label: '录入日期',
            field: 'writeDate',
            type: 'date',
            required: true,
            span: 6
        },
        {
            label: '复核人',
            field: 'doubleCheckPerson',
            type: 'string',
            placeholder: '请输入',
            required: true,
            span: 6
        },
        {
            label: '复核日期',
            field: 'doubleCheckDate',
            type: 'date',
            required: true,
            span: 6
        }
    ]
}