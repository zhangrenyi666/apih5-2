import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "registrant1", '投资规模控制'],
        apiNameByAdd: "updateZjTzSizeControlRecordForFlow",
        apiNameByUpdate: "updateZjTzSizeControlRecordForFlow",
        apiNameByGet: "getZjTzSizeControlRecordDetails",
        apiTitle: "getZjTzFlowTitle",
        flowId: "zjTzSizeControlRecord",

        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        const { flowData = {}, tabs, ...other } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...other}
                    {...config}
                    formConfig={[
                        {
                            field: 'flowId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: 'zjTzSizeControlRecord',
                            hide: true,
                        },
                        {
                            type: 'component',
                            field: 'diyTZGM',
                            Component: obj => {
                                return (
                                    <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                        投资规模
                                    </div>
                                );
                            }
                        },
                        {
                            field: 'projectId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.projectId ? flowData.projectId : '',
                            hide: true,
                        },
                        {
                            field: 'sizeControlId',
                            type: 'string',
                            initialValue: flowData.sizeControlId ? flowData.sizeControlId : '',
                            hide: true,
                        },
                        {
                            type: "string",
                            label: "项目名称",
                            field: "projectName",
                            initialValue: flowData.projectName ? flowData.projectName : '',
                            placeholder: "请输入",
                            required: true,
                            qnnDisabled: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "select",
                            label: "子项目名称",
                            qnnDisabled: true,
                            field: "subprojectInfoId",
                            placeholder: "请输入",
                            span: 12,
                            initialValue: flowData.subprojectInfoId ? flowData.subprojectInfoId : '',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionConfig: {
                                label: 'subprojectName',
                                value: 'subprojectInfoId'
                            },
                            fetchConfig: {
                                apiName: "getZjTzProSubprojectInfoList",
                                otherParams: {
                                    projectId: flowData.projectId ? flowData.projectId : ''
                                }
                            }
                        },
                        {
                            type: "number",
                            label: "变动次数",
                            qnnDisabled: true,
                            field: "changeNumber",
                            initialValue: flowData.changeNumber ? flowData.changeNumber : 0,
                            required: true,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "select",
                            required: true,
                            label: "变动属性",
                            qnnDisabled: true,
                            initialValue: flowData.changePropertyId ? flowData.changePropertyId : '',
                            field: "changePropertyId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'BianDongShuXing'
                                }
                            },
                        },
                        {
                            type: "number",
                            label: "投资规模（元）",
                            required: true,
                            qnnDisabled: true,
                            field: "amount1",
                            initialValue: flowData.amount1 ? flowData.amount1 : 0,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        }, {
                            type: "number",
                            label: "建安费（元）",
                            required: true,
                            qnnDisabled: true,
                            field: "amount2",
                            initialValue: flowData.amount2 ? flowData.amount2 : 0,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        },
                        {
                            type: "number",
                            label: "查缺补漏（元）",
                            field: "amount3",
                            qnnDisabled: true,
                            required: true,
                            initialValue: flowData.amount3 ? flowData.amount3 : 0,
                            placeholder: "请输入",
                            span: 12,
                            //表单项布局
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        },
                        {
                            type: "radio",
                            label: "是否二次谈判",
                            qnnDisabled: true,
                            initialValue: flowData.secondNegotiateId ? flowData.secondNegotiateId : '',
                            field: "secondNegotiateId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ],
                        },
                        {
                            type: "textarea",
                            label: "查缺补漏方案",
                            qnnDisabled: true,
                            initialValue: flowData.scheme ? flowData.scheme : '',
                            field: "scheme",
                            required: true,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        },
                        {
                            label: '附件',
                            field: 'schemeFileList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            qnnDisabled: true,
                            initialValue: flowData.schemeFileList ? flowData.schemeFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "radio",
                            label: "三会批复",
                            qnnDisabled: true,
                            initialValue: flowData.thirdReplyId ? flowData.thirdReplyId : '',
                            field: "thirdReplyId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ],
                        },
                        {
                            label: '附件',
                            field: 'thirdReplyFileList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            qnnDisabled: true,
                            initialValue: flowData.thirdReplyFileList ? flowData.thirdReplyFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "radio",
                            label: "地方政府批复",
                            qnnDisabled: true,
                            initialValue: flowData.localGovId ? flowData.localGovId : '',
                            field: "localGovId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ],
                        },
                        {
                            label: '附件',
                            qnnDisabled: true,
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            initialValue: flowData.localGovFileList ? flowData.localGovFileList : '',
                            field: 'localGovFileList',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },

                        {
                            type: 'component',
                            field: 'diyGSXX',
                            Component: obj => {
                                return (
                                    <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                        投资事业部上传内容
                                    </div>
                                );
                            }
                        },
                        {
                            type: "radio",
                            label: "一公局集团批复",
                            qnnDisabled: true,
                            initialValue: flowData.juId ? flowData.juId : '',
                            field: "juId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ]
                        },
                        {
                            label: '附件',
                            field: 'juFileList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            qnnDisabled: true,
                            initialValue: flowData.juFileList ? flowData.juFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "radio",
                            label: "中国交建批复",
                            qnnDisabled: true,
                            field: "chinaId",
                            placeholder: "请输入",
                            initialValue: flowData.chinaId ? flowData.chinaId : '',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ]
                        },
                        {
                            label: '附件',
                            field: 'chinaFileList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            qnnDisabled: true,
                            initialValue: flowData.chinaFileList ? flowData.chinaFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: 'component',
                            field: 'diyHTTJ',
                            Component: obj => {
                                return (
                                    <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                        合同条件
                                    </div>
                                );
                            }
                        },
                        {
                            field: 'sizeControlRecordId',
                            qnnDisabled: true,
                            initialValue: flowData.sizeControlRecordId ? flowData.sizeControlRecordId : '',
                            type: 'string',
                            hide: true,
                        },
                        {
                            type: "string",
                            label: "主键ID",
                            qnnDisabled: true,
                            initialValue: flowData.contractConditionId ? flowData.contractConditionId : '',
                            field: "contractConditionId",
                            hide: true
                        },
                        {
                            field: 'registerDate1',
                            type: 'date',
                            qnnDisabled: true,
                            label: '登记日期',
                            required: true,
                            initialValue: flowData.registerDate1 ? flowData.registerDate1 : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            field: 'registrant1',
                            type: 'string',
                            label: '登记用户',
                            required: true,
                            qnnDisabled: true,
                            initialValue: flowData.registrant1 ? flowData.registrant1 : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            type: "select",
                            label: "投资收益模式",
                            required: true,
                            qnnDisabled: true,
                            initialValue: flowData.investId ? flowData.investId : '',
                            field: "investId",
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'TouZiShouYiMoShi'
                                }
                            }
                        },
                        {
                            type: "number",
                            label: "一公局集团股比",
                            required: true,
                            qnnDisabled: true,
                            field: "juShare",
                            initialValue: flowData.juShare ? flowData.juShare : 0,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        },
                        {
                            type: "select",
                            label: "一公局集团控制性地位",
                            field: "juId1",
                            qnnDisabled: true,
                            initialValue: flowData.juId1 ? flowData.juId1 : '',
                            placeholder: "请输入",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'yiGongJuJiTuanKongZhiXingDiWei'
                                }
                            }
                        },
                        {
                            type: "select",
                            label: "总承包结算模式",
                            initialValue: flowData.zcbId ? flowData.zcbId : '',
                            field: "zcbId",
                            required: true,
                            qnnDisabled: true,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'ZongChengBaoJieSuanMoShi'
                                }
                            }
                        },
                        {
                            type: "number",
                            label: "施工总承包比例",
                            field: "zcbShare",
                            qnnDisabled: true,
                            required: true,
                            initialValue: flowData.zcbShare ? flowData.zcbShare : 0,
                            placeholder: "请输入",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 16 }
                                }
                            },
                        },
                        {
                            type: "textarea",
                            label: "设计管理情况",
                            field: "ext1",
                            qnnDisabled: true,
                            required: true,
                            initialValue: flowData.ext1 ? flowData.ext1 : '',
                            placeholder: "请输入",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "合同对投资规模控制的要求",
                            qnnDisabled: true,
                            field: "ext2",
                            initialValue: flowData.ext2 ? flowData.ext2 : '',
                            placeholder: "请输入",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            type: "textarea",
                            label: "设计变更特殊条款",
                            qnnDisabled: true,
                            field: "ext3",
                            initialValue: flowData.ext3 ? flowData.ext3 : '',
                            placeholder: "请输入",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            label: '附件',
                            field: 'zjTzFileList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            qnnDisabled: true,
                            initialValue: flowData.zjTzFileList ? flowData.zjTzFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 20 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            },
                        },
                        {
                            field: 'opinionField1',
                            type: 'textarea',
                            initialValue: flowData.contentDesc ? flowData.contentDesc : '',
                            label: "项目公司意见",
                            required: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "托管公司",
                            field: "opinionField2",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "投资事业部审核",
                            field: "opinionField3",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "局各部门评审",
                            field: "opinionField4",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '附件',
                            field: 'zjTzFileOpinionList',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            hide: true,
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    fieldsCURD={(fields, flowData, props) => {
                        var fieldsnew = fields.map((item) => {
                            let { field } = item;
                            var url = props.match.url;
                            var myPublic = props.myPublic.appInfo.mainModule;
                            if (url === `${myPublic}TodoHasTo`) {//待办
                                if (field === 'changePropertyId' || field === 'amount1' || field === 'amount2' || field === 'amount3' || field === 'secondNegotiateId' || field === 'scheme' || field === 'schemeFileList' || field === 'thirdReplyId' || field === 'thirdReplyFileList' || field === 'localGovId' || field === 'localGovFileList' || field === 'juId' || field === 'juFileList' || field === 'chinaId' || field === 'chinaFileList' || field === 'registerDate1' || field === 'registrant1' || field === 'investId' || field === 'juShare' || field === 'juId1' || field === 'zcbId' || field === 'zcbShare' || field === 'ext1' || field === 'ext2' || field === 'ext3' || field === 'zjTzFileList') {
                                    item.disabled = false;
                                } else {

                                }
                            }
                            return item;
                        });
                        return fieldsnew
                    }}
                />
            </div>
        );
    }
}
export default index;
