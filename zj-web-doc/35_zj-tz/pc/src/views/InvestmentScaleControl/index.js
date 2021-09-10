import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg } from 'antd';
const config = {

    antd: {
        rowKey: function (row) {
            return row.sizeControlId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
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
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName : '',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            sizeControlSubject:props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.sizeControlSubject : '',
            zcbShare: '',
            investId: '',
            juShare: ''
        }
    }
    componentDidMount() {
        const { myFetch } = this.props;
        const { proNameId } = this.state;
        let me = this;
        // 字段赋值
        myFetch('getZjTzProManageList', {}).then(
            ({ data, success, message }) => {
                if (success) {
                    data.forEach(function (item, index) {
                        if (item.projectId === proNameId) {
                            me.setState({
                                zcbShare: item.company8,
                                investId: item.investPatten,
                                juShare: item.company7
                            })
                        }
                    })
                } else {
                }
            }
        );
    }
    render() {
        const { proNameVal, proNameId, zcbShare, investId, juShare, sizeControlSubject } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZjTzSizeControlList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'sizeControlId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable:ext1 === '4' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render:(data) => {
                                    if(data === '0'){
                                        return '有更新';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable:ext1 === '3' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render:(data) => {
                                    if(data === '0'){
                                        return '有更新';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable:ext1 === '2' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render:(data) => {
                                    if(data === '0'){
                                        return '有更新';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable:ext1 === '1' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render:(data) => {
                                    if(data === '0'){
                                        return '有更新';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规模控制主体',
                                dataIndex: 'sizeControlSubject',
                                key: 'sizeControlSubject',
                                width: 120,
                                fixed: 'left',
                                render:(data) => {
                                    if(data === '0'){
                                        return '项目整体';
                                    }else if(data === '1'){
                                        return '单个子项目';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                width: 200,
                                fixed: 'left',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                },
                            },
                            isInForm: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '子项目名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'subprojectName',
                                key: 'subprojectName',
                                fixed: 'left'
                            },
                            isInForm: false,
                            form: {
                                field: 'subprojectName',
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '最新变动次数',
                                width: 130,
                                dataIndex: 'changeNumber',
                                key: 'changeNumber',
                                fixed: 'left',
                                render: (data) => {
                                    let num = '';
                                    if (data) {
                                        if (data === 0) {
                                            num = '第0次';
                                            return num
                                        } else {
                                            num = '第' + data + '次';
                                            return num
                                        }
                                    } else {
                                        return '第0次'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                field: 'changeNumber',
                                type: 'number',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: "第0次",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'zeroChangePropertyName',
                                        title: "变动属性",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'zeroChangePropertyName',
                                        type: "string"
                                    },
                                    {
                                        title: '投资规模（万元）',
                                        dataIndex: 'zeroAmount1',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'zeroAmount1',
                                        render: "bind:_divide::10000::2",
                                    },
                                    {
                                        title: '建安费（万元）',
                                        dataIndex: 'zeroAmount2',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'zeroAmount2',
                                        render: "bind:_divide::10000::2",
                                    },
                                    {
                                        title: '查缺补漏（万元）',
                                        dataIndex: 'zeroAmount3',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'zeroAmount3',
                                        render: "bind:_divide::10000::2",
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "上次变动",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'lastChangePropertyName',
                                        title: "变动属性",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'lastChangePropertyName',
                                        type: "string"
                                    },
                                    {
                                        title: '投资规模（万元）',
                                        dataIndex: 'lastAmount1',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'lastAmount1',
                                        render: "bind:_divide::10000::2",
                                    },
                                    {
                                        title: '建安费（万元）',
                                        dataIndex: 'lastAmount2',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'lastAmount2',
                                        render: "bind:_divide::10000::2",
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "本次变动",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'changePropertyName',
                                        title: "变动属性",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'changePropertyName',
                                        type: "string"
                                    },
                                    {
                                        title: '投资规模（万元）',
                                        dataIndex: 'amount1',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'amount1',
                                        render: "bind:_divide::10000::2",
                                        style: (data, rowData) => {
                                            if (data) {
                                                if (data === rowData.lastAmount1) {
                                                    return {

                                                    }
                                                } else {
                                                    return {
                                                        color: 'red'
                                                    }
                                                }

                                            } else {
                                                return {

                                                }
                                            }
                                        },

                                    },
                                    {
                                        title: '建安费（万元）',
                                        dataIndex: 'amount2',
                                        type: "number",
                                        width: 130,
                                        noHaveSearchInput: true,
                                        key: 'amount2',
                                        style: (data, rowData) => {
                                            if (data) {
                                                if (data === rowData.lastAmount2) {
                                                    return {

                                                    }
                                                } else {
                                                    return {
                                                        color: 'red'
                                                    }
                                                }

                                            } else {
                                                return {

                                                }
                                            }
                                        },
                                        render: "bind:_divide::10000::2",
                                    }
                                ]
                            },
                            isInForm: false
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
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>详细信息</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { sizeControlId, subprojectInfoId, projectName, projectId, addFlag } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}InvestmentScaleControlDetail/${sizeControlId}/${subprojectInfoId}/${projectName}/${projectId}/${addFlag}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (proNameId) {
                                if (proNameId === 'all') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                                } else {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                            }
                        }
                    }}
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
                    tabs={[
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "投资规模",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZjTzSizeControlDetails',
                                            otherParams: {
                                                sizeControlId: obj.clickCb.rowData.sizeControlId
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
                                        field: "sizeControlId", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "radio",
                                        label: "规模控制主体",
                                        field: "sizeControlSubject",
                                        placeholder: "请输入",
                                        initialValue: sizeControlSubject,
                                        disabled:true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 4 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 20 }
                                            }
                                        },
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
                                        type: "string",
                                        label: "项目名称",
                                        field: "projectName",
                                        placeholder: "请输入",
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: proNameVal
                                    },
                                    {
                                        label: '',
                                        field: 'projectId',
                                        type: 'string',
                                        initialValue: proNameId,
                                        hide: true
                                    },
                                    {
                                        type: "select",
                                        label: "子项目名称",
                                        field: "subprojectInfoId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'subprojectName',
                                            value: 'subprojectInfoId'
                                        },
                                        fetchConfig: {
                                            apiName: "getZjTzProSubprojectInfoList",
                                            otherParams: {
                                                projectId: proNameId
                                            }
                                        }
                                    },
                                    {
                                        type: "number",
                                        label: "变动次数",
                                        addDisabled: true,
                                        editDisabled: true,
                                        field: "changeNumber",
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: 0
                                    },
                                    {
                                        type: "select",
                                        required: true,
                                        label: "变动属性",
                                        field: "changePropertyId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'BianDongShuXing'
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "投资规模（元）",
                                        required: true,
                                        field: "amount1",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    }, {
                                        type: "number",
                                        label: "建安费（元）",
                                        required: true,
                                        field: "amount2",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    },
                                    {
                                        type: "number",
                                        label: "查缺补漏（元）",
                                        field: "amount3",
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        //表单项布局
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    },
                                    {
                                        type: "radio",
                                        label: "是否二次谈判",
                                        field: "secondNegotiateId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
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
                                        type: "textarea",
                                        label: "查缺补漏方案",
                                        field: "scheme",
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    },
                                    {
                                        label: '附件',
                                        field: 'schemeFileList',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        schemeFileList: []
                                                    })
                                                }, 200)
                                            }
                                        }
                                    },
                                    {
                                        type: "radio",
                                        label: "三会批复",
                                        field: "thirdReplyId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
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
                                        label: '附件',
                                        field: 'thirdReplyFileList',
                                        type: 'files',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        thirdReplyFileList: []
                                                    })
                                                }, 200)
                                            }
                                        }
                                    },
                                    {
                                        type: "radio",
                                        label: "地方政府批复",
                                        field: "localGovId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
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
                                        label: '附件',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        field: 'localGovFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        localGovFileList: []
                                                    })
                                                }, 200)
                                            }
                                        }
                                    },
                                    {
                                        type: 'textarea',
                                        label: '备注',
                                        field: 'remarks',
                                        placeholder: '请输入',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 21 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 21 },
                                                sm: { span: 20 }
                                            }
                                        }
                                    },
                                    {
                                        field: 'jurisdiction',
                                        type: "string",
                                        label: "投资事业部权限",
                                        hide: true,
                                        initialValue: (obj) => {
                                            let ext1 = obj.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;
                                            if (obj.Pstate.drawerDetailTitle === '详情') {
                                                ext1 = '99';
                                            } else {
                                                // 2  4  不可编辑
                                                if (ext1 === '2' || ext1 === '4') {
                                                    ext1 = '99'
                                                }
                                            }
                                            return ext1
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diyGSXX',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                                    投资事业部上传内容
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "radio",
                                        label: "一公局集团批复",
                                        field: "juId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
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
                                        condition: [
                                            {
                                                regex: { jurisdiction: '99', },
                                                action: 'disabled', //disabled, show, hide, function(){}
                                            },
                                        ],
                                        // hide: (obj) => {
                                        //     var jurisdiction = obj.form.getFieldValue('jurisdiction');
                                        //     if (jurisdiction === '1') {
                                        //         return false 
                                        //     } else {
                                        //         return true
                                        //     }
                                        // },
                                        // dependencies: ["jurisdiction"]
                                    },
                                    {
                                        label: '附件',
                                        field: 'juFileList',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: { jurisdiction: '99', },
                                                action: 'disabled', //disabled, show, hide, function(){}
                                            },
                                        ],
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        juFileList: []
                                                    })
                                                }, 200)
                                            }
                                        }
                                    },
                                    {
                                        type: "radio",
                                        label: "中国交建批复",
                                        field: "chinaId",
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
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
                                        condition: [
                                            {
                                                regex: { jurisdiction: '99', },
                                                action: 'disabled', //disabled, show, hide, function(){}
                                            },
                                        ],
                                    },
                                    {
                                        label: '附件',
                                        field: 'chinaFileList',
                                        type: 'files',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: { jurisdiction: '99', },
                                                action: 'disabled', //disabled, show, hide, function(){}
                                            },
                                        ],
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        chinaFileList: []
                                                    })
                                                }, 200)
                                            }
                                        }
                                    },
                                    {
                                        field: 'registerDate',
                                        type: 'date',
                                        label: '登记日期',
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: () => {
                                            return new Date()
                                        },
                                        // condition:[
                                        //     {
                                        //         regex: {jurisdiction:'99',},
                                        //         action: 'disabled', //disabled, show, hide, function(){}
                                        //     },
                                        //  ],
                                    },
                                    {
                                        field: 'registrant',
                                        type: 'string',
                                        label: '登记用户',
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: (obj) => {
                                            return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                        },
                                        // condition:[
                                        //     {
                                        //         regex: {jurisdiction:'99',},
                                        //         action: 'disabled', //disabled, show, hide, function(){}
                                        //     },
                                        //  ],
                                    }
                                ]
                            }
                        },
                        {
                            field: "form3",
                            name: "qnnForm",
                            title: "合同条件",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZjTzSizeControlDetails',
                                            otherParams: {
                                                sizeControlId: obj.clickCb.rowData.sizeControlId
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
                                        field: "contractConditionId",
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "项目名称",
                                        field: "projectName",
                                        placeholder: "请输入",
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: proNameVal
                                    },
                                    {
                                        label: '',
                                        field: 'projectId',
                                        type: 'select',
                                        initialValue: proNameId,
                                        hide: true,
                                        optionConfig: {
                                            label: 'projectName',
                                            value: 'projectId'
                                        },
                                        fetchConfig: {
                                            apiName: "getZjTzProManageList"
                                        },
                                        placeholder: '请选择',
                                    },
                                    {
                                        field: 'registerDate1',
                                        type: 'date',
                                        label: '登记日期',
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: () => {
                                            return new Date()
                                        }
                                    },
                                    {
                                        field: 'registrant1',
                                        type: 'string',
                                        label: '登记用户',
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        initialValue: (obj) => {
                                            return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "投资收益模式",
                                        required: true,
                                        field: "investId",
                                        initialValue: investId,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        type: "number",
                                        label: "一公局集团股比",
                                        required: true,
                                        field: "juShare",
                                        initialValue: juShare,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    },
                                    {
                                        type: "select",
                                        label: "一公局集团控制性地位",
                                        field: "juId1",
                                        placeholder: "请输入",
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'yiGongJuJiTuanKongZhiXingDiWei'
                                            }
                                        }
                                    },
                                    {
                                        type: "select",
                                        label: "总承包结算模式",
                                        field: "zcbId",
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'ZongChengBaoJieSuanMoShi'
                                            }
                                        }
                                    },
                                    {
                                        type: "number",
                                        label: "施工总承包比例",
                                        field: "zcbShare",
                                        initialValue: zcbShare,
                                        required: true,
                                        placeholder: "请输入",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 16 }
                                            }
                                        },
                                    },
                                    {
                                        type: "textarea",
                                        label: "设计管理情况",
                                        field: "ext1",
                                        required: true,
                                        placeholder: "请输入",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 20 }
                                            }
                                        }
                                    },
                                    {
                                        type: "textarea",
                                        label: "合同对投资规模控制的要求",
                                        field: "ext2",
                                        placeholder: "请输入",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 20 }
                                            }
                                        },
                                    },
                                    {
                                        type: "textarea",
                                        label: "设计变更特殊条款",
                                        field: "ext3",
                                        placeholder: "请输入",
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 20 }
                                            }
                                        },
                                    },
                                    {
                                        label: '附件',
                                        field: 'zjTzFileList',
                                        type: 'files',
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                        
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 20 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 20 },
                                                sm: { span: 20 }
                                            }
                                        },
                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        zjTzFileList: []
                                                    })
                                                }, 200)
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