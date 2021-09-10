import React, { Component } from 'react';
import QnnForm from '../../modules/qnn-table/qnn-form';
import { Form } from 'antd';
import s from './style.less'
const config = {
    fetchConfig: {
        apiName: 'getZjMeetingDetailAddTokenForWechat',
        params: {
            reservationsId: 'reservationsId',
            loginMobile: "loginMobile"
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
            label: '参会人员主键',
            field: 'attendPersonnelId', //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: 'string',
            label: '会议名称',
            field: 'meetingRoomTitle', //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true,
        },
        {
            type: 'string',
            label: '会议时间',
            field: 'meetingTime', //唯一的字段名 ***必传
            placeholder: '无',
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
            field: 'aaa', //唯一的字段名 ***必传
            Component: (obj) => {
                if (obj.match.params.flag == 0) {
                    return <center style={{ color: '#1890ff', width: '100%', height: "6vh", lineHeight: "6vh" }} onClick={() => {
                        obj.props.form.setFieldsValue({ flag: '1' });
                        var push = obj.props.history.push;
                        push('1')
                    }}>展开更多 ↓</center>
                } else {
                    return <center style={{ color: '#1890ff', width: '100%', height: "6vh", lineHeight: "6vh" }} onClick={() => {
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
            placeholder: '请输入',//占位符
            required: true,
        }, {
            type: 'phone',
            label: '手机',
            field: 'telephone', //唯一的字段名 ***必传
            placeholder: '请输入',//占位符
            required: true,
        }, {
            type: 'string',
            label: '单位',
            field: 'unit', //唯一的字段名 ***必传
            required:true,
            placeholder: '请输入',//占位符
        }, {
            type: 'string',
            label: '部门',
            field: 'department', //唯一的字段名 ***必传
            placeholder: '请输入',//占位符
            required: true,
        }, {
            type: 'string',
            label: '职务',
            field: 'job', //唯一的字段名 ***必传
            placeholder: '请输入',//占位符
        }, {
            type: 'string',
            label: 'openId',
            field: 'openId', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            hide: true,
        },
        {
            type: 'string',
            label: '参会人员主键',
            field: 'attendPersonnelId', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            hide: true,
        }, {
            type: 'string',
            label: '参签名',
            field: 'base64Image', //唯一的字段名 ***必传
            placeholder: '无',//占位符
            hide: true,
        }
    ]
}

class idnex extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: props.match.params.page === '0' ? 0 : 1,
            drawState: '0',
        }
    }
    componentDidMount() {
        document.getElementsByTagName('title')[0].innerHTML = "会议签到";
        document.querySelector('#noScroll').addEventListener('touchmove', function (evt) {
            if (!evt._isScroller) {
                evt.preventDefault();
            }
        });
        //获取像素比
        const bp = window.devicePixelRatio || 1;
        this.bp = bp;
        //单位转换
        this.unit = (num = 0) => num * bp;

        //写画布
        const canvasConta = document.querySelector('.canvas');
        const canvas = document.querySelector('canvas');
        const canvasW = canvas.offsetWidth;
        const canvasH = canvas.offsetHeight;
        const canvasOffsettop = canvasConta.offsetTop;
        const canvasOffsetLeft = canvasConta.offsetLeft;
        this.canvasOffsettop = canvasOffsettop;
        this.canvasOffsetLeft = canvasOffsetLeft;
        //绑定给this
        this.canvas = canvas;

        //设置画布大小
        canvas.width = canvasW * bp;
        canvas.height = canvasH * bp;

        this.canvasWidth = canvasW * bp;
        this.canvasHeight = canvasH * bp;

        this.ctx = canvas.getContext('2d');
        this.ctx.lineWidth = this.unit(1);
        this.ctx.lineJoin = 'round';

        canvas.addEventListener('touchstart', this.touchstart, false);
        canvas.addEventListener('touchmove', this.touchmove, false);
        canvas.addEventListener('touchend', this.touchend, false);
    }
    componentWillUnmount() {
        document.getElementsByTagName('title')[0].innerHTML = "";
    }
    touchstart = (e) => {
        let { clientX, clientY } = e.touches[0];
        this.ctx.beginPath();
        this.ctx.strokeStyle = '#000000';
        this.ctx.lineWidth = this.unit(1);
        //正常画
        this.ctx.moveTo(this.unit(clientX) - this.unit(this.canvasOffsetLeft), this.unit(clientY) - this.unit(this.canvasOffsettop));
    }

    touchmove = (e) => {
        let { clientX, clientY } = e.changedTouches[0];
        this.ctx.lineTo(this.unit(clientX) - this.unit(this.canvasOffsetLeft), this.unit(clientY) - this.unit(this.canvasOffsettop));
        this.ctx.stroke();
    }

    touchend = () => {
        this.ctx.closePath();
        let base64Image = this.canvas.toDataURL();
        this.props.form.setFieldsValue({ base64Image });
    }
    change = (e, flag) => {// 0笔
        e.nativeEvent.stopImmediatePropagation();
        if (flag === '2') {//清空
            this.setState({
                drawState: '0',//变成默认的签字笔
            })
            this.ctx.clearRect(0, 0, this.unit(this.canvasWidth), this.unit(this.canvasHeight))
        } else {
            this.setState({
                drawState: flag
            })
        }
    }
    render() {
        let { drawState, page } = this.state;
        return (
            <div style={{ overflow: 'scroll', width: '100%', height: '100vh' }}>
                <div id='noScroll' className={s.sign}>
                    <div className={s.container}>
                        <div className={s.tit}>请签字</div>
                        <div className={`${s.canvas} canvas`}>
                            <div className={s.tools}>
                                <div onClick={(e) => { this.change(e, '0') }} style={{ color: drawState === '0' ? '#108ee9' : '' }}>签字笔</div>
                                <div onClick={(e) => { this.change(e, '2') }}>清空</div>
                            </div>
                            <canvas></canvas>
                        </div>
                    </div>
                </div>
                <QnnForm
                    style={{ overflowY: 'visible', height: 'auto' }}
                    myPublic={this.props.myPublic}
                    form={this.props.form} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                    fetch={this.props.myFetch} //必须返回一个promise
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                    {...config}
                    btns={[
                        /*
                        {
                            label: '返回',
                            isValidate: false,//是否验证表单 默认true
                            onClick: (obj) => {
                                var replace = obj.props.history.replace;
                                replace(`/xmMeetingRoomMobile/Principles`)
                            }
                        },
                        */
                        {
                            label: '签到',
                            type: 'primary',
                            fetchConfig: {
                                apiName: 'zjInsideMeetingSingInForWechat',
                            },
                            condition: [
                                {//条件
                                    regex: {
                                        meetingTime: '',
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        meetingTime: undefined,
                                    },
                                    action: 'hide',
                                },
                                {//条件
                                    regex: {
                                        meetingTime: null,
                                    },
                                    action: 'hide',
                                }
                            ],
                            onClick: (obj) => {
                                if (obj.response.success == true) {
                                    obj.btnfns.Msg.success(obj.response.message);
                                    let { replace } = obj.props.history;
                                    let { mainModule } = obj.props.myPublic.appInfo;
                                    replace(`${mainModule}Principlelist/${page}`)
                                }
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}
export default Form.create()(idnex)