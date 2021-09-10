import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Modal, Button } from 'antd';
import QnnForm from "../../modules/qnn-form";
let config = {
    //流程专属配置   
    parameterName: 'orgID',
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        title: ['其他合同', 'contractName', 'contractNo'],
        apiNameByAdd: "updateZxCtOther",
        apiNameByUpdate: "updateZxCtOther",
        apiNameByGet: "getZxCtOtherDetail",
        todo: "TodoList",
        hasTodo: "HasTodoList"
      },
      isHaveDoc: true,
      docFieldLable: "公文正文",
      docFieldName: "zxErpMainFileList",
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
    
      //切换布局
      formLayout: "leftDoc",
      //是否使用描述式表单
      formType: "descriptions",
      descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
      },
      //左侧公文附件字段名字  
      filesFieldName: "zxErpFileList",
      //请求左侧待办已办列表的ajax配置
      //@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
      
  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            list: null,
        }
    }
    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    componentWillMount() {
        const { flowData } = this.props;
        let paramsArry = {
            workId: flowData.workId,
            apiName: "getZxCtOtherDetail",
            apiType: "POST",
            flowId: "zxCtOtherReviewApply",
            title: "其他合同-" + flowData.contractNo
        };
        this.props.myFetch('openFlow', paramsArry).then(({ success, message, data }) => {
            if (success) {
                let list = JSON.parse(data.apiData);
                this.setState({
                    list: list
                });
            } else {
            }
        });
    }
    render() {
        const { isInQnnTable, flowData, match: { url } } = this.props;
        const { visibleBjdh, list } = this.state;
        this.props.match.path = url + '/HasTodoList';
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
            <div style={{ fontSize: 20, fontWeight: 700, textAlign: 'center' }} >合同会签单</div>
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
                flowButtons={[]}
                initialValueByDocs={list?.zxZhengWenFileList}
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
                        type: "string",
                        field: "zxCtOtherManageId",
                        initialValue: list?.zxCtOtherManageId,
                        hide: true
                    },
                    {
                        field: 'isDeduct',
                        type: 'string',
                        initialValue: list?.isDeduct,
                        hide: true,
                    },
                    {
                        field: 'contractNo',
                        type: 'string',
                        qnnDisabled: true,
                        label: '合同编号',
                        initialValue: list?.contractNo,
                        span: 12
                    },
                    {
                        field: 'contractName',
                        type: 'string',
                        qnnDisabled: true,
                        label: '合同名称',
                        initialValue: list?.contractName,
                        span: 12
                    },
                    {
                        field: 'contractType',
                        type: 'string',
                        qnnDisabled: true,
                        label: '合同类型',
                        initialValue: list?.contractType,
                        span: 12
                    },
                    {
                        field: 'firstName',
                        type: 'string',
                        qnnDisabled: true,
                        label: '甲方名称',
                        initialValue: list?.firstName,
                        span: 12,
                    },
                    {
                        field: 'secondName',
                        type: 'string',
                        qnnDisabled: true,
                        label: '乙方名称',
                        initialValue: list?.secondName,
                        span: 12,
                    },
                    {
                        field: 'modelType',
                        type: 'string',
                        qnnDisabled: true,
                        label: '正文模板类型',
                        initialValue: ' ',
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
                                }}>清单查看</Button>
                            );
                        },
                        span: 12,
                    },
                    {
                        field: 'editList',
                        type: 'string',
                        qnnDisabled: true,
                        label: '清单编辑',
                        initialValue: ' ',
                        span: 12,
                    },
                    {
                        type: "textarea",
                        label: "项目总经",
                        field: "opinionField1",
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
                        label: "项目经理",
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
                        label: "公司主管部门",
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
                        label: "公司主管领导",
                        field: "opinionField4",
                        opinionField: true,
                        span: 12,
                        qnnDisabled: true,
                        initialValue: () => {
                            let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
                            return data
                        }
                    },
                ]}
                flowId={'zxCtOtherReviewApply'}
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
                    
                    formConfig={[
                        {
                            type: "qnnTable",
                            field: "zxCtOtherWorksList",
                            incToForm: true,
                            qnnTableConfig: {
                                antd: {
                                    rowKey: 'conRevDetailId',
                                    size: 'small',
                                    scroll: {
                                        y: document.documentElement.clientHeight * 0.6
                                    }
                                },
                                fetchConfig:{
                                    apiName: 'getZxCtOtherWorksList',
                                    otherParams: () => {
                                        if (this.flowForm.rootBody?.workId) {
                                            return {
                                                zxCtOtherManageId: this.flowForm.rootBody.workId
                                            }
                                        } else {
                                            return {
                                                zxCtOtherManageId: flowData?.zxCtOtherManageId
                                            }
                                        }
                                    }
                                  },
                                drawerConfig: {
                                    width: '900px'
                                },
                                pageSize: 999,
                                paginationConfig: false,
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        table: {
                                            title: '清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '清单名称',
                                            width: 180,
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计量单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 100,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同数量',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            width: 100,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同单价(元)',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '不含税合同单价(元)',
                                            dataIndex: 'priceNoTax',
                                            key: 'priceNoTax',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税率(%)',
                                            dataIndex: 'taxRate',
                                            key: 'taxRate',
                                            width: 100,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否抵扣',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            align: 'center',
                                            render: () => {
                                                let isDeduct = '0'
                                                if (this.flowForm.rootBody?.workId) {
                                                    isDeduct = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').isDeduct
                                                } else {
                                                    isDeduct = flowData.isDeduct
                                                }
                                                if (isDeduct === '1') {
                                                    return '是'
                                                } else {
                                                    return '否'
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同金额',
                                            dataIndex: 'contractSum',
                                            key: 'contractSum',
                                            width: 120,
                                            align: 'center',
                                            render: (val, rowData) => {
                                                return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                                            }
                                        }
                                    },

                                    {
                                        table: {
                                            title: '不含税合同金额',
                                            dataIndex: 'contractSumNoTax',
                                            key: 'contractSumNoTax',
                                            width: 120,
                                            align: 'center',
                                            render: (val, rowData) => {
                                                return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remark',
                                            key: 'remark',
                                            width: 150,
                                            align: 'center',
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