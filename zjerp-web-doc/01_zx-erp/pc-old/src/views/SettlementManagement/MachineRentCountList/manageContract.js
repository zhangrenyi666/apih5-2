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
  render() {
    const { activeKey } = this.state;
    const flowData = this.props.flowData
    let contractID = ''
    if (flowData && flowData.contractID) {
      contractID = flowData.contractID
    } else {
      contractID = this.props.btnCallbackFn.form.getFieldValue('apiBody').contractID
    }
    return (
      <div style={{ padding: 15 }}>
        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
          <TabPane tab="合同信息" key="1">
            <QnnForm
              {...this.props}
              match={this.props.match}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              fetchConfig={() => {
                return {
                  apiName: 'getZxCtEqContractDetail',
                  otherParams: { contractID }
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
                  field: 'contractNo',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '签订时间',
                  field: 'signatureDate',
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同名称',
                  field: 'contractName',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同类型',
                  field: 'pp3',
                  type: 'string',
                  span: 12,
                  disabled: true
                },
                {
                  label: '合同甲方',
                  field: 'firstName',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方名称',
                  field: 'secondName',
                  type: 'string',
                  span: 12,
                  disabled: true,
                },
                {
                  label: '乙方性质',
                  field: 'agent',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '登记日期',
                  field: 'registerDate',
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '项目经理',
                  field: 'projectManager',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方代表',
                  field: 'secondPrincipal',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同结束时间',
                  field: 'planEndDate',
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '甲方联系电话',
                  field: 'firstUnitTel',
                  type: 'phone',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方(固话)',
                  field: 'secondUnitTel',
                  type: 'phone',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同类别',
                  field: 'contractCategory',
                  type: 'select',
                  optionConfig: {
                    label: 'label',
                    value: 'value'
                  },
                  optionData: [
                    { label: '设备租赁', value: '0' },
                    { label: '设备采购', value: '1' },
                  ],
                  disabled: true,
                  span: 12
                },
                {
                  label: '结算情况',
                  field: 'settleType',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '含税合同总价(万元)',
                  field: 'contractCost',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0,
                },
                {
                  label: '不含税合同总价(万元)',
                  field: 'contractCostNoTax',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0,
                },
                {
                  label: '合同税额(万元)',
                  field: 'contractCostTax',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0,
                },
                {
                  label: '变更后含税合同金额(万元)',
                  field: 'alterContractSum',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0,
                },
                {
                  label: '变更后不含税合同金额(万元)',
                  field: 'alterContractSumNoTax',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0
                },
                {
                  label: '变更后合同税额(万元)',
                  field: 'alterContractSumTax',
                  type: 'number',
                  disabled: true,
                  span: 12,
                  precision: 6,
                  initialValue: 0,
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
                  label: '乙方(手机)',
                  field: 'pp1',
                  type: 'phone',
                  disabled: true,
                  span: 12
                },

                {
                  label: '乙方传真',
                  field: 'pp2',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同工期',
                  field: 'csTimeLimit',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同录入类型',
                  field: 'auditStatus',
                  type: 'string',
                  disabled: true,
                  span: 12,
                },
                {
                  label: '合同状态',
                  field: 'contractStatus',
                  type: 'select',
                  disabled: true,
                  optionConfig: {
                    label: 'label',
                    value: 'value',
                  },
                  optionData: [
                    { label: '执行中', value: '1' },
                    { label: '终止', value: '3' }
                  ],
                  span: 12
                },
                {
                  label: '合同开始时间',
                  field: 'planStartDate',
                  type: 'date',
                  disabled: true,
                  span: 12
                },
                {
                  label: '租赁方式',
                  field: 'rentType',
                  type: 'select',
                  optionConfig: {
                    label: 'itemName', //默认 label
                    value: 'itemName'
                  },
                  optionData: [
                    {
                      itemName: '时间租赁'
                    },
                    {
                      itemName: '工程量租赁'
                    }
                  ],
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同内容',
                  field: 'content',
                  type: 'textarea',
                  disabled: true,
                  span: 12
                },
                {
                  label: '附件',
                  field: 'zxErpFileList',
                  type: 'files',
                  disabled: true,
                  fetchConfig: {
                    apiName: 'upload'
                  },
                  span: 12
                }
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
                return {
                  apiName: 'getZxCtEqContrItemListForContract',
                  otherParams: {
                    contractID
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
                  isInTable: this.formOne?.form.getFieldValue('contractCategory') === '1' ? true : false,
                  table: {
                    title: '设备分类',
                    dataIndex: 'catParentName',
                    key: 'catParentName',
                    width: 150,
                    fixed: 'left',
                    tooltip: 15,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '编号',
                    dataIndex: 'catCode',
                    key: 'catCode',
                    width: 150,
                    tooltip: 15,
                    fixed: 'left',
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '设备名称',
                    dataIndex: 'catName',
                    key: 'catName',
                    width: 150,
                    tooltip: 15,
                    fixed: 'left',
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: this.formOne?.form.getFieldValue('contractCategory') === '1' ? '规格型号' : '型号',
                    dataIndex: 'spec',
                    key: 'spec',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: this.formOne?.form.getFieldValue('contractCategory') === '1' ? '单位' : '租赁单位',
                    dataIndex: 'unit',
                    key: 'unit',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  isInTable: this.formOne?.form.getFieldValue('contractCategory') === '1' ? false : true,
                  table: {
                    title: '租赁开始时间',
                    dataIndex: 'actualStartDate',
                    key: 'actualStartDate',
                    format: 'YYYY-MM-DD',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  isInTable: this.formOne?.form.getFieldValue('contractCategory') === '0' ? true : true,
                  table: {
                    title: '租赁结束时间',
                    dataIndex: 'actualEndDate',
                    format: 'YYYY-MM-DD',
                    key: 'actualEndDate',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '数量',
                    dataIndex: 'qty',
                    key: 'qty',
                    width: 120,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '含税合同单价(元/台)',
                    dataIndex: 'price',
                    key: 'price',
                    width: 150,
                    align: 'center',
                    render: (val, obj) => {
                      if (obj.qty) {
                        return val
                      } else {
                        return 0
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '含税合同金额',
                    dataIndex: 'contractSum',
                    key: 'contractSum',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '不含税合同单价',
                    dataIndex: 'priceNoTax',
                    key: 'priceNoTax',
                    width: 120,
                    align: 'center',
                    render: (val, obj) => {
                      if (obj.qty) {
                        return val
                      } else {
                        return 0
                      }
                    }
                  }
                },
                {
                  table: {
                    title: '不含税合同金额',
                    dataIndex: 'contractSumNoTax',
                    key: 'contractSumNoTax',
                    width: 120,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '税率%',
                    dataIndex: 'taxRate',
                    key: 'taxRate',
                    width: 120,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后数量',
                    dataIndex: 'alterTrrm',
                    key: 'alterTrrm',
                    width: 120,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后含税单价',
                    dataIndex: 'alterPrice',
                    key: 'alterPrice',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后不含税单价',
                    dataIndex: 'alterPriceNoTax',
                    key: 'alterPriceNoTax',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后不含税金额',
                    dataIndex: 'alterAmtNoTax',
                    key: 'alterAmtNoTax',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后含税金额',
                    dataIndex: 'alterAmt',
                    key: 'alterAmt',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '是否抵扣',
                    dataIndex: 'isDeduct',
                    key: 'isDeduct',
                    width: 100,
                    align: 'center',
                    render: (h) => {
                      if (h === '1') {
                        return '是'
                      } else {
                        return '否'
                      }
                    },
                  }
                },
                {
                  table: {
                    title: '备注',
                    dataIndex: 'remark',
                    key: 'remark',
                    width: 150,
                    tooltip: 15,
                    align: 'center',
                  }
                },
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
                  apiName: 'getZxCtEqContrSupplyListBycontractID',
                  otherParams: {
                    contractID,
                    apih5FlowStatus: '2'
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtEqContrSupplyId",
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
                    filter: true,
                    fieldsConfig: { type: 'string' },
                    fixed: 'left',
                    width: 170,
                    tooltip: 20,
                  }
                },
                {
                  table: {
                    title: '补充协议名称',
                    dataIndex: 'supplyName',
                    key: 'supplyName',
                    filter: true,
                    fieldsConfig: { type: 'string' },
                    fixed: 'left',
                    width: 170,
                    tooltip: 17,
                  }
                },
                {
                  table: {
                    title: '合同名称',
                    dataIndex: 'contractName',
                    key: 'contractName',
                    width: 180,
                    tooltip: 18,
                  }
                },
                {
                  table: {
                    title: '合同类型',
                    dataIndex: 'contractType',
                    key: 'contractType',
                    width: 150,
                    initialValue: '机械管理类补充协议'
                  }
                },
                {
                  table: {
                    title: '甲方名称',
                    dataIndex: 'firstName',
                    key: 'firstName',
                    width: 170,
                    tooltip: 17,
                    filter: true,
                    fieldsConfig: { type: 'string' },
                  }
                },
                {
                  table: {
                    title: '乙方名称',
                    dataIndex: 'secondName',
                    key: 'secondName',
                    width: 170,
                    tooltip: 17,
                    filter: true,
                    fieldsConfig: { type: 'string' },
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
                    title: '含税合同金额',
                    dataIndex: 'contractCost',
                    key: 'contractCost',
                    width: 120,
                  }
                },
                {
                  table: {
                    title: '本期含税增减金额',
                    dataIndex: 'thisAmount',
                    key: 'thisAmount',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '变更后含税金额',
                    dataIndex: 'alterContractSum',
                    key: 'alterContractSum',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '是否抵扣',
                    dataIndex: 'isDeduct',
                    key: 'isDeduct',
                    width: 100,
                    render: (h) => {
                      if (h === '1') {
                        return '是'
                      } else {
                        return '否'
                      }
                    },
                  }
                },
                {
                  table: {
                    title: '开工日期',
                    dataIndex: 'startDate',
                    key: 'startDate',
                    width: 100,
                    format: 'YYYY-MM-DD',
                  }
                },
                {
                  table: {
                    title: '竣工日期',
                    dataIndex: 'endDate',
                    key: 'endDate',
                    width: 100,
                    format: 'YYYY-MM-DD',
                  }
                },
                {
                  table: {
                    title: '合同工期',
                    dataIndex: 'csTimeLimit',
                    key: 'csTimeLimit',
                    width: 100
                  }
                },
                {
                  table: {
                    title: '评审状态',
                    dataIndex: 'apih5FlowStatus',
                    key: 'apih5FlowStatus',
                    width: 100,
                    fixed: 'right',
                    render: (data) => {
                      if (data === '0') {
                        return '待提交';
                      } else if (data === '1') {
                        return '审核中';
                      } else if (data === '2') {
                        return '审核完成';
                      } else if (data === '3') {
                        return '退回';
                      } else if (data === '-1') {
                        return '未审核';
                      }
                    }
                  }
                },
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
                  apiName: 'getZxCtEqCoContractAmtRateList',
                  otherParams: {
                    contractID,
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtEqCoContractAmtRateId",
              }}
              paginationConfig={false}
              firstRowIsSearch={false}
              isShowRowSelect={false}
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