import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
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
    render() {
        const { visible, rowData, orgID, orgName, realName } = this.state;
        return (
            <div>
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
                            rowKey: 'zxSfHazardMainId',
                            size: 'small'
                        }
                    }
                    fetchConfig={{
                        apiName: 'getZxSfHazardMainList',
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
                                            // orgId: orgID,
                                            // orgName: orgName,
                                            // creator: realName
                                        }
                                        this.props.myFetch('addZxSfHazardMain', formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.tab3Id = data.zxSfHazardMainId
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
                                        apiName: 'updateZxSfHazardMain',
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
                                apiName: 'batchDeleteUpdateZxSfHazardMain',
                            }
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfHazardMainId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '机构名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: 'detail',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '编制人',
                                dataIndex: 'creator',
                                key: 'creator',
                                width: 100,
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 120,
                                type: 'string'
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
                                        field: "zxSfHazardMainId",
                                        span: 12,
                                        hide: true
                                    },
                                    {
                                        field: "orgID",
                                        span: 12,
                                        hide: true
                                    },
                                    {
                                        label: '机构名称',
                                        field: "orgName",
                                        type: 'select',
                                        span: 12,
                                        required: true,
                                        optionConfig: {
                                            label: 'companyName',
                                            value: 'companyName'
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyBySelect'
                                        },
                                    },
                                    {
                                        label: '编制人',
                                        field: "creator",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '备注',
                                        field: "remarks",
                                        type: 'string',
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
                                                if (obj.props.form.getFieldValue('zxSfHazardMainId')) {
                                                    return {
                                                        apiName: 'getZxSfHazardList',
                                                        otherParams: {
                                                            zxSfHazardRoomItemId: obj.props.form.getFieldValue('zxSfHazardMainId')
                                                        }
                                                    }
                                                } else {
                                                    return {}
                                                }
                                            },
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'zxSfHazardId',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            tableTdEdit: true,
                                            isShowRowSelect: true,
                                            tableTdEditSaveCb: (newRowData, oldRowData, props) => {
                                                // 后台生成主键id 固定长度为32
                                                if (newRowData.zxSfHazardId.length === 32) {
                                                    this.props.myFetch('updateZxSfHazard', { ...newRowData }).then(
                                                        this.table2.refresh()
                                                    )
                                                } else {
                                                    this.props.myFetch('addZxSfHazard', { ...newRowData, mainID: this.tab3Id, zxSfHazardMainId: null }).then(
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
                                                        field: 'zxSfHazardId',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '机构id',
                                                        field: 'orgID',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '过程（区域）',
                                                        dataIndex: 'proArea',
                                                        key: 'proArea',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: 'proArea',
                                                        required: true,
                                                        optionConfig: {
                                                            label: 'proArea',
                                                            value: 'proArea'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getZxSfHazardRoomAttProArea'
                                                        },
                                                        onChange: (val, thisProps, btnCallbackFn) => {
                                                            if (val) {
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.proArea = val;
                                                                    btnCallbackFn.setEditedRowData({
                                                                        ...editRowData
                                                                    });
                                                                })
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '行为（活动）或设备、环境',
                                                        dataIndex: 'doing',
                                                        key: 'doing',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        // type: 'selectByPaging',
                                                        field: 'doing',
                                                        parent: 'proArea',
                                                        optionConfig: {
                                                            label: 'doing',
                                                            value: 'zxSfHazardRoomAttId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getZxSfHazardRoomAttDoing',
                                                            otherParams: (obj) => {
                                                                return {
                                                                    zxSfHazardRoomAttId: obj?.rowData?.proArea
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, thisProps, btnCallbackFn) => {
                                                            if (val) {
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.doing = val;
                                                                    btnCallbackFn.setEditedRowData({
                                                                        ...editRowData
                                                                    });
                                                                })
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '危险因素',
                                                        dataIndex: 'riskFactors',
                                                        key: 'riskFactors',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        field: 'riskFactors',
                                                        parent: 'doing',
                                                        optionConfig: {
                                                            label: 'riskFactors',
                                                            value: 'zxSfHazardRoomAttId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getZxSfHazardRoomAttRiskFactors',
                                                            otherParams: (obj) => {
                                                                return {
                                                                    zxSfHazardRoomAttId: obj?.rowData?.doing,
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, thisProps, btnCallbackFn) => {
                                                            
                                                            if (val) {
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.riskFactors = val;
                                                                    const itemData = thisProps;
                                                                    let riskLevel = ''
                                                                    let dee = 0
                                                                    dee = +itemData.itemData.lee+ +itemData.itemData.cee+ +itemData.itemData.bee
                                                                    if(dee>=0&&dee<100){
                                                                        riskLevel = '1'
                                                                    }else if(dee>=100&&dee<400){
                                                                        riskLevel = '2'
                                                                    }else if(dee>=450&&dee<540){
                                                                        riskLevel = '3'
                                                                    }else if(dee>=540&&dee<720){
                                                                        riskLevel = '4'
                                                                    }else if(dee>=720){
                                                                        riskLevel = '5'
                                                                    }

                                                                    btnCallbackFn.setEditedRowData({
                                                                        ...editRowData,
                                                                        accident: itemData.itemData.accident,
                                                                        lee: itemData.itemData.lee,
                                                                        bee: itemData.itemData.bee,
                                                                        cee: itemData.itemData.cee,
                                                                        dee: dee,
                                                                        riskLevel: riskLevel
                                                                    });
                                                                })
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '可能导致的伤害（事故）',
                                                        dataIndex: 'accident',
                                                        key: 'accident',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '作业条件危险性评价（L）',
                                                        dataIndex: 'lee',
                                                        key: 'lee',
                                                        tdEdit: true,
                                                        required: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        required: true,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.dee = editRowData.lee&&editRowData.bee&&editRowData.cee? +editRowData.lee + +editRowData.bee + +editRowData.cee:0
                                                                    if(editRowData.dee>=0&&editRowData.dee<100){
                                                                        editRowData.riskLevel = '1'
                                                                    }else if(editRowData.dee>=100&&editRowData.dee<400){
                                                                        editRowData.riskLevel = '2'
                                                                    }else if(editRowData.dee>=450&&editRowData.dee<540){
                                                                        editRowData.riskLevel = '3'
                                                                    }else if(editRowData.dee>=540&&editRowData.dee<720){
                                                                        editRowData.riskLevel = '4'
                                                                    }else if(editRowData.dee>=720){
                                                                        editRowData.riskLevel = '5'
                                                                    }
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '作业条件危险性评价（E）',
                                                        dataIndex: 'bee',
                                                        key: 'bee',
                                                        tdEdit: true,
                                                        required: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        required: true,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.dee = editRowData.lee&&editRowData.bee&&editRowData.cee? +editRowData.lee + +editRowData.bee + +editRowData.cee:0
                                                                    if(editRowData.dee>=0&&editRowData.dee<100){
                                                                        editRowData.riskLevel = '1'
                                                                    }else if(editRowData.dee>=100&&editRowData.dee<400){
                                                                        editRowData.riskLevel = '2'
                                                                    }else if(editRowData.dee>=450&&editRowData.dee<540){
                                                                        editRowData.riskLevel = '3'
                                                                    }else if(editRowData.dee>=540&&editRowData.dee<720){
                                                                        editRowData.riskLevel = '4'
                                                                    }else if(editRowData.dee>=720){
                                                                        editRowData.riskLevel = '5'
                                                                    }
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '作业条件危险性评价（C）',
                                                        dataIndex: 'cee',
                                                        key: 'cee',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        required: true,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.dee = editRowData.lee&&editRowData.bee&&editRowData.cee? +editRowData.lee + +editRowData.bee + +editRowData.cee:0
                                                                    if(editRowData.dee>=0&&editRowData.dee<100){
                                                                        editRowData.riskLevel = '1'
                                                                    }else if(editRowData.dee>=100&&editRowData.dee<400){
                                                                        editRowData.riskLevel = '2'
                                                                    }else if(editRowData.dee>=450&&editRowData.dee<540){
                                                                        editRowData.riskLevel = '3'
                                                                    }else if(editRowData.dee>=540&&editRowData.dee<720){
                                                                        editRowData.riskLevel = '4'
                                                                    }else if(editRowData.dee>=720){
                                                                        editRowData.riskLevel = '5'
                                                                    }    
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '作业条件危险性评价（D）',
                                                        dataIndex: 'dee',
                                                        key: 'dee',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        disabled:true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '风险等级',
                                                        dataIndex: 'riskLevel',
                                                        key: 'riskLevel',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        disabled:true
                                                    }
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
                                                        apiName: 'batchDeleteUpdateZxSfHazardMain',
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
            </div>
        )
    }
}
export default index