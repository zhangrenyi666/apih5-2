import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
// import moment from 'moment';
import { message as Msg, Modal, Input, Row, Col } from 'antd';
// import $ from 'jquery'
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxBuStockPriceId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 16 }
        }
    }
};
const configItem = {
    antd: {
        rowKey: 'zxBuStockPriceItemId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
const configItemSJ = {
    antd: {
        rowKey: 'zxBuStockPriceItemId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
const configItemLQHHL = {
    antd: {
        rowKey: 'zxBuStockPriceItemId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
const configItemJplHhl = {
    antd: {
        rowKey: 'zxBuStockPriceItemId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
const configItemhtl = {
    antd: {
        rowKey: 'zxBuStockPriceItemId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            zxBuStockPriceId: '',
            businessType: '2',
            orgName: '',
            orgID: '',
            QnnTableItemData: [],
            zjid: '',
            //砂浆
            shaJiangData: [],
            shaJiangDataid: '',
            shaJiangMixType: '25',
            //沥青混合
            lqhhlData: [],
            lqhhlId: '',
            lqhhlMixType: '22',
            //级配类混合料
            jplHhlData: [],
            jplHhlid: '',
            jplHhlMixType: '23',
            //灰土类
            htlData: [],
            htlid: '',
            htlMixType: '24',
            //混凝土
            hntData: [],
            hntid: '',
            hntMixType: '21'
        }
    }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    //判空返回0
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxBuStockPriceList',
                        otherParams: {
                            businessType: this.state.businessType,
                            budgetType: '标后预算'
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消',
                                },
                                {
                                    name: 'submitDiy',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.props.myFetch('addZxBuStockPrice', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 新增接口，返回个主键id
                                                    this.setState({
                                                        zxBuStockPriceId: data.zxBuStockPriceId,
                                                        orgName: data.orgName,
                                                        orgID: data.orgID
                                                    })
                                                    obj.btnCallbackFn.form.setFieldsValue({ zxBuStockPriceId: data.zxBuStockPriceId });
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    },
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                                this.setState({
                                    zxBuStockPriceId: obj.selectedRows[0].zxBuStockPriceId,
                                    orgName: obj.selectedRows[0].orgName,
                                    orgID: obj.selectedRows[0].orgID,

                                })
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消'
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
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
                                        this.props.myFetch('updateZxBuStockPrice', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 获取树数据
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxBuStockPrice', obj.selectedRows).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.table.refresh();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                        }
                    ]}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "zxBuStockPriceId",
                                        hide: true
                                    },
                                    {
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'budgetType',
                                        type: 'string',
                                        hide: true,
                                        initialValue: '标后预算'
                                    },
                                    {
                                        field: 'businessType',
                                        type: 'string',
                                        hide: true,
                                        initialValue: this.state.businessType
                                    },
                                    {
                                        label: '项目名称',
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'orgName',
                                            value: 'orgID',
                                            linkageFields: {
                                                orgName: 'orgName'
                                            }
                                        },
                                        editDisabled: true,
                                        fetchConfig: {
                                            apiName: 'getZxBuProjectTypeCheckOver',
                                            otherParams:{
                                                orgID: departmentId
                                            }
                                        },
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '编制人',
                                        field: 'reporter',
                                        type: 'string',
                                        initialValue: "管理员",
                                        editDisabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '编制机构名称',
                                        field: 'reportOrgName',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: '北京建筑分公司',
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '编制日期',
                                        field: 'reportDate',
                                        type: 'date',
                                        span: 12,
                                        editDisabled: true,
                                        initialValue: new Date(),
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '备注',
                                        field: 'remarks',
                                        type: 'textarea',
                                        span: 24,
                                        editDisabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        //混凝土配合比表
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "混凝土配合比表",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <div style={{ padding: '10px' }}>
                                    <Row>
                                        <Col span={8}>
                                            <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                                            <Input disabled='disabled' value={this.state.orgName} style={{ margin: '10px' }}></Input>
                                        </Col>
                                    </Row>
                                    <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        // data={this.state.QnnTableItemData}
                                        fetchConfig={{
                                            apiName: "getZxBuStockPriceItemList",
                                            otherParams: {
                                                orgID: this.state.orgID,
                                                mixType: this.state.hntMixType
                                            }
                                        }}
                                        {...configItem}
                                        wrappedComponentRef={(me) => {
                                            this.tableHNT = me;
                                        }}
                                        method={{
                                            tableTdEditSaveCb: (obj) => {
                                                console.log(obj)
                                                const { myFetch } = this.props;
                                                myFetch("updateZxBuStockPriceItem",obj).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tableHNT.refresh()
                                                        } else {
                                                        }
                                                    }
                                                )
                                            }
                                        }}
                                        tableTdEdit={true}
                                        tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                                        // isShowRowSelect={true}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'zxBuStockPriceItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '砼标号',
                                                    dataIndex: 'resID',
                                                    key: 'resID',
                                                    width: 200,
                                                    align: 'center',
                                                    type: 'select',
                                                    fixed: 'left',
                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                },
                                                form: {
                                                    allowClear: false,
                                                    type: 'select',
                                                    field:'resID',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                        linkageFields: {
                                                            resName:'resName'
                                                        }
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'hunNingTuPeiHeBiBiao'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'resName',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥52.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty1',
                                                            key: 'qty1',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price1',
                                                            key: 'price1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt1',
                                                            key: 'amt1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt1'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥42.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty2',
                                                            key: 'qty2',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price2',
                                                            key: 'price2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt2',
                                                            key: 'amt2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt2'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥32.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty3',
                                                            key: 'qty3',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price3',
                                                            key: 'price3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt3',
                                                            key: 'amt3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt3'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '黄砂(河沙)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty4',
                                                            key: 'qty4',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price4',
                                                            key: 'price4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt4',
                                                            key: 'amt4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt4'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '机制砂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty5',
                                                            key: 'qty5',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price5',
                                                            key: 'price5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt5',
                                                            key: 'amt5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt5'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '碎石',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty6',
                                                            key: 'qty6',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price6',
                                                            key: 'price6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt6',
                                                            key: 'amt6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt6'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty7',
                                                            key: 'qty7',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price7',
                                                            key: 'price7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt7',
                                                            key: 'amt7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt7'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '粉煤灰',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty8',
                                                            key: 'qty8',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                            tdEditCb: () => {

                                                            }
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price8',
                                                            key: 'price8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt8',
                                                            key: 'amt8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt8'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '速凝剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty9',
                                                            key: 'qty9',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty9',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt9 = rowData.qty9 && rowData.price9
                                                                        ? (this.FloatMulTwo(rowData.qty9,rowData.price9)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(newRowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price9',
                                                            key: 'price9',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price9',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt9 = rowData.qty9 && rowData.price9
                                                                        ? (this.FloatMulTwo(rowData.qty9,rowData.price9)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(newRowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt9',
                                                            key: 'amt9',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt9'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '减水剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty10',
                                                            key: 'qty10',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty10',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt10 = rowData.qty10 && rowData.price10
                                                                        ? (this.FloatMulTwo(rowData.qty10,rowData.price10)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(newRowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price10',
                                                            key: 'price10',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price10',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt10 = rowData.qty10 && rowData.price10
                                                                        ? (this.FloatMulTwo(rowData.qty10,rowData.price10)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(newRowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt10',
                                                            key: 'amt10',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt10'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '防水剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty11',
                                                            key: 'qty11',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty11',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt11 = rowData.qty11 && rowData.price11
                                                                        ? (this.FloatMulTwo(rowData.qty11,rowData.price11)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(newRowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price11',
                                                            key: 'price11',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price11',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt11 = rowData.qty11 && rowData.price11
                                                                        ? (this.FloatMulTwo(rowData.qty11,rowData.price11)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(newRowData.amt11)
                                                                        + Number(rowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt11',
                                                            key: 'amt11',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt11'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '膨胀剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty12',
                                                            key: 'qty12',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty12',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt12 = rowData.qty12 && rowData.price12
                                                                        ? (this.FloatMulTwo(rowData.qty12,rowData.price12)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(newRowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price12',
                                                            key: 'price12',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price12',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt12 = rowData.qty12 && rowData.price12
                                                                        ? (this.FloatMulTwo(rowData.qty12,rowData.price12)).toFixed(2) : 0;
                                                                    //不含税小计(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(newRowData.amt12);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                                    await this.tableHNT.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt12',
                                                            key: 'amt12',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt12'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税小计(元)',
                                                    dataIndex: 'sumAmt',
                                                    width: 130,
                                                    key: 'sumAmt',
                                                },
                                                form: {
                                                    type: 'number',
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '耗用率',
                                                    dataIndex: 'bhAmt',
                                                    key: 'bhAmt',
                                                    tdEdit: drawerTitile === '详情' ? false : true
                                                },
                                                form: {
                                                    type: 'number'
                                                },
                                                onBlur: async ()=>{
                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                    const newRowData = {};
                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                    await this.tableHNT.setEditedRowData({
                                                        ...newRowData
                                                    });
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '拌合运输费(元/m³)',
                                                    dataIndex: 'ysAmt',
                                                    width: 200,
                                                    key: 'ysAmt',
                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                },
                                                form: {
                                                    type: 'number'
                                                },
                                                onBlur: async ()=>{
                                                    const rowData = await this.tableHNT.getEditedRowData(false);
                                                    const newRowData = {};
                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                    const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                                    const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                                    newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt,bhAmt) + ysAmt ;
                                                    await this.tableHNT.setEditedRowData({
                                                        ...newRowData
                                                    });
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '总计',
                                                    dataIndex: 'scenePrice',
                                                    key: 'scenePrice',
                                                },
                                                form: {
                                                    type: 'number',
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            {
                                                name: "diyadd",
                                                icon: "plus",
                                                type: "primary",
                                                label: "新增",
                                                onClick: (obj) => {
                                                    const { myFetch } = this.props;
                                                    console.log(obj)
                                                    myFetch("addZxBuStockPriceItem", { 
                                                        orgID: this.state.orgID,
                                                        mixType: this.state.hntMixType,
                                                        qty1: 0,
                                                        price1: 0,
                                                        amt1: 0,
                                                        qty2: 0,
                                                        price2: 0,
                                                        amt2: 0,
                                                        qty3: 0,
                                                        price3: 0,
                                                        amt3: 0,
                                                        qty4: 0,
                                                        price4: 0,
                                                        amt4: 0,
                                                        qty5: 0,
                                                        price5: 0,
                                                        amt5: 0,
                                                        qty6: 0,
                                                        price6: 0,
                                                        amt6: 0,
                                                        qty7: 0,
                                                        price7: 0,
                                                        amt7: 0,
                                                        qty8: 0,
                                                        price8: 0,
                                                        amt8: 0,
                                                        qty9: 0,
                                                        price9: 0,
                                                        amt9: 0,
                                                        qty10: 0,
                                                        price10: 0,
                                                        amt10: 0,
                                                        qty11: 0,
                                                        price11: 0,
                                                        amt11: 0,
                                                        qty12: 0,
                                                        price12: 0,
                                                        amt12: 0,
                                                        sumAmt: 0,
                                                        bhAmt: 0,
                                                        ysAmt: 0,
                                                        scenePrice: 0
                                                    }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableHNT.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    )
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            {
                                                name: 'diydel',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.tableHNT.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        ]}
                                    />
                                </div>
                            }
                        },
                        //砂浆配合比表
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "砂浆配合比表",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <div style={{ padding: '10px' }}>
                                    <Row>
                                        <Col span={8}>
                                            <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                                            <Input disabled='disabled' value={this.state.orgName} style={{ margin: '10px' }}></Input>
                                        </Col>
                                    </Row>
                                    <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        // data={this.state.shaJiangData}
                                        fetchConfig={{
                                            apiName: "getZxBuStockPriceItemList",
                                            otherParams: {
                                                orgID: this.state.orgID,
                                                mixType: this.state.shaJiangMixType
                                            }
                                        }}
                                        {...configItemSJ}
                                        wrappedComponentRef={(me) => {
                                            this.tableshaJiang = me;
                                        }}
                                        method={{
                                            tableTdEditSaveCb: (obj) => {
                                                const { myFetch } = this.props;
                                                myFetch("updateZxBuStockPriceItem", obj).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tableshaJiang.refresh()
                                                        } else {
                                                        }
                                                    }
                                                )
                                            }
                                        }}
                                        tableTdEdit={true}
                                        tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                                        // isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'shaJiangDataid',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '砼标号',
                                                    dataIndex: 'resID',
                                                    key: 'resID',
                                                    type: 'select',
                                                    width: 200,
                                                    align: 'center',
                                                    fixed: 'left',
                                                    tdEdit: drawerTitile === '详情' ? false : true
                                                },
                                                form: {
                                                    allowClear: false,
                                                    type: 'select',
                                                    field:'resID',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                        linkageFields: {
                                                            resName:'resName'
                                                        }
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'shaJiangPeiHeBiBiao'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'resName',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥52.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty1',
                                                            key: 'qty1',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1 
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price1',
                                                            key: 'price1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1 
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt1',
                                                            key: 'amt1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt1'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥42.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty2',
                                                            key: 'qty2',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price2',
                                                            key: 'price2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt2',
                                                            key: 'amt2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt2'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥32.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty3',
                                                            key: 'qty3',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price3',
                                                            key: 'price3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt3',
                                                            key: 'amt3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt3'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '黄砂(河沙)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty4',
                                                            key: 'qty4',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price4',
                                                            key: 'price4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                            tdEditCb: () => {

                                                            }
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt4',
                                                            key: 'amt4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt4'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '机制砂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty5',
                                                            key: 'qty5',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price5',
                                                            key: 'price5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt5',
                                                            key: 'amt5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt5'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty6',
                                                            key: 'qty6',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price6',
                                                            key: 'price6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt6',
                                                            key: 'amt6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt6'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '膨胀剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty7',
                                                            key: 'qty7',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price7',
                                                            key: 'price7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableshaJiang.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.sumAmt = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableshaJiang.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt7',
                                                            key: 'amt7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt7'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税总价(元)',
                                                    dataIndex: 'sumAmt',
                                                    width: 150,
                                                    key: 'sumAmt'
                                                },
                                                form: {
                                                    type: 'number'
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            {
                                                name: "diyadd",
                                                icon: "plus",
                                                type: "primary",
                                                label: "新增",
                                                onClick: () => {
                                                    const { myFetch } = this.props;
                                                    myFetch("addZxBuStockPriceItem", { orgID: this.state.orgID, mixType: this.state.shaJiangMixType }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableshaJiang.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    )
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            {
                                                name: 'diydel',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.tableshaJiang.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        ]}
                                    />
                                </div>
                            }
                        },
                        //沥青混合料配合比表
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "沥青混合料配合比表",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <div style={{ padding: '10px' }}>
                                    <Row>
                                        <Col span={8}>
                                            <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                                            <Input disabled='disabled' value={this.state.orgName} style={{ margin: '10px' }}></Input>
                                        </Col>
                                    </Row>
                                    <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        // data={this.state.QnnTableItemData}
                                        fetchConfig={{
                                            apiName: "getZxBuStockPriceItemList",
                                            otherParams: {
                                                orgID: this.state.orgID,
                                                mixType: this.state.lqhhlMixType
                                            }
                                        }}
                                        {...configItemLQHHL}
                                        wrappedComponentRef={(me) => {
                                            this.tableLQHHL = me;
                                        }}
                                        method={{
                                            tableTdEditSaveCb: (obj) => {
                                                const { myFetch } = this.props;
                                                myFetch("updateZxBuStockPriceItem", obj).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tableLQHHL.refresh()
                                                        } else {
                                                        }
                                                    }
                                                )
                                            }
                                        }}
                                        tableTdEdit={true}
                                        tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                                        // isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'zxBuStockPriceItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '混合料类型',
                                                    dataIndex: 'resID',
                                                    key: 'resID',
                                                    width: 200,
                                                    align: 'center',
                                                    fixed: 'left',
                                                    tdEdit: drawerTitile === '详情' ? false : true
                                                },
                                                form: {
                                                    allowClear: false,
                                                    type: 'select',
                                                    field:'resID',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                        linkageFields: {
                                                            resName:'resName'
                                                        }
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'liQingHunHeLiaoPeiHeBiBiao'
                                                        }
                                                    },
                                                    placeholder: '请选择'
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'resName',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '路面用石屑(0-0.3cm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty1',
                                                            key: 'qty1',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1 
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price1',
                                                            key: 'price1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1 
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt1',
                                                            key: 'amt1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt1'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '路面用碎石(0.3-0.5cm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty2',
                                                            key: 'qty2',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price2',
                                                            key: 'price2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt2',
                                                            key: 'amt2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt2'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '路面用碎石(0.5-1cm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty3',
                                                            key: 'qty3',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price3',
                                                            key: 'price3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt3',
                                                            key: 'amt3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt3'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '路面用碎石(1-2cm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty4',
                                                            key: 'qty4',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price4',
                                                            key: 'price4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt4',
                                                            key: 'amt4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt4'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '路面用碎石(1-3cm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty5',
                                                            key: 'qty5',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price5',
                                                            key: 'price5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt5',
                                                            key: 'amt5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt5'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '机制砂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty6',
                                                            key: 'qty6',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price6',
                                                            key: 'price6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt6',
                                                            key: 'amt6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt6'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '矿粉',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty7',
                                                            key: 'qty7',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price7',
                                                            key: 'price7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt7',
                                                            key: 'amt7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt7'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty8',
                                                            key: 'qty8',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price8',
                                                            key: 'price8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt8',
                                                            key: 'amt8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt8'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '普通沥青',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty9',
                                                            key: 'qty9',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty9',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt9 = rowData.qty9 && rowData.price9
                                                                        ? (this.FloatMulTwo(rowData.qty9,rowData.price9)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(newRowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price9',
                                                            key: 'price9',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price9',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt9 = rowData.qty9 && rowData.price9
                                                                        ? (this.FloatMulTwo(rowData.qty9,rowData.price9)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(newRowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt9',
                                                            key: 'amt9',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt9'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '改性沥青',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty10',
                                                            key: 'qty10',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty10',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt10 = rowData.qty10 && rowData.price10
                                                                        ? (this.FloatMulTwo(rowData.qty10,rowData.price10)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(newRowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price10',
                                                            key: 'price10',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price10',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt10 = rowData.qty10 && rowData.price10
                                                                        ? (this.FloatMulTwo(rowData.qty10,rowData.price10)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(newRowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt10',
                                                            key: 'amt10',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt10'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '橡胶改性沥青',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty11',
                                                            key: 'qty11',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty11',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt11 = rowData.qty11 && rowData.price11
                                                                        ? (this.FloatMulTwo(rowData.qty11,rowData.price11)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(newRowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price11',
                                                            key: 'price11',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price11',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt11 = rowData.qty11 && rowData.price11
                                                                        ? (this.FloatMulTwo(rowData.qty11,rowData.price11)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(newRowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt11',
                                                            key: 'amt11',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt11'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '抗剥落剂',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty12',
                                                            key: 'qty12',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty12',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt12 = rowData.qty12 && rowData.price12
                                                                        ? (this.FloatMulTwo(rowData.qty12,rowData.price12)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(newRowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price12',
                                                            key: 'price12',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price12',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt12 = rowData.qty12 && rowData.price12
                                                                        ? (this.FloatMulTwo(rowData.qty12,rowData.price12)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(newRowData.amt12)
                                                                        + Number(rowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt12',
                                                            key: 'amt12',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt12'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '木质纤维',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty13',
                                                            key: 'qty13',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty13',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt13 = rowData.qty13 && rowData.price13
                                                                        ? (this.FloatMulTwo(rowData.qty13,rowData.price13)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(newRowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price13',
                                                            key: 'price13',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price13',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableLQHHL.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt13 = rowData.qty13 && rowData.price13
                                                                        ? (this.FloatMulTwo(rowData.qty13,rowData.price13)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8)
                                                                        + Number(rowData.amt9)
                                                                        + Number(rowData.amt10)
                                                                        + Number(rowData.amt11)
                                                                        + Number(rowData.amt12)
                                                                        + Number(newRowData.amt13);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableLQHHL.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt13',
                                                            key: 'amt13',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt13'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税总价(元)',
                                                    dataIndex: 'scenePrice',
                                                    key: 'scenePrice',
                                                    width: 150
                                                },
                                                form: {
                                                    type: 'number'
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            {
                                                name: "diyadd",
                                                icon: "plus",
                                                type: "primary",
                                                label: "新增",
                                                onClick: () => {
                                                    const { myFetch } = this.props;
                                                    myFetch("addZxBuStockPriceItem", { orgID: this.state.orgID, mixType: this.state.lqhhlMixType }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableLQHHL.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    )
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            {
                                                name: 'diydel',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.tableLQHHL.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        ]}
                                    />
                                </div>
                            }
                        },
                        //级配类混合料配合比表
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "级配类混合料配合比表",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <div style={{ padding: '10px' }}>
                                    <Row>
                                        <Col span={8}>
                                            <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                                            <Input disabled='disabled' value={this.state.orgName} style={{ margin: '10px' }}></Input>
                                        </Col>
                                    </Row>
                                    <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        // data={this.state.jplHhlData}
                                        fetchConfig={{
                                            apiName: "getZxBuStockPriceItemList",
                                            otherParams: {
                                                orgID: this.state.orgID,
                                                mixType: this.state.jplHhlMixType
                                            }
                                        }}
                                        {...configItemJplHhl}
                                        wrappedComponentRef={(me) => {
                                            this.tableJplhhl = me;
                                        }}
                                        method={{
                                            tableTdEditSaveCb: (obj) => {
                                                const { myFetch } = this.props;
                                                myFetch("updateZxBuStockPriceItem", obj).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tableJplhhl.refresh()
                                                        } else {
                                                        }
                                                    }
                                                )
                                            }
                                        }}
                                        tableTdEdit={true}
                                        tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                                        // isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'zxBuStockPriceItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '混合料类型',
                                                    dataIndex: 'resID',
                                                    key: 'resID',
                                                    width: 200,
                                                    align: 'center',
                                                    fixed: 'left',
                                                    type: 'select',
                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                },
                                                form: {
                                                    allowClear: false,
                                                    type: 'select',
                                                    field:'resID',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                        linkageFields: {
                                                            resName:'resName'
                                                        }
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'jiPeiLeiHunHeLiaoPeiHeBiBiao'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'resName',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty1',
                                                            key: 'qty1',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price1',
                                                            key: 'price1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt1',
                                                            key: 'amt1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt1'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '碎石(19-31.5mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty2',
                                                            key: 'qty2',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price2',
                                                            key: 'price2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt2',
                                                            key: 'amt2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt2'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '碎石(9.5-19mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty3',
                                                            key: 'qty3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price3',
                                                            key: 'price3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt3',
                                                            key: 'amt3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt3'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '碎石(4.75-9.5mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty4',
                                                            key: 'qty4',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price4',
                                                            key: 'price4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt4',
                                                            key: 'amt4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt4'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '石屑',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty5',
                                                            key: 'qty5',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price5',
                                                            key: 'price5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt5',
                                                            key: 'amt5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt5'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '砾石(19-31.5mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty6',
                                                            key: 'qty6',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price6',
                                                            key: 'price6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price6',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt6 = rowData.qty6 && rowData.price6
                                                                        ? (this.FloatMulTwo(rowData.qty6,rowData.price6)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(newRowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt6',
                                                            key: 'amt6',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt6'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '砾石(9.5-19mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty7',
                                                            key: 'qty7',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price7',
                                                            key: 'price7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price7',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt7 = rowData.qty7 && rowData.price7
                                                                        ? (this.FloatMulTwo(rowData.qty7,rowData.price7)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(newRowData.amt7)
                                                                        + Number(rowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt7',
                                                            key: 'amt7',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt7'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '砾石(4.75-9.5mm)',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty8',
                                                            key: 'qty8',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price8',
                                                            key: 'price8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price8',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tableJplhhl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt8 = rowData.qty8 && rowData.price8
                                                                        ? (this.FloatMulTwo(rowData.qty8,rowData.price8)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5)
                                                                        + Number(rowData.amt6)
                                                                        + Number(rowData.amt7)
                                                                        + Number(newRowData.amt8);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tableJplhhl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt8',
                                                            key: 'amt8',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt8'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税总价(元)',
                                                    dataIndex: 'scenePrice',
                                                    key: 'scenePrice',
                                                    width: 150
                                                },
                                                form: {
                                                    type: 'number'
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            {
                                                name: "diyadd",
                                                icon: "plus",
                                                type: "primary",
                                                label: "新增",
                                                onClick: () => {
                                                    const { myFetch } = this.props;
                                                    myFetch("addZxBuStockPriceItem", { orgID: this.state.orgID, mixType: this.state.jplHhlMixType }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableJplhhl.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    )
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            {
                                                name: 'diydel',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.tableJplhhl.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        ]}
                                    />
                                </div>
                            }
                        },
                        //灰土类混合料配合比表
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "灰土类混合料配合比表",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <div style={{ padding: '10px' }}>
                                    <Row>
                                        <Col span={8}>
                                            <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                                            <Input disabled='disabled' value={this.state.orgName} style={{ margin: '10px' }}></Input>
                                        </Col>
                                    </Row>
                                    <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        data={this.state.htlData}
                                        {...configItemhtl}
                                        wrappedComponentRef={(me) => {
                                            this.tablehtl = me;
                                        }}
                                        method={{
                                            tableTdEditSaveCb: (obj) => {
                                                const { myFetch } = this.props;
                                                myFetch("updateZxBuStockPriceItem", obj).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.tablehtl.refresh()
                                                        } else {
                                                        }
                                                    }
                                                )
                                            }
                                        }}
                                        tableTdEdit={true}
                                        tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                                        fetchConfig={{
                                            apiName: "getZxBuStockPriceItemList",
                                            otherParams: {
                                                orgID: this.state.orgID,
                                                mixType: this.state.htlMixType
                                            }
                                        }}
                                        // isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'zxBuStockPriceItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '混合料类型',
                                                    dataIndex: 'resID',
                                                    key: 'resID',
                                                    width: 200,
                                                    align: 'center',
                                                    type: 'select',
                                                    fixed: 'left',
                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                },
                                                form: {
                                                    allowClear: false,
                                                    type: 'select',
                                                    field:'resID',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                        linkageFields: {
                                                            resName:'resName'
                                                        }

                                                    },
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'huiTuLeiHunHeLiaoPeiHeBiBiao'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'resName',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '石灰',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty1',
                                                            key: 'qty1',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true,
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price1',
                                                            key: 'price1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price1',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt1 = rowData.qty1 && rowData.price1
                                                                        ? (this.FloatMulTwo(rowData.qty1,rowData.price1)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(newRowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt1',
                                                            key: 'amt1',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt1'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '粉煤灰',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty2',
                                                            key: 'qty2',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price2',
                                                            key: 'price2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price2',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt2 = rowData.qty2 && rowData.price2
                                                                        ? (this.FloatMulTwo(rowData.qty2,rowData.price2)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(newRowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt2',
                                                            key: 'amt2',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt2'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '工业矿渣',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty3',
                                                            key: 'qty3',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price3',
                                                            key: 'price3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price3',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt3 = rowData.qty3 && rowData.price3
                                                                        ? (this.FloatMulTwo(rowData.qty3,rowData.price3)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(newRowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt3',
                                                            key: 'amt3',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt3'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '水泥32.5',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty4',
                                                            key: 'qty4',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price4',
                                                            key: 'price4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price4',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt4 = rowData.qty4 && rowData.price4
                                                                        ? (this.FloatMulTwo(rowData.qty4,rowData.price4)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(newRowData.amt4)
                                                                        + Number(rowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt4',
                                                            key: 'amt4',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt4'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '土',
                                                    children: [
                                                        {
                                                            title: '每方混合料用量t',
                                                            dataIndex: 'qty5',
                                                            key: 'qty5',
                                                            width: 130,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'qty5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '不含税材料单价',
                                                            dataIndex: 'price5',
                                                            key: 'price5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'price5',
                                                                onBlur: async ()=>{
                                                                    const rowData = await this.tablehtl.getEditedRowData(false);
                                                                    const newRowData = {};
                                                                    //小计1
                                                                    newRowData.amt5 = rowData.qty5 && rowData.price5
                                                                        ? (this.FloatMulTwo(rowData.qty5,rowData.price5)).toFixed(2) : 0
                                                                    //不含税总价(元)
                                                                    newRowData.scenePrice = Number(rowData.amt1)
                                                                        + Number(rowData.amt2)
                                                                        + Number(rowData.amt3)
                                                                        + Number(rowData.amt4)
                                                                        + Number(newRowData.amt5);
                                                                    //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                                                    await this.tablehtl.setEditedRowData({
                                                                        ...newRowData
                                                                    });
                                                                }
                                                            },
                                                            tdEdit: drawerTitile === '详情' ? false : true
                                                        },
                                                        {
                                                            title: '小计',
                                                            dataIndex: 'amt5',
                                                            key: 'amt5',
                                                            width: 120,
                                                            fieldsConfig: {
                                                                type: 'number',
                                                                field: 'amt5'
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税总价(元)',
                                                    dataIndex: 'scenePrice',
                                                    key: 'scenePrice',
                                                    width: 150
                                                },
                                                form: {
                                                    type: 'number'
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            {
                                                name: "diyadd",
                                                icon: "plus",
                                                type: "primary",
                                                label: "新增",
                                                onClick: () => {
                                                    const { myFetch } = this.props;
                                                    myFetch("addZxBuStockPriceItem", { orgID: this.state.orgID, mixType: this.state.htlMixType }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tablehtl.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    )
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            {
                                                name: 'diydel',
                                                icon: 'delete',
                                                type: 'danger',
                                                label: '删除',
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    confirm({
                                                        content: '确定删除此数据吗?',
                                                        onOk: () => {
                                                            this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    this.tablehtl.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    });
                                                },
                                                hide: (val) => {
                                                    if (drawerTitile === '详情') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        ]}
                                    />
                                </div>
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxBuStockPriceId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 250,
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: 'detail',
                                align: "center",
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    this.setState({
                                        zxBuStockPriceId: obj.rowData.zxBuStockPriceId,
                                        orgName: obj.rowData.orgName
                                    })
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '编制人',
                                width: 150,
                                dataIndex: 'reporter',
                                key: 'reporter',
                                align: "center",
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '编制机构名称',
                                width: 200,
                                dataIndex: 'reportOrgName',
                                key: 'reportOrgName',
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '编制日期',
                                width: 150,
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                format: 'YYYY-MM-DD',
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
                                dataIndex: 'remarks',
                                key: 'remarks',
                                tooltip: 23,
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;