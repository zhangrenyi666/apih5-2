import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import { Modal } from 'antd';
import s from './style.less';
import { push } from 'react-router-redux';
import QnnForm from '../modules/qnn-table/qnn-form';
const confirm = Modal.confirm;
class index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}ScientificList/0`));
                        }}
                    >
                        {"科研课题新增"}
                    </NavBar>
                </div>
                <div style={{ height: window.innerHeight - 45 }}>
                    <QnnForm
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                           this.form = me;
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
                                required: true,
                                placeholder: "请输入"
                            },
                            {
                                label: '课题编号',
                                field: 'topicNo',
                                type: "string",
                                required: true,
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
                                required: true,
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
                                onChange: (vals) => {
                                    if (vals.length) {
                                        let endVals = [vals[vals.length - 1]];
                                        this.form.form.setFieldsValue({ undertakUnitNameList: endVals });
                                    } else {
                                        this.form.form.setFieldsValue({ undertakUnitNameList: [] });
                                    }
                                },
                                required: true,
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
                                onChange: (vals) => {
                                    if (vals.length) {
                                        this.form.form.setFieldsValue({ joinUnitNameList: vals });
                                    } else {
                                        this.form.form.setFieldsValue({ joinUnitNameList: [] });
                                    }
                                },
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
                                onChange: (vals) => {
                                    if (vals.length) {
                                        let endVals = [vals[vals.length - 1]];
                                        this.form.form.setFieldsValue({ approvalUnitNameList: endVals });
                                    } else {
                                        this.form.form.setFieldsValue({ approvalUnitNameList: [] });
                                    }
                                }
                            },
                            {
                                label: '负责人',
                                field: 'responsiblePerson',
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '联系方式',
                                field: 'contactMode',
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '参与人员',
                                field: 'joinPersonList',
                                type: 'select',
                                required: true,
                                mode: "tags",
                                placeholder: '请输入'
                            },
                            {
                                label: '经费预算(万元)',
                                field: 'budget',
                                type: 'number',
                                placeholder: '请输入'
                            },
                            {
                                label: '局拨款金额(万元)',
                                field: 'bureauAppropriationAmount',
                                type: 'number',
                                placeholder: '请输入'
                            },
                            {
                                label: '研发计划时间',
                                field: 'planTime',
                                type: 'rangeDate',
                                required: true,
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
                        btns={[
                            {
                                label: '发布',
                                field: 'submit',
                                type: 'primary',
                                onClick: (obj) => {
                                    const { fetchByCb, Msg } = obj.btnCallbackFn;
                                    confirm({
                                        centered: true,
                                        title: `确认要发布这条信息吗？`,
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            fetchByCb('addZjSjConsultScientificResearchTopic', obj.values, function ({ data, success, message }) {
                                                if (success) {
                                                    Msg.success(message);
                                                    dispatch(push(`${mainModule}ScientificList/2`));
                                                } else {
                                                    Msg.error(message);
                                                }
                                            })
                                        },
                                    });
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