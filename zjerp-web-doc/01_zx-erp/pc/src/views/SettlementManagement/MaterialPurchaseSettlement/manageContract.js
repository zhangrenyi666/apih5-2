import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import QnnTable from "../../modules/qnn-table";
import { Tabs } from 'antd';
const { TabPane } = Tabs;
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      activeKey: '1',
    }
  }
  tabsCallback = (activeKey) => {
    this.setState({
      activeKey
    })
  }
  componentDidMount() {
    const flowData = this.props.flowData
    if (flowData && flowData.contractID) {
      this.setState({ contractID: flowData.contractID })
    } else {
      this.setState({ contractID: this.props.apibody ? JSON.parse(this.props.apibody).contractID : '' })
    }
  }
  render() {
    const { activeKey, contractID } = this.state;
    return (
      <div style={{ padding: 15 }}>
        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
          <TabPane tab="合同信息" key="1">
            <QnnForm
              {...this.props}
              match={this.props.match}
              fetch={this.props.myFetch}
              fetchConfig={{
                apiName: 'getZxCtSuppliesContractDetail',
                otherParams: () => {
                  const flowData = this.props.flowData
                  let zxCtSuppliesContractId = ''
                  if (this.props.apibody) {
                    zxCtSuppliesContractId = this.props.apibody ? JSON.parse(this.props.apibody).contractID : ''
                  } else if (flowData && flowData.contractID) {
                    zxCtSuppliesContractId = flowData.contractID
                  }
                  return { zxCtSuppliesContractId }
                }
              }}
              wrappedComponentRef={(me) => {
                this.formOne = me;
              }}
              formItemLayout={{
                labelCol: {
                  xs: { span: 8 },
                  sm: { span: 8 }
                },
                wrapperCol: {
                  xs: { span: 16 },
                  sm: { span: 16 }
                }
              }}
              formConfig={[
                {
                  label: '合同编号',
                  field: "contractNo",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '签订时间',
                  field: "signatureDate",
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同名称',
                  field: "contractName",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同甲方',
                  field: "orgName",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同乙方',
                  field: "secondName",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '摘要',
                  field: "subject",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '项目经理',
                  field: "projectManager",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方代表',
                  field: "secondPrincipal",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '甲方联系电话',
                  field: "firstUnitTel",
                  type: 'phone',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同类别',
                  field: "code7",
                  type: 'select',
                  disabled: true,
                  optionConfig: {
                    label: 'itemName', //默认 label
                    value: 'itemId'
                  },
                  fetchConfig: {
                    apiName: 'getBaseCodeSelect',
                    otherParams: {
                      itemId: 'category_contr_type_CL'
                    }
                  },
                  span: 12
                },
                {
                  label: '物资来源',
                  field: "materialSource",
                  type: 'select',
                  disabled: true,
                  optionConfig: {
                    label: 'itemName', //默认 label
                    value: 'itemId'
                  },
                  fetchConfig: {
                    apiName: 'getBaseCodeSelect',
                    otherParams: {
                      itemId: 'category_contract_materialSource'
                    }
                  },
                  span: 12
                },
                {
                  label: '付款约定',
                  field: "paymentAssumpsit",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方（固话）',
                  field: "secondUnitTel",
                  type: 'specialPlane',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方性质',
                  field: "agent",
                  type: 'select',
                  optionConfig: {
                    label: 'label',
                    value: 'value'
                  },
                  optionData: [
                    {
                      label: '生产厂家',
                      value: '0'
                    },
                    {
                      label: '经销商',
                      value: '1'
                    }
                  ],
                  disabled: true,
                  span: 12
                },
                {
                  label: '含税合同总价（万元）',
                  field: "contractCost",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '不含税合同总价（万元）',
                  field: "contractCostNoTax",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同税额（万元）',
                  field: "contractCostTax",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '变更后含税合同金额（万元）',
                  field: "alterContractSum",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '变更后不含税合同金额（万元）',
                  field: "alterContractSumNoTax",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '变更后合同税额（万元）',
                  field: "alterContractSumTax",
                  type: 'number',
                  disabled: true,
                  span: 12
                },
                {
                  label: '是否抵扣',
                  field: "isDeduct",
                  span: 12,
                  type: 'radio',
                  optionData: [
                    { label: '是', value: '1' },
                    { label: '否', value: '0' }
                  ],
                  initialValue: '0',
                  disabled: true,
                },
                {
                  label: '装运方式约定',
                  field: "transferAssumpsit",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '其他条款',
                  field: "otherAssumpsit",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方（手机）',
                  field: "pp1",
                  type: 'phone',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方代表身份证',
                  field: "secondPrincipalIDCard",
                  type: 'identity',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方（传真）',
                  field: "pp2",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同开始日期',
                  field: "planStartDate",
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同结束日期',
                  field: "planEndDate",
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同工期（天）',
                  field: "csTimeLimit",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同录入类型',
                  field: "apih5FlowStatus",
                  disabled: true,
                  type: 'select',
                  optionConfig: {
                    label: 'label',
                    value: 'value'
                  },
                  optionData: [
                    {
                      label: '未审核',
                      value: ''
                    },
                    {
                      label: '审核中',
                      value: '1'
                    },
                    {
                      label: '已审核',
                      value: '2'
                    },
                    {
                      label: '退回',
                      value: '3'
                    }
                  ],
                  placeholder: '请选择',
                  span: 12
                },
                {
                  label: '合同状态',
                  field: "contractStatus",
                  type: 'select',
                  optionConfig: {
                    label: 'itemName', //默认 label
                    value: 'itemId'
                  },
                  optionData: [
                    {
                      itemName: '终止',
                      itemId: '10000'
                    },
                    {
                      itemName: '执行中',
                      itemId: '10001'
                    }
                  ],
                  disabled: true,
                  span: 12
                },
                {
                  label: '结算情况',
                  field: "settleType",
                  type: 'select',
                  optionConfig: {
                    label: 'label',
                    value: 'value'
                  },
                  optionData: [
                    {
                      label: '集中结算前终止',
                      value: '0'
                    },
                    {
                      label: '实际无结算',
                      value: '1'
                    },
                    {
                      label: '结算开始执行',
                      value: '2'
                    },
                    {
                      label: '已最终结算',
                      value: '3'
                    }
                  ],
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同内容',
                  field: "content",
                  type: 'string',
                  placeholder: '请输入',
                  span: 12,
                  disabled: true,
                },
                {
                  label: '备注',
                  field: "remarks",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
              ]}
            />
          </TabPane>
          <TabPane tab="清单明细" key="2">
            <QnnTable
              {...this.props}
              match={this.props.match}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              fetchConfig={() => {
                if (this.formOne?.form?.getFieldValue?.('code7') === 'WL') {
                  return {
                    apiName: 'getZxCtSuppliesContrLeaseListList',
                    otherParams: {
                      contractID
                    }
                  }
                } else {
                  return {
                    apiName: 'getZxCtSuppliesContrShopListList',
                    otherParams: {
                      contractID
                    }
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "id",
              }}
              paginationConfig={{ position: 'bottom' }}
              isShowRowSelect={false}
              firstRowIsSearch={false}
              formConfig={[
                {
                  table: {
                    title: '物资类别',
                    dataIndex: 'workType',
                    key: 'workType',
                    width: 150,
                    fixed: 'left'
                  }
                },
                {
                  table: {
                    title: '物资编码',
                    dataIndex: 'workNo',
                    key: 'workNo',
                    width: 180,
                    fixed: 'left'
                  }
                },
                {
                  table: {
                    title: '物资名称',
                    dataIndex: 'workName',
                    key: 'workName',
                    width: 180
                  }
                },
                {
                  table: {
                    title: '规格型号',
                    dataIndex: 'spec',
                    key: 'spec',
                    width: 150
                  }
                },
                {
                  table: {
                    title: '单位',
                    dataIndex: 'unit',
                    key: 'unit',
                    width: 100
                  }
                },
                {
                  table: {
                    title: '是否网价',
                    dataIndex: 'isNetPrice',
                    key: 'isNetPrice',
                    width: 100,
                    render: (data) => {
                      if (data === '1') {
                        return '是';
                      } else {
                        return '否';
                      }
                    }
                  }
                },
                {
                  isInTable: () => {
                    if (this.formOne?.form?.getFieldValue?.('code7') === 'WL') {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  table: {
                    title: '租期单位',
                    dataIndex: 'rentUnit',
                    key: 'rentUnit',
                    width: 100
                  },
                },
                {
                  isInTable: () => {
                    if (this.formOne?.form?.getFieldValue?.('code7') === 'WL') {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  table: {
                    title: '租期',
                    dataIndex: 'contrTrrm',
                    key: 'contrTrrm',
                    width: 100
                  },
                },
                {
                  table: {
                    title: '数量',
                    dataIndex: 'qty',
                    key: 'qty',
                    width: 100,
                    render: (data, rowData) => {
                      if (rowData.qty || rowData.qty === 0) {
                        return rowData.qty.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '含税单价',
                    dataIndex: 'price',
                    key: 'price',
                    width: 120,
                    render: (data, rowData) => {
                      if (rowData.price || rowData.price === 0) {
                        return rowData.price.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '含税金额',
                    dataIndex: 'contractSum',
                    key: 'contractSum',
                    width: 120,
                    render: (data, rowData) => {
                      if (rowData.contractSum || rowData.contractSum === 0) {
                        return rowData.contractSum.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '不含税单价',
                    dataIndex: 'priceNoTax',
                    key: 'priceNoTax',
                    width: 120,
                    render: (data, rowData) => {
                      if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                        return rowData.priceNoTax.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '不含税金额',
                    dataIndex: 'contractSumNoTax',
                    key: 'contractSumNoTax',
                    width: 120,
                    render: (data, rowData) => {
                      if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                        return rowData.contractSumNoTax.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '税率(%)',
                    dataIndex: 'taxRate',
                    key: 'taxRate',
                    width: 100,
                    type: 'select'
                  }
                },
                {
                  table: {
                    title: '是否抵扣',
                    dataIndex: 'isDeduct',
                    key: 'isDeduct',
                    width: 100,
                    render: (data) => {
                      if (data === '1') {
                        return '是';
                      } else {
                        return '否';
                      }
                    }
                  }
                },
                {
                  isInTable: () => {
                    if (this.formOne?.form?.getFieldValue?.('code7') === 'WL') {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  table: {
                    title: '变更后租期',
                    dataIndex: 'alterTrrm',
                    key: 'alterTrrm',
                    width: 120
                  },
                },
                {
                  table: {
                    title: '变更后数量',
                    dataIndex: 'changeQty',
                    key: 'changeQty',
                    width: 120,
                    render: (data, rowData) => {
                      if (rowData.changeQty || rowData.changeQty === 0) {
                        return rowData.changeQty.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '变更后含税单价',
                    dataIndex: 'changePrice',
                    key: 'changePrice',
                    width: 150,
                    render: (data, rowData) => {
                      if (rowData.changePrice || rowData.changePrice === 0) {
                        return rowData.changePrice.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '变更后不含税单价',
                    dataIndex: 'changePriceNoTax',
                    key: 'changePriceNoTax',
                    width: 150,
                    render: (data, rowData) => {
                      if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                        return rowData.changePriceNoTax.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '变更后含税金额',
                    dataIndex: 'changeContractSum',
                    key: 'changeContractSum',
                    width: 150,
                    render: (data, rowData) => {
                      if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                        return rowData.changeContractSum.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '变更后不含税金额',
                    dataIndex: 'changeContractSumNoTax',
                    key: 'changeContractSumNoTax',
                    width: 180,
                    render: (data, rowData) => {
                      if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                        return rowData.changeContractSumNoTax.toFixed(2)
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '变更日期',
                    dataIndex: 'changeDate',
                    key: 'changeDate',
                    width: 130,
                    format: 'YYYY-MM-DD'
                  }
                },
                {
                  table: {
                    title: '备注',
                    dataIndex: 'remarks',
                    key: 'remarks',
                    width: 150,
                  }
                }
              ]}
            />
          </TabPane>
          <TabPane tab="补充协议" key="3">
            <QnnTable
              {...this.props}
              match={this.props.match}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              fetchConfig={() => {
                return {
                  apiName: 'getZxCtSuppliesContrReplenishAgreementListByContrId',
                  otherParams: {
                    pp6: contractID,
                    apih5FlowStatus: '2'
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "replenishAgreementId",
              }}
              paginationConfig={{ position: 'bottom' }}
              isShowRowSelect={false}
              firstRowIsSearch={false}
              formConfig={[
                {
                  table: {
                    title: '补充协议编号',
                    dataIndex: 'contractNo',
                    key: 'contractNo',
                    width: 170,
                    fixed: 'left'
                  }
                },
                {
                  table: {
                    title: '补充协议名称',
                    dataIndex: 'pp3',
                    key: 'pp3',
                    width: 170,
                    fixed: 'left',
                  }
                },
                {
                  table: {
                    title: '合同名称',
                    dataIndex: 'contractName',
                    key: 'contractName',
                    width: 180,
                  }
                },
                {
                  table: {
                    title: '合同类型',
                    dataIndex: 'contractType',
                    key: 'contractType',
                    width: 120
                  }
                },
                {
                  table: {
                    title: '甲方名称',
                    dataIndex: 'firstName',
                    key: 'firstName',
                    width: 170,
                    type: 'string'
                  }
                },
                {
                  table: {
                    title: '乙方名称',
                    dataIndex: 'secondName',
                    key: 'secondName',
                    width: 170,
                    type: 'string'
                  }
                },
                {
                  table: {
                    title: '合同签订人',
                    dataIndex: 'agent',
                    key: 'agent',
                    width: 120,
                  }
                },
                {
                  table: {
                    title: '含税合同金额（万元）',
                    dataIndex: 'contractCost',
                    key: 'contractCost',
                    width: 180,
                  }
                },
                {
                  table: {
                    title: '本期含税增减金额（万元）',
                    dataIndex: 'pp4',
                    key: 'pp4',
                    width: 200
                  }
                },
                {
                  table: {
                    title: '变更后含税金额（万元）',
                    dataIndex: 'alterContractSum',
                    key: 'alterContractSum',
                    width: 180
                  }
                },
                {
                  table: {
                    title: '是否抵扣',
                    dataIndex: 'isDeduct',
                    key: 'isDeduct',
                    width: 100,
                    render: (data) => {
                      if (data === '1') {
                        return '是';
                      } else {
                        return '否';
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '开工日期',
                    dataIndex: 'startDate',
                    key: 'startDate',
                    width: 120,
                    format: 'YYYY-MM-DD'
                  }
                },
                {
                  table: {
                    title: '竣工日期',
                    dataIndex: 'endDate',
                    key: 'endDate',
                    width: 120,
                    format: 'YYYY-MM-DD'
                  }
                },
                {
                  table: {
                    title: '发起人',
                    dataIndex: 'beginPer',
                    key: 'beginPer',
                    width: 130
                  }
                },
                {
                  table: {
                    title: '评审状态',
                    dataIndex: 'apih5FlowStatus',
                    key: 'apih5FlowStatus',
                    width: 120,
                    render: (data) => {
                      if (data === '2') {
                        return '评审通过';
                      } else {
                        return '--';
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '物资来源',
                    dataIndex: 'materialSource',
                    key: 'materialSource',
                    width: 120,
                    type: 'string'
                  },
                }
              ]}
            />
          </TabPane>
          <TabPane tab="保证金比例设置" key="4">
            <QnnTable
              {...this.props}
              match={this.props.match}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              fetchConfig={() => {
                return {
                  apiName: 'getZxCtSuppliesMarginRatioList',
                  otherParams: {
                    contractID
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtSuppliesMarginRatioId",
              }}
              paginationConfig={false}
              isShowRowSelect={false}
              firstRowIsSearch={false}
              formConfig={[
                {
                  table: {
                    title: '保证金',
                    dataIndex: 'statisticsName',
                    key: 'statisticsName',
                    width: 150,
                    initialValue: 0
                  }
                },
                {
                  table: {
                    title: '扣除比例%',
                    dataIndex: 'statisticsRate',
                    key: 'statisticsRate',
                    width: 120,
                    initialValue: 0
                  }
                },
                {
                  table: {
                    title: '期限',
                    dataIndex: 'timeLimit',
                    key: 'timeLimit',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '返还条件',
                    dataIndex: 'backCondition',
                    key: 'backCondition',
                    width: 150,
                  }
                },
              ]}
            />
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default index;