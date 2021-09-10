import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
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
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          addIsGetData={true} 
          fetchConfig={{
            apiName: 'getZxSaFsInventory',
            otherParams: { 
              zxSaFsSettlementId: params.zxSaFsSettlementId ,
              // billID: params?.billID 
            }
          }}
          formConfig={[
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
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算含税金额(元)',
              field: "thisAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算不含税金额(元)',
              field: "thisAmtNoTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算税额(元)',
              field: "thisAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计清单结算含税金额(元)',
              field: "totalAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              type: 'qnnTable',
              field: 'detailList',
              incToForm: true,
              qnnTableConfig: {
                rowDisabledCondition: () => { return params.clickName === 'detail' || params.billType === '2' },
                antd: {
                  rowKey: 'zxSaFsEnumerationSettlementDetailedId',
                  size: 'small'
                },
                paginationConfig: {
                  position: 'bottom'
                },
                isShowRowSelect: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSaFsEnumerationSettlementDetailedId',
                    }
                  },
                  {
                    table: {
                      title: '清单编号',
                      dataIndex: 'equipCode',
                      key: 'equipCode',
                      tdEdit: false,
                      align: 'center',
                      fixed: 'left',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '清单名称',
                      dataIndex: 'equipName',
                      key: 'equipName',
                      tdEdit: false,
                      align: 'center',
                      fixed: 'left',
                      width: 180
                    }
                  },
                  {
                    table: {
                      title: '计量单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      tdEdit: false,
                      align: 'center',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '含税单价(元)',
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '合同数量',
                      dataIndex: 'contractQty',
                      key: 'contractQty',
                      tdEdit: false,
                      align: 'center',
                      width: 120,
                    }
                  },
                  {
                    table: {
                      title: '合同含税金额(元)',
                      dataIndex: 'contractAmt',
                      key: 'contractAmt',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'changedQty',
                      key: 'changedQty',
                      tdEdit: false,
                      align: 'center',
                      width: 120
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额(元)',
                      dataIndex: 'changedAmt',
                      key: 'changedAmt',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '税率(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      initialValue: '0',
                      tdEdit: false,
                      width: 100,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '本期结算数量',
                      dataIndex: 'thisQty',
                      key: 'thisQty',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      required: true,
                      placeholder: '请输入',
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        rowData.thisQty = val ? val : 0
                        rowData.thisAmt = (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)  //本期结算含税金额
                        if (rowData.taxRate && rowData.taxRate !== '0') {
                          rowData.thisAmtNoTax = ((rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)  //本期结算不含税金额
                          rowData.thisAmtTax = (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val) - ((rowData.contractPrice ? rowData.contractPrice : 0) * val) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                        }
                        rowData.totalQty = (rowData.upQty ? rowData.upQty : 0) + Number(val)  //至本期末累计结算数量
                        rowData.totalAmt = (rowData.upAmt ? rowData.upAmt : 0) + (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)  //至本期末累计结算金额
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      }
                    },
                  },
                  {
                    table: {
                      title: '本期结算含税金额(元)',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
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
                      width: 190,
                      align: 'center',
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
                      width: 170,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '至上期末累计结算数量',
                      dataIndex: 'upQty',
                      key: 'upQty',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
                      initialValue: 0,
                    }
                  },
                  {
                    table: {
                      title: '至上期末累计结算含税金额(元)',
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      tdEdit: false,
                      width: 250,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '至本期末累计结算数量',
                      dataIndex: 'totalQty',
                      key: 'totalQty',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '至本期末累计结算金额',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.totalAmt ? rowData.totalAmt.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                      width: 200,
                      align: 'center',
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
