import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
		//流程专属配置  --qnn-table中配了这也得配置
		workFlowConfig: {
			//后台定的字段
			title: ["", "", "请假申请"], //标题字段
			apiNameByAdd: "addZjFlowLeaveApply",
			apiNameByUpdate: "updateZjFlowLeaveApply",
			apiNameByGet: "getZjFlowLeaveApplyDetail",
			flowId: "zjLeaveApply",
			formLink: {
                zjLeaveApply: "FlowByzjLeaveApply",
                zjWorkApply: "FlowByzjWorkApply",
                zjTripApply: "FlowByzjTripApply"
            },
			//待办已办切换路由
			todo: "todoByzjLeaveApply",
			hasTodo: "hasTodoByzjLeaveApply"
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
		formConfig: [
		             {
            type: "text",
            label: "标题",
            field: "leaveTitle",
            addShow: false
        },
					 {
		            	 type: "datetime",
		            	 label: "申请日期",
		            	 field: "applyDate",
		            	 placeholder: "无"
		             },
		             {
		            	 type: "string",
		            	 label: "姓名",
		            	 field: "name",
		            	 placeholder: "请输入",
		            	 required: true
		             },		
		             {
		            	 label:'部门',
		            	 field:'oaDeptList',
		            	 type: 'treeSelect',
		            	 initialValue:[],
		            	 treeSelectOption:{
		            		 help:true,
							 selectType: "1",
							 maxNumber: 1,
		            		 fetchConfig: {//配置后将会去请求下拉选项数据
		            			  apiName: "getSysDepartmentAllTree2"
		            		 },
		            		 search:true,
		            		 searchPlaceholder:'姓名、账号、电话',
		            		 // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
		            		 searchParamsKey:'search',//搜索文字的K 默认是'searchText'   [string]
		            		 searchOtherParams:{pageSize:999}//搜索时的其他参数  [object]
		            	 }
		             },
		             {
		            	 type: "string",
		            	 label: "请假天数",
		            	 field: "leaveDays",
		            	 placeholder: "请输入",
		            	 required: true
		             },
		             {
		            	 type: "select",
		            	 label: "请假类型",
		            	 placeholder: "请选择",
		            	 field: "leaveType",
		            	 required: true,
		            	 optionConfig: {
		            		 label: "label",
		            		 value: "value"
		            	 },
		            	 optionData: [
		            	              {
		            	            	  value: "0",
		            	            	  label: "年休假"
		            	              },
		            	              {
		            	            	  value: "1",
		            	            	  label: "事假"
		            	              },
		            	              {
		            	            	  value: "2",
		            	            	  label: "病假"
		            	              },
		            	              {
		            	            	  value: "3",
		            	            	  label: "探亲假"
		            	              },
		            	              {
		            	            	  value: "4",
		            	            	  label: "婚假"
		            	              },
		            	              {
		            	            	  value: "5",
		            	            	  label: "（陪）产假"
		            	              },
		            	              {
		            	            	  value: "6",
		            	            	  label: "丧假"
		            	              },
		            	              {
		            	            	  value: "7",
		            	            	  label: "倒休假"
		            	              }
		            	              ]
		             },
		             {
		            	 type: "datetime",
		            	 label: "开始时间",
		            	 field: "startTime",
		            	 placeholder: "无"
		             },
		             {
		            	 type: "datetime",
		            	 label: "结束时间",
		            	 field: "endTime",
		            	 placeholder: "无"
		             },
		             {
		            	 type: "datetime",
		            	 label: "销假日期",
		            	 field: "terminateDate",
		            	 placeholder: "无"
		             },
		             {
		            	 type: "textarea",
		            	 label: "请假原由",
		            	 field: "leaveReason",
		            	 placeholder: "请输入",
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
		            	 label: "注",
		            	 field: " ",
                         qnnDisabled: true,
		            	 initialValue: "01 事假：部门、中心、事业部领导以下员工因事请假，一天之内的报本部门、中心、事业部负责人批准，三天（含）以内的报公司分管领导批准，四天以上的党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；部门、中心、事业部领导（含）以上员工因事请假的先报分管领导审批，之后党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；\n02 病假：部门、中心、事业部领导以下员工因病请假，五天之内的报本部门、中心、事业部负责人批准，六天以上十天（含）以内的报公司分管领导批准，十一天以上的党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；部门、中心、事业部领导（含）以上员工因病请假的先报分管领导审批，之后党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；\n03.年休假、婚假、（陪）产假、丧假、倒休假：普通员工报部门、中心、事业部负责人批准；部门、中心、事业部负责人报公司分管领导批准;",
						formItemLayout: {
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 24 }
                }
            }
		             },
		             {
		            	 type: "textarea",
		            	 label: "部门负责人意见",
		            	 field: "opinionField1",
		            	 addShow: false
		             },
		             {
		            	 type: "textarea",
		            	 label: "公司分管领导意见",
		            	 field: "opinionField2",
		            	 addShow: false
		             },
		             {
		            	 type: "textarea",
		            	 label: "公司总经理意见",
		            	 field: "opinionField3",
		            	 addShow: false
		             },
		             {
		            	 type: "textarea",
		            	 label: "公司董事长意见",
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
