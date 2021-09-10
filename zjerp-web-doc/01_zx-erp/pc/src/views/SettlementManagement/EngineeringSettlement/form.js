import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Drawer, Modal, Spin } from "antd";
import ManageContract from './manageContract';
import moment from 'moment';
let config = {
  //流程专属配置   
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['工程类结算', 'contractName', 'billNo'],
    apiNameByAdd: "addZxSaProjectSettleAuditOnCommitFlow",
    apiNameByUpdate: "updateZxSaProjectSettleAuditOnCommitFlow",
    apiNameByGet: "getZxSaProjectSettleAuditDetail",
    flowId: 'zxSaProjectSettleAudit',
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  parameterName: 'orgID',
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "textAttachmentList",
  docFieldIsRequired: false,
  docIsReadOnly: false,
  formLayout: "leftDoc",
  formType: "descriptions",
  descriptionsConfig: {
    "layout": "horizontal",
    "column": 12,
    size: "small"
  },
  filesFieldName: "zxErpFileList",
  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      visible: false,
      visibleForPrint: false,
      loading: false,
      urlForprint: ''
    }
  }
  onClose = () => {
    this.setState({
      visible: false,
    });
  }
  handleCancel = () => {
    this.setState({ visibleForPrint: false, loading: false });
  }
  // 意见字段
  opinionFields = [
    { label: "项目各部门会签", field: "opinionField2" },
    { label: "项目领导会签", field: "opinionField3" },
    { label: "项目经理审批", field: "opinionField4" },
    { label: "公司结算中心审核", field: "opinionField5" },
    { label: "公司结算中心主任审批", field: "opinionField7" },
    { label: "公司总经审批", field: "opinionField6" },
    { label: "总部结算中心审核", field: "opinionField9" },
    { label: "总部各部门会签", field: "opinionField10" },
    { label: "总部领导会签", field: "opinionField11" },
    { label: "事业部审核", field: "opinionField12" },
    { label: "法律顾问审核", field: "opinionField13" },
    { label: "相关事业部审核", field: "opinionField14" },
    { label: "相关事业部领导审批", field: "opinionField15" },
    { label: "事业部领导审批", field: "opinionField16" },
    { label: "托管公司结算中心审核", field: "opinionField17" },
    { label: "托管公司各部门会签", field: "opinionField18" },
    { label: "托管公司领导审批", field: "opinionField19" },
    { label: "项目校对", field: "opinionField29" },
  ];

  render() {
    const { myPublic: { appInfo: { ureport } }, isInQnnTable, flowData = {} } = this.props
    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
    const { visible, loading } = this.state;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <Form
          {...this.props}
          {...config}
          wrappedComponentRef={(me) => {
            this.flowForm = me;
          }}
          upload={this.props.myUpload}
          initialValueByDocs={flowData?.textAttachmentList}
          initialValueByFiles={flowData?.zxErpFileList}
          formConfig={[
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'zxSaProjectSettleAudit',
              hide: true,
            },
            {
              field: 'contractName',
              type: 'string',
              initialValue: flowData?.contractName,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: flowData?.orgID,
              hide: true,
            },
            {
              field: 'id',
              type: 'string',
              initialValue: flowData?.id,
              hide: true,
            },
            {
              field: 'contractID',
              type: 'string',
              initialValue: flowData?.contractID,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'billType',
              type: 'select',
              qnnDisabled: true,
              label: '结算类型：',
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
              field: 'billNo',
              type: 'string',
              qnnDisabled: true,
              label: '结算单编号：',
              initialValue: flowData?.billNo,
              span: 12,
            },
            {
              field: 'orgName',
              type: 'string',
              qnnDisabled: true,
              label: '合同甲方：',
              initialValue: flowData?.orgName,
              span: 12,
            },
            {
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              label: '合同乙方：',
              initialValue: flowData?.secondName,
              span: 12,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号：',
              initialValue: flowData?.contractNo,
              span: 12,
            },
            {
              field: "secondName_1",
              type: 'string',
              qnnDisabled: true,
              label: '结算时限：',
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
              field: 'beginDate33',
              type: 'string',
              qnnDisabled: true,
              label: '是否简易计税：',
              span: 12,
              initialValue: '否'
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
              field: 'content',
              type: 'string',
              qnnDisabled: true,
              label: '结算内容及说明：',
              initialValue: flowData?.content,
              span: 12,
            },
            {
              type: 'qnnTable',
              field: 'table1',
              label: '统计项：',
              span: 24,
              qnnTableConfig: {
                rowKey: 'table1',
                fetchConfig: {
                  apiName: 'getZxSaProjectSettleAuditItemList',
                  otherParams: () => {
                    if (this.flowForm.rootBody?.workId) {
                      return {
                        projectSettleAuditId: this.state.apibody ? JSON.parse(this.state.apibody).id : ''
                      }
                    } else if (flowData?.id) {
                      return {
                        projectSettleAuditId: flowData.id
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
              field: 'reportPerson',
              type: 'string',
              qnnDisabled: true,
              label: '结算计算人：',
              initialValue: flowData?.reportPerson,
              span: 12,
            },
            {
              field: 'reCountPerson',
              type: 'string',
              qnnDisabled: true,
              label: '复核人：',
              initialValue: flowData?.reCountPerson,
              span: 12,
            },
            {
              field: "diyButton2",
              type: 'Component',
              label: '审批内容：',
              Component: obj => {
                let contractNo = ''
                let contractName = ''
                if (this.flowForm.rootBody?.workId) {
                  contractNo = this.state.apibody ? JSON.parse(this.state.apibody).contractNo : ''
                  contractName = this.state.apibody ? JSON.parse(this.state.apibody).contractName : ''
                } else if (flowData?.id) {
                  contractNo = flowData?.contractNo
                  contractName = flowData?.contractName
                }
                return <div
                  style={{
                    marginLeft: 25, marginTop: 10,
                    overflow: 'hidden',
                  }}
                >
                  <p
                    style={{ float: 'left', color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      this.setState({ visible: true })
                    }}
                  >{contractNo} - {contractName}</p>
                </div>
              },
              span: 24,
            },
            {
              field: "diyButton4",
              type: 'Component',
              label: '结算单：',
              Component: obj => {
                let billNo = ''
                let id = ''
                if (this.flowForm.rootBody?.workId) {
                  billNo = this.state.apibody ? JSON.parse(this.state.apibody).billNo : ''
                  id = this.state.apibody ? JSON.parse(this.state.apibody).id : ''
                } else if (flowData?.id) {
                  billNo = flowData?.billNo
                  id = flowData?.id
                }
                return <div
                  style={{
                    marginLeft: 25, marginTop: 10,
                    overflow: 'hidden',
                  }}
                >
                  <p
                    style={{ color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      let filter = [
                        '&projectSettleAuditId=' + id,
                        '&id=' + id
                      ]
                      let Url = `${ureport}preview?_u=minio:工程类结算单结算单.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 工程类结算单</p>
                </div>
              },
              span: 24,
            },
            {
              field: "diyButton3",
              type: 'Component',
              label: '结算表：',
              Component: obj => {
                let billNo = ''
                let id = ''
                if (this.flowForm.rootBody?.workId) {
                  billNo = this.state.apibody ? JSON.parse(this.state.apibody).billNo : ''
                  id = this.state.apibody ? JSON.parse(this.state.apibody).id : ''
                } else if (flowData?.id) {
                  billNo = flowData?.billNo
                  id = flowData?.id
                }
                return <div
                  style={{
                    marginLeft: 25, marginTop: 10,
                    overflow: 'hidden',
                  }}
                >
                  <p
                    style={{ color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      let filter = [
                        '&settleauditId=' + id,
                        '&id=' + id
                      ]
                      let Url = `${ureport}preview?_u=minio:工程类结算单结算表一览.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 工程类结算表一览</p>
                  <p
                    style={{ color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      let filter = [
                        '&settleauditId=' + id,
                        '&id=' + id
                      ]
                      let Url = `${ureport}preview?_u=minio:工程类结算单结算表打印版.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 工程类结算表打印版</p>
                  <p
                    style={{ color: 'rgb(24,144,255)' }}
                    onClick={() => {
                      let filter = [
                        '&settleauditId=' + id,
                        '&id=' + id
                      ]
                      let Url = `${ureport}preview?_u=minio:工程类结算单结算单营改增一览.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 工程类结算表营改增一览</p>
                </div>
              },
              span: 24,
            },
            {
              field: 'contractInitiationComments',
              type: 'textarea',
              label: '主要内容',
              opinionField: true,
              initialValue: flowData?.contractInitiationComments,
              span: 12,
            },
            ...this.opinionFields.map((item) => {
              return {
                label: item.label,
                field: item.field,
                type: "textarea",
                opinionField: true,
                addShow: false,
                span: 12,
              };
            }),
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          fieldsCURD={(obj, flowData) => {
            if (flowData?.apiData) {
              this.setState({ apibody: flowData.apiData })
            }
            return obj
          }}
        />
        {
          visible ? <Drawer
            title={'工程类合同管理'}
            placement="right"
            onClose={this.onClose}
            visible={visible}
            width={window.screen.width * 0.75}
            className={'drawerClass'}
          >
            <ManageContract {...this.props} flowData={flowData} apibody={this.state.apibody} />
          </Drawer> : null
        }
        <Modal
          width={'90%'}
          style={{ paddingBottom: '0', top: '0' }}
          title="打印"
          visible={this.state.visibleForPrint}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={this.handleCancel}
          bodyStyle={{ padding: '10px', overflow: 'hidden' }}
          centered={true}
          wrapClassName={'modals'}
        >
          {
            <Spin spinning={loading}>
              <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={() => {
                this.setState({ loading: false })
              }} src={this.state.urlForprint}></iframe>
            </Spin>
          }

        </Modal>
      </div>
    );
  }
}
export default index;