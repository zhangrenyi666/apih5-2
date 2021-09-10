import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Tabs, Modal } from "antd";
const { TabPane } = Tabs;

const config = {
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
        }
    }
};

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            rowData: null,
            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
            orgName: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyName,
            realName: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
        }
    }
    tab2Id = null
    tab3Id = null
    table2EditStatus = true
    detailTableDataList = [
        {
            key: '0',
            cbType: '自航工程船舶',
        },
        {
            key: '1',
            cbType: '非自航工程船舶'
        },
        {
            key: '2',
            cbType: '工程辅助船舶'
        },
        {
            key: '3',
            cbType: '交通船舶'
        },
        {
            key: '4',
            cbType: '其他',
        }
    ]
    render() {
        const { visible, rowData, orgID, orgName, realName } = this.state;
        return (
            <div>
                <Tabs>
                    <TabPane tab="船舶水上交通事故" key="1">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            antd={
                                {
                                    rowKey: function (row) {
                                        return row.zxSfSWAccidentId
                                    },
                                    size: 'small'
                                }
                            }
                            fetchConfig={{
                                apiName: 'getZxSfSWAccidentList',
                                // otherParams: {
                                //     projectId: projectId
                                // }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    onClick: () => {
                                        // 新增清空 主表id
                                        this.tab2Id = null
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'diySubmit',
                                            field: 'diySubmit',
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                var formData = {
                                                    ...obj._formData,
                                                    orgID: orgID,
                                                    orgName: orgName,
                                                }
                                                this.props.myFetch('addZxSfSWAccident' ,formData).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tab2Id = data.zxSfAccidentId
                                                            obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.success(message);
                                                            obj.btnCallbackFn.refresh();
                                                            obj.btnCallbackFn.form.setFieldsValue(data);
                                                            obj.btnCallbackFn.setActiveKey('1');
                                                        } else {
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.error(message);
                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].isimported === '0') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxSfSWAccident',
                                            },
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxSfSWAccident',
                                    }
                                },
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSfSWAccidentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '填报单位',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        onClick: 'detail',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '期次',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        format: 'YYYY-MM'
                                    }
                                },
                                {
                                    table: {
                                        title: '单位负责人',
                                        dataIndex: 'manager',
                                        key: 'manager',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '处（科）负责人',
                                        dataIndex: 'deManager',
                                        key: 'deManager',
                                        width: 120,
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '填表人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '填表时间',
                                        dataIndex: 'bizDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        key: 'bizDate',
                                        width: 150,
                                    }
                                }
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    qnnTableConfig: {},
                                    content: {
                                        formConfig: [
                                            {
                                                label: '主键id',
                                                field: "zxSfSWAccidentId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '期次',
                                                field: "datePeriod",
                                                type: 'month',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '备注',
                                                field: "notes",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '单位负责人',
                                                field: "manager",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '处（科）负责人',
                                                field: "deManager",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '填表人',
                                                field: "creator",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '填表时间',
                                                field: "bizDate",
                                                type: 'date',
                                                span: 12,
                                            },
                                            {
                                                label: '附件上传',
                                                field: "zxErpFileList",
                                                type: 'files',
                                                span: 12,
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "form2",
                                    name: "qnnForm",
                                    title: "详细",
                                    disabled: function (obj) {
                                        return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfAccidentId"))
                                    },
                                    content: {
                                        formConfig: [
                                            {
                                                type: 'qnnTable',
                                                field: 'table2',
                                                qnnTableConfig: {
                                                    incToForm: true,
                                                    fetchConfig: (obj) => {
                                                        if (obj.props.form.getFieldValue('zxSfSWAccidentId')) {
                                                            return {
                                                                apiName: 'getZxSfSWAccidentItemList',
                                                                otherParams: {
                                                                    accidentId: obj.props.form.getFieldValue('zxSfSWAccidentId')
                                                                }
                                                            }
                                                        } else {
                                                            return {}
                                                        }
                                                    },
                                                    actionBtnsPosition: "top",
                                                    antd: {
                                                        rowKey: 'zxSfAccidentItemId',
                                                        size: 'small'
                                                    },
                                                    paginationConfig: {
                                                        position: 'bottom'
                                                    },
                                                    tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                        // 后台生成主键id 固定长度为32
                                                        if (newRowData.zxSfAccidentItemId.length === 32) {
                                                            this.props.myFetch('updateZxSfAccidentItem', { ...newRowData }).then(
                                                                this.table2.refresh()
                                                            )
                                                        } else {
                                                            this.props.myFetch('addZxSfAccidentItem', { ...newRowData, accidentId: this.tab2Id, zxSfAccidentItemId: null }).then(
                                                                this.table2.refresh()
                                                            )
                                                        }
                                                    },
                                                    wrappedComponentRef: (me) => {
                                                        this.table2 = me;
                                                    },
                                                    formConfig: [
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主键id',
                                                                field: 'zxSfAccidentItemId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主表id',
                                                                field: 'accidentId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '项目id',
                                                                field: 'orgID',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            table: {
                                                                title: '船舶种类',
                                                                dataIndex: 'a1',
                                                                key: 'a1',
                                                                tdEdit: true,
                                                            },
                                                            form: {
                                                                type: 'date',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '统计期内船舶总数（艘）',
                                                                dataIndex: 'a2',
                                                                key: 'a2',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                required: true,
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故件数（合计）',
                                                                dataIndex: 'a3',
                                                                key: 'a3',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故件数（重大）',
                                                                dataIndex: 'a4',
                                                                key: 'a4',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故件数（大）',
                                                                dataIndex: 'a5',
                                                                key: 'a5',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '碰撞',
                                                                dataIndex: 'a6',
                                                                key: 'a6',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '搁浅',
                                                                dataIndex: 'a7',
                                                                key: 'a7',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '触礁',
                                                                dataIndex: 'a8',
                                                                key: 'a8',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '触损',
                                                                dataIndex: 'a9',
                                                                key: 'a9',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '浪损',
                                                                dataIndex: 'a10',
                                                                key: 'a10',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '火灾爆炸',
                                                                dataIndex: 'a11',
                                                                key: 'a11',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '风灾',
                                                                dataIndex: 'a12',
                                                                key: 'a12',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '自沉',
                                                                dataIndex: 'a13',
                                                                key: 'a13',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '其它',
                                                                dataIndex: 'a14',
                                                                key: 'a14',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '受伤',
                                                                dataIndex: 'a15',
                                                                key: 'a15',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '死亡失踪',
                                                                dataIndex: 'a16',
                                                                key: 'a16',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '总艘数（艘）',
                                                                dataIndex: 'a17',
                                                                key: 'a17',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '总吨数（吨）',
                                                                dataIndex: 'a18',
                                                                key: 'a18',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '功率（千瓦）',
                                                                dataIndex: 'a19',
                                                                key: 'a19',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '机动船艘数',
                                                                dataIndex: 'a20',
                                                                key: 'a20',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '机动船总吨数',
                                                                dataIndex: 'a21',
                                                                key: 'a21',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '非机动船艘数',
                                                                dataIndex: 'a22',
                                                                key: 'a22',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '非机动船总吨数',
                                                                dataIndex: 'a23',
                                                                key: 'a23',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故直接经济损失（万元）',
                                                                dataIndex: 'a24',
                                                                key: 'a24',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                    ],
                                                    actionBtns: [
                                                        {
                                                            name: "addRow",
                                                            icon: "plus",
                                                            type: "primary",
                                                            label: "新增行",

                                                            addCb: (rowData) => {

                                                            },
                                                        },
                                                        {
                                                            name: 'del',
                                                            icon: 'delete',
                                                            type: 'danger',
                                                            label: '删除',
                                                            fetchConfig: {//ajax配置
                                                                apiName: 'batchDeleteUpdateZxSfSWAccidentItem',
                                                            }
                                                            // hide: (obj) => {
                                                            //     // if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                            //     //     return true;
                                                            //     // } else {
                                                            //     //     return false;
                                                            //     // }
                                                            // },
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }


                                }
                            ]}
                        >

                        </QnnTable>
                    </TabPane>
                    <TabPane tab="伤亡事故情况统计" key="2">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            antd={
                                {
                                    rowKey: 'zxSfAccidentId',
                                    size: 'small'
                                }
                            }
                            fetchConfig={{
                                apiName: 'getZxSfAccidentList',
                                // otherParams: {
                                //     projectId: projectId
                                // }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    onClick: () => {
                                        // 新增清空 主表id
                                        this.tab2Id = null
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'diySubmit',
                                            field: 'diySubmit',
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                var formData = {
                                                    ...obj._formData,
                                                    orgID: orgID,
                                                    orgName: orgName,
                                                }
                                                this.props.myFetch( 'addZxSfAccident', formData).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tab2Id = data.zxSfAccidentId
                                                            obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.success(message);
                                                            obj.btnCallbackFn.refresh();
                                                            obj.btnCallbackFn.form.setFieldsValue(data);
                                                            obj.btnCallbackFn.setActiveKey('1');
                                                        } else {
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.error(message);
                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].isimported === '0') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxSfAccident',
                                            },
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxSfAccident',
                                    }
                                },
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSfAccidentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '填报单位',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        onClick: 'detail',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '月份',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        type: 'month',
                                        format: 'YYYY-MM'
                                    }
                                },
                                {
                                    table: {
                                        title: '单位负责人',
                                        dataIndex: 'unitManager',
                                        key: 'unitManager',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '处（科）负责人',
                                        dataIndex: 'deManager',
                                        key: 'deManager',
                                        width: 120,
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '制表人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '单位职工平均人数',
                                        dataIndex: 'avgEmpNum',
                                        key: 'avgEmpNum',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '报出日期',
                                        dataIndex: 'bizDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        key: 'bizDate',
                                        width: 150,
                                    }
                                },
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    qnnTableConfig: {},
                                    content: {
                                        formConfig: [
                                            {
                                                label: '主键id',
                                                field: "zxSfAccidentId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '月份',
                                                field: "datePeriod",
                                                type: 'month',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '单位负责人',
                                                field: "unitManager",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '处（科）负责人',
                                                field: "deManager",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '制表人',
                                                field: "creator",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '单位职工平均人数',
                                                field: "avgEmpNum",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '报出日期',
                                                field: "bizDate",
                                                type: 'date',
                                                span: 12,
                                            },
                                            {
                                                label: '附件上传',
                                                field: "zxErpFileList",
                                                type: 'files',
                                                span: 12,
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "form2",
                                    name: "qnnForm",
                                    title: "详细",
                                    disabled: function (obj) {
                                        return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfAccidentId"))
                                    },
                                    content: {
                                        formConfig: [
                                            {
                                                type: 'qnnTable',
                                                field: 'table2',
                                                qnnTableConfig: {
                                                    incToForm: true,
                                                    fetchConfig: (obj) => {
                                                        if (obj.props.form.getFieldValue('zxSfAccidentId')) {
                                                            return {
                                                                apiName: 'getZxSfAccidentItemList',
                                                                otherParams: {
                                                                    accidentId: obj.props.form.getFieldValue('zxSfAccidentId')
                                                                }
                                                            }
                                                        } else {
                                                            return {}
                                                        }
                                                    },
                                                    actionBtnsPosition: "top",
                                                    antd: {
                                                        rowKey: 'zxSfAccidentItemId',
                                                        size: 'small'
                                                    },
                                                    paginationConfig: {
                                                        position: 'bottom'
                                                    },
                                                    tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                        // 后台生成主键id 固定长度为32
                                                        if (newRowData.zxSfAccidentItemId.length === 32) {
                                                            this.props.myFetch('updateZxSfAccidentItem', { ...newRowData }).then(
                                                                this.table2.refresh()
                                                            )
                                                        } else {
                                                            this.props.myFetch('addZxSfAccidentItem', { ...newRowData, accidentId: this.tab2Id, zxSfAccidentItemId: null }).then(
                                                                this.table2.refresh()
                                                            )
                                                        }
                                                    },
                                                    wrappedComponentRef: (me) => {
                                                        this.table2 = me;
                                                    },
                                                    formConfig: [
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主键id',
                                                                field: 'zxSfAccidentItemId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主表id',
                                                                field: 'accidentId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '项目id',
                                                                field: 'orgID',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            table: {
                                                                title: '发生事故时间',
                                                                dataIndex: 'a1',
                                                                key: 'a1',
                                                                tdEdit: true,
                                                            },
                                                            form: {
                                                                type: 'date',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故地点',
                                                                dataIndex: 'a2',
                                                                key: 'a2',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                required: true,
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故单位名称',
                                                                dataIndex: 'a3',
                                                                key: 'a3',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '工程分类及等级、建设类型',
                                                                dataIndex: 'a4',
                                                                key: 'a4',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故原因',
                                                                dataIndex: 'a5',
                                                                key: 'a5',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故类别',
                                                                dataIndex: 'a6',
                                                                key: 'a6',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '伤亡人员姓名',
                                                                dataIndex: 'a7',
                                                                key: 'a7',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '死亡',
                                                                dataIndex: 'a8',
                                                                key: 'a8',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '重伤',
                                                                dataIndex: 'a9',
                                                                key: 'a9',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '轻伤',
                                                                dataIndex: 'a10',
                                                                key: 'a10',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '非本企业人员死亡',
                                                                dataIndex: 'a11',
                                                                key: 'a11',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '非本企业人员重伤',
                                                                dataIndex: 'a12',
                                                                key: 'a12',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '非本企业人员轻伤',
                                                                dataIndex: 'a13',
                                                                key: 'a13',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '直接经济损失（万元）',
                                                                dataIndex: 'a14',
                                                                key: 'a14',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '操作工作日（工日）',
                                                                dataIndex: 'a15',
                                                                key: 'a15',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '事故结案批复单位',
                                                                dataIndex: 'a16',
                                                                key: 'a16',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                    ],
                                                    actionBtns: [
                                                        {
                                                            name: "addRow",
                                                            icon: "plus",
                                                            type: "primary",
                                                            label: "新增行",
                                                            addCb: (rowData) => {

                                                            },
                                                            // hide: (obj) => {
                                                            //     console.log(obj)
                                                            //     // if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                            //     //     return true;
                                                            //     // } else {
                                                            //     //     return false;
                                                            //     // }
                                                            //     return false
                                                            // },
                                                        },
                                                        {
                                                            name: 'del',
                                                            icon: 'delete',
                                                            type: 'danger',
                                                            label: '删除',
                                                            fetchConfig: {//ajax配置
                                                                apiName: 'batchDeleteUpdateZxSfAccidentItem',
                                                            }
                                                            // hide: (obj) => {
                                                            //     // if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                            //     //     return true;
                                                            //     // } else {
                                                            //     //     return false;
                                                            //     // }
                                                            // },
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }


                                }
                            ]}
                        >

                        </QnnTable>
                    </TabPane>
                    <TabPane tab="职工伤亡事故年度统计表" key="3">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}

                            antd={
                                {
                                    rowKey: 'zxSfEAccidentId',
                                    size: 'small'
                                }
                            }
                            fetchConfig={{
                                apiName: 'getZxSfEAccidentList',
                                // otherParams: {
                                //     projectId: projectId
                                // }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    onClick: () => {
                                        // 新增清空 主表id
                                        this.tab3Id = null
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'diySubmit',
                                            field: 'diySubmit',
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                                var formData = {
                                                    ...obj._formData,
                                                    orgId: orgID,
                                                    orgName: orgName,
                                                    creator: realName
                                                }
                                                this.props.myFetch('addZxSfEAccident', formData).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tab3Id = data.zxSfEAccidentId
                                                            obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.success(message);
                                                            obj.btnCallbackFn.refresh();
                                                            obj.btnCallbackFn.form.setFieldsValue(data);
                                                            obj.btnCallbackFn.setActiveKey('1');
                                                        } else {
                                                            obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                            Msg.error(message);
                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].isimported === '0') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();

                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxSfEAccident',
                                            },
                                            hide: (obj) => {
                                                var index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxSfEAccident',
                                    }
                                },
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSfEAccidentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '填表单位',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        onClick: 'detail',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '年份',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        format: 'YYYY-MM'
                                    }
                                },
                                {
                                    table: {
                                        title: '填报人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 120,
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '填报时间',
                                        dataIndex: 'createDate',
                                        key: 'createDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        width: 150
                                    }
                                },
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    qnnTableConfig: {},
                                    content: {
                                        formConfig: [
                                            {
                                                label: '主键id',
                                                field: "zxSfEAccidentItemId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '主表id',
                                                field: "zxSfEAccidentId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '年份',
                                                field: "datePeriod",
                                                type: 'month',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '填报时间',
                                                field: "createDate",
                                                type: 'date',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '附件上传',
                                                field: "zxErpFileList",
                                                type: 'files',
                                                span: 12,
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "form2",
                                    name: "qnnForm",
                                    title: "详细",
                                    disabled: function (obj) {
                                        return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfAccidentId"))
                                    },
                                    content: {
                                        formConfig: [
                                            {
                                                type: 'qnnTable',
                                                field: 'table2',
                                                qnnTableConfig: {
                                                    incToForm: true,
                                                    fetchConfig: (obj) => {
                                                        if (obj.props.form.getFieldValue('zxSfEAccidentId')) {
                                                            return {
                                                                apiName: 'getZxSfEAccidentItemList',
                                                                otherParams: {
                                                                    eaId: obj.props.form.getFieldValue('zxSfEAccidentId')
                                                                }
                                                            }
                                                        } else {
                                                            return {}
                                                        }
                                                    },
                                                    actionBtnsPosition: "top",
                                                    antd: {
                                                        rowKey: 'zxSfEAccidentItemId',
                                                        size: 'small'
                                                    },
                                                    paginationConfig: {
                                                        position: 'bottom'
                                                    },
                                                    tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                        // 后台生成主键id 固定长度为32
                                                        if (newRowData.zxSfEAccidentItemId.length === 32) {
                                                            this.props.myFetch('updateZxSfEAccidentItem', { ...newRowData }).then(
                                                                this.table2.refresh()
                                                            )
                                                        } else {
                                                            this.props.myFetch('addZxSfEAccidentItem', { ...newRowData, eaId: this.tab3Id, zxSfEAccidentItemId: null }).then(
                                                                this.table2.refresh()
                                                            )
                                                        }
                                                    },
                                                    wrappedComponentRef: (me) => {
                                                        this.table2 = me;
                                                    },
                                                    formConfig: [
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主键id',
                                                                field: 'zxSfEAccidentItemId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '主表id',
                                                                field: 'eaId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '机构id',
                                                                field: 'orgId',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            isInTable: false,
                                                            form: {
                                                                label: '项目id',
                                                                field: 'orgID',
                                                                hide: true
                                                            }
                                                        },
                                                        {
                                                            table: {
                                                                title: '单位',
                                                                dataIndex: 'orgName',
                                                                key: 'orgName',
                                                                tdEdit: true,
                                                            },
                                                            form: {
                                                                type: 'select',
                                                                placeholder: '请输入',
                                                                optionConfig: {
                                                                    label: 'companyName',
                                                                    value: 'companyId'
                                                                },
                                                                fetchConfig: {
                                                                    apiName: 'getSysCompanyBySelect'
                                                                },
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '职工平均人数',
                                                                dataIndex: 'a1',
                                                                key: 'a1',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'number',
                                                                required: true,
                                                                placeholder: '请输入',
                                                                onBlur: (e, obj, btnCallbackFn) => {
                                                                    let val = e.target.value;
                                                                    setTimeout(async () => {
                                                                        await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                            editRowData.a2 = editRowData.a4 && editRowData.a5 ? +editRowData.a4 + +editRowData.a5 + +val : 0
                                                                            editRowData.a8 = editRowData.a2 && editRowData.a3 ? (+editRowData.a3 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a9 = editRowData.a2 && editRowData.a4 ? (+editRowData.a4 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a10 = editRowData.a2 && editRowData.a5 ? (+editRowData.a5 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                        })
                                                                    }, 0)
                                                                }
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '伤亡人数合计',
                                                                dataIndex: 'a2',
                                                                key: 'a2',
                                                                tdEdit: true,
                                                            },
                                                            form: {
                                                                type: 'number',
                                                                placeholder: '请输入',
                                                                disabled: true
                                                            }
                                                        },
                                                        {
                                                            table: {
                                                                title: '死亡',
                                                                dataIndex: 'a3',
                                                                key: 'a3',
                                                                tdEdit: true,

                                                            },
                                                            form: {
                                                                type: 'number',
                                                                placeholder: '请输入',
                                                                onBlur: (e, obj, btnCallbackFn) => {
                                                                    let val = e.target.value;
                                                                    setTimeout(async () => {
                                                                        await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                            editRowData.a2 = editRowData.a4 && editRowData.a5 ? +editRowData.a4 + +editRowData.a5 + +val : 0
                                                                            editRowData.a8 = editRowData.a2 && editRowData.a3 ? (+editRowData.a3 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a9 = editRowData.a2 && editRowData.a4 ? (+editRowData.a4 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a10 = editRowData.a2 && editRowData.a5 ? (+editRowData.a5 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                        })
                                                                    }, 0)
                                                                }
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '重伤',
                                                                dataIndex: 'a4',
                                                                key: 'a4',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'number',
                                                                placeholder: '请输入',
                                                                onBlur: (e, obj, btnCallbackFn) => {
                                                                    let val = e.target.value;
                                                                    setTimeout(async () => {
                                                                        await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                            editRowData.a2 = editRowData.a4 && editRowData.a5 ? +editRowData.a4 + +editRowData.a5 + +val : 0
                                                                            editRowData.a8 = editRowData.a2 && editRowData.a3 ? (+editRowData.a3 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a9 = editRowData.a2 && editRowData.a4 ? (+editRowData.a4 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a10 = editRowData.a2 && editRowData.a5 ? (+editRowData.a5 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                        })
                                                                    }, 0)
                                                                }
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '轻伤',
                                                                dataIndex: 'a5',
                                                                key: 'a5',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'number',
                                                                placeholder: '请输入',
                                                                onBlur: (e, obj, btnCallbackFn) => {
                                                                    let val = e.target.value;
                                                                    setTimeout(async () => {
                                                                        await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                            editRowData.a2 = editRowData.a4 && editRowData.a5 ? +editRowData.a4 + +editRowData.a5 + +val : 0
                                                                            editRowData.a8 = editRowData.a2 && editRowData.a3 ? (+editRowData.a3 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a9 = editRowData.a2 && editRowData.a4 ? (+editRowData.a4 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            editRowData.a10 = editRowData.a2 && editRowData.a5 ? (+editRowData.a5 / +editRowData.a2 * 100).toFixed(2) : 0
                                                                            btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                        })
                                                                    }, 0)
                                                                }
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '直接经济损失（万元）',
                                                                dataIndex: 'a6',
                                                                key: 'a6',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '操作工作日（工日）',
                                                                dataIndex: 'a7',
                                                                key: 'a7',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'number',
                                                                placeholder: '请输入',
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '死亡率（‰）',
                                                                dataIndex: 'a8',
                                                                key: 'a8',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                                disabled: true
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '重伤率（‰）',
                                                                dataIndex: 'a9',
                                                                key: 'a9',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                                disabled: true
                                                            },
                                                        },
                                                        {
                                                            table: {
                                                                title: '月负伤频率率（‰）',
                                                                dataIndex: 'a10',
                                                                key: 'a10',
                                                                tdEdit: true
                                                            },
                                                            form: {
                                                                type: 'string',
                                                                placeholder: '请输入',
                                                                disabled: true
                                                            },
                                                        },
                                                    ],
                                                    actionBtns: [
                                                        {
                                                            name: "addRow",
                                                            icon: "plus",
                                                            type: "primary",
                                                            label: "新增行",

                                                            addCb: (rowData) => {
                                                            },
                                                            // hide: (obj) => {
                                                            //     console.log(obj)
                                                            //     // if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                            //     //     return true;
                                                            //     // } else {
                                                            //     //     return false;
                                                            //     // }
                                                            //     return false
                                                            // },
                                                        },
                                                        {
                                                            name: 'del',
                                                            icon: 'delete',
                                                            type: 'danger',
                                                            label: '删除',
                                                            fetchConfig: {//ajax配置
                                                                apiName: 'batchDeleteUpdateZxSfEAccidentItem',
                                                            }
                                                            // hide: (obj) => {
                                                            //     // if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                            //     //     return true;
                                                            //     // } else {
                                                            //     //     return false;
                                                            //     // }
                                                            // },
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }


                                }
                            ]}
                        >

                        </QnnTable>
                    </TabPane>
                    <TabPane tab="事故案例报告" key="4">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            antd={
                                {
                                    rowKey: function (row) {
                                        return row.zxSfOtherAddFileReportId
                                    },
                                    size: 'small'
                                }
                            }
                            fetchConfig={{
                                apiName: 'getZxSfOtherAddFileReportList',
                                // otherParams: {
                                //     projectId: projectId
                                // }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'addZxSfOtherAddFileReport',
                                                otherParams: {
                                                    orgID: orgID,
                                                    orgName: orgName,
                                                    creator: realName
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].isimported === '0') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxSfOtherAddFileReport',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxSfOtherAddFileReport',
                                    }
                                },
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSfOtherAddFileReportId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '名称',
                                        dataIndex: 'title',
                                        key: 'title',
                                        onClick: 'detail',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '机构名称',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        width: 100,
                                    }
                                },
                                {
                                    table: {
                                        title: '备注',
                                        dataIndex: 'notes',
                                        key: 'notes',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '填报人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 120,
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '填报日期',
                                        dataIndex: 'createDate',
                                        key: 'createDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        width: 150
                                    }
                                },
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    qnnTableConfig: {},
                                    content: {
                                        formConfig: [
                                            {
                                                label: '主键id',
                                                field: "zxSfOtherAddFileReportId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '名称',
                                                field: "title",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '备注',
                                                field: "notes",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '填报日期',
                                                field: "createDate",
                                                type: 'date',
                                                span: 12,
                                            },
                                            {
                                                label: '附件上传',
                                                field: "zxErpFileList",
                                                type: 'files',
                                                span: 12,
                                            },
                                        ]
                                    }
                                },
                            ]}
                        >
                        </QnnTable>
                    </TabPane>
                    <TabPane tab="事故快报表" key="5">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            antd={
                                {
                                    rowKey: function (row) {
                                        return row.zxSfOtherAddFilePressId
                                    },
                                    size: 'small'
                                }
                            }
                            fetchConfig={{
                                apiName: 'getZxSfOtherAddFilePressList',
                                // otherParams: {
                                //     projectId: projectId
                                // }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'addZxSfOtherAddFilePress',
                                                otherParams: {
                                                    orgID: orgID,
                                                    creator: realName
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].isimported === '0') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxSfOtherAddFilePress',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxSfOtherAddFilePress',
                                    }
                                },
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSfOtherAddFilePressId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '名称',
                                        dataIndex: 'title',
                                        key: 'title',
                                        onClick: 'detail',
                                        width: 150,
                                    }
                                },
                                {
                                    table: {
                                        title: '机构名称',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        width: 100,
                                    }
                                },
                                {
                                    table: {
                                        title: '填报人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 120,
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '填报日期',
                                        dataIndex: 'createDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        key: 'createDate',
                                        width: 150
                                    }
                                },
                                {
                                    table: {
                                        title: '备注',
                                        dataIndex: 'notes',
                                        key: 'notes',
                                        width: 150
                                    }
                                }
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    qnnTableConfig: {},
                                    content: {
                                        formConfig: [
                                            {
                                                label: '主键id',
                                                field: "zxSfOtherAddFileReportId",
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '名称',
                                                field: "title",
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                            },
                                            {
                                                label: '机构名称',
                                                field: "orgName",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '备注',
                                                field: "notes",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '填报日期',
                                                field: "createDate",
                                                type: 'date',
                                                span: 12,
                                            },
                                            {
                                                label: '填报人',
                                                field: "creator",
                                                type: 'string',
                                                span: 12,
                                            },
                                            {
                                                label: '附件上传',
                                                field: "zxErpFileList",
                                                type: 'files',
                                                span: 12,
                                            },
                                        ]
                                    }
                                },
                            ]}
                        >
                        </QnnTable>
                    </TabPane>
                </Tabs>
            </div >
        )
    }
}
export default index;