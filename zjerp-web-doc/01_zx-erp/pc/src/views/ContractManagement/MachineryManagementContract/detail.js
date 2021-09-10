import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Modal, Button } from 'antd';
import s from './style.less';
let config = {
  //流程专属配置   
  parameterName: 'firstID',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['设备租赁类合同评审', 'contractName', 'contractNo'],
    apiNameByAdd: "updateZxCtEqContrApply",
    apiNameByUpdate: "updateZxCtEqContrApply",
    apiNameByGet: "getZxCtEqContrApplyDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "zxErpMainFileList",
  docFieldIsRequired: true,
  docIsReadOnly: false,
  docFormFormItemLayout: {
    labelCol: {
      xs: { span: 3 },
      sm: { span: 3 }
    },
    wrapperCol: {
      xs: { span: 20 },
      sm: { span: 20 }
    }
  },

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
    super(props);
    this.state = {
      visibleBjdh: false,
      list: null,
      flowButtons: []
    }
  }
  handleCancelBjdh = () => {
    this.setState({ visibleBjdh: false });
  }
  componentWillMount() {
    const { flowData } = this.props;
    let paramsArry = {
      workId: flowData?.workId,
      apiName: "getZxCtEqContrApplyDetail",
      apiType: "POST",
      flowId: "zxCtEqContrApply",
      title: "设备租赁类合同评审-" + flowData.contractNo
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
    const { isInQnnTable = true, flowData, match: { url } } = this.props;
    const { visibleBjdh, list, flowButtons } = this.state;
    this.props.match.path = url + '/HasTodoList';
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }} className={s.root}>
        <div style={{ fontSize: 20, fontWeight: 700, textAlign: 'center' }} >合同会签单</div>
        {list ? <Form
          {...this.props}
          {...config}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          wrappedComponentRef={(me) => {
            this.flowForm = me;
          }}
          todoListModuleShow={true}
          doctModuleShow={true}
          filesModuleShow={true}
          flowButtons={flowButtons}
          initialValueByDocs={list?.zxErpMainFileList}
          initialValueByFiles={list?.zxErpFileList}
          formConfig={[
            {
              type: "string",
              label: "workId",
              field: "workId",
              hide: true,
              initialValue: function (obj) {
                console.log(obj.match.params);
                return obj.match.params["workId"];
              }
            },
            {
              field: 'firstID',
              type: 'string',
              initialValue: list?.firstID,
              hide: true,
            },
            {
              type: "string",
              field: "zxCtEqContrApplyId",
              initialValue: list?.zxCtEqContrApplyId,
              hide: true
            },
            {
              type: "string",
              field: "secondID",
              initialValue: list?.secondID,
              hide: true
            },
            {
              type: "string",
              field: "isDeduct",
              initialValue: list?.isDeduct,
              hide: true
            },
            {
              type: "string",
              field: "contractCategory",
              initialValue: list?.contractCategory,
              hide: true
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '合同编号',
              initialValue: list?.contractNo,
              span: 12,
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '合同名称',
              initialValue: list?.contractName,
              span: 12,
            },
            {
              field: 'firstName',
              type: 'string',
              qnnDisabled: true,
              label: '甲方名称',
              initialValue: list?.firstName,
              span: 12,
            },
            {
              label: '乙方名称',
              initialValue: list?.secondName,
              field: 'secondName',
              qnnDisabled: true,
              span: 12,
              type: 'string',
            },
            {
              field: 'pp3',
              type: 'string',
              qnnDisabled: true,
              label: '合同类型',
              initialValue: list?.pp3,
              span: 12,
            },
            {
              field: 'modelType',
              type: 'string',
              qnnDisabled: true,
              label: '正文模板类型',
              initialValue: ' ',
              span: 12,
            },
            {
              type: "component",
              field: "component66",
              label: '信息查看',
              Component: obj => {
                return (
                  <Button type="primary" onClick={() => {
                    this.setState({
                      visibleBjdh: true
                    })
                  }}>清单查看</Button>
                );
              },
              span: 12,
            },
            {
              field: 'editList',
              type: 'string',
              qnnDisabled: true,
              label: '清单编辑',
              initialValue: ' ',
              span: 12,
            },
            {
              field: 'opinionField19',
              type: 'textarea',
              qnnDisabled: true,
              label: '主要内容',
              span: 12,
              initialValue: () => {
                let data = list && list.opinionField19 ? (list.opinionField19).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "项目各部门会签",
              field: "opinionField1",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "项目领导会签",
              field: "opinionField2",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "公司主管部门审核",
              field: "opinionField3",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField3 ? (list.opinionField3).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "公司各部门(含法律部)会签",
              field: "opinionField5",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField5 ? (list.opinionField5).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "公司主管领导审批",
              field: "opinionField4",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "公司总经理审批",
              field: "opinionField6",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField6 ? (list.opinionField6).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "公司主管部门审核",
              field: "opinionField7",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField7 ? (list.opinionField7).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "总部各部门会签",
              field: "opinionField8",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField8 ? (list.opinionField8).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "总部领导",
              field: "opinionField9",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField9 ? (list.opinionField9).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "总部领导会签",
              field: "opinionField10",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField10 ? (list.opinionField10).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "托管公司主管部门审核",
              field: "opinionField11",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField11 ? (list.opinionField11).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "托管公司各部门会签",
              field: "opinionField12",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField12 ? (list.opinionField12).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "事业部审核",
              field: "opinionField13",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField13 ? (list.opinionField13).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "事业部领导审批",
              field: "opinionField14",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField14 ? (list.opinionField14).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "相关事业部审核",
              field: "opinionField15",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField15 ? (list.opinionField15).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "法律顾问审核",
              field: "opinionField16",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField16 ? (list.opinionField16).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "相关事业部领导审批",
              field: "opinionField17",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField17 ? (list.opinionField17).replace(/<br\/>/g, "\n") : '';
                return data
              }
            },
            {
              type: "textarea",
              label: "合同校对",
              field: "opinionField18",
              opinionField: true,
              span: 12,
              qnnDisabled: true,
              initialValue: () => {
                let data = list && list.opinionField18 ? (list.opinionField18).replace(/<br\/>/g, "\n") : '';
                return data
              }
            }
          ]}
          flowId={'zxCtEqContrApply'}
        /> : null}
        <Modal
          width='1200px'
          style={{ top: '0' }}
          title="详细信息"
          visible={visibleBjdh}
          footer={null}
          onCancel={this.handleCancelBjdh}
          bodyStyle={{ width: '1200px' }}
          centered={true}
          destroyOnClose={this.handleCancelBjdh}
          wrapClassName={'modals'}
        >
          <QnnForm
            fetch={this.props.myFetch}
            formConfig={[
              {
                type: "qnnTable",
                field: "zxSkLimitPriceItemList",
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxCtEqContrItemId',
                    size: 'small',
                    scroll: {
                      y: document.documentElement.clientHeight * 0.6
                    }
                  },
                  drawerConfig: {
                    width: '900px'
                  },
                  pageSize: 999,
                  paginationConfig: false,
                  isShowRowSelect: false,
                  fetchConfig: {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: () => {
                      if (this.flowForm.rootBody?.workId) {
                        return {
                          zxCtEqContrApplyId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtEqContrApplyId
                        }
                      } else {
                        return {
                          zxCtEqContrApplyId: flowData.zxCtEqContrApplyId
                        }
                      }
                    },
                  },
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxCtEqContrItemId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: '编号',
                        dataIndex: 'catCode',
                        key: 'catCode',
                        width: 150,
                        disabled: true,
                        fixed: 'left',
                        align: 'center',
                      },
                    },
                    {
                      table: {
                        title: '设备名称',
                        width: 150,
                        fixed: 'left',
                        dataIndex: 'catName',
                        align: 'center',
                        key: 'catName',
                      }
                    },
                    {
                      table: {
                        title: '型号',
                        dataIndex: 'spec',
                        key: 'spec',
                        width: 120,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '租赁单位',
                        dataIndex: 'rentUnit',
                        key: 'rentUnit',
                        width: 100,
                        align: 'center',
                      }
                    },
                    {
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
                        title: '合同数量',
                        dataIndex: 'qty',
                        key: 'qty',
                        width: 100,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '含税合同单价(元/台)',
                        dataIndex: 'price',
                        key: 'price',
                        width: 180,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '含税合同金额',
                        dataIndex: 'contractSum',
                        key: 'contractSum',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                        }
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
                        title: '不含税合同金额',
                        dataIndex: 'contractSumNoTax',
                        key: 'contractSumNoTax',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                        }
                      },
                    },
                    {
                      table: {
                        title: '税率(%)',
                        dataIndex: 'taxRate',
                        key: 'taxRate',
                        width: 100,
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
                          let isDeduct = '0'
                          if (this.flowForm.rootBody?.workId) {
                            isDeduct = this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').isDeduct
                          } else {
                            isDeduct = flowData.isDeduct
                          }
                          if (isDeduct === '1') {
                            return '是'
                          } else {
                            return '否'
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: '备注',
                        dataIndex: 'remark',
                        key: 'remark',
                        width: 150,
                        align: 'center',
                      }
                    },
                  ]
                }
              }
            ]}
          />
        </Modal>
      </div>
    );
  }
}
export default index;