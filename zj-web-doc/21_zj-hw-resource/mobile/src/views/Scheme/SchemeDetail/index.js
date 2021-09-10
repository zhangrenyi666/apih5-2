import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { goBack } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjHwZyResourceSchemeDetail",
        params: {
            schemeId: "schemeId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "schemeId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "select",
            label: "类别",
            field: "schemeClass", //唯一的字段名 ***必传
            optionData: [//默认选项数据//可为function (props)=>array
                {
                    name: '优秀QC成果库',
                    id: '0',
                },
                {
                    name: '工序工艺标准化库',
                    id: '1',
                },
                {
                    name: '培训库',
                    id: '2',
                },
                {
                    name: '论文库',
                    id: '3',
                },
                {
                    name: '方案库',
                    id: '4',
                },
                {
                    name: '专家库',
                    id: '5',
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
            label: "管理部门",
            field: "schemeDepName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },        
        {
            type: "string",
            label: "名称",
            field: "schemeName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "关键词",
            field: "pointExplain", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: 'files',
            label: '附件',
            field: 'schemeFileList', //唯一的字段名 ***必传
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
                    >{"方案详情"}</NavBar>
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
