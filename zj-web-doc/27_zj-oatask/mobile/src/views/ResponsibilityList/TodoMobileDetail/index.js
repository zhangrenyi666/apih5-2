import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { push } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjOataskDetail",
        params: {
            taskId: "taskId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "taskId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "select",
            label: "状态",
            field: "taskStateFlag", //唯一的字段名 ***必传
            optionData: [//默认选项数据//可为function (props)=>array
                {
                    name: '待办',
                    id: '1',
                },
                {
                    name: '未完成',
                    id: '2',
                },
                {
                    name: '延期审核中',
                    id: '3',
                },
                {
                    name: '完成审核中',
                    id: '4',
                },
                {
                    name: '已完成',
                    id: '5',
                },
                {
                    name: '未通过',
                    id: '6',
                }
            ],
            optionConfig: {//下拉选项配置
                label: 'name', //默认 label
                value: 'id',//最终的值使用逗号连接 默认值使用valueName 默认['value']
            },
            placeholder: "无",
            disabled: true
        },
        {
            type: "string",
            label: "任务事项",
            field: "taskMatter", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "string",
            label: "编号",
            field: "taskNumber", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "string",
            label: "承办部门",
            field: "undertakeDeptName", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "string",
            label: "负责人",
            field: "leaderName", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "textarea",
            label: "内容摘要",
            field: "content", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "date",
            label: "下发时间",
            field: "startTime", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "date",
            label: "完成期限",
            field: "endTime", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
    ],
    btns: [
        {
            label: '同意',
            type: 'primary', //primary dashed danger
            onClick:(obj) => {
                const { dispatch, myPublic: { appInfo: { mainModule } } } = obj.props;
                obj.values.operateFlag = '0';
                obj.btnfns.myFetch('updateZjOataskState', obj.values, function ({ data, success, message }) {
                    if (success) {
                        obj.btnfns.Msg.success(message);
                        dispatch(push(`${mainModule}ResponsibilityList/0`));
                    } else {
                        obj.btnfns.Msg.error(message);
                    }
                })
            },
            affirmDesc: '您确认同意吗？',
            affirmYes: '确定',
            affirmNo: '取消',
        },
        {
            label: '驳回',
            type: 'danger', //primary dashed danger
            onClick:(obj) => {
                const { dispatch, myPublic: { appInfo: { mainModule } } } = obj.props;
                obj.values.operateFlag = '1';
                obj.btnfns.myFetch('updateZjOataskState', obj.values, function ({ data, success, message }) {
                    if (success) {
                        obj.btnfns.Msg.success(message);
                        dispatch(push(`${mainModule}ResponsibilityList/0`));
                    } else {
                        obj.btnfns.Msg.error(message);
                    }
                })
            },
            affirmDesc: '您确认驳回吗？',
            affirmYes: '确定',
            affirmNo: '取消',
        }
    ]
};

class idnex extends Component {
    render() {
        const { dispatch, myPublic: { androidApi, appInfo: { mainModule } } } = this.props;
        return (
            <div style={{ height: "100vh" }}>
                <div
                    style={{
                        width: "100%",
                        height: "45px",
                        position: "fixed",
                        left: "0",
                        top: "0"
                    }}
                >
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            if (androidApi) {
                                androidApi.closeActivity()
                            } else {
                                dispatch(push(`${mainModule}ResponsibilityList/0`));
                            }
                        }}
                    >{"任务清单详情"}</NavBar>
                </div>
                <div style={{ height: '100%', paddingTop: "45px", overflow: 'hidden scroll' }}>
                    <QnnForm
                        {...this.props}
                        form={this.props.form} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                        fetch={this.props.myFetch} //必须返回一个promise
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        {...config}
                    />
                </div>
            </div>
        );
    }
}
export default Form.create()(idnex);
