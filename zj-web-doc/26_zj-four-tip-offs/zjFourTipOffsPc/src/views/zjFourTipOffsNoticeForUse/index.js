import React, { Component } from "react";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Tabs } from 'antd';
const { TabPane } = Tabs;
//普通页面配置
const config = {
    fetchConfig: {
        apiName: "pcGetZjUseNoticeDetails",
        otherParams: { type: '0' },
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 2 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 10 }
        }
    },
    formConfig: [
        {
            type: "string",
            label: "主键",
            field: "id", //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: "string",
            label: "类型",
            field: "type", //唯一的字段名 ***必传
            hide: true,
            initialValue: '0'
        },
        {
            type: "string",
            label: "标题",
            field: "title", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "须知描述",
            field: "problemDescribe", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "备注",
            field: "remarks", //唯一的字段名 ***必传
            placeholder: "请输入", //占位符
        },
    ],

    btns: [
        {
            label: "保存",
            field:'submit',
            type: "primary",
            onClick: function (obj) { //此时里面会多一个response
                const { fetchByCb, Msg } = obj.btnfns;
                if (obj.values.id) {
                    fetchByCb('updateZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                } else {
                    fetchByCb('addZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                }
            },
        }
    ]
};
const config1 = {
    fetchConfig: {
        apiName: "pcGetZjUseNoticeDetails",
        otherParams: { type: '1' },
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 2 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 10 }
        }
    },
    formConfig: [
        {
            type: "string",
            label: "主键",
            field: "id", //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: "string",
            label: "类型",
            field: "type", //唯一的字段名 ***必传
            hide: true,
            initialValue: '1'
        },
        {
            type: "string",
            label: "标题",
            field: "title", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "须知描述",
            field: "problemDescribe", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "备注",
            field: "remarks", //唯一的字段名 ***必传
            placeholder: "请输入", //占位符
        },
    ],

    btns: [
        {
            label: "保存",
            field:'submit1',
            type: "primary",
            onClick: function (obj) { //此时里面会多一个response
                const { fetchByCb, Msg } = obj.btnfns;
                if (obj.values.id) {
                    fetchByCb('updateZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                } else {
                    fetchByCb('addZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                }
            },
        }
    ]
};
const config2 = {
    fetchConfig: {
        apiName: "pcGetZjUseNoticeDetails",
        otherParams: { type: '2' },
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 2 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 10 }
        }
    },
    formConfig: [
        {
            type: "string",
            label: "主键",
            field: "id", //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: "string",
            label: "类型",
            field: "type", //唯一的字段名 ***必传
            hide: true,
            initialValue: '2'
        },
        {
            type: "string",
            label: "标题",
            field: "title", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "须知描述",
            field: "problemDescribe", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "备注",
            field: "remarks", //唯一的字段名 ***必传
            placeholder: "请输入", //占位符
        },
    ],

    btns: [
        {
            label: "保存",
            type: "primary",
            field:'submit2',
            onClick: function (obj) { //此时里面会多一个response
                const { fetchByCb, Msg } = obj.btnfns;
                if (obj.values.id) {
                    fetchByCb('updateZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                } else {
                    fetchByCb('addZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                }
            },
        }
    ]
};
const config3 = {
    fetchConfig: {
        apiName: "pcGetZjUseNoticeDetails",
        otherParams: { type: '3' },
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 2 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 10 }
        }
    },
    formConfig: [
        {
            type: "string",
            label: "主键",
            field: "id", //唯一的字段名 ***必传
            hide: true,
        },
        {
            type: "string",
            label: "类型",
            field: "type", //唯一的字段名 ***必传
            hide: true,
            initialValue: '3'
        },
        {
            type: "string",
            label: "标题",
            field: "title", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "须知描述",
            field: "problemDescribe", //唯一的字段名 ***必传
            required: true,
            placeholder: "请输入", //占位符
        },
        {
            type: "textarea",
            label: "备注",
            field: "remarks", //唯一的字段名 ***必传
            placeholder: "请输入", //占位符
        },
    ],

    btns: [
        {
            label: "保存",
            field:'submit3',
            type: "primary",
            onClick: function (obj) { //此时里面会多一个response
                const { fetchByCb, Msg } = obj.btnfns;
                if (obj.values.id) {
                    fetchByCb('updateZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                } else {
                    fetchByCb('addZjUseNotice', obj.values, function ({ data, success, message }) {
                        if (success) {
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    })
                }
            },
        }
    ]
};

class idnex extends Component {
    state = {
        key: '1'
    }
    callback = (key) => {
        this.setState({ key });
    }
    render() {
        const { key } = this.state;
        return (
            <div style={{ height: "100%" }}>
                <Tabs activeKey={key} onChange={this.callback}>
                    <TabPane tab="技术质量" key="1">
                        <QnnForm
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            {...config}
                        />
                    </TabPane>
                    <TabPane tab="违规违纪" key="2">
                        <QnnForm
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            {...config1}
                        />
                    </TabPane>
                    <TabPane tab="安环隐患" key="3">
                        <QnnForm
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            {...config2}
                        />
                    </TabPane>
                    <TabPane tab="合作、共享、服务、交流" key="4">
                        <QnnForm
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            {...config3}
                        />
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}
export default idnex;
