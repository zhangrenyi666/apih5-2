import React, { Component } from 'react';
import QnnForm from '../../modules/qnn-table/qnn-form';
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form } from 'antd';
import Operation from '../operation';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proposalId: props.match.params.proposalId || '',
        }
    }
    render() {
        const { proposalId } = this.state;
        let { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}ChairmanOfTheCompanyUnion`));
                    }}
                >
                    {"审批详情"}
                </NavBar>
                <div>
                    <QnnForm
                        form={this.props.form}
                        history={this.props.history}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        qnnFormContextHeight={document.documentElement.clientHeight - 90}
                        tabs={[
                            {
                                field: "form2",
                                name: "qnnForm",
                                title: "基本信息",
                                content: {
                                    fetchConfig: {
                                        apiName: 'getZjLabourUnionProposalDetails',
                                        otherParams: {
                                            proposalId: proposalId
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
                                            }
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
                                            }
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
                                    return <Operation proposalId={proposalId} {...props} />;
                                }
                            }
                        ]}
                    />
                </div>
            </div>
        )
    }
}
export default Form.create()(Index)