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
  isNumber(obj) {
    var t1 = /^\d+(\.\d+)?$/; //非负浮点数
    var t2 = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if (t1.test(obj) || t2.test(obj)) {
      return true;
    } else {
      return false;
    }

  }
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
                apiName: 'getZxCtSuppliesShopSettlementListDetail',
                otherParams: { zxCtSuppliesShopSettlementId: params.paramsData.zxCtSuppliesShopSettlementId }
              }
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
              field: "thisAmtByPay",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期支付项结算不含税金额(元)',
              field: "thisAmtNoTaxByPay",
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
              field: "thisAmtTaxByPay",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计支付项结算金额(元)',
              field: "totalAmtByPay",
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
              label: '本期运杂费(元)',
              field: "transportAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期垫资费(元)',
              field: "padTariffAmt",
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
              label: '本期奖罚金(元)',
              field: "fineAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期其他款项(元)',
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
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '2') {
                    return {
                      apiName: 'getZxCtSuppliesShopPaySettlementItemListByContID',
                      otherParams: {
                        mainID: params.paramsData.zxCtSuppliesShopSettlementId
                      }
                    }
                  } else {
                    return {}
                  }
                },
                antd: {
                  rowKey: 'zxCtSuppliesShopPaySettlementItemId',
                  size: 'small'
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtSuppliesShopPaySettlementItemId !== this.table.form.getFieldValue('editLineName')
                },
                paginationConfig: {
                  position: 'bottom'
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                actionBtns: [
                  {
                    name: 'diyaddRow',//内置add del
                    icon: 'plus',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '新增行',
                    field: "add",
                    hide: () => {
                      if (params.paramsData.clickName === 'detail' || params.paramsData.billType === '2') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      console.log(this.tableOne);
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtSuppliesShopPaySettlementItemId = new Date().getTime().toString()
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.table.form.setFieldsValue({
                        editLineName: newRowData.zxCtSuppliesShopPaySettlementItemId
                      })
                    },
                  },
                  {
                    name: 'diydel',//内置add del
                    icon: 'del',//icon
                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                    label: '删除',
                    field: "del",
                    hide: () => {
                      if (params.paramsData.clickName === 'detail' || params.paramsData.billType === '2') {
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
                            if (data[i].zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          for (var i = 0; i < deleteData.length; i++) {
                            deleteData[i].zxCtSuppliesShopSettlementId = params.paramsData.zxCtSuppliesShopSettlementId
                          }
                          this.props.myFetch('batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.refresh();
                                this.table.refresh()
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
                      label: '主键id',
                      field: 'zxCtSuppliesShopPaySettlementItemId',
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
                          contrType: 'mater'
                        }
                      },
                      onChange: (val, obj) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.payName = obj.itemData.workName
                            rowData.payType = obj.itemData.workType
                            rowData.payID = obj.itemData.id
                            rowData.unit = obj.itemData.unit
                            rowData.qty = 0
                            rowData.price = 0
                            rowData.taxRate = 0
                            rowData.thisAmt = 0
                            rowData.thisAmtNoTax = 0
                            rowData.thisAmtTax = 0
                          }
                          return rowData;
                        })
                        console.log(tableOneData);
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
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
                        let pageData = this.table.form.getFieldsValue()
                        let thisAmt = 0      //本期支付项结算含税金额
                        let thisAmtNoTax = 0  //本期支付项结算不含税金额
                        let thisAmtTax = 0  //本期支付项结算税额
                        let transportAmt = 0 //本期运杂费
                        let fineAmt = 0  //本期奖罚金
                        let padTariffAmt = 0 //本期垫支费
                        let otherAmt = 0 //本期其他款项
                        let upAmt = pageData.totalAmtByPay - pageData.thisAmtByPay //至上期末累计结算金额
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.qty = Number(val)     //本期结算数量  
                            rowData.thisAmt = (rowData.price ? rowData.price : 0) * Number(val)  //本期结算金额
                            if (rowData.taxRate) {
                              rowData.thisAmtNoTax = ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //本期结算不含税金额
                              rowData.thisAmtTax = (rowData.price ? rowData.price : 0) * Number(val) - ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)//本期结算税额
                            }
                          }
                          thisAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          thisAmtNoTax += rowData?.thisAmtNoTax ? rowData.thisAmtNoTax : 0
                          thisAmtTax += rowData?.thisAmtTax ? rowData.thisAmtTax : 0
                          upAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          if (rowData?.payType === '运杂费') {
                            transportAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '奖罚金') {
                            fineAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '垫资费') {
                            padTariffAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '其他款项') {
                            otherAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          }
                          return rowData;
                        })
                        if (!tableOneData.filter(item => item === null).length) {
                          this.table.form.setFieldsValue({
                            thisAmtByPay: Math.round(thisAmt * 100) / 100,
                            thisAmtNoTaxByPay: Math.round(thisAmtNoTax * 100) / 100,
                            thisAmtTaxByPay: Math.round(thisAmtTax * 100) / 100,
                            totalAmtByPay: Math.round((upAmt) * 100) / 100,
                            transportAmt: Math.round(transportAmt * 100) / 100,
                            fineAmt: Math.round(fineAmt * 100) / 100,
                            padTariffAmt: Math.round(padTariffAmt * 100) / 100,
                            otherAmt: Math.round(otherAmt * 100) / 100,
                          })
                          setTimeout(() => {
                            this.tableOne.btnCallbackFn.setState({
                              data: tableOneData
                            })
                          }, 0)
                        }
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
                        let pageData = this.table.form.getFieldsValue()
                        let val = obj.target.value;
                        let thisAmt = 0      //本期支付项结算含税金额
                        let thisAmtNoTax = 0  //本期支付项结算不含税金额
                        let thisAmtTax = 0  //本期支付项结算税额
                        let transportAmt = 0 //本期运杂费
                        let fineAmt = 0  //本期奖罚金
                        let padTariffAmt = 0 //本期垫支费
                        let otherAmt = 0 //本期其他款项
                        let upAmt = pageData.totalAmtByPay - pageData.thisAmtByPay //至上期末累计结算金额
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.price = Number(val)     //本期结算数量  
                            rowData.thisAmt = (rowData.qty ? rowData.qty : 0) * val  //本期结算含税金额
                            if (rowData.taxRate) {
                              rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * val) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //本期结算不含税金额
                              rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * val - ((rowData.qty ? rowData.qty : 0) * val) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)//本期结算税额
                            }
                          }
                          thisAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          thisAmtNoTax += rowData?.thisAmtNoTax ? rowData.thisAmtNoTax : 0
                          thisAmtTax += rowData?.thisAmtTax ? rowData.thisAmtTax : 0
                          upAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          if (rowData?.payType === '运杂费') {
                            transportAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '奖罚金') {
                            fineAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '垫资费') {
                            padTariffAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          } else if (rowData?.payType === '其他款项') {
                            otherAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                          }
                          return rowData;
                        })
                        if (!tableOneData.filter(item => item === null).length) {
                          this.table.form.setFieldsValue({
                            thisAmtByPay: Math.round(thisAmt * 100) / 100,
                            thisAmtNoTaxByPay: Math.round(thisAmtNoTax * 100) / 100,
                            thisAmtTaxByPay: Math.round(thisAmtTax * 100) / 100,
                            totalAmtByPay: Math.round((upAmt) * 100) / 100,
                            transportAmt: Math.round(transportAmt * 100) / 100,
                            fineAmt: Math.round(fineAmt * 100) / 100,
                            padTariffAmt: Math.round(padTariffAmt * 100) / 100,
                            otherAmt: Math.round(otherAmt * 100) / 100,
                          })
                          setTimeout(() => {
                            this.tableOne.btnCallbackFn.setState({
                              data: tableOneData
                            })
                          }, 0)
                        }
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
                      onChange: (val) => {
                        let thisAmtNoTax = 0  //本期支付项结算不含税金额
                        let thisAmtTax = 0  //本期支付项结算税额
                        let tableData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.taxRate = val       //税率
                            rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                            rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                          }
                          thisAmtNoTax += rowData?.thisAmtNoTax ? rowData.thisAmtNoTax : 0
                          thisAmtTax += rowData?.thisAmtTax ? rowData.thisAmtTax : 0
                          return rowData;
                        })
                        this.table.form.setFieldsValue({
                          thisAmtNoTaxByPay: Math.round(thisAmtNoTax * 100) / 100,
                          thisAmtTaxByPay: Math.round(thisAmtTax * 100) / 100,
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableData
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
                    }
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
                    }
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
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                      width: 120,
                      tooltip: 15,
                    },
                    form: {
                      field: 'remarks',
                      type: 'string',
                      placeholder: '请输入',
                      onBlur: (e) => {
                        let val = e.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.remarks = val
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
                    },
                  },
                  {
                    isInTable: () => {
                      if (params.paramsData.clickName === 'detail' || params.paramsData.billType === '2') {
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
                        if (rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (obj.rowData.zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                          console.log(obj);
                          if (!obj.rowData.payName) {
                            Msg.warn("请选择名称！")
                            return
                          } else if (!obj.rowData.qty) {
                            Msg.warn("请填写数量！")
                            return
                          } else if (!obj.rowData.price) {
                            Msg.warn("请填写单价！")
                            return
                          } else if (!obj.rowData.taxRate) {
                            Msg.warn("请选择税率！")
                            return
                          }
                          Toast.loading('loading...');
                          setTimeout(() => {
                            let body = {
                              ...obj.rowData
                            };
                            body.zxCtSuppliesShopSettlementId = params.paramsData.zxCtSuppliesShopSettlementId;
                            this.props.myFetch(this.isNumber(obj.rowData.zxCtSuppliesShopPaySettlementItemId) ? 'addZxCtSuppliesShopPaySettlementItemByPayId' : 'updateZxCtSuppliesShopPaySettlementItemByPayId', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  this.table.form.setFieldsValue({
                                    editLineName: ''
                                  })
                                  obj.btnCallbackFn.refresh();
                                  this.table.refresh()
                                  Toast.hide()
                                } else {
                                  Toast.hide()
                                  Msg.error(message);
                                }
                              }
                            );
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtSuppliesShopPaySettlementItemId === this.table.form.getFieldValue('editLineName')) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.table.form.setFieldsValue({
                            editLineName: obj.rowData.zxCtSuppliesShopPaySettlementItemId
                          })
                          obj.btnCallbackFn.refresh();
                        }
                      }
                    }
                  }
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
