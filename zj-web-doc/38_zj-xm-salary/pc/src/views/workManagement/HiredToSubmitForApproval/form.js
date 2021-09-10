import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Button, } from "antd";
import PersonInfo from './personInfo'

let config = {
    //流程专属配置   
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        //后台定的字段
        title: ['titleName'], //标题字段
        apiNameByAdd: "updateWorkFlowStatus",
        apiNameByUpdate: "updateWorkFlowStatus",
        apiNameByGet: "getWorkZjXmSalaryUserExtensionHistoryDetail",
        flowId: 'rylpApply',
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    parameterName: 'orgID',
    isHaveDoc: true, // true
    docModuleShow: false,
    docFieldLable: "公文正文",
    docFieldName: "applyFileList",
    docFieldIsRequired: false,
    docIsReadOnly: false,
    docFormFormItemLayout: {
        labelCol: {
            xs: { span: 4 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 20 },
            sm: { span: 20 }
        }
    },
    //切换布局
    formLayout: "leftDoc",
    //是否使用描述式表单
    formType: "descriptions",
    descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
    },
    // //左侧公文附件字段名字  
    filesFieldName: "fileList",
    filesModuleShow: false,
    //请求左侧待办已办列表的ajax配置
    //@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
    getTodoDataFetchConfig: (curList) => ({
        apiName: curList === "todo" ? "getTodoList" : "getHasTodoList"
    }),
};
const formItemLayout = {
    labelCol: {
        xs: { span: 6 },
        sm: { span: 6 }
    },
    wrapperCol: {
        xs: { span: 18 },
        sm: { span: 18 }
    }
}

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            modalShowStatus: false,
            extensionHistoryId: '',
            flowData: null
        }
        this.getDataFunc()
    }

    getDataFunc = async () => {
        let params = {}
        if (this.props.flowData?.extensionHistoryId) {
            params = {
                extensionHistoryId: this.props.flowData?.extensionHistoryId
            }
        } else {
            params = { workId: this.props.rowData.workId }
        }
        const { data, success, message } = await this.props.myFetch('getZjXmSalaryUserExtensionHistoryDetail', { ...params })

        if (success) this.setState({
            // flowData: { ...data, jsonDetail: JSON.stringify({ ...data }) }
            flowData: {...data}
        })
    }

    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    zjXmSalaryEmployApprovalId = ''
    render() {
        const { isInQnnTable, } = this.props;
        const { flowData } = this.state
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                {
                    flowData ? <Form
                        {...this.props}
                        {...config}
                        upload={this.props.myUpload}
                        wrappedComponentRef={(me) => {
                            this.flowForm = me;
                        }}
                        openFlowDataParms={(formData, props, flowData) => {
                            this.zjXmSalaryEmployApprovalId = formData.zjXmSalaryEmployApprovalId
                            return formData
                        }}
                        formConfig={
                            [
                                {
                                    field: 'flowId',
                                    type: 'string',
                                    initialValue: 'ZxFsReviewWorkFlow',
                                    hide: true,
                                },
                                {
                                    field: 'isDeduct',
                                    type: 'string',
                                    initialValue: flowData?.isDeduct,
                                    hide: true,
                                },
                                {
                                    field: 'workId',
                                    type: 'string',
                                    initialValue: flowData?.workId,
                                    hide: true,
                                },
                                {
                                    field: 'zjXmSalaryEmployApprovalId',
                                    type: 'string',
                                    initialValue: flowData?.zjXmSalaryEmployApprovalId,
                                    hide: true,
                                },
                                {
                                    field: 'extensionHistoryId',
                                    type: 'string',
                                    initialValue: flowData?.extensionHistoryId,
                                    hide: true,
                                },
                                {
                                    label: '标题',
                                    field: 'titleName',
                                    type: 'string',
                                    span: 12,
                                    qnnDisabled: true,
                                    formItemLayout: formItemLayout,
                                    initialValue: flowData?.titleName,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '报审单位',
                                    type: 'string',
                                    field: 'applyForUnitName',
                                    span: 12,
                                    qnnDisabled: true,
                                    formItemLayout: formItemLayout,
                                    initialValue: flowData?.orgName,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '报审单位',
                                    type: 'string',
                                    span: 12,
                                    qnnDisabled: true,
                                    field: 'applyForUnitCode',
                                    initialValue: flowData?.applyForUnitCode,
                                    hide: true
                                },
                                {
                                    label: '报审人',
                                    field: 'applyForUser',
                                    type: 'string',
                                    addDisabled: true,
                                    editDisabled: true,
                                    span: 12,
                                    qnnDisabled: true,
                                    formItemLayout: formItemLayout,
                                    initialValue: flowData?.createUserName,
                                    placeholder: '请输入'
                                },
                                {
                                    label: "附件",
                                    field: "flowFileList",
                                    type: "files",
                                    initialValue: flowData?.flowFileList,
                                    span: 12,
                                    // qnnDisabled: true,
                                    formItemLayout: formItemLayout,
                                    fetchConfig: {
                                        apiName: "upload"
                                    }
                                },
                                // {
                                //     field: 'jsonDetail',
                                //     type: 'string',
                                //     initialValue: flowData?.jsonDetail,
                                //     hide: true,
                                // },
                                {
                                    type: "component",
                                    field: "component66",
                                    label: '信息查看',
                                    Component: obj => {
                                        return (
                                            <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {

                                                this.setState({
                                                    extensionHistoryId: flowData.extensionHistoryId
                                                }, () => {
                                                    this.setState({
                                                        modalShowStatus: 'detail',
                                                    })
                                                })
                                            }}>
                                                查看详情
                                            </Button>
                                        );
                                    },
                                    span: 12,
                                },
                                // {
                                //     type: "textarea",
                                //     label: "项目人事",
                                //     field: "opinionField1",
                                //     opinionField: true,
                                //     addShow: false,
                                //     span: 12,
                                // },
                                {
                                    type: "textarea",
                                    label: "项目经理",
                                    field: "opinionField1",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "人力资源部经办人",
                                    field: "opinionField2",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "人力资源部经理",
                                    field: "opinionField3",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "主管部门经理",
                                    field: "opinionField4",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "分管领导",
                                    field: "opinionField5",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                // {
                                //     type: "textarea",
                                //     label: "分管部门",
                                //     field: "opinionField7",
                                //     opinionField: true,
                                //     addShow: false,
                                //     span: 12,
                                // },
                                // {
                                //     type: "textarea",
                                //     label: "项目人事",
                                //     field: "opinionField8",
                                //     opinionField: true,
                                //     addShow: false,
                                //     span: 12,
                                // },
                            ]
                        }
                        {...this.props.workFlowConfig}
                        {...config.workFlowConfig}
                        tabs={[]}
                    /> : null
                }

                <PersonInfo
                    propsData={this.props}
                    modalShowStatus={this.state.modalShowStatus}
                    extensionHistoryId={this.state.extensionHistoryId}
                    primaryKey={this.state.primaryKey}
                    closeCb={(val, formData) => {
                        if (val === 'close') {
                            this.setState({
                                modalShowStatus: ''
                            }, async () => {
                                this.buttomTable.setTableData(this.buttomTable.getTableData().concat(formData))
                                this.buttomTable.refresh()
                            })
                        } else {
                            if (formData) {
                                this.setState({
                                    extensionHistoryId: formData.extensionHistoryId,

                                }, () => {
                                    this.setState({
                                        modalShowStatus: val
                                    })
                                })
                                this.buttomTable.setTableData(this.buttomTable.getTableData().concat(formData))
                            } else {
                                this.setState({
                                    modalShowStatus: val
                                }, () => {
                                    if (val === 'saveSuccess') {

                                        this.buttomTable.refresh()
                                    }
                                })
                            }
                        }
                    }}
                    tabsDataFunc={(data) => {

                    }}
                />
            </div>
        );
    }
}
export default index;
