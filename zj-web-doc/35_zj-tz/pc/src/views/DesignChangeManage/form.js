import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import { message as Msg } from "antd";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "createUserName", '设计变更'],
        apiNameByAdd: "updateZjTzDesignChangeRecord",
        apiNameByUpdate: "updateZjTzDesignChangeRecord",
        apiNameByGet: "getZjTzDesignChangeRecordDetails",
        apiTitle: "getZjTzFlowTitle",
        flowId: "zjTzDesignChangeRecord",
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        const { flowData = {} } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    formConfig={[
                        {
                            field: 'flowId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: 'zjTzDesignChangeRecord',
                            hide: true,
                        },
                        {
                            field: 'designChangeRecordId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.designChangeRecordId ? flowData.designChangeRecordId : '',
                            hide: true,
                        },
                        {
                            field: 'projectId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.projectId ? flowData.projectId : '',
                            hide: true,
                        },
                        {
                            field: 'projectName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '项目名称',
                            placeholder: '请输入',
                            initialValue: flowData.projectName ? flowData.projectName : '',
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
                            type: "select",
                            label: "子项目名称",
                            field: "subprojectInfoId",
                            placeholder: "请输入",
                            initialValue: flowData.subprojectInfoId ? flowData.subprojectInfoId : '',
                            qnnDisabled: true,
                            optionConfig: {
                                label: 'subprojectName',
                                value: 'subprojectInfoId'
                            },
                            fetchConfig: {
                                apiName: "getZjTzProSubprojectInfoList",
                                otherParams: {
                                    projectId: flowData.projectId ? flowData.projectId : ''
                                }
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
                        {
                            label: '设计阶段',
                            field: 'designStageId',
                            type: 'select',
                            qnnDisabled: true,
                            placeholder: '请选择',
                            initialValue: flowData.designStageId ? flowData.designStageId : '',
                            required: true,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'sheJiJieDuan'
                                }
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
                        {
                            label: '变更等级',
                            field: 'changeLevelId',
                            initialValue: flowData.changeLevelId ? flowData.changeLevelId : '',
                            type: 'select',
                            qnnDisabled: true,
                            placeholder: '请选择',
                            required: true,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'bianGengDengJi'
                                }
                            },
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '是否有动态设计机制',
                            field: 'dynamicId',
                            initialValue: flowData.dynamicId ? flowData.dynamicId : '',
                            required: true,
                            qnnDisabled: true,
                            type: 'select',
                            optionData: [
                                {
                                    label: "是",
                                    value: "1"
                                },
                                {
                                    label: "否",
                                    value: "0"
                                }
                            ],
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更性质',
                            field: 'changeNatureId',
                            initialValue: flowData.changeNatureId ? flowData.changeNatureId : '',
                            type: 'select',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            required: true,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'bianGengXingZhi'
                                }
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
                        {
                            label: '设计变更名称',
                            field: 'designChangeName',
                            initialValue: flowData.designChangeName ? flowData.designChangeName : '',
                            type: 'textarea',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '设计变更编号',
                            field: 'designChangeNumber',
                            initialValue: flowData.designChangeNumber ? flowData.designChangeNumber : '',
                            type: 'string',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更增减金额',
                            field: 'changeAmount',
                            qnnDisabled: true,
                            initialValue: flowData.changeAmount ? flowData.changeAmount : '',
                            type: 'number',
                            formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                            parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                            placeholder: '请输入',
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更时间',
                            field: 'changeDate',
                            initialValue: flowData.changeDate ? flowData.changeDate : '',
                            type: 'date',
                            qnnDisabled: true,
                            placeholder: '请选择',
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '设计内容简要描述',
                            field: 'content',
                            qnnDisabled: true,
                            initialValue: flowData.content ? flowData.content : '',
                            type: 'textarea',
                            required: true,
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
                            type: 'textarea',
                            label: '备注',
                            qnnDisabled: true,
                            initialValue: flowData.remarks ? flowData.remarks : '',
                            field: 'remarks',
                            placeholder: '请输入',
                            required: false,
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
                            field: 'createTime',
                            type: 'date',
                            label: '创建日期',
                            required: true,
                            qnnDisabled: true,
                            initialValue: flowData.createTime ? flowData.createTime : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            field: 'createUserName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '创建用户',
                            required: true,
                            initialValue: flowData.createUserName ? flowData.createUserName : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '附件',
                            qnnDisabled: true,
                            field: 'zjTzFileList',
                            initialValue: flowData.zjTzFileList ? flowData.zjTzFileList : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                                otherParams: {
                                    name: '设计变更管理'
                                }
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
                            },
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            onChange: (val, rowData) => {
                                if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                    Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                    setTimeout(() => {
                                        this.table.qnnForm.form.setFieldsValue({
                                            zjTzFileList: []
                                        })
                                    }, 200)
                                }
                            }
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
                            hide: true,
                            field: 'zjTzFileOpinionList',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                                otherParams: {
                                    name: '设计变更管理'
                                }
                            },
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            onChange: (val, rowData) => {
                                if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                    Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                    setTimeout(() => {
                                        this.table.qnnForm.form.setFieldsValue({
                                            zjTzFileOpinionList: []
                                        })
                                    }, 200)
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
                            if (field === 'zjTzFileOpinionList') {
                                if (flowData.flowNode.nodeId === 'Node5') {
                                    item.hide = false;
                                    item.required = true;
                                    item.disabled = false;
                                } else {
                                    item.hide = true;
                                }
                            } else if (url === `${myPublic}TodoHasTo`) {//待办
                                if (field === 'remark' || field === 'zjTzFileList') {
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
