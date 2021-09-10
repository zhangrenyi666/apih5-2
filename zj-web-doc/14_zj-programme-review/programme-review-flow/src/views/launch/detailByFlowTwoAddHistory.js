// import { basic } from '../modules/layouts';
import React,{ Component } from "react";
import { Form,Tabs } from 'antd';
import QnnForm from '../modules/qnn-table/qnn-form';
import IndexFlow from './indexFlow';
const { TabPane } = Tabs;
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
            label: '流程ID',
            field: 'workId', //唯一的字段名 ***必传
            hide: true
        },     
        {
            type: 'string',
            label: 'trackID',
            field: 'trackId', //唯一的字段名 ***必传
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
            type: 'string',
            label: '方案编号',
            field: 'schemeNumber', //唯一的字段名 ***必传
            disabled: true,	
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
            type: 'string',
            label: '方案名称',
            field: 'schemeName', //唯一的字段名 ***必传
            disabled: true,	
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
            disabled: true,	
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
                },{
                    id: "suidj-189",
                    name: "北京技术分中心"
                }, {
                    id: "suidj-240",
                    name: "西北技术分中心"
                }, {
                    id: "suidj-423",
                    name: "南京技术分中心"
                }, {
                    id: "suidj-549",
                    name: "西南技术分中心"
                }, {
                    id: "suidj-584",
                    name: "第八技术分中心"
                }, {
                    id: "suidj-2183",
                    name: "华北技术分中心"
                }, {
                    id: "suidj-2230",
                    name: "华南技术分中心"
                }, {
                    id: "suidj-768",
                    name: "盾构技术分中心"
                }, {
                    id: "suidj-800",
                    name: "电气化技术分中心"
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
            disabled: true,	
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
                    sm: { span: 4 }
                },
                wrapperCol: {
                    sm: { span: 18 }
                }
            },
        },
        {
            type: 'string',
            label: '专家评分',
            field: 'expertScore', //唯一的字段名 ***必传
            disabled: true,	
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
            type: 'string',
            label: '方案得分',
            field: 'programmingScore', //唯一的字段名 ***必传
            disabled: true,	
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
            disabled: true,	
            placeholder: '请输入',//占位符
            rows: 20, //行高 默认4                        
            autosize: { minRows: 6, maxRows: 20 },
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
            disabled: true,	
            rows: 20, //行高 默认4                        
            placeholder: '请输入',//占位符
            span: 24,
            autosize: { minRows: 6, maxRows: 20 },
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
        // {
        //     type: 'files',
        //     label: '公文附件',
        //     field: 'documentAccessoryNew', //唯一的字段名 ***必传
        //     disabled: true,		            
        //     desc: '点击或者拖动上传', //默认 点击或者拖动上传
        //     subdesc: '只支持单个上传',//默认空
        //     fetchConfig: {
        //         apiName: window.configs.domain + 'upload',
        //         // name:'123', //上传文件的name 默认空
        //     },
        //     // accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
        //     // max: 2, //最大上传数量
        //     formItemLayout: {
        //         labelCol: {
        //             xs: { span: 24 },
        //             sm: { span: 3 }
        //         },
        //         wrapperCol: {
        //             xs: { span: 24 },
        //             sm: { span: 20 }
        //         }
        //     },
        // },
        // {
        //     type: 'textarea',
        //     label: '备注',
        //     field: 'remarks', //唯一的字段名 ***必传
        //     disabled: true,		
        //     rows: 20, //行高 默认4                        
        //     placeholder: '请输入',//占位符
        //     span: 24,
        //     autosize: { minRows: 6,maxRows: 20 },
        //     formItemLayout: {
        //         labelCol: {
        //             xs: { span: 24 },
        //             sm: { span: 3 }
        //         },
        //         wrapperCol: {
        //             xs: { span: 24 },
        //             sm: { span: 20 }
        //         }
        //     },
        // },
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
        },
        {
            type: "textarea",
            label: "事业部技术负责人审查意见",
            field: "opinionField2",
            placeholder: "请输入", //占位符
            disabled: true,	
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
        // {
        //     type: "textarea",
        //     label: "总承包部审查意见",
        //     field: "opinionField5",
        //     placeholder: "请输入", //占位符
        //     disabled: true,	
        //     span: 24,
        //     autosize: { minRows: 6,maxRows: 20 },
        //     formItemLayout: {
        //         labelCol: {
        //             sm: { span: 3 }
        //         },
        //         wrapperCol: {
        //             sm: { span: 20 }
        //         }
        //     },            
        // },
        {
            type: "textarea",
            label: "专家审查意见",
            field: "opinionField4",
            placeholder: "请输入", //占位符
            disabled: true,	
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
            type: "textarea",
            label: "事业部施工部审查意见",
            field: "opinionField3",
            placeholder: "请输入", //占位符
            disabled: true,	
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
            type: "textarea",
            label: "科学技术质量部意见",
            field: "opinionField6",
            placeholder: "请输入", //占位符
            disabled: true,	
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
            type: "textarea",
            label: "公司总工审查意见",
            field: "opinionField7",
            placeholder: "请输入", //占位符
            disabled: true,	
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

    ]
};
class index extends Component {
    // state = { seted:false }
    // render() {
    //     return (
    //         <div>
    //             <QnnForm
    //                 {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
    //                 fetch={this.props.myFetch} 
	// 	 upload={this.props.myUpload} //必须返回一个promise
    //                 headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
    //                 {...config}

    //                 wrappedComponentRef={(me)=>{ 
    //                     if(me && !this.state.seted){
    //                         me.btnfns.setValues(this.props.rowData);
    //                         this.setState({seted:true})
    //                     }
    //                 }}

    //                 //需要从置父级传入的fetch配置和tabs配置 否则会覆盖本组件页面的配置
    //                 fetchConfig={{}} 
    //                 tabs={[]} />
    //         </div>
    //     );
    // }
    state = { seted: false, activeKey: '1' }
    callback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render() {
        const { activeKey } = this.state;
        const { trackId = '' } = this.props.rowData;
        return (
            <div>
                {trackId ? <Tabs activeKey={activeKey} onChange={this.callback}>
                    <TabPane tab="基础信息" key="1">
                        <QnnForm
                            {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            {...config}

                            wrappedComponentRef={(me) => {
                                if (me && !this.state.seted) {
                                    me.btnfns.setValues(this.props.rowData);
                                    this.setState({ seted: true })
                                }
                            }}

                            //需要从置父级传入的fetch配置和tabs配置 否则会覆盖本组件页面的配置
                            fetchConfig={{}}
                            tabs={[]} />
                    </TabPane>
                    <TabPane tab="审批历史" key="2">
                     <IndexFlow {...this.props}/>
                    </TabPane>
                </Tabs> : <QnnForm
                            {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            {...config}

                            wrappedComponentRef={(me) => {
                                if (me && !this.state.seted) {
                                    me.btnfns.setValues(this.props.rowData);
                                    this.setState({ seted: true })
                                }
                            }}

                            //需要从置父级传入的fetch配置和tabs配置 否则会覆盖本组件页面的配置
                            fetchConfig={{}}
                            tabs={[]} />}
            </div>
        );
    }    
}
export default Form.create()(index)
