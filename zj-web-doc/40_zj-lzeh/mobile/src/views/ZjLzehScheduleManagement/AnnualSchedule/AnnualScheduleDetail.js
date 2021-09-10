import React, { Component } from "react";
import QnnForm from "../../modules/qnn-form";
import { NavBar, Icon } from "antd-mobile"
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
class index extends Component {
    componentDidMount() {
        window.oldTitle = document.title;
        document.title = "年度计划进度详情";
    }
    componentWillUnmount() {
        document.title = window.oldTitle;
    }
    render() {
        return (
            <div style={{ height: '100vh' }}>
                <div >
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            this.props.history.goBack()
                        }}
                    >
                        {"年度计划进度详情"}
                    </NavBar>
                </div>
                <QnnForm
                    style={{ paddingRight: "10px" }}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo }}
                    fetchConfig={() => {
                        return {
                            apiName: 'getZjLzehYearPlanProgressDetail',
                            otherParams: { zjLzehYearPlanProgressId: this.props.match.params.zjLzehYearPlanProgressId }
                        }
                    }}
                    formConfig={[
                        {
                            label: '年度',
                            field: "showYear",
                            type: 'string',
                            disabled: true,
                        },
                        {
                            label: '计划年产值(万元)',
                            field: "planYearOutValue",
                            type: 'number',
                            disabled: true,
                        },
                        {
                            label: '实际年产值(万元)',
                            field: "yearOutValue",
                            type: 'number',
                            disabled: true,
                        },
                        {
                            label: '任务完成率(%)',
                            field: "completeRate",
                            type: 'number',
                            disabled: true,
                        },
                        {
                            field: "diyText",
                            type: 'Component',
                            Component: obj => {
                                return <div style={{ padding: '10px 6% 0px 10px', fontWeight: 700 }}>备注：</div>
                            },
                            span: 24,
                        },
                        {
                            field: "remarks",
                            type: "textarea",
                            disabled: true,
                        },
                        {
                            field: "diyText",
                            type: 'Component',
                            Component: obj => {
                                return <div style={{ padding: '10px 6% 0px 10px', fontWeight: 700 }}>附件：</div>
                            },
                            span: 24,
                        },
                        {
                            field: 'fileList',
                            type: 'files',
                            disabled: true,
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                        },
                    ].map(item => {
                        return {
                            ...item,
                            labelStyle: { fontWeight:700,textAlign: "left"},
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 17 },
                                    sm: { span: 17 }
                                }
                            },
                        }
                    })}
                />
            </div>
        );
    }
}
export default Form.create()(index);
