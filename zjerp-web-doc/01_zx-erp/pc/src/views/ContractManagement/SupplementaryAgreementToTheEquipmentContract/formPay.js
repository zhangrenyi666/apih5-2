import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import { Modal, Button } from 'antd';
import QnnForm from "../../modules/qnn-form";
let config = {
  //流程专属配置   
  parameterName: 'orgID',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    //后台定的字段
    title: ["设备采购类合同补充协议", 'contractName', 'contractNo'], //标题字段
    apiNameByAdd: "updateZxCtEqContrSupply",
    apiNameByUpdate: "updateZxCtEqContrSupply",
    apiNameByGet: "getZxCtEqContrSupplyDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList",
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
    }
  }
  handleCancelBjdh = () => {
    this.setState({ visibleBjdh: false });
  }
  render() {
    const { isInQnnTable, flowData = {} } = this.props;
    const { visibleBjdh } = this.state;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
        <Form
          {...this.props}
          {...config}
          wrappedComponentRef={(me) => {
              this.flowForm = me;
          }}
          upload={this.props.myUpload}
          formConfig={[
            {
              field: 'contrAlterID',
              type: 'string',
              hide: true,
              initialValue: flowData?.contrAlterID,
            },
            {
                field: 'orgID',
                type: 'string',
                initialValue: flowData?.orgID,
                hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'ZxCtSuppliesContrApplySBCGB',
              hide: true,
            },
            {
              field: 'zxCtEqContrSupplyId',
              type: 'string',
              initialValue: flowData?.zxCtEqContrSupplyId,
              hide: true,
            },
            {
              label: '补充协议编号',
              field: "contractNo",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.contractNo,
              span: 12,
            },
            {
              label: '补充协议名称',
              field: "supplyName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.supplyName,
              span: 12,
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '合同名称',
              initialValue: flowData?.contractName,
              span: 12,
            },
            {
              label: '合同类型',
              field: "contractType",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.contractType,
              span: 12,
            },
            {
              label: '甲方名称',
              field: "firstName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.firstName,
              span: 12,
            },
            {
              label: '乙方名称',
              field: "secondName",
              type: 'string',
              qnnDisabled: true,
              initialValue: flowData?.secondName,
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
                label: "主要内容",
                field: "opinionField19",
                type: "textarea",
                opinionField: true,
                opinionFieldByCreate: true, 
                span: 12,
            },
            {
                type: "textarea",
                label: "公司总经理审批",
                field: 'opinionField1',
                opinionField: true,
                addShow: false,
                span: 12,
            },
            {
                type: "textarea",
                label: "公司主管部门领导审批",
                field: 'opinionField2',
                opinionField: true,
                addShow: false,
                span: 12,
            },
            {
                type: "textarea",
                label: "公司各部门(含法律部)会签",
                field: 'opinionField3',
                opinionField: true,
                addShow: false,
                span: 12,
            },
            {
                type: "textarea",
                label: "合同校对",
                field: 'opinionField4',
                opinionField: true,
                addShow: false,
                span: 12,
            },
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'ZxCtSuppliesContrApplySBCGB'}
        />
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
                field: "zxCtOtherSupplyAgreementWorksList",
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxCtEqContrAlterItemResoureId',
                    size: 'small',
                    scroll: {
                      y: document.documentElement.clientHeight * 0.6
                    }
                  },
                  fetchConfig: {
                    apiName: 'getZxCtEqContrAlterItemResoureList',
                    otherParams: () => {
                      if (this.flowForm?.rootBody?.workId) {
                        return {
                          zxCtEqContrSupplyId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtEqContrSupplyId,
                          contrAlterID: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').contrAlterID
                        }
                      } else {
                        return {
                          zxCtEqContrSupplyId: flowData?.zxCtEqContrSupplyId,
                          contrAlterID: flowData?.contrAlterID
                        }
                      }
                    }
                  },
                  drawerConfig: {
                    width: '900px'
                  },
                  pageSize: 999,
                  paginationConfig: false,
                  isShowRowSelect: false,
                  formConfig: [
                    {
                      table: {
                        title: '变更类型',
                        dataIndex: 'alterType',
                        key: 'alterType',
                        width: 120,
                        align: 'center',
                        fixed: 'left',
                        render: (h) => {
                          if (h === '1') {
                            return '新增'
                          } else {
                            return '修改'
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: '编码',
                        dataIndex: 'catCode',
                        key: 'catCode',
                        width: 140,
                        align: 'center',
                        fixed: 'left',
                      }
                    },
                    {
                      table: {
                        title: '设备名称',
                        width: 150,
                        dataIndex: 'catName',
                        key: 'catName',
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: '型号',
                        width: 150,
                        dataIndex: 'spec',
                        key: 'spec',
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        title: ' 租赁单位',
                        dataIndex: 'rentUnit',
                        key: 'rentUnit',
                        width: 100,
                        align: 'center',
                      }
                    },
                    {
                      table: {
                        dataIndex: 'actualStartDate',
                        key: 'actualStartDate',
                        title: '租赁开始时间',
                        span: 12,
                        width: 150,
                        format: 'YYYY-MM-DD',
                      }
                    },
                    {
                      table: {
                        title: '租赁结束时间',
                        dataIndex: 'actualEndDate',
                        key: 'actualEndDate',
                        span: 12,
                        width: 150,
                        format: 'YYYY-MM-DD',
                      }
                    },
                    {
                      table: {
                        title: '数量',
                        dataIndex: 'qty',
                        key: 'qty',
                        width: 100,
                        align: 'center',
                      },
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
                        title: '不含税税额',
                        dataIndex: 'contractSumTax',
                        key: 'contractSumTax',
                        width: 120,
                        align: 'center',
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
                        title: '变更后租赁开始时间',
                        dataIndex: 'alterRentStartDate',
                        key: 'alterRentStartDate',
                        width: 180,
                        format: 'YYYY-MM-DD',
                      }
                    },
                    {
                      table: {
                        title: '变更后租赁结束时间',
                        dataIndex: 'alterRentEndDate',
                        key: 'alterRentEndDate',
                        width: 180,
                        format: 'YYYY-MM-DD',
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
                        title: '变更后含税金额',
                        dataIndex: 'alterAmt',
                        key: 'alterAmt',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.alterAmt ? rowData.alterAmt.toFixed(2) : 0
                        }
                      }
                    },
                    {
                      table: {
                        title: '变更后不含税金额',
                        dataIndex: 'alterAmtNoTax',
                        key: 'alterAmtNoTax',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.alterAmtNoTax ? rowData.alterAmtNoTax.toFixed(2) : 0
                        }
                      }
                    },
                    {
                      table: {
                        title: '变更后税额',
                        dataIndex: 'alterAmtTax',
                        key: 'alterAmtTax',
                        width: 150,
                        align: 'center',
                        render: (val, rowData) => {
                          return rowData.alterAmtTax ? rowData.alterAmtTax.toFixed(2) : 0
                        }
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
                        width: 180,
                        align: 'center',
                      }
                    }
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
