import { basic } from '../modules/layouts';
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-form";
// import FlowFormByYY from "./form";
import FlowByOne from "./flowByOne";
import FlowByTwo from "./flowByTwo";
import FlowByThree from "./flowByThree";
import FlowByFour from "./flowByFour";
import detailByFlowOne from "./detailByFlowOne";
import detailByFlowTwo from "./detailByFlowTwo";
import detailByFlowThree from "./detailByFlowThree";
import { Modal } from 'antd';
const { confirm } = Modal;

class index extends Component {
    constructor() {
        super();
        this.state = {
            flag: true,
            dStatus: null, //add | detail
        }
    }
    launch = () => {
        return {
            //流程专属配置
            workFlowConfig: {
                //后台定的字段
                title: ["一级方案流程发起"], //标题字段
                apiNameByAdd: "addZjPrProgrammeLaunchFlow",
                apiNameByUpdate: "updateZjPrProgrammeLaunchFlow",
                apiNameByGet: "getZjPrProgrammeLaunchFlowDetailByWorkId",
                flowId: "zjYongYin",
                //移动端需要用到
                formLink: {
                    zjPartyFeeUse: "FlowByDFAwait",
                    zjYongYin: "FlowByYYAwait",
                },

                //移动端需要用到
                //待办已办切换路由
                todo: "FlowByYYAwait",
                hasTodo: "FlowByYYOver"
            },
            fetchConfig: {//表格的ajax配置
                apiName: 'getZjPrProgrammeLaunchListNew',
            },
            antd: { //同步antd table组件配置 ***必传
                rowKey: function (row) {// ***必传
                    return row.launchId
                },
                size: 'small'
            },
            drawerConfig: {
                width: '80%'
            },
            paginationConfig: {// 同步antd的分页组件配置   
                position: 'bottom'
            },
            // actionBtnsPosition: "bottom",
            actionBtns: [
                {
                    name: 'add',//内置add del
                    icon: 'plus',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '方案审批新增',
                    onClick: () => {
                        this.setState({
                            flag: true,
                            dStatus: 'add'
                        })
                    },
                    // Component: MerchantsAdd,
                    formBtns: [
                        {
                            name: 'cancel', //关闭右边抽屉
                            type: 'dashed',//类型  默认 primary
                            label: '取消',
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                        },
                        {
                            name: 'diyJB',//内置add del
                            type: 'primary',//类型  默认 primary
                            label: '提交',//提交数据并且关闭右边抽屉
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            onClick: function (obj) { //此时里面会多一个response
                                const { fetch, msg } = obj.btnCallbackFn;
                                fetch('addZjPrProgrammeLaunchNew', { ...obj._formData }, function ({ data, success, message }) {
                                    if (success) {
                                        var launchId = {
                                            launchId: data.launchId
                                        };
                                        obj.props.form.setFieldsValue(launchId);
                                        msg.success(message)
                                        obj.btnCallbackFn.refresh();
                                        obj.btnCallbackFn.closeDrawer(false);
                                    } else {
                                        msg.error(message);
                                    }
                                })
                            }
                        },
                    ]
                },
                {
                    name: 'add',//内置add del
                    hide: true,
                    icon: 'plus',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '四级方案审批新增',
                    // Component: MerchantsAdd,
                    onClick: () => {
                        this.setState({
                            flag: false
                        })
                    },
                    formBtns: [
                        {
                            name: 'cancel', //关闭右边抽屉
                            type: 'dashed',//类型  默认 primary
                            label: '取消',
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                        },
                        {
                            name: 'diyJB',//内置add del
                            type: 'primary',//类型  默认 primary
                            label: '提交',//提交数据并且关闭右边抽屉
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            onClick: function (obj) { //此时里面会多一个response
                                const { fetch, msg } = obj.btnCallbackFn;
                                fetch('addZjPrProgrammeLaunchByIVNew', { ...obj._formData }, function ({ data, success, message }) {
                                    if (success) {
                                        var launchId = {
                                            launchId: data.launchId
                                        };
                                        obj.props.form.setFieldsValue(launchId);
                                        msg.success(message)
                                        obj.btnCallbackFn.refresh();
                                        obj.btnCallbackFn.closeDrawer(false);
                                    } else {
                                        msg.error(message);
                                    }
                                })
                            }
                        },
                    ]
                },
                {
                    label: "发起流程",
                    drawerTitle: "发起流程", //点击后的抽屉标题
                    name: "Component",
                    icon: "plus",
                    type: "primary",
                    Component: function (obj) {
                        //删除掉不需要的字段（每个流程都需要改的地）
                        var delAttr = ["id"];
                        //判断是否选中数据
                        var selectedRows = obj.selectedRows;
                        if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                            obj.btnCallbackFn.closeDrawer();
                            obj.btnCallbackFn.msg.error('请选择一条数据！');
                            return <div />
                        }
                        if (obj.selectedRows[0].reviewState != "") {
                            obj.btnCallbackFn.closeDrawer();
                            obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                            return <div />
                        } else if (obj.selectedRows[0].schemeLevel == "2") {
                            //打开流程form
                            return <FlowByOne
                                wrappedComponentRef={(me) => {
                                    if (me) {
                                        //加上apiBody.
                                        let flowData = {};
                                        for (const key in selectedRows[0]) {
                                            if (selectedRows[0].hasOwnProperty(key)) {
                                                if (!delAttr.includes(key)) {
                                                    const element = selectedRows[0][key];
                                                    flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                                }
                                            }
                                        }
                                        setTimeout(function () {
                                            var vals = QnnForm.sFormatData({ ...flowData }, me.props.formConfig, 'set');
                                            me.props.form.setFieldsValue({
                                                ...vals
                                            })
                                        }, 1)
                                    }
                                }}
                                {...obj} tabs={[]} />
                        } else if (obj.selectedRows[0].schemeLevel == "1") {
                            //打开流程form
                            return <FlowByTwo
                                wrappedComponentRef={(me) => {
                                    if (me) {

                                        //加上apiBody.
                                        let flowData = {};
                                        for (const key in selectedRows[0]) {
                                            if (selectedRows[0].hasOwnProperty(key)) {
                                                if (!delAttr.includes(key)) {
                                                    const element = selectedRows[0][key];
                                                    flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                                }
                                            }
                                        }
                                        setTimeout(function () {
                                            var vals = QnnForm.sFormatData({ ...flowData }, me.props.formConfig, 'set');
                                            me.props.form.setFieldsValue({
                                                ...vals
                                            })
                                        }, 1)
                                    }
                                }}
                                {...obj} tabs={[]} />
                        } else if (obj.selectedRows[0].schemeLevel == "0") {
                            //打开流程form
                            return <FlowByThree
                                wrappedComponentRef={(me) => {
                                    if (me) {
                                        //加上apiBody.
                                        let flowData = {};
                                        for (const key in selectedRows[0]) {
                                            if (selectedRows[0].hasOwnProperty(key)) {
                                                if (!delAttr.includes(key)) {
                                                    const element = selectedRows[0][key];
                                                    flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                                }
                                            }
                                        }
                                        setTimeout(function () {
                                            var vals = QnnForm.sFormatData({ ...flowData }, me.props.formConfig, 'set');
                                            me.props.form.setFieldsValue({
                                                ...vals
                                            })
                                        }, 1)
                                    }
                                }}
                                {...obj} tabs={[]} />
                        }
                    }
                },
                {
                    name: 'edit',//内置add del
                    icon: 'edit',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '未发起方案编辑',
                    field: 'weiFaQiFangBianJiBtn',
                    onClick: (obj) => {
                        console.log(obj);
                        const { fetch, msg } = obj.btnCallbackFn;
                        if (obj.selectedRows[0].reviewState != "") {
                            msg.error('已发起审批的不可编辑！');
                            obj.btnCallbackFn.closeDrawer(false);
                            return;
                        }
                        this.setState({
                            dStatus: "detail"
                        })
                    },
                    formBtns: [
                        {
                            name: 'cancel', //关闭右边抽屉
                            type: 'dashed',//类型  默认 primary
                            label: '取消',
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                        },
                        {
                            name: 'diyJB',//内置add del
                            type: 'primary',//类型  默认 primary
                            label: '修改',//提交数据并且关闭右边抽屉
                            hide: function (obj) {
                                var index = obj.btnCallbackFn.getActiveKey();
                                if (index === "2" || index === "3") {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            onClick: function (obj) { //此时里面会多一个response
                                const { fetch, msg } = obj.btnCallbackFn;
                                fetch('updateZjPrProgrammeLaunch', { ...obj._formData }, function ({ data, success, message }) {
                                    if (success) {
                                        var launchId = {
                                            launchId: data.launchId
                                        };
                                        obj.props.form.setFieldsValue(launchId);
                                        msg.success(message)
                                        obj.btnCallbackFn.refresh();
                                        obj.btnCallbackFn.closeDrawer(false);
                                    } else {
                                        msg.error(message);
                                    }
                                })
                            }
                        },
                    ]
                },
                {
                    name: 'export',//内置add del
                    icon: 'export',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '导出',
                    onClick: (obj) => {
                        console.log(obj);
                        const { fetch, msg } = obj.btnCallbackFn;
                        if (obj.selectedRows.length) {
                            if (obj.selectedRows.length > 1) {
                                msg.error('导出时只能选择一条数据！');
                            } else {
                                // if (obj.selectedRows[0].reviewState == '4') {
                                    confirm({
                                        content: '请选择要加盖印章',
                                        okText: "一公局印章",
                                        cancelText: "隧道局印章",
                                        onOk() {
                                            if (obj.selectedRows[0].schemeLevel == '1' || obj.selectedRows[0].schemeLevel == '2') {
                                                window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewOne.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "$sealType=ygj" + "&type=.docx&name=" + obj.selectedRows[0].schemeName;
                                                //  window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewOne.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "&sealType=ygj" +"&type=.docx&name="+obj.selectedRows[0].schemeName;
                                            } else if (obj.selectedRows[0].schemeLevel == '0') {
                                                window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "$sealType=ygj" + "&type=.docx&name=" + obj.selectedRows[0].schemeName;
                                                // window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "&sealType=ygj" +"&type=.docx&name="+obj.selectedRows[0].schemeName;
                                            } else if (obj.selectedRows[0].schemeLevel == '3') {
                                                msg.error('IV级方案不可导出');
                                            }
                                        },
                                        onCancel() {
                                            if (obj.selectedRows[0].schemeLevel == '1' || obj.selectedRows[0].schemeLevel == '2') {
                                                window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewOne.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "$sealType=sdj" + "&type=.docx&name=" + obj.selectedRows[0].schemeName;
                                                //  window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewOne.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "&sealType=sdj" +"&type=.docx&name="+obj.selectedRows[0].schemeName;
                                            } else if (obj.selectedRows[0].schemeLevel == '0') {
                                                window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "$sealType=sdj" + "&type=.docx&name=" + obj.selectedRows[0].schemeName;
                                                // window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId + "&sealType=sdj" +"&type=.docx&name="+obj.selectedRows[0].schemeName;
                                            } else if (obj.selectedRows[0].schemeLevel == '3') {
                                                msg.error('IV级方案不可导出');
                                            }
                                        }
                                    }); 
                                // }
                                // else {
                                    // msg.error('请选择评审已通过的数据！');
                                // }
                            }
                        } else {
                            msg.error('未选择任何项！');
                        }


                    }
                },
                {
                    name: 'diydel',//内置add del
                    icon: 'delete',//icon
                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                    label: '未发起方案删除',
                    // fetchConfig: {//ajax配置
                    // 	apiName: 'batchDeleteUpdateZjPrProgrammeLaunch',
                    // },
                    onClick: (obj) => {
                        const { fetch, msg } = obj.btnCallbackFn;
                        if (obj.selectedRows.length) {
                            var selectedRows = obj.selectedRows;
                            var noSelectedRows = [];
                            for (let i = 0; i < obj.selectedRows.length; i++) {
                                console.log(obj.selectedRows[i].reviewState);
                                if (obj.selectedRows[i].reviewState != "") {
                                    noSelectedRows.push(obj.selectedRows[i]);
                                }
                            }
                            if (noSelectedRows == '') {
                                fetch('batchDeleteUpdateZjPrProgrammeLaunch', [...selectedRows], function ({ data, success, message }) {
                                    if (success) {
                                        msg.success(message)
                                        obj.btnCallbackFn.refresh();
                                    } else {
                                        msg.error(message);
                                    }
                                })
                            } else {
                                msg.error('选择项中存在已提交审核的！');
                            }
                        } else {
                            msg.error('未选择任何项！');
                        }


                    }
                }
            ],
            //每个表单项的布局 -- 搜索区域
            formItemLayoutSearch: {
                //默认数据
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 6 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 18 }
                }
            },
            tabs: this.state.flag ? [
                {
                    field: "form1",
                    name: "qnnForm",
                    title: "方案新建",
                    content: {
                        fetchConfig: function (obj) {
                            var rowData = obj.clickCb.rowData;
                            if (rowData) {
                                return {
                                    apiName: 'getzjPrProgrammeLaunchDetail',
                                    otherParams: {
                                        launchId: rowData.launchId
                                    }
                                }
                            } else if (obj.form.getFieldsValue().launchId != '') {
                                return {
                                    apiName: 'getzjPrProgrammeLaunchDetail',
                                    otherParams: {
                                        launchId: obj.form.getFieldsValue().launchId
                                    }
                                }
                            } else {
                                return {};
                            }
                        },
                        formItemLayout: {
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 4 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 20 }
                            }
                        },
                        formConfig: [
                            {
                                type: 'string',
                                label: '主键ID',
                                field: 'recordid', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                type: 'string',
                                label: '方案id',
                                field: 'detailedListId', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                type: 'string',
                                label: '方案审批id',
                                field: 'launchId', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                type: 'string',
                                label: '方案id',
                                field: 'schemeId', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                //普通选择框 可以和其他字段关联
                                type: "selectByPaging",
                                label: "项目名称",
                                disabled: this.state.dStatus === 'detail',
                                field: "projectName", //唯一的字段名 ***必传
                                placeholder: "请选择",
                                required: true,
                                fetchConfig: {
                                    apiName: "getZjSchemeConfirmationListBySelect",
                                    otherParams: {
                                        codePid: "0"
                                    },
                                    searchKey: 'projectName'
                                },
                                optionConfig: {
                                    //下拉选项配置
                                    label: "projectName",
                                    value: "recordid"
                                },
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                onChange: (val, props) => {

                                    if (val && this.state.dStatus === 'add') {

                                        //需要清空的子字段名字
                                        var childrenField = 'schemeNumber';

                                        props.props.form.resetFields([childrenField])
                                    }

                                },
                                //可以和别的输入框联动起来
                                condition: [
                                    {//条件
                                        regex: {
                                            schemeNumber: null,
                                        },
                                        action: 'disabled',
                                    },
                                ]
                            },
                            {
                                //普通选择框 可以和其他字段关联
                                type: "selectByPaging",
                                label: "方案编号",
                                field: "schemeNumber", //唯一的字段名 ***必传
                                required: true,
                                hide: this.state.dStatus === 'detail',
                                placeholder: "请选择",
                                fetchConfig: {
                                    apiName: "getZjSchemeDetailedListSelectAllList",
                                    params: {
                                        recordid: "projectName"
                                    },
                                    // searchKey:'projectName'
                                },
                                span: 12,
                                optionConfig: {
                                    //下拉选项配置
                                    label: "schemeNumber",
                                    value: "schemeNumber",
                                    linkageFields: {
                                        schemeName: "schemeName",
                                        engineeringType: "engineeringType",
                                        province: "province",
                                        hierarchyDescription: "hierarchyDescription",
                                        schemeId: "schemeId",
                                        implementationTime: "implementationTime",
                                        projectGeneralUser: "projectChiefEng",
                                        projectGeneralUserTel: "projectManagerTel",
                                        compilingSubject: "compilingSubject",
                                        projectLocation: "projectLocation",
                                        schemeType: "schemeType",
                                        projectClass: "projectClass"
                                        // linkageTwo:"orgId",
                                    }
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                            {
                                type: 'string',
                                label: '方案类型',
                                field: 'schemeType', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                type: 'string',
                                label: '项目所属板块',
                                field: 'projectClass', //唯一的字段名 ***必传
                                hide: true
                            },
                            {
                                type: 'string',
                                label: '方案编号',
                                disabled: true,
                                field: 'reviewUserState1', //唯一的字段名 ***必传
                                span: 12,
                                hide: this.state.dStatus === 'add',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'string',
                                label: '方案名称',
                                field: 'schemeName', //唯一的字段名 ***必传
                                editDisabled: true,
                                disabled: true,
                                placeholder: '请输入',//占位符
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            // {
                            // 	type: 'string',
                            // 	label: '工程类别',
                            // 	field: 'engineeringType', //唯一的字段名 ***必传
                            // 	editDisabled: true,
                            // 	disabled: true,
                            // 	placeholder: '请输入',//占位符
                            // 	span: 12,
                            // 	formItemLayout: {
                            // 		labelCol: {
                            // 			xs: { span: 24 },
                            // 			sm: { span: 4 }
                            // 		},
                            // 		wrapperCol: {
                            // 			xs: { span: 24 },
                            // 			sm: { span: 18 }
                            // 		}
                            // 	},
                            // },
                            {
                                type: 'select',
                                label: '编制主体',
                                field: 'compilingSubject', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                // required: true,
                                span: 12,
                                disabled: true,
                                editDisabled: true,
                                // multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                                // showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                                optionData: [//默认选项数据//可为function (props)=>array
                                    {
                                        name: '第一技术分中心',
                                        id: '4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '第二技术分中心',
                                        id: '4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '第三技术分中心',
                                        id: '1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '第四技术分中心',
                                        id: '4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '第五技术分中心',
                                        id: '848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51',
                                    },
                                    {
                                        name: '第六技术分中心',
                                        id: '4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '第七技术分中心',
                                        id: '4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '厦门技术分中心',
                                        id: '4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '桥隧技术分中心',
                                        id: '4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '海威技术分中心',
                                        id: '2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51',
                                    },
                                    {
                                        name: '总承包技术分中心',
                                        id: '4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '建筑技术分中心',
                                        id: '15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039',
                                    },
                                    {
                                        name: '世通技术分中心',
                                        id: '4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4',
                                    },
                                    {
                                        name: '海外技术中心',
                                        id: '22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039',
                                    }, {
                                        id: "suidj-189",
                                        name: "北京技术分中心"
                                    }, {
                                        id: "suidj-240",
                                        name: "西北技术分中心"
                                    }, {
                                        id: "suidj-423",
                                        name: "南京技术分中心"
                                    }, {
                                        id: "suidj-549",
                                        name: "西南技术分中心"
                                    }, {
                                        id: "suidj-584",
                                        name: "第八技术分中心"
                                    }, {
                                        id: "suidj-2183",
                                        name: "华北技术分中心"
                                    }, {
                                        id: "suidj-2230",
                                        name: "华南技术分中心"
                                    }, {
                                        id: "suidj-768",
                                        name: "盾构技术分中心"
                                    }, {
                                        id: "suidj-800",
                                        name: "电气化技术分中心"
                                    },{
                                        id: "suidj-8a8bb35a765172cc01768484656d0be1",
                                        name: "华中技术分中心"
                                    }
                                ],
                                optionConfig: {//下拉选项配置
                                    label: 'name', //默认 label
                                    value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'string',
                                label: '项目位置',
                                field: 'projectLocation', //唯一的字段名 ***必传
                                editDisabled: true,
                                disabled: true,
                                placeholder: '请输入',//占位符
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'datetime',
                                label: '方案计划实施时间',
                                field: 'implementationTime', //唯一的字段名 ***必传
                                span: 12,
                                placeholder: '请选择',
                                hide: function (obj) {
                                    var btnField = obj.clickCb.rowInfo.field;
                                    //编辑和详情不显示
                                    if (btnField !== "weiFaQiFangBianJiBtn") {
                                        return false;
                                    }
                                    return true;
                                },
                                // disabled: true,
                                editDisabled: true,
                                is24: true,//是否是24小时制 默认true
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'datetime',
                                label: '方案计划实施时间',
                                field: 'implementationTimeaaa', //唯一的字段名 ***必传
                                span: 12,
                                placeholder: '请选择',
                                hide: function (obj) {
                                    var btnField = obj.clickCb.rowInfo.field;
                                    //编辑和详情不显示
                                    if (btnField !== "weiFaQiFangBianJiBtn") {
                                        return true;
                                    }
                                    return false;
                                },
                                // disabled: true,
                                editDisabled: true,
                                is24: true,//是否是24小时制 默认true
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'string',
                                label: '方案编制人',
                                field: 'programmingPerson', //唯一的字段名 ***必传
                                required: true,
                                placeholder: '请输入',//占位符
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'string',
                                label: '编制人联系方式',
                                field: 'programmingPersonTel', //唯一的字段名 ***必传
                                required: true,
                                placeholder: '请输入',//占位符
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                            {
                                type: 'textarea',
                                label: '等级划分说明/施工重难点',
                                field: 'hierarchyDescription', //唯一的字段名 ***必传
                                disabled: true,
                                editDisabled: true,
                                placeholder: '请输入',//占位符
                                rows: 20, //行高 默认4                        
                                autosize: { minRows: 6, maxRows: 20 },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            },
                            {
                                type: 'textarea',
                                label: '备注',
                                field: 'remarks', //唯一的字段名 ***必传
                                // required: true,
                                rows: 20, //行高 默认4                        
                                placeholder: '请输入',//占位符
                                span: 24,
                                autosize: { minRows: 6, maxRows: 20 },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            },
                            {
                                type: 'textarea',
                                label: "方案初评情况",
                                // editDisabled:true,  	
                                help: "（对方案的内部审查情况进行说明）",
                                rows: 20, //行高 默认4
                                style: {
                                    paddingBottom: "20px"
                                },
                                field: 'programmingPreliminaryTrial', //唯一的字段名 ***必传
                                required: true,
                                placeholder: '请输入',//占位符
                                span: 24,
                                autosize: { minRows: 6, maxRows: 20 },
                                formItemLayout: {
                                    labelCol: {
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 20 }
                                    }
                                },
                            }
                        ],
                    }
                }
            ] : [
                    {
                        field: "form1",
                        name: "qnnForm",
                        title: "四级方案新建",
                        content: {
                            fetchConfig: function (obj) {
                                var rowData = obj.clickCb.rowData;
                                if (rowData) {
                                    return {
                                        apiName: 'getzjPrProgrammeLaunchDetailByIV',
                                        otherParams: {
                                            launchId: rowData.launchId
                                        }
                                    }
                                } else if (obj.form.getFieldsValue().launchId != '') {
                                    return {
                                        apiName: 'getzjPrProgrammeLaunchDetailByIV',
                                        otherParams: {
                                            launchId: obj.form.getFieldsValue().launchId
                                        }
                                    }
                                } else {
                                    return {};
                                }
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            },
                            formConfig: [
                                {
                                    type: 'string',
                                    label: '主键ID',
                                    field: 'recordid', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '方案id',
                                    field: 'detailedListId', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '方案审批id',
                                    field: 'launchId', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '方案id',
                                    field: 'schemeId', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    //普通选择框 可以和其他字段关联
                                    type: "selectByPaging",
                                    label: "项目名称",
                                    field: "projectName", //唯一的字段名 ***必传
                                    placeholder: "请选择",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getZjSchemeConfirmationListBySelect",
                                        otherParams: {
                                            codePid: "0"
                                        },
                                        searchKey: 'projectName'
                                    },
                                    optionConfig: {
                                        //下拉选项配置
                                        label: "projectName",
                                        value: "recordid"
                                    },
                                    span: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                    onChange: function (val, props) {

                                        if (val) {

                                            //需要清空的子字段名字
                                            var childrenField = 'schemeNumber';

                                            props.props.form.resetFields([childrenField])
                                        }

                                    },
                                    //可以和别的输入框联动起来
                                    condition: [
                                        {//条件
                                            regex: {
                                                schemeNumber: null,
                                            },
                                            action: 'disabled',
                                        },
                                    ]
                                },
                                {
                                    //普通选择框 可以和其他字段关联
                                    type: "selectByPaging",
                                    label: "方案编号",
                                    field: "schemeNumber", //唯一的字段名 ***必传
                                    required: true,
                                    placeholder: "请选择",
                                    fetchConfig: {
                                        apiName: "getZjSchemeDetailedListSelectAllListOnlyIv",
                                        params: {
                                            recordid: "projectName"
                                        },
                                        // searchKey:'schemeName'
                                    },
                                    span: 12,
                                    optionConfig: {
                                        //下拉选项配置
                                        label: "schemeNumber",
                                        value: "schemeNumber",
                                        linkageFields: {
                                            schemeName: "schemeName",
                                            engineeringType: "engineeringType",
                                            province: "province",
                                            hierarchyDescription: "hierarchyDescription",
                                            schemeId: "schemeId",
                                            implementationTime: "implementationTime",
                                            projectGeneralUser: "projectChiefEng",
                                            projectGeneralUserTel: "projectManagerTel",
                                            compilingSubject: "compilingSubject",
                                            projectLocation: "projectLocation",
                                            schemeType: "schemeType",
                                            projectClass: "projectClass"
                                            // linkageTwo:"orgId",
                                        }
                                    },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'string',
                                    label: '方案类型',
                                    field: 'schemeType', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '项目所属板块',
                                    field: 'projectClass', //唯一的字段名 ***必传
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '方案名称',
                                    field: 'schemeName', //唯一的字段名 ***必传
                                    disabled: true,
                                    editDisabled: true,
                                    placeholder: '请输入',//占位符
                                    span: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'select',
                                    label: '编制主体',
                                    field: 'compilingSubject', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    // required: true,
                                    span: 12,
                                    disabled: true,
                                    editDisabled: true,
                                    // multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                                    // showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                                    optionData: [//默认选项数据//可为function (props)=>array
                                        {
                                            name: '第一技术分中心',
                                            id: '4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '第二技术分中心',
                                            id: '4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '第三技术分中心',
                                            id: '1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '第四技术分中心',
                                            id: '4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '第五技术分中心',
                                            id: '848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51',
                                        },
                                        {
                                            name: '第六技术分中心',
                                            id: '4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '第七技术分中心',
                                            id: '4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '厦门技术分中心',
                                            id: '4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '桥隧技术分中心',
                                            id: '4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '海威技术分中心',
                                            id: '2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51',
                                        },
                                        {
                                            name: '总承包技术分中心',
                                            id: '4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '建筑技术分中心',
                                            id: '15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039',
                                        },
                                        {
                                            name: '世通技术分中心',
                                            id: '4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4',
                                        },
                                        {
                                            name: '海外技术中心',
                                            id: '22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039',
                                        }, {
                                            id: "suidj-189",
                                            name: "北京技术分中心"
                                        }, {
                                            id: "suidj-240",
                                            name: "西北技术分中心"
                                        }, {
                                            id: "suidj-423",
                                            name: "南京技术分中心"
                                        }, {
                                            id: "suidj-549",
                                            name: "西南技术分中心"
                                        }, {
                                            id: "suidj-584",
                                            name: "第八技术分中心"
                                        }, {
                                            id: "suidj-2183",
                                            name: "华北技术分中心"
                                        }, {
                                            id: "suidj-2230",
                                            name: "华南技术分中心"
                                        }, {
                                            id: "suidj-768",
                                            name: "盾构技术分中心"
                                        }, {
                                            id: "suidj-800",
                                            name: "电气化技术分中心"
                                        },{
                                            id: "suidj-8a8bb35a765172cc01768484656d0be1",
                                            name: "华中技术分中心"
                                        }
                                    ],
                                    optionConfig: {//下拉选项配置
                                        label: 'name', //默认 label
                                        value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                                    },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'string',
                                    label: '项目位置',
                                    field: 'projectLocation', //唯一的字段名 ***必传
                                    disabled: true,
                                    editDisabled: true,
                                    placeholder: '请输入',//占位符
                                    span: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'datetime',
                                    label: '方案计划实施时间',
                                    field: 'implementationTime', //唯一的字段名 ***必传
                                    span: 12,
                                    placeholder: '请选择',
                                    // disabled: true,
                                    editDisabled: true,
                                    is24: true,//是否是24小时制 默认true
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'string',
                                    label: '方案编制人',
                                    field: 'programmingPerson', //唯一的字段名 ***必传
                                    required: true,
                                    placeholder: '请输入',//占位符
                                    span: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'string',
                                    label: '编制人联系方式',
                                    field: 'programmingPersonTel', //唯一的字段名 ***必传
                                    required: true,
                                    placeholder: '请输入',//占位符
                                    span: 12,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 18 }
                                        }
                                    },
                                },
                                {
                                    type: 'textarea',
                                    label: '等级划分说明/施工重难点',
                                    field: 'hierarchyDescription', //唯一的字段名 ***必传
                                    disabled: true,
                                    editDisabled: true,
                                    placeholder: '请输入',//占位符
                                    rows: 20, //行高 默认4                        
                                    autosize: { minRows: 6, maxRows: 20 },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    type: 'files',
                                    label: '方案正文（最大200M）',
                                    field: 'documentTextNew', //唯一的字段名 ***必传
                                    required: true,//是否必填
                                    desc: '点击或者拖动上传', //默认 点击或者拖动上传
                                    subdesc: '只支持单个上传',//默认空
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        // name:'123', //上传文件的name 默认空
                                    },
                                    // accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                                    // max: 2, //最大上传数量
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    type: 'files',
                                    label: '方案附件',
                                    field: 'documentAccessoryNew', //唯一的字段名 ***必传
                                    required: true,//是否必填
                                    desc: '点击或者拖动上传', //默认 点击或者拖动上传
                                    subdesc: '只支持单个上传',//默认空
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        // name:'123', //上传文件的name 默认空
                                    },
                                    // accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                                    // max: 2, //最大上传数量
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    type: 'textarea',
                                    label: '备注',
                                    field: 'remarks', //唯一的字段名 ***必传
                                    // required: true,
                                    rows: 20, //行高 默认4                        
                                    placeholder: '请输入',//占位符
                                    span: 24,
                                    autosize: { minRows: 6, maxRows: 20 },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    type: 'textarea',
                                    label: "方案初评情况",
                                    // editDisabled:true,  	
                                    help: "（对方案的内部审查情况进行说明）",
                                    // editDisabled:true,  	
                                    style: {
                                        paddingBottom: "20px"
                                    },
                                    rows: 20, //行高 默认4
                                    field: 'programmingPreliminaryTrial', //唯一的字段名 ***必传
                                    required: true,
                                    placeholder: '请输入',//占位符
                                    span: 24,
                                    autosize: { minRows: 6, maxRows: 20 },
                                    formItemLayout: {
                                        labelCol: {
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            sm: { span: 20 }
                                        }
                                    },
                                }
                            ],
                        }
                    }
                ],

            formConfig: [
                {
                    isInForm: false,
                    table: {
                        width: 25,
                        align: 'center',
                        title: 'No.', //表头标题
                        dataIndex: 'no', //表格里面的字段
                        key: 'no',//表格的唯一key    
                        render: (data, rows, index) => {
                            return index + 1;
                        }
                    },
                },
                // {
                // 	table: {
                // 		title: '单位名称', //表头标题
                // 		dataIndex: 'unitName', //表格里面的字段
                // 		key: 'unitName',//表格的唯一key  					
                // 		// onClick: "Component",
                // 		// Component: {
                // 		// 	projectName: {
                // 		// 		"甘肃平天高速PTLM4标": FlowFormByYY
                // 		// 	}
                // 		// }
                // 	},
                // 	isInSearch: true,
                // 	form: {
                // 		type: 'string',
                // 		placeholder: "请输入",
                // 	},
                // },
                {
                    table: {
                        title: '项目名称', //表头标题
                        dataIndex: 'projectName', //表格里面的字段
                        isInSearch: true,
                        key: 'projectName',//表格的唯一key  
                    },
                    isInForm: false,
                    isInSearch: true,
                    form: {
                        type: 'string',
                        placeholder: "请输入"
                    }
                },
                {
                    table: {
                        width: 80,
                        title: '所属板块', //表头标题
                        dataIndex: 'projectClass', //表格里面的字段
                        key: 'projectClass',//表格的唯一key  
                        render: (data) => {
                            let r = "未知";
                            switch (data) {
                                case "0":
                                    r = "公路市政"
                                    break
                                case "1":
                                    r = "铁路轨道"
                                    break
                                case "2"://
                                    r = "城市房建"
                                    break
                                case "3":
                                    r = "海外事业部"
                                    break
                            }
                            return r
                        }
                    },
                    isInForm: false,
                    isInSearch: true,
                    form: {
                        type: 'select',
                        label: '所属板块',
                        field: 'projectClass', //唯一的字段名 ***必传
                        placeholder: '请选择',
                        required: true,
                        multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                        showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                        optionData: [//默认选项数据//可为function (props)=>array
                            {
                                value: '0',
                                text: '公路市政'
                            },
                            {
                                value: '1',
                                text: '铁路轨道'
                            },
                            {
                                value: '2',
                                text: '城市房建'
                            },
                            {
                                value: '3',
                                text: '海外事业部'
                            },
                        ],
                        optionConfig: {//下拉选项配置
                            label: 'text', //默认 label
                            value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                        },
                    }
                },
                {
                    table: {
                        title: '方案编号', //表头标题
                        dataIndex: 'schemeNumber', //表格里面的字段
                        key: 'schemeNumber',//表格的唯一key  					
                    },
                    isInSearch: true,
                    form: {
                        type: 'string',
                        placeholder: "请输入"
                    }
                },
                {
                    table: {
                        title: '状态',
                        dataIndex: 'projectGeneralUser',
                        key: 'projectGeneralUser',
                    },
                    isInTable: false,
                    isInSearch: true,
                    form: {
                        type: 'select',
                        label: '状态',
                        field: 'reviewState', //唯一的字段名 ***必传
                        placeholder: '请选择',
                        required: true,
                        multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                        showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                        optionData: [//默认选项数据//可为function (props)=>array
                            {
                                value: '2',
                                text: '评审中'
                            },
                            {
                                value: '3',
                                text: '评审未通过'
                            },
                            {
                                value: '4',
                                text: '评审已通过'
                            },
                        ],
                        optionConfig: {//下拉选项配置
                            label: 'text', //默认 label
                            value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                        },
                    }
                },
                {
                    table: {
                        title: '方案名称', //表头标题
                        dataIndex: 'schemeName', //表格里面的字段
                        key: 'schemeName',//表格的唯一key  
                        isCanClick: function (obj) {
                            if (obj.rowData.workId) {
                                return true
                            } else {
                                if (obj.rowData.reviewState == '4') {
                                    return true
                                } else {
                                    return false;
                                }
                            }
                        },
                        onClick: 'Component',
                        Component: {
                            schemeLevel: {
                                "3": (obj) => {
                                    this.setState({
                                        dStatus: "detail"
                                    })
                                    return <FlowByFour {...obj} />
                                },
                                "2": detailByFlowOne,
                                "1": detailByFlowTwo,
                                "0": detailByFlowThree
                                // "2": FlowByOne,
                                // "1": FlowByTwo,
                                // "0": FlowByThree
                            }
                        },
                        // btns: [
                        // 	{
                        // 		label: '导出',
                        // 		type: 'primary', //primary dashed danger
                        // 		fetchConfig: {
                        // 			//api 默认提交整个表单的数据
                        // 			apiName: 'submit',
                        // 		},
                        // 		onClick: function (obj) { //此时里面会多一个 response
                        // 			console.log(obj)
                        // 		},

                        // 	}
                        // ]
                    },
                    isInSearch: true,
                    form: {
                        type: 'string',
                        placeholder: "请输入"
                    }
                },
                {
                    table: {
                        title: '方案计划实施时间',
                        dataIndex: 'implementationTime',
                        key: 'implementationTime',
                        format: 'YYYY-MM-DD',
                    },
                    isInForm: false
                },
                {
                    table: {
                        width: 90,
                        title: '审查结果', //表头标题
                        dataIndex: 'reviewResults', //表格里面的字段
                        key: 'reviewResults',//表格的唯一key  
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '方案编制者', //表头标题
                        dataIndex: 'programmingPerson', //表格里面的字段
                        key: 'programmingPerson',//表格的唯一key  
                    },
                    isInForm: false
                },
                {
                    table: {
                        width: 80,
                        title: '评审状态', //表头标题
                        dataIndex: 'reviewState', //表格里面的字段
                        key: 'reviewState',//表格的唯一key  
                        render: (data) => {
                            let r = "未知";
                            switch (data) {
                                case "":
                                    r = "未发起"
                                    break
                                case "0":
                                    r = "未评审"
                                    break
                                case "1":
                                    r = "评审通过"
                                    break
                                case "2"://
                                    r = "评审中"
                                    break
                                case "3":
                                    r = "评审未通过"
                                    break
                                case "4"://
                                    r = "评审已通过"
                                    break
                            }
                            return r
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '方案上传时间',
                        dataIndex: 'createTime',
                        key: 'createTime',
                        format: 'YYYY-MM-DD HH:mm:ss',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '方案评审通过时间',
                        dataIndex: 'modifyTime',
                        key: 'modifyTime',
                        format: 'YYYY-MM-DD HH:mm:ss',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注', //表头标题
                        dataIndex: 'remarks', //表格里面的字段
                        key: 'remarks',//表格的唯一key  
                    },
                    isInForm: false
                },
                {
                    table: {
                        width: 40,
                        title: '审查结果',
                        dataIndex: 'reviewResults',
                        key: 'reviewResults',
                    },
                    isInTable: false,
                    // isInSearch: true,
                    // form: {
                    //     type: 'string',
                    //     placeholder: "请输入"
                    // }
                },
                {
                    table: {
                        title: '技术等级',
                        dataIndex: 'schemeLevel',
                        key: 'schemeLevel',
                    },
                    isInTable: false,
                    isInSearch: true,
                    form: {
                        type: 'select',
                        label: '技术等级',
                        field: 'schemeLevel', //唯一的字段名 ***必传
                        placeholder: '请选择',
                        required: true,
                        multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                        showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                        optionData: [//默认选项数据//可为function (props)=>array
                            {
                                value: '0',
                                text: 'Ⅲ级施工方案'
                            },
                            {
                                value: '1',
                                text: 'Ⅱ级施工方案'
                            },
                            {
                                value: '2',
                                text: 'I级施工方案'
                            },
                            {
                                value: '3',
                                text: 'IV级施工方案'
                            },
                        ],
                        optionConfig: {//下拉选项配置
                            label: 'text', //默认 label
                            value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                        },
                    }
                },

            ],
            searchFormColNum: 6,
        }
    }
    render() {
        console.log(this.props)
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...this.launch.bind(this)()}
                />
            </div>
        );
    }
}
export default basic(index);
