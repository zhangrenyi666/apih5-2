import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["", "", "户籍入户申请"],
		apiNameByAdd: "addZjFlowRhHouseholdIn",
		apiNameByUpdate: "updateZjFlowRhHouseholdIn",
		apiNameByGet: "getZjFlowRhHouseholdInDetail",
		flowId: "jthjrhApply",
		formLink: {
			jthjrhApply: "FlowByjthjrhApply"
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
			field: "inId",
			hide: true
		},

		{
			type: "textarea",
			label: "本人申请",
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
		{
			label: '附件',
			field: 'fileList',
			type: 'files',
			fetchConfig: {
				apiName: window.configs.domain + 'upload',
			}
		},
		{
			type: "textarea",
			label: "备注",
			field: "remarks",
			qnnDisabled: true,
			addDisabled: true,
			// editDisabled:true,
			placeholder: "请输入",
			// initialValue: `1.公司必须证明申请入户人员为本单位在职职工；\n2.调离的必须先将户口迁出（不含公司所属各单位内流动）\n3.工作单位如有变动须及时通知公司办公室行政外联处。`,
			initialValue: '1.公司必须证明申请入户人员为本单位在职职工；\n2.调离的必须先将户口迁出（不含公司所属各单位内流动）\n3.工作单位如有变动须及时通知公司办公室行政外联处。'
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