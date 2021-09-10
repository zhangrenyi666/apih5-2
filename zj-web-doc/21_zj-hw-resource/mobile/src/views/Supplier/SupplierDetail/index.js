import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Form } from "antd";
import { NavBar, Icon } from "antd-mobile"; //, Icon
import { goBack } from 'react-router-redux';
const config = {
    fetchConfig: {
        apiName: "getZjHwZyResourceSupplierDetail",
        params: {
            supplierId: "supplierId",
        },
    },
    formConfig: [
        {
            type: "string",
            label: "主键ID",
            field: "supplierId", //唯一的字段名 ***必传
            hide: true, //是否隐藏 默认 false
            isUrlParams: true //是否是从地址参数中取值 默认false
        },
        {
            type: "select",
            label: "类别",
            field: "supperClass", //唯一的字段名 ***必传
            optionData: [//默认选项数据//可为function (props)=>array
                {
                    name: '分包供应商',
                    id: '0',
                },
                {
                    name: '物资供应商',
                    id: '1',
                },
                {
                    name: '机械租赁供应商',
                    id: '2',
                },
                {
                    name: '广告宣传供应商',
                    id: '3',
                },
                {
                    name: '信息供应商',
                    id: '4',
                },
                {
                    name: '安全供应商',
                    id: '5',
                },
                {
                    name: '其它供应商',
                    id: '6',
                },
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
            label: "资质编号",
            field: "resourceNumber", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "经营范围",
            field: "businessScope", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "管理部门",
            field: "supplierDepName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "经营地",
            field: "businessPlace", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "资质等级",
            field: "aptitudeLevel", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "注册地",
            field: "registeredAddress", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "单位名称",
            field: "unitName", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "信用代码",
            field: "unifiedSocialCreditCode", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "注册资金",
            field: "registeredFund", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "法定代表人",
            field: "legalRepresentative", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "身份证号",
            field: "idCard", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "联系人",
            field: "linkman", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "联系方式",
            field: "contactWay", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "date",
            label: "入库时间",
            field: "warehouseTime", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "使用项目",
            field: "useProject", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "推荐单位",
            field: "initRecommendationUnit", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "项目评价",
            field: "projectEvaluation", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "textarea",
            label: "公司评价",
            field: "corpEvaluation", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "合作业绩",
            field: "cooperationPerformance", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: "string",
            label: "业绩金额",
            field: "cooperativePerformanceAmount", //唯一的字段名 ***必传
            placeholder:'无',
            disabled: true
        },
        {
            type: 'files',
            label: '附件',
            field: 'supplierFileList', //唯一的字段名 ***必传
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
                    >{"供应商详情"}</NavBar>
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
