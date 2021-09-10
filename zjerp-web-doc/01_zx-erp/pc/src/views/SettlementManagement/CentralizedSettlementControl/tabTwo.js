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

class index extends Component {
  render() {
    let params = this.props
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          fetch={this.props.myFetch}
          {...config}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          fetchConfig={() => {
            if (params.paramsData.tabIndex === '1') {
              return {
                apiName: 'getZxSaProjectSettleAuditDetail',
                otherParams: { id: params.paramsData.id }
              }
            } else { return {} }
          }}
          formConfig={[
            {
              label: '含税合同金额(万元)',
              field: "workContractAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '变更后含税合同金额(万元)',
              field: "workChangeAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算含税金额(元)',
              field: "workThisAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算不含税金额(元)',
              field: "workThisAmtNoTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算税额(元)',
              field: "workThisAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计清单结算含税金额(元)',
              field: "workTotalAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              type: 'qnnTable',
              field: 'workSettleItemList',
              incToForm: true,
              qnnTableConfig: {
                tableTdEdit: params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' ? false : true,
                rowDisabledCondition: () => { return params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' },
                antd: {
                  rowKey: 'projectWorkSettleItemId',
                  size: 'small'
                },
                paginationConfig: {
                  position: 'bottom'
                },
                isShowRowSelect: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                tableTdEditFetchConfig: async (obj) => {
                  let body = await obj.qnnTableInstance.getEditedRowData()
                  return {
                    apiName: 'updateZxSaProjectWorkSettleItem',
                    otherParams: { ...body },
                    success: ({ success, message }) => {
                      if (success) {
                        this.table.refresh()
                        Msg.success(message)
                      }
                    }
                  }
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'projectWorkSettleItemId',
                    }
                  },
                  {
                    table: {
                      title: '清单细目',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      tdEdit: false,
                      align: 'center',
                      tooltip: 15,
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '清单名称',
                      dataIndex: 'workName',
                      key: 'workName',
                      tdEdit: false,
                      align: 'center',
                      tooltip: 10,
                      width: 200
                    }
                  },
                  {
                    table: {
                      title: '分包单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      tdEdit: false,
                      align: 'center',
                      width: 140
                    }
                  },
                  {
                    table: {
                      title: '含税单价(元)',
                      dataIndex: 'price',
                      key: 'price',
                      tdEdit: false,
                      align: 'center',
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '合同数量',
                      dataIndex: 'contractQty',
                      key: 'contractQty',
                      tdEdit: false,
                      align: 'center',
                      width: 120
                    }
                  },
                  {
                    table: {
                      title: '合同含税金额(元)',
                      dataIndex: 'contractAmt',
                      key: 'contractAmt',
                      tdEdit: false,
                      align: 'center',
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '变更后含税单价(元)',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
                      tdEdit: false,
                      align: 'center',
                      width: 160
                    }
                  },
                  {
                    table: {
                      title: '变更后合同数量',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
                      tdEdit: false,
                      align: 'center',
                      width: 160
                    }
                  },
                  {
                    table: {
                      title: '变更后合同含税金额',
                      dataIndex: 'changeAmt',
                      key: 'changeAmt',
                      tdEdit: false,
                      align: 'center',
                      width: 160,
                    },
                  },
                  {
                    table: {
                      title: '税率(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      initialValue: '0',
                      tdEdit: false,
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '本期结算数量',
                      dataIndex: 'thisQty',
                      key: 'thisQty',
                      tdEdit: true,
                      width: 170,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      required: true,
                      placeholder: '请输入',
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        if (rowData.changePrice) {
                          rowData.thisTotalAmt = val * rowData.changePrice
                        } else {
                          rowData.thisTotalAmt = val * rowData.price ? rowData.price : 0
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      }
                    },
                  },
                  {
                    table: {
                      title: '本期结算含税金额',
                      dataIndex: 'thisTotalAmt',
                      key: 'thisTotalAmt',
                      tdEdit: false,
                      width: 160,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '上期末累计结算数量',
                      dataIndex: 'upAllQty',
                      key: 'upAllQty',
                      tdEdit: false,
                      width: 160,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '上期末累计结算含税金额',
                      dataIndex: 'upAllTotalAmt',
                      key: 'upAllTotalAmt',
                      tdEdit: false,
                      width: 170,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '计算式',
                      dataIndex: 'planning',
                      key: 'planning',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                      initialValue: 0,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: '图号',
                      dataIndex: 'mapNum',
                      key: 'mapNum',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remark',
                      key: 'remark',
                      tdEdit: true,
                      width: 200,
                      align: 'center',
                      tooltip: 15,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                ],
              }
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
