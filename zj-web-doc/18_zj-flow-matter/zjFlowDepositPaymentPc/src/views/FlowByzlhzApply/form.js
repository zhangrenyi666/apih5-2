import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from 'moment';
const config = {
	//流程专属配置  --qnn-table中配了这也得配置
	workFlowConfig: {
		//后台定的字段
		title: ["sendTime", "战略合作框架协议、联合体协议评审(局)"], //标题字段
		apiNameByAdd: "addZjFlowStrategicCooperation",
		apiNameByUpdate: "updateZjFlowStrategicCooperation",
		apiNameByGet: "getZjFlowStrategicCooperationDetail",
		flowId: "zlhzApply",
		formLink: {
			gszlhzApply: "FlowBygszlhzApply",
			zlhzApply: "FlowByzlhzApply"
		},
		//待办已办切换路由
		todo: "todoByzlhzApply",
		hasTodo: "hasTodozlhzApply"
	},
	formItemLayout: {
		labelCol: {
			xs: { span: 6 },
			sm: { span: 6 }
		},
		wrapperCol: {
			xs: { span: 18 },
			sm: { span: 18 }
		}
	},
};
class index extends Component {
	render() {
		const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
					btnsCURD={({ btns,flowData: { nodeVars,flowId,workId } }) => {
                        if (nodeVars && nodeVars.canExport === '1') {
                            let { myPublic: { domain,appInfo: { ureport } } } = this.props;
                            var printUrl = `${ureport}excel?_u=file:strategicCooperation.xml&url=${domain}&workId=${workId}`
                            btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
                        }
                        return btns;
                    }}
					formConfig = {[
						{
							type: "string",
							label: "workId",
							field: "workId",
							hide: true
						},
						{
							type: "string",
							label: "主键ID",
							field: "reviewId",
							hide: true
						},
						{
							type: "string",
							label: "申请时间format",
							field: "sendTime",
                            initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
							placeholder: "请输入",
							hide: true
						},
						{
							type: "string",
							label: "项目全称",
							field: "projectName",
							required:true,
							placeholder:'请输入'
						},
						{
							type: "string",
							label: "拟稿人",
							field: "draftAuthor",
							initialValue:realName,
							qnnDisabled:true,
							placeholder:'请输入'
						},
						{
							type: "phone",
							label: "联系电话",
							field: "phone",
							required:true,
							placeholder:'请输入'
						},
						{
							type: "date",
							label: "填报日期",
							field: "fillInDate",
							required:true,
							placeholder:'请选择'
						},
						{
							type: "select",
							label: "类型",
							field: "reviewTypeId",
							required: true,
							placeholder:'请选择',
							optionConfig: {
								label: "label",
								value: "value"
							},
							optionData: [
								{
									value: "1",
									label: "战略合作框架协议"
								},
								{
									value: "2",
									label: "联合体协议"
								}
							]
						},
						{
							type: "textarea",
							label: "部门意见",
							field: "opinionField1",
							addShow: false
						},
						{
							type: "textarea",
							label: "会签意见",
							field: "opinionField2",
							addShow: false
						},
						{
							type: "textarea",
							label: "公司分管领导",
							field: "opinionField3",
							addShow: false
						},
						{
							type: "textarea",
							label: "公司总经理意见",
							field: "opinionField4",
							addShow: false
						},
						{
							type: "textarea",
							label: "公司董事长意见",
							field: "opinionField5",
							addShow: false
						}
					]}
				/>
			</div>
		);
	}
}
export default index;
