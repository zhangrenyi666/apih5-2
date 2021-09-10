import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import { Divider } from 'antd';
import moment from "moment";
let config = {
	formType: "descriptions",
	descriptionsConfig: {
        column: 8, 
		layout: "horizontal",
		bordered:true
    },
	workFlowConfig: {
		title: ["name", "createTimeq", "户口页/户口首页借用流程"],
		apiNameByAdd: "addZjFlowHkBorrow",//???
		apiNameByUpdate: "updateZjFlowHkBorrow",//???
		apiNameByGet: "getZjFlowHkBorrowDetail",//???
		flowId: "huKouApply",
		todo: "todoByhuKou",
		hasTodo: "hasTodoByhuKou"
	},
	
	formTailLayout: {
		wrapperCol: {
			sm: {
				offset: 12
			}
		}
	},
	formConfig: [
        {
            type: 'component',
            field: 'diyJJJ',
            formItem: true,
            Component: obj => {
                return (
                    <Divider style={{ fontSize: '22px',color: '#545456',height: '38px',lineHeight: '38px',margin: '16px 0 0 0',textAlign: 'center' }} orientation="middle">中交一公局集团户口首页/个人户口页借用</Divider>
                );
            }
        },
		{
			label: '',
			type: 'string',
			hide:true,
			field: 'typeId',
			initialValue:'1'
		},
		{
			label: '时间',
			type: 'date',
			field: 'appDate',
			required:true,
			placeholder: '请输入...',
			span: 8,
			formItem:true,
			style: {
				border:'none'
			},
			initialValue: () => {
				return new Date().getTime()
			},
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
		},
		{
			type: "string",
			label: "workId",
			field: "workId",
			hide: function () {
				return true
			},
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "主键ID",
			field: "hkBorrowId",
			hide: function () {
				return true
			},
		},
		{
			type: "string",
			label: "标题时间",
			field: "createTimeq",
			hide: function () {
				return true
			},
			initialValue: () => {
				console.log(moment(new Date()).format('YYYY-MM-DD'));
				return moment(new Date()).format('YYYY-MM-DD')
			}
		},
		{
			label: '姓名',
			type: 'string',
			field: 'name',
			required:true,
			placeholder: '请输入...',
			span:24
		},
		{
			label: '单位/部门',
			type: 'string',
			field: 'unit',
			span:24
		},
		{
			label: '联系方式',
			type: 'string',//???
			field: 'phone',
			span:24
		},
		{
			label: '借用个人户口页',
			type: 'string',
			field: 'ext11',
			placeholder: '请输入...',
			span: 4,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
		},
		{
			label: '借用集体户首页',
			type: 'string',
			field: 'ext12',
			// prefix: '张',
			suffix:'张',
			placeholder: '请输入...',
			span: 4,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
		},
		// {
        //     type: 'string',
        //     label: '借用材料',
        //     field: 'firstList',//???
        //     placeholder: '请输入',
        //     span: 8,
        //     tdStyle: {},
        //     children: {
        //         descriptionsConfig: {
        //             column: 7,
        //             layout: "vertical",
        //         },
        //         formConfig: [ 
        //            {
		// 				label: '个人户口页',
		// 				type: 'string',
		// 				field: 'ext11',
		// 				placeholder: '请输入...'
		// 			},
		// 			{
		// 				label: '首页',
		// 				type: 'string',
		// 				field: 'ext12',
		// 				// prefix: '张',
		// 				suffix:'张',
		// 				placeholder: '请输入...'
		// 			}
        //         ]
        //     }
        // },
		// {
		// 	type: 'string',
        //     label: '办公室确认',
        //     field: 'highList',//???
        //     placeholder: '请输入',
        //     span: 8,
		// 	tdStyle: {},
		// 	children: {
        //         descriptionsConfig: {
        //             column: 7,
        //             layout: "vertical",
        //         },
        //         formConfig: [ 
		// 			{
		// 				label: '个人户口页',
		// 				type: 'string',
		// 				field: 'ext21',
		// 				placeholder: '请输入...'
		// 			},
		// 			{
		// 				label: '首页',
		// 				type: 'string',
		// 				field: 'ext22',
		// 				suffix:'张',
		// 				placeholder: '请输入...'
		// 			}
        //         ]
        //     }
		// },
		{
			label: '办公室确认个人户口页',
			type: 'string',
			field: 'ext21',
			placeholder: '请输入...',
			span: 4,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
		},
		{
			label: '办公室确认首页',
			type: 'string',
			field: 'ext22',
			suffix:'张',
			placeholder: '请输入...',
			span: 4,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
		},
		{
			label: '用途',
			type: 'textarea',
			field: 'purpose',
			placeholder: '请输入...',
			span:8,
			autoSize:{
				minRows: 2
			}
		},
		{
			label: '邮寄地址',
			type: 'textarea',
			field: 'emailAddress',
			placeholder: '请输入...',
			span:8,
			autoSize:{
				minRows: 2
			}
		},
		{
			type: "textarea",
			label: "人力资源部意见",
			field: "opinionField1",
			addShow: false,
			placeholder: "请输入",
			opinionField: true,
			span: 8,
			autoSize:{
				minRows: 2
			}
		},
		{
			type: "textarea",
			label: "办公室意见",
			field: "opinionField2",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			span: 8,
			autoSize:{
				minRows: 2
			}
		},
		{
			type: "textarea",
			label: "备注",
			field: "remarks",
			qnnDisabled: true,
			placeholder: "请输入",
			initialValue: `1、请先发送所在单位人力资源部审核后再提交集团办公室`,
			span: 8,
			autoSize:{
				minRows: 2
			}
		},
		{
			label: "附件",
			field: "fileList",
			type: "files",
			initialValue: [],
			fetchConfig: {
				apiName: window.configs.domain + "upload"
			}
		}
	]
};
class index extends Component {
	render() {
		const {
			isInQnnTable,
			myPublic: { domain,appInfo: { ureport } }
		} = this.props;
		return (<div style={
			{
				height: isInQnnTable ? "" : "100vh"
			}
		} >

			<Form
				{...this.props}
				{...config}
				{...this.props.workFlowConfig}
				{...config.workFlowConfig}
			/>  </div>
		);
	}
}
export default index;