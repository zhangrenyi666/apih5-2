import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Tabs } from "antd";
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
        const { orgID, orgName, realName } = this.state;
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
                            method={{
                                onClickFunTaboneAdd: () => {
                                    this.tab2Id = null
                                },
                                hideFunTaboneAddCancel: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                hidecancel: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                onClickSave: (obj) => {
                                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                    var index = obj.btnCallbackFn.getActiveKey();

                                    var formData = {
                                        ...obj._formData,
                                        orgID: orgID,
                                        orgName: orgName,
                                    }

                                    switch (index) {
                                        case '0':
                                            this.props.myFetch('addZxSfSWAccident', formData).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.tab2Id = data.zxSfSWAccidentId
                                                        // obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
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
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfSWAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    swaID: obj._formData.zxSfSWAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }


                                },
                                disabledEdit: (obj) => {
                                    // let data = obj.btnCallbackFn.getSelectedRows();
                                    // if (data.length === 1 && data[0].isimported === '0') {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                hideEditCan: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                hideEditSave: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                onClickEditSaveaa: (obj) => {
                                    var index = obj.btnCallbackFn.getActiveKey();
                                    switch (index) {
                                        case '0':
                                            this.props.myFetch('updateZxSfSWAccident', { ...obj._formData, table2: null }).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfSWAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    swaID: obj._formData.zxSfSWAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            // actionBtns={[
                            //     {
                            //         name: 'add',
                            //         icon: 'plus',
                            //         type: 'primary',
                            //         label: '新增',
                            //         onClick: 'bind:onClickFunTaboneAdd',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: 'bind:hideFunTaboneAddCancel'
                            //             },
                            //             {
                            //                 name: 'diySubmit',
                            //                 field: 'diySubmit',
                            //                 label: '保存',
                            //                 hide: 'bind:hidecancel',
                            //                 onClick: 'bind:onClickSave'
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'edit',
                            //         icon: 'edit',
                            //         type: 'primary',
                            //         label: '修改',
                            //         disabled: 'bind:disabledEdit',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: "bind:hideEditCan"
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'updateZxSfSWAccident',
                            //                 },
                            //                 hide: 'bind:hideEditSave',
                            //                 onClick: 'bind:onClickEditSaveaa'
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'del',
                            //         icon: 'delete',
                            //         type: 'danger',
                            //         label: '删除',
                            //         fetchConfig: {
                            //             apiName: 'batchDeleteUpdateZxSfSWAccident'
                            //         }
                            //     }
                            // ]}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    var props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    //安全事故
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "SafetyAccident1"
                                    }
                                }
                            }}
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
                                        filter: true,
                                        width: 150,
                                    },
                                    form: {
                                        type: 'string',
                                        field: 'orgName'
                                    }
                                },
                                {
                                    table: {
                                        title: '期次',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        filter: true,
                                        format: 'YYYY-MM'
                                    },
                                    form: {
                                        field: "datePeriod",
                                        type: 'month',
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
                                        console.log(obj.btnCallbackFn.form.getFieldsValue());
                                        return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfSWAccidentId"))
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
                                                                    swaID: obj.props.form.getFieldValue('zxSfSWAccidentId')
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
                                                    // tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    // tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                    //     console.log(newRowData, oldRowData, props)
                                                    //     newRowData.btnCallbackFn.getEditedRowData().then((val) => {
                                                    //         console.log(val)
                                                    //         // 后台生成主键id 固定长度为32
                                                    //         if (newRowData.zxSfAccidentItemId.length === 32) {
                                                    //             this.props.myFetch('updateZxSfAccidentItem', { ...val }).then(
                                                    //                 // () => {
                                                    //                 //     this.table2_0.refresh()
                                                    //                 // }
                                                    //             )
                                                    //         } else {
                                                    //             this.props.myFetch('addZxSfSWAccidentItem', { ...val, swaID: this.tab2Id, zxSfAccidentItemId: null }).then(
                                                    //                 // () => {
                                                    //                 //     this.table2_0.refresh()
                                                    //                 // }
                                                    //             )
                                                    //         }
                                                    //     })
                                                    // },
                                                    wrappedComponentRef: (me) => {
                                                        this.table2_0 = me;
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
                                                                type: 'string',
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
                                apiName: 'getZxSfAccidentList'
                            }}
                            method={{
                                onClickAddwe: () => {
                                    this.tab2Id = null
                                },
                                hidesDSG: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                hideCalll: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                onClickwerwe: (obj) => {
                                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                    var index = obj.btnCallbackFn.getActiveKey();

                                    var formData = {
                                        ...obj._formData,
                                        orgID: orgID,
                                        orgName: orgName,
                                    }

                                    switch (index) {
                                        case '0':
                                            this.props.myFetch('addZxSfAccident', formData).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.tab2Id = data.zxSfAccidentId
                                                        // obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
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
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    accidentId: obj._formData.zxSfAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }

                                },
                                disableddsftr: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && data[0].isimported === '0') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                hideFjyk: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                hideSavertyu: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false
                                },
                                onClickSaveoyui: (obj) => {
                                    var index = obj.btnCallbackFn.getActiveKey();
                                    switch (index) {
                                        case '0':
                                            this.props.myFetch('updateZxSfAccident', { ...obj._formData, table2: null }).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    accidentId: obj._formData.zxSfAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            // actionBtns={[
                            //     {
                            //         name: 'add',
                            //         icon: 'plus',
                            //         type: 'primary',
                            //         label: '新增',
                            //         onClick: 'bind:onClickAddwe',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: "bind:hidesDSG"
                            //             },
                            //             {
                            //                 name: 'diySubmit',
                            //                 field: 'diySubmit',
                            //                 label: '保存',
                            //                 hide: 'bind:hideCalll',
                            //                 onClick: "bind:onClickwerwe"
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'edit',
                            //         icon: 'edit',
                            //         type: 'primary',
                            //         label: '修改',
                            //         disabled: 'bind:disableddsftr',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: "bind:hideFjyk"
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'updateZxSfAccident',
                            //                 },
                            //                 hide: "bind:hideSavertyu",
                            //                 onClick: "bind:onClickSaveoyui"
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'del',
                            //         icon: 'delete',
                            //         type: 'danger',
                            //         label: '删除',
                            //         fetchConfig: {
                            //             apiName: 'batchDeleteUpdateZxSfAccident'
                            //         }
                            //     }
                            // ]}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    var props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    //安全事故
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "SafetyAccident2"
                                    }
                                }
                            }}
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
                                        filter: true,
                                        width: 150,
                                    },
                                    form: {
                                        type: 'string',
                                        field: 'orgName'
                                    }
                                },
                                {
                                    table: {
                                        title: '月份',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        type: 'month',
                                        filter: true,
                                        format: 'YYYY-MM'
                                    },
                                    form: {
                                        field: "datePeriod",
                                        type: 'month',
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
                                        type: 'number',
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
                                                type: 'number',
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
                                                    // tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    // tableTdEditSaveCb: (newRowData, oldRowData, props) => {

                                                    //     newRowData.btnCallbackFn.getEditedRowData().then(val => {
                                                    //         // 后台生成主键id 固定长度为32
                                                    //         if (newRowData.zxSfAccidentItemId.length === 32) {
                                                    //             this.props.myFetch('updateZxSfAccidentItem', { ...val }).then(
                                                    //                 () => {
                                                    //                     this.table2_1.refresh()
                                                    //                 }
                                                    //             )
                                                    //         } else {
                                                    //             this.props.myFetch('addZxSfAccidentItem', { ...val, accidentId: this.tab2Id, zxSfAccidentItemId: null }).then(
                                                    //                 () => {
                                                    //                     this.table2_1.refresh()
                                                    //                 }
                                                    //             )
                                                    //         }
                                                    //     })
                                                    // },
                                                    wrappedComponentRef: (me) => {
                                                        this.table2_1 = me;
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
                                                                type: 'date'
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
                                apiName: 'getZxSfEAccidentList'
                            }}
                            method={{
                                onClickAddtyuty: () => {
                                    this.tab3Id = null
                                },
                                hideAddkjhh: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false;
                                },
                                hideAddvdf: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false;
                                },
                                onClickAddlkpo: (obj) => {
                                    var index = obj.btnCallbackFn.getActiveKey();
                                    switch (index) {
                                        case '0':
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
                                                        // obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
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
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfEAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    eaId: obj._formData.zxSfEAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }

                                },
                                disabledEdit: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && data[0].isimported === '0') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                hideEditdaf: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();

                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false;
                                },
                                hideEditSave: (obj) => {
                                    // var index = obj.btnCallbackFn.getActiveKey();
                                    // if (index === "1") {
                                    //     return true;
                                    // } else {
                                    //     return false;
                                    // }
                                    return false;
                                },
                                onClickEditSave: (obj) => {
                                    var index = obj.btnCallbackFn.getActiveKey();
                                    switch (index) {
                                        case '0':
                                            this.props.myFetch('updateZxSfEAccident', { ...obj._formData, table2: null }).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        case '1':
                                            this.props.myFetch('batchSaveUpdateZxSfEAccidentItem', obj._formData.table2.map(item => {
                                                return {
                                                    ...item,
                                                    eaId: obj._formData.zxSfEAccidentId
                                                }
                                            })).then(({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success('保存成功!')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            break
                                        default:
                                            break
                                    }
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            // actionBtns={[
                            //     {
                            //         name: 'add',
                            //         icon: 'plus',
                            //         type: 'primary',
                            //         label: '新增',
                            //         onClick: 'bind:onClickAddtyuty',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: 'bind:hideAddkjhh'
                            //             },
                            //             {
                            //                 name: 'diySubmit',
                            //                 field: 'diySubmit',
                            //                 label: '保存',
                            //                 hide: 'bind:hideAddvdf',
                            //                 onClick: 'bind:onClickAddlkpo'
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'edit',
                            //         icon: 'edit',
                            //         type: 'primary',
                            //         label: '修改',
                            //         disabled: 'bind:disabledEdit',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //                 hide: 'bind:hideEditdaf'
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'updateZxSfEAccident',
                            //                 },
                            //                 hide: 'bind:hideEditSave',
                            //                 onClick: 'bind:onClickEditSave'
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'del',
                            //         icon: 'delete',
                            //         type: 'danger',
                            //         label: '删除',
                            //         fetchConfig: {
                            //             apiName: 'batchDeleteUpdateZxSfEAccident'
                            //         }
                            //     }
                            // ]}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    var props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    //安全事故
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "SafetyAccident3"
                                    }
                                }
                            }}
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
                                        filter: true,
                                        width: 150,
                                    },
                                    form: {
                                        field: "orgName",
                                        type: 'string',
                                    }
                                },
                                {
                                    table: {
                                        title: '年份',
                                        dataIndex: 'datePeriod',
                                        key: 'datePeriod',
                                        width: 100,
                                        filter: true,
                                        format: 'YYYY-MM'
                                    },
                                    form: {
                                        field: "datePeriod",
                                        type: 'month',
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
                                        return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfEAccidentId"))
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
                                                    // tableTdEdit: true,
                                                    isShowRowSelect: true,
                                                    // tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                    //     newRowData.btnCallbackFn.getEditedRowData().then(val => {
                                                    //         // 后台生成主键id 固定长度为32
                                                    //         if (newRowData.zxSfEAccidentItemId.length === 32) {
                                                    //             this.props.myFetch('updateZxSfEAccidentItem', { ...val }).then(
                                                    //                 () => {
                                                    //                     this.table2_2.refresh()
                                                    //                 }
                                                    //             )
                                                    //         } else {
                                                    //             this.props.myFetch('addZxSfEAccidentItem', { ...val, eaId: this.tab3Id, zxSfEAccidentItemId: null }).then(
                                                    //                 () => {
                                                    //                     this.table2_2.refresh()
                                                    //                 }
                                                    //             )
                                                    //         }
                                                    //     }
                                                    //     )
                                                    // },

                                                    wrappedComponentRef: (me) => {
                                                        this.table2_2 = me;
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
                                apiName: 'getZxSfOtherAddFileReportList'
                            }}
                            method={{
                                disabledEditfdsg: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && data[0].isimported === '0') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                onClickEditSaveiiii: (obj) => {
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            // actionBtns={[
                            //     {
                            //         name: 'add',
                            //         icon: 'plus',
                            //         type: 'primary',
                            //         label: '新增',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'addZxSfOtherAddFileReport'
                            //                 }
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'edit',
                            //         icon: 'edit',
                            //         type: 'primary',
                            //         label: '修改',
                            //         disabled: "bind:disabledEditfdsg",
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'updateZxSfOtherAddFileReport',
                            //                 },
                            //                 onClick: "bind:onClickEditSaveiiii"
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'del',
                            //         icon: 'delete',
                            //         type: 'danger',
                            //         label: '删除',
                            //         fetchConfig: {
                            //             apiName: 'batchDeleteUpdateZxSfOtherAddFileReport'
                            //         }
                            //     }
                            // ]}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    var props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    //安全事故
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "SafetyAccident4"
                                    }
                                }
                            }}
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
                                        filter: true,
                                        width: 100,
                                    },
                                    form: {
                                        field: "orgName",
                                        type: 'string',
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
                                        filter: true,
                                        width: 150
                                    },
                                    form: {
                                        field: "createDate",
                                        type: 'date',
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
                                                label: '',
                                                field: "orgID",
                                                initialValue: orgID,
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '',
                                                field: "orgName",
                                                initialValue: orgName,
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '',
                                                field: "creator",
                                                initialValue: realName,
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
                            method={{
                                disabledFunEditas: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && data[0].isimported === '0') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                },
                                onClickFunEditsdfkjg: (obj) => {
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            // actionBtns={[
                            //     {
                            //         name: 'add',
                            //         icon: 'plus',
                            //         type: 'primary',
                            //         label: '新增',
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'addZxSfOtherAddFilePress'
                            //                 }
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'edit',
                            //         icon: 'edit',
                            //         type: 'primary',
                            //         label: '修改',
                            //         disabled: "bind:disabledFunEditas",
                            //         formBtns: [
                            //             {
                            //                 name: 'cancel',
                            //                 type: 'dashed',
                            //                 label: '取消',
                            //             },
                            //             {
                            //                 name: 'submit',
                            //                 type: 'primary',
                            //                 label: '保存',
                            //                 fetchConfig: {
                            //                     apiName: 'updateZxSfOtherAddFilePress'
                            //                 },
                            //                 onClick: "bind:onClickFunEditsdfkjg"
                            //             }
                            //         ]
                            //     },
                            //     {
                            //         name: 'del',
                            //         icon: 'delete',
                            //         type: 'danger',
                            //         label: '删除',
                            //         fetchConfig: {
                            //             apiName: 'batchDeleteUpdateZxSfOtherAddFilePress'
                            //         }
                            //     }
                            // ]}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    var props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    //安全事故
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "SafetyAccident5"
                                    }
                                }
                            }}
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
                                        filter: true,
                                        width: 100,
                                    },
                                    form: {
                                        field: "orgName",
                                        type: 'string',
                                    }
                                },
                                {
                                    table: {
                                        title: '填报人',
                                        dataIndex: 'creator',
                                        key: 'creator',
                                        width: 120,
                                        type: 'string'
                                    },

                                },
                                {
                                    table: {
                                        title: '填报日期',
                                        dataIndex: 'createDate',
                                        type: 'date',
                                        format: 'YYYY-MM-DD',
                                        filter: true,
                                        key: 'createDate',
                                        width: 150
                                    },
                                    form: {
                                        field: "createDate",
                                        type: 'date',
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
                                                label: '',
                                                field: "orgID",
                                                initialValue: orgID,
                                                span: 12,
                                                hide: true
                                            },
                                            {
                                                label: '',
                                                field: "creator",
                                                initialValue: realName,
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