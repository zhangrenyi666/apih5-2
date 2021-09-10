//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from 'react-router-redux';
import { message as Msg } from 'antd';
import moment from 'moment';
const config = {
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
    constructor(props) {
        super(props);
        this.state = {
            applicationId: props.match.params.applicationId === '0' ? '' : props.match.params.applicationId,
            workId: props.match.params.workId === '0' ? '' : props.match.params.workId,
            mergeFlag: false,
            apih5FlowStatus: props.match.params.apih5FlowStatus || '',
            proposalId: ''
        }
    }
    render() {
        const { applicationId, mergeFlag, proposalId, workId, apih5FlowStatus } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={applicationId ? {//表格的ajax配置
                        apiName: 'getZjLabourUnionProposalListByFlowApplication',
                        otherParams: {
                            applicationId: applicationId
                        }
                    } : workId ? {//表格的ajax配置
                        apiName: 'getZjLabourUnionProposalListByFlowApplication',
                        otherParams: {
                            workId: workId
                        }
                    } : {}}
                    actionBtns={apih5FlowStatus === '0' || apih5FlowStatus === '3' || apih5FlowStatus === '4' ? [
                        {
                            name: 'goBack',//内置add del
                            icon: 'redo',//icon
                            type: 'dashed',//类型  默认 primary  [primary dashed danger]
                            label: '返回',
                            onClick: (obj) => {
                                const { dispatch, myPublic: { appInfo: { mainModule } } } = obj.props;
                                if (applicationId) {
                                    dispatch(push(`${mainModule}GroupAcase`));
                                } else {
                                    dispatch(push(`${mainModule}FlowByApplicationAwait`));
                                }
                            }
                        },
                        {
                            name: 'merge',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '提案数据添加',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                this.setState({
                                    mergeFlag: true,
                                }, () => {
                                    obj.btnCallbackFn.setDrawerBtns([
                                        {
                                            name: "cancel",
                                            type: "dashed",
                                            label: "取消"
                                        },
                                        {
                                            name: "mergeSubmit",
                                            type: "primary",
                                            label: "提交",
                                            onClick: (obj) => {
                                                var selectedRows = this.tableHB.getSelectedRows();
                                                obj.btnCallbackFn.fetch('batchToAddZjLabourUnionProposal', { applicationId: applicationId, zjLabourUnionProposalList: selectedRows }, ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        obj.btnCallbackFn.refresh();
                                                        obj.btnCallbackFn.closeDrawer(false);
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                });
                                            }
                                        }
                                    ])
                                    obj.btnCallbackFn.closeDrawer(true);
                                    obj.btnCallbackFn.clearSelectedRows();
                                })
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchToRemoveZjLabourUnionProposal',
                            },
                        }
                    ] : [
                            {
                                name: 'goBack',//内置add del
                                icon: 'redo',//icon
                                type: 'dashed',//类型  默认 primary  [primary dashed danger]
                                label: '返回',
                                onClick: (obj) => {
                                    const { dispatch, myPublic: { appInfo: { mainModule } } } = obj.props;
                                    if (applicationId) {
                                        dispatch(push(`${mainModule}GroupAcase`));
                                    } else {
                                        dispatch(push(`${mainModule}FlowByApplicationAwait`));
                                    }
                                }
                            }
                        ]}
                    tabs={mergeFlag ? [{
                        field: "form1",
                        name: "qnnForm",
                        title: "提案数据",
                        content: {
                            formConfig: [
                                {
                                    type: "qnnTable",
                                    field: "qnnTableWH", //唯一的字段名 ***必传
                                    qnnTableConfig: {
                                        fetchConfig: {//表格的ajax配置
                                            apiName: 'getRegisteredZjLabourUnionProposalList',
                                        },
                                        antd: {
                                            rowKey: function (row) {
                                                return row.proposalId;
                                            },
                                            size: 'small'
                                        },
                                        limit: 99999,
                                        curPage: 1,
                                        paginationConfig: false,
                                        wrappedComponentRef: (me) => {
                                            this.tableHB = me;
                                        },
                                        formConfig: [
                                            {
                                                table: {
                                                    title: '提案名称', //表头标题
                                                    dataIndex: 'proposalName', //表格里面的字段
                                                    key: 'proposalName',//表格的唯一key
                                                    onClick: 'detail',
                                                    tooltip: 15
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '类别', //表头标题
                                                    dataIndex: 'belongType', //表格里面的字段
                                                    key: 'belongType',//表格的唯一key
                                                },
                                                isInForm: false
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
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '提案人',
                                                    field: 'proposer',
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    required: true,
                                                    disabled: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '提案日期',
                                                    field: 'proposalDate',
                                                    type: 'date',
                                                    placeholder: '请选择',
                                                    disabled: true,
                                                    required: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '案名',
                                                    field: 'proposalName',
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    disabled: true,
                                                    required: true,
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '案由',
                                                    field: 'proposalReason',
                                                    type: 'textarea',
                                                    disabled: true,
                                                    placeholder: '请输入'
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '整改建议',
                                                    field: 'rectifySuggest',
                                                    type: 'textarea',
                                                    disabled: true,
                                                    placeholder: '请输入'
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'files',
                                                    label: '附件',
                                                    field: 'attachmentList',
                                                    desc: '点击上传',
                                                    disabled: true,
                                                    fetchConfig: {
                                                        apiName: window.configs.domain + 'upload',
                                                    },
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '公司工会主席',
                                                    field: 'chairmanList',
                                                    type: 'treeSelect',
                                                    disabled: true,
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
                                                                chairmanList: '',
                                                            },
                                                            action: 'hide',
                                                        },
                                                        {//条件
                                                            regex: {
                                                                chairmanList: null,
                                                            },
                                                            action: 'hide',
                                                        },
                                                        {//条件
                                                            regex: {
                                                                chairmanList: undefined,
                                                            },
                                                            action: 'hide',
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主责部门',
                                                    field: 'mainDeptList',
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '协办部门',
                                                    field: 'assistDeptList',
                                                    type: 'treeSelect',
                                                    disabled: true,
                                                    treeSelectOption: {
                                                        help: true,
                                                        selectType: '1',
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
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
                                                }
                                            }
                                        ]
                                    }
                                }
                            ]
                        }
                    }] : [
                            {
                                field: "form4",
                                name: "qnnForm",
                                title: "基本信息",
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
                                        {
                                            label: '公司工会主席',
                                            field: 'chairmanList',
                                            type: 'treeSelect',
                                            disabled: true,
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
                                                        chairmanList: '',
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanList: null,
                                                    },
                                                    action: 'hide',
                                                },
                                                {//条件
                                                    regex: {
                                                        chairmanList: undefined,
                                                    },
                                                    action: 'hide',
                                                }
                                            ]
                                        },
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
                                            disabled: true,
                                            treeSelectOption: {
                                                help: true,
                                                selectType: '1',
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
                                        }
                                    ]
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
                                    this.setState({ mergeFlag: false, proposalId: obj.rowData.proposalId });
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
                        //         align: "center",
                        //         showType: "tile",
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
                        //                             apiName: 'updateZjLabourUnionProposal',
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