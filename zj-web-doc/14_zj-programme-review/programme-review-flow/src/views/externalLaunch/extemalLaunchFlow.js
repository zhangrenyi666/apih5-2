import React,{ Component } from "react";
import { Form } from "../modules/work-flow";
import CangYongYiJian from "./CangYongYiJian";
let config = {
    //流程专属配置  ---在qnn-table中配后这也得配
    workFlowConfig: {
        //后台定的字段
        title: ["schemeName"], //标题字段
        apiNameByAdd: "addZjPrExtemalLaunchFlow",
        apiNameByUpdate: "updateZjPrExtemalLaunchFlow",
        apiNameByGet: "getZjPrExtemalLaunchFlowDetailByWorkId",
        flowId: "zjProExternalLaunchFlow",
    },
    openParamsFormat: (body,props) => {
        var rowData = props.rowData;
        return {
            trackId: rowData.trackId,
            ...body
        }
    },  
    fieldsCURD: (fields,flowData,props) => {
        return fields.map((item) => {
            let { field,required } = item;
            if (field === "score") {
                // console.log(item)
                if (required) {
                    item.hide = false;
                    item.qnnDisabled = false;
                    item.disabled = false;
                } else {
                    item.hide = true;
                }

            }
            return item;
        });
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        let nodeName = "";
        if (this.props.rowData) {
            nodeName = this.props.rowData.nodeName;
            // console.log(nodeName);
        }
        // console.log(this.props);
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    createOpenFlowCallback={({ props }) => {
                        props.btnCallbackFn.clearSelectedRows()
                        // console.log(obj)
                    }}
                    componentsKey={{
                        cangYongYiJian: CangYongYiJian
                    }}
                    formConfig={[
                        {
                            type: "string",
                            label: "workId",
                            field: "workId",
                            hide: true,
                            initialValue: function (obj) {
                                // console.log(obj);
                                return obj.match.params["workId"];
                            }
                        },
                        {
                            type: "string",
                            label: "trackId",
                            field: "trackId",
                            hide: true,
                            initialValue: function (obj) {
                                return obj.match.params["trackId"];
                            }
                        },                     
                        {
                            type: "string",
                            label: "发起ID",
                            field: "launchId",
                            hide: true
                        },
                        {
                            type: "string",
                            label: "方案ID",
                            field: "schemeId",
                            hide: true
                        },
                        {
                            type: "string",
                            label: "清单ID",
                            field: "detailedListId",
                            hide: true
                        },
                        {
                            type: 'string',
                            label: '方案类型',
                            field: 'schemeType', //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            type: 'string',
                            label: '项目所属板块',
                            field: 'projectClass', //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            //普通选择框 可以和其他字段关联
                            type: "selectByPaging",
                            label: "项目名称",
                            field: "projectName", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            required: true,
                            fetchConfig: {
                                apiName: "getZjSchemeConfirmationListBySelect",
                                otherParams: {
                                    codePid: "0"
                                },
                                searchKey: 'projectName'
                            },
                            optionConfig: {
                                //下拉选项配置
                                label: "projectName",
                                value: "recordid"
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            },
                            onChange: function (val,props) {

                                if (val) {

                                    //需要清空的子字段名字
                                    var childrenField = 'schemeNumber';

                                    props.props.form.resetFields([childrenField])
                                }

                            },
                            //可以和别的输入框联动起来
                            condition: [
                                {//条件
                                    regex: {
                                        schemeNumber: null,
                                    },
                                    action: 'disabled',
                                },
                            ]
                        },
                        {
                            //普通选择框 可以和其他字段关联
                            type: "selectByPaging",
                            label: "方案编号",
                            field: "schemeNumber", //唯一的字段名 ***必传
                            required: true,
                            placeholder: "请选择",
                            fetchConfig: {
                                apiName: "getZjSchemeAdoptDetailedListAllList",
                                params: {
                                    recordid: "apiBody.projectName"
                                },
                                // searchKey:'schemeName'
                            },
                            span: 12,
                            optionConfig: {
                                //下拉选项配置
                                label: "schemeNumber",
                                value: "schemeNumber",
                                linkageFields: {
                                    schemeName: "schemeName",
                                    engineeringType: "engineeringType",
                                    province: "province",
                                    hierarchyDescription: "hierarchyDescription",
                                    schemeId: "schemeId",
                                    implementationTime: "implementationTime",
                                    projectGeneralUser: "projectChiefEng",
                                    projectGeneralUserTel: "projectManagerTel",
                                    compilingSubject: "compilingSubject",
                                    projectLocation: "projectLocation",
                                    schemeType: "schemeType",
                                    projectClass: "projectClass"
                                    // linkageTwo:"orgId",
                                }
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'string',
                            label: '方案名称',
                            field: 'schemeName', //唯一的字段名 ***必传
                            qnnDisabled: true,
                            opinionField: true,
                            placeholder: '请输入',//占位符
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'select',
                            label: '编制主体',
                            field: 'compilingSubject', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            // required: true,
                            span: 12,
                            qnnDisabled: true,
                            opinionField: true,
                            // multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                            // showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                            optionData: [//默认选项数据//可为function (props)=>array
                                {
                                    name: '第一技术分中心',
                                    id: '4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '第二技术分中心',
                                    id: '4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '第三技术分中心',
                                    id: '1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '第四技术分中心',
                                    id: '4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '第五技术分中心',
                                    id: '848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51',
                                },
                                {
                                    name: '第六技术分中心',
                                    id: '4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '第七技术分中心',
                                    id: '4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '厦门技术分中心',
                                    id: '4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '桥隧技术分中心',
                                    id: '4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '海威技术分中心',
                                    id: '2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51',
                                },
                                {
                                    name: '总承包技术分中心',
                                    id: '4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '建筑技术分中心',
                                    id: '15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039',
                                },
                                {
                                    name: '世通技术分中心',
                                    id: '4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4',
                                },
                                {
                                    name: '海外技术中心',
                                    id: '22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039',
                                }, {
                                    value: "suidj-189",
                                    text: "北京技术分中心"
                                }, {
                                    value: "suidj-240",
                                    text: "西北技术分中心"
                                }, {
                                    value: "suidj-423",
                                    text: "南京技术分中心"
                                }, {
                                    value: "suidj-549",
                                    text: "西南技术分中心"
                                }, {
                                    value: "suidj-584",
                                    text: "第八技术分中心"
                                }, {
                                    value: "suidj-2183",
                                    text: "华北技术分中心"
                                }, {
                                    value: "suidj-2230",
                                    text: "华南技术分中心"
                                }, {
                                    value: "suidj-768",
                                    text: "盾构技术分中心"
                                }, {
                                    value: "suidj-800",
                                    text: "电气化技术分中心"
                                },{
                                    id: "suidj-8a8bb35a765172cc01768484656d0be1",
                                    name: "华中技术分中心"
                                }
                            ],
                            optionConfig: {//下拉选项配置
                                label: 'name', //默认 label
                                value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'string',
                            label: '项目位置',
                            field: 'projectLocation', //唯一的字段名 ***必传
                            placeholder: '请输入',//占位符
                            span: 12,
                            qnnDisabled: true,
                            opinionField: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'datetime',
                            label: '方案计划实施时间',
                            field: 'implementationTime', //唯一的字段名 ***必传
                            span: 12,
                            placeholder: '请选择',
                            qnnDisabled: true,
                            opinionField: true,
                            is24: true,//是否是24小时制 默认true
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'string',
                            label: '方案编制人',
                            field: 'programmingPerson', //唯一的字段名 ***必传
                            qnnDisabled: true,
                            opinionField: true,
                            placeholder: '请输入',//占位符
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'string',
                            label: '编制人联系方式',
                            field: 'programmingPersonTel', //唯一的字段名 ***必传
                            qnnDisabled: true,
                            opinionField: true,
                            placeholder: '请输入',//占位符
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    sm: { span: 18 }
                                }
                            },
                        },
                        {
                            type: 'textarea',
                            label: '等级划分说明/施工重难点',
                            field: 'hierarchyDescription', //唯一的字段名 ***必传
                            qnnDisabled: true,
                            opinionField: true,
                            placeholder: '请输入',//占位符
                            rows: 20, //行高 默认4                        
                            autosize: { minRows: 6,maxRows: 20 },
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            type: 'textarea',
                            label: '备注',
                            field: 'remarks', //唯一的字段名 ***必传
                            qnnDisabled: true,
                            opinionField: true,
                            rows: 20, //行高 默认4                        
                            placeholder: '请输入',//占位符
                            span: 24,
                            autosize: { minRows: 6,maxRows: 20 },
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            type: 'files',
                            label: '方案正文（最大200M）',
                            field: 'documentText', //唯一的字段名 ***必传
                            desc: '点击上传', //默认 点击或者拖动上传
                            subdesc: '只支持单个上传',//默认空
                            help: "（包含修改意见回复）",
                            onRemove: false,
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                                // name:'123', //上传文件的name 默认空
                            },
                            // accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            // max: 2, //最大上传数量
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            type: "textarea",
                            label: "科学技术质量部意见",
                            field: "opinionField1",
                            placeholder: "请输入", //占位符
                            // help: "（负责依法合规、质量管理、创优规划、技术可行性、技术创新等审查）",
                            addShow: false,
                            opinionField: true,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            type: "textarea",
                            label: "公司总工审查意见",
                            field: "opinionField2",
                            placeholder: "请输入", //占位符
                            addShow: false,
                            opinionField: true,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    sm: { span: 20 }
                                }
                            },
                        }, 
                    ]}
                    fetchConfig={{}}
                    tabs={[]} />
            </div>
        );
    }
}
export default index;
