import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import { Divider } from 'antd';
import s from './style.less';
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["","personApproveTitle","临聘人员申请"],
		apiNameByAdd: "addZjFlowPersonApprove",
		apiNameByUpdate: "updateZjFlowPersonApproveForFlow",
		apiNameByGet: "getZjFlowPersonApproveDetail",
		flowId: "sanPerson",
		formLink: {
			sanPerson: 'FlowBysanPersonForm'
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
			field: "personApproveId",
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
			label: '标题', 
			type: 'string',
			required:true,
			field:'personApproveTitle',
			placeholder: '请输入...'
		},
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