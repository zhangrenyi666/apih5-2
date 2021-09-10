import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Drawer, Modal, Button } from "antd";
import ManageContract from './manageContract';
import moment from 'moment';
const confirm = Modal.confirm;
let config = {
  //流程专属配置   
  workFlowConfig: {
    title: ['附属类结算单', 'contractName', 'billNo'],
    apiNameByAdd: "addZxSaFsSettlementApplyFlow",
    apiNameByUpdate: "addZxSaFsSettlementApplyFlow",
    apiNameByGet: "getZxSaFsSettlementDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList",
    flowId:'FsSelementWorkFlow'
  },
  parameterName:'orgID'
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      visible: false,
    }
  }
  onClose = () => {
    this.setState({
      visible: false,
    });
  };
  render() {
    const { myPublic: { domain, appInfo: { ureport } }, isInQnnTable, flowData = {} } = this.props;
    const { visible } = this.state;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <Form
          {...this.props}
          {...config}
          upload={this.props.myUpload}
          formConfig={[
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'FsSelementWorkFlow',
              hide: true,
            },
            {
              field: 'contractName',
              type: 'string',
              initialValue: flowData?.contractName,
              hide: true,
            },
            {
              field: 'zxSaFsSettlementId',
              type: 'string',
              initialValue: flowData?.zxSaFsSettlementId,
              hide: true,
            },
            {
              field: 'zxCtFsContractId',
              type: 'string',
              initialValue: flowData?.zxCtFsContractId,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'billNo',
              type: 'string',
              qnnDisabled: true,
              label: '结算单编号',
              initialValue: flowData?.billNo,
              span: 12,
            },
            {
              field: 'billType',
              type: 'select',
              qnnDisabled: true,
              label: '结算类型',
              initialValue: flowData?.billType,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '中期', value: '0' },
                { label: '最终', value: '1' },
                { label: '返还保证金', value: '2' }
              ],
            },
            {
              field: 'orgName',
              type: 'string',
              qnnDisabled: true,
              label: '合同甲方',
              initialValue: flowData?.orgName,
              span: 12,
            },
            {
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              label: '合同乙方',
              initialValue: flowData?.secondName,
              span: 12,
            },
            {
              label: '结算时限',
              field: "secondName_1",
              type: 'string',
              qnnDisabled: true,
              span: 12,
              initialValue: () => {
                let beginDate = new Date(moment(flowData?.beginDate).format());
                let endDate = new Date(moment(flowData?.endDate).format());
                let BeginDate = beginDate.getFullYear() + '-' + (beginDate.getMonth() + 1) + '-' + beginDate.getDate()
                let EndDate = endDate.getFullYear() + '-' + (endDate.getMonth() + 1) + '-' + endDate.getDate()
                let value = BeginDate + ' 至 ' + EndDate
                return value
              }
            },
            {
              label: '是否抵扣',
              field: 'isDeduct',
              type: 'radio',
              optionData: [
                { label: '是', value: '1' },
                { label: '否', value: '0' }
              ],
              qnnDisabled: true,
              initialValue: flowData.isDeduct ? flowData.isDeduct : '0',
              span: 12,
            },
            {
              field: "content",
              type: 'textarea',
              qnnDisabled: true,
              label: '结算内容及说明',
              initialValue: flowData?.content,
              span: 12,
            },
            {
              field: "diyButton2",
              type: 'Component',
              Component: obj => {
                return <Button
                  style={{
                    margin: 20, background: 'rgb(24,144,255)',
                    marginLeft: 10
                  }}
                  onClick={() => {
                    this.setState({ visible: true })
                  }}
                >
                  <p style={{ color: '#fff' }}>合同查询</p>
                </Button>
              },
              span: 24,
            },
            {
              field: "diyText",
              type: 'Component',
              Component: obj => {
                return <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>审批内容</div>
              },
              span: 24,
            },
            {
              field: "diyButton",
              type: 'Component',
              Component: obj => {
                let billNo = ''
                let zxSaFsSettlementId = ''
                if (flowData?.zxSaFsSettlementId) {
                  billNo = flowData?.billNo
                  zxSaFsSettlementId = flowData?.zxSaFsSettlementId
                } else {
                  billNo = obj.qnnFormInitialValues.apiBody.billNo
                  zxSaFsSettlementId = obj.qnnFormInitialValues.apiBody.zxSaFsSettlementId
                }
                return <div
                  style={{
                    marginLeft: 95, marginTop: 20,
                    overflow: 'hidden',
                  }}
                >
                  <p style={{ float: 'left' }}>结算单明细：</p>
                  <p
                    style={{ float: 'left', color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      confirm({
                        content: '确定下载该结算单吗?',
                        onOk: () => {
                          window.open(`${ureport}excel?_u=file:zxFsSellementDetail.ureport.xml&url=${domain}&zxSaFsSettlementId=${zxSaFsSettlementId}`);
                        }
                      })
                    }}
                  >{billNo} - 附属类结算单</p>
                </div>
              },
              span: 24,
            },
            {
              label: '附件',
              field: 'zxErpFileList',
              type: 'files',
              qnnDisabled: true,
              initialValue: flowData?.zxErpFileList,
              fetchConfig: {
                apiName: 'upload'
              },
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 21 }
                }
              }
            },
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: (obj) => {
                  if (flowData?.zxSaFsSettlementId) {
                    return {
                      apiName: 'getZxSaFsStatisticsDetailList',
                      otherParams: {
                        zxSaFsSettlementId: flowData?.zxSaFsSettlementId
                      }
                    }
                  } else if (obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.zxSaFsSettlementId) {
                    return {
                      apiName: 'getZxSaFsStatisticsDetailList',
                      otherParams: {
                        zxSaFsSettlementId: obj.qnnFormProps.qnnformData.initialValues.apiBody.zxSaFsSettlementId
                      }
                    }
                  }
                },
                paginationConfig: false,
                isShowRowSelect: false,
                pageSize: 999,
                formConfig: [
                  {
                    table: {
                      title: '统计项',
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 100,
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '本期（元）',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      width: 150,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                  },
                  {
                    table: {
                      title: '开累（元）',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 100,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                  },
                ],
              }
            },
            {
              label: "项目经营负责",
              field: 'opinionField1',
              type: 'textarea',
              opinionField: true,
              addShow: false,
              placeholder: '请输入',
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "项目总经济师",
              field: "opinionField2",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "项目经理",
              field: "opinionField3",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "上报总部",
              field: "opinionField4",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "上报总部(托管)",
              field: "opinionField5",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司审核人",
              field: "opinionField6",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司经营负责",
              field: "opinionField7",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "公司总经济师",
              field: "opinionField8",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
            {
              type: "textarea",
              label: "校对",
              field: "opinionField9",
              opinionField: true,
              addShow: false,
              formItemLayout: {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
              }
            },
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
        />
        {
          visible ? <Drawer
            title={'附属类合同管理'}
            placement="right"
            onClose={this.onClose}
            visible={visible}
            width={window.screen.width * 0.75}
            className={'drawerClass'}
          >
            <ManageContract {...this.props} flowData={flowData} />
          </Drawer> : null
        }
      </div>
    );
  }
}
export default index;
