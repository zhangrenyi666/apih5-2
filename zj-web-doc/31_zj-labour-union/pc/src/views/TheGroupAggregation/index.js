//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Form } from 'antd';
import Operation from '../DepartmentalProposals/operation';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjLabourUnionProposalListByGroup',
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
            mergeData: [],
            mergeFlag: false,
            proposalId: '',
            visibleTH: false,
            visibleLA: false,
            visibleZBM: false
        }
    }
    handleCancelTH = () => {
        this.setState({ visibleTH: false });
    }
    handleCancelLA = () => {
        this.setState({ visibleLA: false });
    }
    handleCancelZBM = () => {
        this.setState({ visibleZBM: false });
    }
    render() {
        const { mergeData, mergeFlag, proposalId, visibleTH, visibleLA, visibleZBM } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    actionBtns={[
                        {
                            name: 'merge',//内置add del
                            icon: 'font-colors',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '合并',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length > 1) {
                                    for (var i = 0; i < obj.selectedRows.length; i++) {
                                        if (obj.selectedRows[i].feedbackStatus !== '21') {
                                            Msg.error(obj.selectedRows[i].feedbackStatusName);
                                            break;
                                        } else if (i === obj.selectedRows.length - 1) {
                                            this.setState({
                                                mergeData: obj.selectedRows,
                                                mergeFlag: true
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
                                                            if (selectedRows.length === 1) {
                                                                var data = this.state.mergeData.map((item) => {
                                                                    if (item.proposalId === selectedRows[0].proposalId) {
                                                                        item.selectFlag = '1';
                                                                        item.mergeStatus = '2';
                                                                        item.mergeOpinion2 = obj._formData.mergeOpinion2;
                                                                    }
                                                                    return item;
                                                                })
                                                                obj.btnCallbackFn.fetch('toMergeZjLabourUnionProposal', data, ({ data, success, message }) => {
                                                                    if (success) {
                                                                        Msg.success(message);
                                                                        obj.btnCallbackFn.refresh();
                                                                        obj.btnCallbackFn.closeDrawer(false);
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            } else {
                                                                Msg.error('请选择一条数据作为主数据！');
                                                            }
                                                        }
                                                    }
                                                ])
                                                obj.btnCallbackFn.closeDrawer(true);
                                                obj.btnCallbackFn.clearSelectedRows();
                                                if (this.tableHB) {
                                                    this.tableHB.refresh();
                                                }
                                            })
                                        }
                                    }
                                } else {
                                    Msg.error('请选择至少两条数据合并！');
                                }
                            }
                        },
                        {
                            name: 'putOnRecord',//内置add del
                            icon: 'highlight',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '立案',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '21') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        this.setState({
                                            mergeData: obj.selectedRows,
                                            visibleLA: true
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条数据立案！');
                                }
                            }
                        },
                        {
                            name: 'department',//内置add del
                            icon: 'import',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '转建议案',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '21') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        this.setState({
                                            mergeData: obj.selectedRows,
                                            visibleZBM: true
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条数据立案！');
                                }
                            }
                        },
                        {
                            name: 'reply',//内置add del
                            icon: 'message',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '退回',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '21') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        this.setState({
                                            mergeData: obj.selectedRows,
                                            visibleTH: true
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条数据退回！');
                                }
                            }
                        }
                    ]}
                    tabs={
                        mergeFlag ? [
                            {
                                field: "form1",
                                name: "qnnForm",
                                title: "合并数据",
                                content: {
                                    formConfig: [
                                        {
                                            type: "qnnTable",
                                            field: "qnnTableWH", //唯一的字段名 ***必传
                                            qnnTableConfig: {
                                                data: mergeData,
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
                                                            label: '工会主席',
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
                                                            hide:(obj) => {
                                                                if(!obj.clickCb.rowData.chairmanList.length){
                                                                    return true;
                                                                }
                                                            }
                                                        },
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
                                                ]
                                            }
                                        },
                                        {
                                            label: '合并意见',
                                            field: 'mergeOpinion2',
                                            type: 'textarea',
                                            placeholder: '请输入'
                                        }
                                    ]
                                }
                            }
                        ] : [
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
                                                label: '工会主席',
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
                                                hide:(obj) => {
                                                    if(!obj.clickCb.rowData.chairmanList.length){
                                                        return true;
                                                    }
                                                }
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
                                                hide:(obj) => {
                                                    if(!obj.clickCb.rowData.mainDeptList.length){
                                                        return true;
                                                    }
                                                }
                                            },
                                            {
                                                label: '协办部门',
                                                field: 'assistDeptList',
                                                type: 'treeSelect',
                                                disabled: true,
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
                                                hide:(obj) => {
                                                    if(!obj.clickCb.rowData.assistDeptList.length){
                                                        return true;
                                                    }
                                                }
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
                                                disabled: true,
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
                            ]
                    }
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
                                title: '反馈状态', //表头标题
                                dataIndex: 'feedbackStatusName', //表格里面的字段
                                key: 'feedbackStatusName',//表格的唯一key
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合并状态', //表头标题
                                dataIndex: 'mergeStatus', //表格里面的字段
                                key: 'mergeStatus',//表格的唯一key
                                render: (data) => {
                                    if (data === '0') {
                                        return '未合并';
                                    } else if (data === '1') {
                                        return '公司已合并';
                                    } else if (data === '2') {
                                        return '集团已合并';
                                    } else if (data === '3') {
                                        return '被废弃';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '立案状态', //表头标题
                                dataIndex: 'registerStatus', //表格里面的字段
                                key: 'registerStatus',//表格的唯一key
                                render: (data) => {
                                    if (data === '1') {
                                        return '已立案';
                                    } else if (data === '0') {
                                        return '已转建议案';
                                    } else {
                                        return '未处理';
                                    }
                                }
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
                {visibleTH ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="集团退回"
                        visible={visibleTH}
                        footer={null}
                        onCancel={this.handleCancelTH}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modalss'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '案名',
                                    field: 'proposalName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].proposalName,
                                    disabled: true
                                },
                                {
                                    label: '整改建议',
                                    field: 'rectifySuggest',
                                    type: 'textarea',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].rectifySuggest,
                                    disabled: true
                                },
                                {
                                    type: "textarea",
                                    label: "退回意见",
                                    field: "returnOpinion", //唯一的字段名 ***必传
                                    placeholder: "请输入",
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibleTH: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        mergeData[0].reportStatus = '2';
                                        mergeData[0].returnOpinion = obj.values.returnOpinion;
                                        obj.btnfns.myFetch('toReturnZjLabourUnionProposal', ...mergeData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visibleTH: false });
                                                this.table.clearSelectedRows();
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
                {visibleLA ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="立案"
                        visible={visibleLA}
                        footer={null}
                        onCancel={this.handleCancelLA}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modalss'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '案名',
                                    field: 'proposalName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].proposalName,
                                    disabled: true
                                },
                                {
                                    label: '整改建议',
                                    field: 'rectifySuggest',
                                    type: 'textarea',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].rectifySuggest
                                },
                                {
                                    label: '立案编号',
                                    field: 'registerNumber',
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                                {
                                    label: '主责部门',
                                    field: 'mainDeptList',
                                    type: 'treeSelect',
                                    required: true,
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
                                    }
                                },
                                {
                                    label: '协办部门',
                                    field: 'assistDeptList',
                                    type: 'treeSelect',
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
                                    }
                                },
                                {
                                    label: '完成时限',
                                    field: 'limitTime',
                                    type: 'date',
                                    placeholder: '请选择',
                                    required: true,
                                    disabledDate: (current) => {
                                        return current < moment(new Date().getFullYear() + "-01" + "-01" + " 00" + ":00").valueOf() || current > moment(new Date().getFullYear() + "-12" + "-31" + " 23" + ":59").valueOf();
                                    },
                                },
                                {
                                    label: '立案意见',
                                    field: 'registerOpinion',
                                    type: 'textarea',
                                    placeholder: '请输入'
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibleLA: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        mergeData[0].rectifySuggest = obj.values.rectifySuggest;
                                        mergeData[0].registerNumber = obj.values.registerNumber;
                                        mergeData[0].mainDeptList = obj.values.mainDeptList;
                                        mergeData[0].assistDeptList = obj.values.assistDeptList;
                                        mergeData[0].limitTime = obj.values.limitTime;
                                        mergeData[0].registerOpinion = obj.values.registerOpinion;
                                        obj.btnfns.myFetch('toRegisterZjLabourUnionProposal', ...mergeData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visibleLA: false });
                                                this.table.clearSelectedRows();
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
                {visibleZBM ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="转建议案"
                        visible={visibleZBM}
                        footer={null}
                        onCancel={this.handleCancelZBM}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modalss'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '案名',
                                    field: 'proposalName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].proposalName,
                                    disabled: true
                                },
                                {
                                    label: '整改建议',
                                    field: 'rectifySuggest',
                                    type: 'textarea',
                                    placeholder: '请输入',
                                    initialValue:mergeData[0].rectifySuggest,
                                },
                                {
                                    label: '立案编号',
                                    field: 'registerNumber',
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                                {
                                    label: '主责部门',
                                    field: 'mainDeptList',
                                    type: 'treeSelect',
                                    required: true,
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
                                    }
                                },
                                {
                                    label: '协办部门',
                                    field: 'assistDeptList',
                                    type: 'treeSelect',
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
                                    }
                                },
                                {
                                    label: '完成时限',
                                    field: 'limitTime',
                                    type: 'date',
                                    placeholder: '请选择',
                                    required: true,
                                    disabledDate: (current) => {
                                        return current < moment(new Date().getFullYear() + "-01" + "-01" + " 00" + ":00").valueOf() || current > moment(new Date().getFullYear() + "-12" + "-31" + " 23" + ":59").valueOf();
                                    },
                                },
                                {
                                    label: '立案意见',
                                    field: 'registerOpinion',
                                    type: 'textarea',
                                    placeholder: '请输入'
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibleZBM: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        mergeData[0].rectifySuggest = obj.values.rectifySuggest;
                                        mergeData[0].registerNumber = obj.values.registerNumber;
                                        mergeData[0].mainDeptList = obj.values.mainDeptList;
                                        mergeData[0].assistDeptList = obj.values.assistDeptList;
                                        mergeData[0].limitTime = obj.values.limitTime;
                                        mergeData[0].registerOpinion = obj.values.registerOpinion;
                                        obj.btnfns.myFetch('toAssignDeptZjLabourUnionProposal', ...mergeData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visibleZBM: false });
                                                this.table.clearSelectedRows();
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default Form.create()(index);