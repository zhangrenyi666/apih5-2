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
		title: ["","","随父入户申请"],
		apiNameByAdd: "addZjFlowRhHouseholdKid",
		apiNameByUpdate: "updateZjFlowRhHouseholdKid",
		apiNameByGet: "getZjFlowRhHouseholdKidDetail",
		flowId: "znrhApply",
		formLink: {
			znrhApply: "FlowByznrhApplyForm"
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
			field: "kidId",
			hide: true
		}, 
	
		{
			type: 'component',
			field: 'diy1',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px',color: '#545456', height: '38px',lineHeight:'38px',background:'#f0f2f5',textAlign:'left' }} orientation="left">男方</Divider>
				);
			}
		},
		{
			label: '姓名', 
			type: 'string',
			required:true,
			field:'manName',
			placeholder: '请输入...'
		},
		{
			label: '户口所在地', 
			required:true,
			type: 'string',
			field:'manPlace',
			placeholder: '请输入...'
		},
		{
			label: '身份证号码', 
			type: 'identity',
			required:true,
			field:'manIdCard',
			placeholder: '请输入...'
		},
		{
			label: '工作单位及职务', 
			type: 'string',
			required:true,
			field:'manDuty',
			placeholder: '请输入...'
		},
		{
			type: 'component',
			field: 'diy2',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px',lineHeight:'38px',background:'#f0f2f5',textAlign:'left' }} orientation="left">女方</Divider>
				);
			}
		},
		{
			label: '姓名', 
			type: 'string',
			field: 'womanName',
			required:true,
			placeholder: '请输入...'
		},
		{
			label: '户口所在地', 
			type: 'string',
			required:true,
			field:'womanPlace',
			placeholder: '请输入...'
		},
		{
			label: '身份证号码', 
			type: 'identity',
			required:true,
			field:'womanIdCard',
			placeholder: '请输入...'
		},
		{
			label: '工作单位及职务', 
			type: 'string',
			required:true,
			field:'womanDuty',
			placeholder: '请输入...'
		},
		{
			type: 'component',
			field: 'diy3',
			Component: obj => {
				return (
					<Divider style={{ fontSize: '14px', color: '#545456', height: '38px',lineHeight:'38px',background:'#f0f2f5',textAlign:'left' }} orientation="left">申请随父子女</Divider>
				);
			}
		},
		{
			label: '姓名', 
			type: 'string',
			required:true,
			field:'kidName',
			placeholder: '请输入...'
		},
		{
			label: '性别', 
			type: 'select',
			field: 'kidSex',
			required:true,
			placeholder: '请输入...',
			optionData: [{ label: "男",value: "0" },{ label: "女",value: "1" }]
		},
		{
			label: '出生日期', 
			type: 'date',
			required:true,
			field:'kidBirth',
			placeholder: '请输入...',
			format:'YYYY-MM-DD'
		},
		{
			type: "qnnForm",
			field: "otherKidList",
			label: "其他子女",
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
						label: '姓名', 
						type: 'string',
						field:'otherKidName',
						placeholder: '请输入...'
					},
					{
						label: '性别', 
						type: 'select',
						field:'otherKidSex',
						placeholder: '请输入...',
						optionData: [{ label: "男",value: "0" },{ label: "女",value: "1" }]
					},
					{
						label: '身份证号', 
						type: 'identity',
						field:'otherKidIdCard',
						placeholder: '请输入...'
					},
				]
			}
		},
		{
			label: '通讯地址及电话', 
			type: 'string',
			field:'addressPhone',
			placeholder: '请输入...'
		},
		
		{
			type: "textarea",
			label: "所在单位人事部门意见",
			field: "opinionField2",
			addShow: false,
			placeholder: "请输入",
			opinionField: true
		},
		{
			type: "textarea",
			label: "所在单位主要领导意见",
			field: "opinionField3",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司人力资源部意见",
			field: "opinionField4",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司办公室意见",
			field: "opinionField5",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "公司分管领导意见",
			field: "opinionField6",
			opinionField: true,
			addShow: false,
			placeholder: "请输入"
		},
		{
			type: "textarea",
			label: "备注",
			field: "remarks",
			qnnDisabled: true,
			addDisabled:true,
			placeholder: "请输入",
			// initialValue: `1.公司必须证明申请入户人员为本单位在职职工；\n2.调离的必须先将户口迁出（不含公司所属各单位内流动）\n3.工作单位如有变动须及时通知公司办公室行政外联处。`,
			initialValue: '1、如实填写，此表一式两份，公司人力资源部和办公室各留存一份。'
		},
		{
			label: '附件',
			field: 'fileList',
			type: 'files',
			fetchConfig: {
				apiName: window.configs.domain + 'upload',
			}
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