import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    apiNameByAdd: "addFlowAssetsInLaunch",
    apiNameByUpdate: "updateFlowAssetsAfterSubmit",
    apiNameByGet: "getAssetsDetails",
    apiTitle: "getZjHwAssetsFlowTitle",
    flowId: "projectDepPurchasingProcess",
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
    todo: "todoByprojectDepPurchasingProcess",
    hasTodo: "hasTodoByprojectDepPurchasingProcess"
    // ...window.configs.workFlowConfig,
  },

  formConfig: [
    {
      hide: true,
      type: "string",
      label: "申请类型代码",// 1为公司  2为项目
      field: "typeAssets",
      initialValue: "2",
      placeholder: "",
      required: true
    },
	{
      hide: true,
	  type: "string",
      label: "flowIdFlag",
      field: "flowIdFlag",   
      initialValue: '1'
    },
    {
      hide: true,
      type: "string",
      label: "资产状态代码（默认为未验收2）",//
      field: "zcztdm",
      initialValue: "2",
      placeholder: "",
      required: true
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
      type: "datetime",
      label: "申购日期",
      field: "grrq",
      placeholder: "请选择",
      required: true
    },
    {
      type: "string",
      label: "计量单位",
      field: "measureUnit",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "数量",
      field: "quantity",
      placeholder: "请输入",
      initialValue: "1",
      required: true
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
      type: "datetime",
      label: "出厂日期",
      field: "scrq",
      placeholder: "请选择",
      initialValue: new Date()
    },
    {
      type: "string",
      label: "残值率（%）",
      field: "xzldmName",
      placeholder: "请输入"
    },
    {
      type: "select",
      label: "购入方式",
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
      type: "textarea",
      label: "申购原因",
      field: "appReason",
      placeholder: "请输入"
    },
    {
      type: "textarea",
      label: "备注",
      field: "bz",
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
      label: "项目经理",
      field: "proManagerOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "分管领导",
      field: "branchLeaderOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司主管部门经办人",
      field: "corpMainDepOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "公司主管部门部长",
      field: "depHeadOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "总经理",
      field: "generalManagerOpinion",
      addShow: false
    },
    {
      type: "textarea",
      label: "党委书记、执行董事",
      field: "chairmanOpinion",
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
