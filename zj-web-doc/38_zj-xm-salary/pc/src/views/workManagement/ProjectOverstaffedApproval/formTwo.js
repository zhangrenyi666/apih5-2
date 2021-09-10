import React from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Modal, Button } from "antd";
import Apih5 from "qnn-apih5"
import PersonInfo from '../comp/PersonInfo'
let config = {
    //流程专属配置   
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        //后台定的字段
        title: ['titleName'], //标题字段
        apiNameByAdd: "updateZjXmSalaryOvermanApproval",
        apiNameByUpdate: "updateZjXmSalaryOvermanApproval",
        apiNameByGet: "getZjXmSalaryOvermanApprovalDetail",
        flowId: 'CopycyspApply',
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    parameterName: 'orgID',
    isHaveDoc: true,
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

class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false
        }
    }
    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    render() {
        const { isInQnnTable, flowData } = this.props;
        const { visibleBjdh } = this.state
        const orgId = this.apih5.getOrgId()
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.flowForm = me;
                    }}
                    openFlowDataParms={(formData, props, flowData)=>{
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
                                field: 'zjXmSalaryOvermanApprovalId',
                                type: 'string',
                                initialValue: flowData?.zjXmSalaryOvermanApprovalId,
                                hide: true,
                            },
                            {
                                label: '标题',
                                field: 'title',
                                type: 'string',
                                span: 12,
                                qnnDisabled: true,
                                formItemLayout: formItemLayout,
                                initialValue: flowData?.title,
                                placeholder: '请选择'
                            },
                            {
                                label: '报审单位',
                                type: 'string',
                                field: 'applyForUnitName',
                                span: 12,
                                qnnDisabled: true,
                                formItemLayout: formItemLayout,
                                initialValue: flowData?.applyForUnitName,
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
                                field: 'realName',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                span: 12,
                                qnnDisabled: true,
                                formItemLayout: formItemLayout,
                                initialValue: flowData?.realName,
                                placeholder: '请输入'
                            },
                            {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: flowData?.fileList,
                                span: 12,
                                // qnnDisabled: true,
                                formItemLayout: formItemLayout,
                                fetchConfig: {
                                    apiName: window.configs.domain + "upload"
                                }
                            },
                            {
                                type: "component",
                                field: "component66",
                                label: '信息查看',
                                Component: obj => {
                                    return (
                                        <Button type="primary" onClick={() => {
                                            this.setState({
                                                visibleBjdh: true
                                            })
                                        }}>详情查看</Button>
                                    );
                                },
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "项目超员部门",
                                field: "opinionField1",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "项目分管领导",
                                field: "opinionField2",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "项目经理",
                                field: "opinionField3",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "人力资源部经办人",
                                field: "opinionField4",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "人力资源部经理",
                                field: "opinionField5",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                            {
                                type: "textarea",
                                label: "相关分管领导",
                                field: "opinionField6",
                                opinionField: true,
                                addShow: false,
                                span: 12,
                            },
                        ]
                    }
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    tabs={[]}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="详细信息"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <QnnForm
                        fetch={this.props.myFetch}
                        formConfig={[
                            {
                                type: "qnnTable",
                                field: "zxSkLimitPriceItemList",
                                qnnTableConfig: {
                                    wrappedComponentRef: (me) => {
                                        this.buttomTable = me
                                    },
                                    antd: {
                                        rowKey: 'zjXmSalaryEmployApprovalId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZjXmSalaryOvermanSubList',
                                        otherParams: () => {
                                            if (this.flowForm.rootBody?.workId) {
                                                return {
                                                    zjXmSalaryOvermanApprovalId: this.zjXmSalaryEmployApprovalId
                                                }
                                            } else {
                                                return {
                                                    zjXmSalaryOvermanApprovalId: flowData.zjXmSalaryOvermanApprovalId
                                                }
                                            }
                                        }
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zjXmSalaryOvermanSubId',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zjXmSalaryOvermanApprovalId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: 'No.',
                                                dataIndex: 'index',
                                                key: 'index',
                                                width: 50,
                                                fixed: 'left',
                                                render: (data, rowData, index) => {
                                                    return index + 1;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '岗位',
                                                dataIndex: 'psot',
                                                key: 'psot',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                field: "psot",
                                                type: "select",
                                                placeholder: "请选择...",
                                                fetchConfig: {
                                                    apiName: "getSysProjectDeptConfSelect",
                                                    otherParams: {
                                                        projectId: orgId
                                                    }
                                                },
                                                optionConfig: {
                                                    label: 'jobTypeName',
                                                    value: 'jobType',
                                                },
                                            },
                                        },
                                        {
                                            table: {
                                                title: '人数添加',
                                                dataIndex: 'num',
                                                key: 'num',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'num',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'remarks',
                                                placeholder: '请输入',
                                            },
                                        },
                                    ]
                                }
                            }
                        ]}
                    />
                </Modal>
                <PersonInfo
                    propsData={this.props}
                    modalShowStatus={this.state.modalShowStatus}
                    projectId={this.state.projectId}
                    extensionHistoryId={this.state.extensionHistoryId}
                    primaryKey={this.state.primaryKey}
                    type={'salary'}
                    isReview={true}
                    closeCb={(val) => {
                        this.setState({
                            modalShowStatus: val
                        }, () => {
                            if (val === 'saveSuccess') {
                                this.buttomTable.refresh()
                            }
                        })

                    }}
                    tabsDataFunc={(data) => {
                        console.log(data)
                    }}
                />
            </div>
        );
    }
}
export default index;
