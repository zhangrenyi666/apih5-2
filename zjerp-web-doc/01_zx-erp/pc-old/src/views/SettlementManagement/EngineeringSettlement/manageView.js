import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Modal } from "antd";
const confirm = Modal.confirm;
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }
  render() {
    let { myPublic: { domain, appInfo: { ureport } }, flowData = {} } = this.props;
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
              apiName: 'getZxSaProjectSettleAuditYgzInfo',
              otherParams: {
                settleauditId: flowData?.id
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
              label: '乙方单位',
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
              label: '期次',
              field: 'period',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '结算表编号',
              field: 'signedNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              type: 'qnnTable',
              field: 'workSettleItemList',
              incToForm: true,
              qnnTableConfig: {
                antd: {
                  rowKey: 'projectWorkSettleId',
                  size: 'small'
                },
                paginationConfig: {
                  position: 'bottom'
                },
                isShowRowSelect: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
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
                          window.open(`${ureport}excel?_u=file:exportZxSaProjectSettleAuditYgzInfo.ureport.xml&url=${domain}&settleauditId=${flowData?.id}`);
                        }
                      })
                    },
                  },
                ],
                formConfig: [
                  {
                    table: {
                      title: '签认单编号',
                      dataIndex: 'signedNo',
                      key: 'signedNo',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '细目编号',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '细目名称',
                      dataIndex: 'workName',
                      key: 'workName',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '含税合同单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '不含税合同单价',
                      width: 150,
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '合同税金',
                      width: 120,
                      dataIndex: 'contractTax',
                      key: 'contractTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '原合同',
                      width: 400,
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
                          width: 110
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'contractAmtNoTax',
                          key: 'contractAmtNoTax',
                          width: 110
                        },
                        {
                          title: '税额',
                          dataIndex: 'contractAmtTax',
                          key: 'contractAmtTax',
                          width: 110
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更合同',
                      width: 400,
                      dataIndex: 'contractNo',
                      key: 'contractNo',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'changeQty',
                          key: 'changeQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'changeAmt',
                          key: 'changeAmt',
                          width: 110
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'changeAmtNoTax',
                          key: 'changeAmtNoTax',
                          width: 110
                        },
                        {
                          title: '税额',
                          dataIndex: 'changeAmtTax',
                          key: 'changeAmtTax',
                          width: 110
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '本期',
                      width: 400,
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
                          dataIndex: 'thisTotalAmt',
                          key: 'thisTotalAmt',
                          width: 110
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'thisTotalAmtNoTax',
                          key: 'thisTotalAmtNoTax',
                          width: 110
                        },
                        {
                          title: '税额',
                          dataIndex: 'thisTotalAmtTax',
                          key: 'thisTotalAmtTax',
                          width: 110
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至上期末累计',
                      width: 400,
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
                          dataIndex: 'upTotalAmt',
                          key: 'upTotalAmt',
                          width: 110
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'upTotalAmtNoTax',
                          key: 'upTotalAmtNoTax',
                          width: 110
                        },
                        {
                          title: '税额',
                          dataIndex: 'upTotalAmtTax',
                          key: 'upTotalAmtTax',
                          width: 110
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至本期末累计',
                      width: 400,
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'allQty',
                          key: 'totalQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'allTotalAmt',
                          key: 'allTotalAmt',
                          width: 110
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'allTotalAmtNoTax',
                          key: 'allTotalAmtNoTax',
                          width: 110
                        },
                        {
                          title: '税额',
                          dataIndex: 'allTotalAmtTax',
                          key: 'allTotalAmtTax',
                          width: 110
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
                      dataIndex: 'remarks',
                      key: 'remarks',
                      width: 200,
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