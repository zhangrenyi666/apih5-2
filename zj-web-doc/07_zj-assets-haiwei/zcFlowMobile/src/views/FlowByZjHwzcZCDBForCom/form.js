import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
	//流程专属配置
	workFlowConfig: {
		//后台定的字段
		apiNameByUpdate: "updateZjHwzcZcdb",
		apiNameByGet: "getZjHwzcZcdbDetails",
		apiTitle: "getZjHwAssetsFlowTitle",
		flowId: "cropTransfers",
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
		todo: "todoByprojectTransfer",
		hasTodo: "hasTodoByprojectTransfer"
		// ...window.configs.workFlowConfig,
	},
	formConfig: [
	{
      hide: true,
	  type: "string",
      label: "flowIdFlag",
      field: "flowIdFlag",   
      initialValue: '5'
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
			type: "datetime",
			label: "出厂日期",
			field: "scrq",
			placeholder: "请选择",
			initialValue: new Date()
		},
		{
			type: "string",
			label: "数量",
			field: "quantity",
			placeholder: "请输入",
			initialValue: "1"
		},
		{
			type: "string",
			label: "参考价（元）",
			field: "zcyz",
			placeholder: "请输入"
		},
		{
			type: "string",
			label: "累计折旧（元）",
			placeholder: "请输入",
			field: "ljzj"
		},
		{
			type: "string",
			label: "资产净值（元）",
			placeholder: "请输入",
			field: "zxjz"
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
			label: "使用单位",
			field: "sydwName",
			placeholder: "请输入",
			required: true
		},
		{
			type: "string",
			label: "调入单位",
			field: "tranToUnit",
			placeholder: "请输入"
		},
		{
			type: "string",
			label: "调拨后管理员",
			field: "tranAdminName",
			placeholder: "请输入"
		},
		{
			type: "datetime",
			label: "调拨日期",
			field: "tranTime",
			placeholder: "请选择"
		},
		{
			type: "textarea",
			label: "调拨依据",
			field: "tranGist",
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "调拨备注",
			field: "tranRemarks",
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
			label: "部长签字",
			field: "opinionField1",
			addShow: false
		},
		{
			type: "textarea",
			label: "B项目财务审批意见",
			field: "opinionField2",
			addShow: false
		},
		{
			type: "textarea",
			label: "公司财务审批意见",
			field: "opinionField3",
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
