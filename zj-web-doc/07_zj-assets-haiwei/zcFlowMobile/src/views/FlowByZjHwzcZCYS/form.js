import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    apiNameByUpdate: "updateZjHwzcZcys",
    apiNameByGet: "getZjHwzcZcysDetail",
	apiTitle: "getZjHwAssetsFlowTitle",
    flowId: "purchasingSystem",
    formLink: {
      cropPurchasingProcess: "FlowBycropPurchasingProcess",
      projectDepPurchasingProcess: "FlowByprojectDepPurchasingProcess",
      purchasingSystem: "FlowBypurchasingSystem",
      projectTransfer: "FlowByprojectTransfer",
      cropTransfers: "FlowBycropTransfers",
      projectScrap: "FlowByprojectScrap",
      cropScrap: "FlowBycropScrap"
    },
    //待办已办切换路由
    todo: "todoBypurchasingSystem",
    hasTodo: "hasTodoBypurchasingSystem"
    // ...window.configs.workFlowConfig,
  },

  formConfig: [
    {
      hide: true,
      type: "string",
      label: "所属资产id",//
      field: "sszcid",
      initialValue: "",
      placeholder: "",
      required: true
    },
	{
      hide: true,
	  type: "string",
      label: "flowIdFlag",
      field: "flowIdFlag",   
      initialValue: '2'
    },
    {
      type: "linkage",
      fetchConfig: {
        apiName: "getAssetsTypeTreeAllList"
      },
      optionConfig: {
        value: "recordid",
        label: "assetTypeIdAndName",
        children: "currentList"
      },
      children: {
        //所有配置见qnn-form
        form: {
          label: "资产大类",
          field: "sszclx1",
          type: "select",
          required: true, //是否必填
          placeholder: "请选择"
        },
        children: {
          form: {
            label: "资产小类",
            placeholder: "请选择",
            field: "sszclx2",
            required: true, //是否必填
            type: "select"
          }
        }
      }
    },
    {
      type: "string",
      label: "资产名称",
      field: "zcmc",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "参考价（元）",
      field: "zcyz",
      placeholder: "请输入"
    },
    {
      type: "string",
      label: "规格型号",
      field: "ggxh",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "耐用年限",
      field: "synx",
      placeholder: "请输入"
    },
    {
      type: "select",
      label: "资产来源",
      field: "grfsdm",
      placeholder: "请选择", //占位符
      fetchConfig: {
        apiName: "getBuyMannerSelectAllList"
      },
      optionConfig: {
        label: "dmnr",
        value: "dmbh"
      }
    },
    {
      type: "datetime",
      label: "出厂日期",
      field: "scrq",
      placeholder: "请选择",
      initialValue: new Date()
    },
    {
      type: "string",
      label: "管理员",
      field: "glryName",//
      placeholder: "无"
    },
    {
      type: "string",
      label: "使用单位",
      field: "sydwName",//sydwName
      placeholder: "无"
    },
    {
      type: "select",
      label: "使用部门",
      field: "sybmid",
      placeholder: "请选择", //占位符
      fetchConfig: {
        apiName: "getDepartmentSelectAllList"
      },
      optionConfig: {
        label: "dmnr",
        value: "dmbh"
      }
    },
    {
      type: "string",
      label: "存放地点",
      field: "cfdd1",//
      placeholder: "无"
    },
    {
      type: "string",
      label: "使用人",
      field: "syr",//
      placeholder: "无"
    },
    {
      type: "string",
      label: "验收人员",
      field: "ysr",//
      placeholder: "无"
    },
    {
      type: "datetime",
      label: "验收日期",
      field: "yssj",
      placeholder: "请选择",
      initialValue: new Date()
    },
    {
      type: "textarea",
      label: "验收说明",
      field: "remarks",
      placeholder: "请输入"
    },
    {
      type: "files",
      label: "附件",
      field: "imageList",
      projectName: "zj-assets-haiwei",
      desc: "点击选择上传", //默认 点击或者拖动上传
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
      }
    },
    {
      type: "textarea",
      label: "管理部门负责人审批意见",
      field: "opinionField1",
      addShow: false
    },
    {
      type: "textarea",
      label: "使用部门意见",
      field: "opinionField2",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司主管部门意见",
      field: "opinionField3",
      addShow: false
    },
    {
      type: "textarea",
      label: "项目财务部门负责人审批意见",
      field: "opinionField4",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司财务负责人审批意见",
      field: "opinionField5",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司财务固定资产管理人员审批意见",
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
