import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Modal, Button } from 'antd';
import QnnForm from "../../modules/qnn-form";
let config = {
  //流程专属配置   
  parameterName: 'orgId',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['附属合同', 'contractName', 'contractNo'],
    apiNameByAdd: "addZxCtFsContractReviewApplyFlow",
    apiNameByUpdate: "updateZxCtFsContractReviewApplyFlow",
    apiNameByGet: "getZxCtFsContractReviewDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "zxZhengWenFileList",
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
      visibleBjdh: false,
    }
  }
  handleCancelBjdh = () => {
    this.setState({ visibleBjdh: false });
  }
  render() {
    const { isInQnnTable, flowData = {} } = this.props;
    const { visibleBjdh } = this.state;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <div style={{ fontSize: 20, fontWeight: 700, textAlign: 'center' }} >合同会签单</div>
        <Form
          {...this.props}
          {...config}
          wrappedComponentRef={(me) => {
            this.flowForm = me;
          }}
          upload={this.props.myUpload}
          formConfig={[
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
              field: 'orgId',
              type: 'string',
              initialValue: flowData?.orgId,
              hide: true,
            },
            {
              field: 'contractReviewId',
              type: 'string',
              initialValue: flowData?.contractReviewId,
              hide: true,
            },
            {
              field: 'zxCtFsContractId',
              type: 'string',
              initialValue: flowData?.zxCtFsContractId,
              hide: true,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '附属合同编号',
              initialValue: flowData?.contractNo,
              span: 12
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '附属合同名称',
              initialValue: flowData?.contractName,
              span: 12
            },
            {
              field: 'contractType',
              type: 'string',
              qnnDisabled: true,
              label: '合同类型',
              initialValue: flowData?.contractType === '附属类' || flowData?.contractType === 'P9' ? '附属类' : '',
              span: 12
            },
            {
              field: 'firstName',
              type: 'string',
              qnnDisabled: true,
              label: '甲方名称',
              initialValue: flowData?.firstName,
              span: 12,
            },
            {
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              label: '乙方名称',
              initialValue: flowData?.secondName,
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
              field: 'initiateFlowReview',
              type: 'textarea',
              label: '主要内容',
              opinionField: true,
              initialValue: flowData?.initiateFlowReview,
              span: 12,
            },
            {
              type: "textarea",
              label: "项目各部门会签",
              field: "opinionField2",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "项目领导会签",
              field: "opinionField3",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司主管部门审核",
              field: "opinionField4",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司各部门(含法律部)会签",
              field: "opinionField6",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司主管领导审核",
              field: "opinionField5",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司总经理审批",
              field: "opinionField7",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "总部主管部门",
              field: "opinionField8",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "总部各部门会签",
              field: "opinionField9",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "总部领导会签",
              field: "opinionField10",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "托管公司主管部门审核",
              field: "opinionField11",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "托管公司各部门会签",
              field: "opinionField12",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "托管公司领导审批",
              field: "opinionField13",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "事业部审核",
              field: "opinionField15",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "事业部领导审批",
              field: "opinionField16",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "相关事业部审核",
              field: "opinionField17",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "相关事业部领导审批",
              field: "opinionField18",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "法律顾问审核",
              field: "opinionField19",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "合同校对",
              field: "opinionField14",
              opinionField: true,
              addShow: false,
              span: 12,
            },
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'ZxFsReviewWorkFlow'}
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
                  antd: {
                    rowKey: 'conRevDetailId',
                    size: 'small',
                    scroll: {
                      y: document.documentElement.clientHeight * 0.6
                    }
                  },
                  pageSize: 999,
                  paginationConfig: false,
                  isShowRowSelect: false,
                  fetchConfig: {
                    apiName: 'getZxCtFsContractReviewDetailList',
                    otherParams: () => {
                      if (this.flowForm.rootBody?.workId) {
                        return {
                          contractReviewId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').contractReviewId
                        }
                      } else {
                        return {
                          contractReviewId: flowData.contractReviewId
                        }
                      }
                    }
                  },
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'conRevDetailId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: '清单编号',
                        dataIndex: 'workNo',
                        key: 'workNo',
                        width: 150,
                        align: 'center',
                      },
                    },
                    {
                      table: {
                        title: '清单名称',
                        width: 180,
                        dataIndex: 'workName',
                        key: 'workName',
                        align: 'center',
                      },
                    },
                    {
                      table: {
                        title: '计量单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        width: 100,
                        align: 'center',
                      },
                    },
                    {
                      table: {
                        title: '合同数量',
                        dataIndex: 'qty',
                        key: 'qty',
                        width: 100,
                        align: 'center',
                      },
                    },
                    {
                      table: {
                        title: '含税合同单价(元)',
                        dataIndex: 'price',
                        key: 'price',
                        width: 150,
                        align: 'center',
                      },
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
                        dataIndex: 'remarks',
                        key: 'remarks',
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