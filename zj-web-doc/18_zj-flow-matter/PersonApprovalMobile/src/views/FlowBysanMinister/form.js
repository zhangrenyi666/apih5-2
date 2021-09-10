import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["", "ministerApproveTitle", "部长申请"],
		apiNameByAdd: "addZjFlowMinisterApprove",
		apiNameByUpdate: "updateZjFlowMinisterApproveForFlow",
		apiNameByGet: "getZjFlowMinisterApproveDetail",
		flowId: "sanMinister",
		formLink: {
			sanMinister:'FlowBysanMinisterForm'
		},
		todo: "todoByZjPerson",
		hasTodo: "hasTodoByZjPerson"
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
			field: "ministerApproveId",
			hide: true
		},
		{
			label: '项目名称', 
			type: 'string',
			required:true,
			field:'proName',
			placeholder: '请输入...'
		},
		{
			label: '项目负责人', 
			type: 'string',
			required:true,
			field:'proLeader',
			placeholder: '请输入...'
		},
		{
			label: '标题', 
			type: 'string',
			required:true,
			field:'ministerApproveTitle',
			placeholder: '请输入...'
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