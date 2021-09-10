import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import moment from "moment";
let config = {
  //流程专属配置  ---在qnn-table中配后这也得配
  workFlowConfig: {
    //后台定的字段
    title: ["applyUnitId", "title_sendTime", "党费使用申请"], //标题字段
    apiNameByAdd: "addZjFlowPartyFeeUse",
    apiNameByUpdate: "updateZjFlowPartyFeeUse",
    apiNameByGet: "getZjFlowPartyFeeUseDetail",
    apiTitle: "getZjFlowPartyFeeUseTitle",
    flowId: "zjPartyFeeUse",
    formLink: {
      zjPartyFeeUse: "FlowByzjPartyFeeUse"
      // zjYongYin: "FlowByzjYongYin",
      // zjYyOutSeal: "FlowByzjYyOutSeal",
      // ZjMeetingRoom: "FlowByZjMeetingRoom"
    },
    //待办已办切换路由
    todo: "todoByzjPartyFeeUse",
    hasTodo: "hasTodoByzjPartyFeeUse"
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
      field: "feeUseId",
      hide: true
    },
    {
      type: "select",
      label: "申请单位",
      field: "applyUnitId",
      placeholder: "请选择", //占位符
      required: true, //是否必填
      fetchConfig: {
        apiName: "getZjFlowPartyFeeUnitList"
      },
      optionConfig: {
        label: "unitName",
        value: "feeUnitId"
      }
    },
    {
      type: "datetime",
      label: "申请时间",
      field: "applyTime",
      placeholder: "请选择",
      // initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
      required: true
    },
    {
      type: "linkage",
      fetchConfig: {
        apiName: "getZjFlowPartyFeeDetailAllList"
      },
      optionConfig: {
        value: "detailId",
        label: "detailName",
        children: "currentList"
      },
      children: {
        //所有配置见qnn-form
        form: {
          label: "党费明细一",
          field: "feeDetailId1",
          type: "select",
          required: true, //是否必填
          placeholder: "请选择"
        },
        children: {
          form: {
            label: "党费明细二",
            placeholder: "请选择",
            field: "feeDetailId2",
            required: true, //是否必填
            type: "select"
          }
        }
      }
    },
    {
      type: "string",
      label: "联系电话",
      field: "phone", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      required: true //是否必填
    },
    {
      type: "integer",
      label: "申请金额",
      field: "applyMoneySmall", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      required: true //是否必填
    },
    {
      type: "string",
      label: "金额（大写）",
      field: "applyMoneyBig", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      disabled: true,
      condition: [
        {
          //条件
          regex: {
            //匹配规则 正则或者字符串
            "apiBody.workId": "add"
          },
          action: "hide" //disabled,  show,  hide, function(){}
        }
      ]
    },
    {
      type: "textarea",
      label: "申请事由",
      field: "applyReason",
      placeholder: "请输入", //占位符
      required: true //是否必填
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
      label: "支部书记意见",
      field: "opinionField1",
      placeholder: "请输入", //占位符
      addShow: false
    },
    // {
    //   type: "textarea",
    //   label: "党建主管意见",
    //   field: "opinionField2",
    //   placeholder: "请输入", //占位符
    //   addShow: false
    // },
    {
      type: "textarea",
      label: "党委工作部意见",
      field: "opinionField3",
      placeholder: "请输入", //占位符
      addShow: false
    },
    {
      type: "textarea",
      label: "党委副书记意见",
      field: "opinionField4",
      placeholder: "请输入", //占位符
      addShow: false
    },
    {
      type: "textarea",
      label: "党委书记意见",
      field: "opinionField5",
      placeholder: "请输入", //占位符
      addShow: false
    }
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
