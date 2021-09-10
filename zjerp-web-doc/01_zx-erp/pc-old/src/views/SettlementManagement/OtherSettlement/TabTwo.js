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
            if (params.tabIndex === '1') {
              return {
                apiName: 'getZxSaOtherEquipResSettleList',
                otherParams: { zxSaOtherEquipSettleId: params.zxSaOtherEquipSettleId }
              }
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
              field: 'zxSaOtherEquipResSettleItemList',
              incToForm: true,
              disabled: () => {
                if (params.name === 'detail' || params.billType === '2') {
                  return true
                } else {
                  return false
                }
              },
              qnnTableConfig: {
                antd: {
                  rowKey: 'zxSaOtherEquipResSettleItemId',
                  size: 'small'
                },
                tableTdEdit: true,
                paginationConfig: {
                  position: 'bottom'
                },
                isShowRowSelect: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                tableTdEditSaveCb: (obj) => {
                  if (!obj.thisQty) {
                    Msg.error('请填写本期结算数量');
                    this.tableOne.qnnSetState({ editRowId: obj.zxSaOtherEquipResSettleItemId })
                    return
                  }
                  Toast.loading('loading...');
                  this.props.myFetch('updateZxSaOtherEquipResSettleItem', obj).then(
                    ({ success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.table.refresh()
                        Toast.hide()
                      } else {
                        Toast.hide()
                        this.tableOne.qnnSetState({ editRowId: obj.zxSaOtherEquipResSettleItemId })
                        Msg.error(message);
                      }
                    }
                  );
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSaOtherEquipResSettleItemId',
                      hide: true
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
                      tooltip: 15,
                      width: 150
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
                      tooltip: 16,
                      width: 200
                    }
                  },
                  {
                    table: {
                      title: '计量单位',
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
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
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
                      width: 120
                    }
                  },
                  {
                    table: {
                      title: '合同含税金额',
                      dataIndex: 'contractAmt',
                      key: 'contractAmt',
                      tdEdit: false,
                      align: 'center',
                      width: 160
                    }
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'changedQty',
                      key: 'changedQty',
                      tdEdit: false,
                      align: 'center',
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '变更后含税金额',
                      dataIndex: 'changedAmt',
                      key: 'changedAmt',
                      tdEdit: false,
                      align: 'center',
                      width: 160
                    }
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
                      title: `<div>本期结算数量<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'thisQty',
                      key: 'thisQty',
                      tdEdit: true,
                      width: 160,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          if (data.contractPrice) {
                            data.thisAmt = Number(val) * (data.contractPrice ? data.contractPrice : 0)
                            if (data.taxRate && data.taxRate !== '0') {
                              data.thisAmtNoTax = ((data.contractPrice ? data.contractPrice : 0) * Number(val)) / (1 + Number(data.taxRate) / 100)  //本期结算不含税金额
                              data.thisAmtTax = (data.contractPrice ? data.contractPrice : 0) * Number(val) - ((data.contractPrice ? data.contractPrice : 0) * Number(val)) / (1 + Number(data.taxRate) / 100)//本期结算税额
                            }
                          }
                          data.totalQty = (data.upQty ? data.upQty : 0) + Number(val)  //至本期末累计结算数量
                          data.totalAmt = (data.upAmt ? data.upAmt : 0) + (data.contractPrice ? data.contractPrice : 0) * Number(val)  //至本期末累计结算金额
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      }
                    },
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
                        return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '本期结算不含税金额',
                      dataIndex: 'thisAmtNoTax',
                      key: 'thisAmtNoTax',
                      tdEdit: false,
                      width: 160,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
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
                      title: '至上期末累计结算含税金额',
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      tdEdit: false,
                      width: 180,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '至本期末累计结算数量',
                      dataIndex: 'totalQty',
                      key: 'totalQty',
                      tdEdit: false,
                      width: 180,
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
                      title: '计算式',
                      dataIndex: 'planning',
                      key: 'planning',
                      tdEdit: true,
                      width: 150,
                    },
                    form: {
                      field: 'planning',
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
