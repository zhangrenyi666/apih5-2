import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Button, Drawer } from 'antd';
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
const drawercConfig = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 16 }
    }
  },
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      lineName: ''
    }
    this.props.onRef && this.props.onRef({
      getVals: async () => await this.table.form.getFieldsValue()
    })
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
          fetchConfig={() => {
            if (params.paramsData.tabIndex === '1') {
              return {
                apiName: 'getZxCtSuppliesShopSettlementListDetail',
                otherParams: { zxCtSuppliesShopSettlementId: params.paramsData.zxCtSuppliesShopSettlementId }
              }
            }
          }}
          formConfig={[
            {
              field: "zxCtSuppliesShopSettlementId",
              type: 'string',
              hide: true,
            },
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
              field: "resThisAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算不含税金额(元)',
              field: "resThisAmtNoTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '本期清单结算税额(元)',
              field: "resThisAmtTax",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '累计清单结算含税金额(元)',
              field: "resTotalAmt",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 12
            },
            {
              label: '单据编号',
              field: "stockBillNos",
              type: 'textarea',
              span: 20,
              disabled: true,
              formItemLayout: {
                labelCol: {
                  xs: { span: 5 },
                  sm: { span: 5 }
                },
                wrapperCol: {
                  xs: { span: 20 },
                  sm: { span: 20 }
                }
              },
            },
            {
              field: "diyButton",
              type: 'Component',
              Component: obj => {
                return <Button
                  style={{
                    background: params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' ? 'rgb(245,245,245)' : 'rgb(24,144,255)',
                    margin: '12px 5px', borderRadius: 3,
                    color: params.paramsData.clickName === 'detail' || params.paramsData.billType === '2' ? 'rgb(180,180,180)' : '#fff'
                  }}
                  onClick={() => {
                    if (params.paramsData.clickName === 'detail' || params.paramsData.billType === '2') {
                      return
                    } else {
                      this.setState({ showDrawer: true })
                      if (this.drawerOne) {
                        this.drawerOne.refresh()
                      }
                    }
                  }}
                >请选择</Button>;
              },
              disabled: () => {
                if (params.paramsData.clickName === 'detail') {
                  return true;
                } else {
                  return false;
                }
              },
              span: 4,
              formItemLayout: {
                labelCol: {
                  xs: { span: 0 },
                  sm: { span: 0 }
                },
                wrapperCol: {
                  xs: { span: 4 },
                  sm: { span: 4 }
                }
              },
            },
            {
              type: 'qnnTable',
              field: 'shopResSettleItemList',
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
                  rowKey: 'zxCtSuppliesShopResSettleItemId',
                  size: 'small'
                },
                tableTdEdit: true,
                paginationConfig: false,
                isShowRowSelect: true,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                tableTdEditSaveCb: (obj) => {
                  if (!obj.thisPrice) {
                    Msg.error('请选择单价');
                    this.tableOne.qnnSetState({ editRowId: obj.zxCtSuppliesShopResSettleItemId })
                    return
                  }
                  if (!obj.taxRate) {
                    Msg.error('请填写税率');
                    this.tableOne.qnnSetState({ editRowId: obj.zxCtSuppliesShopResSettleItemId })
                    return
                  }
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxCtSuppliesShopResSettleItemId',
                    }
                  },
                  {
                    table: {
                      title: '收料单编号',
                      dataIndex: 'stockBillNo',
                      key: 'stockBillNo',
                      tdEdit: false,
                      align: 'center',
                      width: 180,
                      tooltip: 20,
                    }
                  },
                  {
                    table: {
                      title: '物资编码',
                      dataIndex: 'resCode',
                      key: 'resCode',
                      tdEdit: false,
                      align: 'center',
                      tooltip: 15,
                      width: 150
                    }
                  },
                  {
                    table: {
                      title: '物资名称',
                      dataIndex: 'resName',
                      key: 'resName',
                      tdEdit: false,
                      align: 'center',
                      tooltip: 15,
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
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      tdEdit: false,
                      width: 100,
                      align: 'center',
                      initialValue: '0',
                    },
                  },
                  {
                    table: {
                      title: `<div>本期结算数量<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'thisQty',
                      key: 'thisQty',
                      tdEdit: false,
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: `<div>本期结算单价(元)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'thisPrice',
                      key: 'thisPrice',
                      tdEdit: true,
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'selectByQnnTable',
                      optionConfig: {
                        label: 'price',
                        value: 'price'
                      },
                      dropdownMatchSelectWidth: 800,
                      placeholder: '请选择',
                      qnnTableConfig: {
                        antd: {
                          rowKey: "zxCtSuppliesContrShopListId"
                        },
                        selectType: "radio",
                        fetchConfig: {
                          apiName: 'getZxCtSuppliesContrShopListByWorkID',
                          otherParams: (obj) => {
                            return {
                              workNo: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.resCode,
                              workName: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.resName,
                              contractID:params.paramsData.contractID
                            }
                          }
                        },
                        formConfig: [
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "workName",
                              title: "清单名称",
                            },
                          },
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "workNo",
                              title: "物资编码",
                            },
                          },
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "qty",
                              title: "合同数量",
                            },
                          },
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "price",
                              title: "材料单价",
                            },
                          },
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "surplusQty",
                              title: "剩余结算量",
                            },
                          },
                          {
                            isInForm: false,
                            table: {
                              dataIndex: "taxRate",
                              title: "税率",
                            },
                          }
                        ]
                      },
                      onChange: (val, obj) => {
                        if(obj.itemData[0].surplusQty === 0){
                          Msg.warn('所选单价剩余结算量为0，请重试')
                          return
                        }
                        this.tableOne.getEditedRowData(false).then((data) => {
                          data.thisPrice = obj.itemData[0].price
                          data.thisAmt = obj.itemData[0].price * data.thisQty
                          data.thisAmtNoTax = ((data.thisQty ? data.thisQty : 0) * Number(obj.itemData[0].price)) / (1 + (obj.itemData[0].taxRate ? Number(obj.itemData[0].taxRate) : 0) / 100)  //本期结算不含税金额
                          data.thisAmtTax = (data.thisQty ? data.thisQty : 0) * Number(obj.itemData[0].price) - ((data.thisQty ? data.thisQty : 0) * Number(obj.itemData[0].price)) / (1 + (obj.itemData[0].taxRate ? Number(obj.itemData[0].taxRate) : 0) / 100)//本期结算税额
                          data.taxRate = obj.itemData[0].taxRate ? Number(obj.itemData[0].taxRate) : 0
                          data.resID = obj.itemData[0].zxCtSuppliesContrShopListId
                          this.tableOne.setEditedRowData({ ...data })
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: '本期结算含税金额(元)',
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
                      title: '本期结算不含税金额(元)',
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
                      title: '本期结算税额(元)',
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
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                      width: 120,
                      tooltip: 20,
                      align: 'center',
                    },
                    form: {
                      field: 'remarks',
                      type: 'string',
                      placeholder: '请输入',
                    },
                  }
                ],
              }
            }
          ]}
        />
        <Drawer
          title="单据编号选择"
          placement="right"
          closable={true}
          onClose={() => { this.setState({ showDrawer: false }) }}
          visible={this.state.showDrawer}
          width={650}
        >
          <QnnTable
            {...this.props}
            fetch={this.props.myFetch}
            upload={this.props.myUpload}
            {...drawercConfig}
            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
            wrappedComponentRef={(me) => {
              this.drawerOne = me;
            }}
            fetchConfig={{
              apiName: 'getZxCtSupReceivingAndReturnListByResID',
              otherParams: {
                purchaseContractID: params.paramsData.contractID,
                busDate: params.paramsData.periodDate,
                mainID: params.paramsData.zxCtSuppliesShopSettlementId,
                startTime: params.paramsData.startDate,
                endTime: params.paramsData.documentsEndTime,
                inOrgID: params.paramsData.orgID,
                outOrgID: params.paramsData.secondID,
              },
              success: (data) => {
                if (data.success === true && data.data && data.data.length > 0) {
                  let selectRowDataList = []
                  for (var i = 0; i < data.data.length; i++) {
                    if (data.data[i].isSelect === '1') selectRowDataList.push(data.data[i])
                  }
                  this.drawerOne.setSelectedRows(selectRowDataList)
                }
              }
            }}
            formConfig={[
              {
                table: {
                  title: '单据编号',
                  dataIndex: 'billNo',
                  key: 'billNo',
                  width: 170,
                  tooltip: 25
                }
              },
              {
                table: {
                  title: '业务日期',
                  dataIndex: 'busDate',
                  key: 'busDate',
                  width: 120,
                  format: 'YYYY-MM-DD',
                }
              },
              {
                table: {
                  title: '单据类型',
                  dataIndex: 'bizType',
                  key: 'bizType',
                  width: 120,
                }
              }
            ]}
            actionBtnsPosition={"top"}
            actionBtns={[
              {
                name: 'diy',//内置add del
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '保存',
                hide: () => {
                  if (params.paramsData.clickName === 'detail') {
                    return true;
                  } else {
                    return false;
                  }
                },
                onClick: (obj) => {
                  let otherParams = {}
                  let zxCtSuppliesShopResSettleItemList = obj.selectedRows
                  if (zxCtSuppliesShopResSettleItemList && zxCtSuppliesShopResSettleItemList.length > 0) {
                    zxCtSuppliesShopResSettleItemList[0].zxCtSuppliesShopSettlementId = params.paramsData.zxCtSuppliesShopSettlementId
                    zxCtSuppliesShopResSettleItemList[0].contractID = params.paramsData.contractID
                  }
                  otherParams.zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemList
                  otherParams.zxCtSuppliesShopSettlementId = params.paramsData.zxCtSuppliesShopSettlementId
                  otherParams.contractID = params.paramsData.contractID
                  this.props.myFetch('addZxCtSuppliesShopResSettleItemByStockId', otherParams).then(
                    ({ success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.setState({ showDrawer: false })
                        obj.btnCallbackFn.clearSelectedRows();
                        obj.btnCallbackFn.refresh()
                        this.table.refresh()
                      } else {
                        this.table.form.setFieldsValue({
                          stockBillIDs: ''
                        })
                        Msg.error(message);
                      }
                    }
                  );
                }
              }
            ]}
          />
        </Drawer>
      </div>
    );
  }
}
export default index;
