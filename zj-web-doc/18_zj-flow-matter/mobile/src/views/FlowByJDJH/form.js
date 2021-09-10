import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import moment from "moment";
let config = {
  //流程专属配置  ---在qnn-table中配后这也得配
  workFlowConfig: {
    //后台定的字段
    title: ["applyUnitId", "title_sendTime", "监督计划申请"], //标题字段
    apiNameByUpdate: "submitZjJdjhYearSupPlan",
    apiNameByGet: "getZjJdjhYearSupPlanDetails",
    flowId: "yearSupPlan",
    formLink: {
      yearSupPlan: "FlowByyearSupPlan",
      yearSupPlanExecutive: "FlowByyearSupPlanExecutive"
    },
    //待办已办切换路由
    todo: "todoByyearSupPlan",
    hasTodo: "hasTodoByyearSupPlan"
  },
  //qnn-form配置
  formConfig: [
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
      type: "string",
      label: "申请时间format",
      field: "title_sendTime",
      placeholder: "请输入",
      initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
      hide: true
    },

    {
      type: "string",
      label: "申请id",
      field: "yearSupPlanId",
      hide: true
    },
    {
      type: "string",
      label: "监督责任部门",
      field: "supDutyDepName", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
    },
	{
      type: "string",
      label: "填报人",
      field: "informant", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
    },
    {
      type: "select",
      label: "年度",
      field: "year", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      optionData: [
        //默认选项数据
        {
          name: "2019",
          id: "0"
        },
        {
          name: "2020",
          id: "1"
        },
        {
          name: "2021",
          id: "2"
        },
        {
          name: "2022",
          id: "3"
        },
        {
          name: "2023",
          id: "4"
        }
      ],
      optionConfig: {
        //下拉选项配置
        label: "name", //默认 label
        value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
      }
    },
    {
      type: "textarea",
      label: "部门总经理审批",
      field: "opinionField1",
      placeholder: "请输入", //占位符
    },
  ]
};
class index extends Component {
  render() {
    const { isInQnnTable } = this.props;
    return (
      <div style={{ height: isInQnnTable ? "" : "100vh" }}>
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
