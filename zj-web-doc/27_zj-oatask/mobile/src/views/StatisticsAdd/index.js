import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import { Form } from 'antd';
import { NavBar, Icon } from 'antd-mobile';
import { goBack } from 'connected-react-router';
import s from "./index.less";
const config = {
    fetchConfig: {
        apiName: 'getLoginUserInfo',
    },
    formConfig: [
        {
            type: 'string',
            label: '任务类型',
            field: 'taskType', //唯一的字段名 ***必传
            initialValue:'1',
            hide:true,
        },
        {
            type: 'select',
            label: '任务来源',
            field: 'taskSourceId', //唯一的字段名 ***必传
            placeholder: '请选择',
            required:true,
            optionData: [//默认选项数据//可为function (props)=>array
                {
                    name: '年度重点工作',
                    id: '0'
                },
                {
                    name: '日常性工作',
                    id: '2'
                },
                {
                    name: '其他',
                    id: '3'
                }
            ],
            optionConfig: {//下拉选项配置
                label: 'name', //默认 label
                value: 'id'
            },
        },
        {
            type: 'textarea',
            label: '任务事项',
            field: 'taskMatter', //唯一的字段名 ***必传
            required: true,
            placeholder: '请输入',
        },
        {
            type: 'date',
            label: '开始时间',
            field: 'startTime', //唯一的字段名 ***必传
            placeholder: '请选择',
            required: true,
        },
        {
            type: 'date',
            label: '结束时间',
            field: 'endTime', //唯一的字段名 ***必传
            placeholder: '请选择',
            required: true,
        },
        {
            type: 'textarea',
            label: '内容摘要',
            field: 'content', //唯一的字段名 ***必传
            required: true,
            placeholder: '请输入',
        },
        {
            type: "string",
            label: "姓名",
            field: "auditorName",
            hide:true,
            initialValue:function(obj){
                var userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo; 
                return userInfo.realName; 
            }
        },
        {
            type: "string",
            label: "姓名Id",
            field: "auditorId",
            hide:true,
            initialValue:function(obj){
                var userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo; 
                return userInfo.userId; 
            }
        },
        {
            label: '承办部门',
            field: 'oaDeptList',
            type: 'treeSelect', 
            initialValue:function(obj){
                var userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo;   
                var departmentList = userInfo.departmentList.map(item=>{
                    return {
                        label:item.departmentName,
                        value:item.departmentId,
                        type:"1",
                    }
                });
                // var curCompanyList = userInfo.companyList.filter(item=>item.companySelectFlag === 1);
                if(departmentList && departmentList.length){
                    return departmentList
                }
                return []; 
            },
            treeSelectOption: {
                selectType: "1",
                maxNumber: 1,
                fetchConfig: {//配置后将会去请求下拉选项数据
                    apiName: "getSysDepartmentUserAllTree"
                },
                search: true,
                searchPlaceholder: '部门',
                searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
            }
        },
        {
            label:'负责人',
            field:'oaLeaderList',
            type: 'treeSelect',
            initialValue:[],
            required: true,
            treeSelectOption:{
                selectType:"2",
                maxNumber:1,
                fetchConfig: {
                    apiName: 'getSysDepartmentUserAllTree',
                },
                search:true,
                searchPlaceholder:'姓名、账号、电话',
                searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                searchParamsKey:'search',//搜索文字的K 默认是'searchText'   [string]
                searchOtherParams:{pageSize:999}//搜索时的其他参数  [object]
            }
        },
        {
            label:'审核人',
            field:'oaAuditorList',
            type: 'treeSelect',
            initialValue:function(obj){
                var userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo;
                let oaAuditorList = [{label:userInfo.realName,value:userInfo.userId,type:"2"}];
                return oaAuditorList;
            },
            disabled: true,
            treeSelectOption:{
                selectType:"2",
                maxNumber:1,
                fetchConfig: {
                    apiName: 'getSysDepartmentUserAllTree',
                },
                search:true,
                searchPlaceholder:'姓名、账号、电话',
                searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                searchParamsKey:'search',//搜索文字的K 默认是'searchText'   [string]
                searchOtherParams:{pageSize:999}//搜索时的其他参数  [object]
            }
        },
        {
            type: "files",
            label: "附件",
            field: "fileList",
            desc: "点击上传", 
            fetchConfig: {
                apiName: window.configs.domain + "upload"
            }
        },
    ]
}

class idnex extends Component {
    render() {
        const {
            dispatch
        } = this.props;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(goBack());
                    }}
                >{"任务分派"}</NavBar>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}>
                    <QnnForm
                        {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                        fetch={this.props.myFetch} //必须返回一个promise
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        {...config}
                        btns={[
                            {
                                type: 'dashed',
                                label: '取消',
                                isValidate: false,
                                onClick: (obj) => {
                                    dispatch(goBack());
                                },
                            },
                            {
                                label: '提交',
                                type: 'primary', //primary dashed danger
                                fetchConfig: {
                                    apiName: 'addZjOataskForWeChat',
                                },
                                affirmDesc: '确认提交数据吗？',//有这文字会点击按钮验证通过时将自动弹出提示
                                affirmYes: '确定',// 确认窗的确定按钮文字 默认确定
                                affirmNo: '取消',//确认窗的取消按钮文字  默认取消 
                                isValidate: false,   
                                onClick: (obj) => {
                                    if(obj.response.success){
                                        dispatch(goBack());
                                    }
                                },  
                            }
                        ]}
                    />
                </div>
            </div>
        )
    }
}
export default Form.create()(idnex);