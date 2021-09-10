import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["", "", "集体户籍申请"],
		apiNameByAdd: "addZjFlowRhHouseholdOut",
		apiNameByUpdate: "updateZjFlowRhHouseholdOut",
		apiNameByGet: "getZjFlowRhHouseholdOutDetail",
		flowId: "CopyjthjrhApply",
		formLink: {
			CopyjthjrhApply: "FlowByCopyjthjrhApply"
		},
		todo: "todoByZjInHome",
		hasTodo: "hasTodoByZjInHome"
	},
	
	//qnn-form配置
	formConfig: [
		{
			type: "string",
			label: "workId",
			field: "workId",
			hide: true,
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "",
			field: "outId",
			hide: true
		},
        {
			type: "qnnForm",
			field: "outInfoList",
			label: "集体户籍申请",
			textObj: {
				add: "添加",
				del: "删除"
			},
			canAddForm: true,  
			addBtnStyle: {
				width: '87px'
			},
			qnnFormConfig: {
				formConfig: [
					{
						label: '申请入户人姓名', 
						type: 'string',
						required:true,
						field:'outName',
						placeholder: '请输入...'
                    },
                    {
						label: '申请入户人身份证号码', 
						type: 'identity',
						field: 'outIdCard',
						required:true,
						placeholder: '请输入...'
					},
					{
						label: '京内职工姓名', 
						type: 'string',
						field: 'beijingName',
						required:true,
						placeholder: '请输入...'
                    },
                    {
						label: '京内职工联系方式', 
						type: 'phone',
						required:true,
						field:'beijingPhone',
						placeholder: '请输入...'
                    },
                    {
						label: '与申请人关系',
						required:true,
						type: 'string',
						field:'relationship',
						placeholder: '请输入...'
                    },
                    {
						label: '进京理由', 
						type: 'textarea',
						field:'toBeijingReason',
						placeholder: '请输入...'
					},
					{
						label: '附件',
						field: 'fileList',
						type: 'files',
						fetchConfig: {
							apiName: window.configs.domain + 'upload',
						}
					}
				]
			}
		},
		{
			type: "textarea",
			label: "职工本人申请",
			field: "opinionField1",
			opinionField: true,
			addShow: true,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司所属单位意见",
			field: "opinionField2",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司人力资源部意见",
			field: "opinionField3",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司办公室意见",
			field: "opinionField4",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司分管领导意见",
			field: "opinionField5",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		
	]
};
class index extends Component {
	state = {
        height:document.documentElement.clientHeight,
        hidden: false,
    };
	render() {
		
		const { isInQnnTable } = this.props; 
		console.log(this.state.height);
        return (
			<div style={{ height: this.state.height}}>
				<Form
					{...this.props}
					{...config}
					{...this.props.workFlowConfig}
					{...config.workFlowConfig}
				/>
				{/* <div style={{ position: 'fixed',top:'20px', }}>{this.state.height}</div> */}
			</div>
        );
    }
}
export default index;