import React, { Component } from 'react';
import QnnForm from '../../../modules/qnn-table/qnn-form';
import { Form, Modal } from 'antd';
import s from './style.less';
import { NavBar,Icon } from 'antd-mobile';
import { push } from 'react-router-redux';
const config = {
    fetchConfig:{
        apiName:'getZjReportDetails',//可为function 返回必须为string
        params:{//从表单中取字段  此时可以取默认值或者网址上的值
            reportId:'reportId',
        },
    },
}

class idnex extends Component {
    constructor(props){
        super(props);
        this.state = {
            page:Number(this.props.match.params.page) || 0,
            visible:false,
            file:''
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { page } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}RealNameList/${page}`));
                        }}
                    >{"举报详情"}</NavBar>
                </div>
                <div className={s.center}>
                    <QnnForm
                        {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                        fetch={this.props.myFetch} //必须返回一个promise
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        {...config}
                        formConfig = {[
                            {
                                type: "string",
                                label: "主键ID",
                                field: "reportId", //唯一的字段名 ***必传
                                isUrlParams: true,
                                hide:true
                            },
                            {//普通选择框
                                type: 'select',
                                label: '所属项目',
                                field: 'itemId', //唯一的字段名 ***必传
                                placeholder: '无',
                                disabled: true,
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: ['itemId'],//最终的值使用逗号连接 默认值使用valueName 默认['value']
                                },
                                fetchConfig: {//配置后将会去请求下拉选项数据
                                    apiName: 'getZjProblemLevelList',
                                }
                            },
                            {
                                type: "string",
                                label: "地理位置",
                                field: "problemLocal", //唯一的字段名 ***必传
                                disabled:true,
                                placeholder: "无", //占位符
                            },
                            {
                                type: "string",
                                label: "标题",
                                field: "title", //唯一的字段名 ***必传
                                disabled:true,
                                placeholder: "无", //占位符
                            },
                            {
                                type: "textarea",
                                label: "问题描述",
                                field: "problemDescribe", //唯一的字段名 ***必传
                                disabled:true,
                                placeholder: "无", //占位符
                            },
                            {
                                type: 'files',
                                label: '问题附件',
                                field: 'problemAttachment', //唯一的字段名 ***必传
                                initialValue:[],
                                onPreview:(file) => {
                                    this.setState({
                                        file:file.mobileUrl
                                    },() => {
                                        this.setState({visible:true})
                                    })
                                },
                                disabled:true,
                                fetchConfig: {//配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + 'upload',
                                },
                            }
                        ]}
                    />
                </div>
                <div>
                    <Modal
                        width={'90%'}
                        style={{ paddingBottom: '0', top: '0',overflow:'hidden' }}
                        title="附件详情"
                        visible={this.state.visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', height: '85vh',overflow:'scroll' }}
                        centered={true}
                        wrapClassName={'modals'}
                    >
                        <iframe width='100%' height='100%' frameBorder={0} src={this.state.file}></iframe>
                    </Modal>
                </div>
            </div>
        )
    }
}
export default Form.create()(idnex)