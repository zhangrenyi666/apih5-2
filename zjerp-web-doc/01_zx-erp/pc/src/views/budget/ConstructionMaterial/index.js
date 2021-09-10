import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
// const config = {
//     antd: {
//         rowKey: "zxBuStockPriceId",
//         size: "small",
//     },
//     drawerConfig: {
//         width: window.innerWidth * 0.8,
//     },
//     paginationConfig: {
//         position: "bottom",
//     },
// };
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

        this.state = {
            data: [],
            zxBuStockPriceId: "",
            visible: false,
            dataItemList: [],
            //标后预算材料基价_非复合材料明细
            wcMixType: "31",
            syMixType: "32",
            //标后预算材料基价_非复合材料
            businessType: "3",
            sheetHeadNumber: 2,
            sheetStartNumber: 3,
            orgID: "",
            orgName: "",
            disabled: false,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        };
    }

    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    async Itemcompute() {
        const tabIndex = this.tableMaster.getTabsIndex()
        let rowData = null
        if (tabIndex === '1') {
            rowData = await this.tableMXYW.getEditedRowData(false);
        } else if (tabIndex === '2') {
            rowData = await this.tableMXSY.getEditedRowData(false);
        }
        const newRowData = {};
        //出厂税金单价(mcsgPriceTax) => 材料价格(不含税)(mcsgPrice) * 出厂税率(taxRate)
        newRowData.mcsgPriceTax = (this.FloatMulTwo(rowData.mcsgPrice || 0, Number(rowData.taxRate || 0)) / 100).toFixed(2)
        //运费税金单价(ysFeeTax) => 运费(不含税)(ysFee) * 运费税率(ysTaxRate)

        newRowData.ysFeeTax = (this.FloatMulTwo(rowData.ysFee || 0, Number(rowData.ysTaxRate || 0)) / 100).toFixed(2)
        //不含税到场价(元)(scenePrice) => 材料价格(不含税)(mcsgPrice) + 运费(不含税)(ysFee)
        newRowData.scenePrice = (Number(rowData.mcsgPrice || 0) + Number(rowData.ysFee || 0)).toFixed(2)
        //进项税金(元)(taxAmt) => 数量(qty) * (出厂税金单价(mcsgPriceTax) + 运费税金单价(ysFeeTax))
        const price = Number(newRowData.mcsgPriceTax) + Number(newRowData.ysFeeTax)
        newRowData.taxAmt = (this.FloatMulTwo(rowData.qty || 0, price || 0)).toFixed(2)

        if (tabIndex === '1') {
            await this.tableMXYW.setEditedRowData({
                ...newRowData
            });
        } else if (tabIndex === '2') {
            await this.tableMXSY.setEditedRowData({
                ...newRowData
            });
        }

    }
    render() {
        const { departmentId } = this.state

        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { this.tableMaster = me }}
                    method={{}}
                    antd={{
                        rowKey: "zxBuStockPriceId",
                        size: "small",
                    }}
                    fetchConfig={{
                        apiName: "getZxBuStockPriceList",
                        otherParams: {
                            orgID: departmentId,
                            businessType: this.state.businessType,
                            budgetType: '施工预算'
                        },
                    }}
                    actionBtns={[
                        {
                            name: "add", //内置add del
                            icon: "plus", //icon
                            type: "primary", //类型  默认 primary  [primary dashed danger]
                            label: "新增",
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey("0");
                            },
                            formBtns: [
                                {
                                    name: "cancel", //关闭右边抽屉
                                    type: "dashed", //类型  默认 primary
                                    label: "取消",
                                },
                                {
                                    name: "diysubmit", //内置add del
                                    type: "primary", //类型  默认 primary
                                    label: "保存", //提交数据并且关闭右边抽屉
                                    field: "adddiySubmit",
                                    onClick: async (obj) => {
                                        if (obj.btnCallbackFn.getActiveKey() !== "0") {
                                            //关闭弹窗
                                            obj.btnCallbackFn.closeDrawer();
                                        } else {
                                            this.props.myFetch(
                                                "addZxBuStockPrice",
                                                {
                                                    ...obj._formData,
                                                    budgetType: '施工预算',
                                                    businessType: '3'
                                                },
                                            ).then(({ data, success, message }) => {
                                                if (success) {
                                                    //禁用
                                                    this.setState({
                                                        zxBuStockPriceId: data.zxBuStockPriceId,
                                                        orgID: data.orgID,
                                                        orgName: data.orgName,
                                                        disabled: true,
                                                    });
                                                    if (this.tableMaster) {
                                                        this.tableMaster.refresh();
                                                    }
                                                    //提交后切换成明细页面  并且把添加的数据id添加到表单中
                                                    obj.btnCallbackFn.form.setFieldsValue({
                                                        zxBuStockPriceId: data.zxBuStockPriceId,
                                                    });
                                                    obj.btnCallbackFn.msg.success(message);
                                                    obj.btnCallbackFn.setActiveKey("1");
                                                } else {
                                                    obj.btnCallbackFn.msg.error(message);
                                                }
                                            })
                                        }

                                        // const { data, success, message } = await this.props.myFetch('addZxBuStockPrice', await obj.qnnTableInstance.getQnnForm().getValues())
                                        // if (success) {
                                        //     console.log(data)
                                        //     this.form.refresh()
                                        // } else {
                                        //     Msg.error(message)
                                        // }
                                    },
                                    //如果tab面是第一个显示,否则不显示
                                    // hide: (obj) => {
                                    //     let index = obj.btnCallbackFn.getActiveKey();
                                    //     if (index === "0") {
                                    //         return true;
                                    //     } else {
                                    //         return false;
                                    //     }
                                    // },
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1" || index === "2") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                            ],
                        },
                        {
                            name: "edit", //内置add del
                            icon: "edit", //icon
                            type: "primary", //类型  默认 primary  [primary dashed danger]
                            label: "修改",
                            disabled: (obj) => {
                                const rowData = obj.qnnTableInstance.getSelectedRows()
                                if (rowData && rowData.length === 1) {
                                    if (rowData[0].status === '已审核') {
                                        return true
                                    } else {
                                        return false
                                    }
                                } else {
                                    return true
                                }
                            },
                            formBtns: [
                                {
                                    name: "cancel", //关闭右边抽屉
                                    type: "dashed", //类型  默认 primary
                                    label: "取消",
                                },
                                {
                                    name: "diySubmit", //内置add del
                                    type: "primary", //类型  默认 primary
                                    label: "保存", //提交数据并且关闭右边抽屉
                                    onClick: (obj) => {

                                    },
                                    //如果tab面是第一个显示,否则不显示
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1" || index === "2") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                            ],

                        },
                        {
                            name: "del", //内置add del
                            icon: "delete", //icon
                            type: "danger", //类型  默认 primary  [primary dashed danger]
                            label: "删除",
                            disabled: (obj) => {
                                let data = obj.qnnTableInstance.getSelectedRows();
                                if (data.length > 0) {
                                    for (let m = 0; m < data.length; m++) {
                                        if (data[m].status === '已审核') {
                                            return true;
                                        }
                                    }
                                    return false
                                } else {
                                    return true;
                                }
                            },
                            fetchConfig: {
                                //ajax配置
                                apiName: "batchDeleteUpdateZxBuStockPrice",
                            },
                        },
                    ]}
                    methods={
                        {
                            itemQueryFunc: (obj) => {
                                const tabIndex = obj.qnnFormProps.qnnformData.qnnFormProps.tableFns.getTabsIndex()
                                return {
                                    mixType: tabIndex === '1' ? this.state.wcMixType : this.state.syMixType,
                                    orgID: obj.qnnFormProps.qnnformData.qnnFormProps.tableFns.qnnForm.form.getFieldsValue()
                                        .orgID,
                                    stockPriceID: obj.qnnFormProps.qnnformData.qnnFormProps.tableFns.qnnForm.form.getFieldsValue()
                                        .zxBuStockPriceId
                                };
                            }
                        }
                    }

                    formConfig={[
                        {
                            table: {
                                title: "项目名称",
                                dataIndex: "orgName",
                                key: "orgName",
                                align: "center",
                                // type: 'select',
                                filter: true,
                                onClick: "detail",
                            },
                            form: {
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID'
                                },
                                editDisabled: true,
                                fetchConfig: {
                                    apiName: 'getZxBuProjectTypeCheckOver',
                                    otherParams: {
                                        orgID: departmentId,
                                    }
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                                onChange: (val, obj) => {
                                    const companyName = obj.itemData.companyName
                                    obj.form.setFieldsValue({
                                      reportOrgName:companyName
                                    })
                                  }
                            }
                        },
                        {
                            table: {
                                title: "编制人",
                                dataIndex: "reporter",
                                key: "reporter",
                                align: "center",
                            },
                        },
                        {
                            table: {
                                title: "期次",
                                dataIndex: "period",
                                key: "period",
                                align: "center",
                            },
                        },
                        {
                            table: {
                                title: "编制机构名称",
                                dataIndex: "reportOrgName",
                                key: "reportOrgName",
                                align: "center",
                            },
                        },
                        {
                            table: {
                                title: "编制日期",
                                dataIndex: "reportDate",
                                key: "reportDate",
                                align: "center",
                                format: "YYYY-MM-DD",
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                            },
                            form: {
                                field: 'reportDate',
                                type: 'date',
                                spanForm: 12,
                                initialValue: new Date(),
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: "状态",
                                dataIndex: "status",
                                key: "status",
                                align: "center",
                            },
                        },
                        {
                            table: {
                                title: "备注",
                                dataIndex: "remarks",
                                key: "remarks",
                                align: "center",
                            },
                        },
                    ]}
                    
                    formIsChangeedAlertTextContent={false}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "项目信息",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        field: "zxBuStockPriceId",
                                        type: "string",
                                        hide: true,
                                    },
                                    {
                                        field: "orgName",
                                        type: "string",
                                        hide: true,
                                    },
                                    {
                                        field: "businessType",
                                        type: "string",
                                        hide: true,
                                        initialValue: this.state.businessType,
                                    },
                                    {
                                        label: "项目名称",
                                        field: "orgID",
                                        type: "select",
                                        optionConfig: {
                                            label: "orgName",
                                            value: "orgID",
                                            linkageFields: {
                                                orgName: "orgName",
                                                comID: "companyId",
                                                comName: "companyName",
                                            },
                                        },
                                        editDisabled: true,
                                        fetchConfig: {
                                            apiName: "getZxBuProjectTypeCheckOver",
                                            otherParams: {
                                                orgID: departmentId,
                                            },
                                        },
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: "编制人",
                                        field: "reporter",
                                        type: "string",
                                        initialValue: "管理员",
                                        editDisabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: "期次",
                                        field: "periodDate",
                                        type: "month",
                                        editDisabled: true,
                                        span: 12,
                                        required: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: "编制机构名称",
                                        field: "reportOrgName",
                                        type: "string",
                                        disabled: true,
                                        initialValue: "北京建筑分公司",
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: "编制日期",
                                        field: "reportDate",
                                        type: "date",
                                        span: 12,
                                        editDisabled: true,
                                        initialValue: new Date(),
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: "备注",
                                        field: "remarks",
                                        type: "textarea",
                                        span: 24,
                                        editDisabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 4 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 },
                                            },
                                        },
                                    },
                                    {
                                        type: "string",
                                        field: "comID",
                                        hide: true,
                                    },
                                    {
                                        type: "string",
                                        field: "comName",
                                        hide: true,
                                    },
                                ],
                            },
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "已完工程",
                            disabled: function (obj) {
                                return obj.clickCb.rowInfo.name === "edit" ||
                                    obj.clickCb.rowInfo.name === "detail"
                                    ? false
                                    : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId");
                            },
                            content: {
                                
                                formConfig: [
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableMX",
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "zxBuStockPriceItemId",
                                                size: "small",
                                            },
                                            drawerConfig: {
                                                width: "1000px",
                                            },
                                            paginationConfig: {
                                                position: "bottom",
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableMXYW = me;
                                            },
                                            fetchConfig: {
                                                apiName: "getZxBuStockPriceItemList",
                                                otherParams: 'bind:itemQueryFunc',
                                            },
                                            isShowRowSelect: true,
                                            tableTdEdit: true,
                                            method: {
                                                tableTdEditSaveCb: async (obj) => {
                                                    const tabIndex = this.tableMaster.getTabsIndex()
                                                    let rowData = null
                                                    if (tabIndex === '1') {
                                                        rowData = await this.tableMXYW.getEditedRowData(false);
                                                    } else if (tabIndex === '2') {
                                                        rowData = await this.tableMXSY.getEditedRowData(false);
                                                    }

                                                    const { myFetch } = this.props;
                                                    myFetch("updateZxBuStockPriceItem", rowData).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                if (tabIndex === '1') {
                                                                    this.tableMXYW.refresh();
                                                                } else if (tabIndex === '2') {
                                                                    this.tableMXSY.refresh();
                                                                }
                                                            } else {
                                                            }
                                                        }
                                                    );
                                                },
                                            },
                                            tableTdEditSaveCb: "bind:tableTdEditSaveCb",
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: "材料编号",
                                                        dataIndex: "resCode",
                                                        key: "resCode",
                                                        align: "center",
                                                        width: 100,
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "材料名称",
                                                        dataIndex: "resName",
                                                        key: "resName",
                                                        align: "center",
                                                        fixed: "left",
                                                        width: 150,
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "单位",
                                                        dataIndex: "unit",
                                                        key: "unit",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 80,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "数量",
                                                        dataIndex: "qty",
                                                        key: "qty",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 80,
                                                        fieldConfig: {
                                                            type: "number",
                                                            onBlur: () => {
                                                                this.Itemcompute();
                                                            }
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "规格型号",
                                                        dataIndex: "spec",
                                                        key: "spec",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 200,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "参考值",
                                                        dataIndex: "referValue",
                                                        key: "referValue",
                                                        width: 100,
                                                        align: "center",
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "是否调差",
                                                        dataIndex: "isAdjustment",
                                                        key: "isAdjustment",
                                                        align: "center",
                                                        tdEdit: true,
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            return data === "0" ? "是" : "否";
                                                        },
                                                        fieldsConfig: {
                                                            type: "select",
                                                            allowClear: false,
                                                            optionConfig: {
                                                                label: "label",
                                                                value: "value",
                                                            },
                                                            optionData: [
                                                                { label: "是", value: "0" },
                                                                { label: "否", value: "1" },
                                                            ],
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "出厂价(元)",
                                                        // tdEdit: true,
                                                        children: [
                                                            {
                                                                title: "材料价格(不含税)",
                                                                dataIndex: "mcsgPrice",
                                                                key: "mcsgPrice",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldConfig: {
                                                                    type: "number",
                                                                    onBlur: () => {
                                                                        this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "出厂税率",
                                                                dataIndex: "taxRate",
                                                                key: "taxRate",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    allowClear: false,
                                                                    type: "select",
                                                                    optionConfig: {
                                                                        label: "itemName",
                                                                        value: "itemId",
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: "getBaseCodeSelect",
                                                                        otherParams: {
                                                                            itemId: "shuiLv",
                                                                        },
                                                                    },
                                                                    placeholder: "请选择",
                                                                    onChange: async (val, obj) => {
                                                                        await obj.qnnTableInstance.setEditedRowData({
                                                                            taxRate: val
                                                                        })
                                                                        await this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "出厂税金单价",
                                                                dataIndex: "mcsgPriceTax",
                                                                key: "mcsgPriceTax",
                                                                align: "center",
                                                                width: 100,
                                                            },
                                                        ],
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "运费(元)",
                                                        children: [
                                                            {
                                                                dataIndex: "ysFee",
                                                                title: "运费(不含税)",
                                                                align: "center",
                                                                key: "ysFee",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldConfig: {
                                                                    type: "number",
                                                                    onBlur: () => {
                                                                        this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "运费税率",
                                                                dataIndex: "ysTaxRate",
                                                                key: "ysTaxRate",
                                                                // type: "select",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    allowClear: false,
                                                                    type: "select",
                                                                    optionConfig: {
                                                                        label: "itemName",
                                                                        value: "itemId",
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: "getBaseCodeSelect",
                                                                        otherParams: {
                                                                            itemId: "shuiLv",
                                                                        },
                                                                    },
                                                                    placeholder: "请选择",
                                                                    onChange: async (val, obj) => {
                                                                        await obj.qnnTableInstance.setEditedRowData({
                                                                            ysTaxRate: val
                                                                        })
                                                                        await this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "运费税金单价",
                                                                dataIndex: "ysFeeTax",
                                                                key: "ysFeeTax",
                                                                width: 100,
                                                                align: "center",
                                                            },
                                                        ],
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "不含税到场价(元)",
                                                        dataIndex: "scenePrice",
                                                        key: "scenePrice",
                                                        align: "center",
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "进项税金(元)",
                                                        dataIndex: "taxAmt",
                                                        key: "taxAmt",
                                                        align: "center",
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                // {
                                                //     name: "export", //内置add del
                                                //     type: "primary", //类型  默认 primary  [primary dashed danger]
                                                //     label: "导入",
                                                //     onClick: () => {
                                                //         this.setState({
                                                //             visible: true,
                                                //         });
                                                //     },
                                                // },
                                                {
                                                    name: "diyadd",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "新增",
                                                    onClick: (obj) => {
                                                        const { myFetch } = this.props;
                                                        myFetch("addZxBuStockPriceItem", {
                                                            orgID: this.tableMaster.form.getFieldValue(
                                                                "orgID"
                                                            ),
                                                            mixType: this.state.wcMixType,
                                                            stockPriceID: obj.qnnFormProps.qnnformData.qnnFormProps.tableFns.qnnForm.form.getFieldsValue()
                                                                .zxBuStockPriceId
                                                        }).then(({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableMXYW.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        });
                                                    },
                                                },
                                                {
                                                    name: "diydel",
                                                    icon: "delete",
                                                    type: "danger",
                                                    label: "删除",
                                                    disabled:'bind:_actionBtnNoSelected',
                                                    onClick: (obj) => {
                                                        confirm({
                                                            content: "确定删除此数据吗?",
                                                            onOk: () => {
                                                                this.props
                                                                    .myFetch(
                                                                        "batchDeleteUpdateZxBuStockPriceItem",
                                                                        obj.selectedRows
                                                                    )
                                                                    .then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            this.tableMXYW.refresh();
                                                                            this.tableMXYW.clearSelectedRows();
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                            },
                                                        });
                                                    },
                                                },
                                                // {
                                                //     name: "diydel",
                                                //     icon: "delete",
                                                //     type: "danger",
                                                //     label: "获取",
                                                //     onClick: (obj) => {
                                                //         console.log(this.state.orgID)
                                                //     }
                                                // }
                                            ],
                                        },
                                    },
                                ],
                            },
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "剩余工程",
                            disabled: function (obj) {
                                return obj.clickCb.rowInfo.name === "edit" ||
                                    obj.clickCb.rowInfo.name === "detail"
                                    ? false
                                    : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId");
                            },
                            content: {
                                
                                formConfig: [
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableMX",
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "zxBuStockPriceItemId",
                                                size: "small",
                                            },
                                            drawerConfig: {
                                                width: "1000px",
                                            },
                                            paginationConfig: {
                                                position: "bottom",
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableMXSY = me;
                                            },
                                            fetchConfig: {
                                                apiName: "getZxBuStockPriceItemList",
                                                otherParams: 'bind:itemQueryFunc',
                                            },
                                            isShowRowSelect: true,
                                            tableTdEdit: true,
                                            method: {
                                                tableTdEditSaveCb: async (obj) => {
                                                    const rowData = await this.tableMXSY.getEditedRowData()
                                                    const { myFetch } = this.props;
                                                    myFetch("updateZxBuStockPriceItem", rowData).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableMXSY.refresh();
                                                            } else {
                                                            }
                                                        }
                                                    );
                                                },
                                            },
                                            tableTdEditSaveCb: "bind:tableTdEditSaveCb",
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: "材料编号",
                                                        dataIndex: "resCode",
                                                        key: "resCode",
                                                        align: "center",
                                                        width: 100,
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "材料名称",
                                                        dataIndex: "resName",
                                                        key: "resName",
                                                        align: "center",
                                                        fixed: "left",
                                                        width: 150,
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "单位",
                                                        dataIndex: "unit",
                                                        key: "unit",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 80,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "数量",
                                                        dataIndex: "qty",
                                                        key: "qty",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 80,
                                                        fieldConfig: {
                                                            type: "number",
                                                            onBlur: () => {
                                                                this.Itemcompute();
                                                            }
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "规格型号",
                                                        dataIndex: "spec",
                                                        key: "spec",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 200,
                                                        fieldConfig: {
                                                            type: "string",
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "参考值",
                                                        dataIndex: "referValue",
                                                        key: "referValue",
                                                        width: 100,
                                                        align: "center",
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "是否调差",
                                                        dataIndex: "isAdjustment",
                                                        key: "isAdjustment",
                                                        align: "center",
                                                        tdEdit: true,
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            return data === "0" ? "是" : "否";
                                                        },
                                                        fieldsConfig: {
                                                            type: "select",
                                                            allowClear: false,
                                                            optionConfig: {
                                                                label: "label",
                                                                value: "value",
                                                            },
                                                            optionData: [
                                                                { label: "是", value: "0" },
                                                                { label: "否", value: "1" },
                                                            ],
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "出厂价(元)",
                                                        // tdEdit: true,
                                                        children: [
                                                            {
                                                                title: "材料价格(不含税)",
                                                                dataIndex: "mcsgPrice",
                                                                key: "mcsgPrice",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldConfig: {
                                                                    type: "number",
                                                                    onBlur: () => {
                                                                        this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "出厂税率",
                                                                dataIndex: "taxRate",
                                                                key: "taxRate",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    allowClear: false,
                                                                    type: "select",
                                                                    optionConfig: {
                                                                        label: "itemName",
                                                                        value: "itemId",
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: "getBaseCodeSelect",
                                                                        otherParams: {
                                                                            itemId: "shuiLv",
                                                                        },
                                                                    },
                                                                    placeholder: "请选择",
                                                                    onChange: async (val, obj) => {
                                                                        await obj.qnnTableInstance.setEditedRowData({
                                                                            taxRate: val
                                                                        })
                                                                        await this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "出厂税金单价",
                                                                dataIndex: "mcsgPriceTax",
                                                                key: "mcsgPriceTax",
                                                                align: "center",
                                                                width: 100,
                                                            },
                                                        ],
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "运费(元)",
                                                        children: [
                                                            {
                                                                dataIndex: "ysFee",
                                                                title: "运费(不含税)",
                                                                align: "center",
                                                                key: "ysFee",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldConfig: {
                                                                    type: "number",
                                                                    onBlur: () => {
                                                                        this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "运费税率",
                                                                dataIndex: "ysTaxRate",
                                                                key: "ysTaxRate",
                                                                // type: "select",
                                                                align: "center",
                                                                tdEdit: true,
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    allowClear: false,
                                                                    type: "select",
                                                                    optionConfig: {
                                                                        label: "itemName",
                                                                        value: "itemId",
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: "getBaseCodeSelect",
                                                                        otherParams: {
                                                                            itemId: "shuiLv",
                                                                        },
                                                                    },
                                                                    placeholder: "请选择",
                                                                    onChange: async (val, obj) => {
                                                                        await obj.qnnTableInstance.setEditedRowData({
                                                                            ysTaxRate: val
                                                                        })
                                                                        await this.Itemcompute();
                                                                    },
                                                                },
                                                            },
                                                            {
                                                                title: "运费税金单价",
                                                                dataIndex: "ysFeeTax",
                                                                key: "ysFeeTax",
                                                                width: 100,
                                                                align: "center",
                                                            },
                                                        ],
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "不含税到场价(元)",
                                                        dataIndex: "scenePrice",
                                                        key: "scenePrice",
                                                        align: "center",
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "进项税金(元)",
                                                        dataIndex: "taxAmt",
                                                        key: "taxAmt",
                                                        align: "center",
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                // {
                                                //     name: "export", //内置add del
                                                //     type: "primary", //类型  默认 primary  [primary dashed danger]
                                                //     label: "导入",
                                                //     onClick: () => {
                                                //         this.setState({
                                                //             visible: true,
                                                //         });
                                                //     },
                                                // },
                                                {
                                                    name: "diyadd",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "新增",
                                                    onClick: (obj) => {
                                                        const { myFetch } = this.props;
                                                        myFetch("addZxBuStockPriceItem", {
                                                            orgID: this.tableMaster.form.getFieldValue(
                                                                "orgID"
                                                            ),
                                                            mixType: this.state.syMixType,
                                                            stockPriceID: obj.qnnFormProps.qnnformData.qnnFormProps.tableFns.qnnForm.form.getFieldsValue()
                                                                .zxBuStockPriceId
                                                        }).then(({ data, success, message }) => {
                                                            if (success) {
                                                                this.tableMXSY.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        });
                                                    },
                                                },
                                                {
                                                    name: "diydel",
                                                    icon: "delete",
                                                    type: "danger",
                                                    label: "删除",
                                                    disabled:'bind:_actionBtnNoSelected',
                                                    onClick: (obj) => {
                                                        confirm({
                                                            content: "确定删除此数据吗?",
                                                            onOk: () => {
                                                                this.props
                                                                    .myFetch(
                                                                        "batchDeleteUpdateZxBuStockPriceItem",
                                                                        obj.selectedRows
                                                                    )
                                                                    .then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            this.tableMXSY.refresh();
                                                                            this.tableMXSY.clearSelectedRows();
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                            },
                                                        });
                                                    },
                                                },
                                            ],
                                        },
                                    },
                                ],
                            },
                        },
                    ]}
                />
            </div>
        )
    }
}

export default index;
