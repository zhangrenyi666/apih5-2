import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Button, Drawer, Tabs } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import Operation from './operation';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtFsContractId',
    size: 'small',
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
    this.state = {
      flowData: null,
      visible: false,
      activeKey: '1'
    }
  }
  onClose = () => {
    this.setState({
      visible: false,
    });
  };
  tabsCallback = (activeKey) => {
    this.setState({ activeKey })
  }
  render() {
    const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const { flowData, visible, activeKey } = this.state;
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
            apiName: 'getZxCtFsContractList',
            otherParams: { orgID: departmentId }
          }}
          actionBtns={[
            {
              name: "edit",
              icon: 'edit',//icon
              label: "修改",
              field: "edit",
              type: "primary",
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
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
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                    this.props.myFetch('updateZxCtFsContract', obj._formData).then(
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
            },
            {
              name: 'ditbutton',//内置add del
              icon: 'plus',//icon
              type: 'primary',
              label: '执行中/终止',
              field: "diybutton",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1) {
                  return true;
                } else {
                  return false;
                }
              },
              onClick: (obj) => {
                this.table.clearSelectedRows();
                obj.btnCallbackFn.setBtnsLoading('add', 'ditbutton');
                confirm({
                  content: obj.selectedRows[0].contractStatus === '3' ? '确定恢复执行吗?' : '确定终止吗',
                  onOk: () => {
                    let params = obj.selectedRows[0]
                    params.contractStatus = obj.selectedRows[0].contractStatus === '3' ? '1' : '3'
                    this.props.myFetch('updateZxCtFsContract', params).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                })
              }
            },
            {
              name: 'diyDel',
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              field: "del",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length === 0) {
                  return true;
                } else {
                  return false;
                }
              },
              onClick: (obj) => {
                confirm({
                  content: '确定删除选中的数据吗?',
                  onOk: () => {
                    obj.selectedRows.map((item) => {
                      if (item.settleType !== '实际无结算') {
                        Msg.warn('所选合同存在已开始执行合同，不可删除')
                        return
                      }
                      this.props.myFetch('batchDeleteUpdateZxCtFsContract', obj.selectedRows)
                        .then(({ success, message }) => {
                          if (success) {
                            Msg.success(message)
                            obj.btnCallbackFn.refresh()
                          } else {
                            Msg.error(message)
                          }
                        })
                    })
                  }
                })
              },
            },
            {
              disabled: "bind:_actionBtnNoSelected",
              icon: "plus",
              label: "合同评审记录查看",
              name: "ditbutton",
              tableField: "recordView",
              type: "primary",
              onClick: (obj) => {
                this.props.myFetch('openFlowByReport', { apiName: 'getZxCtFsContractReviewDetail', apiType: 'POST', flowId: 'ZxFsReviewWorkFlow', workId: obj.selectedRows[0].workId }).then(
                  ({ success, message, data }) => {
                    if (success) {
                      this.setState({
                        activeKey: '1',
                        flowData: data,
                        visible: true
                      })
                    } else {
                      Msg.error(message)
                    }
                  }
                );
              }
            },
          ]}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.clearSelectedRows();
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
                tooltip: 20
              },
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 150,
                tooltip: 15
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
                width: 170,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 20
              }
            },
            {
              table: {
                title: '合同乙方',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 170,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 20
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
                title: '合同录入类型',
                dataIndex: 'audit',
                key: 'audit',
                width: 120,
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
                dataIndex: 'code7',
                key: 'code7',
                width: 100,
                render: (h) => {
                  if (h === '0') {
                    return '物资其他类'
                  } else if (h === '1') {
                    return '物资运输类'
                  }
                }
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 100
              }
            }
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "合同信息",
              content: {
                formConfig: [
                  {
                    field: 'orgID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtFsContractId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'contractReviewId',
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
                    label: '合同类型',
                    field: "contractType",
                    initialValue: '附属类',
                    type: 'string',
                    required: true,
                    placeholder: '请输入',
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
                    field: "secondID",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerNewId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: {
                      apiName: 'getZxCrCustomerNewList',
                      searchKey: 'customerName'
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
                    field: "firstPrincipalIDCard",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方代表身份证',
                    field: "secondPrincipalIDCard",
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
                    field: "pp1",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方(传真)',
                    field: "pp2",
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
                    field: "audit",
                    type: 'string',
                    span: 12,
                    disabled: true,
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
                        xs: { span: 16 },
                        sm: { span: 16 }
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
                    span: 12
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
                    field: "code7",
                    span: 12,
                    disabled: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '物资其他类', value: '0' },
                      { label: '物资运输类', value: '1' },
                    ],
                    placeholder: '请选择',
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
                    field: 'remarks',
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
              }
            },
            {
              field: "form2",
              name: "qnnTable",
              title: "清单明细",
              content: {
                fetchConfig: (obj) => {
                  if (this.table.getActiveKey() === '1') {
                    return {
                      apiName: 'getAllZxCtFsReviewDetailList',
                      otherParams: {
                        zxCtFsContractId: this.table.qnnForm.form.getFieldValue('zxCtFsContractId'),
                        contractReviewId: this.table.qnnForm.form.getFieldValue('contractReviewId')
                      }
                    }
                  } else { return {} }
                },
                isShowRowSelect: false,
                paginationConfig: false,
                formConfig: [
                  {
                    table: {
                      title: '清单编号',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 150,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '清单名称',
                      width: 150,
                      type: 'string',
                      dataIndex: 'workName',
                      key: 'workName',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '计量单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '合同数量',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 100,
                      type: 'number',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '含税合同单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '不含税合同单价',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '税率%',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      type: 'string',
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
                        let isDeduct = this.table.form.getFieldValue('isDeduct')
                        if (isDeduct === '1') {
                          return '是';
                        } else {
                          return '否';
                        }
                      }
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '含税合同金额',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
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
                      title: '不含税合同金额',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
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
                      title: '变更后数量',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
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
                      title: '变更后含税单价',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
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
                      title: '变更后不含税单价',
                      dataIndex: 'changePriceNoTax',
                      key: 'changePriceNoTax',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额',
                      dataIndex: 'changeContractSum',
                      key: 'changeContractSum',
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
                      title: '变更后不含税金额',
                      dataIndex: 'changeContractSumNoTax',
                      key: 'changeContractSumNoTax',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      spanForm: 12
                    }
                  },
                ]
              }
            },
            {
              field: "table2",
              name: "qnnTable",
              title: "补充协议",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsSideAgreementList',
                    otherParams: {
                      zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId'),
                      contractReviewId: obj.tableFns.form.getFieldValue('contractReviewId'),
                      apih5FlowStatus: '2'
                    }
                  }
                },
                drawerConfig: {
                  width: '1000px'
                },
                wrappedComponentRef: (me) => {
                  this.TableTwo = me
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtFsSideAgreementId",
                },
                paginationConfig: false,
                actionBtns: [
                  {
                    name: "edit",
                    label: "修改",
                    field: "edit",
                    type: "primary",
                    formBtns: [
                      {
                        name: 'cancel', //关闭右边抽屉
                        type: 'dashed',//类型  默认 primary
                        label: '取消',
                        hide: (obj) => {
                          var index = obj.btnCallbackFn.getActiveKey();
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
                          var index = obj.btnCallbackFn.getActiveKey();
                          if (index === "0") {
                            return false;
                          } else {
                            return true;
                          }
                        },
                        onClick: (obj) => {
                          obj._formData.zxCtFsContractId = obj.selectedRows[0].zxCtFsContractId
                          obj.btnCallbackFn.clearSelectedRows();
                          obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                          this.props.myFetch('updateZxCtFsSideAgreement', obj._formData).then(
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
                      tooltip: 15,
                      onClick: 'detail',
                    }
                  },
                  {
                    table: {
                      title: '补充协议名称',
                      dataIndex: 'pp3',
                      key: 'pp3',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 150,
                      tooltip: 15,
                    }
                  },
                  {
                    table: {
                      title: '合同名称',
                      dataIndex: 'contractName',
                      key: 'contractName',
                      width: 150,
                      tooltip: 15,
                    }
                  },
                  {
                    table: {
                      title: '合同类型',
                      dataIndex: 'contractType',
                      key: 'contractType',
                      width: 150,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '甲方名称',
                      dataIndex: 'firstName',
                      key: 'firstName',
                      width: 150,
                      tooltip: 15,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '乙方名称',
                      dataIndex: 'secondName',
                      key: 'secondName',
                      tooltip: 15,
                      width: 150,
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
                      dataIndex: 'pp4',
                      key: 'pp4',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额(万元)',
                      dataIndex: 'alterContractSum',
                      key: 'alterContractSum',
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
                      dataIndex: 'createUserName',
                      key: 'createUserName',
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
                isShowRowSelect: true,
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "补充协议基础信息",
                    content: {
                      formConfig: [
                        {
                          field: "apih5FlowStatus",
                          type: 'string',
                          hide: true,
                        },
                        {
                          field: 'zxCtFsSideAgreementId',
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
                          field: "pp3",
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
                          initialValue: '附属类补充协议'
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
                          field: "pp4",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期不含税增减金额(万元)',
                          field: "pp4NoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '本期增减税额(万元)',
                          field: "pp4Tax",
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
                          label: '合同工期',
                          field: "csTimeLimit",
                          type: 'string',
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '批复单位',
                          field: 'pp1',
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
                          field: 'remarks',
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
                      fetchConfig: () => {
                        const zxCtFsSideAgreementId = this.TableTwo.form.getFieldValue('zxCtFsSideAgreementId')
                        return {
                          apiName: 'getZxCtFsSideAgreementInventoryList',
                          otherParams: { zxCtFsSideAgreementId }
                        }
                      },
                      antd: {
                        size: "small",
                        rowKey: "zxCtFsSideAgreementInventoryId",
                      },
                      formConfig: [
                        {
                          table: {
                            title: '变更类型',
                            type: 'string',
                            dataIndex: 'alterType',
                            key: 'alterType',
                            width: 120,
                            align: 'center',
                            fixed: 'left',
                          },
                        },
                        {
                          table: {
                            title: '清单编号',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 100,
                            type: 'string',
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '清单名称',
                            width: 150,
                            type: 'string',
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
                            type: 'string',
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '数量',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
                            type: 'number',
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
                            type: 'number',
                          }
                        },
                        {
                          table: {
                            title: '变更后单价',
                            dataIndex: 'changePrice',
                            key: 'changePrice',
                            width: 120,
                            type: 'number',
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
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            type: 'string',
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '是否抵扣',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: (data) => {
                              if (data === '1') {
                                return '是';
                              } else {
                                return '否';
                              }
                            }
                          }
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
                ]
              }
            },
            {
              field: "table2",
              name: "qnnTable",
              title: "保证金比例设置",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsContractBondList',
                    otherParams: { zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtFsContractBondId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtFsContractBondId !== this.state.lineEdit
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyaddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtFsContractBondId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtFsContractBondId = Date.parse(new Date())
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtFsContractBondId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    disabled: (obj) => {
                      if (obj.btnCallbackFn.getSelectedRows().length) {
                        return false;
                      } else {
                        return true;
                      }
                    },
                    onClick: (obj) => {
                      confirm({
                        content: '确定删除选中的数据吗?',
                        onOk: () => {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtFsContractBondId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtFsContractBond', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      })
                    }
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtFsContractBondId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: `<div>保证金<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 150,
                      tdEdit: true,
                      initialValue: 0
                    },
                    form: {
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
                      width: 100,
                      tdEdit: true,
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
                      tdEdit: true,
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
                      placeholder: '请输入'
                    }
                  },
                  {
                    isInTable: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'detail') {
                        return false
                      } else {
                        return true
                      }
                    },
                    isInForm: false,
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxCtFsContractBondId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtFsContractBondId) {
                          obj.btnCallbackFn.getEditedRowData().then((data) => {
                            if (!data.statisticsName) {//
                              Msg.warn('请填写保证金！')
                              return
                            }
                            console.log(data);
                            let body = {
                              ...data,
                            };
                            body.zxCtFsContractId = obj.props.tableFns.form.getFieldValue('zxCtFsContractId')
                            body.zxCtFsContractBondId = obj.rowData.zxCtFsContractBondId
                            this.props.myFetch(!isNaN(obj.rowData.zxCtFsContractBondId) ? 'addZxCtFsContractBond' : 'updateZxCtFsContractBond', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  this.setState({
                                    lineEdit: ''
                                  })
                                  obj.btnCallbackFn.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              }
                            );
                          })
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtFsContractBondId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtFsContractBondId
                          })
                        }
                      }
                    }
                  }
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
                    apiName: 'getZxSaFsSettlementList',
                    otherParams: {
                      orgID: departmentId,
                      zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId'),
                      apih5FlowStatus: '2'
                    }
                  }

                },
                formConfig: [
                  {
                    table: {
                      title: '结算单编号',
                      dataIndex: 'billNo',
                      key: 'billNo',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 150,
                      tooltip: 15,
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
                      tooltip: 15,
                    }
                  },
                  {
                    table: {
                      title: '结算期次',
                      dataIndex: 'periodTime',
                      key: 'periodTime',
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
                      tooltip: 15,
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
                      tooltip: 15,
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
                      title: '重新评审次数',
                      dataIndex: 'notPassNum',
                      key: 'notPassNum',
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
                      title: '发起人',
                      dataIndex: 'createUserName',
                      key: 'createUserName',
                      width: 100,
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
                          field: 'zxSaFsSettlementId',
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '项目名称',
                          field: "orgName",
                          type: 'string',
                          showSearch: true,
                          span: 12,
                        },
                        {
                          label: '合同编号',
                          field: "contractNo",
                          type: 'string',
                          showSearch: true,
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
                          field: "periodTime",
                          type: 'month',
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
                          label: '计算人',
                          field: "countPerson",
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
                          placeholder: '请输入',
                          span: 12,
                        },
                        {
                          label: '结算内容及说明',
                          field: 'content',
                          type: 'textarea',
                          span: 12,
                          placeholder: '请输入',
                        },
                        {
                          label: '备注信息',
                          field: 'remarks',
                          type: 'textarea',
                          span: 12,
                          placeholder: '请输入',
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
                          apiName: 'getZxSaFsInventory',
                          otherParams: (obj) => {
                            return {
                              zxSaFsSettlementId: obj.form.getFieldValue('zxSaFsSettlementId')
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
                          field: 'table2',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '1' && obj.qnnFormProps.form.getFieldValue('zxSaFsInventorySettlementId')) {
                                return {
                                  apiName: 'getZxSaFsInventorySettlementDetailList',
                                  otherParams: { zxSaFsInventorySettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsInventorySettlementId') },
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
                                  title: '清单编号',
                                  dataIndex: 'equipCode',
                                  key: 'equipCode',
                                  align: 'center',
                                  fixed: 'left',
                                  tooltip: 15,
                                }
                              },
                              {
                                table: {
                                  title: '清单名称',
                                  dataIndex: 'equipName',
                                  key: 'equipName',
                                  align: 'center',
                                  fixed: 'left',
                                  tooltip: 15,
                                }
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
                                  width: 80,
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
                                  title: '备注',
                                  dataIndex: 'remarks',
                                  key: 'remarks',
                                  width: 150,
                                  align: 'center',
                                  tooltip: 15,
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
                          apiName: 'getZxSaFsPaySettlement',
                          otherParams: (obj) => {
                            return {
                              zxSaFsSettlementId: obj.form.getFieldValue('zxSaFsSettlementId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          field: "zxSaFsPaySettlementId",
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
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2' && obj.qnnFormProps.form.getFieldValue('zxSaFsPaySettlementId')) {
                                return {
                                  apiName: 'getZxSaFsPaySettlementDetailList',
                                  otherParams: { zxSaFsPaySettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsPaySettlementId') }
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
                                  tooltip: 15,
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
                                  dataIndex: 'remarks',
                                  key: 'remarks',
                                  width: 120,
                                  tooltip: 15,
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
                                  apiName: 'getZxSaFsStatisticsDetailList',
                                  otherParams: () => {
                                    return {
                                      zxSaFsSettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsSettlementId')
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
        {
          visible ? <Drawer
            title={'合同评审详情'}
            placement="right"
            onClose={this.onClose}
            visible={visible}
            width={window.screen.width * 0.75}
            className={'drawerClass'}
            footer={
              <div
                style={{
                  textAlign: 'right',
                }}
              >
                <Button onClick={this.onClose}> 取消 </Button>
              </div>
            }
          >
            <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
              <TabPane tab="基础信息" key="1">
                <QnnForm
                  {...this.props}
                  match={this.props.match}
                  fetch={this.props.myFetch}
                  upload={this.props.myUpload}
                  headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                  data={flowData?.apiData ? JSON.parse(flowData.apiData) : null}
                  formConfig={[
                    {
                      label: '附属合同编号',
                      field: "contractNo",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '附属合同名称',
                      field: "contractName",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '合同类型',
                      field: "contractType",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '原合同编号',
                      field: "fromContractNo",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '合同类别',
                      field: "code7",
                      type: 'select',
                      optionConfig: {
                        label: 'label',
                        value: 'value'
                      },
                      optionData: [
                        { label: '物资其他类', value: '0' },
                        { label: '物资运输类', value: '1' },
                      ],
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '甲方名称',
                      field: "firstID",
                      type: 'select',
                      optionConfig: {
                        label: 'orgName',
                        value: 'orgID',
                      },
                      fetchConfig: {
                        apiName: 'getZxCtContractListByStatus'
                      },
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '乙方名称',
                      field: "secondName",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      field: 'secondOrgType',
                      type: 'select',
                      label: '协作单位类型',
                      disabled: true,
                      span: 12,
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      optionData: [
                        { label: '施工协作单位', value: '0' },
                        { label: '设备生产商', value: '2' },
                        { label: '设备出租商', value: '3' },
                        { label: '设备经销商', value: '4' },
                        { label: '材料生产商', value: '5' },
                        { label: '材料租赁商', value: '6' },
                        { label: '材料经销商', value: '7' },
                        { label: '其他协作单位', value: '8' },
                      ],
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
                    },
                    {
                      label: '合同签订人',
                      field: "agent",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '含税合同金额(万元)',
                      field: "contractCost",
                      type: 'number',
                      disabled: true,
                      span: 12,
                      precision: 6,
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
                    },
                    {
                      label: '不含税合同金额(万元)',
                      field: "contractCostNoTax",
                      type: 'number',
                      disabled: true,
                      span: 12,
                      precision: 6,
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
                    },
                    {
                      label: '合同税额(万元)',
                      field: "contractCostTax",
                      type: 'number',
                      disabled: true,
                      span: 12,
                      formItemLayout: {
                        labelCol: {
                          xs: { span: 8 },
                          sm: { span: 8 }
                        },
                        wrapperCol: {
                          xs: { span: 16 },
                          sm: { span: 16 }
                        }
                      },
                      precision: 6,
                    },
                    {
                      label: '是否抵扣',
                      field: "isDeduct",
                      type: 'radio',
                      optionData: [
                        { label: '是', value: '1' },
                        { label: '否', value: '0' }
                      ],
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '合同工期',
                      field: "csTimeLimit",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '发起人',
                      field: "createUserName",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '合同内容',
                      field: 'content',
                      type: 'textarea',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '备注',
                      field: 'remarks',
                      type: 'textarea',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: "清单细目",
                      type: 'qnnTable',
                      field: 'qnnTableList',
                      qnnTableConfig: {
                        fetchConfig: (obj) => {
                          return {
                            apiName: 'getZxCtFsContractReviewDetailList',
                            otherParams: {
                              contractReviewId: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.contractReviewId
                            }
                          }
                        },
                        antd: {
                          size: "small",
                          rowKey: 'conRevDetailId'
                        },
                        paginationConfig: {
                          position: 'bottom'
                        },
                        isShowRowSelect: false,
                        formConfig: [
                          {
                            table: {
                              title: '清单编号',
                              dataIndex: 'workNo',
                              key: 'workNo',
                              width: 150,
                              tooltip: 15,
                              fixed: 'left',
                              align: 'center',
                            },
                            form: {
                              type: 'select',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '清单名称',
                              dataIndex: 'workName',
                              key: 'workName',
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
                            table: {
                              title: '计量单位',
                              dataIndex: 'unit',
                              key: 'unit',
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
                            table: {
                              title: '合同数量',
                              dataIndex: 'qty',
                              key: 'qty',
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
                              title: '含税合同单价(元)',
                              dataIndex: 'price',
                              key: 'price',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '不含税合同单价(元)',
                              dataIndex: 'priceNoTax',
                              key: 'priceNoTax',
                              width: 120,
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '税率(%)',
                              dataIndex: 'taxRate',
                              key: 'taxRate',
                              width: 150,
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
                              width: 150,
                              render: (h) => {
                                if (h === '1') {
                                  return '是';
                                } else {
                                  return '否';
                                }
                              }
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '含税合同金额',
                              dataIndex: 'contractSum',
                              key: 'contractSum',
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
                              title: '变更后数量',
                              dataIndex: 'changeQty',
                              key: 'changeQty',
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
                              title: '变更后含税单价',
                              dataIndex: 'changePrice',
                              key: 'changePrice',
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
                              title: '变更后不含税单价',
                              dataIndex: 'changePriceNoTax',
                              key: 'changePriceNoTax',
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
                              dataIndex: 'changeContractSum',
                              key: 'changeContractSum',
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
                              dataIndex: 'changeContractSumNoTax',
                              key: 'changeContractSumNoTax',
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
                              dataIndex: 'remarks',
                              key: 'remarks',
                              width: 150,
                              tooltip: 15,
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                        ]
                      }
                    },
                    {
                      type: "textarea",
                      label: "审批意见",
                      field: "opinionField1",
                      formatter: (val) => {
                        if (val) {
                          if (val.indexOf('<br/>') !== -1) {
                            return val.replace("<br/>", "\r\n");
                          }
                        }
                      },
                      disabled: true,
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
                    }
                  ]}
                />
              </TabPane>
              <TabPane tab="操作历史" key="2">
                <Operation {...this.props} flowData={flowData} />
              </TabPane>
            </Tabs>
          </Drawer> : null
        }
      </div>
    );
  }
}

export default index;