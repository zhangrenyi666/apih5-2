//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal, Form } from 'antd';
import QnnForm from "../modules/qnn-table/qnn-form";
import Operation from './operation';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjLabourUnionProposalListByProposer',
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
            mergeData: [],
            visible: false,
            addFlag:false,
            visibles:false
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { mergeData, visible, addFlag } = this.state;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            willExecute:(obj) => {
                                this.setState({addFlag:true,proposalId:''})
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjLabourUnionProposal',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'reported',//内置add del
                            icon: 'upload',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '上报公司',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].feedbackStatus !== '-1') {
                                        Msg.error(obj.selectedRows[i].feedbackStatusName);
                                        break;
                                    } else if (i === obj.selectedRows.length - 1) {
                                        obj.btnCallbackFn.confirm({
                                            title: "确认信息是否上报公司?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            centered: true,
                                            onOk: () => {
                                                this.props.myFetch('batchToReportCompanyZjLabourUnionProposal', obj.selectedRows).then(({ success, data, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            }
                        },
                        {
                            name: 'reply',//内置add del
                            icon: 'message',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '满意度回复',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '32') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        if (obj.selectedRows[0].replyContent) {
                                            Msg.error('该数据已回复！');
                                        } else {
                                            this.setState({
                                                mergeData: obj.selectedRows,
                                                visible: true
                                            })
                                        }
                                    }
                                } else {
                                    Msg.error('请选择一条数据回复！');
                                }
                            }
                        }
                        // {
                        //     name: 'del',//内置add del
                        //     icon: 'delete',//icon
                        //     type: 'danger',//类型  默认 primary  [primary dashed danger]
                        //     label: '删除',
                        //     fetchConfig: {//ajax配置
                        //         apiName: 'batchDeleteUpdateZjLabourUnionProposal',
                        //     },
                        // }
                    ]}
                    tabs={addFlag ? [
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基本信息",
                            content: {
                                fetchConfig: this.state.proposalId ? {
                                    apiName: 'getZjLabourUnionProposalDetails',
                                    otherParams: {
                                        proposalId: this.state.proposalId
                                    }
                                } : {},
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
                                        placeholder: '请选择',
                                        initialValue:'2',
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
                                        showSearch: true,
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
                                        initialValue: realName,
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '附议人',
                                        field: 'seconderList',
                                        type: 'treeSelect',
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
                                        disabledDate: (current) => {
                                            return current < moment(new Date().getFullYear() + "-01" + "-01" + " 00" + ":00").valueOf() || current > moment(new Date().getFullYear() + "-12" + "-31" + " 23" + ":59").valueOf();
                                        },
                                        required: true,
                                    },
                                    {
                                        label: '案名',
                                        field: 'proposalName',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                    },
                                    {
                                        label: '案由',
                                        field: 'proposalReason',
                                        type: 'textarea',
                                        maxLength:1000,
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '整改建议',
                                        field: 'rectifySuggest',
                                        type: 'textarea',
                                        maxLength:1000,
                                        placeholder: '请输入'
                                    },
                                    {
                                        type: 'files',
                                        label: '附件',
                                        field: 'attachmentList',
                                        desc: '点击上传',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                        },
                                    }
                                ]
                            }
                        }
                    ] : [
                        {
                            field: "form2",
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
                                    this.setState({ addFlag:false, proposalId: obj.rowData.proposalId });
                                },
                                tooltip: 15
                            },
                            isInForm: false
                        },
                        // {
                        //     table: {
                        //         title: '案由', //表头标题
                        //         dataIndex: 'proposalReason', //表格里面的字段
                        //         key: 'proposalReason',//表格的唯一key
                        //         tooltip: 15
                        //     },
                        //     isInForm: false
                        // },
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
                                title: '回复状态', //表头标题
                                dataIndex: 'replyContent', //表格里面的字段
                                key: 'replyContent',//表格的唯一key
                                render: (data) => {
                                    if (data) {
                                        return '已回复';
                                    } else {
                                        return '未回复';
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
                        {
                            isInForm: false,
                            table: {
                                title: '操作',
                                dataIndex: 'action', //表格里面的字段
                                key: 'action',//表格的唯一key  
                                align: "center",
                                showType: "tile",
                                btns: [
                                    {
                                        name: 'edit',
                                        render: function (obj) {
                                            if(obj.rowData.feedbackStatus === '-1'){
                                                return '<a>修改</a>';
                                            }else{
                                                return '';
                                            }
                                        },
                                        willExecute:(obj) => {
                                            this.setState({
                                                addFlag:true,
                                                proposalId:obj.rowData.proposalId
                                            })
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '提交',//提交数据并且关闭右边抽屉
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZjLabourUnionProposal',
                                                },
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                />
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="满意度回复"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modals'}
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
                            formConfig = {[
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
                                    label: "回复内容",
                                    field: "replyContent", //唯一的字段名 ***必传
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
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        mergeData[0].replyContent = obj.values.replyContent;
                                        obj.btnfns.myFetch('updateZjLabourUnionProposalReplyContent', ...mergeData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visible: false });
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