import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import FormList from './formList'
let config = {
  //流程专属配置   
    parameterName:'orgID',
  workFlowConfig: {
    title: ['附属合同', 'contractName', 'contractNo'],
    apiNameByAdd: "updateZxCtFsContractReview",
    apiNameByUpdate: "updateZxCtFsContractReview",
    apiNameByGet: "getZxCtFsContractReviewDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "zxZhengWenFileList",
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
  }
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {

    }
  }
  render() {
    const { isInQnnTable, flowData = {} } = this.props;
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
              initialValue: 'ZxFsReviewWorkFlow',
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'contractReviewId',
              type: 'string',
              initialValue: flowData?.contractReviewId,
              hide: true,
            },
            {
              field: 'firstName',
              type: 'string',
              initialValue: flowData?.firstName,
              hide: true,
            },
            {
              field: 'comName',
              type: 'string',
              initialValue: flowData?.comName,
              hide: true,
            },
            {
              field: 'comId',
              type: 'string',
              initialValue: flowData?.comId,
              hide: true,
            },
            {
              field: 'orgName',
              type: 'string',
              initialValue: flowData?.comName,
              hide: true,
            },
            {
              field: 'orgId',
              type: 'string',
              initialValue: flowData?.orgId,
              hide: true,
            },
            {
              field: 'secondId',
              type: 'string',
              initialValue: flowData?.secondId,
              hide: true,
            },
            {
              field: 'zxCtFsContractId',
              type: 'string',
              initialValue: flowData?.zxCtFsContractId,
              hide: true,
            },
            {
              field: 'contractNo',
              type: 'string',
              qnnDisabled: true,
              label: '附属合同编号',
              initialValue: flowData?.contractNo,
              span: 12
            },
            {
              field: 'contractName',
              type: 'string',
              qnnDisabled: true,
              label: '附属合同名称',
              initialValue: flowData?.contractName,
              span: 12
            },
            {
              field: 'contractType',
              type: 'string',
              qnnDisabled: true,
              label: '合同类型',
              initialValue: flowData?.contractType,
              span: 12
            },
            {
              field: 'fromContractNo',
              type: 'string',
              qnnDisabled: true,
              label: '原合同编号',
              initialValue: flowData?.fromContractNo,
              span: 12
            },
            {
              field: 'code7',
              type: 'select',
              qnnDisabled: true,
              label: '合同类别',
              initialValue: flowData?.code7,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '物资其他类', value: '0' },
                { label: '物资运输类', value: '1' },
              ],
            },
            {
              field: 'firstId',
              type: 'select',
              qnnDisabled: true,
              label: '甲方名称',
              initialValue: flowData?.firstId,
              span: 12,
              optionConfig: {
                label: 'orgName',
                value: 'orgID',
              },
              fetchConfig: { apiName: 'getZxCtContractListByStatus' }
            },
            {
              field: 'secondName',
              type: 'string',
              qnnDisabled: true,
              label: '乙方名称',
              initialValue: flowData?.secondName,
              span: 12,
            },
            {
              field: 'agent',
              type: 'string',
              qnnDisabled: true,
              label: '合同签订人',
              initialValue: flowData?.agent,
              span: 12
            },
            {
              field: 'contractCost',
              type: 'number',
              qnnDisabled: true,
              label: '含税合同金额',
              initialValue: flowData?.contractCost,
              span: 12
            },
            {
              field: 'contractCostNoTax',
              type: 'number',
              qnnDisabled: true,
              label: '不含税合同金额',
              initialValue: flowData?.contractCostNoTax,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'contractCostTax',
              type: 'number',
              qnnDisabled: true,
              label: '合同税额',
              initialValue: flowData?.contractCostTax,
              placeholder: '请输入',
              span: 12,
            },
            {
              field: 'isDeduct',
              type: 'radio',
              qnnDisabled: true,
              label: '是否抵扣',
              initialValue: flowData?.isDeduct,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '否', value: '0' },
                { label: '是', value: '1' },
              ],
            },
            {
              field: 'csTimeLimit',
              type: 'string',
              qnnDisabled: true,
              label: '合同工期',
              initialValue: flowData?.csTimeLimit,
              span: 12
            },
            {
              field: 'createUserName',
              type: 'string',
              qnnDisabled: true,
              label: '发起人',
              initialValue: flowData?.createUserName,
              span: 12
            },
            {
              field: 'remarks',
              type: 'textarea',
              qnnDisabled: true,
              label: '备注',
              initialValue: flowData?.remarks,
              span: 12
            },
            {
              field: 'content',
              type: 'textarea',
              qnnDisabled: true,
              label: '合同内容',
              initialValue: flowData?.content,
              span: 12 
            },
            {
              label: '合同清单',
              field: "diyArea_4",
              type: 'Component',
              Component: obj => {
                console.log(obj);
                let paramsData = {}
                if (flowData?.contractReviewId) {
                  paramsData.contractReviewId = flowData.contractReviewId
                  paramsData.isToDo = false
                } else {
                  paramsData.contractReviewId = obj.initialValues.apiBody.contractReviewId
                  paramsData.isToDo = true
                  paramsData.flowStatus = obj.rowInfo.rows.flowStatus
                }
                return <FormList {...this.props} flowData={paramsData} />
              }
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
                  sm: { span: 4 }
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 20 }
                }
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
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
                  xs: { span: 3 },
                  sm: { span: 3 }
                },
                wrapperCol: {
                  xs: { span: 3 },
                  sm: { span: 20 }
                }
              }
            }
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'ZxFsReviewWorkFlow'}
        />
      </div>
    );
  }
}
export default index;
