import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';

class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            source:props.match.params.source
        };
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { source } = this.state;
        return (
            
            <div className={s.root}>
            <div>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}PersonPage/${this.props.saveNodes.node.workId}/${this.props.saveNodes.node.status}/${this.props.saveNodes.node.nodeName}/${source}`));
                    }}
                >
                    {"意见"}
                </NavBar>
            </div>
            <div
                style={{
                    height: document.documentElement.clientHeight - 45
                }}
            >
                <QnnForm
                    {...this.props} 
                    match={this.props.match}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload} //必须返回一个promise
                    data={this.props.saveNodes.node}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 8 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 16 },
                            sm: { span: 16 }
                        }
                        }}
                        formConfig={[
                            {
                                type: "string",
                                label: "主键ID",
                                field: "personInfoId", 
                                hide: true
                            },
                            {
                                field: 'status',
                                hide: true,
                                type: 'string',
                            },
                            {
                                field: 'nodeName',
                                hide: true,
                                type: 'string',
                            },
                            {
                                type: "textarea",
                                label: "人力资源部资格初审意见",
                                // required:true,
                                field: "opinionField1", 
                                placeholder: "请输入",
                                disabled: (obj) => {
                                    var nodeName = obj.form.getFieldValue('nodeName');
                                    if (nodeName === '人力资源初审') {
                                        return false 
                                    } else {
                                        return true
                                    }
                                    
                                },
                                dependencies: ["nodeName"]
                            },
                            {
                                type: "textarea",
                                label: "审批意见",
                                field: "opinionField2", 
                                // required:true,
                                placeholder: "请输入",
                                disabled: (obj) => {
                                    var nodeName = obj.form.getFieldValue('nodeName');
                                    if (nodeName === '公司主管领导') {
                                        return false 
                                    } else {
                                        return true
                                    }
                                },
                                dependencies: ["nodeName"],
                            }
                        ]}
                        btns={[
                            {
                                name: 'diy', 
                                type: 'dashed',
                                label: '取消',
                                field: "detailCancelBtn",
                                onClick: () => {
                                    dispatch(push(`${mainModule}PersonPage/${this.props.saveNodes.node.workId}/${this.props.saveNodes.node.status}/${this.props.saveNodes.node.nodeName}/${source}`));
                                }
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '提交',
                                onClick: (obj) => {
                                    var params = {
                                        
                                    };
                                    params.personInfoId = obj.values.personInfoId;
                                    params.opinionField1 = obj.values.opinionField1;
                                    params.opinionField2 = obj.values.opinionField2;
                                    this.props.myFetch('approveZjFlowPersonInfo',params).then(({ success,data,message }) => {
                                        if (success) {
                                            dispatch(push(`${mainModule}PersonPage/${this.props.saveNodes.node.workId}/${obj.values.status}/${obj.values.nodeName}/${source}`));
                                        } else {
                                        }
                                    });
                                    
                                }
                            }
                        ]}
                />
            </div>
        </div>
        )
    }
}
export default Form.create()(Index)