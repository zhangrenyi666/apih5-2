import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
  //流程专属配置  --qnn-table中配了这也得配置
   workFlowConfig: {
    //后台定的字段
    title: ["", "", "局机关事务申请"], //标题字段
    apiNameByAdd: "addZjFlowAffairsApply",
    apiNameByUpdate: "updateZjFlowAffairsApply",
    apiNameByGet: "getZjFlowAffairsApplyDetail",
    flowId: "zjAffairsApply",
    formLink: {
      zjAffairsApply: "FlowByzjAffairsApply"
    },
    //待办已办切换路由
    todo: "todoByzjAffairsApply",
    hasTodo: "hasTodoByzjAffairsApply"
  },

  formConfig: [
    {
        type: "datetime",
        label: "申请日期",
        placeholder: "请选择",
        field: "applyDate",
        required: true
    },
    {
		            	 label:'申请部门',
		            	 field:'oaDeptList',
		            	 type: 'treeSelect',
		            	 initialValue:[],
		            	 treeSelectOption:{
		            		 help:true,
							 selectType: "1",
							 maxNumber: 1,
		            		 fetchConfig: {//配置后将会去请求下拉选项数据
		            			 apiName: 'getSysDepartmentAllTree2',
		            		 },
		            		 search:true,
		            		 searchPlaceholder:'姓名、账号、电话',
		            		 // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
		            		 searchParamsKey:'search',//搜索文字的K 默认是'searchText'   [string]
		            		 searchOtherParams:{pageSize:999}//搜索时的其他参数  [object]
		            	 }
		             },	
	{
      type: "textarea",
      label: "申请事项",
      field: "applyMatter",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "部门联络人",
      field: "deptLinkman",
      placeholder: "请输入",
      required: true
    },
    {
      type: "string",
      label: "房间号",
      field: "roomNumber",
      placeholder: "请输入",
      required: true
    },
	{
      type: "string",
      label: "电话",
      field: "phone",
      placeholder: "请输入",
      required: true
    },
    {
      type: "textarea",
      label: "内容及原因",
      field: "contectReason",
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
      label: "申请部门负责人审批意见",
      field: "opinionField1",
      addShow: false
    },
    {
      type: "textarea",
      label: "申请部门公司分管领导审批意见",
      field: "opinionField2",
      addShow: false
    },
    {
      type: "textarea",
      label: "办公室负责人审批意见",
      field: "opinionField3",
      addShow: false
    },
	{
      type: "textarea",
      label: "公司分管领导审批意见",
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
