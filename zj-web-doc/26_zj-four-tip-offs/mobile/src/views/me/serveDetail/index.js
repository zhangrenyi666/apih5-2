import React, { Component } from 'react';
import QnnForm from '../../modules/qnn-table/qnn-form';
import { Form, Modal, message as Msg, Divider, Empty } from 'antd';
import s from './style.less';
import { NavBar, Icon } from 'antd-mobile';
import moment from 'moment';
import $ from 'jquery';
const confirm = Modal.confirm;

class idnex extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(this.props.match.params.page) || 0,
            data: [],
            flag: true,
            visible: false,
            file: null
        }
    }
    handleCancel = () => {
        $("meta[name='viewport']").attr('content', "width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no");
        this.setState({ visible: false, file: null });
    }
    componentDidMount() {
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        })
    }
    render() {
        const { data, visible, file } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <div className={s.root} style={{ display: visible ? "none" : 'block' }}>
                    <div className={s.top}>
                        <NavBar
                            style={{ width: "100%" }}
                            mode="dark"
                            icon={<Icon type="left" />}
                            onLeftClick={() => {
                                window.history.back();
                            }}
                        >{"合作、共享、服务、交流详情"}</NavBar>
                    </div>
                    <div className={s.center}>
                        <QnnForm
                            {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                            form={this.props.form}
                            fetch={this.props.myFetch} //必须返回一个promise
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            fetchConfig={{
                                apiName: 'getZjProblemFeedbackDetails',//可为function 返回必须为string
                                params: {//从表单中取字段  此时可以取默认值或者网址上的值
                                    feedbackId: 'feedbackId',
                                },
                            }}
                            formConfig={[
                                {
                                    type: "string",
                                    label: "主键ID",
                                    field: "feedbackId", //唯一的字段名 ***必传
                                    isUrlParams: true,
                                    hide: true
                                },
                                {//普通选择框
                                    type: 'string',
                                    label: '所属项目',
                                    field: 'itemId', //唯一的字段名 ***必传
                                    placeholder: '无',
                                    disabled: true,
                                },
                                {//普通选择框
                                    type: 'select',
                                    label: '交流类型',
                                    field: 'serverId', //唯一的字段名 ***必传
                                    placeholder: '无',
                                    disabled: true,
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//最终的值使用逗号连接 默认值使用valueName 默认['value']
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: 'getBaseCodeSelect',
                                        otherParams: { itemId: 'fuwuleixing' }
                                    }
                                },
                                // {
                                //     type: "string",
                                //     label: "标题",
                                //     field: "feedbackTitle", //唯一的字段名 ***必传
                                //     disabled: true,
                                //     placeholder: "无", //占位符
                                // },
                                {
                                    type: "textarea",
                                    label: "问题描述",
                                    field: "feedbackDescribe", //唯一的字段名 ***必传
                                    disabled: true,
                                    placeholder: "无", //占位符
                                },
                                {
                                    type: 'camera',
                                    label: '图片附件',
                                    field: 'attachment', //唯一的字段名 ***必传
                                    disabled: true,
                                    cameraConfig: {
                                        type: "images",
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: window.configs.domain + 'upload',
                                    },
                                },
                                {
                                    type: 'files',
                                    label: '其它附件',
                                    field: 'otherFiles', //唯一的字段名 ***必传
                                    initialValue: [],
                                    disabled: true,
                                    onPreview: (file, fileList) => {
                                        if (/(image)/ig.test(file.type)) {
                                            $("meta[name='viewport']").attr('content', "width=600, minimum-scale=0.4, maximum-scale=3.0");
                                        } else {
                                            $("meta[name='viewport']").attr('content', "width=device-width");
                                        }

                                        this.setState({
                                            file: file.mobileUrl
                                        }, () => {
                                            this.setState({ visible: true })
                                        })
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: window.configs.domain + 'upload',
                                    },
                                },
                                {
                                    type: 'Component',
                                    field: 'diy', //唯一的字段名 ***必传
                                    placeholder: '请输入',//占位符
                                    Component: obj => {
                                        if (this.state.flag) {
                                            this.props.myFetch('getZjProblemFeedbackDetails', { feedbackId: obj.match.params.feedbackId }).then(({ data, success, message }) => {
                                                if (success) {
                                                    this.setState({
                                                        data: data.feedbackRecoveryList,
                                                        flag: false
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                        }
                                        return (
                                            <div>
                                                {
                                                    data && data.length ? data.map((item, index) => {
                                                        return <div className={s.centers} key={index}>
                                                            <div className={s.tops}>
                                                                <div className={s.topl}>{item.createUserName}</div>
                                                                <div className={s.topr}>{moment(item.createTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                                                            </div>
                                                            <Divider style={{ margin: "8px 0px" }} />
                                                            <div className={s.tops}>
                                                                <div className={s.topl}>{item.content}</div>
                                                            </div>
                                                        </div>
                                                    }) : null
                                                }
                                            </div>
                                        );
                                    }
                                },
                            ]}
                        />
                    </div>
                </div>
                {/* 这里不能使用display */}
                {
                    visible ? <div>
                        <div className={s.goback} onClick={this.handleCancel}> 返回</div>
                        <iframe id="iv" src={file} className={s.rootIV}></iframe>
                    </div> : null
                }
            </div>
        )
    }
}
export default Form.create()(idnex)