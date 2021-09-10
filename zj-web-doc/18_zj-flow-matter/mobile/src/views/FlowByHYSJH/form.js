import { Form } from "../modules/work-flow";
import React, { Component } from "react";
const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //流程特有配置
    title: ["meetingNameStr"], //标题字段
    apiNameByUpdate: "updateZjMeetingPlanFlow",
    apiNameByGet: "getZjMeetingPlanFlowDetailByWorkId",
    flowId: "ZjMeetingPlanSummary",
    formLink: {
      ZjMeetingPlanSummary: "FlowByZjMeetingPlanSummary"
    },
    //待办已办切换路由
    todo: "todoByZjMeetingPlanSummary",
    hasTodo: "hasTodoByZjMeetingPlanSummary"
  },
  formConfig: [
    {
      type: "string",
      label: "申请时间",
      field: "createTime",
      placeholder: "请输入",
      hide: true
    },
    {
      type: "string",
      label: "workId",
      field: "workId",
      hide: true,
      initialValue: function (obj) {
        return obj.match.params["workId"];
      }
    },
    {
      type: "string",
      label: "会议类型",
      field: "meetingType",
      placeholder: "无",
    },
    {
      type: "textarea",
      label: "费用组成",
      field: "costOf",
      placeholder:'无'
    },
    {
      type: "textarea",
      label: "办公室主任意见",
      field: "opinionField2",
      placeholder:'请输入...'
    },
    {
      type: "textarea",
      label: "公司主管领导意见",
      field: "opinionField3",
      placeholder:'请输入...'
    }
  ]
};
class index extends Component {
  render() {
    return (
      <div>
        <Form
          {...this.props}
          {...config}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
        />
      </div>
    );
  }
}
export default index;
