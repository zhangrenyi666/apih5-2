import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg } from "antd";
import moment from 'moment';
import s from "./style.less";
const config = {

    antd: {
        rowKey: function (row) {
            return row.projectId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    },
    labelConfig: {
        actionBtn: <div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>操作</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题
        detail: <div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>详情</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题
        add: <div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>新增</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题
        edit: <div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>修改</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: null
        }
    }
    componentWillMount () { }
    componentDidMount () { }
    formatNum (num) {
        var newStr = "";
        var count = 0;
        var str = num.toString();
        // 当数字是整数
        if (str.indexOf(".") == -1) {
            for (var i = str.length - 1; i >= 0; i--) {
                if (count % 3 == 0 && count != 0) {
                    newStr = str.charAt(i) + "," + newStr;
                } else {
                    newStr = str.charAt(i) + newStr;
                }
                count++;
            }
            str = newStr; //自动补小数点后两位
            return str;
        }
        // 当数字带有小数
        else {
            for (var i = str.indexOf(".") - 1; i >= 0; i--) {
                if (count % 3 == 0 && count != 0) {
                    newStr = str.charAt(i) + "," + newStr;
                } else {
                    newStr = str.charAt(i) + newStr; //逐个字符相接起来
                }
                count++;
            }
            str = newStr + (str + "00").substr((str + "00").indexOf("."), 3);
            return str;
        }
    }
    render () {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzProManageListForHard',
                        otherParams: {
                            projectId: projectId,
                            projectType: '0'
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectType',
                                type: 'string',
                                initialValue: '0',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目编号',
                                width: 100,
                                tooltip: 10,
                                dataIndex: 'proNo',
                                filter: true,
                                onClick: 'detail',
                                key: 'proNo',
                                align:'center',
                                drawerTitle:<div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>详情</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题,
                                willExecute: (obj) => {
                                    this.setState({
                                        projectId: obj.rowData.projectId
                                    })
                                },
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field: 'proNo',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '管理单位',
                                width: 100,
                                tooltip: 10,
                                dataIndex: 'companyName',
                                key: 'companyName',
                                filter: true,
                                align:'center',
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'companyId',
                                optionConfig: {
                                    label: 'companyName',
                                    value: 'companyId'
                                },
                                fetchConfig: {
                                    apiName: "getHomeProgressPlaningComname"
                                },
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 300,
                                tooltip: 50,
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                fieldsConfig: {
                                    field: 'projectId',
                                    showSearch: true,
                                    type: 'select',
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: "getZjTzProManageList"
                                    },
                                }
                            },
                            isInForm: false,
                            form: {
                                field: 'projectId',
                                showSearch: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                            },
                        },
                        {
                            table: {
                                title: '项目简称',
                                width: 200,
                                tooltip: 50,
                                dataIndex: 'projectShortName',
                                key: 'projectShortName',
                                filter: true,
                            },
                            isInForm: false,
                            form: {
                                field: 'projectShortName',
                                type: 'string',
                                placeholder: '请输入'
                            },
                        }, {
                            table: {
                                title: '工程类别',
                                width: 160,
                                tooltip: 23,
                                dataIndex: 'proCategoryName',
                                key: 'proCategoryName',
                                filter: true,
                            },
                            isInForm: false,
                            form: {
                                field: 'proCategoryId',
                                type: 'select',
                                required: true,
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'xiangMuLeiBie'
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '项目进展',
                                width: 140,
                                dataIndex: 'proProcessName',
                                key: 'proProcessName',
                                filter: true,
                            },
                            form: {
                                field: 'proProcessId',
                                type: 'select',
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'xiangMuJinZhan'
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '所在区域',
                                width: 140,
                                dataIndex: 'areaName',
                                key: 'areaName',
                                filter: true
                            },
                            form: {
                                type: "select",
                                field: "areaId",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'suoZaiQuYu'
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '投资合同额(元)',
                                width: 160,
                                dataIndex: 'amount1',
                                key: 'amount1',
                                filter: true,
                                render: (data) => {
                                    return data ? this.formatNum(data) : 0
                                }
                            },
                            isInForm: false,
                            form: {
                                field: 'amount1',
                                type: 'string'
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 80,
                                btns: [
                                    {
                                        name: 'edit',
                                        field: 'editOutBtn',
                                        render: function (rowData) {
                                            return '<a>修改</a>';
                                        },
                                        drawerTitle:<div style={{overflow:'hidden'}}><div style={{float:'left',paddingRight:'20px'}}>编辑</div>&nbsp;&nbsp;&nbsp;<div style={{color:'red', float:'left',lineHeight:'14px',fontSize:'14px'}}><div>有子项目的，除了在本界面填报项目整体的时限信息外（若有），还须在 “子项目” 界面填报相关子项目时限信息</div><div>置灰部分的信息从数据采集共享系统直接引用</div></div></div>,//抽屉新增左上角文字,//行右边下拉菜单标题
                                        willExecute: (obj) => {
                                            this.setState({
                                                projectId: obj.rowData.projectId
                                            })
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',
                                                type: 'primary',
                                                label: '保存',
                                                fetchConfig: {
                                                    apiName: 'updateZjTzProManage'
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                    method={{
                        willAddClick: () => {//willExecute
                            this.setState({
                                projectId: null
                            })
                        },
                        addSaveClick: (obj) => {
                            this.props.myFetch(this.state.projectId ? 'updateZjTzProManage' : 'addZjTzProManage', obj._formData).then(
                                ({ success, message, data }) => {
                                    if (success) {
                                        Msg.success(message);
                                        if (obj.btnCallbackFn.getActiveKey() === '0') {
                                            this.table.qnnForm.form.setFieldsValue({
                                                projectId: data.projectId
                                            })
                                            this.setState({
                                                projectId: data.projectId
                                            }, () => {
                                                obj.btnCallbackFn.setActiveKey('1');
                                            })
                                        } else if (obj.btnCallbackFn.getActiveKey() === '1') {
                                            obj.btnCallbackFn.setActiveKey('2');
                                        } else if (obj.btnCallbackFn.getActiveKey() === '2') {
                                            obj.btnCallbackFn.setActiveKey('3');
                                        } else if (obj.btnCallbackFn.getActiveKey() === '3') {
                                            obj.btnCallbackFn.closeDrawer();
                                        }
                                        this.table.refresh();
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                fetchConfig: (obj) => {
                                    if (this.state.projectId) {
                                        return {
                                            apiName: 'getZjTzProManageDetails',
                                            otherParams: {
                                                projectId: this.state.projectId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }

                                },
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "projectId",
                                        hide: true
                                    },
                                    {
                                        field: 'projectType',
                                        type: 'string',
                                        initialValue: '0',
                                        hide: true,
                                    },
                                    {
                                        type: "string",
                                        label: "项目编号",
                                        field: "proNo",
                                        required: true,
                                        // editDisabled: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "项目简称",
                                        field: "projectShortName",
                                        // editDisabled: true,
                                        placeholder: "请输入",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "项目名称",
                                        field: "projectName",
                                        // editDisabled: true,
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "工程类别",
                                        editDisabled: true,
                                        field: "proCategoryId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'xiangMuLeiBie'
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "项目类型",
                                        field: "proTypeId",
                                        editDisabled: true,
                                        placeholder: "请输入",
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'xiangMuLeiXing'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "投资模式",
                                        editDisabled: true,
                                        field: "investPatten",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "项目进展",
                                        editDisabled: true,
                                        field: "proProcessId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'xiangMuJinZhan'
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "排序号",
                                        editDisabled: true,
                                        field: "sequence",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "建设期（月）",
                                        editDisabled: true,
                                        field: "constructPeriod",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "运营期/回购期（年）",
                                        editDisabled: true,
                                        field: "runPeriod",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        editDisabled: true,
                                        label: "投资协议签订时间",
                                        field: "signDate1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "特许权合同/PPP合同（建设移交合同）签订时间",
                                        editDisabled: true,
                                        field: "signDate2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "中标投资额（元）",
                                        field: "amount1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "中标建安费（元）",
                                        field: "amount3",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "radio",
                                        label: "规模控制主体",
                                        field: "sizeControlSubject",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        initialValue: '0',
                                        optionData: [
                                            {
                                                label: "项目整体",
                                                value: "0"
                                            },
                                            {
                                                label: "单个子项目",
                                                value: "1"
                                            }
                                        ],
                                    },
                                    {
                                        type: "radio",
                                        label: "工期分析主体",
                                        field: "analySubject",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        initialValue: '0',
                                        optionData: [
                                            {
                                                label: "项目",
                                                value: "0"
                                            },
                                            {
                                                label: "子项目",
                                                value: "1"
                                            }
                                        ],
                                    },
                                    {
                                        type: "radio",
                                        label: "建设期结束标志",
                                        field: "constructEnd",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        required:true,
                                        optionData: [
                                            {
                                                label: "交工",
                                                value: "0"
                                            },
                                            {
                                                label: "竣工",
                                                value: "1"
                                            }
                                        ],
                                    },
                                    {
                                        type: "date",
                                        label: "合同约定开工时间",
                                        // editDisabled: true,
                                        field: "signDate3",
                                        // required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "合同约定交工时间",
                                        field: "handoverDatePlan",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "合同约定竣工时间",
                                        field: "complateDatePlan",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        editDisabled: true,
                                        label: "合同约定运营 / 回购开始时间",
                                        field: "runDate1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "合同约定运营/回购结束时间",
                                        editDisabled: true,
                                        field: "runDate3", //唯一的字段名 ***必传
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "策划批复开工时间",
                                        field: "approvalStartdate",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "策划批复交工时间",
                                        field: "approvalHandoverDate",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "策划批复竣工时间",
                                        field: "approvalCompleteDate",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        label: "实际开工时间",
                                        // editDisabled: true,
                                        field: "actualDate",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    // {
                                    //     type: "date",
                                    //     label: "交（竣）工验收时间",
                                    //     field: "checkDate",
                                    //     editDisabled: true,
                                    //     placeholder: "请输入",
                                    //     span: 12,
                                    //     formItemLayout: {
                                    //         labelCol: {
                                    //             xs: { span: 20 },
                                    //             sm: { span: 10 }
                                    //         },
                                    //         wrapperCol: {
                                    //             xs: { span: 20 },
                                    //             sm: { span: 14 }
                                    //         }
                                    //     }
                                    // },
                                    {
                                        type: "date",
                                        label: "实际交工时间",
                                        field: "handoverDateActrual",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },


                                    {
                                        type: "date",
                                        label: "实际竣工时间",
                                        field: "complateDateActrual",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        editDisabled: true,
                                        label: "实际运营/回购开始时间 ",
                                        field: "runDate2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        type: "date",
                                        label: "实际运营/回购结束时间",
                                        field: "runDate4",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        editDisabled: true,
                                        label: "批复类型",
                                        field: "replyType",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionData: [
                                            {
                                                label: "中国交建批复",
                                                value: "1"
                                            },
                                            {
                                                label: "一公局集团批复",
                                                value: "2"
                                            },
                                            {
                                                label: "未批复",
                                                value: "3"
                                            },
                                        ],
                                    },
                                    {
                                        type: "radio",
                                        label: "是否并入一公局集团财务报表",
                                        editDisabled: true,
                                        field: "mergeFlag",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionData: [
                                            {
                                                label: "否",
                                                value: "0"
                                            },
                                            {
                                                label: "是",
                                                value: "1"
                                            }
                                        ],
                                    },
                                    {
                                        type: "radio",
                                        editDisabled: true,
                                        label: "是否签约",
                                        field: "signFlag",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionData: [
                                            {
                                                label: "否",
                                                value: "0"
                                            },
                                            {
                                                label: "是",
                                                value: "1"
                                            }
                                        ],
                                    },
                                    {
                                        type: "date",
                                        editDisabled: true,
                                        label: "中交批复时间",
                                        field: "zjDate",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "管理单位",
                                        field: "unitId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'guanLiDanWei'
                                            }
                                        },
                                    },
                                    {
                                        label: '管理单位组织机构',
                                        field: 'companyList',
                                        // editDisabled: true,
                                        required: true,
                                        type: "treeSelect",
                                        treeSelectOption: {
                                            selectType: '1',
                                            maxNumber: 1,
                                            fetchConfig: {
                                                apiName: 'getSysDepartmentUserAllTree',
                                            },
                                            useCollect: false,
                                            collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                            collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                            collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]

                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },

                                    {
                                        type: "number",
                                        label: "中交批复投资额（元）",
                                        editDisabled: true,
                                        field: "zjAmount1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其中：建安费（元）",
                                        field: "zjAmount2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "中交集团批复文号",
                                        editDisabled: true,
                                        field: "zjNo",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "政府批复概算（元）",
                                        editDisabled: true,
                                        field: "goverAmount",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "项目所在地",
                                        editDisabled: true,
                                        field: "location",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "所在区域",
                                        field: "areaId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'suoZaiQuYu'
                                            }
                                        },
                                    },
                                    {
                                        type: "textarea",
                                        label: "工程概况",
                                        editDisabled: true,
                                        field: "proProfile",
                                        placeholder: "请输入",
                                        autoSize: {
                                            minRows: 2,
                                            maxRows: 10
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: "textarea",
                                        label: "纳入国家、省发改委PPP项目库或财政部、省财政厅PPP综合信息平台项目库情况（具体阶段）",
                                        field: "ext1", //唯一的字段名 ***必传
                                        placeholder: "请输入", //占位符
                                        autoSize: {
                                            minRows: 2,
                                            maxRows: 10
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    },
                                    {
                                        type: "textarea",
                                        label: "政府承诺给予的优惠、补贴等",
                                        editDisabled: true,
                                        field: "ext2", //唯一的字段名 ***必传
                                        placeholder: "请输入", //占位符
                                        autoSize: {
                                            minRows: 2,
                                            maxRows: 10
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: "textarea",
                                        label: "项目地图跳转参数",
                                        field: "ext3", //唯一的字段名 ***必传
                                        placeholder: "请输入", //占位符
                                        hide: true,
                                        autoSize: {
                                            minRows: 2,
                                            maxRows: 10
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: "textarea",
                                        label: "跳转参数例子以及说明",
                                        field: "ext4", //唯一的字段名 ***必传
                                        placeholder: "请输入", //占位符
                                        hide: true,
                                        autoSize: {
                                            minRows: 2,
                                            maxRows: 10
                                        },
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        // 金额信息 TAB
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "金额信息",
                            disabled: (args) => {
                                return args.form.getFieldValue("projectId") || args.clickCb.selectedRows[0]?.["projectId"] ? false : true
                            },
                            content: {
                                fetchConfig: (obj) => {
                                    if (this.state.projectId) {
                                        return {
                                            apiName: 'getZjTzProManageDetails',
                                            otherParams: {
                                                projectId: this.state.projectId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "projectId", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyJE',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    合同金额
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "number",
                                        label: "合同额（元）",
                                        editDisabled: true,
                                        field: "amount1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "回购总额（元）",
                                        editDisabled: true,
                                        field: "amount2", //唯一的字段名 ***必传
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其中：建安费（元）",
                                        editDisabled: true,
                                        field: "amount3", //唯一的字段名 ***必传
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "征拆费（元）",
                                        editDisabled: true,
                                        field: "amount4", //唯一的字段名 ***必传
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        editDisabled: true,
                                        label: "管理费（元）",
                                        field: "amount5",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        editDisabled: true,
                                        label: "监理费（元）",
                                        field: "amount6",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "勘察设计费（元）",
                                        editDisabled: true,
                                        field: "amount7",
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其他（元）",
                                        editDisabled: true,
                                        field: "amount8",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyJG',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    资金结构
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "number",
                                        editDisabled: true,
                                        label: "资本金（元）",
                                        field: "fund1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "自有资金（元）",
                                        editDisabled: true,
                                        field: "fund2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "基金（元）",
                                        editDisabled: true,
                                        field: "fund3",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其中：一公局认购基金（元）",
                                        field: "fund4",
                                        editDisabled: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        editDisabled: true,
                                        label: "其他股东（元）",
                                        field: "fund5",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        editDisabled: true,
                                        label: "融资（元）",
                                        field: "fund6",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "银行借款（元）",
                                        editDisabled: true,
                                        field: "fund7",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "基金（元）",
                                        editDisabled: true,
                                        field: "fund8",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其中：一公局认购基金（元） ",
                                        editDisabled: true,
                                        field: "fund9",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "信托等其他（元）",
                                        editDisabled: true,
                                        field: "fund10",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "政府补贴（元）",
                                        editDisabled: true,
                                        field: "fund11",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其他资金（元）",
                                        editDisabled: true,
                                        field: "fund12",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyPGCN',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    项目评估与承诺
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "评估预算类别",
                                        field: "evaluate1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "评估投资总额（元）",
                                        field: "evaluate2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "其中：建安费（元）",
                                        field: "evaluate3",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "项目财务内部收益率%",
                                        editDisabled: true,
                                        field: "evaluate4",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "资本金财务内部收益率% ",
                                        editDisabled: true,
                                        field: "evaluate5",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "动态投资回收期（年） ",
                                        editDisabled: true,
                                        field: "evaluate6",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "评估时假定价差收益率%",
                                        editDisabled: true,
                                        field: "evaluate7",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "评估价差收益（元）",
                                        editDisabled: true,
                                        field: "evaluate8",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "评估施工利润（元）",
                                        editDisabled: true,
                                        field: "evaluate9",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "评估投融资收益（元）",
                                        editDisabled: true,
                                        field: "evaluate10",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "承诺价差收益（元）",
                                        editDisabled: true,
                                        field: "evaluate11",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "承诺投融资收益（元）",
                                        editDisabled: true,
                                        field: "evaluate12",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "投评月均收入",
                                        field: "evaluate13",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "投评月均车辆通行数量（辆）",
                                        editDisabled: true,
                                        field: "evaluate14",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyYYHGMX',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    历年运营/回购明细（投评数据）
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableWB",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 0 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        qnnTableConfig: {
                                            tabs: [],
                                            fetchConfig: (obj) => {
                                                if (this.state.projectId) {
                                                    return {
                                                        apiName: 'getZjTzProRebuyInfoPlanList',
                                                        otherParams: {
                                                            projectId: this.state.projectId
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZjTzProRebuyInfoPlanList',
                                                        otherParams: {
                                                            projectId: ''
                                                        }
                                                    }
                                                }
                                            },
                                            antd: {
                                                rowKey: 'rebuyInfoId',
                                                size: 'small'
                                            },
                                            limit: 999999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableHgMx = me;
                                            },
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: true,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "关联的主键id",
                                                        field: "rebuyInfoId",
                                                        type: 'string',
                                                        hide: true
                                                    },
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "",
                                                        field: "projectId",
                                                        type: 'string',
                                                        hide: true,
                                                        initialValue: (obj) => {
                                                            if (this.state.projectId) {
                                                                return this.state.projectId
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '年份',
                                                        dataIndex: 'appointDate',
                                                        key: 'appointDate',
                                                        width: '20%',
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY')
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'year',
                                                        field: 'appointDate',
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: "本年<span style='color: red'>投评</span>应回购或运营收费金额",
                                                        width: '50%',
                                                        dataIndex: 'appointAmount',
                                                        key: 'appointAmount',
                                                        render: (data) => {
                                                            return data ? this.formatNum(data) : 0
                                                        }
                                                    },
                                                    form: {
                                                        label: '本年投评应回购或运营收费金额',
                                                        type: 'number',
                                                        field: 'appointAmount',
                                                        placeholder: '请输入',
                                                        max: 99999999999999,
                                                        formItemLayoutForm: {
                                                            labelCol: {
                                                                xs: { span: 24 },
                                                                sm: { span: 8 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 24 },
                                                                sm: { span: 16 }
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: "操作",
                                                        fixed: 'right',
                                                        dataIndex: 'action',
                                                        key: 'action',
                                                        align: "center",
                                                        noHaveSearchInput: true,
                                                        showType: "tile",
                                                        width: 80,
                                                        btns: [
                                                            {
                                                                name: 'edit',
                                                                render: function (rowData) {
                                                                    let btnField = rowData.qnnFormProps.qnnformData.qnnFormProps.clickCb.rowInfo.field;
                                                                    if (btnField === 'addOutBtn' || btnField === 'editOutBtn') {
                                                                        return '<a>修改</a>';
                                                                    } else {
                                                                        return ''
                                                                    }
                                                                },
                                                                formBtns: [
                                                                    {
                                                                        name: 'cancel',
                                                                        type: 'dashed',
                                                                        label: '取消',
                                                                    },
                                                                    {
                                                                        name: 'submit',
                                                                        type: 'primary',
                                                                        label: '保存',
                                                                        fetchConfig: {
                                                                            apiName: 'updateZjTzProRebuyInfoPlan',
                                                                        }
                                                                    }
                                                                ]
                                                            }
                                                        ]
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'add',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                    formBtns: [
                                                        {
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '取消',
                                                        },
                                                        {
                                                            name: 'submit',
                                                            type: 'primary',
                                                            label: '保存',
                                                            fetchConfig: {
                                                                apiName: 'addZjTzProRebuyInfoPlan',
                                                                // otherParams: {
                                                                //     projectId
                                                                // }
                                                            }
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjTzProRebuyInfoPlan',
                                                    },
                                                }
                                            ]
                                        }
                                    },
                                ],
                            }
                        },
                        // 子项目   TAB
                        {
                            field: "form3",
                            name: "qnnForm",
                            title: "子项目",
                            disabled: (args) => {
                                return args.form.getFieldValue("projectId") || args.clickCb.selectedRows[0]?.["projectId"] ? false : true
                            },
                            content: {
                                formConfig: [
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableZGS",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 0 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        qnnTableConfig: {
                                            tabs: [],
                                            fetchConfig: (obj) => {
                                                if (this.state.projectId) {
                                                    return {
                                                        apiName: 'getZjTzProSubprojectInfoList',
                                                        otherParams: {
                                                            projectId: this.state.projectId
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZjTzProSubprojectInfoList',
                                                        otherParams: {
                                                            projectId: ''
                                                        }
                                                    }
                                                }
                                                // return {
                                                //     apiName: 'getZjTzProSubprojectInfoList',
                                                //     otherParams: {
                                                //         projectId
                                                //     }
                                                // }
                                            },
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'subprojectInfoId',
                                                size: 'small'
                                            },
                                            drawerConfig: {
                                                width: '1100px'
                                            },
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 10 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 14 },
                                                    sm: { span: 14 }
                                                }
                                            },
                                            limit: 999999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableZGS = me;
                                            },
                                            isShowRowSelect: true,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "关联的主键id",
                                                        field: "subprojectInfoId",
                                                        type: 'string',
                                                        hide: true
                                                    },
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "",
                                                        field: "projectId",
                                                        type: 'string',
                                                        hide: true,
                                                        initialValue: (obj) => {
                                                            if (this.state.projectId) {
                                                                return this.state.projectId
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '子项目编号',
                                                        dataIndex: 'subprojectNumber',
                                                        key: 'subprojectNumber',
                                                        width: 150,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'subprojectNumber',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '子项目名称',
                                                        dataIndex: 'subprojectName',
                                                        key: 'subprojectName',
                                                        width: 150,
                                                        tooltip: 18,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: 'subprojectName',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '中标投资额(元)',
                                                        dataIndex: 'amount1',
                                                        key: 'amount1',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '中标建安费(元)',
                                                        dataIndex: 'amount3',
                                                        key: 'amount3',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '建设期结束标志',
                                                        dataIndex: 'constructEnd',
                                                        key: 'constructEnd',
                                                        type: 'select',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'label',
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '交工',
                                                                value: '0'
                                                            },
                                                            {
                                                                label: '竣工',
                                                                value: '1'
                                                            }
                                                        ],
                                                        placeholder: '请选择',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '建设期(月)',
                                                        dataIndex: 'constructPeriod',
                                                        key: 'constructPeriod',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同约定开工时间',
                                                        dataIndex: 'signDate3',
                                                        key: 'signDate3',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同约定交工时间',
                                                        dataIndex: 'handoverDatePlan',
                                                        key: 'handoverDatePlan',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同约定竣工时间',
                                                        dataIndex: 'complateDatePlan',
                                                        key: 'complateDatePlan',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同约定运营/回购开始时间',
                                                        dataIndex: 'runDate1',
                                                        key: 'runDate1',
                                                        format: 'YYYY-MM-DD',
                                                        width: 200
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同约定运营/回购结束时间',
                                                        dataIndex: 'runDate3',
                                                        key: 'runDate3',
                                                        format: 'YYYY-MM-DD',
                                                        width: 200
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '策划批复开工时间',
                                                        dataIndex: 'approvalStartDate',
                                                        key: 'approvalStartDate',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '策划批复交工时间',
                                                        dataIndex: 'approvalHandoverDate',
                                                        key: 'approvalHandoverDate',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '策划批复竣工时间',
                                                        dataIndex: 'approvalCompleteDate',
                                                        key: 'approvalCompleteDate',
                                                        format: 'YYYY-MM-DD',
                                                        width: 130
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '实际开工时间',
                                                        dataIndex: 'startDateActual',
                                                        key: 'startDateActual',
                                                        format: 'YYYY-MM-DD',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '实际交工时间',
                                                        dataIndex: 'handoverDateActual',
                                                        key: 'handoverDateActual',
                                                        format: 'YYYY-MM-DD',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '实际竣工时间',
                                                        dataIndex: 'complateDateActual',
                                                        key: 'complateDateActual',
                                                        format: 'YYYY-MM-DD',
                                                        width: 120
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '实际运营/回购开始时间',
                                                        dataIndex: 'runDate2',
                                                        key: 'runDate2',
                                                        format: 'YYYY-MM-DD',
                                                        width: 170
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '实际运营/回购结束时间',
                                                        dataIndex: 'runDate4',
                                                        key: 'runDate4',
                                                        format: 'YYYY-MM-DD',
                                                        width: 170
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '请输入',
                                                        spanForm: 12
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: "操作",
                                                        fixed: 'right',
                                                        dataIndex: 'action',
                                                        key: 'action',
                                                        align: "center",
                                                        noHaveSearchInput: true,
                                                        showType: "tile",
                                                        width: 80,
                                                        btns: [
                                                            {
                                                                name: 'edit',
                                                                render: function (rowData) {
                                                                    let btnField = rowData.qnnFormProps.qnnformData.qnnFormProps.clickCb.rowInfo.field;
                                                                    if (btnField === 'addOutBtn' || btnField === 'editOutBtn') {
                                                                        return '<a>修改</a>';
                                                                    } else {
                                                                        return ''
                                                                    }
                                                                },
                                                                formBtns: [
                                                                    {
                                                                        name: 'cancel',
                                                                        type: 'dashed',
                                                                        label: '取消',
                                                                    },
                                                                    {
                                                                        name: 'submit',
                                                                        type: 'primary',
                                                                        label: '保存',
                                                                        fetchConfig: {
                                                                            apiName: 'updateZjTzProSubprojectInfo',
                                                                        },
                                                                        // onClick: (obj) => {
                                                                        //     this.props.myFetch('updateZjTzProSubprojectInfo',obj.values).then(
                                                                        //         ({ success,message }) => {
                                                                        //             if (success) {
                                                                        //                 Msg.success(message);
                                                                        //                 this.tableZGS.refresh();
                                                                        //             } else {
                                                                        //                 Msg.error(message)
                                                                        //             }
                                                                        //         }
                                                                        //     );
                                                                        // }
                                                                    }
                                                                ]
                                                            }
                                                        ]
                                                    }
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'add',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                    formBtns: [
                                                        {
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '取消',
                                                        },
                                                        {
                                                            name: 'submit',
                                                            type: 'primary',
                                                            label: '保存',
                                                            fetchConfig: {
                                                                apiName: 'addZjTzProSubprojectInfo',
                                                                // otherParams: {
                                                                //     projectId
                                                                // }
                                                            },
                                                            // onClick: (obj) => {
                                                            //     this.props.myFetch('addZjTzProSubprojectInfo',obj.values).then(
                                                            //         ({ success,message }) => {
                                                            //             if (success) {
                                                            //                 Msg.success(message);
                                                            //                 this.tableZGS.refresh();
                                                            //             } else {
                                                            //                 Msg.error(message)
                                                            //             }
                                                            //         }
                                                            //     );
                                                            // }
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjTzProSubprojectInfo',
                                                    },
                                                }
                                            ]
                                        }
                                    },
                                ]
                            }
                        },
                        // 项目公司 TAB
                        {
                            field: "form3",
                            name: "qnnForm",
                            title: "项目公司",
                            disabled: (args) => {
                                return args.form.getFieldValue("projectId") || args.clickCb.selectedRows[0]?.["projectId"] ? false : true
                            },
                            content: {
                                fetchConfig: (obj) => {
                                    if (this.state.projectId) {
                                        return {
                                            apiName: 'getZjTzProManageDetails',
                                            otherParams: {
                                                projectId: this.state.projectId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [
                                    {
                                        type: 'component',
                                        field: 'diyGSXX',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    项目公司信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "项目公司名称",
                                        field: "company1",
                                        editDisabled: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "date",
                                        editDisabled: true,
                                        label: "项目公司成立时间",
                                        field: "company2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "项目公司注册地",
                                        editDisabled: true,
                                        field: "company3",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "注册资本金（元）",
                                        editDisabled: true,
                                        field: "company4",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "项目资本金比例%",
                                        editDisabled: true,
                                        field: "company5",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "我方资本金（自有资金）（元） ",
                                        editDisabled: true,
                                        field: "company6",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "一公局占整个项目的股比%",
                                        editDisabled: true,
                                        field: "company7",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "我方施工比例%",
                                        editDisabled: true,
                                        field: "company8",
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "中交方占整个项目的股比%",
                                        editDisabled: true,
                                        field: "company9",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        editDisabled: true,
                                        label: "股权情况",
                                        field: "company10",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyGQJG',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    项目公司股权结构（股东名称及股比）
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableGd",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 0 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        qnnTableConfig: {
                                            tabs: [],
                                            fetchConfig: (obj) => {
                                                if (this.state.projectId) {
                                                    return {
                                                        apiName: 'getZjTzProShareholderInfoList',
                                                        otherParams: {
                                                            projectId: this.state.projectId
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZjTzProShareholderInfoList',
                                                        otherParams: {
                                                            projectId: ''
                                                        }
                                                    }
                                                }
                                                // return {
                                                //     apiName: 'getZjTzProShareholderInfoList',
                                                //     otherParams: {
                                                //         projectId
                                                //     }
                                                // }
                                            },
                                            antd: {
                                                rowKey: 'shareholderInfoId',
                                                size: 'small'
                                            },
                                            limit: 999999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableGd = me;
                                            },
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: false,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "关联的主键id",
                                                        field: "shareholderInfoId",
                                                        type: 'string',
                                                        hide: true
                                                    },
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "",
                                                        field: "projectId",
                                                        type: 'string',
                                                        hide: true,
                                                        initialValue: (obj) => {
                                                            if (this.state.projectId) {
                                                                return this.state.projectId
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    },
                                                },

                                                {
                                                    table: {
                                                        title: '股东名称',
                                                        dataIndex: 'shareholderName',
                                                        key: 'shareholderName'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        field: 'shareholderName',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '股东类型',
                                                        dataIndex: 'shareholderType',
                                                        key: 'shareholderType',
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: 'shareholderType',
                                                        placeholder: '请输入',
                                                        optionData: [
                                                            {
                                                                label: "公司内",
                                                                value: "01"
                                                            },
                                                            {
                                                                label: "公司外",
                                                                value: "02"
                                                            },
                                                        ],
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '股比',
                                                        dataIndex: 'proportion',
                                                        key: 'proportion'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'proportion',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '施工比例',
                                                        dataIndex: 'constructionProportion',
                                                        key: 'constructionProportion'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'constructionProportion',
                                                        placeholder: '请输入',
                                                        max: 999
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '出资额（元）',
                                                        dataIndex: 'contributionAmount',
                                                        key: 'contributionAmount'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'contributionAmount',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         title: "操作",
                                                //         fixed: 'right',
                                                //         dataIndex: 'action',
                                                //         key: 'action',
                                                //         align: "center",
                                                //         noHaveSearchInput: true,
                                                //         showType: "tile",
                                                //         width: 80,
                                                //         btns: [
                                                //             {
                                                //                 name: 'edit',
                                                //                 render: function (rowData) {
                                                //                     let btnField = rowData.qnnFormProps.qnnformData.qnnFormProps.clickCb.rowInfo.field;
                                                //                     if (btnField === 'addOutBtn' || btnField === 'editOutBtn') {
                                                //                         return '<a>修改</a>';
                                                //                     } else {
                                                //                         return ''
                                                //                     }
                                                //                 },
                                                //                 formBtns: [
                                                //                     {
                                                //                         name: 'cancel',
                                                //                         type: 'dashed',
                                                //                         label: '取消',
                                                //                     },
                                                //                     {
                                                //                         name: 'submit',
                                                //                         type: 'primary',
                                                //                         label: '保存',
                                                //                         fetchConfig: {
                                                //                             apiName: 'updateZjTzProShareholderInfo',
                                                //                         }
                                                //                     }
                                                //                 ]
                                                //             }
                                                //         ]
                                                //     }
                                                // }
                                            ],
                                            actionBtns: [
                                                // {
                                                //     name: 'add',
                                                //     icon: 'plus',
                                                //     type: 'primary',
                                                //     label: '新增',
                                                //     formBtns: [
                                                //         {
                                                //             name: 'cancel',
                                                //             type: 'dashed',
                                                //             label: '取消',
                                                //         },
                                                //         {
                                                //             name: 'submit',
                                                //             type: 'primary',
                                                //             label: '保存',
                                                //             fetchConfig: {
                                                //                 apiName: 'addZjTzProShareholderInfo',
                                                //                 // otherParams: {
                                                //                 //     projectId
                                                //                 // }
                                                //             }
                                                //         }
                                                //     ]
                                                // },
                                                // {
                                                //     name: 'del',
                                                //     icon: 'delete',
                                                //     type: 'danger',
                                                //     label: '删除',
                                                //     fetchConfig: {
                                                //         apiName: 'batchDeleteUpdateZjTzProShareholderInfo',
                                                //     },
                                                // }
                                            ]
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyYF',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    回购协议详情
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableWB",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 0 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        },
                                        qnnTableConfig: {
                                            tabs: [],
                                            fetchConfig: (obj) => {
                                                if (this.state.projectId) {
                                                    return {
                                                        apiName: 'getZjTzProRebuyInfoList',
                                                        otherParams: {
                                                            projectId: this.state.projectId
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZjTzProRebuyInfoList',
                                                        otherParams: {
                                                            projectId: ''
                                                        }
                                                    }
                                                }
                                                // return {
                                                //     apiName: 'getZjTzProRebuyInfoList',
                                                //     otherParams: {
                                                //         projectId
                                                //     }
                                                // }
                                            },
                                            antd: {
                                                rowKey: 'rebuyInfoId',
                                                size: 'small'
                                            },
                                            limit: 999999,
                                            curPage: 1,
                                            paginationConfig: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableHgMx = me;
                                            },
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: false,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "关联的主键id",
                                                        field: "rebuyInfoId",
                                                        type: 'string',
                                                        hide: true
                                                    },
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        title: "",
                                                        field: "projectId",
                                                        type: 'string',
                                                        hide: true,
                                                        initialValue: (obj) => {
                                                            if (this.state.projectId) {
                                                                return this.state.projectId
                                                            } else {
                                                                return ''
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '协议约定次数',
                                                        dataIndex: 'appointNumber',
                                                        key: 'appointNumber'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'appointNumber',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '协议约定金额（元）',
                                                        dataIndex: 'appointAmount',
                                                        key: 'appointAmount'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        field: 'appointAmount',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '协议约定时间',
                                                        dataIndex: 'appointDate',
                                                        key: 'appointDate',
                                                        render: (data) => {
                                                            if (data) {
                                                                return moment(data).format('YYYY-MM-DD')
                                                            } else {
                                                                return ''
                                                            }

                                                        }
                                                    },
                                                    form: {
                                                        type: 'year',
                                                        field: 'appointDate',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         title: "操作",
                                                //         fixed: 'right',
                                                //         dataIndex: 'action',
                                                //         key: 'action',
                                                //         align: "center",
                                                //         noHaveSearchInput: true,
                                                //         showType: "tile",
                                                //         width: 80,
                                                //         btns: [
                                                //             {
                                                //                 name: 'edit',
                                                //                 render: function (rowData) {
                                                //                     let btnField = rowData.qnnFormProps.qnnformData.qnnFormProps.clickCb.rowInfo.field;
                                                //                     if (btnField === 'addOutBtn' || btnField === 'editOutBtn') {
                                                //                         return '<a>修改</a>';
                                                //                     } else {
                                                //                         return ''
                                                //                     }
                                                //                 },
                                                //                 formBtns: [
                                                //                     {
                                                //                         name: 'cancel',
                                                //                         type: 'dashed',
                                                //                         label: '取消',
                                                //                     },
                                                //                     {
                                                //                         name: 'submit',
                                                //                         type: 'primary',
                                                //                         label: '保存',
                                                //                         fetchConfig: {
                                                //                             apiName: 'updateZjTzProRebuyInfo',
                                                //                         }
                                                //                     }
                                                //                 ]
                                                //             }
                                                //         ]
                                                //     }
                                                // }
                                            ],
                                            actionBtns: [
                                                // {
                                                //     name: 'add',
                                                //     icon: 'plus',
                                                //     type: 'primary',
                                                //     label: '新增',
                                                //     formBtns: [
                                                //         {
                                                //             name: 'cancel',
                                                //             type: 'dashed',
                                                //             label: '取消',
                                                //         },
                                                //         {
                                                //             name: 'submit',
                                                //             type: 'primary',
                                                //             label: '保存',
                                                //             fetchConfig: {
                                                //                 apiName: 'addZjTzProRebuyInfo',
                                                //                 // otherParams: {
                                                //                 //     projectId
                                                //                 // }
                                                //             }
                                                //         }
                                                //     ]
                                                // },
                                                // {
                                                //     name: 'del',
                                                //     icon: 'delete',
                                                //     type: 'danger',
                                                //     label: '删除',
                                                //     fetchConfig: {
                                                //         apiName: 'batchDeleteUpdateZjTzProRebuyInfo',
                                                //     },
                                                // }
                                            ]
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyCY',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    项目成员
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "公司经理/电话",
                                        editDisabled: true,
                                        field: "member1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "公司总会/电话",
                                        editDisabled: true,
                                        field: "member2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "公司经营负责人/电话",
                                        field: "member3",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        },
                                    },
                                    {
                                        type: "string",
                                        label: "报表负责人/电话",
                                        field: "member4",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyGSZL',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    公司资料
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "法定代表人",
                                        editDisabled: true,
                                        field: "legalPersonName",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '营业执照',
                                        hide: false,
                                        field: 'zjTzBylawFileList',
                                        type: 'files',

                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload'
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        label: '公司章程',
                                        hide: false,
                                        field: 'zjTzLicenseFileList',
                                        type: 'files',

                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload'
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyKZ',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    业务属性扩展
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '附件',
                                        field: 'zjTzFileList',
                                        type: 'files',
                                        editDisabled: true,
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '项目'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;