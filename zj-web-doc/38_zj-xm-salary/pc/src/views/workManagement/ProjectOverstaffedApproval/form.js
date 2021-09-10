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
        flowId: 'cyspApply',
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

// const fourItemLayout = {
//     labelCol: {
//         sm: { span: 8 }
//     },
//     wrapperCol: {
//         sm: { span: 16 }
//     }
// }
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            flowData: null
        }
        this.getDataFunc()
    }

    getDataFunc = async () => {
        let params = {}
        if (this.props.flowData?.zjXmSalaryOvermanApprovalId) {
            params = {
                zjXmSalaryOvermanApprovalId: this.props.flowData?.zjXmSalaryOvermanApprovalId
            }
        } else {
            params = { workId: this.props.rowData.workId }
        }
        const { data, success, message } = await this.props.myFetch('getZjXmSalaryOvermanApprovalDetail', { ...params })

        if (success) this.setState({
            flowData: data
        })
    }

    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    render() {
        const { isInQnnTable } = this.props;
        const { visibleBjdh } = this.state
        const { projectId, projectName, departmentName, departmentId } = this.apih5.getUserInfo('curCompany')
        const { realName } = this.apih5.getUserInfo()
        const orgId = this.apih5.getOrgId()
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
                                    field: 'zjXmSalaryOvermanApprovalId',
                                    type: 'string',
                                    initialValue: flowData?.zjXmSalaryOvermanApprovalId,
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
                                {
                                    type: "component",
                                    field: "component66",
                                    label: '信息查看',
                                    Component: obj => {
                                        return (
                                            <QnnForm
                                                fetch={this.props.myFetch}
                                                upload={this.props.myUpload}
                                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                                wrappedComponentRef={(me) => { this.form = me }}
                                                method={{}}
                                                componentsKey={{}}
                                                fetchConfig={{
                                                    apiName: 'getZjXmSalaryOvermanApprovalDetail',
                                                    otherParams: () => {
                                                        return {
                                                            zjXmSalaryOvermanApprovalId: flowData.zjXmSalaryOvermanApprovalId
                                                        }
                                                    }
                                                }}
                                                formConfig={[
                                                    {
                                                        field: 'zjXmSalaryOvermanApprovalId',
                                                        type: 'string',
                                                        hide: true,
                                                    },
                                                    {
                                                        label: '标题',
                                                        field: 'title',
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '请选择',
                                                        disabled: true,
                                                    },
                                                    {
                                                        label: '部门名称',
                                                        field: 'departmentName',
                                                        type: 'string',
                                                        span: 12,
                                                        required: true,
                                                        disabled: true,
                                                        initialValue: departmentName,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    },
                                                    {
                                                        label: '部门ID',
                                                        type: 'string',
                                                        field: 'departmentId',
                                                        initialValue: departmentId,
                                                        disabled: true,
                                                        hide: true
                                                    },
                                                    {
                                                        label: '报审单位',
                                                        type: 'string',
                                                        field: 'applyForUnitName',
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        initialValue: projectName,
                                                        span: 12,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                        disabled: true,
                                                        placeholder: '请输入'
                                                    },
                                                    {
                                                        label: '报审单位code',
                                                        type: 'string',
                                                        field: 'companyCode',
                                                        initialValue: projectId,
                                                        hide: true
                                                    },
                                                    {
                                                        label: 'projectId',
                                                        type: 'string',
                                                        field: 'projectId',
                                                        initialValue: projectId,
                                                        hide: true
                                                    },
                                                    {
                                                        label: 'projectName',
                                                        type: 'string',
                                                        field: 'projectName',
                                                        initialValue: projectName,
                                                        hide: true
                                                    },
                                                    {
                                                        label: '报审人',
                                                        field: 'realName',
                                                        type: 'string',
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        initialValue: realName,
                                                        span: 12,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                        disabled: true,
                                                        placeholder: '请输入'
                                                    },
                                                    {
                                                        type: 'select',
                                                        label: '工程类型',
                                                        field: 'proType',
                                                        placeholder: '请选择',
                                                        initialValue: this.state.greyContainerData ? this.state.greyContainerData.proType : null,
                                                        optionConfig: {
                                                            label: 'itemName', //默认 label
                                                            value: 'itemId',
                                                            linkageFields: {
                                                                'proTypeName': 'itemName',
                                                            }
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'gongChengLeiXing'
                                                            }
                                                        },
                                                        pageConfig: { limit: 20 },
                                                        span: 12,
                                                        required: true,
                                                        disabled: true,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                    },
                                                    {
                                                        label: '工程类型名称',
                                                        type: 'string',
                                                        field: 'proTypeName',
                                                        initialValue: this.state.greyContainerData ? this.state.greyContainerData.proTypeName : null,
                                                        hide: true
                                                    },
                                                    {
                                                        type: 'select',
                                                        label: '项目级别',
                                                        field: 'proLevel',
                                                        placeholder: '请选择',
                                                        initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevel : null,
                                                        optionConfig: {
                                                            label: 'itemName', //默认 label
                                                            value: 'itemId',
                                                            linkageFields: {
                                                                'proLevelName': 'itemName',
                                                            }
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'xiangMuJiBie'
                                                            }
                                                        },
                                                        pageConfig: { limit: 20 },
                                                        span: 12,
                                                        required: true,
                                                        disabled: true,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                    },
                                                    {
                                                        label: '项目级别名称',
                                                        type: 'string',
                                                        field: 'proLevelName',
                                                        initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevelName : null,
                                                        hide: true
                                                    },
                                                    {
                                                        label: '已超员人数',
                                                        field: 'supplementNum',
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        initialValue: this.state.greyContainerData ? this.state.greyContainerData.supplementNum : null,
                                                        span: 24,
                                                        required: true,
                                                        disabled: true,
                                                    },
                                                    {
                                                        type: 'textarea',
                                                        label: '标准岗位（8）',
                                                        field: 'standardPost',
                                                        placeholder: '请输入',
                                                        required: false,
                                                        span: 24,
                                                        // required: true,
                                                        disabled: true,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                    },
                                                    {
                                                        type: 'textarea',
                                                        label: '实际岗位（9）',
                                                        field: 'actualPost',
                                                        placeholder: '请输入',
                                                        required: false,
                                                        span: 24,
                                                        // required: true,
                                                        disabled: true,
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                    },
                                                    {
                                                        label: "岗位",
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
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                        onChange: (val, obj) => {
                                                            this.table.setDeawerValues({
                                                                postName: obj.children
                                                            })
                                                        },
                                                        disabled: true,
                                                        span: 12
                                                    },
                                                    {
                                                        field: 'postName',
                                                        hide: true
                                                    },
                                                    {
                                                        label: '人数添加',
                                                        type: 'number',
                                                        field: 'num',
                                                        placeholder: '请输入',
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 6 },
                                                                sm: { span: 6 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 18 },
                                                                sm: { span: 18 }
                                                            }
                                                        },
                                                        disabled: true,
                                                        span: 12
                                                    },
                                                    {
                                                        type: 'textarea',
                                                        label: '超员原因',
                                                        field: 'overmanReason',
                                                        placeholder: '请输入',
                                                        span: 24,
                                                        required: true,
                                                    },
                                                    {
                                                        label: "附件",
                                                        field: "fileList",
                                                        type: "files",
                                                        initialValue: [],
                                                        disabled: true,
                                                        fetchConfig: {
                                                            apiName: "upload"
                                                        }
                                                    }
                                                ]}
                                            />
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
                                    label: "公司主管部门",
                                    field: "opinionField5",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "对口事业部",
                                    field: "opinionField6",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                                {
                                    type: "textarea",
                                    label: "人力资源部经理",
                                    field: "opinionField7",
                                    opinionField: true,
                                    addShow: false,
                                    span: 12,
                                },
                            ]
                        }
                        {...this.props.workFlowConfig}
                        {...config.workFlowConfig}
                        tabs={[]}
                    /> : null
                }

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
                                            field: 'zjXmSalaryOvermanApprovalId',
                                            type: 'string',
                                            hide: true,
                                        },
                                        {
                                            label: '标题',
                                            field: 'title',
                                            type: 'string',
                                            required: true,
                                            placeholder: '请选择'
                                        },
                                        {
                                            label: '部门名称',
                                            field: 'departmentName',
                                            type: 'string',
                                            span: 12,
                                            required: true,
                                            disabled: true,
                                            initialValue: departmentName,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '部门ID',
                                            type: 'string',
                                            field: 'departmentId',
                                            initialValue: departmentId,
                                            hide: true
                                        },
                                        {
                                            label: '报审单位',
                                            type: 'string',
                                            field: 'applyForUnitName',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: projectName,
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            placeholder: '请输入'
                                        },
                                        {
                                            label: '报审单位code',
                                            type: 'string',
                                            field: 'companyCode',
                                            initialValue: projectId,
                                            hide: true
                                        },
                                        {
                                            label: 'projectId',
                                            type: 'string',
                                            field: 'projectId',
                                            initialValue: projectId,
                                            hide: true
                                        },
                                        {
                                            label: 'projectName',
                                            type: 'string',
                                            field: 'projectName',
                                            initialValue: projectName,
                                            hide: true
                                        },
                                        {
                                            label: '报审人',
                                            field: 'realName',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: realName,
                                            span: 12,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            placeholder: '请输入'
                                        },
                                        {
                                            type: 'select',
                                            label: '工程类型',
                                            field: 'proType',
                                            placeholder: '请选择',
                                            initialValue: this.state.greyContainerData ? this.state.greyContainerData.proType : null,
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId',
                                                linkageFields: {
                                                    'proTypeName': 'itemName',
                                                }
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'gongChengLeiXing'
                                                }
                                            },
                                            pageConfig: { limit: 20 },
                                            span: 12,
                                            required: true,
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        },
                                        {
                                            label: '工程类型名称',
                                            type: 'string',
                                            field: 'proTypeName',
                                            initialValue: this.state.greyContainerData ? this.state.greyContainerData.proTypeName : null,
                                            hide: true
                                        },
                                        {
                                            type: 'select',
                                            label: '项目级别',
                                            field: 'proLevel',
                                            placeholder: '请选择',
                                            initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevel : null,
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId',
                                                linkageFields: {
                                                    'proLevelName': 'itemName',
                                                }
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'xiangMuJiBie'
                                                }
                                            },
                                            pageConfig: { limit: 20 },
                                            span: 12,
                                            required: true,
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                        },
                                        {
                                            label: '项目级别名称',
                                            type: 'string',
                                            field: 'proLevelName',
                                            initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevelName : null,
                                            hide: true
                                        },
                                        {
                                            label: '已超员人数',
                                            field: 'supplementNum',
                                            type: 'number',
                                            placeholder: '请输入',
                                            initialValue: this.state.greyContainerData ? this.state.greyContainerData.supplementNum : null,
                                            span: 24,
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            type: 'textarea',
                                            label: '标准岗位（8）',
                                            field: 'standardPost',
                                            placeholder: '请输入',
                                            required: false,
                                            span: 12,
                                            // required: true,
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 10 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 10 },
                                                    sm: { span: 10 }
                                                }
                                            },
                                        },
                                        {
                                            type: 'textarea',
                                            label: '实际岗位（9）',
                                            field: 'actualPost',
                                            placeholder: '请输入',
                                            required: false,
                                            span: 12,
                                            // required: true,
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 10 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 10 },
                                                    sm: { span: 10 }
                                                }
                                            },
                                        },
                                        {
                                            label: "岗位",
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
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            onChange: (val, obj) => {
                                                this.table.setDeawerValues({
                                                    postName: obj.children
                                                })
                                            },
                                            span: 12
                                        },
                                        {
                                            field: 'postName',
                                            hide: true
                                        },
                                        {
                                            label: '人数添加',
                                            type: 'number',
                                            field: 'num',
                                            placeholder: '请输入',
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            span: 12
                                        },
                                        {
                                            type: 'textarea',
                                            label: '超员原因',
                                            field: 'overmanReason',
                                            placeholder: '请输入',
                                            span: 24,
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: "附件",
                                            field: "fileList",
                                            type: "files",
                                            initialValue: [],
                                            fetchConfig: {
                                                apiName: "upload"
                                            }
                                        }
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
