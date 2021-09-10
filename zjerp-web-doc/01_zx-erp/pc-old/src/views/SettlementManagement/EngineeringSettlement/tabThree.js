import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
const config = {
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
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          {...config}
          fetchConfig={() => {
            if (params.paramsData.tabIndex === '2') {
              return {
                apiName: 'getZxSaProjectSettleAuditDetail',
                otherParams: { id: params.paramsData.id }
              }
            } else {
              return {}
            }
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formConfig={[
            {
              field: "projectPaySettleId",
              type: 'string',
              hide: true
            },
            {
              label: '本期支付项结算含税金额(元)',
              field: "payThisAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期支付项结算不含税金额(元)',
              field: "payThisAmtNoTax",
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
              label: '累计支付项结算金额(元)',
              field: "payTotalAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '物资调拨费用本期结算小计(元)',
              field: "payMaterialAmt",
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
              label: '机械调拨费用本期结算小计(元)',
              field: "payMachineAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '临时用工费本期结算小计(元)',
              field: "payTempAmt",
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
              label: '奖罚金额本期结算小计(元)',
              field: "payFineAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '补偿金额本期结算小计(元)',
              field: "payRecoupAmt",
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
              label: '其他款项本期结算小计(元)',
              field: "payOtherAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12,
            },
            {
              type: 'qnnTable',
              field: 'paySettleItemList',
              incToForm: true,
              disabled: () => {
                if (params.paramsData.clickName === 'detail' || params.paramsData.billType === '2') {
                  return true
                } else {
                  return false
                }
              },
              qnnTableConfig: {
                antd: {
                  rowKey: 'projectPaySettleItemId',
                  size: 'small'
                },
                tableTdEdit: true,
                paginationConfig: {
                  position: 'bottom'
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                tableTdEditSaveCb: (obj) => {
                  if (!obj.taxRate) {
                    Msg.error('请填写税率');
                    this.tableOne.qnnSetState({ editRowId: obj.projectPaySettleItemId })
                    return
                  }
                  if (!obj.payName) {
                    Msg.error('请填写名称');
                    this.tableOne.qnnSetState({ editRowId: obj.projectPaySettleItemId })
                    return
                  }
                  if (!obj.qty) {
                    Msg.error('请填写数量');
                    this.tableOne.qnnSetState({ editRowId: obj.projectPaySettleItemId })
                    return
                  }
                  if (!obj.price) {
                    Msg.error('请填写含税单价');
                    this.tableOne.qnnSetState({ editRowId: obj.projectPaySettleItemId })
                    return
                  }
                  Toast.loading('loading...');
                  let idLength = obj.projectPaySettleItemId.length
                  let rowData = this.table.form.getFieldsValue()
                  let params = { ...obj }
                  params.projectPaySettleId = rowData?.projectPaySettleId
                  this.props.myFetch(idLength < 18 ? 'addZxSaProjectPaySettleItem' : 'updateZxSaProjectPaySettleItem', params).then(
                    ({ success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.table.refresh()
                        Toast.hide()
                      } else {
                        Toast.hide()
                        this.tableOne.qnnSetState({ editRowId: obj.projectPaySettleItemId })
                        Msg.error(message);
                      }
                    }
                  );
                },
                actionBtns: [
                  {
                    name: "addRow",
                    icon: "plus",
                    type: "primary",
                    label: "新增行",
                  },
                  {
                    name: "del",
                    icon: 'delete',
                    type: 'danger',
                    label: "删除",
                    fetchConfig: {
                      apiName: 'batchDeleteUpdateZxSaProjectPaySettleItem',
                    },
                  }
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'projectPaySettleItemId',
                      hide: true
                    }
                  },
                  {
                    table: {
                      title: '支付项类型',
                      dataIndex: 'payType',
                      key: 'payType',
                      tdEdit: false,
                      fixed: 'left',
                      width: 120
                    },
                  },
                  {
                    table: {
                      title: `<div>名称<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'payName',
                      key: 'payName',
                      tdEdit: true,
                      fixed: 'left',
                      width: 150,
                      tooltip: 15,
                    },
                    form: {
                      allowClear: false,//是否显示清除按钮
                      type: 'select',
                      field: 'payID',
                      showSearch: true,
                      placeholder: '请选择',
                      optionConfig: {
                        label: 'workName',
                        value: 'id',
                      },
                      fetchConfig: {
                        apiName: 'getZxSaProjectSetItemList',
                        otherParams: {
                          orgID: params.paramsData.orgID,
                          contrType: 'enginner'
                        }
                      },
                      onChange: (val, obj) => {
                        this.tableOne.getEditedRowData(false).then((data) => {
                          data.payName = obj.itemData.workName
                          data.payType = obj.itemData.workType
                          data.payID = val
                          data.unit = obj.itemData.unit
                          data.qty = null
                          data.price = null
                          data.taxRate = null
                          data.thisAmt = 0
                          data.thisAmtNoTax = 0
                          data.thisAmtTax = 0
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      },
                    },
                  },
                  {
                    table: {
                      title: '单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      tdEdit: false
                    },
                  },
                  {
                    table: {
                      title: `<div>数量<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'qty',
                      key: 'qty',
                      tdEdit: true
                    },
                    form: {
                      precision: 3,
                      field: 'qty',
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          data.qty = Number(val)     //本期结算数量 
                          data.thisAmt = (data.price ? data.price : 0) * Number(val)  //本期结算金额
                          if (data.taxRate) {
                            data.thisAmtNoTax = ((data.price ? data.price : 0) * Number(val)) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100)  //本期结算不含税金额
                            data.thisAmtTax = (data.price ? data.price : 0) * Number(val) - ((data.price ? data.price : 0) * Number(val)) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100)//本期结算税额
                          }
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      },
                    },
                  },
                  {
                    table: {
                      title: `<div>含税单价(元)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'price',
                      key: 'price',
                      tdEdit: true,
                      width: 120
                    },
                    form: {
                      field: 'price',
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          data.price = Number(val)     //本期结算数量 
                          data.thisAmt = (data.qty ? data.qty : 0) * Number(val)  //本期结算含税金额
                          if (data.taxRate) {
                            data.thisAmtNoTax = ((data.qty ? data.qty : 0) * Number(val)) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100) //本期结算不含税金额
                            data.thisAmtTax = (data.qty ? data.qty : 0) * Number(val) - ((data.qty ? data.qty : 0) * Number(val)) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100)//本期结算税额
                          }
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      tdEdit: true,
                      width: 120
                    },
                    form: {
                      field: 'taxRate',
                      type: 'select',
                      allowClear: false,
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          data.taxRate = val       //税率
                          data.thisAmtNoTax = ((data.qty ? data.qty : 0) * (data.price ? data.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                          data.thisAmtTax = (data.qty ? data.qty : 0) * (data.price ? data.price : 0) - ((data.qty ? data.qty : 0) * (data.price ? data.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: '含税金额(元)',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      tdEdit: false,
                      width: 170,
                      render: (val, rowData) => {
                        return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '不含税金额(元)',
                      dataIndex: 'thisAmtNoTax',
                      key: 'thisAmtNoTax',
                      tdEdit: false,
                      width: 170,
                      render: (val, rowData) => {
                        return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '税额(元)',
                      dataIndex: 'thisAmtTax',
                      key: 'thisAmtTax',
                      tdEdit: false,
                      width: 140,
                      render: (val, rowData) => {
                        return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remark',
                      key: 'remark',
                      tdEdit: true,
                      width: 120,
                      tooltip: 15,
                    },
                    form: {
                      field: 'remark',
                      type: 'string',
                      placeholder: '请输入',
                    },
                  },
                ],
              },
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
