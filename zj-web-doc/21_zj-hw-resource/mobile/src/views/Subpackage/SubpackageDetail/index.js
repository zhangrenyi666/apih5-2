import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { goBack } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjHwZyResourcePriceSubpackageDetailDetail",
        params: {
            subpackageDetailId: "subpackageDetailId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "subpackageDetailId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "string",
            label: "编码",
            field: "code", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "标准工序名称",
            field: "standardProcessName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "单位",
            field: "unit", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "施工内容",
            field: "constructionContent", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "计价规则",
            field: "valuationRules", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "平原",
            field: "plain", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        }, 
		{
            type: "textarea",
            label: "山岭",
            field: "ridge", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
		{
            type: "textarea",
            label: "等级路",
            field: "levelRoad", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
		{
            type: "textarea",
            label: "改建路",
            field: "rebuildRoad", //唯一的字段名 ***必传
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
                    >{"分包限价详情"}</NavBar>
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
