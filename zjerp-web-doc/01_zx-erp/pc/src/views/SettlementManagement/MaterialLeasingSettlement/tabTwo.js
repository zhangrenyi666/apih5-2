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
  constructor(props) {
    super(props)
    this.state = {
      lineName: ''
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
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          fetchConfig={() => {
            if (params.paramsData.tabIndex === '1') {
              return {
                apiName: 'getZxCtSuppliesLeaseSettlementListDetail',
                otherParams: { zxCtSuppliesLeaseSettlementListId: params.paramsData.zxCtSuppliesLeaseSettlementListId }
              }
            } else { return {} }
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
              label: '变更后含税合同金额(万元)',
              field: "changeAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算含税金额(元)',
              field: "thisEquipAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算不含税金额(元)',
              field: "thisEquipAmtNoTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算税额(元)',
              field: "thisEquipAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计清单结算含税金额(元)',
              field: "totalEquipAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              type: 'qnnTable',
              field: 'table1',
              disabled:true,
              qnnTableConfig: {
                tableTdEdit: params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' ? false : true,
                fetchConfig: (obj) => {
                  if (params.paramsData.tabIndex === '1') {
                    return {
                      apiName: 'getZxCtSuppliesLeaseResSettleItemListByConID',
                      otherParams: {
                        contractID: params.paramsData.contractID,
                        billNo: params.paramsData.billNo,
                        mainID: params.paramsData.zxCtSuppliesLeaseSettlementListId
                      },
                    }
                  } else {
                    return {}
                  }
                },
                antd: {
                  rowKey: 'zxCtSuppliesLeaseSettlementItemId',
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
                    apiName: 'updateZxCtSuppliesLeaseResSettleItemByConID',
                    otherParams: { ...body },
                    success: ({ success, message }) => {
                      if (success) {
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
                      field: 'zxCtSuppliesLeaseSettlementItemId',
                    }
                  },
                  {
                    table: {
                      title: '物资编码',
                      dataIndex: 'equipCode',
                      key: 'equipCode',
                      tdEdit: false,
                      align: 'center',
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '物资名称',
                      dataIndex: 'equipName',
                      key: 'equipName',
                      tdEdit: false,
                      align: 'center',
                      width: 160
                    }
                  },
                  {
                    table: {
                      title: '规格型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      tdEdit: false,
                      align: 'center',
                      width: 120
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
                      title: '租期单位',
                      dataIndex: 'rentUnit',
                      key: 'rentUnit',
                      tdEdit: false,
                      align: 'center',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '单价(元)',
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
                      tdEdit: false,
                      align: 'center',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '变更后单价(元)',
                      dataIndex: 'alterPrice',
                      key: 'alterPrice',
                      tdEdit: false,
                      align: 'center',
                      width: 120
                    }
                  },
                  {
                    table: {
                      title: '合同数量',
                      dataIndex: 'contractQty',
                      key: 'contractQty',
                      tdEdit: false,
                      align: 'center',
                      width: 100
                    }
                  },
                  {
                    table: {
                      title: '变更后合同数量',
                      dataIndex: 'changedQty',
                      key: 'changedQty',
                      tdEdit: false,
                      align: 'center',
                      width: 150,
                    },
                  },
                  {
                    table: {
                      title: '合同租期',
                      dataIndex: 'contrTrrm',
                      key: 'contrTrrm',
                      tdEdit: false,
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '变更后合同租期',
                      dataIndex: 'alterTrrm',
                      key: 'alterTrrm',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
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
                      dataIndex: 'signQty',
                      key: 'signQty',
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
                        rowData.totalQty = (rowData.upQty ? rowData.upQty : 0) + Number(val)  //至本期末累计结算数量
                        if (rowData.signContrTrrm && rowData.signContrTrrm !== 0) {
                          rowData.signThisAmt = Number(val) * rowData.signContrTrrm       //本期结算数量*租期
                          if (rowData.contractPrice && rowData.contractPrice !== 0) {
                            rowData.thisAmt = Number(val) * rowData.signContrTrrm * rowData.contractPrice  //本期结算含税金额
                            rowData.totalAmt = (rowData.upAmt ? rowData.upAmt : 0) + Number(val) * rowData.signContrTrrm * rowData.contractPrice  //至本期末累计结算金额
                            if (rowData.taxRate && rowData.taxRate !== '0') {
                              //本期结算不含税金额
                              rowData.thisAmtNoTax = (Number(val) * rowData.signContrTrrm * rowData.contractPrice) / (1 + Number(rowData.taxRate) / 100)
                              //本期结算税额
                              rowData.thisAmtTax = (Number(val) * rowData.signContrTrrm * rowData.contractPrice) - ((Number(val) * rowData.signContrTrrm * rowData.contractPrice) / (1 + Number(rowData.taxRate) / 100))
                            }
                          }
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      },
                    },
                  },
                  {
                    table: {
                      title: '本期结算租期',
                      dataIndex: 'signContrTrrm',
                      key: 'signContrTrrm',
                      tdEdit: true,
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      required: true,
                      type: 'number',
                      initialValue: 0,
                      placeholder: '请输入',
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        if (rowData.signQty && rowData.signQty !== 0) {
                          rowData.signThisAmt = Number(val) * rowData.signQty       //本期结算数量*租期
                          if (rowData.contractPrice && rowData.contractPrice !== 0) {
                            rowData.thisAmt = Number(val) * rowData.signQty * rowData.contractPrice  //本期结算含税金额
                            rowData.totalAmt = (rowData.upAmt ? rowData.upAmt : 0) + Number(val) * rowData.signQty * rowData.contractPrice  //至本期末累计结算金额
                            if (rowData.taxRate && rowData.taxRate !== '0') {
                              //本期结算不含税金额
                              rowData.thisAmtNoTax = (Number(val) * rowData.signQty * rowData.contractPrice) / (1 + Number(rowData.taxRate) / 100)
                              //本期结算税额
                              rowData.thisAmtTax = (Number(val) * rowData.signQty * rowData.contractPrice) - ((Number(val) * rowData.signQty * rowData.contractPrice) / (1 + Number(rowData.taxRate) / 100))
                            }
                          }
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      },
                      // onBlur: (obj) => {
                      //   let val = obj.target.value;
                      //   let thisEquipAmt = 0      //本期清单结算含税金额
                      //   let thisEquipAmtNoTax = 0  //本期清单结算不含税金额
                      //   let thisEquipAmtTax = 0  //本期清单结算税额
                      //   let upAmt = 0 //至上期末累计结算金额
                      //   let tableOneData = this.tableOne.state.data.map((rowData) => {
                      //     if (rowData.zxCtSuppliesLeaseSettlementItemId === this.state.lineItem) {
                      //       rowData.signContrTrrm = Number(val)     //本期结算数量 

                      //     }
                      //     thisEquipAmt += rowData?.thisAmt ? rowData.thisAmt : 0
                      //     thisEquipAmtNoTax += rowData?.thisAmtNoTax ? rowData.thisAmtNoTax : 0
                      //     thisEquipAmtTax += rowData?.thisAmtTax ? rowData.thisAmtTax : 0
                      //     upAmt += rowData?.upAmt ? rowData.upAmt : 0
                      //     return rowData;
                      //   })
                      //   if (!tableOneData.filter(item => item === null).length) {
                      //     this.table.form.setFieldsValue({
                      //       thisEquipAmt: Math.round(thisEquipAmt * 100) / 100,
                      //       thisEquipAmtNoTax: Math.round(thisEquipAmtNoTax * 100) / 100,
                      //       thisEquipAmtTax: Math.round(thisEquipAmtTax * 100) / 100,
                      //       totalEquipAmt: Math.round((upAmt + thisEquipAmt) * 100) / 100,
                      //     })
                      //     this.tableOne.btnCallbackFn.setState({
                      //       data: tableOneData
                      //     }, () => {
                      //       document.activeElement.blur()
                      //     })
                      //   }
                      // },
                    },
                  },
                  {
                    table: {
                      title: '本期结算数量*租期',
                      dataIndex: 'signThisAmt',
                      key: 'signThisAmt',
                      tdEdit: false,
                      width: 160,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.signThisAmt ? Number(rowData.signThisAmt).toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '本期租赁起始日期',
                      dataIndex: 'signRentStartDate',
                      key: 'signRentStartDate',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                      format: "YYYY-MM-DD",
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '本期租赁截止日期',
                      dataIndex: 'signRentEndDate',
                      key: 'signRentEndDate',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                      format: "YYYY-MM-DD",
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '本期结算含税金额',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      tdEdit: false,
                      width: 160,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmt ? Number(rowData.thisAmt).toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '本期结算不含税金额',
                      dataIndex: 'thisAmtNoTax',
                      key: 'thisAmtNoTax',
                      tdEdit: false,
                      width: 170,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmtNoTax ? Number(rowData.thisAmtNoTax).toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '本期结算税额',
                      dataIndex: 'thisAmtTax',
                      key: 'thisAmtTax',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmtTax ? Number(rowData.thisAmtTax).toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '至上期末累计结算数量*租期',
                      dataIndex: 'upQty',
                      key: 'upQty',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                      initialValue: 0,
                    }
                  },
                  {
                    table: {
                      title: '至上期末累计结算含税金额',
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '至本期末累计结算数量*租期',
                      dataIndex: 'totalQty',
                      key: 'totalQty',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '至本期末累计结算含税金额',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      tdEdit: false,
                      width: 200,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                      width: 150,
                      align: 'center'
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
