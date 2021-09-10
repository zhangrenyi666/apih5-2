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
    rowKey: 'zxSaFsPaySettlementDetailId',
    size: 'small'
  },
  paginationConfig: {
    position: 'bottom'
  },

}
class index extends Component {
  componentDidMount() {
    if (this.props.onRef) {
      this.props.onRef(this);
    }
  }
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          fetch={this.props.myFetch}
          {...config}
          fetchConfig={() => {
            if (params.tabIndex === '2') {
              return {
                apiName: 'getZxSaFsPaySettlement',
                otherParams: { zxSaFsSettlementId: params.zxSaFsSettlementId }
              }
            }
            return {}
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          formConfig={[
            {
              field: "zxSaFsSettlementId",
              type: 'string',
              hide: true
            },
            {
              field: "zxSaFsPaySettlementId",
              type: 'string',
              hide: true
            },
            {
              label: '本期支付项结算含税金额(元)',
              field: "thisAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期支付项结算不含税金额(元)',
              field: "thisAmtNoTax",
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
              field: "thisAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计支付项结算金额(元)',
              field: "totalAmt",
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
              field: 'payList',
              incToForm: true,
              qnnTableConfig: {
                ...tableConfig,
                rowDisabledCondition: () => { return params.clickName === 'detail' || params.billType === '2' },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    field: "add",
                    addRowDefaultData: { addFlag: '1' },
                    hide: () => { if (params.clickName === 'detail' || params.billType === '2') return true }
                  },
                  {
                    name: 'delRow',
                    icon: 'delete',
                    type: 'danger',
                    label: '删除',
                    field: "del",
                    hide: () => { if (params.clickName === 'detail' || params.billType === '2') return true }
                  }
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxSaFsPaySettlementDetailId',
                      hide: true
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'payID',
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
                      required: true,
                      type: 'select',
                      showSearch: true,
                      placeholder: '请选择',
                      optionConfig: {
                        label: 'workName',
                        value: 'workName',
                      },
                      fetchConfig: {
                        apiName: 'getZxSaProjectSetItemList',
                        otherParams: {
                          orgID: params.orgID,
                          contrType: '21'
                        }
                      },
                      allowClear: false,
                      onChange: async (val, obj) => {
                        let tableOneData = obj.rowData
                        let allData = await obj.qnnTableInstance.getTableData()
                        for (var i = 0; i < allData.length; i++) {
                          if (obj.rowIndex === i) continue
                          if (allData[i].payName === obj.itemData.workName) {
                            tableOneData.payName = null
                            tableOneData.payType = null
                            tableOneData.unit = null
                            await obj.qnnTableInstance.setEditedRowData(tableOneData)
                            Msg.warn('已存在相同名称，请重试!')
                            return
                          }
                        }
                        tableOneData.payName = val
                        tableOneData.payType = obj.itemData.workType
                        tableOneData.unit = obj.itemData.unit
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
                        rowData.qty = val ? val : 0
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
                        rowData.price = val
                        rowData.thisAmt = (rowData.qty ? rowData.qty : 0) * val  //本期结算含税金额
                        if (rowData.taxRate && rowData.taxRate !== '0') {
                          rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100) //本期结算不含税金额
                          rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * val - ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
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
                        rowData.taxRate = val
                        rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                        rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      },
                    }
                  },
                  {
                    table: {
                      title: '本期结算含税金额(元)',
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
                      width: 180,
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
                      width: 180,
                    },
                    form: {
                      field: 'remark',
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
