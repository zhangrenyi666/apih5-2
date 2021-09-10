import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { goBack } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjHwZyResourceInventoryDetail",
        params: {
            inventoryId: "inventoryId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "inventoryId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "select",
            label: "类别",
            field: "inventoryClass", //唯一的字段名 ***必传
            optionData: [//默认选项数据//可为function (props)=>array
                {
                    name: '制度清单',
                    id: '0',
                },
                {
                    name: '流程清单',
                    id: '1',
                },
                {
                    name: '可调配清单',
                    id: '2',
                },
                {
                    name: '模板类清单',
                    id: '3',
                }
            ],
            optionConfig: {//下拉选项配置
                label: 'name', //默认 label
                value: ['id'],//最终的值使用逗号连接 默认值使用valueName 默认['value']
            },
            placeholder: "无",
            disabled: true
        },
        {
            type: "string",
            label: "名称",
            field: "inventoryName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "关键词",
            field: "inventoryKeyword", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "管理部门",
            field: "inventoryDepName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: 'files',
            label: '附件',
            field: 'inventoryFileList', //唯一的字段名 ***必传
            disabled: true,
            fetchConfig: {
                apiName: window.configs.domain + 'upload',
            },
        },
        {
            type: "textarea",
            label: "备注",
            field: "remarks", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
    ]
};

class idnex extends Component {
    render() {
        const { dispatch, myPublic: { androidApi } } = this.props;
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
                            if (androidApi && this.state.falg == 0) {
                                androidApi.closeActivity()
                            } else {
                                dispatch(goBack())
                            }
                        }}
                    >{"清单管理详情"}</NavBar>
                </div>
                <div style={{ height:'100%',marginTop: "45px",overflow:'hidden scroll' }}>
                    <QnnForm
                        myPublic={this.props.myPublic}
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
