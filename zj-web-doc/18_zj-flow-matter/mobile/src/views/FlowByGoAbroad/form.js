import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
    //流程专属配置  --qnn-table中配了这也得配置
   workFlowConfig: {
			//后台定的字段
			title: ["", "", "因私出国申请（各单位）"], //标题字段
			apiNameByAdd: "addZjFlowGoAbroadApply",
			apiNameByUpdate: "updateZjFlowGoAbroadApply",
			apiNameByGet: "getZjFlowGoAbroadApplyDetail",
			flowId: "zjGoAbroad",
			formLink: {
				zjGoAbroad: "FlowByzjGoAbroad",
				zjjgGoAbroad: "FlowByzjjgGoAbroad"
			},
			//待办已办切换路由
			todo: "todoByzjGoAbroad",
			hasTodo: "hasTodoByzjGoAbroad"
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
            type: "string",
            label: "姓名",
            field: "name",
            placeholder: "请输入",
            required: true
        },
		{
            hide: true,
			type: "string",
            label: "类别标识",
            field: "typeFlag",
			initialValue:"0",
            required: true
        },
		{
            type: "select",
            label: "性别",
            placeholder: "请选择",
            field: "sex",
            required: true,
            optionConfig: {
                label: "label",
                value: "value"
            },
            optionData: [
                {
                    value: "0",
                    label: "男"
                },
                {
                    value: "1",
                    label: "女"
                }
            ]
        },
		{
            type: "date",
            label: "出生年月",
            field: "birth",
            placeholder: "无"
        },
        {
            type: "string",
            label: "民族",
            field: "nation",
            placeholder: "请输入",
            required: true
        }, 
		{
            type: "string",
            label: "籍贯",
            field: "nativePlace",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "政治面貌",
            field: "politicsPlace",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "工作单位",
            field: "workUnit",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "职务",
            field: "duty",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "职称",
            field: "professional",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "户口所在地",
            field: "residentAddress",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "身份证号码",
            field: "idCard",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "护照号码",
            field: "passportNumber",
            placeholder: "请输入",
            required: true
        },
		{
            type: "textarea",
            label: "出国（境）事由",
            field: "goAbroadReason",
            placeholder: "请输入"
        },
		{
            type: "string",
            label: "前往国家/地区",
            field: "goCountry",
            placeholder: "请输入",
            required: true
        },
		{
            type: "string",
            label: "预计行程",
            field: "expectedTravel",
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
            type: "number",
            label: "同行人数",
            field: "peersNumber",
            placeholder: "请输入",
			precision: 0, //数值精度
            required: true
        },
        {
            type: "qnnForm",
            field: "detailList",
            label: "同行人员信息",
            //文字配置 默认数据如下 [object]
            textObj: {
                add: "添加同行人员信息",
                del: "删除"
            },
            //是否能新增form表单(true可动态增删) 默认false [bool]
            //注意：开启后表单项值的类型为array  关闭为object
            canAddForm: true,
            //canAddForm===true 的初期値设置格式（提交数据格式&后台返回字段格式 同）
            //canAddForm===false 的初期値设置格式（提交数据格式&后台返回字段格式 同）
            //qnn-form配置  
            qnnFormConfig: {
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
                        type: "string",
                        label: "称谓",
                        field: "appellation",
                        placeholder: "请输入"
                    },
					{
                        type: "string",
                        label: "姓名",
                        field: "infoName",
                        placeholder: "请输入"
                    },
					{
                        type: "number",
                        label: "年龄",
                        field: "infoAge",
						precision: 0, //数值精度
                        placeholder: "请输入"
                    },
					{
                        type: "string",
                        label: "政治面貌",
                        field: "infoPoliticsPlace",
                        placeholder: "请输入"
                    },					
                    {
                        type: "string",
                        label: "工作单位及职务",
                        field: "infoWorkUnitAndDuty",
                        placeholder: "请输入"
                    }
                ]
            }
        },
        /*  {
            type: "files",
            label: "附件",
            field: "fileList",
            desc: "点击选择上传", //默认 点击或者拖动上传
            fetchConfig: {
                //配置后将会去请求下拉选项数据
                apiName: window.configs.domain + "upload"
            }
        }, */
        {
            type: "textarea",
            label: "公司党委书记意见",
            field: "opinionField1",
            addShow: false
        },
        {
            type: "textarea",
            label: "集团分管领导意见",
            field: "opinionField2",
            addShow: false
        },
        {
            type: "textarea",
            label: "党委组织部意见",
            field: "opinionField4",
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
