import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { goBack } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjHwZyResourcePriceMaterialDetail",
        params: {
            materialPriceId: "materialPriceId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "materialPriceId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "string",
            label: "项目名称",
            field: "proName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "物资大类",
            field: "materialType", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "物资编号",
            field: "materialNumber", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "物资名称",
            field: "materialName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "规格型号",
            field: "sepcificationModel", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "计量单位",
            field: "measure", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "所在省份",
            field: "province", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "限价",
            field: "price", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
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
                    >{"物资限价详情"}</NavBar>
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
