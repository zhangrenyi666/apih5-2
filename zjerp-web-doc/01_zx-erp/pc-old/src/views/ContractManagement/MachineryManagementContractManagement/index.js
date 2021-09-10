import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Button, Drawer, Tabs } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Toast } from 'antd-mobile';
import MachineryManagementContractDetail from './detail'
import Operation from './operation';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'contractID',
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
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
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
            apiName: 'getZxCtEqContractList',
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
                    let index = obj.btnCallbackFn.getActiveKey();
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
                    let index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('updateZxCtEqContract', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          if (this.tableOne) {
                            this.tableOne.refresh();
                          }
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
                    let params = {}
                    params.contractID = obj.selectedRows[0].contractID
                    this.props.myFetch('recoverZxCtEqContract', params).then(
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
              name: 'diyDel',//内置add del
              field: 'del',
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length === 0 ) {
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
                      this.props.myFetch('batchDeleteUpdateZxCtEqContract', obj.selectedRows)
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
              field: 'diyview',
              label: "合同评审记录查看",
              name: "ditbutton",
              tableField: "recordView",
              type: "primary",
              onClick: (obj) => {
                this.props.myFetch('openFlowByReport', { apiName: 'getZxCtEqContrApplyDetail', apiType: 'POST', flowId: 'zxCtEqContrApply', workId: obj.selectedRows[0].workId }).then(
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
              isInTable: false,
              form: {
                field: 'contractID',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                fixed: 'left',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
                onClick: 'detail',
                tooltip: 20,
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
                width: 180,
                tooltip: 18,
              }
            },
            {
              table: {
                title: '合同类型',
                dataIndex: 'pp3',
                key: 'pp3',
                filter: true,
                type: 'string',
                fieldsConfig: { type: 'string' },
                width: 150,
              }
            },
            {
              table: {
                title: '合同甲方',
                dataIndex: 'firstName',
                key: 'firstName',
                width: 180,
                type: 'string',
                tooltip: 18,
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
                tooltip: 18,
              }
            },
            {
              table: {
                title: '合同类别',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                width: 150,
                render: (data) => {
                  if (data === '0') {
                    return '设备租赁';
                  } else if (data === '1') {
                    return '设备采购';
                  }
                }
              }
            },
            {
              table: {
                title: '结算情况',
                dataIndex: 'settleType',
                key: 'settleType',
                width: 150,
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
                width: 150
              }
            },
            {
              table: {
                title: '变更后含税合同金额(万元)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 180
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
                title: '合同工期',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                width: 150,
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
                filter: true,
              }
            },
            {
              table: {
                title: '合同开始时间',
                dataIndex: 'planStartDate',
                key: 'planStartDate',
                width: 120,
                format: "YYYY-MM-DD"
              },
              isInForm: false
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
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    type: "string",
                    label: "主键ID",
                    field: "contractID",
                    hide: true
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'firstId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '合同编号',
                    field: 'contractNo',
                    type: 'string',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '签订时间',
                    field: 'signatureDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '合同名称',
                    field: 'contractName',
                    type: 'string',
                    required: true,
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类型',
                    field: 'pp3',
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '合同甲方',
                    field: 'firstName',
                    type: 'string',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12
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
                    label: '乙方性质',
                    field: 'agent',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '登记日期',
                    field: 'registerDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '项目经理',
                    field: 'projectManager',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方代表',
                    field: 'secondPrincipal',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同结束时间',
                    field: 'planEndDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '甲方联系电话',
                    field: 'firstUnitTel',
                    type: 'phone',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '乙方(固话)',
                    field: 'secondUnitTel',
                    type: 'phone',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类别',
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
                    placeholder: '请选择',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '结算情况',
                    field: 'settleType',
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '含税合同总价(万元)',
                    field: 'contractCost',
                    type: 'number',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '不含税合同总价(万元)',
                    field: 'contractCostNoTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '合同税额(万元)',
                    field: 'contractCostTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '变更后含税合同金额(万元)',
                    field: 'alterContractSum',
                    type: 'number',
                    placeholder: '请输入',
                    disabled: true,
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '变更后不含税合同金额(万元)',
                    field: 'alterContractSumNoTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6,
                    initialValue: 0
                  },
                  {
                    label: '变更后合同税额(万元)',
                    field: 'alterContractSumTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '是否抵扣',
                    field: "isDeduct",
                    span: 12,
                    type: 'radio',
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    disabled: true,
                  },
                  {
                    label: '乙方(手机)',
                    field: 'pp1',
                    type: 'phone',
                    placeholder: '请输入',
                    span: 12
                  },

                  {
                    label: '乙方传真',
                    field: 'pp2',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同工期',
                    field: 'csTimeLimit',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同录入类型',
                    field: 'auditStatus',
                    type: 'string',
                    disabled: true,
                    span: 12,
                  },
                  {
                    label: '合同状态',
                    field: 'contractStatus',
                    type: 'select',
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
                    allowClear: false,
                    showSearch: true,
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '合同开始时间',
                    field: 'planStartDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '租赁方式',
                    field: 'rentType',
                    type: 'select',
                    optionConfig: {
                      label: 'itemName', //默认 label
                      value: 'itemName'
                    },
                    optionData: [
                      {
                        itemName: '时间租赁'
                      },
                      {
                        itemName: '工程量租赁'
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '合同内容',
                    field: 'content',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '上传附件',
                    field: 'zxErpFileList',
                    type: 'files',
                    required: true,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    span: 12
                  }
                ]
              }
            },
            {
              field: "table2",
              name: "qnnForm",
              title: "合同清单明细",
              hide: (obj) => {
                let contractCategory = obj.form.getFieldValue('contractCategory')
                if (contractCategory !== '0') {
                  return true
                } else {
                  return false
                }
              },
              componentsKey: {
                MachineryManagementContractDetail: () => {
                  return <MachineryManagementContractDetail {...this.props} />
                }
              },
              content: {
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'bondList',
                    qnnTableConfig: {
                      antd: {
                        rowKey: 'id',
                        size: 'small'
                      },
                      isShowRowSelect: false,
                      fetchConfig: () => {
                        if (this.table.getActiveKey() === '1') {
                          return {
                            apiName: 'getZxCtEqContrItemListForContract',
                            otherParams: {
                              contractID: this.table.qnnForm.form.getFieldValue('contractID'),
                            },
                          }
                        } else {
                          return {}
                        }
                      },
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            dataIndex: 'zxEqResCategoryId',
                            key: 'zxEqResCategoryId',
                            type: 'string',
                          }
                        },
                        {
                          table: {
                            title: '编号',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 150,
                            tooltip: 15,
                            fixed: 'left',
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '设备名称',
                            dataIndex: 'catName',
                            key: 'catName',
                            width: 150,
                            tooltip: 15,
                            fixed: 'left'
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '型号',
                            dataIndex: 'spec',
                            key: 'spec',
                            width: 150,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '租赁单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 150,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
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
                            render: (val, obj) => {
                              if (obj.qty) {
                                return val
                              } else {
                                return 0
                              }
                            }
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
                            render: (val, obj) => {
                              if (obj.qty) {
                                return val
                              } else {
                                return 0
                              }
                            }
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
                            title: '变更后数量',
                            dataIndex: 'alterTrrm',
                            key: 'alterTrrm',
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
                            dataIndex: 'alterPrice',
                            key: 'alterPrice',
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
                            title: '变更后不含税单价',
                            dataIndex: 'alterPriceNoTax',
                            key: 'alterPriceNoTax',
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
                            title: '变更后不含税金额',
                            dataIndex: 'alterAmtNoTax',
                            key: 'alterAmtNoTax',
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
                            dataIndex: 'alterAmt',
                            key: 'alterAmt',
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
                            title: '是否抵扣',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: () => {
                              let isDeduct = this.table.btnCallbackFn?.form?.getFieldValue('isDeduct')
                              if (isDeduct === '1') {
                                return '是'
                              } else {
                                return '否'
                              }
                            },
                          }
                        },
                        {
                          table: {
                            title: '备注',
                            dataIndex: 'remark',
                            key: 'remark',
                            width: 150,
                            tooltip: 15,
                          }
                        },
                        {
                          table: {
                            title: '限价信息',
                            dataIndex: 'action',
                            key: 'action',
                            align: "center",
                            showType: "tile",
                            fixed: 'right',
                            width: 120,
                            render: () => {
                              return '<a>详情</a>'
                            },
                            drawerTitle: "限价信息",
                            onClick: 'Component',
                            Component: MachineryManagementContractDetail
                          },
                        },
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table3",
              name: "qnnForm",
              title: "合同清单明细",
              hide: (obj) => {
                let contractCategory = obj.form.getFieldValue('contractCategory')
                if (contractCategory === '0') {
                  return true
                } else {
                  return false
                }
              },
              content: {
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'bondList_1',
                    qnnTableConfig: {
                      antd: {
                        rowKey: 'id',
                        size: 'small'
                      },
                      isShowRowSelect: false,
                      fetchConfig: () => {
                        if (this.table.getActiveKey() === '1') {
                          return {
                            apiName: 'getZxCtEqContrItemListForContract',
                            otherParams: {
                              contractID: this.table.qnnForm.form.getFieldValue('contractID'),
                            },
                          }
                        } else {
                          return {}
                        }
                      },
                      formConfig: [
                        {
                          table: {
                            title: '设备分类',
                            dataIndex: 'catParentName',
                            key: 'catParentName',
                            width: 150,
                            fixed: 'left',
                            tooltip: 15,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '编号',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 150,
                            fixed: 'left',
                            tooltip: 15,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '设备名称',
                            dataIndex: 'catName',
                            key: 'catName',
                            width: 150,
                            fixed: 'left',
                            tooltip: 15,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '规格型号',
                            dataIndex: 'spec',
                            key: 'spec',
                            width: 150,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 150,
                          },
                          form: {
                            type: 'string',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '数量',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 150,
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
                            render: (val, obj) => {
                              if (obj.qty) {
                                return val
                              } else {
                                return 0
                              }
                            }
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
                            render: (val, obj) => {
                              if (obj.qty) {
                                return val
                              } else {
                                return 0
                              }
                            }
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
                            title: '变更后数量',
                            dataIndex: 'alterTrrm',
                            key: 'alterTrrm',
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
                            dataIndex: 'alterPrice',
                            key: 'alterPrice',
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
                            title: '变更后不含税单价',
                            dataIndex: 'alterPriceNoTax',
                            key: 'alterPriceNoTax',
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
                            title: '变更后不含税金额',
                            dataIndex: 'alterAmtNoTax',
                            key: 'alterAmtNoTax',
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
                            dataIndex: 'alterAmt',
                            key: 'alterAmt',
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
                            title: '是否抵扣',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: () => {
                              let isDeduct = this.table.btnCallbackFn?.form?.getFieldValue('isDeduct')
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
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table4",
              name: "qnnTable",
              title: "补充协议",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrSupplyListBycontractID',
                    otherParams: {
                      contractID: obj.tableFns.form.getFieldValue('contractID'),
                      apih5FlowStatus: '2'
                    },
                  }
                },
                drawerConfig: {
                  width: "1000px"
                },
                antd: {
                  rowKey: 'zxCtEqContrSupplyId',
                  size: 'small'
                },
                paginationConfig: {
                  position: "bottom"
                },
                desc: '本页面所有金额单位为：万元',
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'edit',
                    icon: 'edit',
                    type: 'primary',
                    label: '修改',
                    formBtns: [
                      {
                        name: 'cancel',
                        type: 'dashed',
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
                          this.props.myFetch('updateZxCtEqContrSupplyForContractTab', obj._formData).then(
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
                      width: 150,
                      onClick: 'detail',
                      tooltip: 15,
                    }
                  },
                  {
                    table: {
                      title: '补充协议名称',
                      dataIndex: 'supplyName',
                      key: 'supplyName',
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
                      initialValue: '机械管理类补充协议'
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
                      width: 150,
                      tooltip: 15,
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
                      title: '含税合同金额',
                      dataIndex: 'contractCost',
                      key: 'contractCost',
                      width: 120,
                    }
                  },
                  {
                    table: {
                      title: '本期含税增减金额',
                      dataIndex: 'thisAmount',
                      key: 'thisAmount',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额',
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
                      title: '合同工期',
                      dataIndex: 'csTimeLimit',
                      key: 'csTimeLimit',
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
                    desc: '本页面所有金额单位为：万元',
                    content: {
                      formConfig: [
                        {
                          field: "apih5FlowStatus",
                          type: 'string',
                          hide: true,
                        },
                        {
                          field: "zxCtEqContrSupplyId",
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
                          field: "supplyName",
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
                          label: '含税合同金额',
                          field: "contractCost",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '不含税合同金额',
                          field: "contractCostNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '合同税额',
                          field: "contractCostTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期含税增减金额',
                          field: "thisAmount",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期不含税增减金额',
                          field: "thisAmountNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '本期增减税额',
                          field: "thisAmountTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后含税金额',
                          field: "alterContractSum",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后不含税金额',
                          field: "alterContractSumNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '请输入',
                          span: 12
                        },
                        {
                          label: '变更后税额',
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
                          initialValue: '0',
                          type: 'radio',
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
                        let contrAlterID = ''
                        if (obj.clickCb.rowInfo.name === "edit") {
                          contrAlterID = obj.initialValues.contrAlterID
                        } else if (obj.clickCb.rowInfo.name === "detail") {
                          contrAlterID = obj.initialValues.contrAlterID
                        }
                        return {
                          apiName: 'getZxCtEqContrAlterItemResoureList',
                          otherParams: { contrAlterID }
                        }
                      },
                      wrappedComponentRef: (me) => {
                        this.tableOne = me;
                      },
                      antd: {
                        size: "small",
                        rowKey: "zxCtFsSideAgreementInventoryId",
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
                            render: (data) => {
                              if (data === '1') {
                                return '新增';
                              } else if (data === '2') {
                                return '修改';
                              }
                            }
                          },
                          form: {
                            type: 'string',
                            field: 'alterType',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '编码',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 100,
                            type: 'string',
                            align: 'center',
                            fixed: 'left',
                          },
                          form: {
                            type: 'string',
                            field: 'catCode',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '设备名称',
                            width: 150,
                            type: 'string',
                            dataIndex: 'catName',
                            key: 'catName',
                            align: 'center',
                            fixed: 'left',
                          },
                          form: {
                            type: 'string',
                            field: 'catName',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '型号',
                            width: 150,
                            type: 'string',
                            dataIndex: 'spec',
                            key: 'spec',
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            field: 'spec',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '租赁单位',
                            dataIndex: 'rentUnit',
                            key: 'rentUnit',
                            width: 100,
                            type: 'string',
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            field: 'rentUnit',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '租赁开始时间',
                            dataIndex: 'actualStartDate',
                            key: 'actualStartDate',
                            width: 100,
                            type: 'string',
                            align: 'center',
                            format: 'YYYY-MM-DD',
                          },
                          form: {
                            type: 'date',
                            field: 'actualStartDate',
                            spanForm: 12,
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '租赁结束时间',
                            dataIndex: 'actualEndDate',
                            key: 'actualEndDate',
                            width: 100,
                            type: 'string',
                            align: 'center',
                            format: 'YYYY-MM-DD',
                          },
                          form: {
                            type: 'date',
                            field: 'actualEndDate',
                            spanForm: 12,
                            format: 'YYYY-MM-DD',
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
                          form: {
                            type: 'number',
                            field: 'qty',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '含税合同单价',
                            dataIndex: 'price',
                            key: 'price',
                            width: 120,
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'price',
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
                            field: 'contractSum',
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
                            field: 'priceNoTax',
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
                            field: 'contractSumNoTax',
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
                            field: 'contractSumTax',
                            spanForm: 12
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
                          form: {
                            type: 'number',
                            field: 'taxRate',
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
                            render: (data) => {
                              if (data === '1') {
                                return '是';
                              } else {
                                return '否';
                              }
                            }
                          },
                          form: {
                            field: 'isDeduct',
                            spanForm: 12,
                            type: 'radio',
                            optionData: [
                              { label: '是', value: '1' },
                              { label: '否', value: '0' }
                            ],
                            initialValue: '0'
                          }
                        },
                        {
                          table: {
                            title: '变更后租赁开始时间',
                            dataIndex: 'alterRentStartDate',
                            key: 'alterRentStartDate',
                            span: 12,
                            width: 150,
                            format: 'YYYY-MM-DD',
                          },
                          form: {
                            type: 'date',
                            field: 'alterRentStartDate',
                            spanForm: 12,
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '变更后租赁结束时间',
                            dataIndex: 'alterRentEndDate',
                            key: 'alterRentEndDate',
                            span: 12,
                            width: 150,
                            format: 'YYYY-MM-DD',
                          },
                          form: {
                            type: 'date',
                            field: 'alterRentEndDate',
                            spanForm: 12,
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '变更后数量',
                            dataIndex: 'alterTrrm',
                            key: 'alterTrrm',
                            width: 120,
                            align: 'center',
                            type: 'number',
                          },
                          form: {
                            type: 'number',
                            field: 'alterTrrm',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '变更后含税单价',
                            dataIndex: 'alterPrice',
                            key: 'alterPrice',
                            width: 120,
                            type: 'number',
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'alterPrice',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '变更后不含税单价',
                            dataIndex: 'alterPriceNoTax',
                            key: 'alterPriceNoTax',
                            width: 120,
                            type: 'number',
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'alterPriceNoTax',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '变更后含税金额',
                            dataIndex: 'alterAmt',
                            key: 'alterAmt',
                            width: 120,
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'alterAmt',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '变更后不含税金额',
                            dataIndex: 'alterAmtNoTax',
                            key: 'alterAmtNoTax',
                            width: 150,
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'alterAmtNoTax',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '变更后税额',
                            dataIndex: 'alterAmtTax',
                            key: 'alterAmtTax',
                            width: 150,
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            field: 'alterAmtTax',
                            spanForm: 12
                          }
                        },
                        {
                          table: {
                            title: '备注',
                            dataIndex: 'remark',
                            key: 'remark',
                            width: 150,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            field: 'remark',
                            spanForm: 12
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table5",
              name: "qnnTable",
              title: "保证金比例设置",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqCoContractAmtRateList',
                    otherParams: { contractID: obj.tableFns.form.getFieldValue('contractID') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtEqCoContractAmtRateId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtEqCoContractAmtRateId !== this.state.lineEdit
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
                      let tableFourData = this.tableFour.state.data ? this.tableFour.state.data : [];
                      if (tableFourData && tableFourData.length > 0) {
                        for (var i = 0; i < tableFourData.length; i++) {
                          if (tableFourData[i].zxCtEqCoContractAmtRateId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtEqCoContractAmtRateId = Date.parse(new Date())
                      tableFourData.push(newRowData)
                      this.tableFour.btnCallbackFn.setState({
                        data: tableFourData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtEqCoContractAmtRateId
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
                          let data = this.tableFour.state ? this.tableFour.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqCoContractAmtRateId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            } else if (data[i].allowDelete !== '1') {
                              Msg.warn('存在被引用数据，无法删除')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtEqCoContractAmtRate', deleteData).then(
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
                      field: 'zxCtEqCoContractAmtRateId',
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
                      placeholder: '请输入',
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
                        if (rowData.zxCtEqCoContractAmtRateId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtEqCoContractAmtRateId) {
                          Toast.loading('loading...');
                          obj.btnCallbackFn.getEditedRowData().then((data) => {
                            if (!data.statisticsName) {//
                              Msg.warn('请填写保证金！')
                              return
                            }
                            let body = {
                              ...obj.rowData,
                              ...data,
                            };
                            body.contractID = obj.props.tableFns.form.getFieldValue('contractID')
                            this.props.myFetch(!isNaN(obj.rowData.zxCtEqCoContractAmtRateId) ? 'addZxCtEqCoContractAmtRate' : 'updateZxCtEqCoContractAmtRate', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Toast.hide()
                                  Msg.success(message);
                                  this.setState({
                                    lineEdit: ''
                                  })
                                  obj.btnCallbackFn.refresh();
                                } else {
                                  Toast.hide()
                                  Msg.error(message);
                                }
                              }
                            );
                          })
                        } else {
                          let data = this.tableFour.state ? this.tableFour.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqCoContractAmtRateId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtEqCoContractAmtRateId
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
              hide: (obj) => {
                let contractCategory = obj.form.getFieldValue('contractCategory')
                if (contractCategory !== '0') {
                  return true
                } else {
                  return false
                }
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaEquipSettleAuditList',
                    otherParams: {
                      contractID: obj.tableFns.form.getFieldValue('contractID'),
                      apih5FlowStatus:'2',
                      orgID: departmentId,
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
                      width: 180,
                      tooltip: 22,
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
                      width: 170,
                      tooltip: 17,
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
                  width: '1200px'
                },
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "信息登记",
                    content: {
                      formConfig: [
                        {
                          hide: true,
                          field: 'zxSaEquipSettleAuditId',
                        },
                        {
                          field: 'zxSaEquipResSettleId',
                          type: 'string',
                          hide: true,
                        },
                        {
                          field: 'zxSaEquipPaySettleId',
                          type: 'string',
                          hide: true
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
                          field: "signedNos",
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
                          initialValue: '0',
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
                          field: "autoNum",
                          span: 12,
                          placeholder: '请输入',
                          type: 'number',
                        },
                        {
                          label: '填报人',
                          field: "reportPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '请输入',
                        },
                        {
                          label: '计算人',
                          field: "countPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '请输入',
                        },
                        {
                          label: '复核人',
                          field: "reCountPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '请输入',
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
                          label: '机械性能及运转情况评价',
                          field: 'appraisal',
                          type: 'textarea',
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
                          field: 'remark',
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
                          apiName: 'getZxSaEquipSettleAuditDetail',
                          otherParams: (obj) => {
                            return {
                              zxSaEquipSettleAuditId: obj.form.getFieldValue('zxSaEquipSettleAuditId')
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
                          label: '变更后含税合同金额(万元)',
                          field: "changeAmt",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期清单结算含税金额(元)',
                          field: "thisAmtRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期清单结算不含税金额(元)',
                          field: "thisAmtNoTaxRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '本期清单结算税额(元)',
                          field: "thisAmtTaxRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '累计清单结算含税金额(元)',
                          field: "totalAmtRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          type: 'qnnTable',
                          field: 'table1',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '1') {
                                return {
                                  apiName: 'getZxSaEquipResSettleItemList',
                                  otherParams: { zxSaEquipResSettleId: obj.qnnFormProps.form.getFieldValue('zxSaEquipResSettleId') },
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaEquipResSettleItemId',
                              size: 'small'
                            },
                            paginationConfig: {
                              position: 'bottom'
                            },
                            isShowRowSelect: false,
                            formConfig: [
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'zxSaEquipResSettleItemId',
                                }
                              },
                              {
                                table: {
                                  title: '编号',
                                  dataIndex: 'equipCode',
                                  key: 'equipCode',
                                  align: 'center',
                                  fixed: 'left',
                                  tooltip: 15,
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '设备名称',
                                  dataIndex: 'equipName',
                                  key: 'equipName',
                                  align: 'center',
                                  fixed: 'left',
                                  tooltip: 15,
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '型号',
                                  dataIndex: 'spec',
                                  key: 'spec',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '单位',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '起租月份',
                                  dataIndex: 'startDate',
                                  key: 'startDate',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '单价(元)',
                                  dataIndex: 'contractPrice',
                                  key: 'contractPrice',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '合同数量',
                                  dataIndex: 'contractQty',
                                  key: 'contractQty',
                                  width: 100,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '合同含税金额(元)',
                                  dataIndex: 'contractAmt',
                                  key: 'contractAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '变更后含税金额(元)',
                                  dataIndex: 'changedAmt',
                                  key: 'changedAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '变更后数量',
                                  dataIndex: 'changedQty',
                                  key: 'changedQty',
                                  width: 120,
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
                                }
                              },
                              {
                                table: {
                                  title: '本期运转台时(小时)',
                                  dataIndex: 'runHour',
                                  key: 'runHour',
                                  width: 150,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '本期油耗(L)',
                                  dataIndex: 'useOil',
                                  key: 'useOil',
                                  width: 120,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '本期结算含税金额(元)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '本期结算不含税金额(元)',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '本期结算税额(元)',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 180,
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
                                }
                              },
                              {
                                table: {
                                  title: '至上期末累计结算含税金额(元)',
                                  dataIndex: 'upAmt',
                                  key: 'upAmt',
                                  width: 250,
                                  align: 'center',
                                }
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
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '备注',
                                  dataIndex: 'remark',
                                  key: 'remark',
                                  width: 120,
                                  align: 'center',
                                  tooltip: 15,
                                }
                              }
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
                          apiName: 'getZxSaEquipSettleAuditDetail',
                          otherParams: (obj) => {
                            return {
                              zxSaEquipSettleAuditId: obj.form.getFieldValue('zxSaEquipSettleAuditId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          label: '本期支付项结算含税金额(元)',
                          field: "thisAmtPay",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期支付项结算不含税金额(元)',
                          field: "thisAmtNoTaxPay",
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
                          field: "thisAmtTaxPay",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '累计支付项结算金额(元)',
                          field: "totalAmtPay",
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
                          label: '本期进退场费',
                          field: "inOutAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期奖罚',
                          field: "fineAmt",
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
                          label: '本期伙食费',
                          field: "foodAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '本期其他款项',
                          field: "otherAmt",
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
                          field: 'table1',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2') {
                                return {
                                  apiName: 'getZxSaEquipPaySettleItemList',
                                  otherParams: { zxSaEquipPaySettleId: obj.qnnFormProps.form.getFieldValue('zxSaEquipPaySettleId') },
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaEquipPaySettleItemId',
                              size: 'small'
                            },
                            paginationConfig: {
                              position: 'bottom'
                            },
                            isShowRowSelect: false,
                            formConfig: [
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'zxSaEquipPaySettleItemId',
                                  hide: true
                                }
                              },
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
                                }
                              },
                              {
                                table: {
                                  title: '单价(元)',
                                  dataIndex: 'price',
                                  key: 'price',
                                  width: 120
                                }
                              },
                              {
                                table: {
                                  title: '税率(%)',
                                  dataIndex: 'taxRate',
                                  key: 'taxRate',
                                  width: 100
                                }
                              },
                              {
                                table: {
                                  title: '本期结算金额(元)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 150,
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
                                  title: '本期结算税额(元)',
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
                                }
                              }
                            ],
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "table1",
                    name: "qnnForm",
                    title: "统计项",
                    content: {
                      formConfig: [
                        {
                          type: 'qnnTable',
                          field: 'table1',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '3') {
                                return {
                                  apiName: 'getZxSaEquipSettleAuditItemList',
                                  otherParams: () => {
                                    return {
                                      zxSaEquipSettleAuditId: obj.qnnFormProps.form.getFieldValue('zxSaEquipSettleAuditId')
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
                      label: '合同编号',
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
                      label: '合同名称',
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
                      field: "secondID",
                      type: 'selectByQnnTable',
                      optionConfig: {
                        label: 'customerName',
                        value: 'zxCrCustomerNewId',
                      },
                      qnnTableConfig: {
                        antd: { rowKey: "zxCrCustomerNewId" },
                        fetchConfig: { apiName: "getZxCrCustomerNewList" },
                        searchBtnsStyle: "inline",
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
                      field: 'secondOrgType',
                      type: 'string',
                      label: '协作单位类型',
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
                      label: '合同类别',
                      field: "contractCategory",
                      type: 'select',
                      optionConfig: {
                        label: 'label',
                        value: 'value'
                      },
                      optionData: [
                        { label: '设备租赁', value: '0' },
                        { label: '设备采购', value: '1' },
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
                      label: '合同类型',
                      field: "pp3",
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
                      field: 'remark',
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
                            apiName: 'getZxCtEqContrItemList',
                            otherParams: {
                              zxCtEqContrApplyId: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.zxCtEqContrApplyId
                            }
                          }
                        },
                        antd: {
                          size: "small",
                          rowKey: 'zxCtEqContrItemId'
                        },
                        paginationConfig: {
                          position: 'bottom'
                        },
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
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
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
                              if (rowData.contractCategory === '1') {
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
                              if (rowData.contractCategory === '0') {
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
                              if (rowData.contractCategory === '1') {
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
                              if (rowData.contractCategory === '0') {
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
                              if (rowData.contractCategory === '1') {
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
                              if (rowData.contractCategory === '1') {
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
                              if (rowData.contractCategory === '0') {
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
                              if (rowData.contractCategory === '0') {
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
                              render: (h) => {
                                if (h === '1') {
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