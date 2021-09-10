import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //后台定的字段
    title: ["operator", "sendTime", "纪委用印申请"], //标题字段
    apiNameByAdd: "addZjFlowJwSealApply",
    apiNameByUpdate: "updateZjFlowJwSealApply",
    apiNameByGet: "getZjFlowJwSealApplyDetail",
    flowId: "zjjwYongYin",
    formLink: {
      zjjwYongYin: "FlowByJwSeal"
    },
    //待办已办切换路由
    todo: "todoByzjjwYongYin",
    hasTodo: "hasTodoByzjjwYongYin"
  },

  formConfig: [
    {
      hide: true,
      type: "string",
      label: "申请时间",
      field: "sendTime",
      placeholder: "请输入",
      initialValue: moment().format("YYYY-MM-DD HH:mm:ss")
    },
    {
      type: "string",
      label: "用印单位",
      field: "sealUnit",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "发往单位",
      field: "sendUnit",
      placeholder: "请输入",
      required: true
    },
    {
      type: "textarea",
      label: "用印申请内容",
      field: "sealApplyContent",
      placeholder: "请输入",
      required: true
    },
	{
      type: "textarea",
      label: "内容简介",
      field: "contentIntroduce",
      placeholder: "请输入",
      required: true
    },
    {
      type: "select",
      label: "印别",
      placeholder: "请选择",
      field: "sealTypeOne",
      required: true,
      optionConfig: {
        label: "label",
        value: "value"
      },
      optionData: [
        {
          value: "0",
          label: "一公局集团"
        },
        {
          value: "1",
          label: "一公局"
        },
        {
          value: "2",
          label: "隧道局"
        }       
      ]
    },
	{
      type: "select",
      label: "印别",
      placeholder: "请选择",
      field: "sealTypeTwo",
      required: true,
      optionConfig: {
        label: "label",
        value: "value"
      },
      optionData: [
        {
          value: "0",
          label: "监察部印章"
        },
        {
          value: "1",
          label: "纪委印章"
        },
        {
          value: "2",
          label: "监事会印章"
        }       
      ]
    },
    {
      type: "number",
      label: "用印次数",
      placeholder: "请输入",
      field: "useNumber",
      max: 99,
      required: true
    },
    {
      type: "string",
      field: "operator",
      label: "经办人",
      required: true,
      placeholder: "请输入经办人姓名"
    },
    {
      type: "string",
      label: "联系电话",
      placeholder: "请输入",
      field: "phone",
      required: true
    },
    {
      type: "files",
      label: "附件",
      field: "fileList",
      desc: "点击选择上传", //默认 点击或者拖动上传
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
      }
    },
    {
      type: "textarea",
      label: "部门领导审批意见",
      field: "opinionField1",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司领导审批意见",
      field: "opinionField2",
      addShow: false
    },
    {
      type: "textarea",
      label: "部门领导（局）审批意见",
      field: "opinionField3",
      addShow: false
    },
	{
      type: "textarea",
      label: "局分管领导审批意见",
      field: "opinionField4",
      addShow: false
    },
    {
      type: "textarea",
      label: "监察部长审批意见",
      field: "opinionField5",
      addShow: false
    },
    {
      type: "textarea",
      label: "纪委领导审批意见",
      field: "opinionField6",
      addShow: false
    }
  ]
};
class index extends Component {
  render() {
    return (
      <div
        style={{
          height: "100vh"
        }}
      >
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
