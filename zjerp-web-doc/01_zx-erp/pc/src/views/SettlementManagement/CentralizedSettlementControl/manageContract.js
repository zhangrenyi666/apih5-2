import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import QnnTable from "../../modules/qnn-table";
import TabTwo from './detaiTabTwo'
import { Tabs } from 'antd';
const { TabPane } = Tabs;
const shortLabel = {
  labelCol: {
    sm: { span: 3 }
  },
  wrapperCol: {
    sm: { span: 21 }
  }
}
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
      this.setState({ contractID: this.props.apibody ? JSON.parse(this.props.apibody).contractID :'' })
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
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              fetchConfig={() => {
                return {
                  apiName: 'getZxGcsgCtContractDetails',
                  otherParams: { ctContractId: contractID }
                }
              }}
              wrappedComponentRef={(me) => {
                this.formOne = me;
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
                  type: 'string',
                  label: '合同编号',
                  field: 'contractNo',
                  disabled: true,
                  span: 12
                },
                {
                  type: 'string',
                  label: '合同名称',
                  field: 'contractName',
                  disabled: true,
                  span: 12
                },
                {
                  type: 'select',
                  label: '合同类型',
                  field: 'contractManageType',
                  optionData: [
                    { label: "内部分包", value: "0" },
                    { label: "专业分包", value: "1" },
                    { label: "劳务分包", value: "2" },
                    { label: "总体分包", value: "3" },
                  ],
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '合同甲方',
                  field: 'firstName',
                  disabled: true,
                  span: 12,
                },
                {
                  type: 'string',
                  label: '乙方名称',
                  field: 'secondName',
                  disabled: true,
                  span: 12,
                },
                {
                  type: 'number',
                  label: '含税合同金额（万元）',
                  field: 'contractCost',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },
                {
                  type: 'number',
                  label: '不含税合同金额（万元）',
                  field: 'contractCostNoTax',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },
                {
                  type: 'number',
                  label: '合同税额（万元）',
                  field: 'contractCostTax',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },
                {
                  type: 'number',
                  label: '变更后含税合同金额（万元）',
                  field: 'alterContractSum',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },
                {
                  type: 'number',
                  label: '变更后不含税合同金额（万元）',
                  field: 'alterContractSumNoTax',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },
                {
                  type: 'number',
                  label: '变更后合同税额（万元）',
                  field: 'alterContractSumTax',
                  disabled: true,
                  span: 12,
                  initialValue: 0
                },

                {
                  type: 'radio',
                  label: '是否抵扣',
                  field: 'isDeduct',
                  disabled: true,
                  span: 12,
                  optionData: [
                    { label: "否", value: "0" },
                    { label: "是", value: "1" }
                  ]
                },
                {
                  type: 'date',
                  label: '签订日期',
                  field: 'signatureDate',
                  disabled: true,
                  span: 12,
                },

                {
                  type: 'string',
                  label: '项目经理',
                  field: 'projectManager',
                  disabled: true,
                  span: 12,
                },
                {
                  type: 'string',
                  label: '乙方代表',
                  field: 'secondPrincipal',
                  disabled: true,
                  span: 12,
                },
                {
                  type: 'phone',
                  label: '甲方联系电话',
                  field: 'firstUnitTel',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'phone',
                  label: '乙方（固话）',
                  field: 'secondUnitTel',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'phone',
                  label: '乙方（手机）',
                  field: 'secondPartyMobile',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '乙方（传真）',
                  field: 'secondPartyFax',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'select',
                  label: '乙方性质',
                  field: 'agent',
                  disabled: true,
                  span: 12,
                  optionData: [
                    { label: "单位", value: "0" },
                    { label: "个人", value: "1" },
                  ]
                },
                {
                  type: 'date',
                  label: '合同开工日期',
                  field: 'planStartDate',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'date',
                  label: '合同竣工日期',
                  field: 'planEndDate',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'select',
                  label: '合同录入类型',
                  field: 'audit',
                  disabled: true,
                  span: 12,
                  optionData: [
                    { label: "评审通过", value: "0" },
                  ]
                },
                {
                  type: 'select',
                  label: '合同状态',
                  field: 'contractStatus',
                  span: 12,
                  optionData: [
                    { label: "执行中", value: "0" },
                    { label: "终止", value: "1" },
                  ],
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '结算情况',
                  field: 'settleType',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '授权委托人姓名',
                  field: 'wtrName',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'textarea',
                  label: '合同内容',
                  field: 'content',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'textarea',
                  label: '备注',
                  field: 'remarks',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'identity',
                  label: '授权委托人身份证号',
                  field: 'wtrPhone',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '推荐人姓名',
                  field: 'referenceName',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'string',
                  label: '推荐人职务',
                  field: 'referencePost',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'phone',
                  label: '推荐人电话',
                  field: 'referencePhone',
                  span: 12,
                  disabled: true,
                },
                {
                  type: 'qnnTable',
                  label: '保证金明细',
                  field: 'coContractAmtRateList',
                  incToForm: true,
                  formItemStyle: {
                    marginLeft: 5
                  },
                  formItemLayout: shortLabel,
                  qnnTableConfig: {
                    tableTdEdit: false,
                    antd: {
                      rowKey: "ctCoContractAmtRateId",
                      size: 'small',
                    },
                    formConfig: [
                      {
                        table: {
                          title: '保证金',
                          dataIndex: 'statisticsName',
                          width: 120,
                        }
                      },
                      {
                        table: {
                          title: '扣除比例(%)',
                          dataIndex: 'statisticsRate',
                          width: 120,
                        }
                      },
                      {
                        table: {
                          title: '期限',
                          dataIndex: 'timeLimit',
                          width: 120,
                        }
                      },
                      {
                        table: {
                          title: '返回条件',
                          dataIndex: 'backCondition',
                        }
                      },
                    ],
                  }
                },
                {
                  type: 'files',
                  label: '附件',
                  field: 'commonAttachmentList',
                  disabled: true,
                  fetchConfig: { apiName: 'upload' },
                  max: 999,
                  span: 24,
                  formItemLayout: shortLabel,
                  formItemStyle: {
                    marginLeft: 5
                  },
                },
              ]}
            />
          </TabPane>
          <TabPane tab="清单明细" key="2">
            <TabTwo {...this.props} contractID={contractID} />
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
                  apiName: 'bxGetZxGcsgCtContrApplyList',
                  otherParams: {
                    ctContractId: contractID,
                    apih5FlowStatus: '2'
                  }
                }
              }}
              antd={{
                size: "small",
                rowKey: "zxCtFsSideAgreementId",
              }}
              paginationConfig={{ position: 'bottom' }}
              isShowRowSelect={false}
              formConfig={[
                {
                  table: {
                    title: '补充协议编号',
                    dataIndex: 'contractNo',
                    fixed: 'left',//固定到右边
                    width: 170,
                    tooltip: 20
                  },
                },
                {
                  table: {
                    title: '补充协议名称',
                    dataIndex: 'agreementName',
                    width: 170,
                    tooltip: 17
                  },
                },
                {
                  table: {
                    title: '合同名称',
                    dataIndex: 'contractName',
                    width: 170,
                    tooltip: 17
                  },
                },
                {
                  table: {
                    title: '合同类型',
                    dataIndex: 'contractType',
                    width: 150,
                  },
                },

                {
                  table: {
                    title: '甲方名称',
                    dataIndex: 'firstName',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '乙方名称',
                    dataIndex: 'secondName',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '合同签订人',
                    dataIndex: 'agent',
                    width: 120,
                  }
                },

                {
                  table: {
                    title: '合同含税金额（万元）',
                    dataIndex: 'contractCost',
                    width: 180,
                  }
                },
                {
                  table: {
                    title: '本期含税增减金额（万元）',
                    dataIndex: 'currentTaxAmount',
                    width: 200,
                  }
                },
                {
                  table: {
                    title: '变更后含税金额（万元）',
                    dataIndex: 'alterContractSum',
                    width: 200,
                  }
                },
                {
                  table: {
                    title: '开工日期',
                    dataIndex: 'startDate',
                    format: 'YYYY-MM-DD',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '竣工日期',
                    dataIndex: 'endDate',
                    format: 'YYYY-MM-DD',
                    width: 150,
                  }
                },
                {
                  table: {
                    title: '合同内容',
                    dataIndex: 'content',
                    width: 180,
                  },
                },
                {
                  table: {
                    title: '备注',
                    dataIndex: 'remarks',
                    width: 150,
                  },
                }
              ]}
            />
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default index;