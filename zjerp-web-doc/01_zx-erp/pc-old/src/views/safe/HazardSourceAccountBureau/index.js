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
                            name: 'add',//??????add del
                            icon: 'plus',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            onClick: () => {
                                // ???????????? ??????id
                                this.tab3Id = null
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
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
                                    label: '??????',//???????????????????????????????????? 
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
                            name: 'edit',//??????add del
                            icon: 'edit',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
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
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
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
                                    name: 'submit',//??????add del
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    fetchConfig: {//ajax??????
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
                            name: 'del',//??????add del
                            icon: 'delete',//icon
                            type: 'danger',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            fetchConfig: {//ajax??????
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
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: 'detail',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'creator',
                                key: 'creator',
                                width: 100,
                            }
                        },
                        {
                            table: {
                                title: '??????',
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
                            title: "????????????",
                            qnnTableConfig: {},
                            content: {
                                formConfig: [
                                    {
                                        label: '??????id',
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
                                        label: '????????????',
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
                                        label: '?????????',
                                        field: "creator",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '??????',
                                        field: "remarks",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '????????????',
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
                            title: "??????",
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
                                                // ??????????????????id ???????????????32
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
                                                        label: '??????id',
                                                        field: 'zxSfHazardId',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '??????id',
                                                        field: 'orgID',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
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
                                                        title: '????????????????????????????????????',
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
                                                        title: '????????????',
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
                                                        title: '?????????????????????????????????',
                                                        dataIndex: 'accident',
                                                        key: 'accident',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????????????????L???',
                                                        dataIndex: 'lee',
                                                        key: 'lee',
                                                        tdEdit: true,
                                                        required: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '?????????',
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
                                                        title: '??????????????????????????????E???',
                                                        dataIndex: 'bee',
                                                        key: 'bee',
                                                        tdEdit: true,
                                                        required: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '?????????',
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
                                                        title: '??????????????????????????????C???',
                                                        dataIndex: 'cee',
                                                        key: 'cee',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '?????????',
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
                                                        title: '??????????????????????????????D???',
                                                        dataIndex: 'dee',
                                                        key: 'dee',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '?????????',
                                                        disabled:true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'riskLevel',
                                                        key: 'riskLevel',
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                        disabled:true
                                                    }
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????",

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
                                                    label: '??????',
                                                    fetchConfig: {//ajax??????
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