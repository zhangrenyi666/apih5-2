import React, { Component } from 'react';
import QnnForm from '../../modules/qnn-table/qnn-form';
import { Form } from 'antd';
const config = {
    fetchConfig: {
        apiName: 'getZjMeetingDetailAddTokenForWechat',
        params: {
            reservationsId: 'reservationsId',
            loginMobile: 'loginMobile'
        }
    },
    formConfig: [
        {
            type: 'string',
            label: '会议室预定主键',
            field: 'reservationsId', //唯一的字段名 ***必传
            hide: true,
            isUrlParams: true,
        },
        {
            type: 'string',
            label: '点击显引',
            field: 'flag', //唯一的字段名 ***必传
            hide: true,
            isUrlParams: true,
        },
        {
            type: 'string',
            label: '点击显引',
            field: 'meetingType', //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: 'string',
            label: '参会人员主键',
            field: 'attendPersonnelId', //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: 'string',
            label: '会议名称',
            field: 'meetingRoomTitle', //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true,
        },
        {
            type: 'string',
            label: '会议时间',
            field: 'meetingTime', //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true,
        }, {
            type: 'string',
            label: '会议类型',
            field: 'meetingRoomTypeId', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
        }, {
            type: 'string',
            label: '会议地点',
            field: 'meetingRoomName', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
        }, {
            type: 'textarea',
            label: '会议内容',
            field: 'meetingContent', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
        },
        {
            type: 'Component',
            field: 'diy', //唯一的字段名 ***必传
            Component: (obj) => {
                if (obj.match.params.flag == 0) {
                    return <center style={{ color: '#1890ff', width: '100%',height:"6vh",lineHeight:"6vh" }} onClick={() => {
                        obj.props.form.setFieldsValue({ flag: '1' });
                        var push = obj.props.history.push;
                        push('1')
                    }}>展开更多 ↓</center>
                } else {
                    return <center style={{ color: '#1890ff', width: '100%',height:"6vh",lineHeight:"6vh" }} onClick={() => {
                        obj.props.form.setFieldsValue({ flag: '0' });
                        var push = obj.props.history.push;
                        push('0')
                    }}>收起更多 ↑</center>
                }
            }
        }, {
            type: 'string',
            label: '主持人',
            field: 'convenor', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        flag: '0',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ]
        }, {
            type: 'string',
            label: '联系人',
            field: 'contactsUser', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        flag: '0',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ]
        }, {
            type: 'string',
            label: '联系电话',
            field: 'contactsTel', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        flag: '0',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ]
        }, {
            type: 'textarea',
            label: '备注',
            field: 'remarks', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled: true,
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        flag: '0',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ]
        }, {
            type: 'string',
            label: '姓名',
            field: 'attendPersonnelUser', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled:true,
        }, {
            type: 'phone',
            label: '手机',
            field: 'telephone', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled:true,
        }, {
            type: 'string',
            label: '单位',
            field: 'unit', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled:true,
        }, {
            type: 'string',
            label: '部门',
            field: 'department', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            disabled:true,
        },
        {
            type: 'string',
            label: '参会人员主键',
            field: 'attendPersonnelId', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            hide: true,
        },{
            type: 'string',
            label: 'openId',
            field: 'openId', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            hide: true,
        }, {
            type: 'select',
            label: '是否参会',
            field: 'isAttendMeeting', //唯一的字段名 ***必传
            placeholder: '请选择',//占位符
            disabled:true,
            defaultValue: '0',
            optionData: [//默认选项数据
                {
                    name: '是',
                    isAttendMeeting: '0',
                },
                {
                    name: '否',
                    isAttendMeeting: '1',
                },
            ],
            optionConfig: {//下拉选项配置
                label: 'name', //默认 label
                value: 'isAttendMeeting',//最终的值使用逗号连接 默认值使用valueName 默认['value']
            },
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        meetingType: '1',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ],
        }, {
            type: 'textarea',
            label: '不参会原因',
            field: 'notAttendReason', //唯一的字段名 ***必传
            disabled:true,
            placeholder: '请输入',//占位符
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        isAttendMeeting: '0',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                },
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        meetingType: '1',
                    },
                    action: 'hide', //disabled,  show,  hide, function(){}
                }
            ],
            formItemLayout:{
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 24 }
                }
            },
        }
    ]
}

class idnex extends Component {
    constructor(props){
        super(props);
        this.state = {
            page:props.match.params.page === '0' ? 0 : 1
        }
    }
    componentDidMount() {
        document.getElementsByTagName('title')[0].innerHTML = "回执详情";
    }
    componentWillUnmount() {
        document.getElementsByTagName('title')[0].innerHTML = "";
    }
    render() {
        const { page } = this.state;
        return (
            <div style={{width:'100%',height:'100%'}}>
                <QnnForm
                    myPublic={this.props.myPublic}
                    form={this.props.form} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                    fetch={this.props.myFetch} //必须返回一个promise
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                    {...config}
                    btns = {[
                        {
                            label: '返回',
                            isValidate: false,//是否验证表单 默认true
                            onClick: (obj) => {
                                let { replace } = obj.props.history;
                                let { mainModule } = obj.props.myPublic.appInfo;
                                replace(`${mainModule}Principlelist/${page}`)
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}
export default Form.create()(idnex)