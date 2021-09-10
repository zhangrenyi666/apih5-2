import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
import moment from 'moment';
let config = {
  //流程专属配置   
  parameterName: 'companyId',
  editDocCdnAddress: window.configs.ntkoAddress,
  workFlowConfig: {
    title: ['公司半年信用评价', 'companyName', "periodStr"],
    apiNameByAdd: "updateZxCrHalfYearCreditEvaAuditStatus",
    apiNameByUpdate: "updateZxCrHalfYearCreditEvaAuditStatus",
    apiNameByGet: "getZxCrHalfYearCreditEvaDetail",
    todo: "TodoList",
    hasTodo: "HasTodoList"
  },
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "zwFileList",
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
  //切换布局
  formLayout: "leftDoc",
  //是否使用描述式表单
  formType: "descriptions",
  descriptionsConfig: {
    "layout": "horizontal",
    "column": 12,
    size: "small"
  },
  //左侧公文附件字段名字  
  filesFieldName: "zxErpFileList",

  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
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
              field: 'flowId',
              type: 'string',
              initialValue: 'ZxCrHalfYearCreditEvaAudit',
              hide: true,
            },
            {
              field: 'workId',
              type: 'string',
              initialValue: flowData?.workId,
              hide: true,
            },
            {
              field: 'companyId',
              type: 'string',
              initialValue: flowData?.companyId,
              hide: true,
            },
            {
              field: 'zxCrHalfYearCreditEvaId',
              type: 'string',
              initialValue: flowData?.zxCrHalfYearCreditEvaId,
              hide: true,
            },

            {
              field: 'companyName',
              type: 'string',
              qnnDisabled: true,
              label: '机构名称',
              initialValue: flowData?.companyName,
              span: 12
            },
            {
              field: 'period',
              type: 'halfYear',
              qnnDisabled: true,
              label: '评价期次',
              initialValue: flowData?.period,
              span: 12
            },
            {
              field: 'periodStr',
              type: 'string',
              hide: true,
              initialValue: () => {
                let periodY = flowData.period ? moment(flowData?.period).format('YYYY') : ''
                let periodM = Number(flowData.period ? moment(flowData?.period).format('MM') : 0)
                let periodMM = periodM > 6 ? '/下半年' : '/上半年'
                return periodY + periodMM
              },
              span: 12
            },
            {
              label: "发起人意见",
              field: 'opinionField1',
              type: 'textarea',
              opinionField: true,
              addShow: false,
              placeholder: '请输入',
              span: 12,
            },
            {
              type: "textarea",
              label: "施工科意见",
              field: "opinionField2",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "经营科意见",
              field: "opinionField3",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "技术质量科意见",
              field: "opinionField4",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "安全科意见",
              field: "opinionField5",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "物设科意见",
              field: "opinionField6",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "财务科意见",
              field: "opinionField7",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "法律部门意见",
              field: "opinionField8",
              opinionField: true,
              addShow: false,
              span: 12,
            },
            {
              type: "textarea",
              label: "组长意见",
              field: "opinionField9",
              opinionField: true,
              addShow: false,
              span: 12,
            }
          ]}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          flowId={'ZxCrHalfYearCreditEvaAudit'}
        />
      </div>
    );
  }
}
export default index;
