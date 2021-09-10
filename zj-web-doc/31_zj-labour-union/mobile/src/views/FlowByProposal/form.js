import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["proposalName", "部门落实"], //标题字段
        apiNameByAdd: "updateZjLabourUnionProposalFirstByFlow",
        apiNameByUpdate: "updateZjLabourUnionProposalSecondByFlow",
        apiNameByGet: "getZjLabourUnionProposalDetailsByFlow",
        flowId: "zjLabourUnionProposal",

        todo: "FlowByProposalAwait",
        hasTodo: "FlowByProposalOver"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    formConfig={[
                        {
                            field: 'proposalId',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label: '届次',
                            type: 'select',
                            field: 'session',
                            required: true,
                            qnnDisabled: true,
                            placeholder: '请选择',
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'jieCi'
                                }
                            },
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '所属类别',
                            type: 'select',
                            field: 'belongType',
                            required: true,
                            qnnDisabled: true,
                            placeholder: '请选择',
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'suoShuLeiBie'
                                }
                            },
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '所属团组',
                            type: 'select',
                            field: 'belongGroup',
                            required: true,
                            placeholder: '请选择',
                            qnnDisabled: true,
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'suoShuTuanZu'
                                }
                            },
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '提案人',
                            field: 'proposer',
                            type: 'string',
                            placeholder: '请输入',
                            required: true,
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '附议人',
                            field: 'seconderList',
                            type: 'treeSelect',
                            qnnDisabled: true,
                            treeSelectOption: {
                                help: true,
                                selectType: '2',
                                fetchConfig: {
                                    apiName: 'getSysDepartmentUserAllTree',
                                },
                                search: true,
                                searchPlaceholder: '姓名、账号、电话',
                                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '提案日期',
                            field: 'proposalDate',
                            type: 'date',
                            placeholder: '请选择',
                            required: true,
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '案名',
                            field: 'proposalName',
                            type: 'string',
                            placeholder: '请输入',
                            required: true,
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '案由',
                            field: 'proposalReason',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '整改建议',
                            field: 'rectifySuggest',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: 'files',
                            label: '附件',
                            field: 'attachmentList',
                            desc: '点击上传',
                            qnnDisabled: true,
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '公司合并意见',
                            field: 'mergeOpinion1',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion1': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion1': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion1': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '公司工会主席',
                            field: 'chairmanList',
                            type: 'treeSelect',
                            qnnDisabled: true,
                            treeSelectOption: {
                                help: true,
                                selectType: '2',
                                maxNumber: 1,
                                fetchConfig: {
                                    apiName: 'getSysDepartmentUserAllTree',
                                },
                                search: true,
                                searchPlaceholder: '姓名、账号、电话',
                                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.chairmanList': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanList': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanList': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '上报内容',
                            field: 'reportContent',
                            type: 'textarea',
                            placeholder: '请输入',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.reportContent': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.reportContent': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.reportContent': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '工会主席是否同意',
                            field: 'chairmanAgree',
                            type: 'radio',
                            qnnDisabled: true,
                            optionData: [  //可为function (props)=>array
                                {
                                    label: "不同意",
                                    value: "0"
                                },
                                {
                                    label: "同意",
                                    value: "1"
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.chairmanAgree': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanAgree': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanAgree': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '工会主席审批意见',
                            field: 'chairmanOpinion',
                            type: 'textarea',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.chairmanOpinion': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanOpinion': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.chairmanOpinion': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '集团合并意见',
                            field: 'mergeOpinion2',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion2': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion2': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mergeOpinion2': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '立案编号',
                            field: 'registerNumber',
                            type: 'string',
                            placeholder: '请输入',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.registerNumber': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.registerNumber': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.registerNumber': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '主责部门',
                            field: 'mainDeptList',
                            type: 'treeSelect',
                            qnnDisabled: true,
                            treeSelectOption: {
                                help: true,
                                selectType: '2',
                                maxNumber: 1,
                                fetchConfig: {
                                    apiName: 'getSysDepartmentUserAllTree',
                                },
                                search: true,
                                searchPlaceholder: '姓名、账号、电话',
                                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.mainDeptList': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mainDeptList': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.mainDeptList': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '协办部门',
                            field: 'assistDeptList',
                            type: 'treeSelect',
                            qnnDisabled: true,
                            treeSelectOption: {
                                help: true,
                                selectType: '1',
                                maxNumber: 1,
                                fetchConfig: {
                                    apiName: 'getSysDepartmentAllTree',
                                },
                                search: true,
                                searchPlaceholder: '姓名、账号、电话',
                                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.assistDeptList': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.assistDeptList': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.assistDeptList': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '完成时限',
                            field: 'limitTime',
                            type: 'date',
                            placeholder: '请选择',
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.limitTime': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.limitTime': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.limitTime': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '立案意见',
                            field: 'registerOpinion',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.registerOpinion': '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.registerOpinion': null,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        'apiBody.registerOpinion': undefined,
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '主要措施',
                            field: 'mainMeasures',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '主要节点和目标',
                            field: 'mainNodes',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '一季度落实显隐',
                            field: 'canDisplay1',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label: '一季度落实情况反馈',
                            field: 'feedback1',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay1': '0',
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '二季度落实显隐',
                            field: 'canDisplay2',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label: '二季度落实情况反馈',
                            field: 'feedback2',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay2': '0',
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '三季度落实显隐',
                            field: 'canDisplay3',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label: '三季度落实情况反馈',
                            field: 'feedback3',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay3': '0',
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            label: '四季度落实显隐',
                            field: 'canDisplay4',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label: '四季度落实情况反馈',
                            field: 'feedback4',
                            type: 'textarea',
                            addShow: false,
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay4': '0',
                                    },
                                    action: 'hide',
                                }
                            ]
                        },
                        {
                            type: "textarea",
                            label: "一季度审批意见",
                            field: "opinionField1",
                            placeholder: '请输入',
                            opinionField: true,
                            addShow: false,
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay1': '0',
                                    },
                                    action: 'hide',
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "二季度审批意见",
                            field: "opinionField2",
                            placeholder: '请输入',
                            opinionField: true,
                            addShow: false,
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay2': '0',
                                    },
                                    action: 'hide',
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "三季度审批意见",
                            field: "opinionField3",
                            placeholder: '请输入',
                            opinionField: true,
                            addShow: false,
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay3': '0',
                                    },
                                    action: 'hide',
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "四季度审批意见",
                            field: "opinionField4",
                            placeholder: '请输入',
                            opinionField: true,
                            addShow: false,
                            condition: [
                                {//条件
                                    regex: {
                                        'apiBody.canDisplay4': '0',
                                    },
                                    action: 'hide',
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    fieldsCURD={(fields, flowData, props) => {
                        var rowData = JSON.parse(flowData.apiData);
                        return fields.map((item) => {
                            let { field } = item;
                            if (rowData && rowData.implementStatus === '0' && !flowData.nodeVars) {
                                if (field === 'mainMeasures' || field === 'mainNodes') {
                                    item.disabled = false;
                                }
                            }
                            if(rowData && rowData.canEdit1 === '1' && !flowData.nodeVars){
                                if (field === 'feedback1') {
                                    item.disabled = false;
                                }
                            }
                            if(rowData && rowData.canEdit2 === '1' && !flowData.nodeVars){
                                if (field === 'feedback2') {
                                    item.disabled = false;
                                }
                            }
                            if(rowData && rowData.canEdit3 === '1' && !flowData.nodeVars){
                                if (field === 'feedback3') {
                                    item.disabled = false;
                                }
                            }
                            if(rowData && rowData.canEdit4 === '1' && !flowData.nodeVars){
                                if (field === 'feedback4') {
                                    item.disabled = false;
                                }
                            }
                            return item;
                        });
                    }}
                />
            </div>
        );
    }
}
export default index;
