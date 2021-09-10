import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import { Drawer, Modal, Spin } from "antd";
import ManageContract from './manageContract';
import moment from 'moment';
import s from './style.less';
let config = {
  //流程专属配置   
  parameterName: 'orgID',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['物资采购结算', 'contractName', 'billNo'],
    apiNameByAdd: "addZxCtSuppliesShopSettlementListFlow",
    apiNameByUpdate: "updateZxCtSuppliesShopSettlementListFlow",
    apiNameByGet: "getZxCtSuppliesShopSettlementListFlowDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList",
    flowId: 'ZxCtSuppliesShopSettlement',
  },
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "documentFileList",
  docIsReadOnly: false,
  formLayout: "leftDoc",
  formType: "descriptions",
  descriptionsConfig: {
    "layout": "horizontal",
    "column": 12,
    size: "small"
  },
  filesFieldName: "settlementFileList",
  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      visible: false,
      list: null,
      flowButtons: [],
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
    { label: "主要内容", field: "initiateFlowReview" },
    { label: "项目各部门会签", field: "opinionField2" },
    { label: "项目领导会签", field: "opinionField3" },
    { label: "项目经理审批", field: "opinionField4" },
    { label: "公司结算中心审核", field: "opinionField5" },
    { label: "公司结算中心主任审批", field: "opinionField6" },
    { label: "公司总经审批", field: "opinionField7" },
    { label: "总部结算中心审核", field: "opinionField8" },
    { label: "总部各部门会签", field: "opinionField9" },
    { label: "总部领导会签", field: "opinionField10" },
    { label: "托管公司结算中心审核", field: "opinionField11" },
    { label: "托管公司各部门会签", field: "opinionField12" },
    { label: "托管公司领导审批", field: "opinionField13" },
    { label: "事业部审核", field: "opinionField14" },
    { label: "相关事业部审核", field: "opinionField15" },
    { label: "相关事业部领导审批", field: "opinionField16" },
    { label: "事业部领导审批", field: "opinionField17" },
    { label: "项目校对", field: "opinionField18" }
  ];
  componentWillMount() {
    const { flowData } = this.props;
    let paramsArry = {
      workId: flowData.workId,
      apiName: "getZxCtSuppliesShopSettlementListFlowDetail",
      apiType: "POST",
      flowId: "ZxCtSuppliesShopSettlement",
      title: "物资采购类结算单-" + flowData.contractNo
    };
    this.props.myFetch('openFlowByReport', paramsArry).then(({ success, message, data }) => {
      if (success) {
        let list = JSON.parse(data.apiData);
        this.setState({
          list: list,
          flowButtons: data?.flowButtons || []
        });
      } else {
      }
    });
  }
  render() {
    const { myPublic: { appInfo: { ureport } }, isInQnnTable, flowData = {}, match: { url } } = this.props
    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
    const { visible, loading, list, flowButtons } = this.state;
    this.props.match.path = url + '/HasTodoList';
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }} className={s.root}>
        {list ? <Form
          {...this.props}
          {...config}
          wrappedComponentRef={(me) => {
            this.flowForm = me;
          }}
          todoListModuleShow={true}
          doctModuleShow={true}
          filesModuleShow={true}
          flowButtons={flowButtons}
          initialValueByDocs={list?.documentFileList}
          initialValueByFiles={list?.settlementFileList}
          formConfig={[
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'ZxCtSuppliesShopSettlement',
              hide: true,
            },
            {
              field: 'contractName',
              type: 'string',
              initialValue: list?.contractName,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: list?.orgID,
              hide: true,
            },
            {
              field: 'contractID',
              type: 'string',
              initialValue: list?.contractID,
              hide: true,
            },
            {
              field: 'zxCtSuppliesShopSettlementId',
              type: 'string',
              initialValue: list?.zxCtSuppliesShopSettlementId,
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: list?.workId,
              hide: true,
            },
            {
              field: 'billType',
              type: 'select',
              qnnDisabled: true,
              label: '结算类型：',
              initialValue: list?.billType,
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
              initialValue: list?.billNo,
              span: 12,
            },
            {
              field: 'orgName',
              type: 'string',
              qnnDisabled: true,
              label: '合同甲方：',
              initialValue: list?.orgName,
              span: 12,
            },
            {
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              label: '合同乙方：',
              initialValue: list?.secondName,
              span: 12,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号：',
              initialValue: list?.contractNo,
              span: 12,
            },
            {
              field: "secondName_1",
              type: 'string',
              qnnDisabled: true,
              label: '结算时限：',
              span: 12,
              initialValue: () => {
                let beginDate = new Date(moment(list?.beginDate).format());
                let endDate = new Date(moment(list?.endDate).format());
                let BeginDate = beginDate.getFullYear() + '-' + (beginDate.getMonth() + 1) + '-' + beginDate.getDate()
                let EndDate = endDate.getFullYear() + '-' + (endDate.getMonth() + 1) + '-' + endDate.getDate()
                let value = BeginDate + ' 至 ' + EndDate
                return value
              }
            },
            {
              label: '是否抵扣：',
              field: 'isDeduct',
              type: 'radio',
              optionData: [
                { label: '是', value: '1' },
                { label: '否', value: '0' }
              ],
              qnnDisabled: true,
              initialValue: list?.isDeduct ? list.isDeduct : '0',
              span: 12,
            },
            {
              field: 'content',
              type: 'string',
              qnnDisabled: true,
              label: '结算内容及说明：',
              initialValue: list?.content,
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
                  apiName: 'getZxCtSuppliesShopSettlementItemListByConID',
                  otherParams: () => {
                    if (this.flowForm.rootBody?.workId) {
                      return {
                        zxCtSuppliesShopSettlementId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtSuppliesShopSettlementId
                      }
                    } else {
                      return {
                        zxCtSuppliesShopSettlementId: flowData.zxCtSuppliesShopSettlementId
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
              field: 'countPerson',
              type: 'string',
              qnnDisabled: true,
              label: '计算人：',
              initialValue: list?.countPerson,
              span: 12,
            },
            {
              field: 'reCountPerson',
              type: 'string',
              qnnDisabled: true,
              label: '复核人：',
              initialValue: list?.reCountPerson,
              span: 12,
            },
            {
              field: 'remarks',
              type: 'string',
              qnnDisabled: true,
              label: '备注：',
              initialValue: list?.remarks,
              span: 12,
            },
            {
              field: "diyButton",
              type: 'component',
              label: '结算单明细：',
              Component: obj => {
                let billNo = ''
                let zxCtSuppliesShopSettlementId = ''
                if (this.flowForm.rootBody?.workId) {
                  billNo = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').billNo
                  zxCtSuppliesShopSettlementId = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtSuppliesShopSettlementId
                } else {
                  billNo = flowData?.billNo
                  zxCtSuppliesShopSettlementId = flowData?.zxCtSuppliesShopSettlementId
                }
                return <div>
                  <div
                    style={{ color: 'rgb(24,144,255)', marginLeft: 25, marginTop: 10, }}
                    onClick={() => {
                      let filter = [
                        '&zxCtSuppliesShopSettlementId=' + zxCtSuppliesShopSettlementId,
                        // '&_n=${billNo}-物资采购类本期结算金额计算表'
                      ]
                      let Url = `${ureport}preview?_u=minio:物资采购类本期结算金额计算表.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 物资采购类本期结算金额计算表</div>
                  <div
                    style={{ color: 'rgb(24,144,255)', marginLeft: 25, marginTop: 10, }}
                    onClick={() => {
                      let filter = [
                        '&zxCtSuppliesShopSettlementId=' + zxCtSuppliesShopSettlementId,
                        // '&_n=${billNo}-物资采购类本期结算金额汇总表'
                      ]
                      let Url = `${ureport}preview?_u=minio:物资采购类本期结算金额汇总表.xml&_t=1,4,5,6&access_token=${access_token}&delFlag=0${filter.join('')}`
                      this.setState({ urlForprint: Url }, () => {
                        this.setState({ visibleForPrint: true })
                      })
                    }}
                  >{billNo} - 物资采购类本期结算金额汇总表</div>
                </div>
              },
              span: 24,
            },
            {
              type: "component",
              field: "diyButton2",
              label: '信息查看：',
              Component: obj => {
                let contractNo = ''
                let contractName = ''
                if (this.flowForm.rootBody?.workId) {
                  contractNo = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').contractNo
                  contractName = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').contractName
                } else {
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
              span: 12,
            },
            ...this.opinionFields.map((item) => {
              return {
                label: item.label,
                field: item.field,
                type: "textarea",
                opinionField: true,
                qnnDisabled: true,
                span: 12,
                initialValue: () => {
                  let data = list && list[item.field] ? (list[item.field]).replace(/<br\/>/g, "\n") : '';
                  return data
                }
              };
            })
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          fieldsCURD={(obj, flowData) => {
            if (flowData?.apiData) {
              this.setState({ apibody: flowData.apiData })
            }

            return obj
          }}
        /> : null}
        {
          visible ? <Drawer
            title={'物资采购类合同管理'}
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
