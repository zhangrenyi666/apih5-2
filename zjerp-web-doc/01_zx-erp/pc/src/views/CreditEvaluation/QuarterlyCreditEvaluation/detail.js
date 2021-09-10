import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import moment from 'moment';
import s from './style.less';
let config = {
    //流程专属配置   
    parameterName: 'companyId',
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        title: ['公司半年信用评价', 'companyName', "periodStr"],
        apiNameByAdd: "updateZxCrHalfYearCreditEvaAuditStatus",
        apiNameByUpdate: "updateZxCrHalfYearCreditEvaAuditStatus",
        apiNameByGet: "getZxCrHalfYearCreditEvaDetail",
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    isHaveDoc: true,
    docFieldLable: "公文正文",
    docFieldName: "zwFileList",
    docFieldIsRequired: true,
    docIsReadOnly: false,
    docFormFormItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 20 },
            sm: { span: 20 }
        }
    },
    formLayout: "leftDoc",
    formType: "descriptions",
    descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
    },
    filesFieldName: "zxErpFileList",
    getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            list: null,
            flowButtons: []
        }
    }
    componentWillMount() {
        const { flowData } = this.props;
        let paramsArry = {
            workId: flowData.workId,
            apiName: "getZxCrHalfYearCreditEvaDetail",
            apiType: "POST",
            flowId: "ZxCrHalfYearCreditEvaAudit",
            title: "公司半年信用评价-" + flowData.companyName
        };
        this.props.myFetch('openFlowByReport', paramsArry).then(({ success, data }) => {
            if (success) {
                let list = JSON.parse(data.apiData);
                this.setState({
                    list: list,
                    flowButtons: data?.flowButtons || []
                });
            } else {
            }
        });
    }
    render() {
        const { isInQnnTable, match: { url } } = this.props;
        const { list, flowButtons } = this.state;
        this.props.match.path = url + '/HasTodoList';
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }} className={s.root}>
                {list ? <Form
                    {...this.props}
                    {...config}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    wrappedComponentRef={(me) => {
                        this.flowForm = me;
                    }}
                    todoListModuleShow={true}
                    doctModuleShow={true}
                    filesModuleShow={true}
                    flowButtons={flowButtons}
                    initialValueByDocs={list?.zwFileList}
                    initialValueByFiles={list?.zxErpFileList}
                    formConfig={[
                        {
                            type: "string",
                            label: "workId",
                            field: "workId",
                            hide: true,
                            initialValue: function (obj) {
                                return obj.match.params["workId"];
                            }
                        },
                        {
                            field: 'companyId',
                            type: 'string',
                            initialValue: list?.companyId,
                            hide: true,
                        },
                        {
                            field: 'zxCrHalfYearCreditEvaId',
                            type: 'string',
                            initialValue: list?.zxCrHalfYearCreditEvaId,
                            hide: true,
                        },

                        {
                            field: 'companyName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '机构名称',
                            initialValue: list?.companyName,
                            span: 12
                        },
                        {
                            field: 'period',
                            type: 'halfYear',
                            qnnDisabled: true,
                            label: '评价期次',
                            initialValue: list?.period,
                            span: 12
                        },
                        {
                            field: 'periodStr',
                            type: 'string',
                            hide: true,
                            initialValue: () => {
                                let periodY = list.period ? moment(list?.period).format('YYYY') : ''
                                let periodM = Number(list.period ? moment(list?.period).format('MM') : 0)
                                let periodMM = periodM > 6 ? '/下半年' : '/上半年'
                                return periodY + periodMM
                            },
                            span: 12
                        },
                        {
                            type: 'textarea',
                            label: "发起人意见",
                            field: 'opinionField1',
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "施工科意见",
                            field: "opinionField2",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "经营科意见",
                            field: "opinionField3",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField3 ? (list.opinionField3).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "技术质量科意见",
                            field: "opinionField4",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "安全科意见",
                            field: "opinionField5",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField5 ? (list.opinionField5).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "物设科意见",
                            field: "opinionField6",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField6 ? (list.opinionField6).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "财务科意见",
                            field: "opinionField7",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField7 ? (list.opinionField7).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "法律部门意见",
                            field: "opinionField8",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField8 ? (list.opinionField8).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "组长意见",
                            field: "opinionField9",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                                let data = list && list.opinionField9 ? (list.opinionField9).replace(/<br\/>/g, "\n") : '';
                                return data
                            }
                        }
                    ]}
                    flowId={'ZxCrHalfYearCreditEvaAudit'}
                /> : null}
            </div>
        );
    }
}
export default index;
