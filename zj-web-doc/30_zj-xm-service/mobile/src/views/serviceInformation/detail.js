import React, { Component } from 'react';
// import { Form } from "../modules/work-flow";
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form,message as Msg } from 'antd';
import { goBack } from 'connected-react-router';

class Index extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            updateApiname:props.match.params.updateApiname,
            keyData:props.match.params.keyData
        };
    }
    componentDidMount(){
        
    }
    getConfig = (dispatch,mainModule) => {
        const {updateApiname,keyData} = this.state;
        // var loginAndLogoutInfo = this.props.loginAndLogoutInfo || {};
        // var curCompany = loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        
        let formConfig = [
            {
                label:'时间节点',
                field: 'timeNodeId',
                type: 'select',
                placeholder: '请选择',
                fetchConfig: {
                    apiName: "getBaseCodeSelect",
                    otherParams: {
                        itemId: 'shiJianJieDian'
                    }
                },
                optionConfig: {//下拉选项配置
                    label: 'itemName', //默认 label
                    value: 'itemId',//
                },
                required: true,
            },
            {
                label:'事项名称',
                field: 'itemNameId',
                type: 'select',
                placeholder: '请选择',
                fetchConfig: {
                    apiName: "getBaseCodeSelect",
                    otherParams: {
                        itemId: 'shiXiangMingCheng'
                    }
                },
                optionConfig: {//下拉选项配置
                    label: 'itemName', //默认 label
                    value: 'itemId',//
                },
                required: true,
            },
            {
                label: '使用范围',
                field: 'scopeOfUseList',
                type: 'treeSelect',
                required:true,
                treeSelectOption: {
                    selectType: "1",
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentUserAllTree'
                    }
                }
            },
            {
                label:'流程',
                field:'process',
                type: 'textarea',
                placeholder:'请输入',
                required:true
            },
            {
                label:'办理时限',
                field:'handleTimeLimit',
                type: 'string',
                placeholder:'请输入',
                required:true
            },
            {
                label:'流程是否已信息化',
                field:'informationalizationFlag',
                type: 'radio',
                optionData: [  //可为function (props)=>array
                    {
                        label: "否",
                        value: "0"
                    },
                    {
                        label: "是",
                        value: "1"
                    }
                ],
                required:true
            },
            {
                label: '主责部门',
                field: 'mainDepartmentList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "1",
                    maxNumber:1,
                    fetchConfig: {
                        apiName: 'getSysDepartmentUserAllTree'
                    }
                },
                required:true
            },
            {
                label: '责任人',
                field: 'responsiblePersonList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "2",
                    maxNumber:1,
                    fetchConfig: {
                        apiName: 'getSysDepartmentUserAllTree'
                    }
                },
                required:true
            },
            {
                type: 'camera',
                label: '制度关联',
                field: 'systemAttachment', 
                fetchConfig: {
                    apiName: window.configs.domain + 'upload'
                },
                fieldName:"camera", //一定要配置唯一标识
                cameraConfig:{
                    showName:true, //显示文件名字  默认false
                    type:"images",
                    accept: 'image/jpeg'
                },
                max: 2
            },
            {
                label: '附件',
                field: 'attachmentList',
                type: 'files',
                fetchConfig: {
                    apiName: window.configs.domain + 'upload',
                }
            }
        ];
        if(keyData === '项目成立'){
            formConfig.push({
                type: "string",
                label: "主键ID",
                field: "projectEstablishmentId", 
                hide: true
            })
        }else if(keyData === '项目运行'){
            formConfig.push({
                type: "string",
                label: "主键ID",
                field: "projectOperationId", 
                hide: true
            })
        }else if(keyData === '项目收尾'){
            formConfig.push({
                type: "string",
                label: "主键ID",
                field: "projectConclusionId", 
                hide: true
            })
        }else if(keyData === '综合事项'){
            formConfig.push({
                type: "string",
                label: "主键ID",
                field: "comprehensiveMattersId", 
                hide: true
            })
        }else {
            
        }
        return {
            formConfig: formConfig,
            btns:[
                {
                    name: 'submit',
                    type: 'primary',
                    label: '提交',
                    onClick:(obj) =>{
                        console.log(obj);
                        this.props.myFetch(updateApiname,obj.values).then(
                            ({ success,data,message }) => {
                                if (success) {
                                    obj.btnCallbackFn.msg.success(message);
                                    dispatch(push(`${mainModule}serviceInformation`));
                                }else {
                                    obj.btnCallbackFn.msg.error(message);
                                }
                            }
                        );
                        
                    }
                }
            ]
        }
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
            <div>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(goBack());
                    }}
                >
                    {"详情"}
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
                    data={this.props.saveNodes.node}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    
                    {...this.getConfig(dispatch,mainModule)}
                />
            </div>
        </div>
        )
    }
}
const WrappedDynamicRule = Form.create({
    onValuesChange: (props,changedValues,allValues) => {
       
    }
})(Index);
export default WrappedDynamicRule