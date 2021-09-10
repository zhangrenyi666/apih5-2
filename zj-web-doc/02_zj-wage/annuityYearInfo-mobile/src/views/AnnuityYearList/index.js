import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { NavBar, DatePicker } from 'antd-mobile';
import { message as Msg } from 'antd'
import s from './style.less';
import zhCN from 'antd-mobile/lib/date-picker/locale/zh_CN';
const config = {
    formConfig: [
        {
            type: "string",
            label: "姓名",
            field: "userName", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "string",
            label: "部门",
            field: "deptName", //唯一的字段名 ***必传
            placeholder: '无',
            disabled: true
        },
        {
            type: "files",
            label: "附件",
            field: "fileList",
            fetchConfig: {
                apiName: window.configs.domain + "upload"
            },
            disabled: true
        }
    ],
};
class AnnuityYearList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeActionText: props.match.params.year === '0' ? new Date().getFullYear() - 1 : props.match.params.year,
            yearId: props.match.params.yearId === '0' ? '' : props.match.params.yearId,
            value: ''
        };
    }
    componentDidMount() {
        if (!this.state.yearId) {
            this.refresh();
        }
    }
    refresh() {
        const { myFetch } = this.props;
        myFetch('getZjAnnuityYearList', { year: this.state.changeActionText }).then(({ data, success, message }) => {
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
                    >{"年金年份详情"}</NavBar>
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
                        fetch={this.props.myFetch} 
		                upload={this.props.myUpload} //必须返回一个promise
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        {...config}
                        // data={this.state.data}
                        fetchConfig={{
                            apiName: "geteZjAnnuityPersonDetail",
                            otherParams: {
                                userId: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId,
                                yearId: this.state.yearId
                            }
                        }}
                    />
                </div>
            </div>
        )
    }
}
export default AnnuityYearList;
