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
          wrappedComponentRef={(me) => {
            this.formHasTicket = me;
          }}
          fetchConfig={() => {
            return {
              apiName: 'getZxSaFsSettlementReport',
              otherParams: {
                zxSaFsSettlementId: flowData?.zxSaFsSettlementId
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
              label: '结算表编号',
              field: 'signedNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '计算人',
              field: 'countPerson',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '复核人',
              field: 'reCountPerson',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              type: 'qnnTable',
              field: 'ureportList',
              incToForm: true,
              qnnTableConfig: {
                antd: {
                  rowKey: 'zxSaFsEnumerationSettlementDetailedId',
                  size: 'small'
                },
                desc: '清单页面所有金额单位为：元',
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
                          var URL = `${ureport}excel?_u=minio:YgzYiLanForm.ureport.xml&access_token=${access_token}&zxSaFsSettlementId=${flowData?.zxSaFsSettlementId}&_n=${flowData.billNo}附属类营改增一览`;
                          window.open(URL);
                        }
                      })
                    },
                  },
                ],
                formConfig: [
                  {
                    table: {
                      title: '清单编号',
                      dataIndex: 'equipCode',
                      key: 'equipCode',
                      width: 200,
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '结算内容',
                      dataIndex: 'content',
                      key: 'content',
                      width: 200,
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
                      title: '含税单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 120,
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '不含税单价',
                      width: 120,
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '税额',
                      width: 120,
                      dataIndex: 'contractSumTax',
                      key: 'contractSumTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后含税单价',
                      width: 150,
                      dataIndex: 'changePrice',
                      key: 'changePrice'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后不含税单价',
                      width: 150,
                      dataIndex: 'changePriceNoTax',
                      key: 'changePriceNoTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后税额',
                      width: 120,
                      dataIndex: 'changeTax',
                      key: 'changeTax'
                    },
                    isInForm: false
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
                          dataIndex: 'qty',
                          key: 'qty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'contractSum',
                          key: 'contractSum',
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'contractSumNoTax',
                          key: 'contractSumNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'contractSumTax',
                          key: 'contractSumTax',
                          width: 90
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
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'changedAmtNoTax',
                          key: 'changedAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'changeTax',
                          key: 'changeTax',
                          width: 90
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
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'thisAmtNoTax',
                          key: 'thisAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'thisAmtTax',
                          key: 'thisAmtTax',
                          width: 90
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
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'upAmtNoTax',
                          key: 'upAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'upTax',
                          key: 'upTax',
                          width: 90
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
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'totalAmtNoTax',
                          key: 'totalAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'totalTax',
                          key: 'totalTax',
                          width: 90
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