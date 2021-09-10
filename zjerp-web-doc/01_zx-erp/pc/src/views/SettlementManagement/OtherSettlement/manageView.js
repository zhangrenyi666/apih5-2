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
              apiName: 'getUreportZxSaOtherEquipSettleList',
              otherParams: {
                zxCtOtherManageId: flowData?.zxCtOtherManageId,
                zxSaOtherEquipSettleId: flowData?.zxSaOtherEquipSettleId,
                initSerialNumber: flowData?.initSerialNumber
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
              disabled: true,
              initialValue: flowData.secondName
            },
            {
              label: '合同编号',
              field: 'contractNo',
              type: 'string',
              span: 12,
              disabled: true,
              initialValue: flowData.contractNo
            },
            {
              label: '结算表编号',
              field: 'billNo',
              type: 'string',
              span: 12,
              disabled: true,
              initialValue: flowData.billNo
            },
            {
              type: 'qnnTable',
              field: 'settleList',
              incToForm: true,
              qnnTableConfig: {
                antd: {
                  rowKey: 'id',
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
                          var URL = `${ureport}excel?_u=minio:zxSaOtherEquipSettleList_ygz.xml&access_token=${access_token}&zxSaOtherEquipSettleId=${flowData?.zxSaOtherEquipSettleId}&zxCtOtherManageId=${flowData?.zxCtOtherManageId}&_n=${flowData?.billNo}-其他类营改增一览`;
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
                      dataIndex: 'equipName',
                      key: 'equipName',
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
                      dataIndex: 'contractPrice',
                      key: 'contractPrice',
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
                      title: '税金',
                      width: 120,
                      dataIndex: 'contractPriceTax',
                      key: 'contractPriceTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后含税单价',
                      width: 150,
                      dataIndex: 'alterPrice',
                      key: 'alterPrice'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后不含税单价',
                      width: 150,
                      dataIndex: 'alterPriceNoTax',
                      key: 'alterPriceNoTax'
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更后税额',
                      width: 120,
                      dataIndex: 'alterPriceTax',
                      key: 'alterPriceTax'
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
                          dataIndex: 'contractQty',
                          key: 'contractQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'contractAmt',
                          key: 'contractAmt',
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'contractAmtNoTax',
                          key: 'contractAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'contractAmtTax',
                          key: 'contractAmtTax',
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
                          dataIndex: 'changeQty',
                          key: 'changeQty',
                          width: 70
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'changeAmt',
                          key: 'changeAmt',
                          width: 80
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'changeAmtNoTax',
                          key: 'changeAmtNoTax',
                          width: 90
                        },
                        {
                          title: '税额',
                          dataIndex: 'changeAmtTax',
                          key: 'changeAmtTax',
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
                          dataIndex: 'upAmtTax',
                          key: 'upAmtTax',
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
                          dataIndex: 'totalAmtTax',
                          key: 'totalAmtTax',
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
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '备注',
                      width: 200,
                      dataIndex: 'remarks',
                      key: 'remarks'
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