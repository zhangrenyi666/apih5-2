import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //后台定的字段
    title: ["applyRealName", "sendTime", "用印申请"], //标题字段
    apiNameByAdd: "addFlowOutSealInLaunch",
    apiNameByUpdate: "updateFlowOutSealAfterSubmit",
    apiNameByGet: "getFlowOutSealDetailByWorkId",
    flowId: "zjYyOutSeal",

    formLink: {
        // zjPartyFeeUse: "FlowByzjPartyFeeUse",
        zjYongYin: "FlowByzjYongYin",
        zjYyOutSeal: "FlowByzjYyOutSeal"
        // ZjMeetingRoom: "FlowByZjMeetingRoom"
      },
      //待办已办切换路由
      todo: "todoByzjYyOutSeal",
      hasTodo: "hasTodoByzjYyOutSeal"
  },

  formConfig: [
    {
      type: "string",
      label: "申请时间",
      field: "sendTime",
      placeholder: "请输入",
      initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
      hide: true
    },
    {
      type: "string",
      label: "外携用印单位",
      field: "outSealUnit",
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
        type: "date",
        label: "用印时间",
        placeholder: "请选择",
        field: "applyUseTime",
        required: true
      },
    {
      type: "textarea",
      label: "内容",
      field: "content",
      placeholder: "请输入",
      required: true
    },
    {
      type: "select",
      label: "印别",
      placeholder: "请选择",
      field: "sealType",
      required: true,
      optionData: [
        {
          value: "0",
          label: "公章"
        },
        {
          value: "1",
          label: "手签章"
        },
        {
          value: "2",
          label: "人名章"
        },
        {
          value: "3",
          label: "公章,手签章"
        },
        {
          value: "4",
          label: "公章,人名章"
        },
        {
          value: "5",
          label: "手签章,人名章"
        },
        {
          value: "6",
          label: "公章,手签章,人名章"
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
      field: "applyRealName",
      label: "经办人",
      required: true,
      placeholder: "请输入经办人姓名"
    },
    {
      type: "string",
      label: "联系电话",
      placeholder: "请输入",
      field: "applyUserPhone",
    //   required: true
    },
   
    {
      type: "files",
      label: "附件",
      field: "outSealFileList",
      desc: "点击选择上传", //默认 点击或者拖动上传
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
      }
    },
    {
      type: "textarea",
      label: "注",
      placeholder: "请输入备注",
      field: "remark",
      disabled: true,
      initialValue: `1.涉及授权委托事宜的用印审批，须对口部门合法合规人员审批签字\n2.内容一栏需详细填写用印事由，准确反映用印具体用途。`
    },
    {
      type: "textarea",
      label: "合法合规人员审批意见",
      placeholder: "请输入",
      field: "legalOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "部门负责人审批意见",
      placeholder: "请输入",
      field: "depHeadOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "分管公司领导审批意见",
      placeholder: "请输入",
      field: "leaderOpinion",
      addShow: false
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
