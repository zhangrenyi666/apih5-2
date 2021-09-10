//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg } from 'antd';
import FlowFormByProposal from './form';
import QnnForm from "../modules/qnn-form";
import moment from 'moment';
import Operation from '../DepartmentalProposals/operation';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjLabourUnionProposalListByDept',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.proposalId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
};

class index extends Component {
    constructor() {
        super();
        this.state = {
            proposalId: '',
        }
    }
    render() {
        const { proposalId } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.tables = me;
                    }}
                    actionBtns={[
                        {
                            name: 'Component',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '指定负责人',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            Component: (obj) => {
                                this.tables.clearSelectedRows();
                                let delAttr = ["id"];
                                //判断是否选中数据
                                let selectedRows = obj.selectedRows;
                                if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('请选择一条数据！');
                                    return <div />
                                }
                                if (obj.selectedRows[0].apih5FlowStatus != "") {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                    return <div />
                                }
                                return <FlowFormByProposal
                                    wrappedComponentRef={(me) => {
                                        if (me) {
                                            let flowData = {};
                                            for (const key in selectedRows[0]) {
                                                if (selectedRows[0].hasOwnProperty(key)) {
                                                    if (!delAttr.includes(key)) {
                                                        const element = selectedRows[0][key];
                                                        flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                                    }
                                                }
                                            }
                                            setTimeout(function () {
                                                let vals = QnnForm.sFormatData({ ...flowData }, me.props.formConfig, 'set');
                                                me.props.form.setFieldsValue({
                                                    ...vals
                                                })
                                            }, 1)
                                        }
                                    }}
                                    {...obj} tabs={[]} />
                            }
                        }
                    ]}
                    tabs={[
                            {
                                field: "form2",
                                name: "qnnForm",
                                title: "基础信息",
                                content: {
                                    fetchConfig: {
                                        apiName: 'getZjLabourUnionProposalDetails',
                                        otherParams: {
                                            proposalId: this.state.proposalId
                                        }
                                    },
                                    formConfig: [
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
                                            disabled: true,
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
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 8 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 16 }
                                                }
                                            },
                                        },
                                        {
                                            label: '所属类别',
                                            type: 'select',
                                            field: 'belongType',
                                            required: true,
                                            disabled: true,
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
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 8 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 16 }
                                                }
                                            },
                                        },
                                        {
                                            label: '所属团组',
                                            type: 'select',
                                            field: 'belongGroup',
                                            required: true,
                                            placeholder: '请选择',
                                            disabled: true,
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
                                        },
                                        {
                                            label: '提案人',
                                            field: 'proposer',
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: '附议人',
                                            field: 'seconderList',
                                            type: 'treeSelect',
                                            disabled: true,
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
                                            }
                                        },
                                        {
                                            label: '提案日期',
                                            field: 'proposalDate',
                                            type: 'date',
                                            placeholder: '请选择',
                                            disabled: true,
                                            required: true,
                                        },
                                        {
                                            label: '案名',
                                            field: 'proposalName',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                            required: true,
                                        },
                                        {
                                            label: '案由',
                                            field: 'proposalReason',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '整改建议',
                                            field: 'rectifySuggest',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入'
                                        },
                                        {
                                            type: 'files',
                                            label: '附件',
                                            field: 'attachmentList',
                                            desc: '点击上传',
                                            disabled: true,
                                            fetchConfig: {
                                                apiName: window.configs.domain + 'upload',
                                            },
                                        },
                                        {
                                            label: '公司合并意见',
                                            field: 'mergeOpinion1',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        mergeOpinion1: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mergeOpinion1: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mergeOpinion1: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        // {
                                        //     label: '工会主席',
                                        //     field: 'chairmanList',
                                        //     type: 'treeSelect',
                                        //     disabled: true,
                                        //     treeSelectOption: {
                                        //         help: true,
                                        //         selectType: '2',
                                        //         maxNumber: 1,
                                        //         fetchConfig: {
                                        //             apiName: 'getSysDepartmentUserAllTree',
                                        //         },
                                        //         search: true,
                                        //         searchPlaceholder: '姓名、账号、电话',
                                        //         searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                        //         searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        //     },
                                        //     condition: [
                                        //         {//条件
                                        //             regex: {
                                        //                 chairmanList: '',
                                        //             },
                                        //             action: 'hide',
                                        //         },
                                        //         {//条件
                                        //             regex: {
                                        //                 chairmanList: null,
                                        //             },
                                        //             action: 'hide',
                                        //         },
                                        //         {//条件
                                        //             regex: {
                                        //                 chairmanList: undefined,
                                        //             },
                                        //             action: 'hide',
                                        //         }
                                        //     ]
                                        // },
                                        {
                                            label: '上报内容',
                                            field: 'reportContent',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        reportContent: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        reportContent: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        reportContent: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '工会主席是否同意',
                                            field: 'chairmanAgree',
                                            type: 'radio',
                                            disabled: true,
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
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        chairmanAgree: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanAgree: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanAgree: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '工会主席审批意见',
                                            field: 'chairmanOpinion',
                                            type: 'textarea',
                                            disabled: true,
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        chairmanOpinion: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanOpinion: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanOpinion: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '集团合并意见',
                                            field: 'mergeOpinion2',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        mergeOpinion2: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mergeOpinion2: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mergeOpinion2: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '退回意见',
                                            field: 'returnOpinion',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        returnOpinion: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        returnOpinion: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        returnOpinion: undefined,
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
                                            disabled: true,
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        registerNumber: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        registerNumber: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        registerNumber: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '主责部门',
                                            field: 'mainDeptList',
                                            type: 'treeSelect',
                                            disabled:true,
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
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        mainDeptList: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainDeptList: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainDeptList: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '协办部门',
                                            field: 'assistDeptList',
                                            type: 'treeSelect',
                                            disabled:true,
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
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        assistDeptList: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        assistDeptList: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        assistDeptList: undefined,
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
                                            disabled: true,
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        limitTime: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        limitTime: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        limitTime: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '立案意见',
                                            field: 'registerOpinion',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        registerOpinion: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        registerOpinion: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        registerOpinion: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '主要措施',
                                            field: 'mainMeasures',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        mainMeasures: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainMeasures: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainMeasures: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '主要节点和目标',
                                            field: 'mainNodes',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        mainNodes: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainNodes: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        mainNodes: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '一季度落实显隐',
                                            field: 'canDisplay1',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            hide: true
                                        },
                                        {
                                            label: '一季度落实情况反馈',
                                            field: 'feedback1',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        canDisplay1: '0',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback1: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback1: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback1: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '二季度落实显隐',
                                            field: 'canDisplay2',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            hide: true
                                        },
                                        {
                                            label: '二季度落实情况反馈',
                                            field: 'feedback2',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        canDisplay2: '0',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback2: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback2: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback2: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '三季度落实显隐',
                                            field: 'canDisplay3',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            hide: true
                                        },
                                        {
                                            label: '三季度落实情况反馈',
                                            field: 'feedback3',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        canDisplay3: '0',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback3: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback3: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback3: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '四季度落实显隐',
                                            field: 'canDisplay4',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            hide: true
                                        },
                                        {
                                            label: '四季度落实情况反馈',
                                            field: 'feedback4',
                                            type: 'textarea',
                                            disabled: true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        canDisplay4: '0',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback4: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback4: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        feedback4: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
                                        {
                                            label: '最终回复',
                                            field: 'replyContent',
                                            type: 'textarea',
                                            disabled:true,
                                            placeholder: '请输入',
                                            condition: [
                                                {//条件
                                                    regex: {
                                                        replyContent: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        replyContent: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        replyContent: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        }
                                    ]
                                }
                            },
                            {
                                //自定義組件
                                field: "form3",
                                name: "czjl",
                                title: "操作记录",
                                content: props => {
                                    return <Operation {...props} />;
                                }
                            }
                        ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'proposalId',
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '提案名称', //表头标题
                                dataIndex: 'proposalName', //表格里面的字段
                                key: 'proposalName',//表格的唯一key
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    this.setState({ proposalId: obj.rowData.proposalId });
                                },
                                tooltip: 15
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '类别', //表头标题
                                dataIndex: 'belongTypeName', //表格里面的字段
                                key: 'belongTypeName',//表格的唯一key
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所属公司', //表头标题
                                dataIndex: 'belongCompanyName', //表格里面的字段
                                key: 'belongCompanyName',//表格的唯一key
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '立案编号', //表头标题
                                dataIndex: 'registerNumber', //表格里面的字段
                                key: 'registerNumber',//表格的唯一key
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '当前进度', //表头标题
                                dataIndex: 'implementStatus', //表格里面的字段
                                key: 'implementStatus',//表格的唯一key
                                render: (data) => {
                                    if (data === '0') {
                                        return '初始化';
                                    } else if (data === '1') {
                                        return '一季度完成';
                                    } else if (data === '2') {
                                        return '二季度完成';
                                    } else if (data === '3') {
                                        return '三季度完成';
                                    } else if (data === '4') {
                                        return '四季度完成';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '流程状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '4') {
                                        return '未发起';
                                    } else if (data === '0') {
                                        return '已发起';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核结束';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '') {
                                        return '未发起';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '日期', //表头标题
                                dataIndex: 'proposalDate', //表格里面的字段
                                key: 'proposalDate',//表格的唯一key
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        },
                        // {
                        //     isInForm: false,
                        //     table: {
                        //         title: '操作',
                        //         dataIndex: 'action', //表格里面的字段
                        //         key: 'action',//表格的唯一key  
                        //         btns: [
                        //             {
                        //                 name: 'edit',
                        //                 render: function (rowData) {
                        //                     return '<a>修改</a>';
                        //                 },
                        //                 formBtns: [
                        //                     {
                        //                         name: 'cancel', //关闭右边抽屉
                        //                         type: 'dashed',//类型  默认 primary
                        //                         label: '取消',
                        //                     },
                        //                     {
                        //                         name: 'submit',//内置add del
                        //                         type: 'primary',//类型  默认 primary
                        //                         label: '提交',//提交数据并且关闭右边抽屉
                        //                         fetchConfig: {//ajax配置
                        //                             apiName: '',
                        //                         },
                        //                     }
                        //                 ]
                        //             }
                        //         ]
                        //     }
                        // }
                    ]}
                />
            </div>
        );
    }
}

export default index;