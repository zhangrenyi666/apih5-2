import { Form } from "../modules/work-flow";
import React, { Component } from "react";
const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //流程特有配置
    title: ["fallInYear"], //标题字段
    apiNameByUpdate: "updateZjMeetingPlanFallInFlow",
    apiNameByGet: "getZjMeetingPlanFallInFlowDetailByWorkId",
    flowId: "ZjMeetingPlanFallInSummary",
    formLink: {
      ZjMeetingPlanFallInSummary: "FlowByZjMeetingPlanFallInSummary"
    },
    //待办已办切换路由
    todo: "todoByZjMeetingPlanFallInSummary",
    hasTodo: "hasTodoByZjMeetingPlanFallInSummary"
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
      initialValue: function(obj) { 
        return obj.match.params["workId"];
      }
    },
    {
      label: '年度',
      field: 'fallInYear',
      type: 'string',
      placeholder: '请输入'
    },
    {
      label: '主办部门',
      field: 'oaUserName',
      type: 'string',
      placeholder: '请输入'
    },
    {
      label: '填报人',
      field: 'fallInUser',
      type: 'string',
      placeholder: '请输入'
    },
    {
      label: '填报时间',
      field: 'fallInTimeStr',
      type: 'string',
      placeholder: '请输入'
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
      label: '备注',
      field: 'fallInRemakes',
      type: 'string',
      placeholder: '请输入'
    },
    {
      type: "textarea",
      label: "部门领导意见",
      field: "opinionField1",
      placeholder: '请输入...',
    },
    {
      type: "textarea",
      label: "分管领导意见",
      field: "opinionField2",
      placeholder: '请输入...'
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
