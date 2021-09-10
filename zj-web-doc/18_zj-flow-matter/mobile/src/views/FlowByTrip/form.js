import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from "moment";

const config = {
    //流程专属配置  --qnn-table中配了这也得配置
    workFlowConfig: {
        //后台定的字段
        title: ["", "", "出差申请"], //标题字段
        apiNameByAdd: "addZjFlowTripApply",
        apiNameByUpdate: "updateZjFlowTripApply",
        apiNameByGet: "getZjFlowTripApplyDetail",
        flowId: "zjTripApply",
        formLink: {
            zjLeaveApply: "FlowByzjLeaveApply",
            zjWorkApply: "FlowByzjWorkApply",
            zjTripApply: "FlowByzjTripApply"
        },
        //待办已办切换路由
        todo: "todoByzjTripApply",
        hasTodo: "hasTodoByzjTripApply"
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
            type: "text",
            label: "标题",
            field: "tripTitle",
            addShow: false
        },
        {
            type: "datetime",
            label: "申请日期",
            field: "applyDate",
            placeholder: "无"
        },
        {
            type: "string",
            label: "姓名",
            field: "name",
            placeholder: "请输入",
            required: true
        },
        {
            label: "部门",
            field: "oaDeptList",
            type: "treeSelect",
            initialValue: [],
            treeSelectOption: {
                help: true,
                selectType: "1",
                maxNumber: 1,
                fetchConfig: {
                    //配置后将会去请求下拉选项数据
                    apiName: "getSysDepartmentAllTree2"
                },
                search: true,

                searchPlaceholder: "姓名、账号、电话",
                // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                searchParamsKey: "search", //搜索文字的K 默认是'searchText'   [string]
                searchOtherParams: { pageSize: 999 } //搜索时的其他参数  [object]
            }
        },
        {
            type: "string",
            label: "职别",
            field: "position",
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
            type: "string",
            label: "出差地点",
            field: "tripPlace",
            placeholder: "请输入",
            required: true
        },
        {
            type: "select",
            label: "交通工具",
            placeholder: "请选择",
            field: "vehicle",
            required: true,
            optionConfig: {
                label: "label",
                value: "value"
            },
            optionData: [
                {
                    value: "0",
                    label: "汽车"
                },
                {
                    value: "1",
                    label: "火车"
                },
                {
                    value: "2",
                    label: "飞机"
                },
                {
                    value: "3",
                    label: "其他"
                }
            ]
        },
        {
            type: "string",
            label: "出差人",
            field: "traveller",
            placeholder: "请输入",
            required: true
        },
        {
            type: "textarea",
            label: "出差事由",
            field: "tripReason",
            placeholder: "请输入"
        },
        {
            type: "textarea",
            label: "乘坐飞机原因",
            field: "flyReason",
            placeholder: "请输入"
        },
        {
            type: "qnnForm",
            field: "detailList",
            label: "出差详情",
            //文字配置 默认数据如下 [object]
            textObj: {
                add: "添加出差详情",
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
                        type: "string",
                        label: "开始地点",
                        field: "startPlace",
                        placeholder: "请输入"
                    },
                    {
                        type: "string",
                        label: "结束地点",
                        field: "endPlace",
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
            label: "注",
            field: " ",
            qnnDisabled: true,
            initialValue:
                "01.北京市内出差：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报公司报分管领导审批；\n02.北京市以外地区出差：部门、中心领导以下员工出差3天以下（含3天）由所在部门、中心负责人批准；3天以上7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准。部门、中心领导（含）以上员工出差7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准； 事业部公司管干部正副职负责人出差，7天以下（含7天）由公司分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门由公司总经理批准。事业部其他人员出差，1天以下（含1天）由所在部门处室负责人批准；2天以上7天以下（含7天）由事业部部门负责人批准；7天以上，党群部门由事业部党委书记批准，其他部门由事业部总经理批准。 加班：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报分管领导批准",
            formItemLayout: {
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
            type: "textarea",
            label: "部门负责人意见",
            field: "opinionField1",
            addShow: false
        },
        {
            type: "textarea",
            label: "公司分管领导意见",
            field: "opinionField2",
            addShow: false
        },
        {
            type: "textarea",
            label: "公司总经理意见",
            field: "opinionField3",
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
