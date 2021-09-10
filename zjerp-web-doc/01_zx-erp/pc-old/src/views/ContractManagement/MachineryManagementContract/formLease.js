import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
let config = {
  //流程专属配置   
    parameterName:'orgID',
  workFlowConfig: {
    title: ['设备合同评审', 'contractName', 'contractNo'],
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
  }
};
class index extends Component {
  render() {
    const { isInQnnTable } = this.props;
    const { flowData = {} } = this.props;
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
              initialValue: 'zxCtEqContrApply',
              hide: true,
            },
            {
              field: 'zxCtEqContrApplyId',
              type: 'string',
              initialValue: flowData?.zxCtEqContrApplyId,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: flowData?.orgID,
              hide: true,
            },
            {
              field: 'orgName',
              type: 'string',
              initialValue: flowData?.orgName,
              hide: true,
            },
            {
              field: 'comID',
              type: 'string',
              initialValue: flowData?.comID,
              hide: true,
            },
            {
              field: 'comName',
              type: 'string',
              initialValue: flowData?.comName,
              hide: true,
            },
            {
              field: 'secondName',
              type: 'string',
              initialValue: flowData?.secondName,
              hide: true,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号',
              required: true,
              initialValue: flowData?.contractNo,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '合同名称',
              required: true,
              initialValue: flowData?.contractName,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'firstID',
              type: 'select',
              optionConfig: {
                label: 'orgName',
                value: 'orgID',
              },
              fetchConfig: { apiName: 'getZxCtContractListByStatus' },
              qnnDisabled: true,
              label: '甲方名称',
              initialValue: flowData?.firstID,
              span: 12,
            },
            {
              label: '乙方名称',
              initialValue: flowData?.secondID,
              field: 'secondID',
              type: 'selectByQnnTable',
              optionConfig: {
                label: 'customerName',
                value: 'zxCrCustomerNewId',
              },
              qnnDisabled: true,
              span: 12,
              qnnTableConfig: {
                antd: { rowKey: "zxCrCustomerNewId" },
                fetchConfig: { apiName: "getZxCrCustomerNewList"},
                searchBtnsStyle: "inline",
              },
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
              type: "qnnTable",
              field: 'itemList',
              qnnTableConfig: {
                fetchConfig: (obj) => {
                  if (flowData?.zxCtEqContrApplyId) {
                    return {
                      apiName: 'getZxCtEqContrItemList',
                      otherParams: {
                        zxCtEqContrApplyId: flowData?.zxCtEqContrApplyId
                      }
                    }
                  } else if (obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.zxCtEqContrApplyId) {
                    return {
                      apiName: 'getZxCtEqContrItemList',
                      otherParams: {
                        zxCtEqContrApplyId: obj.qnnFormProps.qnnformData.initialValues.apiBody.zxCtEqContrApplyId
                      }
                    }
                  }

                },
                antd: {
                  rowKey: "zxCtEqContrItemId",
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
                      title: '编号',
                      dataIndex: 'catCode',
                      key: 'catCode',
                      width: 150,
                      tooltip: 15,
                      fixed: 'left',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '0') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '设备名称',
                      dataIndex: 'catName',
                      key: 'catName',
                      width: 150,
                      tooltip: 15,
                      fixed: 'left',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '1') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '设备分类',
                      dataIndex: 'catParentName',
                      key: 'catParentName',
                      width: 150,
                      tooltip: 15,
                      fixed: 'left',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '0') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '1') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '名称',
                      dataIndex: 'catName',
                      key: 'catName',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '0') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '租赁单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '1') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '规格型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '1') {
                        return true;
                      } else {
                        return false
                      }
                    },
                    table: {
                      title: '单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '0') {
                        return true;
                      } else {
                        return false
                      }
                    },
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
                    isInTable: (obj) => {
                      let rowData = obj.qnnFormProps.form.getFieldsValue();
                      if (rowData.apiBody.contractCategory === '0') {
                        return true;
                      } else {
                        return false
                      }
                    },
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
              label: "项目各部门",
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
              label: "项目领导",
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
              label: "公司主管部门",
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
              label: "公司主管部门",
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
              label: "公司各部门",
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
              label: "公司总经理",
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
              label: "公司主管部门",
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
              label: "总部各部门",
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
              label: "总部领导",
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
              type: "textarea",
              label: "托管公司主管",
              field: "opinionField10",
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
              label: "托管公司各部门",
              field: "opinionField11",
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
              label: "托管公司领导",
              field: "opinionField12",
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
              label: "合同校对",
              field: "opinionField13",
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
              label: "局主管部门",
              field: "opinionField14",
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
              label: "局主管部门领导",
              field: "opinionField15",
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
              label: "法律顾问",
              field: "opinionField16",
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
              label: "总经理",
              field: "opinionField17",
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
          flowId={'zxCtEqContrApply'}
        />
      </div>
    );
  }
}
export default index;
