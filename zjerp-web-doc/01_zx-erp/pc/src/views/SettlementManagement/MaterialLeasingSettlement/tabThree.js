import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from 'antd';
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
const tableConfig = {
  antd: {
    rowKey: 'zxCtSuppliesLeasePaySettlementItemId',
    size: 'small'
  },
  paginationConfig: {
    position: 'bottom'
  },
}
class index extends Component {
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          fetch={this.props.myFetch}
          {...config}
          fetchConfig={() => {
            if (params.paramsData.tabIndex === '2') {
              return {
                apiName: 'getZxCtSuppliesLeaseSettlementListDetail',
                otherParams: { zxCtSuppliesLeaseSettlementListId: params.paramsData.zxCtSuppliesLeaseSettlementListId }
              }
            }
            return {}
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formConfig={[
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
              label: '本期支付项结算税额(元)',
              field: "payThisAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计支付项结算金额(元)',
              field: "payTotalAmt",
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
              field: "payInOutAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期耗损费(元)',
              field: "payFoodAmt",
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
              field: "payFineAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期其他款项(元)',
              field: "payOtherAmt",
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
                ...tableConfig,
                tableTdEdit: params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' ? false : true,
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '2') {
                    return {
                      apiName: 'getZxCtSuppliesLeasePaySettlementItemListByContID',
                      otherParams: { mainID: params.paramsData.zxCtSuppliesLeaseSettlementListId }
                    }
                  } else { return {} }
                },
                tableTdEditFetchConfig: async (obj) => {
                  let body = await obj.qnnTableInstance.getEditedRowData()
                  body.zxCtSuppliesLeaseSettlementListId = params.paramsData.zxCtSuppliesLeaseSettlementListId;
                  return {
                    apiName: body.addFlag === '1' ? 'addZxCtSuppliesLeasePaySettlementItemByPayId' : 'updateZxCtSuppliesLeasePaySettlementItemByPayId',
                    otherParams: { ...body },
                    success: ({ success, message }) => {
                      if (success) {
                        Msg.success(message)
                      } else {
                        Msg.error(message)
                      }
                    }
                  }
                },
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    field: "addRow",
                    addRowDefaultData: { addFlag: '1' },
                    hide: () => {
                      if (params.paramsData.clickName === 'detail' ||params.paramsData.billType === '2') {
                        return true;
                      } else {
                        return false;
                      }
                    }
                  },
                  {
                    name: 'del',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    field: "del",
                    isRefreshTable: true,
                    hide: () => {
                      if (params.paramsData.clickName === 'detail' ||params.paramsData.billType === '2') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    fetchConfig: {
                      apiName: 'batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId',
                      otherParams: (obj) => {
                        let deleteData = obj.qnnTableInstance.getSelectedRows()
                        for (var i = 0; i < deleteData.length; i++) {
                          deleteData[i].zxCtSuppliesLeaseSettlementListId = params.paramsData.zxCtSuppliesLeaseSettlementListId
                        }
                        return { deleteData }
                      }
                    }
                  }
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxCtSuppliesLeasePaySettlementItemId',
                      hide: true
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'payName',
                      type: 'string',
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
                      title: '名称',
                      dataIndex: 'payName',
                      key: 'payName',
                      tdEdit: true,
                      fixed: 'left',
                      width: 150,
                    },
                    form: {
                      field:'payID',
                      required: true,
                      allowClear: false,//是否显示清除按钮
                      type: 'select',
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
                          contrType: 'P5'
                        }
                      },
                      onChange: (val, obj) => {
                        let tableOneData = obj.rowData
                        tableOneData.payName = obj.itemdata.workName
                        tableOneData.payType = obj.itemdata.workType
                        tableOneData.payID = obj.itemdata.id
                        tableOneData.unit = obj.itemdata.unit
                        tableOneData.qty = 0
                        tableOneData.price = 0
                        tableOneData.taxRate = 0
                        tableOneData.thisAmt = 0
                        tableOneData.thisAmtNoTax = 0
                        tableOneData.thisAmtTax = 0
                        obj.qnnTableInstance.setEditedRowData(tableOneData)
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
                      title: '数量',
                      dataIndex: 'qty',
                      key: 'qty',
                      tdEdit: true
                    },
                    form: {
                      required: true,
                      precision: 3,
                      field: 'qty',
                      type: 'number',
                      placeholder: '请输入',
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        if (val < 0) {
                          Msg.warn('数量必须大于0')
                          return
                        }
                        rowData.thisAmt = (rowData.price ? rowData.price : 0) * Number(val)  //本期结算金额
                        if (rowData.taxRate && rowData.taxRate !== '0') {
                          rowData.thisAmtNoTax = ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)  //本期结算不含税金额
                          rowData.thisAmtTax = (rowData.price ? rowData.price : 0) * Number(val) - ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      }
                    }
                  },
                  {
                    table: {
                      title: '单价(元)',
                      dataIndex: 'price',
                      key: 'price',
                      tdEdit: true,
                      width: 120
                    },
                    form: {
                      required: true,
                      field: 'price',
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        rowData.thisAmt = (rowData.qty ? rowData.qty : 0) * Number(val)  //本期结算含税金额
                        if (rowData.taxRate && rowData.taxRate !== '0') {
                          rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100) //本期结算不含税金额
                          rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * Number(val) - ((rowData.qty ? rowData.qty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      }
                    }
                  },
                  {
                    table: {
                      title: '税率(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      tdEdit: true,
                      width: 120
                    },
                    form: {
                      required: true,
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
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                        rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      },
                    }
                  },
                  {
                    table: {
                      title: '本期结算金额(元)',
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
                    },
                    form: {
                      field: 'remarks',
                      type: 'string',
                      placeholder: '请输入',
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
