import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //后台定的字段
    title: ["", "", "加班申请"], //标题字段
    apiNameByAdd: "addZjFlowOvertimeApply",
    apiNameByUpdate: "updateZjFlowOvertimeApply",
    apiNameByGet: "getZjFlowOvertimeApplyDetail",
    flowId: "zjWorkApply",
    formLink: {
      zjLeaveApply: "FlowByzjLeaveApply",
     zjWorkApply: "FlowByzjWorkApply",
	   zjTripApply: "FlowByzjTripApply"
    },
    //待办已办切换路由
    todo: "todoByzjWorkApply",
    hasTodo: "hasTodoByzjWorkApply"
  },

  formConfig: [
	{
            type: "text",
            label: "标题",
            field: "overtimeTitle",
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
      label: "加班天数",
      field: "overtimeDays",
      placeholder: "请输入",
      required: true
    },
	{
      type: "string",
      label: "倒休天数",
      field: "offDays",
      placeholder: "请输入",
      required: true
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
      type: "textarea",
      label: "加班事由",
      field: "overtimeReason",
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
      initialValue:"部门、中心领导以下员工报部门、中心负责人批准，部门、中心领导（含）以上员工报主管领导批准",
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
