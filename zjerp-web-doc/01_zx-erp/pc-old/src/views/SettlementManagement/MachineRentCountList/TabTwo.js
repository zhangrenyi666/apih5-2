import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from 'antd';
import { Toast } from 'antd-mobile';
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
    console.log(this.props);
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
            if (params.paramsData.tabIndex === '1') {
              return {
                apiName: 'getZxSaEquipSettleAuditDetail',
                otherParams: { zxSaEquipSettleAuditId: params.paramsData.zxSaEquipSettleAuditId }
              }
            }
          }}
          wrappedComponentRef={(me) => {
            this.table = me;
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
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '1') {
                    return {
                      apiName: 'getZxSaEquipResSettleItemList',
                      otherParams: { zxSaEquipResSettleId: params.paramsData.zxSaEquipResSettleId },
                    }
                  } else {
                    return {

                    }
                  }
                },
                antd: {
                  rowKey: 'zxSaEquipResSettleItemId',
                  size: 'small'
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxSaEquipResSettleItemId !== this.state.lineItem
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
                      field: 'zxSaEquipResSettleItemId',
                    }
                  },
                  {
                    table: {
                      title: '编号',
                      dataIndex: 'equipCode',
                      key: 'equipCode',
                      tdEdit: false,
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
                      tdEdit: false,
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
                      tdEdit: false,
                      align: 'center',
                      width: 100,
                    }
                  },
                  {
                    table: {
                      title: '单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      tdEdit: false,
                      align: 'center',
                      width: 100,
                    }
                  },
                  {
                    table: {
                      title: '起租日期',
                      dataIndex: 'startDate',
                      key: 'startDate',
                      tdEdit: false,
                      align: 'center',
                      width: 100,
                    }
                  },
                  {
                    table: {
                      title: '单价(元)',
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
                      tdEdit: false,
                      align: 'center',
                      width: 100,
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '合同数量',
                      dataIndex: 'contractQty',
                      key: 'contractQty',
                      tdEdit: false,
                      width: 100,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '合同含税金额(元)',
                      dataIndex: 'contractAmt',
                      key: 'contractAmt',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '变更后含税金额(元)',
                      dataIndex: 'changedAmt',
                      key: 'changedAmt',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'changedQty',
                      key: 'changedQty',
                      tdEdit: false,
                      width: 120,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '税率(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      tdEdit: false,
                      width: 80,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '本期结算数量',
                      dataIndex: 'thisQty',
                      key: 'thisQty',
                      tdEdit: true,
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      field: 'thisQty',
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let thisAmtRes = 0      //本期清单结算含税金额
                        let thisAmtNoTaxRes = 0  //本期清单结算不含税金额
                        let thisAmtTaxRes = 0  //本期清单结算税额
                        let upAmtRes = 0
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxSaEquipResSettleItemId === this.state.lineItem) {
                              if (Number(data.thisQty) !== Number(rowData.thisQty)) {
                                rowData.thisQty = Number(val)     //本期结算数量  
                                rowData.thisAmt = (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)  //本期结算含税金额
                                if (rowData.taxRate && rowData.taxRate !== '0') {
                                  rowData.thisAmtNoTax = ((rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)  //本期结算不含税金额
                                  rowData.thisAmtTax = (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val) - ((rowData.contractPrice ? rowData.contractPrice : 0) * val) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                                }
                                rowData.totalQty = (rowData.upQty ? rowData.upQty : 0) + Number(val)  //至本期末累计结算数量
                                rowData.totalAmt = (rowData.upAmt ? rowData.upAmt : 0) + (rowData.contractPrice ? rowData.contractPrice : 0) * Number(val)  //至本期末累计结算金额
                                rowData.runHour = data.runHour
                                rowData.useOil = data.useOil
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
                            }
                            thisAmtRes += rowData?.thisAmt ? rowData?.thisAmt : 0
                            thisAmtNoTaxRes += rowData?.thisAmtNoTax ? rowData?.thisAmtNoTax : 0
                            thisAmtTaxRes += rowData?.thisAmtTax ? rowData?.thisAmtTax : 0
                            upAmtRes += rowData?.upAmt ? rowData?.upAmt : 0
                            return rowData;
                          })
                          if (!tableOneData.filter(item => item === null).length) {
                            this.table.form.setFieldsValue({
                              thisAmtRes: Math.round(thisAmtRes * 100) / 100,
                              thisAmtNoTaxRes: Math.round(thisAmtNoTaxRes * 100) / 100,
                              thisAmtTaxRes: Math.round(thisAmtTaxRes * 100) / 100,
                              totalAmtRes: Math.round((upAmtRes + thisAmtRes) * 100) / 100,
                            })
                            setTimeout(() => {
                              this.tableOne.btnCallbackFn.setState({
                                data: tableOneData
                              })
                            }, 0)
                          }
                        })
                      },
                    },
                  },
                  {
                    table: {
                      title: '本期运转台时(小时)',
                      dataIndex: 'runHour',
                      key: 'runHour',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      field: 'runHour'
                    }
                  },
                  {
                    table: {
                      title: '本期油耗(L)',
                      dataIndex: 'useOil',
                      key: 'useOil',
                      tdEdit: true,
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      field: 'useOil'
                    }
                  },
                  {
                    table: {
                      title: '本期结算含税金额(元)',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '本期结算不含税金额(元)',
                      dataIndex: 'thisAmtNoTax',
                      key: 'thisAmtNoTax',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
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
                      width: 180,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                      }
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '至上期末累计结算数量',
                      dataIndex: 'upQty',
                      key: 'upQty',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '至上期末累计结算含税金额(元)',
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      tdEdit: false,
                      width: 250,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '至本期末累计结算数量',
                      dataIndex: 'totalQty',
                      key: 'totalQty',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
                    },
                    form: { type: 'number' }
                  },
                  {
                    table: {
                      title: '至本期末累计结算金额',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
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
                      align: 'center',
                      tooltip: 15,
                    },
                    form: {
                      field: 'remark',
                      type: 'string',
                      placeholder: '请输入',
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
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxSaEquipResSettleItemId === this.state.lineItem) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (obj.rowData.zxSaEquipResSettleItemId === this.state.lineItem) {
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              obj.btnCallbackFn.clearSelectedRows();
                              let body = {
                                ...obj.rowData,
                                ...data,
                              };
                              this.props.myFetch('updateZxSaEquipResSettleItem', body).then(
                                ({ success, message }) => {
                                  if (success) {
                                    Msg.success(message);
                                    this.setState({
                                      lineItem: ''
                                    })
                                    this.table.refresh()
                                    obj.btnCallbackFn.refresh();
                                    Toast.hide()
                                  } else {
                                    Toast.hide()
                                    Msg.error(message);
                                  }
                                }
                              );
                            })
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxSaEquipResSettleItemId === this.state.lineItem) {
                              Msg.error('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineItem: obj.rowData.zxSaEquipResSettleItemId
                          })
                        }
                      }
                    }
                  }
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
