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
                apiName: 'getZxSaEquipSettleAuditDetail',
                otherParams: { zxSaEquipSettleAuditId: params.paramsData.zxSaEquipSettleAuditId }
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
              field: "editLineName",
              type: 'string',
              hide: true
            },
            {
              label: '本期支付项结算含税金额(元)',
              field: "thisAmtPay",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期支付项结算不含税金额(元)',
              field: "thisAmtNoTaxPay",
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
              label: '本期支付项结算税额(元)',
              field: "thisAmtTaxPay",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计支付项结算金额(元)',
              field: "totalAmtPay",
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
              label: '本期进退场费',
              field: "inOutAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期奖罚',
              field: "fineAmt",
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
              label: '本期伙食费',
              field: "foodAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期其他款项',
              field: "otherAmt",
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
              type: 'qnnTable',
              field: 'zxSaEquipPaySettleItemList',
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
                  rowKey: 'zxSaEquipPaySettleItemId',
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
                    this.tableOne.qnnSetState({ editRowId: obj.zxSaEquipPaySettleItemId })
                    return
                  }
                  if (!obj.payName) {
                    Msg.error('请填写名称');
                    this.tableOne.qnnSetState({ editRowId: obj.zxSaEquipPaySettleItemId })
                    return
                  }
                  if (!obj.qty) {
                    Msg.error('请填写数量');
                    this.tableOne.qnnSetState({ editRowId: obj.zxSaEquipPaySettleItemId })
                    return
                  }
                  if (!obj.price) {
                    Msg.error('请填写单价');
                    this.tableOne.qnnSetState({ editRowId: obj.zxSaEquipPaySettleItemId })
                    return
                  }
                  Toast.loading('loading...');
                  let idLength = obj.zxSaEquipPaySettleItemId.length
                  let headersData = { ...obj }
                  headersData.zxSaEquipPaySettleId = params.paramsData.zxSaEquipPaySettleId
                  headersData.period = params.paramsData.period
                  headersData.billNo = params.paramsData.billNo
                  headersData.contractID = params.paramsData.contractID
                  this.props.myFetch(idLength < 18 ? 'addZxSaEquipPaySettleItem' : 'updateZxSaEquipPaySettleItem', headersData).then(
                    ({ success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.table.refresh()
                        Toast.hide()
                      } else {
                        Toast.hide()
                        this.tableOne.qnnSetState({ editRowId: obj.zxSaEquipPaySettleItemId })
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
                      apiName: 'batchDeleteUpdateZxSaEquipPaySettleItem',
                    }
                  }
                ],
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
                    isInTable: false,
                    form: {
                      field: 'payNo',
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
                          contrType: 'P6'
                        }
                      },
                      onChange: (val, obj) => {
                        this.tableOne.getEditedRowData(false).then((data) => {
                          data.payName = obj.itemData.workName
                          data.payType = obj.itemData.workType
                          data.payID = val
                          data.payNo = obj.itemData.workNo
                          data.unit = obj.itemData.unit
                          data.qty = null
                          data.price = null
                          data.taxRate = null
                          data.thisAmt = 0
                          data.thisAmtNoTax = 0
                          data.thisAmtTax = 0
                          console.log(data);
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
                        if (val < 0) {
                          Msg.warn('数量必须大于0')
                          return
                        }
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
                      title: `<div>单价(元)<span style='color: #ff4d4f'>*</span></div>`,
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
                          data.thisAmt = (data.qty ? data.qty : 0) * val  //本期结算含税金额
                          if (data.taxRate) {
                            data.thisAmtNoTax = ((data.qty ? data.qty : 0) * val) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100) //本期结算不含税金额
                            data.thisAmtTax = (data.qty ? data.qty : 0) * val - ((data.qty ? data.qty : 0) * val) / (1 + (data.taxRate ? Number(data.taxRate) : 0) / 100)//本期结算税额
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
                      title: '本期结算金额(元)',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      tdEdit: false,
                      width: 150,
                      render: (val, rowData) => {
                        return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                      }
                    },
                    form: { type: 'number' }
                  },

                  {
                    table: {
                      title: '本期结算不含税金额(元)',
                      dataIndex: 'thisAmtNoTax',
                      key: 'thisAmtNoTax',
                      tdEdit: false,
                      width: 170,
                      render: (val, rowData) => {
                        return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
                      }
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '本期结算税额(元)',
                      dataIndex: 'thisAmtTax',
                      key: 'thisAmtTax',
                      tdEdit: false,
                      width: 140,
                      render: (val, rowData) => {
                        return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                      }
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remark',
                      key: 'remark',
                      tdEdit: true,
                      width: 120,
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
