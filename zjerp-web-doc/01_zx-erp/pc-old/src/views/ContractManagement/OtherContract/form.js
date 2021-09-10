import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
let config = {
  //流程专属配置   
    parameterName:'orgID',
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
  }
};
class index extends Component {
  render() {
    const { isInQnnTable, flowData = {} } = this.props;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <Form
          {...this.props}
          {...config}
          upload={this.props.myUpload}
          formConfig={[
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'zxCtOtherReviewApply',
              hide: true,
            },
            {
              field: 'zxCtOtherId',
              type: 'string',
              initialValue: flowData?.zxCtOtherId,
              hide: true,
            },
            {
              field: 'firstId',
              type: 'string',
              initialValue: flowData?.firstId,
              hide: true,
            },
            {
              field: 'secondId',
              type: 'string',
              initialValue: flowData?.secondId,
              hide: true,
            },
            {
              field: 'contractCode',
              type: 'string',
              initialValue: flowData?.contractCode,
              hide: true,
            },
            {
              field: 'companyName',
              type: 'string',
              initialValue: flowData?.companyName,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'companyId',
              type: 'string',
              initialValue: flowData?.companyId,
              hide: true,
            },
            {
              field: 'orgName',
              type: 'string',
              initialValue: flowData?.orgName,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: flowData?.orgID,
              hide: true,
            },
            {
              field: 'secondOrgType',
              type: 'string',
              initialValue: flowData?.secondOrgType,
              hide: true,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号',
              initialValue: flowData?.contractNo,
              span: 12
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '合同名称',
              initialValue: flowData?.contractName,
              span: 12
            },
            {
              field: 'contractType',
              type: 'string',
              qnnDisabled: true,
              label: '合同类型',
              initialValue: flowData?.contractType,
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
              field: 'agent',
              type: 'string',
              qnnDisabled: true,
              label: '合同签订人',
              initialValue: flowData?.agent,
              span: 12
            },
            {
              field: 'contractCost',
              type: 'number',
              qnnDisabled: true,
              label: '含税合同金额',
              initialValue: flowData?.contractCost,
              span: 12
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
              qnnDisabled: true,
              label: '是否抵扣',
              initialValue: flowData?.isDeduct,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '否', value: '0' },
                { label: '是', value: '1' },
              ],
            },
            {
              field: 'csTimeLimit',
              type: 'string',
              qnnDisabled: true,
              label: '合同工期',
              initialValue: flowData?.csTimeLimit,
              span: 12
            },
            {
              field: 'contractCategory',
              type: 'select',
              qnnDisabled: true,
              label: '合同类别',
              initialValue: flowData?.contractCategory,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '房屋租赁', value: 'FW' },
                { label: '加工承揽', value: 'JG' },
                { label: '技术咨询或服务', value: 'JS' },
                { label: '其他合同', value: 'QT' },
                { label: '行政办公用品采购', value: 'XZ' },
                { label: '征地拆迁', value: 'ZC' },
              ],
            },
            {
              field: 'remark',
              type: 'textarea',
              qnnDisabled: true,
              label: '备注',
              initialValue: flowData?.remark,
              span: 12
            },
            {
              field: 'content',
              type: 'textarea',
              qnnDisabled: true,
              label: '合同内容',
              initialValue: flowData?.content,
              span: 12
            },
            {
              type: "qnnTable",
              field: 'itemList',
              qnnTableConfig: {
                fetchConfig: (obj) => {
                  if (flowData?.zxCtOtherId) {
                    return {
                      apiName: 'getZxCtOtherWorksList',
                      otherParams: {
                        zxCtOtherId: flowData?.zxCtOtherId,
                      }
                    }
                  } else if (obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.zxCtOtherId) {
                    return {
                      apiName: 'getZxCtOtherWorksList',
                      otherParams: {
                        zxCtOtherId: obj.qnnFormProps.qnnformData.initialValues.apiBody.zxCtOtherId,
                      }
                    }
                  }

                },
                antd: {
                  rowKey: "zxCtOtherWorksId",
                  size: 'small',
                },
                paginationConfig: {
                  position: 'bottom'
                },
                paginationConfig: false,
                isShowRowSelect: false,
                formConfig: [
                  {
                    table: {
                      title: '清单编号',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 100,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '清单名称',
                      width: 150,
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
                    },
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
                      title: '含税合同单价',
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
                      render: (h) => {
                        if (h === '1') {
                          return '是';
                        } else {
                          return '否';
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
                    }
                  },
                  {
                    table: {
                      title: '不含税合同金额',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额',
                      dataIndex: 'changeContractSum',
                      key: 'changeContractSum',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税金额',
                      dataIndex: 'changeContractSumNoTax',
                      key: 'changeContractSumNoTax',
                      width: 150,
                      align: 'center',
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
                ],
              }
            },
            {
              label: "项目经营负责",
              field: 'opinionField1',
              type: 'textarea',
              opinionField: true,
              addShow: false,
              placeholder: '请输入',
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "项目总经济师",
              field: "opinionField2",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "项目经理",
              field: "opinionField3",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "上报总部",
              field: "opinionField4",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "上报总部(托管)",
              field: "opinionField5",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司审核人",
              field: "opinionField6",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司经营负责",
              field: "opinionField7",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司总经济师",
              field: "opinionField8",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "校对",
              field: "opinionField9",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              label: '附件',
              field: 'zxErpFileList',
              type: 'files',
              qnnDisabled: true,
              initialValue: flowData?.zxErpFileList,
              fetchConfig: {
                apiName: 'upload'
              },
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'zxCtOtherReviewApply'}
        />
      </div>
    );
  }
}
export default index;
