import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;

const config = {
    antd: {
        rowKey: function (row) {
            return row.sizeControlRecordId
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
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sizeControlId: props.match.params.sizeControlId || '',
            subprojectInfoId: props.match.params.subprojectInfoId || '',
            proNameVal: props.match.params.projectName || '',
            proNameId: props.match.params.proManageId || '',
            addFlag: props.match.params.addFlag || '',
            zcbShare: '',
            investId: '',
            juShare: ''
        }
    }
    componentDidMount () {
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
    render () {
        const { sizeControlId, proNameVal, proNameId, subprojectInfoId, zcbShare, investId, juShare } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzSizeControlRecordList',
                        otherParams: {
                            sizeControlId: sizeControlId,
                            projectId: proNameId
                        },
                        success: (val) => {
                            if (val.data && val.data[0] && val.data[0].addFlag) {
                                this.setState({
                                    addFlag: val.data[0].addFlag
                                })
                            }

                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                    method={{
                        addClick: (obj) => {
                            if (this.state.addFlag === 'false') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('存在未上报/被退回的数据，不允许新增数据！')
                            } else {

                            }
                        },
                        editClick: (obj) => {
                            let ext1 = obj.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;
                            if (obj.selectedRows[0].changeNumber === (obj.selectedRows[0].count - 1)) {
                                if (obj.selectedRows[0].statusId === '1' || obj.selectedRows[0].statusId === '3') {//已上报数据
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('只允许修改未上报/被退回的数据!');
                                    this.tableGM.clearSelectedRows();
                                } else {
                                    this.tableGM.clearSelectedRows();
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('只允许修改最新变动数据!');
                                this.tableGM.clearSelectedRows();
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch, myPublic: { appInfo: { mainModule } } } = this.props;
                            if (obj.selectedRows.length > 0) {
                                let count = obj.selectedRows[0].count;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].statusId === '1' || obj.selectedRows[0].statusId === '3') {
                                        Msg.warn('请选择未上报/被退回的数据！')
                                    } else {
                                        // 删除最新的数据
                                        if (obj.selectedRows[0].changeNumber === (obj.selectedRows[0].count - 1)) {
                                            confirm({
                                                title: "确定删除么?",
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    myFetch('batchDeleteUpdateZjTzSizeControlRecord', obj.selectedRows).then(
                                                        ({ success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                if (count === 1) {
                                                                    obj.props.dispatch(
                                                                        push(`${mainModule}InvestmentScaleControl`)
                                                                    )
                                                                } else {
                                                                    this.tableGM.refresh();
                                                                    this.tableGM.clearSelectedRows();
                                                                }
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        } else {
                                            Msg.warn('只允许删除最新变动数据!');
                                        }
                                    }
                                } else {
                                    Msg.warn('删除时只能选择一条数据!');
                                }
                            } else {
                                Msg.warn('请选择数据!');
                            }

                        },
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}InvestmentScaleControl`)
                            )
                        },
                        // 上报---
                        shangBaoClick1: (obj) => {
                            this.tableGM.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已上报的消息不能上报！');
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('singleReleaseZjTzSizeControlRecord', obj.selectedRows[0]).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择一条数据！');
                            }
                        },
                        shangBaoClick2: (obj) => {
                            this.tableGM.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1' || obj.selectedRows[i].statusId === '3') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已上报的消息不能上报！');
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('singleReleaseZjTzSizeControlRecord', obj.selectedRows[0]).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择一条数据！');
                            }
                        },
                        shenHe: (obj) => {
                            this.tableGM.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                confirm({
                                    title: "确定审查通过么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('checkAndFinishZjTzSizeControlRecord', obj.selectedRows[0]).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.tableGM.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                });
                            } else {
                                Msg.warn('请选择一条数据！');
                            }
                        },
                        // 退回
                        tuiHuiClick: (obj) => {
                            this.tableGM.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length === 1) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // 未上报/被退回
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未上报/被退回的消息不能退回！');
                                } else {
                                    confirm({
                                        title: "确定退回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('singleRecallZjTzSizeControlRecord', obj.selectedRows[0]).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }

                            } else {
                                Msg.warn('请选择一条数据！');
                            }

                        },
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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'sizeControlRecordId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'sizeControlId',
                                type: 'string',
                                initialValue: sizeControlId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: ext1 === '4' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '3' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '2' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '1' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                onClick: 'detail',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                                fixed: 'left',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                },
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '子项目名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'subprojectName',
                                key: 'subprojectName',
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '变动次数',
                                width: 120,
                                dataIndex: 'changeNumber',
                                key: 'changeNumber',
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
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '变动属性',
                                width: 130,
                                tooltip: 23,
                                dataIndex: 'changePropertyName',
                                key: 'changePropertyName'
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>投资规模<br>（万元）</div>',
                                width: 120,
                                dataIndex: 'amount1',
                                key: 'amount1',
                                render: "bind:_divide::10000::2"
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>总投资变动差值<br>（万元）</div>',
                                dataIndex: 'dvalue1',
                                width: 140,
                                key: 'dvalue1',
                                render: "bind:_divide::10000::2",
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>建安费<br>（万元）</div>',
                                width: 120,
                                dataIndex: 'amount2',
                                key: 'amount2',
                                render: "bind:_divide::10000::2",
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>建安费变动差值<br>（万元）</div>',
                                dataIndex: 'dvalue2',
                                width: 140,
                                key: 'dvalue2',
                                render: "bind:_divide::10000::2",
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>查缺补漏<br>（万元）</div>',
                                width: 120,
                                dataIndex: 'amount3',
                                key: 'amount3',
                                render: "bind:_divide::10000::2",
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '查缺补漏处置方案',
                                dataIndex: 'scheme',
                                width: 140,
                                tooltip: 23,
                                key: 'scheme',
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '一公局集团批复',
                                width: 130,
                                dataIndex: 'juName',
                                key: 'juName',
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>中国交建<br>批复</div>',
                                width: 120,
                                dataIndex: 'chinaName',
                                key: 'chinaName',
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '<div>项目三会<br>是否通过</div>',
                                width: 120,
                                dataIndex: 'thirdReplyName',
                                key: 'thirdReplyName',
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '政府批复',
                                width: 120,
                                dataIndex: 'localGovName',
                                key: 'localGovName',
                            },
                            isInForm: false,
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '状态',
                                filter: true,
                                width: 120,
                                fixed: 'right',
                                dataIndex: 'statusId',
                                key: 'statusId',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'statusId',
                                optionData: [
                                    {
                                        label: "未上报",
                                        value: "0"
                                    },
                                    {
                                        label: "已上报",
                                        value: "1"
                                    },
                                    {
                                        label: "被退回",
                                        value: "2"
                                    },
                                    {
                                        label: "托管项目上报",
                                        value: "3"
                                    }
                                ]
                            }
                        }
                    ]}
                    tabs={[
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "投资规模",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZjTzSizeControlRecordDetails',
                                            otherParams: {
                                                sizeControlRecordId: obj.clickCb.rowData.sizeControlRecordId
                                            }
                                        }
                                    } else if (obj.clickCb.selectedRows.length > 0) {
                                        return {
                                            apiName: 'getZjTzSizeControlRecordDetails',
                                            otherParams: {
                                                sizeControlRecordId: obj.clickCb.selectedRows[0].sizeControlRecordId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [

                                    {
                                        field: 'sizeControlId',
                                        type: 'string',
                                        initialValue: sizeControlId,
                                        hide: true,
                                    },
                                    {
                                        field: 'sizeControlRecordId',
                                        type: 'string',
                                        hide: true,
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
                                        type: "string",
                                        label: "",
                                        hide: true,
                                        field: "projectId",
                                        placeholder: "请输入",
                                        initialValue: proNameId,
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        type: "select",
                                        label: "子项目名称",
                                        addDisabled: true,
                                        editDisabled: true,
                                        field: "subprojectInfoId",
                                        placeholder: "请输入",
                                        span: 12,
                                        initialValue: subprojectInfoId,
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
                                        editDisabled: true,
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
                                        }
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        label: '附件',
                                        field: 'schemeFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        schemeFileList: []
                                                    })
                                                }, 200)
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
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        label: '附件',
                                        field: 'thirdReplyFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        thirdReplyFileList: []
                                                    })
                                                }, 200)
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
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        label: '附件',
                                        field: 'localGovFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        field: 'otherInput',
                                        type: "string",
                                        label: "已上报、投资事业部登录、其他项不可编辑",
                                        hide: true,
                                        initialValue: (obj) => {
                                            if (obj.Pstate.drawerDetailTitle === '详情') {
                                                return 'noEdit'
                                            } else if (obj.Pstate.drawerDetailTitle === '编辑') {
                                                if (obj.clickCb.selectedRows[0].statusId === '1') {
                                                    return 'noEdit'
                                                } else {
                                                    return 'canEdit'
                                                }
                                            } else {//新增
                                                let ext1 = obj.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;
                                                if (ext1 === '1') {
                                                    return 'noEdit'
                                                } else {
                                                    return 'canEdit'
                                                }
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
                                    },
                                    {
                                        label: '附件',
                                        field: 'juFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        juFileList: []
                                                    })
                                                }, 200)
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
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        chinaFileList: []
                                                    })
                                                }, 200)
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
                                        }
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
                                        }
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
                                            apiName: 'getZjTzSizeControlRecordDetails',
                                            otherParams: {
                                                sizeControlRecordId: obj.clickCb.rowData.sizeControlRecordId
                                            }
                                        }
                                    } else if (obj.clickCb.selectedRows.length > 0) {
                                        return {
                                            apiName: 'getZjTzSizeControlRecordDetails',
                                            otherParams: {
                                                sizeControlRecordId: obj.clickCb.selectedRows[0].sizeControlRecordId
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                formConfig: [
                                    {
                                        field: 'sizeControlId',
                                        type: 'string',
                                        initialValue: sizeControlId,
                                        hide: true,
                                    },
                                    {
                                        field: 'sizeControlRecordId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        type: "string",
                                        label: "主键ID",//???
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
                                        type: "string",
                                        label: "",
                                        hide: true,
                                        field: "projectId",
                                        placeholder: "请输入",
                                        initialValue: proNameId
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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        type: "number",
                                        label: "一公局集团股比",
                                        required: true,
                                        initialValue: juShare,
                                        field: "juShare",
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        },
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
                                    },
                                    {
                                        label: '附件',
                                        field: 'zjTzFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload',
                                            otherParams: {
                                                name: '投资规模控制'
                                            }
                                        },
                                        showDownloadIcon: true,//是否显示下载按钮
                                        onPreview: "bind:_docFilesByOfficeUrl",//365显示

                                        onChange: (val, rowData) => {
                                            if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                                setTimeout(() => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        zjTzFileList: []
                                                    })
                                                }, 200)
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
                                        condition: [
                                            {
                                                regex: { otherInput: 'noEdit', },
                                                action: 'disabled'
                                            },
                                        ],
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