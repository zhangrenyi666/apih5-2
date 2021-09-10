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
    let zxCtOtherManageId = ''
    if (flowData && flowData.zxCtOtherManageId) {
      zxCtOtherManageId = flowData.zxCtOtherManageId
    } else {
      zxCtOtherManageId = this.props.btnCallbackFn.form.getFieldValue('apiBody').zxCtOtherManageId
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
                  apiName: 'getZxCtOtherManageDetail',
                  otherParams: { zxCtOtherManageId }
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
                  type: 'string',
                  span: 12,
                  type: 'date',
                  disabled: true,
                },
                {
                  label: '合同名称',
                  field: "contractName",
                  type: 'string',
                  disabled: true,
                  span: 12,
                },
                {
                  label: '管理机构',
                  field: "orgName",
                  type: 'string',
                  span: 12,
                  disabled: true,
                },
                {
                  label: '合同类型',
                  field: "contractType",
                  initialValue: '其他类',
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同甲方',
                  field: "firstName",
                  span: 12,
                  type: 'string',
                  disabled: true,
                },
                {
                  label: '乙方名称',
                  field: 'secondName',
                  type: 'string',
                  span: 12,
                  disabled: true,
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
                  label: '甲方代表身份证',
                  field: "firstPrincipalIdCard",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方代表身份证',
                  field: "secondPrincipalIdCard",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '甲方联系电话',
                  field: "firstUnitTel",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方(固话)',
                  field: "secondUnitTel",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方(手机)',
                  field: "secondPhone",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方(传真)',
                  field: "secondFax",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '乙方性质',
                  field: "agent",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同录入类型',
                  field: "auditStatus",
                  type: 'select',
                  span: 12,
                  disabled: true,
                  optionConfig: {
                    label: 'label',
                    value: 'value',
                  },
                  optionData: [
                    {
                      label: '直接录入',
                      value: 'directInput'
                    },
                    {
                      label: '评审通过',
                      value: 'auditPassed'
                    }
                  ],
                },
                {
                  label: '合同状态',
                  field: "contractStatus",
                  type: 'select',
                  span: 12,
                  disabled: true,
                  optionConfig: {
                    label: 'label',
                    value: 'value',
                  },
                  optionData: [
                    {
                      label: '执行中',
                      value: '1'
                    },
                    {
                      label: '终止',
                      value: '3'
                    }
                  ],
                },
                {
                  label: '含税合同总价(万元)',
                  field: "contractCost",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '不含税合同总价(万元)',
                  field: "contractCostNoTax",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '合同税额(万元)',
                  field: "contractCostTax",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '变更后含税合同金额(万元)',
                  field: "alterContractSum",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '变更后不含税合同金额(万元)',
                  field: "alterContractSumNoTax",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '变更后合同税额(万元)',
                  field: "alterContractSumTax",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 12
                },
                {
                  label: '是否抵扣',
                  field: "isDeduct",
                  type: 'radio',
                  optionData: [
                    { label: '是', value: '1' },
                    { label: '否', value: '0' }
                  ],
                  initialValue: '0',
                  span: 12,
                  disabled: true
                },
                {
                  label: '结算情况',
                  field: "settleType",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同开始时间',
                  field: "planStartDate",
                  span: 12,
                  type: 'date',
                  disabled: true,
                },

                {
                  label: '合同结束时间',
                  field: "planEndDate",
                  span: 12,
                  type: 'date',
                  disabled: true,
                },
                {
                  label: '实际开始时间',
                  field: "actualStartDate",
                  disabled: true,
                  span: 12,
                  type: 'date',
                },
                {
                  label: '实际结束时间',
                  field: "actualEndDate",
                  disabled: true,
                  span: 12,
                  type: 'date',
                },
                {
                  label: '监理单位',
                  field: "consultative",
                  type: 'string',
                  disabled: true,
                  span: 12
                },
                {
                  label: '合同类别',
                  field: "contractCategory",
                  type: 'select',
                  span: 12,
                  disabled: true,
                  optionConfig: {
                    label: 'label',
                    value: 'value',
                  },
                  optionData: [
                    { label: '房屋租赁', value: 'FW' },
                    { label: '加工承揽', value: 'JG' },
                    { label: '技术咨询或服务', value: 'JS' },
                    { label: '其他合同', value: 'QT' },
                    { label: '行政办公用品采购', value: 'XZ' },
                    { label: '征地拆迁', value: 'ZC' },
                  ],
                },
                {
                  label: '合同内容',
                  field: 'content',
                  type: 'textarea',
                  disabled: true,
                  formItemLayout: {
                    labelCol: {
                      xs: { span: 4 },
                      sm: { span: 4 }
                    },
                    wrapperCol: {
                      xs: { span: 20 },
                      sm: { span: 20 }
                    }
                  }
                },
                {
                  label: '备注',
                  field: 'remark',
                  type: 'textarea',
                  disabled: true,
                  formItemLayout: {
                    labelCol: {
                      xs: { span: 4 },
                      sm: { span: 4 }
                    },
                    wrapperCol: {
                      xs: { span: 20 },
                      sm: { span: 20 }
                    }
                  }
                },
                {
                  label: '附件',
                  field: 'zxErpFileList',
                  type: 'files',
                  disabled: true,
                  fetchConfig: {
                    apiName: 'upload'
                  },
                  formItemLayout: {
                    labelCol: {
                      xs: { span: 4 },
                      sm: { span: 4 }
                    },
                    wrapperCol: {
                      xs: { span: 24 },
                      sm: { span: 24 }
                    }
                  }
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
                  apiName: 'getZxCtOtherWorksList',
                  otherParams: {
                    zxCtOtherManageId
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "conRevDetailId",
              }}
              paginationConfig={{ position: 'bottom' }}
              isShowRowSelect={false}
              firstRowIsSearch={false}
              formConfig={[
                {
                  table: {
                    title: '清单编号',
                    dataIndex: 'workNo',
                    key: 'workNo',
                    width: 170,
                    tooltip:20,
                    type: 'string',
                    align: 'center',
                  },
                },
                {
                  table: {
                    title: '清单名称',
                    width: 170,
                    tooltip:17,
                    type: 'string',
                    dataIndex: 'workName',
                    key: 'workName',
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '计量单位',
                    dataIndex: 'unit',
                    key: 'unit',
                    width: 100,
                    type: 'string',
                    align: 'center',
                  },
                },
                {
                  table: {
                    title: '合同数量',
                    dataIndex: 'qty',
                    key: 'qty',
                    width: 120,
                    type: 'number',
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '含税合同单价',
                    dataIndex: 'price',
                    key: 'price',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '不含税合同单价',
                    dataIndex: 'priceNoTax',
                    key: 'priceNoTax',
                    width: 150,
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
                    title: '是否抵扣',
                    dataIndex: 'isDeduct',
                    key: 'isDeduct',
                    width: 120,
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
                    title: '含税合同金额',
                    dataIndex: 'contractSum',
                    key: 'contractSum',
                    width: 150,
                    align: 'center',
                  }
                },

                {
                  table: {
                    title: '不含税合同金额',
                    dataIndex: 'contractSumNoTax',
                    key: 'contractSumNoTax',
                    width: 150,
                    align: 'center',
                  },
                },
                {
                  table: {
                    title: '变更后数量',
                    dataIndex: 'changeQty',
                    key: 'changeQty',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后含税单价',
                    dataIndex: 'changePrice',
                    key: 'changePrice',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后不含税单价',
                    dataIndex: 'changePriceNoTax',
                    key: 'changePriceNoTax',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后含税金额',
                    dataIndex: 'changeContractSum',
                    key: 'changeContractSum',
                    width: 150,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '变更后不含税金额',
                    dataIndex: 'changeContractSumNoTax',
                    key: 'changeContractSumNoTax',
                    width: 160,
                    align: 'center',
                  }
                },
                {
                  table: {
                    title: '备注',
                    dataIndex: 'remark',
                    key: 'remarks',
                    width: 150,
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
                  apiName: 'getZxCtOtherSupplyAgreementList',
                  otherParams: {
                    zxCtOtherManageId,
                    apih5FlowStatus: '2'
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtOtherSupplyAgreementId",
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
                    width: 180,
                    tooltip: 20
                  }
                },
                {
                  table: {
                    title: '补充协议名称',
                    dataIndex: 'proposer',
                    key: 'proposer',
                    filter: true,
                    fieldsConfig: { type: 'string' },
                    fixed: 'left',
                    width: 170,
                    tooltip: 17
                  }
                },
                {
                  table: {
                    title: '合同名称',
                    dataIndex: 'contractName',
                    key: 'contractName',
                    width: 170,
                    tooltip: 17
                  }
                },
                {
                  table: {
                    title: '合同类型',
                    dataIndex: 'contractType',
                    key: 'contractType',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '甲方名称',
                    dataIndex: 'firstName',
                    key: 'firstName',
                    width: 150,
                    filter: true,
                    fieldsConfig: { type: 'string' },
                    tooltip: 15
                  }
                },
                {
                  table: {
                    title: '乙方名称',
                    dataIndex: 'secondName',
                    key: 'secondName',
                    width: 150,
                    filter: true,
                    fieldsConfig: { type: 'string' },
                    tooltip: 12
                  }
                },
                {
                  table: {
                    title: '合同签订人',
                    dataIndex: 'agent',
                    key: 'agent',
                    width: 100,
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
                    dataIndex: 'applyAmount',
                    key: 'applyAmount',
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
                    width: 120,
                    format: 'YYYY-MM-DD',
                  }
                },
                {
                  table: {
                    title: '竣工日期',
                    dataIndex: 'endDate',
                    key: 'endDate',
                    width: 120,
                    format: 'YYYY-MM-DD',
                  }
                },
                {
                  table: {
                    title: '发起人',
                    dataIndex: 'beginPer',
                    key: 'beginPer',
                    width: 120
                  }
                },
                {
                  table: {
                    title: '评审状态',
                    dataIndex: 'apih5FlowStatus',
                    key: 'apih5FlowStatus',
                    width: 120,
                    render(h) {
                      if (h === '0') {
                        return '未评审'
                      } else if (h === '1') {
                        return '评审中'
                      } else if (h === '2') {
                        return '评审完成'
                      }
                    },
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
                  apiName: 'getZxCtOtherManageAmtRateList',
                  otherParams: {
                    zxCtOtherManageId
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtOtherManageAmtRateId",
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
                    width: 100,
                    initialValue: 0
                  }
                },
                {
                  table: {
                    title: '期限',
                    dataIndex: 'timeLimit',
                    key: 'timeLimit',
                    width: 150,
                  },
                },
                {
                  table: {
                    title: '返还条件',
                    dataIndex: 'backCondition',
                    key: 'backCondition',
                    width: 150,
                  },
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