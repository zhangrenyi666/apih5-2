import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Modal } from "antd";
const confirm = Modal.confirm;
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      flowData: this.props.flowData ? this.props.flowData : {}
    }
  }
  render() {
    const { flowData } = this.state
    const { myPublic: { appInfo: { ureport } } } = this.props;
    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
    return (
      <div>
        <QnnForm
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{
            token: this.props.loginAndLogoutInfo.loginInfo.token
          }}
          wrappedComponentRef={(me) => {
            this.formHasTicket = me;
          }}
          fetchConfig={() => {
            return {
              apiName: 'taxZxSaEquipSettleAudit',
              otherParams: {
                zxSaEquipSettleAuditId: flowData?.zxSaEquipSettleAuditId
              }
            }
          }}
          formItemLayout={{
            labelCol: {
              xs: { span: 7 },
              sm: { span: 7 }
            },
            wrapperCol: {
              xs: { span: 17 },
              sm: { span: 17 }
            }
          }}
          formConfig={[
            {
              label: '项目名称',
              field: 'orgName',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '机械出租方',
              field: 'secondName',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '合同编号',
              field: 'contractNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '日期',
              field: "businessDate",
              span: 12,
              type: 'date',
              disabled: true,
            },
            {
              label: '结算单编号',
              field: 'signedNos',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              type: 'qnnTable',
              field: 'zxSaEquipResSettleItemList',
              incToForm: true,
              qnnTableConfig: {
                antd: {
                  rowKey: 'zxSaEquipResSettleItemId',
                  size: 'small'
                },
                desc: '清单页面所有金额单位为：元',
                paginationConfig: {
                  position: 'bottom'
                },
                isShowRowSelect: false,
                actionBtns: [
                  {
                    name: 'diyaddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '导出',
                    field: "add",
                    onClick: () => {
                      confirm({
                        content: '确定导出数据吗?',
                        onOk: () => {
                          var URL = `${ureport}excel?_u=minio:taxZxSaEquipSettleAuditList.xml&access_token=${access_token}&zxSaEquipSettleAuditId=${flowData?.zxSaEquipSettleAuditId}&_n=${flowData?.billNo}-设备租赁类营改增一览`;
                          window.open(URL);
                        }
                      })
                    },
                  },
                ],
                formConfig: [
                  {
                    table: {
                      title: '设备名称',
                      dataIndex: 'equipName',
                      key: 'equipName',
                      width: 200,
                    }
                  },
                  {
                    table: {
                      title: '规格型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 200,
                    }
                  },
                  {
                    table: {
                      title: '租赁开始时间',
                      dataIndex: 'startDate',
                      key: 'startDate',
                      format: 'YYYY-MM-DD',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                    },
                  },
                  {
                    table: {
                      title: '含税单价',
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
                      width: 120,
                    },
                  },
                  {
                    table: {
                      title: '不含税单价',
                      width: 120,
                      dataIndex: 'contractPriceNoTax',
                      key: 'contractPriceNoTax'
                    },
                  },
                  {
                    table: {
                      title: '税金',
                      width: 120,
                      dataIndex: 'contractTaxes',
                      key: 'contractTaxes'
                    },
                  },
                  {
                    table: {
                      title: '原合同',
                      width: 330,
                      dataIndex: 'zxCtFsContractId',
                      key: 'zxCtFsContractId',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'contractQty',
                          key: 'contractQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'contractAmt',
                          key: 'contractAmt',
                          width: 80,
                          render: (data, rowData) => {
                            if (!rowData.contractQty && rowData.contractQty !== 0) {
                              return null
                            } else {
                              return rowData.contractAmt
                            }
                          }
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'contractAmtNoTax',
                          key: 'contractAmtNoTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.contractQty && rowData.contractQty !== 0) {
                              return null
                            } else {
                              return rowData.contractAmtNoTax
                            }
                          }
                        },
                        {
                          title: '税额',
                          dataIndex: 'contractAmtTax',
                          key: 'contractAmtTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.contractQty && rowData.contractQty !== 0) {
                              return null
                            } else {
                              return rowData.contractAmtTax
                            }
                          }
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更合同',
                      width: 330,
                      dataIndex: 'contractNo',
                      key: 'contractNo',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'changedQty',
                          key: 'changedQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'changedAmt',
                          key: 'changedAmt',
                          width: 80,
                          render: (data, rowData) => {
                            if (!rowData.changedQty && rowData.changedQty !== 0) {
                              return null
                            } else {
                              return rowData.changedAmt
                            }
                          }
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'changedAmtNoTax',
                          key: 'changedAmtNoTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.changedQty && rowData.changedQty !== 0) {
                              return null
                            } else {
                              return rowData.changedAmtNoTax
                            }
                          }
                        },
                        {
                          title: '税额',
                          dataIndex: 'changedAmtTax',
                          key: 'changedAmtTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.changedQty && rowData.changedQty !== 0) {
                              return null
                            } else {
                              return rowData.changedAmtTax
                            }
                          }
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '本期',
                      width: 330,
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'thisQty',
                          key: 'thisQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'thisAmt',
                          key: 'thisAmt',
                          width: 80,
                          render: (data, rowData) => {
                            if (!rowData.thisQty && rowData.thisQty !== 0) {
                              return null
                            } else {
                              return rowData.thisAmt
                            }
                          }
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'thisAmtNoTax',
                          key: 'thisAmtNoTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.thisQty && rowData.thisQty !== 0) {
                              return null
                            } else {
                              return rowData.thisAmtNoTax
                            }
                          }
                        },
                        {
                          title: '税额',
                          dataIndex: 'thisAmtTax',
                          key: 'thisAmtTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.thisQty && rowData.thisQty !== 0) {
                              return null
                            } else {
                              return rowData.thisAmtTax
                            }
                          }
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至上期末累计',
                      width: 330,
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'upQty',
                          key: 'upQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'upAmt',
                          key: 'upAmt',
                          width: 80,
                          render: (data, rowData) => {
                            if (!rowData.upQty && rowData.upQty !== 0) {
                              return null
                            } else {
                              return rowData.upAmt
                            }
                          }
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'upAmtNoTax',
                          key: 'upAmtNoTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.upQty && rowData.upQty !== 0) {
                              return null
                            } else {
                              return rowData.upAmtNoTax
                            }
                          }
                        },
                        {
                          title: '税额',
                          dataIndex: 'upAmtTax',
                          key: 'upAmtTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.upQty && rowData.upQty !== 0) {
                              return null
                            } else {
                              return rowData.upAmtTax
                            }
                          }
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至本期末累计',
                      width: 330,
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'totalQty',
                          key: 'totalQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'totalAmt',
                          key: 'totalAmt',
                          width: 80,
                          render: (data, rowData) => {
                            if (!rowData.totalQty && rowData.totalQty !== 0) {
                              return null
                            } else {
                              return rowData.totalAmt
                            }
                          }
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'totalAmtNoTax',
                          key: 'totalAmtNoTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.totalQty && rowData.totalQty !== 0) {
                              return null
                            } else {
                              return rowData.totalAmtNoTax
                            }
                          }
                        },
                        {
                          title: '税额',
                          dataIndex: 'totalAmtTax',
                          key: 'totalAmtTax',
                          width: 90,
                          render: (data, rowData) => {
                            if (!rowData.totalQty && rowData.totalQty !== 0) {
                              return null
                            } else {
                              return rowData.totalAmtTax
                            }
                          }
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '税率(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '是否抵扣',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      render: (h) => {
                        return h === '1' ? '是' : '否'
                      }
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '备注',
                      width: 200,
                      dataIndex: 'remark',
                      key: 'remark',
                    },
                    isInForm: false
                  },
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