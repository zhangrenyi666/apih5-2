import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Modal, Button } from "antd";
import QnnForm from "../../modules/qnn-form";
let config = {
  //流程专属配置   
  parameterName: 'firstID',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['设备租赁类合同评审', 'contractName', 'contractNo'],
    apiNameByAdd: "updateZxCtEqContrApply",
    apiNameByUpdate: "updateZxCtEqContrApply",
    apiNameByGet: "getZxCtEqContrApplyDetail",
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
              initialValue: 'zxCtEqContrApply',
              hide: true,
            },
            {
              type: "string",
              field: "isDeduct",
              initialValue: flowData?.isDeduct,
              hide: true
            },
            {
              field: 'zxCtEqContrApplyId',
              type: 'string',
              initialValue: flowData?.zxCtEqContrApplyId,
              hide: true,
            },
            {
                field: 'secondID',
                type: 'string',
                initialValue: flowData?.secondID,
                hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'firstID',
              type: 'string',
              initialValue: flowData?.firstID,
              hide: true,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号',
              initialValue: flowData?.contractNo,
              span: 12,
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '合同名称',
              initialValue: flowData?.contractName,
              span: 12,
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
              label: '乙方名称',
              initialValue: flowData?.secondName,
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              span: 12,
            },
            {
              field: 'agent',
              type: 'string',
              qnnDisabled: true,
              label: '合同签订人',
              initialValue: flowData?.agent,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'contractCost',
              type: 'number',
              qnnDisabled: true,
              label: '含税合同金额',
              initialValue: flowData?.contractCost,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'contractCostNoTax',
              type: 'number',
              qnnDisabled: true,
              label: '不含税合同金额',
              initialValue: flowData?.contractCostNoTax,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'contractCostTax',
              type: 'number',
              qnnDisabled: true,
              label: '合同税额',
              initialValue: flowData?.contractCostTax,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'isDeduct',
              type: 'radio',
              optionData: [
                { label: '是', value: '1' },
                { label: '否', value: '0' }
              ],
              qnnDisabled: true,
              label: '是否抵扣',
              initialValue: flowData.isDeduct ? flowData.isDeduct : '0',
              span: 12,
            },
            {
              label: '合同工期',
              field: 'csTimeLimit',
              qnnDisabled: true,
              initialValue: flowData?.csTimeLimit,
              type: 'string',
              span: 12,
            },
            {
              field: 'contractCategory',
              type: 'select',
              optionConfig: {
                label: 'label',
                value: 'value'
              },
              optionData: [
                { label: '设备租赁', value: '0' },
                { label: '设备采购', value: '1' },
              ],
              qnnDisabled: true,
              label: '合同类别',
              initialValue: flowData?.contractCategory,
              span: 12,
            },
            {
              field: 'pp3',
              type: 'string',
              qnnDisabled: true,
              label: '合同类型',
              initialValue: flowData?.pp3,
              span: 12,
            },
            {
              field: 'createUserName',
              type: 'string',
              qnnDisabled: true,
              label: '发起人',
              initialValue: flowData?.createUserName,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'content',
              type: 'textarea',
              qnnDisabled: true,
              label: '合同内容',
              initialValue: flowData?.content,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'remark',
              type: 'textarea',
              qnnDisabled: true,
              label: '备注',
              initialValue: flowData?.remark,
              placeholder: '请输入',
              span: 12,
            },
            {
              type: "component",
              field: "component66",
              label: '详细信息',
              Component: obj => {
                return (
                  <Button type="primary" onClick={() => {
                    this.setState({
                      visibleBjdh: true
                    })
                  }}>查看</Button>
                );
              },
              span: 12,
            },
            {
                label: "主要内容",
                field: "opinionField19",
                type: "textarea",
                opinionField: true,
                opinionFieldByCreate: true, 
                span: 12,
            },
            {
              label: "项目各部门会签",
              field: 'opinionField1',
              type: 'textarea',
              opinionField: true,
              addShow: false,
              placeholder: '请输入',
              span: 12,
            },
            {
              type: "textarea",
              label: "项目领导会签",
              field: "opinionField2",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司主管部门审核",
              field: "opinionField3",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司各部门(含法律部)会签",
              field: "opinionField5",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司主管领导审批",
              field: "opinionField4",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司总经理审批",
              field: "opinionField6" ,
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "公司主管部门审核",
              field: "opinionField7",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "总部各部门会签",
              field: "opinionField8",
              span: 12,
              opinionField: true,
              addShow: false,
            },
            {
              type: "textarea",
              label: "总部领导",
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
              label: "事业部审核",
              field: "opinionField13",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "事业部领导审批",
              field: "opinionField14",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "相关事业部审核",
              field: "opinionField15",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "法律顾问审核",
              field: "opinionField16",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "相关事业部领导审批",
              field: "opinionField17",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "合同校对",
              field: "opinionField18",
              opinionField: true,
              addShow: false,
              span: 12,
            },
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'zxCtEqContrApply'}
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
                    rowKey: 'zxCtEqContrItemId',
                    size: 'small',
                    scroll: {
                      y: document.documentElement.clientHeight * 0.6
                    }
                  },
                  drawerConfig: {
                    width: '900px'
                  },
                  pageSize: 999,
                  paginationConfig: false,
                  isShowRowSelect: false,
                  fetchConfig: {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: () => {
                      if (this.flowForm.rootBody?.workId) {
                        return {
                          zxCtEqContrApplyId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtEqContrApplyId
                        }
                      } else {
                        return {
                          zxCtEqContrApplyId: flowData.zxCtEqContrApplyId
                        }
                      }
                    },
                  },
                  formConfig: [
                    {
                      table: {
                        title: '编号',
                        width: 150,
                        fixed: 'left',
                        dataIndex: 'catCode',
                        align: 'center',
                        key: 'catCode',
                      }
                    },
                    {
                      table: {
                        title: '设备名称',
                        width: 150,
                        fixed: 'left',
                        dataIndex: 'catName',
                        align: 'center',
                        key: 'catName',
                      }
                    },
                    {
                      table: {
                        title: '型号',
                        dataIndex: 'spec',
                        key: 'spec',
                        width: 120,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '租赁单位',
                        dataIndex: 'rentUnit',
                        key: 'rentUnit',
                        width: 100,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '租赁开始时间',
                        dataIndex: 'actualStartDate',
                        key: 'actualStartDate',
                        format: 'YYYY-MM-DD',
                        width: 150,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '租赁结束时间',
                        dataIndex: 'actualEndDate',
                        format: 'YYYY-MM-DD',
                        key: 'actualEndDate',
                        width: 150,
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
                        title: '含税合同单价(元/台)',
                        dataIndex: 'price',
                        key: 'price',
                        width: 180,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '含税合同金额',
                        dataIndex: 'contractSum',
                        key: 'contractSum',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                        }
                      }
                    },
                    {
                      table: {
                        title: '不含税合同单价',
                        dataIndex: 'priceNoTax',
                        key: 'priceNoTax',
                        width: 150,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '不含税合同金额',
                        dataIndex: 'contractSumNoTax',
                        key: 'contractSumNoTax',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                        }
                      },
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
                        title: '备注',
                        dataIndex: 'remark',
                        key: 'remark',
                        width: 150,
                        align: 'center',
                      }
                    },
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
