import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import s from './style.less';
import { push } from 'react-router-redux';
import QnnForm from '../modules/qnn-table/qnn-form';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topicId: props.match.params.topicId || ''
        }
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { topicId } = this.state;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"科研课题详情"}
                    </NavBar>
                </div>
                <div style={{ height: window.innerHeight - 45 }}>
                    <QnnForm
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        fetchConfig={{
                            apiName: 'getZjSjConsultScientificResearchTopicDetails',
                            otherParams: {
                                topicId: topicId
                            }
                        }}
                        formConfig={[
                            {
                                field: 'topicId',
                                hide: true,
                                type: 'string',
                                placeholder: '请输入'
                            },
                            {
                                label: '课题名称',
                                field: 'topicName',
                                type: "string",
                                disabled: true,
                                placeholder: "请输入"
                            },
                            {
                                label: '课题编号',
                                field: 'topicNo',
                                type: "string",
                                disabled: true,
                                placeholder: "请输入"
                            },
                            {
                                label: '课题类型',
                                field: 'topicTypeId',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'keTiLeiXing'
                                    }
                                },
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label: '承担单位',
                                field: 'undertakUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'chengDanDanWei'
                                    }
                                },
                                disabled: true,
                                placeholder: '请选择'
                            },
                            {
                                label: '参与单位',
                                field: 'joinUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'canYuDanWei'
                                    }
                                },
                                disabled: true,
                                placeholder: '请选择'
                            },
                            {
                                label: '立项批准单位',
                                field: 'approvalUnitNameList',
                                type: 'select',
                                mode: "tags",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'liXiangPiZhunDanWei'
                                    }
                                },
                                initialValue: [],
                                placeholder: '请选择',
                                disabled: true
                            },
                            {
                                label: '负责人',
                                field: 'responsiblePerson',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '联系方式',
                                field: 'contactMode',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '参与人员',
                                field: 'joinPersonList',
                                type: 'select',
                                disabled: true,
                                mode: "tags",
                                optionData:(props) => {
                                    return [{label:'请手动输入'}]
                                },
                                placeholder: '请输入'
                            },
                            {
                                label: '经费预算(万元)',
                                field: 'budget',
                                type: 'number',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '局拨款金额(万元)',
                                field: 'bureauAppropriationAmount',
                                type: 'number',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '研发计划时间',
                                field: 'planTime',
                                type: 'rangeDate',
                                disabled: true,
                                placeholder: '请选择'
                            },
                            {
                                type: "qnnForm",
                                field: "progressList",
                                label: "进度执行情况",
                                textObj: {
                                    add: "添加",
                                    del: "删除"
                                },
                                disabled:true,
                                canAddForm: true,
                                qnnFormConfig: {
                                    formConfig: [
                                        {
                                            label: '报表期次',
                                            type: 'select',
                                            field: 'yearId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'year'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 13,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 13 },
                                                    sm: { span: 13 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 11 },
                                                    sm: { span: 11 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'yearS',
                                            Component: obj => {
                                                return (
                                                    <div style={{ height: '32px', lineHeight: '32px', margin: '12px 0' }}>
                                                        年
                                                    </div>
                                                );
                                            },
                                            span: 1,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'select',
                                            field: 'quarterId',
                                            placeholder: '请选择',
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'quarter'
                                                }
                                            },
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            span: 7,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'component',
                                            field: 'quarterS',
                                            Component: obj => {
                                                return (
                                                    <div style={{ height: '32px', lineHeight: '32px', margin: '12px 0' }}>
                                                        季度
                                                    </div>
                                                );
                                            },
                                            span: 3,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 0 },
                                                    sm: { span: 0 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 24 }
                                                }
                                            },
                                        },
                                        {
                                            label: '本季度计划',
                                            type: 'textarea',
                                            field: 'planForTheQuarter',
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '本季度完成',
                                            type: 'textarea',
                                            field: 'completedThisQuarter',
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '未完成原因',
                                            type: 'textarea',
                                            field: 'unfinishedReason',
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '下季度计划',
                                            type: 'textarea',
                                            field: 'nextQuarterPlan',
                                            placeholder: '请输入'
                                        }
                                    ]
                                }
                            }
                        ]}
                    />
                </div>
            </div>
        );
    }
}

export default index;