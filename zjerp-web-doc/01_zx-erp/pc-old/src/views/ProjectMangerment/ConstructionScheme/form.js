import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
let config = {
  //流程专属配置   
  parameterName:'projectId',
  workFlowConfig: {
    title: ['施组评审发起', 'contractName', 'contractNo'],
    apiNameByAdd: "zxScGroupSchemeReviewApply",
    apiNameByUpdate: "zxScGroupSchemeReviewApply",
    apiNameByGet: "getZxScGroupSchemeDetail",
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
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'flowId',
              type: 'string',
              initialValue: 'zxScGroupScheme',
              hide: true,
            },
            {
              field: 'zxScGroupSchemeId',
              type: 'string',
              initialValue: flowData?.zxScGroupSchemeId,
              hide: true,
            },
            {
              field: 'orgID',
              type: 'string',
              initialValue: flowData?.comId,
              hide: true,
            },
            {
              field: 'projectId',
              type: 'string',
              initialValue: flowData?.projectId,
              hide: true,
            },
            {
              field: 'comName',
              type: 'string',
              qnnDisabled: true,
              label: '单位名称',
              initialValue: flowData?.comName,
              span: 12
            },
            {
              field: 'projectName',
              type: 'string',
              qnnDisabled: true,
              label: '项目名称',
              initialValue: flowData?.projectName,
              span: 12
            },
            {
              field: 'schemeNo',
              type: 'string',
              qnnDisabled: true,
              label: '施组编号',
              initialValue: flowData?.schemeNo,
              span: 12
            },
            {
              field: 'schemeName',
              type: 'string',
              qnnDisabled: true,
              label: '施组名称',
              initialValue: flowData?.schemeName,
              span: 12
            },
            {
              field: 'projectType',
              type: 'select',
              qnnDisabled: true,
              label: '工程类别',
              initialValue: flowData?.projectType,
              span: 12,
              optionConfig: {
                label: 'label',
                value: 'value',
              },
              optionData: [
                { label: '公路工程', value: '0' },
                { label: '铁路工程', value: '1' },
                { label: '市政工程', value: '2' },
                { label: '轨道交通', value: '3' },
                { label: '交安设施', value: '4' },
                { label: '养护工程', value: '5' },
                { label: '汽车试验场', value: '6' },
                { label: '港口工程', value: '7' },
                { label: '房建工程', value: '8' },
                { label: '机场', value: '9' },
                { label: '其他工程', value: '10' },
              ],
            },
            {
              field: 'province',
              type: 'string',
              qnnDisabled: true,
              label: '所在省份',
              initialValue: flowData?.province,
              span: 12,
            },
            {
              field: 'contrDuration',
              type: 'string',
              qnnDisabled: true,
              label: '合同工期',
              initialValue: flowData?.contrDuration,
              span: 12,
            },
            {
              label: '计划开工日期',
              field: "planDate",
              type: 'date',
              qnnDisabled: true,
              span: 12,
              initialValue: flowData?.planDate,
            },
            {
              field: 'contractAmt',
              type: 'number',
              qnnDisabled: true,
              label: '合同金额(万元)',
              initialValue: flowData?.contractAmt,
              span: 12,
              // formItemLayout: {
              //   labelCol: {
              //     xs: { span: 9 },
              //     sm: { span: 9 }
              //   },
              //   wrapperCol: {
              //     xs: { span: 15 },
              //     sm: { span: 15 }
              //   }
              // },
            },
            {
              field: 'projManager',
              type: 'string',
              qnnDisabled: true,
              label: '项目经理',
              initialValue: flowData?.projManager,
              span: 12
            },
            {
              field: 'projEngineer',
              type: 'string',
              qnnDisabled: true,
              label: '项目总工',
              initialValue: flowData?.projEngineer,
              span: 12
            },
            {
              field: 'schemeAppro',
              type: 'string',
              qnnDisabled: true,
              label: '项目联系人',
              initialValue: flowData?.schemeAppro,
              span: 12
            },
            {
              field: 'engineerTel',
              type: 'string',
              qnnDisabled: true,
              label: '项目总工联系方式',
              initialValue: flowData?.engineerTel,
              span: 12
            },
            {
              field: 'approTel',
              type: 'string',
              qnnDisabled: true,
              label: '项目联系人联系方式',
              initialValue: flowData?.approTel,
              span: 12
            },
            {
              label: '上报日期',
              field: "bizDate",
              type: 'date',
              qnnDisabled: true,
              span: 12,
              initialValue: flowData?.bizDate,
            },
            {
              label: '评审结束时间',
              field: "passTime",
              type: 'date',
              qnnDisabled: true,
              span: 12,
              initialValue: flowData?.passTime,
            },
            {
              field: 'remark',
              type: 'textarea',
              qnnDisabled: true,
              label: '备注',
              initialValue: flowData?.remark,
              span: 12
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
            }
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'zxScGroupScheme'}
        />
      </div>
    );
  }
}
export default index;
