import React, { Component } from "react";

import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";

import { Button, message as Msg } from "antd";

const config = {
    antd: {
        rowKey: 'zxSaProjectSettleAnalysisId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
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
    }
};

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tabsProjectId: '',
            queryData: null,
            tabTwoData: null,
            tabOneMainID: null,
        }
    }

    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { tabsProjectId, queryData, tabTwoData } = this.state
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { this.form = me }}
                    method={{}}
                    {...config}
                    formConfig={[
                        {
                            label: '项目名称',
                            field: 'orgID',
                            type: 'select',
                            showSearch: true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId',
                                linkageFields: {
                                    orgName: 'departmentName',
                                    companyId: 'companyId',
                                    companyName: 'companyName',
                                    comID: 'companyId'
                                }
                            },
                            // disabled:
                            editDisabled: true,
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            // fetchConfig: {
                            //     apiName: 'getSysCompanyBySelect'
                            // },
                            placeholder: '请选择',
                            span: 6
                        },
                        {
                            type: 'month',
                            label: '期次 ',
                            field: 'period', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            required: false,
                            format: "YYYY-MM",
                            showTime: false, //不显示时间
                            scope: false, //是否可选择范围
                            span: 6
                        },
                        {
                            type: 'string',
                            label: '合同名称 ',
                            field: 'contractName', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            required: false,
                            span: 6
                        },
                        {
                            type: 'string',
                            label: '合同编号 ',
                            field: 'contractNo', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            required: false,
                            span: 6
                        },
                        {
                            label: '结算类型',
                            field: 'billType',
                            type: 'select',
                            showSearch: true,
                            // optionConfig: {
                            //     label: 'projectName',
                            //     value: 'projectId'
                            // },
                            // fetchConfig: {
                            //     apiName: 'getSysCompanyBySelect'
                            // },
                            placeholder: '请选择',
                            span: 6
                        },
                        {
                            type: 'date',
                            label: '填报开始日期',
                            field: 'startDate', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            required: false,
                            format: "YYYY-MM-DD",
                            showTime: false, //不显示时间
                            scope: false, //是否可选择范围
                            span: 6
                        },
                        {
                            type: 'date',
                            label: '填报结束日期',
                            field: 'editTime', //唯一的字段名 ***必传
                            placeholder: '请选择',
                            required: false,
                            format: "YYYY-MM-DD",
                            showTime: false, //不显示时间
                            scope: false, //是否可选择范围
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'aa',
                            Component: obj => {
                                return (
                                    <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.setState({
                                            queryData: {
                                                ...obj.form.getFieldsValue()
                                            }
                                        })
                                    }}>查询</Button>
                                    </div>
                                );
                            },
                            span: 6
                        }
                    ]}
                />
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
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
                                    name: 'diySubmit',//内置add del
                                    field: 'diySubmit',
                                    label: '保存',
                                    // fetchConfig: {
                                    //     apiName: 'addZxSaProjectSettleAnalysis',
                                    // },
                                    onClick: (obj) => {
                                        if (obj.btnCallbackFn.getActiveKey() === "1") {
                                            obj.btnCallbackFn.closeDrawer();
                                        } else {
                                            obj.btnCallbackFn.fetch(
                                                "addZxSaProjectSettleAnalysis",
                                                {
                                                    ...obj._formData,
                                                    period: new Date().getTime(obj._formData.period)
                                                },
                                            ).then((
                                                { data, success, message }) => {
                                                if (success) {
                                                    this.setState({
                                                        tabOneMainID: data.zxSaProjectSettleAnalysisId
                                                    }, () => {
                                                        //禁用
                                                        if (this.table) {
                                                            this.table.refresh();
                                                        }
                                                        obj.btnCallbackFn.msg.success(message);
                                                        obj.btnCallbackFn.setActiveKey("1");
                                                    })
                                                } else {
                                                    obj.btnCallbackFn.msg.error(message);
                                                }
                                            })
                                        }
                                    },
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
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
                            name: "edit",
                            label: "修改",
                            field: "edit",
                            type: "primary",
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',
                                    label: '取消',
                                    field: "cancel",
                                },
                                {
                                    name: 'diySubmit',
                                    field: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    // fetchConfig: {//ajax配置
                                    //     apiName: 'updateZxSaProjectSettleAnalysisL',
                                    // }
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.fetch(
                                            "updateZxSaProjectSettleAnalysisL",
                                            {
                                                ...obj._formData,
                                                period: new Date().getTime(obj._formData.period)
                                            },
                                        ).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    if (this.table) {
                                                        this.table.refresh();
                                                    }
                                                    obj.btnCallbackFn.msg.success(message);
                                                    //清空选项
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                    //如果在明细(第二个)模块保存提交则关闭弹出框
                                                    if (obj.btnCallbackFn.getActiveKey() === "1") {
                                                        //关闭弹窗
                                                        obj.btnCallbackFn.closeDrawer();
                                                    } else {
                                                        //否则跳入到第二表单中
                                                        obj.btnCallbackFn.setActiveKey("1");
                                                    }
                                                } else {
                                                    obj.btnCallbackFn.msg.error(message);
                                                }
                                            })
                                    },
                                    //如果tab面是第一个显示,否则不显示
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
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
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            field: "del",
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxSaProjectSettleAnalysis',
                            }
                        },
                    ]}
                    fetchConfig={{
                        apiName: 'getZxSaProjectSettleAnalysisList',
                        otherParams: {
                            ...queryData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSaProjectSettleAnalysisId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '结算清单编号',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                width: 50,
                                tooltip: 10
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'billID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 50,
                                onClick: 'detail'
                            },
                            isInForm: false
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
                                title: '结算期次',
                                dataIndex: 'period',
                                key: 'period',
                                format: 'YYYY-MM',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'contractID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'contractNo',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'secondID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '结算类型',
                                dataIndex: 'billType',
                                key: 'billType',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                format: 'YYYY-MM-DD',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本期结算金额(元)',
                                dataIndex: 'thisAmt',
                                key: 'thisAmt',
                                width: 50,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '开累结算金额(元)',
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                width: 50,
                            },
                            isInForm: false
                        },
                    ]}
                    onTabsChange={(tabKey, args) => {
                        if (tabKey === '1') {
                            this.table.getQnnForm().getValues().then(val => {
                                if (val.zxSaProjectSettleAnalysisId) {
                                    this.props.myFetch('getZxSaProjectSettleAnalysisItemList', {
                                        mainID: val.zxSaProjectSettleAnalysisId
                                    }).then(res => {
                                        if (res.success) this.tableMX.setTableData(res.data)
                                    })
                                } else {
                                    if (val.contractID && val.period) {
                                        this.props.myFetch('getZxSaProjectSettleAuditItemByContractId', {
                                            contractID: val.contractID,
                                            period: val.period
                                        }).then(res => {
                                            const list = res.data.filter(ele =>
                                                ele.statisticsName === '合计含税结算金额（小写）' ||
                                                ele.statisticsName === '合计含税结算金额（大写）' ||
                                                ele.statisticsName === '应付工程款（小写）' ||
                                                ele.statisticsName === '应付工程款（大写）'
                                            )

                                            this.props.myFetch('getZxSaProjectSettleAnalysisItemLiJiInfo', {
                                                contractID: val.contractID,
                                            }).then(ret => {
                                                const tableList = list.concat(ret.data).map((item, index) => {
                                                    return {
                                                        ...item,
                                                        index
                                                    }
                                                })

                                                this.tableMX.setTableData(tableList)
                                            })

                                        })
                                    } else {
                                        Msg.error('请填填写合同编号！')
                                    }
                                }
                            })
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxSaProjectSettleAnalysisId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        field: "zxSaProjectSettleAnalysisId", // 主键ID
                                        type: "string",
                                        hide: true,
                                    },
                                    {
                                        label: '结算清单编号',
                                        field: "billNo",
                                        type: 'string',
                                        disabled: true,
                                        span: 12,
                                    },
                                    {
                                        field: "orgName", // 主键ID
                                        type: "string",
                                        hide: true,
                                    },
                                    {
                                        label: "项目名称",
                                        field: "orgID",
                                        type: "select",
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                companyId: 'companyId',
                                                companyName: 'companyName',
                                                comID: 'companyId'
                                            }
                                        },
                                        // disabled:
                                        editDisabled: true,
                                        fetchConfig: {
                                            apiName: 'getSysProjectBySelect',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            obj.fns.setValues(
                                                {
                                                    orgName: obj.children
                                                }
                                            )
                                            this.setState({
                                                tabsProjectId: val
                                            })
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
                                        field: "contractID", // 主键ID
                                        type: "string",
                                        hide: true,
                                    },
                                    {
                                        label: '合同编号',
                                        field: 'contractNo',
                                        type: 'selectByQnnTable',
                                        span: 24,
                                        required: true,
                                        disabled: (obj) => {
                                            return obj.form.getFieldValue('orgID') ? false : true
                                        },
                                        optionConfig: {
                                            label: 'contractName',
                                            value: 'contractNo'
                                        },
                                        onChange: (value, obj) => {
                                            obj.fns.setValues(
                                                {
                                                    ...obj.itemData,
                                                    businessDate: obj.itemData.createTime
                                                }
                                            )
                                        },
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "billNo"
                                            },
                                            selectType: "radio", //默认radio单选  值为 string
                                            fetchConfig: tabsProjectId ? {
                                                apiName: "getZxSaProjectContractNoList",
                                                otherParams: {
                                                    orgID: tabsProjectId
                                                }
                                            } : null,
                                            searchBtnsStyle: "inline",
                                            formItemLayoutSearch: {
                                                labelCol: {
                                                    sm: { span: 8 }
                                                },
                                                wrapperCol: {
                                                    sm: { span: 16 }
                                                }
                                            },
                                            formConfig: [
                                                {
                                                    // isInSearch: true,
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "billNo",
                                                        title: "结算单编号",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    // isInSearch: true,
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "period",
                                                        title: "期次",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    // isInSearch: true,
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "contractNo",
                                                        title: "合同编号",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    // isInSearch: true,
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "contractName",
                                                        title: "合同名称",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    // isInSearch: true,
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "orgName",
                                                        title: "项目名称",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                            ]
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 18 },
                                                sm: { span: 4 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 4 },
                                                sm: { span: 20 },
                                            },
                                        },
                                    },
                                    {
                                        label: '结算期次',
                                        field: "period",
                                        type: 'month',
                                        placeholder: '请选择',
                                        disabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '合同名称',
                                        field: "contractName",
                                        type: 'string',
                                        disabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '合同乙方',
                                        field: "secondName",
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '结算类型',
                                        field: "billType",
                                        type: 'select',
                                        span: 12,
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '中期', value: '0' },
                                            { label: '最终', value: '1' },
                                            { label: '返还保证金', value: '2' }
                                        ],
                                    },
                                    {
                                        label: '业务日期',
                                        field: "businessDate",
                                        span: 12,
                                        required: true,
                                        type: 'date',
                                    },
                                    {
                                        label: '填报人',
                                        field: "reportPerson",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '施工内容',
                                        field: 'content',
                                        type: 'textarea',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '备注',
                                        span: 12,
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '计算人',
                                        field: "countPerson",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '复核人',
                                        field: "reCountPerson",
                                        type: 'string',
                                        span: 12,
                                    },
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "明细",
                            disabled: function (obj) {
                                return obj.clickCb.rowInfo.name === "edit" ||
                                    obj.clickCb.rowInfo.name === "detail"
                                    ? false
                                    : !obj.btnCallbackFn.form.getFieldValue("zxSaProjectSettleAnalysisId");
                            },
                            content: {
                                formConfig: [
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableMX",
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "index",
                                                size: "small",
                                            },
                                            isShowRowSelect: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableMX = me;
                                            },
                                            fetchConfig: {
                                                // apiName: "getZxBuStockPriceItemList",
                                                // otherParams: (obj) => {
                                                // },
                                            },
                                            data: { tabTwoData },
                                            tableTdEdit: true,
                                            method: {
                                                tableTdEditSaveCb: (obj) => {
                                                    // const { myFetch } = this.props;
                                                },
                                            },
                                            tableTdEditSaveCb: "bind:tableTdEditSaveCb",
                                            rowDisabledCondition: (rowData) => {
                                                const { index } = rowData
                                                return index === 0 || index === 1 || index === 2 || index === 3
                                            },
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: "统计项",
                                                        dataIndex: "statisticsName",
                                                        key: "statisticsName",
                                                        align: "center",
                                                        width: 100,
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                            disabled: (obj) => {
                                                                return true
                                                            }
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "本期(元)",
                                                        dataIndex: "thisAmt",
                                                        key: "thisAmt",
                                                        align: "center",
                                                        fixed: "left",
                                                        width: 150,
                                                        tdEdit: true,
                                                        fieldConfig: {
                                                            type: "string",
                                                            disabled: (obj) => {
                                                                return obj.rowIndex === 0 || obj.rowIndex === 1 || obj.rowIndex === 2 || obj.rowIndex === 3
                                                            },
                                                            onBlur: (e, obj) => {
                                                                obj.qnnTableInstance.getEditedRowData().then(val => {
                                                                    const values = { ...val }
                                                                    values.totalAmt = (+values.totalAmt + +e.target.value) + ''
                                                                    obj.qnnTableInstance.setEditedRowData(values)
                                                                })

                                                            },
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "开累(元)",
                                                        dataIndex: "totalAmt",
                                                        key: "totalAmt",
                                                        align: "center",
                                                        fixed: "left",
                                                        tdEdit: true,
                                                        width: 80,
                                                        fieldConfig: {
                                                            type: "string",
                                                            disabled: (obj) => {
                                                                return true
                                                            }
                                                        },
                                                    },
                                                },
                                            ],
                                            // actionBtns: [
                                            // ],
                                        },
                                    },
                                    {
                                        type: 'component',
                                        field: 'aa',
                                        fixed: 'right',
                                        Component: obj => {
                                            return (
                                                <div style={{ textAlign: 'start', padding: '10px', border: 'none' }} className={'rightDrawer_btns__pmuP5'}>
                                                    {
                                                        obj.clickCb.rowInfo.name === "detail" ? null : <Button type="primary" onClick={async () => {
                                                            const val = await obj.btnCallbackFn.getValues()
                                                            const { success, message } = await this.props.myFetch('updateZxSaProjectSettleAnalysisAndItem', {
                                                                zxSaProjectSettleAnalysisId:    // 判断新增还是修改 
                                                                    val.zxSaProjectSettleAnalysisId ?
                                                                        val.zxSaProjectSettleAnalysisId : this.state.tabOneMainID,

                                                                itemList: val.qnnTableMX    // 表格数据作为参数
                                                            })

                                                            if (success) {
                                                                Msg.success(message)
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }}>保存</Button>
                                                    }
                                                </div>
                                            );
                                        },
                                    }
                                ],
                            },
                        },
                    ]}
                ></QnnTable>
            </div >
        )
    }
}


export default index