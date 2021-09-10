import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Button } from 'antd';
const confirm = Modal.confirm;
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      showModel: false
    }
  }
  handleCancelBjdh = () => {
    this.setState({ showModel: false });
  }
  render() {
    let params = this.props
    let { myPublic: { domain } } = this.props;
    const { showModel } = this.state
    return (
      <div style={{ padding: '10px' }}>
        <QnnForm
          fetch={this.props.myFetch}
          formConfig={[
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                rowDisabledCondition: () => { return params.clickName === 'detail' },
                fetchConfig: () => {
                  if (params.tabIndex === '1') {
                    return {
                      apiName: 'getZxSaSettleAuditInvoiceList',
                      otherParams: { mainID: params.baseData.zxCtSuppliesLeaseSettlementListId }
                    }
                  }
                  return {}
                },
                antd: {
                  rowKey: 'zxSaSettleAuditInvoiceId',
                  size: 'small'
                },
                actionBtnsContainerStyle: {
                  textAlign: 'right'
                },
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyDownload',
                    type: 'primary',
                    label: '下载模板',
                    hide: () => { if (params.clickName === 'detail') return true },
                    onClick: () => {
                      confirm({
                        content: '确定下载模板吗?',
                        onOk: () => {
                          window.open(`${domain}system-server/downloadFile?filePath=import/【结算单推送】发票信息导入模板.xlsx&downName=【结算单推送】发票信息导入模板.xlsx`)
                        }
                      })
                    }
                  },
                  {
                    name: 'diyImport',
                    type: 'primary',
                    label: '导入',
                    hide: () => { if (params.clickName === 'detail') return true },
                    onClick: (obj) => {
                      this.setState({ showModel: true })
                    }
                  },
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    hide: () => { if (params.clickName === 'detail') return true },
                    addRowDefaultData: { addFlag: '1', taxName: params.baseData.orgCertificate }
                  },
                  {
                    name: 'delRow',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    hide: () => { if (params.clickName === 'detail') return true },
                  },
                  {
                    name: 'diyButton',
                    type: 'primary',
                    label: '保存',
                    field: "diyButton",
                    hide: () => { if (params.clickName === 'detail') return true },
                    onClick: async (obj) => {
                      let emptyData = []
                      let invoiceList = await obj.qnnTableInstance.getTableData()
                      await invoiceList.map((item) => {
                        if (!item.invoiceType) emptyData.push('发票类型')
                        if (item.invoiceType && item.invoiceType !== '0') {
                          if (!item.invoiceNo) emptyData.push('发票号码')
                          if (!item.invoiceCode) emptyData.push('发票代码')
                          if (!item.taxRate) emptyData.push('税率')
                          if (!item.taxName) emptyData.push('纳税人识别号')
                        }
                        if (!item.ticketDate) emptyData.push('开票日期')
                        if (!item.adValorem && item.adValorem !== 0) emptyData.push('计税合计')
                        return item
                      })
                      if (emptyData.length > 0) {
                        Msg.warn(`请填写${emptyData.join()}`)
                      } else {
                        obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                        let params = this.props.baseData  //基础数据
                        params.invoiceList = invoiceList
                        this.props.myFetch('batchUpdateSpZxSaSettleAuditInvoice', params)
                          .then(({ success, message }) => {
                            if (success) {
                              obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                              this.tableOne.refresh()
                              Msg.success(message)
                              //保存后刷新基础信息
                              this.props.myFetch('getZxCtSuppliesLeaseSettlementListDetail', { zxCtSuppliesLeaseSettlementListId: params.zxCtSuppliesLeaseSettlementListId })
                                .then(({ data }) => {
                                  this.props.TabOneRef.setDeawerValues(data)
                                })
                            } else {
                              obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                              Msg.error(message)
                            }
                          })
                      }

                    }
                  }
                ],
                paginationConfig: {
                  position: 'bottom'
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSaSettleAuditInvoiceId',
                    }
                  },
                  {
                    table: {
                      title: '发票代码',
                      dataIndex: 'invoiceCode',
                      key: 'invoiceCode',
                      tdEdit: (obj) => {
                        if (obj.invoiceType !== '0') return true
                        return false
                      },
                      align: 'center',
                      fixed: 'left',
                      width: 150,
                    },
                    form: {
                      required: true,
                      type: 'string',
                      placeholder: '请输入',
                      dependencies: ['invoiceType'],
                      dependenciesReRender: true,
                      field: 'equipCode',
                    }
                  },
                  {
                    table: {
                      title: '发票号码',
                      dataIndex: 'invoiceNo',
                      key: 'invoiceNo',
                      tdEdit: (obj) => {
                        if (obj.invoiceType !== '0') return true
                        return false
                      },
                      align: 'center',
                      fixed: 'left',
                      width: 180
                    },
                    form: {
                      required: true,
                      type: 'string',
                      placeholder: '请输入',
                      field: 'equipName',
                      dependencies: ['invoiceType'],
                      dependenciesReRender: true,
                    }
                  },
                  {
                    table: {
                      dataIndex: 'ticketDate',
                      key: 'ticketDate',
                      title: '开票日期',
                      tdEdit: true,
                      width: 150,
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      required: true,
                      field: 'actualStartDate',
                      placeholder: '请选择',
                      type: 'date',
                      initialValue: new Date()
                    }
                  },
                  {
                    table: {
                      title: '发票类型',
                      dataIndex: 'invoiceType',
                      key: 'invoiceType',
                      width: 140,
                      align: 'center',
                      tdEdit: true,
                    },
                    form: {
                      field: "invoiceType",
                      required: true,
                      type: 'select',
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      optionData: [
                        { label: '无', value: '0' },
                        { label: '增值税普通发票', value: '1' },
                        { label: '增值税专用发票', value: '2' },
                        { label: '机动车销售统一发票', value: '12' },
                        { label: '增值税电子普通发票', value: '51' },
                        { label: '定额发票', value: '52' },
                        { label: '海关缴款书', value: '53' },
                        { label: '收据', value: '99' },
                      ],
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        rowData.invoiceType = val
                        rowData.taxRate = null
                        rowData.adValorem = null
                        rowData.taxFree = null
                        rowData.taxAmount = null
                        if (val === '0') {
                          rowData.invoiceCode = null
                          rowData.invoiceNo = null
                          rowData.taxName = null
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
                      initialValue: '0',
                      tdEdit: (obj) => {
                        if (obj.invoiceType !== '0') return true
                        return false
                      },
                      width: 100,
                      align: 'center',
                    },
                    form: {
                      required: true,
                      placeholder: '请选择',
                      type: "select",
                      field: 'taxRate',
                      optionConfig: {
                        label: 'value',
                        value: 'value',
                      },
                      dependencies: ['invoiceType'],
                      dependenciesReRender: true,
                      optionData: (obj) => {
                        let objData = obj.rowData
                        if (objData?.invoiceType === '1') {
                          return [{ value: '0' }]
                        }
                        if (objData?.invoiceType === '2' && params.taxCountWay === '2') {
                          return [{ value: '0' }, { value: '3' }, { value: '5' }]
                        }
                        return [
                          { value: '0' }, { value: '1' }, { value: '1.5' },
                          { value: '3' }, { value: '5' }, { value: '6' },
                          { value: '9' }, { value: '10' }, { value: '11' },
                          { value: '13' }, { value: '16' }, { value: '17' },
                        ]
                      },
                      onChange: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        rowData.taxRate = val
                        if (rowData.adValorem || rowData.adValorem === 0) {
                          rowData.taxFree = rowData.adValorem / (1 + Number(val) / 100)
                          rowData.taxAmount = rowData.adValorem - (rowData.adValorem / (1 + Number(val) / 100))
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      },
                    }
                  },
                  {
                    table: {
                      title: '不含税金额(元)',
                      dataIndex: 'taxFree',
                      key: 'taxFree',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.taxFree ? rowData.taxFree.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '税额(元)',
                      dataIndex: 'taxAmount',
                      key: 'taxAmount',
                      tdEdit: false,
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.taxAmount ? rowData.taxAmount.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '计税合计',
                      dataIndex: 'adValorem',
                      key: 'adValorem',
                      tdEdit: true,
                      width: 200,
                      align: 'center',
                    },
                    form: {
                      required: true,
                      field: 'adValorem',
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        let value = rowData.adValorem
                        rowData.adValorem = value
                        if (rowData.taxRate) {
                          rowData.taxFree = value / (1 + Number(rowData.taxRate) / 100)
                          rowData.taxAmount = value - value / (1 + Number(rowData.taxRate) / 100)
                        }
                        await obj.qnnTableInstance.setEditedRowData(rowData)
                      }
                    }
                  },
                  {
                    table: {
                      title: '纳税人识别号',
                      dataIndex: 'taxName',
                      key: 'taxName',
                      tdEdit: (obj) => {
                        if (obj.invoiceType !== '0') return true
                        return false
                      },
                      width: 200,
                      align: 'center',
                    },
                    form: {
                      required: true,
                      field: 'taxName',
                      type: 'string',
                      dependencies: ['invoiceType'],
                      dependenciesReRender: true,
                      placeholder: '请输入',
                    }
                  }
                ]
              }
            }
          ]}
        />
        <Modal
          width='800px'
          style={{ top: '0' }}
          title="导入"
          visible={showModel}
          footer={null}
          onCancel={this.handleCancelBjdh}
          bodyStyle={{ width: '800px', height: '300px' }}
          centered={true}
          destroyOnClose={this.handleCancelBjdh}
          wrapClassName={'modals'}
        >
          <QnnForm
            fetch={this.props.myFetch}
            upload={this.props.myUpload}
            wrappedComponentRef={(me) => {
              this.formOne = me;
            }}
            formConfig={[
              {
                label: '附件',
                field: 'fileList',
                type: 'files',
                fetchConfig: {
                  apiName: 'upload'
                },
                span: 12
              },
              {
                field: 'saveButton',
                type: 'Component',
                Component: obj => {
                  return <Button
                    style={{ margin: 20, background: 'rgb(24,144,255)', color: '#fff' }}
                    onClick={async () => {
                      let params = this.props.baseData
                      let fileList = this.formOne.form.getFieldValue('fileList')
                      if (fileList && fileList.length > 0) {
                        params.fileList = fileList
                        this.props.myFetch("importSpZxSaSettleAuditInvoice", params)
                          .then(({ success, message }) => {
                            if (success) {
                              this.setState({ showModel: false }, () => {
                                this.tableOne.refresh()
                                //保存后刷新基础信息
                                this.props.myFetch('getZxCtSuppliesLeaseSettlementListDetail', { zxCtSuppliesLeaseSettlementListId: params.zxCtSuppliesLeaseSettlementListId })
                                  .then(({ data }) => {
                                    this.props.TabOneRef.setDeawerValues(data)
                                  })
                              })
                            } else {
                              Msg.error(message)
                            }
                          })
                      } else {
                        Msg.warn('请上传导入文件')
                      }
                    }}
                  >保存</Button>
                },
                span: 12
              }
            ]}
          />
        </Modal>
      </div>
    );
  }
}
export default index;
