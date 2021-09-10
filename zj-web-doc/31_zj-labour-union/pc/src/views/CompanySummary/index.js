//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Form } from 'antd';
import s from './style.less';
import Operation from '../DepartmentalProposals/operation';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjLabourUnionProposalListByCorp',
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
    }
};
class index extends Component {
    constructor() {
        super();
        this.state = {
            mergeData: [],
            mergeFlag: false,
            proposalId: '',
            visible: false,
            visibles: false,
            editFlag: false
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    handleCancels = () => {
        this.setState({ visibles: false });
    }
    render() {
        const { mergeData, mergeFlag, proposalId, visible, visibles, editFlag } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
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
                                        if (obj.selectedRows[i].feedbackStatus !== '01') {
                                            Msg.error(obj.selectedRows[i].feedbackStatusName);
                                            break;
                                        } else if (i === obj.selectedRows.length - 1) {
                                            this.setState({
                                                mergeData: obj.selectedRows,
                                                mergeFlag: true,
                                                editFlag: false
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
                                                                        item.mergeStatus = '1';
                                                                        item.mergeOpinion1 = obj._formData.mergeOpinion1;
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
                            name: 'reply',//内置add del
                            icon: 'message',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '退回',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '01' && obj.selectedRows[0].feedbackStatus !== '13') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        this.setState({
                                            mergeData: obj.selectedRows,
                                            visibles: true
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条数据退回！');
                                }
                            }
                        },
                        {
                            name: 'reported',//内置add del
                            icon: 'upload',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '上报工会主席',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length) {
                                    for (var i = 0; i < obj.selectedRows.length; i++) {
                                        if (obj.selectedRows[i].feedbackStatus !== '01') {
                                            Msg.error(obj.selectedRows[i].feedbackStatusName);
                                            break;
                                        } else if (i === obj.selectedRows.length - 1) {
                                            this.setState({
                                                mergeData: obj.selectedRows,
                                                visible: true
                                            });
                                        }
                                    }
                                } else {
                                    Msg.error('请选择数据！');
                                }
                            }
                        },
                        {
                            name: 'reporteds',//内置add del
                            icon: 'upload',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '上报集团公司',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].feedbackStatus !== '12') {
                                        Msg.error(obj.selectedRows[i].feedbackStatusName);
                                        break;
                                    } else if (i === obj.selectedRows.length - 1) {
                                        obj.btnCallbackFn.confirm({
                                            title: "确认信息是否上报集团公司?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            centered: true,
                                            onOk: () => {
                                                this.props.myFetch('batchToReportZjLabourUnionProposal', obj.selectedRows).then(({ success, data, message }) => {
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
                            name: 'editFlag',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            isValidate: false, //是否验证表单 默认true zxOaBusinessProjectChangeToContract
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].feedbackStatus !== '01') {
                                        Msg.error(obj.selectedRows[0].feedbackStatusName);
                                    } else {
                                        this.setState({
                                            proposalId: obj.selectedRows[0].proposalId,
                                            editFlag: true,
                                            mergeFlag: false
                                        }, () => {
                                            obj.btnCallbackFn.setDrawerBtns([
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
                                            ])
                                            obj.btnCallbackFn.closeDrawer(true);
                                            if (this.table) {
                                                this.table.refresh();
                                            }
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条数据编辑！');
                                }
                            }
                        },
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
                                                ]
                                            }
                                        },
                                        {
                                            label: '合并意见',
                                            field: 'mergeOpinion1',
                                            type: 'textarea',
                                            placeholder: '请输入'
                                        }
                                    ]
                                }
                            }
                        ] : editFlag ? [
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
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '整改建议',
                                            field: 'rectifySuggest',
                                            type: 'textarea',
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
                                        }
                                    ]
                                }
                            }
                        ] : [
                                    {
                                        field: "form3",
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
                                        field: "form4",
                                        name: "czjl",
                                        title: "操作记录",
                                        content: props => {
                                            return <Operation {...props} />;
                                        }
                                    }
                                ]
                    }
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '提案名称', //表头标题
                                dataIndex: 'proposalName', //表格里面的字段
                                key: 'proposalName',//表格的唯一key
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    this.setState({ mergeFlag: false, editFlag: false, proposalId: obj.rowData.proposalId });
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
                        //                 willExecute: (obj) => {
                        //                     this.setState({ mergeFlag: false, replyFlag: false, reportedFlag: false });
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
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="上报工会主席"
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
                            formConfig={[
                                {
                                    label: '工会主席',
                                    field: 'chairmanList',
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
                                    type: "textarea",
                                    label: "上报内容",
                                    field: "reportContent", //唯一的字段名 ***必传
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
                                        let body = {
                                            zjLabourUnionProposalList: mergeData,
                                            chairmanList: obj.values.chairmanList,
                                            reportContent: obj.values.reportContent
                                        }
                                        obj.btnfns.myFetch('batchToReportChairmanZjLabourUnionProposal', body, ({ data, success, message }) => {
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
                {visibles ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="公司退回"
                        visible={visibles}
                        footer={null}
                        onCancel={this.handleCancels}
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
                                    initialValue: mergeData[0].proposalName,
                                    disabled: true
                                },
                                {
                                    label: '整改建议',
                                    field: 'rectifySuggest',
                                    type: 'textarea',
                                    placeholder: '请输入',
                                    initialValue: mergeData[0].rectifySuggest,
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
                                        this.setState({ visibles: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        mergeData[0].reportStatus = '0';
                                        mergeData[0].returnOpinion = obj.values.returnOpinion;
                                        obj.btnfns.myFetch('toReturnZjLabourUnionProposal', ...mergeData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visibles: false });
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