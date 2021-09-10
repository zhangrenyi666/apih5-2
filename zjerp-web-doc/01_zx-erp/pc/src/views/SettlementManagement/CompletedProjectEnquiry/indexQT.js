import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxCtOtherManageId',
    size: 'small'
  },
  drawerConfig: {
    width: window.screen.width * 0.75
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 16 }
    }
  }
};
class index extends Component {
  constructor(props) {
    super(props);
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
      propsData: props.propsData || {}
    }
  }
  render() {
    const { departmentId, propsData } = this.state;
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'exportZxCtContractForFinish',
            otherParams: {
              ...propsData
            }
          }}
          formConfig={[
            {
              table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 170,
                onClick: 'detail',
              }
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
              }
            },
            {
              table: {
                title: '管理机构',
                dataIndex: 'orgName',
                key: 'orgName',
                width: 150,
              }
            },
            {
              table: {
                title: '合同类型',
                dataIndex: 'contractType',
                key: 'contractType',
                width: 100
              }
            },

            {
              table: {
                title: '合同甲方',
                dataIndex: 'firstName',
                key: 'firstName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '合同乙方',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '合同录入类型',
                dataIndex: 'auditStatus',
                key: 'auditStatus',
                width: 150,
                render: (h) => {
                  if (h === 'directInput') {
                    return '直接录入'
                  } else if (h === 'auditPassed') {
                    return '评审通过'
                  }
                }
              }
            },
            {
              table: {
                title: '签订时间',
                dataIndex: 'signatureDate',
                key: 'signatureDate',
                width: 120,
                filter: true,
                format: 'YYYY-MM-DD',
                fieldsConfig: {
                  type: 'rangeDate',
                  field: 'signatureDate'
                },
              }
            },
            {
              table: {
                title: '含税合同总价(万元)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                width: 150,
              }
            },
            {
              table: {
                title: '变更后含税合同金额(万元)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 200,
              }
            },
            {
              table: {
                title: '累计结算金额',
                dataIndex: 'totalSettleAmount',
                key: 'totalSettleAmount',
                width: 150,
              }
            },
            {
              table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                },
              }
            },
            {
              table: {
                title: '合同状态',
                dataIndex: 'contractStatus',
                key: 'contractStatus',
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '执行中'
                  } else if (h === '3') {
                    return '终止'
                  }
                }
              }
            },
            {
              table: {
                title: '结算情况',
                dataIndex: 'settleType',
                key: 'settleType',
                width: 100
              }
            },
            {
              table: {
                title: '监理单位',
                dataIndex: 'consultative',
                key: 'consultative',
                width: 100
              }
            },
            {
              table: {
                title: '合同类别',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                width: 100,
                render: (h) => {
                  if (h === 'FW') {
                    return '房屋租赁'
                  } else if (h === 'JG') {
                    return '加工承揽'
                  } else if (h === 'JS') {
                    return '技术咨询或服务'
                  } else if (h === 'QT') {
                    return '其他合同'
                  } else if (h === 'XZ') {
                    return '行政办公用品采购'
                  } else if (h === 'ZC') {
                    return '征地拆迁'
                  }
                }
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remark',
                key: 'remark',
                width: 100
              }
            },
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "合同信息",
              content: {
                formConfig: [
                  {
                    field: 'zxCtOtherManageId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '签订时间',
                    field: "signatureDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '合同名称',
                    field: "contractName",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '管理机构',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '合同类型',
                    field: "contractType",
                    initialValue: '其他类',
                    type: 'string',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '合同甲方',
                    field: "firstName",
                    span: 12,
                    type: 'string',
                    placeholder: '请输入',
                  },
                  {
                    label: '乙方名称',
                    field: "secondId",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerExtAttrId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: (obj) => {
                      return {
                        apiName: 'getZxCrCustomerExtAttrList',
                        otherParams: { type: 'ots' },
                        searchKey: 'customerName'
                      }
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '项目经理',
                    field: "projectManager",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方代表',
                    field: "secondPrincipal",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '甲方代表身份证',
                    field: "firstPrincipalIdCard",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方代表身份证',
                    field: "secondPrincipalIdCard",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '甲方联系电话',
                    field: "firstUnitTel",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方(固话)',
                    field: "secondUnitTel",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方(手机)',
                    field: "secondstring",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方(传真)',
                    field: "secondFax",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方性质',
                    field: "agent",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同录入类型',
                    field: "auditStatus",
                    type: 'select',
                    span: 12,
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '直接录入',
                        value: 'directInput'
                      },
                      {
                        label: '评审通过',
                        value: 'auditPassed'
                      }
                    ],
                  },
                  {
                    label: '合同状态',
                    field: "contractStatus",
                    type: 'select',
                    span: 12,
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '执行中',
                        value: '1'
                      },
                      {
                        label: '终止',
                        value: '3'
                      }
                    ],
                  },
                  {
                    label: '含税合同总价(万元)',
                    field: "contractCost",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '不含税合同总价(万元)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '合同税额(万元)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '变更后含税合同金额(万元)',
                    field: "alterContractSum",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '变更后不含税合同金额(万元)',
                    field: "alterContractSumNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 9 },
                        sm: { span: 9 }
                      },
                      wrapperCol: {
                        xs: { span: 15 },
                        sm: { span: 15 }
                      }
                    }
                  },
                  {
                    label: '变更后合同税额(万元)',
                    field: "alterContractSumTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '是否抵扣',
                    field: "isDeduct",
                    disabled: true,
                    type: 'radio',
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    span: 12,
                  },
                  {
                    label: '结算情况',
                    field: "settleType",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '合同开始时间',
                    field: "planStartDate",
                    span: 12,
                    type: 'date',
                  },

                  {
                    label: '合同结束时间',
                    field: "planEndDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '实际开始时间',
                    field: "actualStartDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '实际结束时间',
                    field: "actualEndDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '监理单位',
                    field: "consultative",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类别',
                    field: "contractCategory",
                    type: 'select',
                    span: 12,
                    disabled: true,
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
                    label: '合同内容',
                    field: 'content',
                    type: 'textarea',
                    required: true,
                    placeholder: '请输入',
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 20 }
                      }
                    }
                  },
                  {
                    label: '备注',
                    field: 'remark',
                    type: 'textarea',
                    placeholder: '请输入',
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 20 }
                      }
                    }
                  },
                  {
                    label: '上传附件',
                    field: 'zxErpFileList',
                    type: 'files',
                    required: true,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 24 }
                      }
                    }
                  }
                ]
              },

            },
            {
              field: "table1",
              name: "qnnTable",
              title: "合同清单明细",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherWorksList',
                    otherParams: {
                      zxCtOtherManageId: this.table.qnnForm.form.getFieldValue('zxCtOtherManageId')
                    }
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "conRevDetailId",
                },
                paginationConfig: { position: 'bottom' },
                isShowRowSelect: false,
                formConfig: [
                  {
                    table: {
                      title: '清单编号',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 180,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '清单名称',
                      width: 170,
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
                      title: '不含税合同单价',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '税率%',
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
                          return '是'
                        } else {
                          return '否'
                        }
                      },
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
                      title: '变更后含税单价',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税单价',
                      dataIndex: 'changePriceNoTax',
                      key: 'changePriceNoTax',
                      width: 150,
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
                ]
              }
            },
            {
              field: "table3",
              name: "qnnTable",
              title: "补充协议",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherSupplyAgreementList',
                    otherParams: {
                      zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId'),
                      apih5FlowStatus: '2'
                    },
                  }
                },
                drawerConfig: {
                  width: window.screen.width * 0.65
                },
                wrappedComponentRef: (me) => {
                  this.TableTwo = me
                },
                antd: {
                  rowKey: 'zxCtOtherSupplyAgreementId',
                  size: 'small'
                },
                paginationConfig: {
                  position: "bottom"
                },
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'edit',//内置add del
                    icon: 'edit',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '修改',
                    formBtns: [
                      {
                        name: 'cancel', //关闭右边抽屉
                        type: 'dashed',//类型  默认 primary
                        label: '取消',
                        hide: (obj) => {
                          var index = obj.btnCallbackFn.getTabsIndex();
                          if (index === "0") {
                            return false;
                          } else {
                            return true;
                          }
                        },
                      },
                      {
                        name: 'diySubmit',
                        field: 'diySubmit',
                        type: 'primary',
                        label: '保存',
                        hide: (obj) => {
                          var index = obj.btnCallbackFn.getTabsIndex();
                          if (index === "0") {
                            return false;
                          } else {
                            return true;
                          }
                        },
                        onClick: (obj) => {
                          obj._formData.zxCtOtherSupplyAgreementId = obj.selectedRows[0].zxCtOtherSupplyAgreementId
                          obj.btnCallbackFn.clearSelectedRows();
                          obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                          this.props.myFetch('updateZxCtOtherSupplyAgreement', obj._formData).then(
                            ({ data, success, message }) => {
                              if (success) {
                                obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                Msg.success(message);
                                obj.btnCallbackFn.refresh();
                                obj.btnCallbackFn.setActiveKey('1');
                              } else {
                                obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      }
                    ]
                  }
                ],
                formConfig: [
                  {
                    table: {
                      title: '补充协议编号',
                      dataIndex: 'contractNo',
                      key: 'contractNo',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 180,
                      onClick: 'detail',
                    }
                  },
                  {
                    table: {
                      title: '补充协议名称',
                      dataIndex: 'proposer',
                      key: 'proposer',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '合同名称',
                      dataIndex: 'contractName',
                      key: 'contractName',
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '合同类型',
                      dataIndex: 'contractType',
                      key: 'contractType',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '甲方名称',
                      dataIndex: 'firstName',
                      key: 'firstName',
                      width: 180,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '乙方名称',
                      dataIndex: 'secondName',
                      key: 'secondName',
                      width: 180,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '合同签订人',
                      dataIndex: 'agent',
                      key: 'agent',
                      width: 100,
                    }
                  },
                  {
                    table: {
                      title: '含税合同金额(万元)',
                      dataIndex: 'contractCost',
                      key: 'contractCost',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '本期含税增减金额(万元)',
                      dataIndex: 'applyAmount',
                      key: 'applyAmount',
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额(万元)',
                      dataIndex: 'alterContractSum',
                      key: 'alterContractSum',
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '是否抵扣',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      render: (h) => {
                        if (h === '1') {
                          return '是'
                        } else {
                          return '否'
                        }
                      },
                    }
                  },
                  {
                    table: {
                      title: '开工日期',
                      dataIndex: 'startDate',
                      key: 'startDate',
                      width: 100,
                      format: 'YYYY-MM-DD',
                    }
                  },
                  {
                    table: {
                      title: '竣工日期',
                      dataIndex: 'endDate',
                      key: 'endDate',
                      width: 100,
                      format: 'YYYY-MM-DD',
                    }
                  },
                  {
                    table: {
                      title: '发起人',
                      dataIndex: 'beginPer',
                      key: 'beginPer',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '评审状态',
                      dataIndex: 'apih5FlowStatus',
                      key: 'apih5FlowStatus',
                      width: 100,
                      fixed: 'right',
                      render: (data) => {
                        if (data === '0') {
                          return '待提交';
                        } else if (data === '1') {
                          return '审核中';
                        } else if (data === '2') {
                          return '审核完成';
                        } else if (data === '3') {
                          return '退回';
                        } else if (data === '-1') {
                          return '未审核';
                        }
                      }
                    }
                  },
                ],
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "补充协议基础信息",
                    content: {
                      formConfig: [
                        {
                          field: 'zxCtOtherSupplyAgreementId',
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '补充协议编号',
                          field: "contractNo",
                          type: 'string',
                          disabled: true,
                          span: 12
                        },
                        {
                          label: '补充协议名称',
                          field: "proposer",
                          type: 'string',
                          placeholder: '请输入',
                          span: 12,
                          required: true,
                        },
                        {
                          label: '合同名称',
                          field: "contractName",
                          disabled: true,
                          span: 12,
                          type: 'string',
                        },
                        {
                          label: '合同类型',
                          field: "contractType",
                          type: 'string',
                          span: 12,
                          disabled: true,
                          initialValue: '机械管理类补充协议'
                        },
                        {
                          label: '甲方名称',
                          field: "firstName",
                          type: 'string',
                          span: 12,
                          disabled: true
                        },
                        {
                          label: '乙方名称',
                          field: "secondName",
                          type: 'string',
                          disabled: true,
                          span: 12
                        },
                        {
                          label: '合同签订人',
                          field: "agent",
                          type: 'string',
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '含税合同金额(万元)',
                          field: "contractCost",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '不含税合同金额(万元)',
                          field: "contractCostNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '合同税额(万元)',
                          field: "contractCostTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期含税增减金额(万元)',
                          field: "applyAmount",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期不含税增减金额(万元)',
                          field: "applyAmountNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '本期增减税额(万元)',
                          field: "applyAmountTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后含税金额(万元)',
                          field: "alterContractSum",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后不含税金额(万元)',
                          field: "alterContractSumNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后税额(万元)',
                          field: "alterContractSumTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '是否抵扣',
                          field: "isDeduct",
                          disabled: true,
                          span: 12,
                          type: 'radio',
                          initialValue: '0',
                          optionData: [
                            { label: '是', value: '1' },
                            { label: '否', value: '0' }
                          ],
                        },
                        {
                          label: '开工日期',
                          field: 'startDate',
                          type: 'date',
                          placeholder: '请选择',
                          span: 12,
                        },
                        {
                          label: '竣工日期',
                          field: 'endDate',
                          type: 'date',
                          placeholder: '请选择',
                          span: 12,
                        },
                        {
                          label: '发起人',
                          field: "beginPer",
                          type: 'string',
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '批复单位',
                          field: 'replyUnit',
                          type: 'string',
                          span: 12,
                          disabled: true,
                        },
                        {
                          label: '批复日期',
                          field: "replyDate",
                          span: 12,
                          type: 'date',
                          disabled: true,
                          format: 'YYYY-MM-DD',
                        },
                        {
                          label: '合同内容',
                          field: 'content',
                          type: 'textarea',
                          span: 12
                        },
                        {
                          label: '变更内容',
                          field: 'alterContent',
                          type: 'textarea',
                          span: 12,
                          disabled: true,
                        },
                        {
                          label: '变更原因',
                          field: 'alterReason',
                          span: 12,
                          type: 'textarea',
                          disabled: true,
                        },
                        {
                          label: '备注',
                          span: 12,
                          field: 'remark',
                          type: 'textarea',
                          placeholder: '请输入',
                        },
                        {
                          label: '附件',
                          field: 'zxErpFileList',
                          type: 'files',
                          fetchConfig: {
                            apiName: 'upload'
                          },
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 4 },
                              sm: { span: 4 }
                            },
                            wrapperCol: {
                              xs: { span: 24 },
                              sm: { span: 24 }
                            }
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "table1",
                    name: "qnnTable",
                    title: "补充协议清单",
                    actionBtnsPosition: "top",
                    disabled: function (obj) {
                      return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : true)
                    },
                    content: {
                      fetchConfig: (obj) => {
                        console.log(obj, this.TableTwo);
                        const zxCtOtherSupplyAgreementId = this.TableTwo.form.getFieldValue('zxCtOtherSupplyAgreementId')
                        return {
                          apiName: 'getZxCtOtherSupplyAgreementWorksList',
                          otherParams: { zxCtOtherSupplyAgreementId }
                        }
                      },
                      antd: {
                        size: "small",
                        rowKey: "zxCtOtherSupplyAgreementWorksId",
                      },
                      formConfig: [
                        {
                          table: {
                            title: '变更类型',
                            dataIndex: 'alterType',
                            key: 'alterType',
                            width: 120,
                            align: 'center',
                            fixed: 'left',
                            type: 'select',
                          },
                          form: {
                            type: 'select',
                            optionConfig: {
                              label: 'label',
                              value: 'value',
                            },
                            optionData: [
                              { label: '新增', value: '1', },
                              { label: '修改', value: '2', }
                            ]
                          }
                        },
                        {
                          table: {
                            title: '清单编号',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 100,
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '清单名称',
                            width: 150,
                            dataIndex: 'workName',
                            key: 'workName',
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '数量',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '含税单价',
                            dataIndex: 'price',
                            key: 'price',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '不含税单价',
                            dataIndex: 'priceNoTax',
                            key: 'priceNoTax',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '含税金额',
                            dataIndex: 'contractSum',
                            key: 'contractSum',
                            width: 120,
                            align: 'center',
                          }
                        },

                        {
                          table: {
                            title: '不含税金额',
                            dataIndex: 'contractSumNoTax',
                            key: 'contractSumNoTax',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '不含税税额',
                            dataIndex: 'contractSumTax',
                            key: 'contractSumTax',
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
                            title: '变更后单价',
                            dataIndex: 'changePrice',
                            key: 'changePrice',
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
                            title: '变更后税额',
                            dataIndex: 'changeContractSumTax',
                            key: 'changeContractSumTax',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '税率',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            align: 'center',
                            type: 'select'
                          },
                          form: {
                            type: "select",
                            optionConfig: {
                              label: 'itemName',
                              value: 'itemId',
                            },
                            fetchConfig: {
                              apiName: 'getBaseCodeSelect',
                              otherParams: { itemId: 'shuiLv' }
                            },
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
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table4",
              name: "qnnTable",
              title: "保证金比例设置",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtOtherManageId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherManageAmtRateList',
                    otherParams: { zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtOtherManageAmtRateId",
                },
                // rowDisabledCondition: (rowData) => {
                //   return rowData.allowDelete === 'N'
                // },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    addRowDefaultData: { addFlag: '1', useCount: 0 }
                  },
                  {
                    name: 'delRow',
                    icon: 'delete',
                    type: 'danger',
                    label: '删除',
                    disabled: (obj) => {
                      let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                      let disabledLength = 0
                      for (var i = 0; i < SelectedRowsData.length; i++) {
                        if (SelectedRowsData[i].useCount !== 0 || SelectedRowsData[i].allowDelete === 'N') {
                          disabledLength++
                        }
                      }
                      if (disabledLength > 0 || SelectedRowsData.length === 0) return true
                    }
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtOtherManageAmtRateId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: '保证金',
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 150,
                      tdEdit: (obj)=>{
                        if(obj.allowDelete === 'N') return false
                        return true
                      },
                      initialValue: 0
                    },
                    form: {
                      required: true,
                      field: 'statisticsName',
                      type: 'string',
                      placeholder: '请输入'
                    }
                  },
                  {
                    table: {
                      title: '扣除比例%',
                      dataIndex: 'statisticsRate',
                      key: 'statisticsRate',
                      tdEdit: true,
                      width: 100,
                      initialValue: 0
                    },
                    form: {
                      field: 'statisticsRate',
                      type: 'number',
                      placeholder: '请输入'
                    }
                  },
                  {
                    table: {
                      title: '期限',
                      dataIndex: 'timeLimit',
                      key: 'timeLimit',
                      width: 150,
                      tdEdit: true
                    },
                    form: {
                      field: 'timeLimit',
                      type: 'string',
                      placeholder: '请输入'
                    }
                  },
                  {
                    table: {
                      title: '返还条件',
                      dataIndex: 'backCondition',
                      key: 'backCondition',
                      width: 150,
                      tdEdit: true,
                    },
                    form: {
                      field: 'backCondition',
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                ]
              }
            },
            {
              field: "table6",
              name: "qnnTable",
              title: "结算单",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaOtherEquipSettleList',
                    otherParams: {
                      orgID: departmentId,
                      zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId'),
                      apih5FlowStatus: '2',
                    }
                  }

                },
                antd: {
                  size: "small",
                  rowKey: "zxSaOtherEquipSettleId",
                },
                formConfig: [
                  {
                    table: {
                      title: '公司名称',
                      dataIndex: 'companyName',
                      key: 'companyName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '结算单编号',
                      dataIndex: 'billNo',
                      key: 'billNo',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 170,
                      onClick: 'detail',
                    }
                  },
                  {
                    table: {
                      title: '项目名称',
                      dataIndex: 'orgName',
                      key: 'orgName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '结算期次',
                      dataIndex: 'periodDate',
                      key: 'periodDate',
                      width: 120,
                      format: 'YYYY-MM'
                    }
                  },
                  {
                    table: {
                      title: '结算类型',
                      dataIndex: 'billType',
                      key: 'billType',
                      width: 120,
                      render: (h) => {
                        if (h === '0') {
                          return '中期'
                        } else if (h === '1') {
                          return '最终'
                        } else if (h === '2') {
                          return '返还保证金'
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: '合同名称',
                      dataIndex: 'contractName',
                      key: 'contractName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '合同乙方',
                      dataIndex: 'secondName',
                      key: 'secondName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '本期结算含税金额',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '开累结算含税金额',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '本期应支付含税金额',
                      dataIndex: 'thisPayAmt',
                      key: 'thisPayAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '开累应支付含税金额',
                      dataIndex: 'totalPayAmt',
                      key: 'totalPayAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '结算期限开始时间',
                      dataIndex: 'beginDate',
                      key: 'beginDate',
                      width: 150,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '结算期限结束时间',
                      dataIndex: 'endDate',
                      key: 'endDate',
                      width: 150,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '业务日期',
                      dataIndex: 'businessDate',
                      key: 'businessDate',
                      width: 100,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '填报人',
                      dataIndex: 'reportPerson',
                      key: 'reportPerson',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '评审状态',
                      dataIndex: 'apih5FlowStatus',
                      key: 'apih5FlowStatus',
                      width: 100,
                      fixed: 'right',
                      render: (data) => {
                        if (data === '0') {
                          return '待提交';
                        } else if (data === '1') {
                          return '审核中';
                        } else if (data === '2') {
                          return '审核完成';
                        } else if (data === '3') {
                          return '退回';
                        } else if (data === '-1') {
                          return '未审核';
                        }
                      }
                    }
                  },
                ],
                drawerConfig: {
                  width: '1100px'
                },
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "信息登记",
                    content: {
                      formConfig: [
                        {
                          field: 'zxSaOtherEquipSettleId',
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '项目名称',
                          field: "orgName",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '合同编号',
                          field: "contractNo",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '结算单编号',
                          field: "billNo",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '合同名称',
                          field: "contractName",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '结算签认单编号',
                          field: "signedNo",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '合同乙方',
                          field: "secondName",
                          type: 'string',
                          span: 12
                        },
                        {
                          label: '结算期次',
                          field: "period",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '结算类型',
                          field: "billType",
                          type: 'select',
                          span: 12,
                          optionConfig: {
                            label: 'label',
                            value: 'value',
                          },
                          optionData: [
                            { label: '中期', value: '0' },
                            { label: '最终', value: '1' },
                            { label: '返还保证金', value: '2' }
                          ],
                        },
                        {
                          label: '是否抵扣',
                          field: "isDeduct",
                          type: 'radio',
                          optionData: [
                            { label: '是', value: '1' },
                            { label: '否', value: '0' }
                          ],
                          span: 8,
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 12 },
                              sm: { span: 12 }
                            },
                            wrapperCol: {
                              xs: { span: 8 },
                              sm: { span: 8 }
                            }
                          }
                        },
                        {
                          label: '是否完工后结算',
                          field: "isFished",
                          type: 'radio',
                          optionData: [
                            { label: '是', value: '1' },
                            { label: '否', value: '0' }
                          ],
                          span: 8,
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 12 },
                              sm: { span: 12 }
                            },
                            wrapperCol: {
                              xs: { span: 8 },
                              sm: { span: 8 }
                            }
                          }
                        },
                        {
                          label: '是否首次结算',
                          field: "isFirst",
                          type: 'radio',
                          optionData: [
                            { label: '是', value: '1' },
                            { label: '否', value: '0' }
                          ],
                          span: 8,
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 12 },
                              sm: { span: 12 }
                            },
                            wrapperCol: {
                              xs: { span: 8 },
                              sm: { span: 8 }
                            }
                          }
                        },
                        {
                          label: '结算单初始化顺序号',
                          field: "initSerialNumber",
                          span: 12,
                          type: 'string',
                        },
                        {
                          label: '填报人',
                          field: "reportPerson",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '填报人电话',
                          field: "reportPersonstring",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '复核人',
                          field: "reCountPerson",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '填报日期',
                          field: "reportDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '业务日期',
                          field: "businessDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '结算期限开始时间',
                          field: "beginDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '结算期限结束时间',
                          field: "endDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '验收情况',
                          field: 'appraisal',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '结算内容及说明',
                          field: 'content',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '备注信息',
                          field: 'remark',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '附件',
                          field: 'zxErpFileList',
                          type: 'files',
                          span: 12,
                          fetchConfig: {
                            apiName: 'upload'
                          },
                        },
                      ]
                    }
                  },
                  {
                    field: "list1",
                    name: "qnnForm",
                    title: "清单结算",
                    content: {
                      fetchConfig: () => {
                        return {
                          apiName: 'getZxSaOtherEquipResSettleList',
                          otherParams: (obj) => {
                            return {
                              zxSaOtherEquipSettleId: obj.form.getFieldValue('zxSaOtherEquipSettleId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          label: '含税合同金额(万元)',
                          field: "contractAmt",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          field: "zxSaFsInventorySettlementId",
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '变更后含税合同金额(万元)',
                          field: "changeAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期清单结算含税金额(元)',
                          field: "thisAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期清单结算不含税金额(元)',
                          field: "thisAmtNoTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期清单结算税额(元)',
                          field: "thisAmtTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '累计清单结算含税金额(元)',
                          field: "totalAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          type: 'qnnTable',
                          field: 'zxSaOtherEquipResSettleItemList',
                          incToForm: true,
                          qnnTableConfig: {
                            antd: {
                              rowKey: 'zxSaOtherEquipResSettleItemId',
                              size: 'small'
                            },
                            paginationConfig: {
                              position: 'bottom'
                            },
                            isShowRowSelect: false,
                            formConfig: [
                              {
                                table: {
                                  title: '清单编号',
                                  dataIndex: 'equipCode',
                                  key: 'equipCode',
                                  align: 'center',
                                  fixed: 'left',
                                }
                              },
                              {
                                table: {
                                  title: '清单名称',
                                  dataIndex: 'equipName',
                                  key: 'equipName',
                                  align: 'center',
                                  fixed: 'left',
                                }
                              },
                              {
                                table: {
                                  title: '计量单位',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                  align: 'center',
                                  tdEdit: false
                                },
                              },
                              {
                                table: {
                                  title: '含税单价(元)',
                                  dataIndex: 'contractPrice',
                                  key: 'contractPrice',
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '合同数量',
                                  dataIndex: 'contractQty',
                                  key: 'contractQty',
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '合同含税金额',
                                  dataIndex: 'contractAmt',
                                  key: 'contractAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '变更后数量',
                                  dataIndex: 'changedQty',
                                  key: 'changedQty',
                                  align: 'center',
                                  width: 150
                                }
                              },
                              {
                                table: {
                                  title: '变更后含税金额',
                                  dataIndex: 'changedAmt',
                                  key: 'changedAmt',
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
                                  title: '本期结算数量',
                                  dataIndex: 'thisQty',
                                  key: 'thisQty',
                                  width: 120,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '本期结算含税金额',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 140,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '本期结算不含税金额',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '本期结算税额',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 120,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '至上期末累计结算数量',
                                  dataIndex: 'upQty',
                                  key: 'upQty',
                                  width: 180,
                                  align: 'center',
                                  initialValue: 0,
                                }
                              },
                              {
                                table: {
                                  title: '至上期末累计结算含税金额',
                                  dataIndex: 'upAmt',
                                  key: 'upAmt',
                                  width: 200,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '至本期末累计结算数量',
                                  dataIndex: 'totalQty',
                                  key: 'totalQty',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '至本期末累计结算金额',
                                  dataIndex: 'totalAmt',
                                  key: 'totalAmt',
                                  width: 200,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '计算式',
                                  dataIndex: 'planning',
                                  key: 'planning',
                                  width: 150,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '备注',
                                  dataIndex: 'remark',
                                  key: 'remark',
                                  width: 150,
                                  align: 'center',
                                },
                              },
                            ],
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "list2",
                    name: "qnnForm",
                    title: "支付项结算",
                    content: {
                      fetchConfig: () => {
                        return {
                          apiName: 'getZxSaOtherEquipPaySettleList',
                          otherParams: (obj) => {
                            return {
                              zxSaOtherEquipSettleId: obj.form.getFieldValue('zxSaOtherEquipSettleId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          field: "zxSaOtherEquipPaySettleId",
                          type: 'string',
                          hide: true
                        },
                        {
                          label: '本期支付项结算含税金额(元)',
                          field: "thisAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期支付项结算不含税金额(元)',
                          field: "thisAmtNoTax",
                          type: 'number',
                          span: 12,
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 9 },
                              sm: { span: 9 }
                            },
                            wrapperCol: {
                              xs: { span: 15 },
                              sm: { span: 15 }
                            }
                          }
                        },
                        {
                          label: '本期支付项结算税额(元)',
                          field: "thisAmtTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '累计支付项结算金额(元)',
                          field: "totalAmt",
                          type: 'number',
                          span: 12,
                          formItemLayout: {
                            labelCol: {
                              xs: { span: 9 },
                              sm: { span: 9 }
                            },
                            wrapperCol: {
                              xs: { span: 15 },
                              sm: { span: 15 }
                            }
                          }
                        },
                        {
                          type: 'qnnTable',
                          field: 'table3',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2' && obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipPaySettleId')) {
                                return {
                                  apiName: 'getZxSaOtherEquipPaySettleItemList',
                                  otherParams: { zxSaOtherEquipPaySettleId: obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipPaySettleId') }
                                }
                              } else {
                                return {}
                              }
                            },
                            paginationConfig: {
                              position: 'bottom'
                            },
                            isShowRowSelect: false,
                            formConfig: [
                              {
                                table: {
                                  title: '支付项类型',
                                  dataIndex: 'payType',
                                  key: 'payType',
                                  fixed: 'left',
                                  width: 120
                                },
                              },
                              {
                                table: {
                                  title: '名称',
                                  dataIndex: 'payName',
                                  key: 'payName',
                                  fixed: 'left',
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '单位',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                },
                              },
                              {
                                table: {
                                  title: '数量',
                                  dataIndex: 'qty',
                                  key: 'qty',
                                },
                              },
                              {
                                table: {
                                  title: '单价(元)',
                                  dataIndex: 'price',
                                  key: 'price',
                                  width: 120
                                },
                              },
                              {
                                table: {
                                  title: '税率(%)',
                                  dataIndex: 'taxRate',
                                  key: 'taxRate',
                                  width: 80
                                },
                                form: {
                                  field: 'taxRate',
                                  type: 'select',
                                  optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                  },
                                  fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: { itemId: 'shuiLv' }
                                  },
                                }
                              },
                              {
                                table: {
                                  title: '本期结算含税金额(元)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 170,
                                }
                              },

                              {
                                table: {
                                  title: '本期结算不含税金额(元)',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 170,
                                }
                              },
                              {
                                table: {
                                  title: '本期结算税额',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 140,
                                }
                              },
                              {
                                table: {
                                  title: '备注',
                                  dataIndex: 'remark',
                                  key: 'remark',
                                  width: 120,
                                },
                              },
                            ],
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "table4",
                    name: "qnnForm",
                    title: "统计项",
                    content: {
                      formConfig: [
                        {
                          type: 'qnnTable',
                          field: 'table4_1',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '3') {
                                return {
                                  apiName: 'getZxSaOtherEquipSettleItemList',
                                  otherParams: () => {
                                    return {
                                      zxSaOtherEquipSettleId: obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipSettleId')
                                    }
                                  }
                                }
                              } else {
                                return {}
                              }
                            },
                            paginationConfig: false,
                            isShowRowSelect: false,
                            pageSize: 999999,
                            antd: {
                              size: "small",
                              rowKey: "zxSaOtherEquipSettleItemId",
                            },
                            formConfig: [
                              {
                                table: {
                                  title: '统计项',
                                  dataIndex: 'statisticsName',
                                  key: 'statisticsName',
                                  width: 100,
                                  type: 'string',
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '本期（元）',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 150,
                                  type: 'string',
                                  tdEdit: true,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '开累（元）',
                                  dataIndex: 'totalAmt',
                                  key: 'totalAmt',
                                  width: 100,
                                  type: 'string',
                                  align: 'center',
                                },
                              }
                            ],
                          }
                        },
                      ]
                    }
                  }
                ]
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;