import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    apiNameByUpdate: "updateZjHwzcZcbf",
    apiNameByGet: "getZjHwzcZcbfDetails",
	apiTitle: "getZjHwAssetsFlowTitle",
    flowId: "cropScrap",
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
    todo: "todoBycropScrap",
    hasTodo: "hasTodoBycropScrap"
    // ...window.configs.workFlowConfig,
  },

  formConfig: [
    {
      hide: true,
	  type: "string",
      label: "flowIdFlag",
      field: "flowIdFlag",   
      initialValue: '3'
    },
	{
      type: "string",
      label: "资产大类",
      field: "sszclx1Name",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "资产小类",
      field: "sszclx2Name",
      placeholder: "请输入",
      required: true
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
      label: "资产编号",
      field: "zcbh",
      placeholder: "请输入"
    },
    {
      type: "string",
      label: "财务编号",
      field: "cwbh",
      placeholder: "请输入"
    },
    {
      type: "string",
      label: "数量",
      field: "quantity",
      placeholder: "请输入",
      initialValue: "1"
    },
    {
      type: "select",
      label: "生产厂家",
      field: "manufacturerId",
      placeholder: "请选择", //占位符
      fetchConfig: {
        apiName: "getManufacturerSelectAllList"
      },
      optionConfig: {
        label: "manufacturerName",
        value: "recordid"
      }
    },
    {
      type: "string",
      label: "存放地点",
      field: "cfdd1",
      placeholder: "请输入"
    },
    {
      type: "string",
      label: "资产原值（元）",
      field: "zcyz",
      placeholder: "请输入",
      required: true
    },
    {
      type: "number",
      label: "累计折旧（元）",
      placeholder: "请输入",
      field: "ljzj"
    },
    {
      type: "number",
      label: "资产净值（元）",
      placeholder: "请输入",
      field: "zxjz"
    },
    {
      type: "number",
      label: "规定使用年限（年）",
      placeholder: "请输入",
      field: "synx"
    },
    {
      type: "select",
      label: "报废方式",
      field: "dmfl",
      placeholder: "请选择", //占位符
      fetchConfig: {
        apiName: "getScrapTypeSelectAllList"
      },
      optionConfig: {
        label: "dmnr",
        value: "dmbh"
      }
    },
    {
      type: "string",
      label: "处理形式",
      field: "clxs",
      placeholder: "请输入"
    },
    {
      type: "number",
      label: "已使用年限（年）",
      placeholder: "请输入",
      field: "ysynx"
    },
    {
      type: "textarea",
      label: "资产现状及报废原因",
      field: "zcxzbfyy",
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
      label: "公司主管部门审批意见",
      field: "opinionField1",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司财务负责人审批意见",
      field: "opinionField2",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司主管领导审批意见",
      field: "opinionField3",
      addShow: false
    },
    {
      type: "textarea",
      label: "总经理审批意见",
      field: "opinionField4",
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
