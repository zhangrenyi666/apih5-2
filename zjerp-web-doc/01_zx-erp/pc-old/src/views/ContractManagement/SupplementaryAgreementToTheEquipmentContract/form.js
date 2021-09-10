import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
let config = {
  //流程专属配置   
  parameterName:'orgID',
  workFlowConfig: {
    //后台定的字段
    title: ["设备合同补充协议", 'contractName', 'contractNo'], //标题字段
    apiNameByAdd: "updateZxCtEqContrSupply",
    apiNameByUpdate: "updateZxCtEqContrSupply",
    apiNameByGet: "getZxCtEqContrSupplyDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList",
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
    const { isInQnnTable, flowData = {} } = this.props
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <Form
          {...this.props}
          {...config}
          upload={this.props.myUpload}
          formConfig={[
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'zxCtEqContrSupply',
              hide: true,
            },
            {
              field: 'zxCtEqContrSupplyId',
              type: 'string',
              initialValue: flowData?.zxCtEqContrSupplyId,
              hide: true,
            },
            {
              field: 'contrAlterID',
              type: 'string',
              initialValue: flowData?.contrAlterID,
              hide: true,
            },
            {
              field: 'contractID',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              field: 'comID',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              field: 'firstID',
              type: 'string',
              initialValue: flowData?.firstID,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              field: 'comName',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              field: 'orgName',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              label: '补充协议编号',
              field: "contractNo",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.contractNo,
              span: 12,
            },
            {
              label: '补充协议名称',
              field: "supplyName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.supplyName,
              span: 12,
            },
            {
              field: 'contractName',
              type: 'select',
              qnnDisabled: true,
              label: '合同名称',
              initialValue: flowData?.contractName,
              span: 12,
              optionConfig: {
                label: 'contractName',
                value: 'contractName',
              },
              fetchConfig: { apiName: 'getZxCtEqContractList' }
            },
            {
              label: '合同类型',
              field: "contractType",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.contractType,
              span: 12,
            },
            {
              label: '甲方名称',
              field: "firstName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.firstName,
              span: 12,
            },
            {
              label: '乙方名称',
              field: "secondName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.secondName,
              span: 12,
            },
            {
              label: '合同签订人',
              field: "agent",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.agent,
              span: 12,
            },
            {
              label: '含税合同金额(万元)',
              field: "contractCost",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.contractCost,
              span: 12,
            },
            {
              label: '不含税合同金额(万元)',
              field: "contractCostNoTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.contractCostNoTax,
              span: 12,
            },
            {
              label: '合同税额(万元)',
              field: "contractCostTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.contractCostTax,
              span: 12,
            },
            {
              label: '本期含税增减金额(万元)',
              field: "thisAmount",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.thisAmount,
              span: 12,
            },
            {
              label: '本期不含税增减金额(万元)',
              field: "thisAmountNoTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.thisAmountNoTax,
              span: 12,
            },
            {
              label: '本期增减税额(万元)',
              field: "thisAmountTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.thisAmountTax,
              span: 12,
            },
            {
              label: '变更后含税金额(万元)',
              field: "alterContractSum",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.alterContractSum,
              span: 12,
            },
            {
              label: '变更后不含税金额(万元)',
              field: "alterContractSumNoTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.alterContractSumNoTax,
              span: 12,
            },
            {
              label: '变更后税额(万元)',
              field: "alterContractSumTax",
              type: 'number',
              qnnDisabled: true,
              initialValue: flowData?.alterContractSumTax,
              span: 12,
            },
            {
              label: '是否抵扣',
              field: 'isDeduct',
              type: 'radio',
              optionData: [
                { label: '是', value: '1' },
                { label: '否', value: '0' }
              ],
              qnnDisabled: true,
              initialValue: flowData.isDeduct ? flowData.isDeduct : '0',
              span: 12,
            },
            {
              label: '开工日期',
              field: "startDate",
              type: 'date',
              format: 'YYYY-MM-DD',
              qnnDisabled: true,
              initialValue: flowData?.startDate,
              span: 12,
            },
            {
              label: '竣工日期',
              field: "endDate",
              type: 'date',
              format: 'YYYY-MM-DD',
              qnnDisabled: true,
              initialValue: flowData?.endDate,
              span: 12,
            },
            {
              label: '合同工期',
              field: "csTimeLimit",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.csTimeLimit,
              span: 12,
            },
            {
              field: 'pp1',
              type: 'select',
              qnnDisabled: true,
              label: '批复单位',
              initialValue: flowData?.pp1,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '项目', value: '项目' },
                { label: '公司', value: '公司' },
                { label: '局', value: '局' },
              ],
            },
            {
              label: '批复日期',
              field: "replyDate",
              type: 'date',
              format: 'YYYY-MM-DD',
              qnnDisabled: true,
              initialValue: flowData?.replyDate,
              span: 12,
            },
            {
              label: '发起人',
              field: "createUserName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.createUserName,
              span: 12,
            },
            {
              label: '变更内容',
              field: "alterContent",
              type: 'textarea',
              qnnDisabled: true,
              initialValue: flowData?.alterContent,
              span: 12,
            },
            {
              label: '合同内容',
              field: "content",
              type: 'textarea',
              qnnDisabled: true,
              initialValue: flowData?.content,
              span: 12,
            },
            {
              label: '变更原因',
              field: "alterReason",
              type: 'textarea',
              qnnDisabled: true,
              initialValue: flowData?.alterReason,
              span: 12,
            },
            {
              label: '备注',
              field: "remark",
              type: 'textarea',
              qnnDisabled: true,
              initialValue: flowData?.remark,
              span: 12,
            },
            {
              type: "qnnTable",
              field: 'itemList',
              qnnTableConfig: {
                fetchConfig: (obj) => {
                  if (flowData?.zxCtEqContrSupplyId) {
                    return {
                      apiName: 'getZxCtEqContrAlterItemResoureList',
                      otherParams: {
                        zxCtEqContrSupplyId: flowData?.zxCtEqContrSupplyId,
                        contrAlterID: flowData?.contrAlterID
                      }
                    }
                  } else if (obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.zxCtEqContrSupplyId && obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.contrAlterID) {
                    return {
                      apiName: 'getZxCtEqContrAlterItemResoureList',
                      otherParams: {
                        zxCtEqContrSupplyId: obj.qnnFormProps.qnnformData.initialValues.apiBody.zxCtEqContrSupplyId,
                        contrAlterID: obj.qnnFormProps.qnnformData.initialValues.apiBody.contrAlterID,
                      }
                    }
                  }

                },
                antd: {
                  rowKey: "zxCtEqContrAlterItemResoureId",
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
                      title: '变更类型',
                      dataIndex: 'alterType',
                      key: 'alterType',
                      width: 150,
                      fixed: 'left',
                      align: 'center',
                      render: (h) => {
                        if (h === '1') {
                          return '新增'
                        } else if (h === '2') {
                          return '修改'
                        }
                      }
                    },
                  },
                  {
                    table: {
                      title: '编码',
                      dataIndex: 'catCode',
                      key: 'catCode',
                      width: 150,
                      tooltip: 15,
                      fixed: 'left',
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '设备名称',
                      dataIndex: 'catName',
                      key: 'catName',
                      width: 150,
                      tooltip: 15,
                      fixed: 'left',
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '租赁单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 150,
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
                    },
                    form: {
                      type: 'date',
                      spanForm: 12
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
                    },
                    form: {
                      type: 'date',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '数量',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '含税合同单价(元/台)',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '含税合同金额',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 150,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '不含税合同单价',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
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
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '不含税税额',
                      dataIndex: 'contractSumTax',
                      key: 'contractSumTax',
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '税率%',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
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
                        let isDeduct = flowData.isDeduct
                        if (isDeduct === '1') {
                          return '是'
                        } else {
                          return '否'
                        }
                      },
                    },
                    form: {
                      spanForm: 12,
                      type: 'radio',
                      optionData: [
                        { label: '是', value: '1' },
                        { label: '否', value: '0' }
                      ],
                      initialValue: '0',
                    }
                  },
                  {
                    table: {
                      title: '变更后租赁开始时间',
                      dataIndex: 'alterRentStartDate',
                      key: 'alterRentStartDate',
                      format: 'YYYY-MM-DD',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后租赁结束时间',
                      dataIndex: 'alterRentEndDate',
                      key: 'alterRentEndDate',
                      format: 'YYYY-MM-DD',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'alterTrrm',
                      key: 'alterTrrm',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后含税单价',
                      dataIndex: 'alterPrice',
                      key: 'alterPrice',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税单价',
                      dataIndex: 'alterPriceNoTax',
                      key: 'alterPriceNoTax',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额',
                      dataIndex: 'alterAmt',
                      key: 'alterAmt',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税金额',
                      dataIndex: 'alterAmtNoTax',
                      key: 'alterAmtNoTax',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后税额',
                      dataIndex: 'alterAmtTax',
                      key: 'alterAmtTax',
                      width: 120,
                    },
                    form: {
                      type: 'number',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remark',
                      key: 'remark',
                      width: 150,
                      tooltip: 15,
                    },
                    form: {
                      type: 'textarea',
                      formItemLayout: {
                        labelCol: {
                          xs: { span: 4 },
                          sm: { span: 4 }
                        },
                        wrapperCol: {
                          xs: { span: 18 },
                          sm: { span: 18 }
                        }
                      }
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
          flowId={'zxCtEqContrSupply'}
        />
      </div>
    );
  }
}
export default index;
