// import { basic } from '../modules/layouts';
import React,{ Component } from "react";
import { Form } from 'antd';
import QnnForm from '../modules/qnn-table/qnn-form';
// import { callbackify } from 'util';
let config = {
    //qnn-form配置
    style: {
        height: window.innerHeight - 55,
        overflowY: 'scroll'
    },
    formConfig: [
        {
            type: 'string',
            label: '主键ID',
            field: 'recordid', //唯一的字段名 ***必传
            hide: true
        },
        {
            type: 'string',
            label: '方案id',
            field: 'detailedListId', //唯一的字段名 ***必传
            hide: true
        },
        {
            type: 'string',
            label: '方案审批id',
            field: 'launchId', //唯一的字段名 ***必传
            hide: true
        },
        {
            type: 'string',
            label: '方案id',
            field: 'schemeId', //唯一的字段名 ***必传
            hide: true
        },
        {
            type: 'string',
            label: '项目名称',
            field: 'projectName', //唯一的字段名 ***必传
            disabled: true,
            editDisabled: true,
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
            label: '方案编号',
            field: 'schemeNumber', //唯一的字段名 ***必传
            disabled: true,
            editDisabled: true,
            placeholder: '请输入',//占位符
            span: 12,
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
            disabled: true,
            editDisabled: true,
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
        // {
        //     type: 'string',
        //     label: '工程类别',
        //     field: 'engineeringType', //唯一的字段名 ***必传
        //     disabled: true,
        //     editDisabled: true,
        //     placeholder: '请输入',//占位符
        //     span: 12,
        //     formItemLayout: {
        //         labelCol: {
        //             xs: { span: 24 },
        //             sm: { span: 4 }
        //         },
        //         wrapperCol: {
        //             xs: { span: 24 },
        //             sm: { span: 18 }
        //         }
        //     },
        // },
        {
            type: 'string',
            label: '编制主体',
            field: 'engineeringType', //唯一的字段名 ***必传
            disabled: true,
            editDisabled: true,
            placeholder: '请输入',//占位符
            span: 12,
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
            label: '所属省份',
            field: 'province', //唯一的字段名 ***必传
            disabled: true,
            editDisabled: true,
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
            type: 'datetime',
            label: '方案计划实施时间',
            field: 'implementationTime', //唯一的字段名 ***必传
            span: 12,
            placeholder: '请选择',
            disabled: true,
            editDisabled: true,
            is24: true,//是否是24小时制 默认true
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
        // {
        //     type: 'string',
        //     label: '项目总工',
        //     field: 'projectGeneralUser', //唯一的字段名 ***必传
        //     disabled: true,
        //     editDisabled: true,
        //     placeholder: '请输入',//占位符
        //     span: 12,
        //     formItemLayout: {
        //         labelCol: {
        //             xs: { span: 24 },
        //             sm: { span: 3 }
        //         },
        //         wrapperCol: {
        //             xs: { span: 24 },
        //             sm: { span: 18 }
        //         }
        //     },
        // },
        // {
        //     type: 'string',
        //     label: '总工联系方式',
        //     field: 'projectGeneralUserTel', //唯一的字段名 ***必传
        //     disabled: true,
        //     editDisabled: true,
        //     placeholder: '请输入',//占位符
        //     span: 12,
        //     formItemLayout: {
        //         labelCol: {
        //             xs: { span: 24 },
        //             sm: { span: 4 }
        //         },
        //         wrapperCol: {
        //             xs: { span: 24 },
        //             sm: { span: 18 }
        //         }
        //     },
        // },
        {
            type: 'string',
            label: '方案编制人',
            field: 'programmingPerson', //唯一的字段名 ***必传
            disabled: true,		            
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
            disabled: true,		
            placeholder: '请输入',//占位符
            span: 12,
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
            type: 'textarea',
            label: '等级划分说明/施工重难点',
            field: 'hierarchyDescription', //唯一的字段名 ***必传
            disabled: true,
            disabled: true,		
            placeholder: '请输入',//占位符
            rows: 20, //行高 默认4                        
            autosize: { minRows: 6,maxRows: 20 },
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
            type: 'files',
            label: '方案正文',
            field: 'documentTextNew', //唯一的字段名 ***必传
            disabled: true,		
            desc: '点击或者拖动上传', //默认 点击或者拖动上传
            subdesc: '只支持单个上传',//默认空
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
            type: 'files',
            label: '方案附件',
            field: 'documentAccessoryNew', //唯一的字段名 ***必传
            disabled: true,		            
            desc: '点击或者拖动上传', //默认 点击或者拖动上传
            subdesc: '只支持单个上传',//默认空
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
            type: 'textarea',
            label: '备注',
            field: 'remarks', //唯一的字段名 ***必传
            disabled: true,		
            rows: 20, //行高 默认4                        
            placeholder: '请输入',//占位符
            span: 24,
            autosize: { minRows: 6,maxRows: 20 },
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
            type: 'textarea',
            label: '方案初评情况',
            // editDisabled:true,  								                          
            rows: 20, //行高 默认4
            field: 'programmingPreliminaryTrial', //唯一的字段名 ***必传
            disabled: true,		
            placeholder: '请输入',//占位符
            span: 24,
            autosize: { minRows: 6,maxRows: 20 },
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
        }

    ]
};
class index extends Component {
    state = { seted:false }
    render() {
        return (
            <div>
                <QnnForm
                    {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                    fetch={this.props.myFetch} 		 upload={this.props.myUpload} //必须返回一个promise
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}

                    wrappedComponentRef={(me)=>{ 
                        if(me && !this.state.seted){
                            me.btnfns.setValues(this.props.rowData);
                            this.setState({seted:true})
                        }
                    }}

                    //需要从置父级传入的fetch配置和tabs配置 否则会覆盖本组件页面的配置
                    fetchConfig={{}} 
                    tabs={[]} />
            </div>
        );
    }
}
export default Form.create()(index)
