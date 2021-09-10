import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { NavBar, DatePicker } from 'antd-mobile';
import { Form, message as Msg, Input, Modal } from 'antd'
import s from './style.less';
import zhCN from 'antd-mobile/lib/date-picker/locale/zh_CN';
const { TextArea } = Input;
const confirm = Modal.confirm;
const config = {
    formConfig: [
        {
            type: "string",
            label: "主键Id",
            field: "incomeId", //唯一的字段名 ***必传
            placeholder: '无',
            hide: true
        },
        {
            type: "string",
            label: "年",
            field: "year", //唯一的字段名 ***必传
            placeholder: '无',
            isUrlParams: true,
            hide: true
        },
        {
            type: "string",
            label: "年Id",
            field: "yearId", //唯一的字段名 ***必传
            placeholder: '无',
            isUrlParams: true,
            hide: true
        },
        {
            type: "string",
            label: "姓名",
            field: "userName", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "number",
            label: "一月",
            field: "one", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "二月",
            field: "two", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "三月",
            field: "three", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "四月",
            field: "four", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "五月",
            field: "five", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "六月",
            field: "six", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "七月",
            field: "seven", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "八月",
            field: "eight", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "九月",
            field: "nine", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "十月",
            field: "ten", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "十一月",
            field: "eleven", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "十二月",
            field: "twelve", //唯一的字段名 ***必传
            placeholder: 0,
            span: 12,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                },
                wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 24 }
                }
            },
            disabled: true
        },
        {
            type: "number",
            label: "当年发放绩效薪酬合计",
            field: "performTotal", //唯一的字段名 ***必传
            placeholder: 0,
            disabled: true
        },
        {
            type: "number",
            label: "各类津贴及奖励合计",
            field: "awardTotal", //唯一的字段名 ***必传
            placeholder: 0,
            disabled: true
        },
        {
            type: "number",
            label: "当年其他单位发放合计",
            field: "otherTotal", //唯一的字段名 ***必传
            placeholder: 0,
            disabled: true
        },
        {
            type: "number",
            label: "年收入总计数",
            field: "yearToatl", //唯一的字段名 ***必传
            placeholder: 0,
            disabled: true
        },
        {
            type: "number",
            label: "本年申报基数",
            field: "declareBase", //唯一的字段名 ***必传
            placeholder: 0,
            disabled: true
        },
    ],
};
class PayrollList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeActionText: props.match.params.year === '0' ? new Date().getFullYear() - 1 : props.match.params.year,
            yearId: props.match.params.yearId === '0' ? '' : props.match.params.yearId,
            value: '',
            readTime:''
        };
    }
    componentDidMount() {
        if (!this.state.yearId) {
            this.refresh();
        }
    }
    refresh() {
        const { myFetch } = this.props;
        myFetch('getZjGzdYearList', { year: this.state.changeActionText }).then(({ data, success, message }) => {
            if (success) {
                if (data.length) {
                    this.setState({
                        yearId: data[0].yearId,
                    })
                }
            } else {
                Msg.error(message)
            }
        });
    }
    render() {
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        mode="dark"
                    >{"工资单详情"}</NavBar>
                    <DatePicker
                        mode="year"
                        title="请选择"
                        extra="Optional"
                        locale={zhCN}
                        minDate={new Date(2018, 1, 1, 0, 0, 0)}
                        maxDate={new Date()}
                        onChange={date =>
                            this.setState({
                                changeActionText: new Date(date).getFullYear(),
                                yearId: ''
                            }, () => {
                                this.refresh();
                            })
                        }
                    >
                        <div className={s.header}>
                            <NavBar mode="light">
                                <span style={{ color: "#108ee9" }}>
                                    {this.state.changeActionText}年
                            </span>
                            </NavBar>
                        </div>
                    </DatePicker>
                </div>
                <div style={{ height: '100%', paddingTop: "100px", overflow: 'hidden scroll' }}>
                    <QnnForm
                        {...this.props}
                        myPublic={this.props.myPublic}
                        form={this.props.form} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                        fetch={this.props.myFetch} 		 upload={this.props.myUpload} //必须返回一个promise
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        {...config}
                        // data={this.state.data}
                        fetchConfig={{
                            apiName: "getZjGzdAnnualIncomeDetail",
                            otherParams: {
                                userId: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId,
                                yearId: this.state.yearId
                            },
                            success:(success,data) => {
                                if(success){
                                    this.setState({
                                        readTime:data.readTime
                                    })
                                }
                            }
                        }}
                        btns={this.state.readTime ? [] : [
                            {
                                label: '同意',
                                type: 'primary',
                                onClick: (obj) => {
                                    const { myPublic: { wx } } = obj.props;
                                    obj.btnfns.myFetch('zjGzdAnnualIncomeOperate', obj.values, function ({ data, success, message }) {
                                        if (success) {
                                            obj.btnfns.Msg.success(message);
                                            wx.closeWindow();
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
                                label: '不同意',
                                type: 'primary',
                                onClick: (obj) => {
                                    this.setState({ value: '' });
                                    confirm({
                                        title: `您确定不同意吗?`,
                                        content: <TextArea name='fileSaveContent' placeholder="请输入意见" onChange={(event) => {
                                            this.setState({ value: event.target.value });
                                        }} />,
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            const { myPublic: { wx } } = obj.props;
                                            obj.values.opinion = this.state.value;
                                            obj.btnfns.myFetch('zjGzdAnnualIncomeOperate', obj.values, function ({ data, success, message }) {
                                                if (success) {
                                                    obj.btnfns.Msg.success(message);
                                                    wx.closeWindow();
                                                } else {
                                                    obj.btnfns.Msg.error(message);
                                                }
                                            })
                                        },
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
export default Form.create()(PayrollList);
