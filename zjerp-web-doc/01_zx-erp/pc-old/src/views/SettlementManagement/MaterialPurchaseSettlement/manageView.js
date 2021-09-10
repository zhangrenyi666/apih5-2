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
              apiName: 'getZxCtSuppliesShopCampChangeIncrease',
              otherParams: {
                zxCtSuppliesShopSettlementId: flowData?.zxCtSuppliesShopSettlementId
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
              label: '合同编号',
              field: 'contractNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '结算表编号',
              field: 'billNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '签认单编号',
              field: 'signedNo',
              type: 'string',
              span: 12,
              disabled: true
            },
            {
              label: '日期',
              field: "reportDate",
              span: 12,
              type: 'date',
              disabled: true,
            },
            {
              label: '期次',
              field: "period",
              span: 12,
              type: 'string',
              disabled: true,
            },
            {
              type: 'qnnTable',
              field: 'campChangeIncreaseList',
              incToForm: true,
              qnnTableConfig: {
                antd: {
                  rowKey: 'id',
                  size: 'small'
                },
                drawerConfig: {
                  width: '1000px'
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
                          window.open(`${ureport}excel?_u=file:YgzYiLanForm.ureport.xml&url=${domain}&zxCtSuppliesLeaseSettlementListId=${flowData?.zxCtSuppliesLeaseSettlementListId}`);
                        }
                      })
                    },
                  },
                ],
                formConfig: [
                  {
                    table: {
                      title: '物资编码',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '物资名称',
                      dataIndex: 'workName',
                      key: 'workName',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '规格型号',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 200,
                      tooltip: 23
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '计量单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '原合同',
                      width: 680,
                      dataIndex: 'zxCtFsContractId',
                      key: 'zxCtFsContractId',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'qty',
                          key: 'qty',
                          width: 80
                        },
                        {
                          title: '含税单价',
                          dataIndex: 'price',
                          key: 'price',
                          width: 100
                        },
                        {
                          title: '不含税单价',
                          dataIndex: 'priceNoTax',
                          key: 'priceNoTax',
                          width: 120
                        },
                        {
                          title: '税金',
                          dataIndex: 'tax',
                          key: 'tax',
                          width: 100
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'contractSum',
                          key: 'contractSum',
                          width: 100
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'contractSumNoTax',
                          key: 'contractSumNoTax',
                          width: 100
                        },
                        {
                          title: '税额',
                          dataIndex: 'contractTax',
                          key: 'contractTax',
                          width: 100
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '变更合同',
                      width: 680,
                      dataIndex: 'contractNo',
                      key: 'contractNo',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'changeQty',
                          key: 'changeQty',
                          width: 80
                        },
                        {
                          title: '含税单价',
                          dataIndex: 'changePrice',
                          key: 'changePrice',
                          width: 100
                        },
                        {
                          title: '不含税单价',
                          dataIndex: 'priceNoTax',
                          key: 'priceNoTax',
                          width: 100
                        },
                        {
                          title: '税金',
                          dataIndex: 'changeTax',
                          key: 'changeTax',
                          width: 100
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'changeContractSum',
                          key: 'changeContractSum',
                          width: 100
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'changeContractSumNoTax',
                          key: 'changeContractSumNoTax',
                          width: 100
                        },
                        {
                          title: '税额',
                          dataIndex: 'changeContractTax',
                          key: 'changeContractTax',
                          width:100
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '本期',
                      width:680,
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'thisQty',
                          key: 'thisQty',
                          width: 80
                        },
                        {
                          title: '含税单价',
                          dataIndex: 'thisPrice',
                          key: 'thisPrice',
                          width: 100
                        },
                        {
                          title: '不含税单价',
                          dataIndex: 'thisPriceNoTax',
                          key: 'thisPriceNoTax',
                          width: 100
                        },
                        {
                          title: '税金',
                          dataIndex: 'thisTax',
                          key: 'thisTax;',
                          width: 100
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'thisAmt',
                          key: 'thisAmt',
                          width: 100
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'thisAmtNoTax',
                          key: 'thisAmtNoTax',
                          width: 100
                        },
                        {
                          title: '税额',
                          dataIndex: 'thisAmtTax',
                          key: 'thisAmtTax',
                          width: 100
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至上期末累计',
                      width:  680,
                      dataIndex: 'upAmt',
                      key: 'upAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'upQty',
                          key: 'upQty',
                          width: 80
                        },
                        {
                          title: '含税单价',
                          dataIndex: 'upPrice',
                          key: 'upPrice',
                          width: 100
                        },
                        {
                          title: '不含税单价',
                          dataIndex: 'upPriceNoTax',
                          key: 'upPriceNoTax',
                          width: 100
                        },
                        {
                          title: '税金',
                          dataIndex: 'upTax',
                          key: 'upTax',
                          width: 100
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'upAmt',
                          key: 'upAmt',
                          width: 100
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'upAmtNoTax',
                          key: 'upAmtNoTax',
                          width: 100
                        },
                        {
                          title: '税额',
                          dataIndex: 'upAmtTax',
                          key: 'upAmtTax',
                          width: 100
                        },
                      ]
                    },
                    isInForm: false
                  },
                  {
                    table: {
                      title: '至本期末累计',
                      width:  680,
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      children: [
                        {
                          title: '数量',
                          dataIndex: 'totalQty',
                          key: 'totalQty',
                          width: 80
                        },
                        {
                          title: '含税单价',
                          dataIndex: 'totalPrice',
                          key: 'totalPrice',
                          width: 100
                        },
                        {
                          title: '不含税单价',
                          dataIndex: 'totalNoTax',
                          key: 'totalNoTax',
                          width: 100
                        },
                        {
                          title: '税金',
                          dataIndex: 'totalTax',
                          key: 'totalTax',
                          width: 100
                        },
                        {
                          title: '含税金额',
                          dataIndex: 'totalAmt',
                          key: 'totalAmt',
                          width: 100
                        },
                        {
                          title: '不含税金额',
                          dataIndex: 'totalAmtNoTax',
                          key: 'totalAmtNoTax',
                          width: 100
                        },
                        {
                          title: '税额',
                          dataIndex: 'totalAmtTax',
                          key: 'totalAmtTax',
                          width: 100
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