import React,{ Component } from "react";
import { Form } from "apih5/modules/work-flow";
import moment from "moment";
let config = {
    //流程专属配置  ---在必须在form和index页面都配置  
    workFlowConfig: {
        //后台定的字段
        title: "复工申请", //标题字段
        apiNameByAdd: "addZjHwSars",
        apiNameByUpdate: "updateZjHwSars",
        apiNameByGet: "getZjHwSarsDetailsByWork",
        flowId: "hwfgApply",

        // apiTitle: "",
        //移动端需要用到
        formLink: {
            // zjPartyFeeUse: "FlowByDFAwait",
            // zjYongYin: "FlowByYYAwait",
        },
        actionParamsFormat: function (body,props) {
			let apiBody = JSON.parse(body.apiBody);
			body.title = apiBody.title;
			body.apiBody = JSON.stringify(apiBody);
			return body
		}
        //移动端需要用到
        //待办已办切换路由
        // todo: "FlowByYYAwait",
        // hasTodo: "FlowByYYOver"
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
            label: "申请id",
            field: "sarsId",
            hide: true
        }, 
        {
            type: "textarea",//
            field: "title",//
            label: "标题",// 
            placeholder: "请输入",
            required: true,
        },
 
        {
            type: "string",// 
            field: "applyCompanyName",//
            label: "申请单位",//
            placeholder: "请输入",
            required: true
        },
        {
            type: "string",//
            field: "country",// 
            label: "所在国家",//
            placeholder: "请输入",
            required: true
        },
        {
            type: "string",//
            field: "region",//
            label: "所属区域",// 
            placeholder: "请输入",
            required: true
        },
        {
            type: "date",//
            field: "returnDate",//
            label: "返岗日期",// 
            placeholder: "请选择",
            required: true, 
        },
        {
            type: "date",//
            field: "applyDate",//
            label: "申请日期",// 
            placeholder: "请选择",
            required: true, 
        },
        {
            type: "textarea",// 
            "field": "opinionField5",//接口对应字段
            "label": "外事管理处意见",//表头名
            placeholder: "请输入",  
            opinionField:true,
            formItemLayout:{
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
            type: "textarea",// 
            "field": "opinionField1",//接口对应字段
            "label": "综合处意见",//表头名
            placeholder: "请输入",  
            opinionField:true,
            formItemLayout:{
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
            type: "textarea",// 
            "field": "opinionField2",//接口对应字段
            "label": "部门会签意见",//表头名
            placeholder: "请输入",  
            opinionField:true,
            formItemLayout:{
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
            type: "textarea",// 
            "field": "opinionField3",//接口对应字段
            "label": "事业部领导意见",//表头名 
            placeholder: "请输入",  
            opinionField:true,
            formItemLayout:{
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
            type: "textarea",// 
            "field": "opinionField4",//接口对应字段
            "label": "分管领导意见",//表头名 
            placeholder: "请输入", 
            opinionField:true,
            formItemLayout:{
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
 
        
        // {
        //     type: "textarea",
        //     label: "意见",
        //     field: "opinionField1",
        //     placeholder: "请输入",
        //     addShow: false
        // }, 
    ]
};
class index extends Component {
    render() { 
        return (
            <div  style={{ height: '100%' }}>
                <Form
                    {...this.props}
                    {...config}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    noNavBar={true}
                    // noFlowHistory={true}
                />
            </div>
        );
    }
}
export default index;
