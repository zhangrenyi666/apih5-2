import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import moment from "moment";

let config = {
    //流程专属配置  --qnn-table中配了这也得配置
    workFlowConfig: {
        //流程特有配置
        title: ["信息化需求确认"], //标题字段
        apiNameByUpdate: "updateFlowNeedsConfirmAfterSubmit",
        apiNameByGet: "getFlowNeedsConfirmDetailByWorkId",
        flowId: "flowIdZj1",
        formLink: {
            flowIdZj1: "FlowByflowIdZj1"
        },
        //待办已办切换路由
        todo: "todoByflowIdZj1",
        hasTodo: "hasTodoByflowIdZj1"
    },
    formConfig: [
        {
            type: "string",
            label: "申请时间",
            field: "createTime",
            placeholder: "请输入",
            hide: true
        },
        {
            type: "string",
            label: "workId",
            field: "workId",
            hide: true,
            initialValue: function(obj) {
                return obj.match.params["workId"];
            }
        },
        {
            type: "string",
            label: "部门",
            field: "deptName",
            placeholder: "无"
        },
        /* {
            type: "treeSelect",
            label: "部门",
            field: "oaDept",
            initialValue: [],
            treeSelectOption: {
                help: true,
                fetchConfig: {
                    //配置后将会去请求下拉选项数据
                    apiName: "getSysDepartmentUserAllTree"
                },
                selectType:"1",
                search: false,
                searchPlaceholder: "姓名、账号、电话",
                searchParamsKey: "search", //搜索文字的K 默认是'searchText'   [string]
                searchOtherParams: { pageSize: 999 } //搜索时的其他参数  [object]
            }
        }, */
        {
            type: "string",
            label: "业务模块",
            field: "serviceModule",
            placeholder: "无"
        },
        {
            type: "string",
            label: "确认人",
            field: "verifier",
            placeholder: "无"
        },
        {
            type: "string",
            label: "联系方式",
            field: "phone",
            placeholder: "无"
        },
        {
            type: "select",
            label: "工作内容",
            field: "workContent",
            placeholder: "无",
            optionData: [
                //默认选项数据
                {
                    name: "系统分析",
                    id: "0"
                },
                {
                    name: "系统开发",
                    id: "1"
                },
                {
                    name: "系统测试",
                    id: "2"
                },
                {
                    name: "现场验证",
                    id: "3"
                },
                {
                    name: "系统分析   、  系统开发",
                    id: "4"
                },
                {
                    name: "系统分析   、  系统测试",
                    id: "5"
                },
                {
                    name: "系统分析   、 现场验",
                    id: "6"
                },
                {
                    name: "系统开发   、  系统测试",
                    id: "7"
                },
                {
                    name: "系统开发   、   现场验证",
                    id: "8"
                },
                {
                    name: "系统测试   、   现场验证",
                    id: "9"
                },
                {
                    name: "系统分析   、系统开发  、 系统测试",
                    id: "10"
                },
                {
                    name: "系统分析   、系统开发  、 现场验证",
                    id: "11"
                },
                {
                    name: "系统开发   、系统测试  、 现场验证",
                    id: "12"
                },
                {
                    name: "系统分析   、系统开发  、 系统测试 、 现场验证",
                    id: "13"
                }
            ],
            optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
            }
        },
        {
            type: "datetime",
            label: "预计完成时间",
            field: "estimatedTime",
            placeholder: "无"
        },
        {
            type: "textarea",
            label: "内容描述",
            field: "contentDesc",
            placeholder: "无"
        },
        {
            type: "files",
            label: "附件",
            field: "needsConfirmFileList",
            desc: "点击选择上传", //默认 点击或者拖动上传
            fetchConfig: {
                //配置后将会去请求下拉选项数据
                apiName: window.configs.domain + "upload"
            }
        },
        {
            type: "textarea",
            label: "分管领导意见",
            field: "opinionField1",
            addShow: false
        },
        {
            type: "textarea",
            label: "其他部门意见",
            field: "opinionField2",
            addShow: false
        },
        {
            type: "textarea",
            label: "业务部门意见",
            field: "opinionField3",
            addShow: false
        },
        {
            type: "textarea",
            label: "信息化管理部意见",
            field: "opinionField4",
            addShow: false
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
