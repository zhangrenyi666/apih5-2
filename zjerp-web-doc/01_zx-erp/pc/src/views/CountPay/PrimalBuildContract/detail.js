import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Modal, Button } from 'antd';
import s from './style.less';
let config = {
    //流程专属配置   
    parameterName: 'orgID',
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        title: ['初始建造合同', 'orgName'],
        apiNameByAdd: "updateZxCtContrCsjz",
        apiNameByUpdate: "updateZxCtContrCsjz",
        apiNameByGet: "getZxCtContrCsjzDetail",
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    },
    isHaveDoc: true,
    docFieldLable: "公文正文",
    docFieldName: "textFileList",
    docFieldIsRequired: true,
    docIsReadOnly: false,
    formLayout: "leftDoc",
    formType: "descriptions",
    descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
    },
    filesFieldName: "fileList",
    getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            list: null,
            flowButtons: []
        }
    }
    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    componentWillMount() {
        const { flowData } = this.props;
        let paramsArry = {
            workId: flowData.workId,
            apiName: "getZxCtContrCsjzDetail",
            apiType: "POST",
            flowId: "zxCtContrCsjz",
            title: "初始建造合同评审-" + flowData.orgName
        };
        this.props.myFetch('openFlowByReport', paramsArry).then(({ success, message, data }) => {
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
        const { isInQnnTable = true, flowData, match: { url } } = this.props;
        const { visibleBjdh, list, flowButtons } = this.state;
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
                    initialValueByDocs={list?.textFileList}
                    initialValueByFiles={list?.fileList}
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
                            field: 'id',
                            type: 'string',
                            initialValue: list?.id ,
                            hide: true,
                        },
                        {
                            field: 'orgID',
                            type: 'string',
                            initialValue: list?.orgID,
                            hide: true,
                        },
                        {
                            field: 'confirmMethod',
                            type: 'string',
                            initialValue: list?.confirmMethod,
                            hide: true,
                        },
                        {
                            field: 'orgName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '项目名称',
                            initialValue: list?.orgName,
                            span: 12,
                        },
                        {
                            field: 'buildTime',
                            type: 'date',
                            label: '创建时间',
                            qnnDisabled: true,
                            initialValue: list?.buildTime,
                            span: 12,
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
                                    }}>合同详情</Button>
                                );
                            },
                            span: 12,
                        },
                        {
                            label: "项目经营负责人",
                            field: "opinionField2",
                            type: "textarea",
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
                            label: "项目总经济师",
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
                            label: "项目经理",
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
                            label: "总部审核人",
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
                            label: "总部经营负责人",
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
                            label: "总部经济师审核",
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
                            label: "总部经理审核",
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
                            label: "托管公司主管部门",
                            field: "opinionField9",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField9 ? (list.opinionField9).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "托管公司其他部门会签",
                            field: "opinionField10",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField10 ? (list.opinionField10).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "托管公司领导",
                            field: "opinionField11",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField11 ? (list.opinionField11).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司审核人",
                            field: "opinionField12",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField12 ? (list.opinionField12).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司经营负责人",
                            field: "opinionField13",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField13 ? (list.opinionField13).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司总经济师",
                            field: "opinionField14",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField14 ? (list.opinionField14).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                        {
                            type: "textarea",
                            label: "校对",
                            field: "opinionField15",
                            opinionField: true,
                            span: 12,
                            qnnDisabled: true,
                            initialValue: () => {
                              let data = list && list.opinionField15 ? (list.opinionField15).replace(/<br\/>/g, "\n") : '';
                              return data
                            }
                        },
                    ]}
                    flowId={'zxCtContrCsjz'}
                /> : null}
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
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZxCtContrCsjzItemList',
                            otherParams: () => {
                                if (this.flowForm.rootBody?.workId) {
                                    return {
                                        id: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').id
                                    }
                                } else {
                                    return {
                                        id: flowData.id
                                    }
                                }
                            }
                        }}
                        addIsGetData={true}
                        formConfig={[
                            {
                                label: '确认法',
                                field: "confirmMethod",
                                type: 'select',
                                span: 12,
                                optionConfig: { label: 'label', value: 'value' },
                                initialValue: () => {
                                    if (this.flowForm.rootBody?.workId) {
                                        return this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').confirmMethod
                                    } else {
                                        return flowData.confirmMethod
                                    }
                                },
                                optionData: [
                                    { label: '施工预算确认法', value: '1' },
                                    { label: '标后预算调整确认法', value: '0' }
                                ],
                                disabled: true
                            },
                            {
                                type: 'qnnTable',
                                field: 'itemBaseList',
                                hide: (obj) => {
                                    if (this.table?.form?.getFieldValue('itemBaseList')?.length > 0) {
                                        return false
                                    } else {
                                        return true
                                    }
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: false,
                                    pageSize: 999,
                                    isShowRowSelect: false,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'hangCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isReduce',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'huizongCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isHuizong',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目',
                                                dataIndex: 'subType',
                                                key: 'subType',
                                                width: 150,
                                                render: (h) => {
                                                    if (h === '0') {
                                                        return '初始预计总收入'
                                                    } else if (h === '1') {
                                                        return '初始预计总成本'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '项目子类',
                                                dataIndex: 'subType2',
                                                key: 'subType2',
                                                width: 150,
                                                render: (h) => {
                                                    if (h === '0') {
                                                        return '标后预算调整确认法'
                                                    } else if (h === '1') {
                                                        return '施工预算确认法'
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目内容',
                                                dataIndex: 'subDetail',
                                                key: 'subDetail',
                                                width: 150,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目内容明细',
                                                dataIndex: 'subDetail2',
                                                key: 'subDetail2',
                                                width: 150,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '金额',
                                                dataIndex: 'amt',
                                                key: 'amt',
                                                width: 120,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '说明',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                            },
                                        }
                                    ]
                                }
                            },
                            {
                                type: 'qnnTable',
                                field: 'itemList',
                                hide: (obj) => {
                                    if (this.table?.form?.getFieldValue('itemList')?.length > 0) {
                                        return false
                                    } else {
                                        return true
                                    }
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: false,
                                    pageSize: 999,
                                    wrappedComponentRef: (me) => {
                                        this.tableTwo = me;
                                    },
                                    isShowRowSelect: false,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'hangCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isHuizong',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isReduce',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'huizongCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目',
                                                dataIndex: 'subType',
                                                key: 'subType',
                                                width: 150,
                                                render: (h) => {
                                                    if (h === '0') {
                                                        return '初始预计总收入'
                                                    } else if (h === '1') {
                                                        return '初始预计总成本'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '项目子类',
                                                dataIndex: 'subType2',
                                                key: 'subType2',
                                                width: 150,
                                                render: (h) => {
                                                    if (h === '0') {
                                                        return '标后预算调整确认法'
                                                    } else if (h === '1') {
                                                        return '施工预算确认法'
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目内容',
                                                dataIndex: 'subDetail',
                                                key: 'subDetail',
                                                width: 150,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '项目内容明细',
                                                dataIndex: 'subDetail2',
                                                key: 'subDetail2',
                                                width: 150,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '金额',
                                                dataIndex: 'amt',
                                                key: 'amt',
                                                width: 120,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '说明',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                            }
                                        }
                                    ]
                                }
                            }
                        ]}
                    />
                </Modal>
            </div>
        );
    }
}
export default index;
