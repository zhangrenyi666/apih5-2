//清单 表单配置 （表格右侧编辑按钮点击后的编辑）
import xiMingTableConfig from "./xiMingTableConfig"
const shortLabel = {
    labelCol: {
        sm: { span: 3 }
    },
    wrapperCol: {
        sm: { span: 21 }
    }
}

export default {
    formItemLayout: {
        labelCol: {
            sm: { span: 9 }
        },
        wrapperCol: {
            sm: { span: 15 }
        }
    },
    tailFormItemLayout: {
        wrapperCol: {
            sm: { offset: 3 }
        },
    },
    btns: [
        {
            label: "保存",
            onClick: "bind:qingDanEditSave",
            field: "qingDanEditSave",
            type: "primary"
        }
    ],
    formConfig: [
        {
            field: 'ccCoAlterId',
            type: 'string',
            hide: true
        },
        {
            field: "replyNo",
            label: "补充协议书编号",
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            field: "proposer",
            label: "补充协议名称",
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            field: "contractName",
            label: "原合同名称",
            type: 'string',
            disabled: true,
            span: 8
        },
        {
            field: "originalTaxAmount",
            label: "原含税合同金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "pp2NoTax",
            label: "原不含税合同金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "pp2Tax",
            label: "原合同税额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "applyAmount",
            label: "本期含税变更增减金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "applyAmountNoTax",
            label: "本期不含税变更增减金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "applyAmountTax",
            label: "本期变更增减税额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "replyAmount",
            label: "变更后含税合同金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "replyAmountNoTax",
            label: "变更后不含税合同金额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            field: "replyAmountTax",
            label: "变更后合同税额（万元）",
            type: 'number',
            disabled: true,
            span: 8,
            precision: 6, //数值精度 
        },
        {
            type: 'date',
            label: '日期',
            field: 'replyDate',
            required: false,
            span: 8,
        },
        {
            type: 'textarea',
            label: '备注',
            field: 'remarks',
            required: false,
            formItemStyle: {
                marginLeft: 4
            },
            formItemLayout: shortLabel,
            // autoSize:{ minRows: 2, maxRows: 6 }
        },
        {
            label: '附件',
            field: 'attachmentList',
            type: 'files',
            required: false,
            fetchConfig: { apiName: 'upload' },
            max: 999,
            formItemStyle: {
                marginLeft: 4
            },
            formItemLayout: shortLabel,
            help: " 提示：请上传变更申请的资料"
        },

        {
            type: 'qnnTable',
            // label: '变更细明',
            field: 'alterWorkList',
            incToForm: true,
            formItemStyle: {
                marginLeft: 4
            },
            // formItemLayout: shortLabel, 
            qnnTableConfig: {
                ...xiMingTableConfig
            },
        },




    ]
}
