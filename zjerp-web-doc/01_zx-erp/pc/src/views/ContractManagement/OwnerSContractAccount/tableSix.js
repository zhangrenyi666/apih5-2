import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import s from './tableSix.less';
const config = {
    antd: {
        rowKey: 'id',
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
            rowData: props?.selectedRows?.[0] || props?.rowData || {},
            drawerDetailTitle: props?.drawerDetailTitle || ''
        }
    }
    render () {
        const { rowData, drawerDetailTitle } = this.state;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCtBalanceList',
                        otherParams: {
                            orgID: rowData?.orgID
                        }
                    }}
                    actionBtns={drawerDetailTitle === '详情' ? [] : [
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
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
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
                                        apiName: 'addZxCtBalance',
                                        success: (obj) => {
                                            if (obj.response.success) {
                                                this.table.setDeawerValues({ ...obj.response.data });
                                                this.table.setTabsIndex('1');
                                                this.table.refresh();
                                            }
                                        }
                                    },
                                    isCloseDrawer: false,
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('id')) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                    hide: (obj) => {
                                        if (obj.btnCallbackFn.getActiveKey() === '0') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    }
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxCtBalance',
                                        success: (obj) => {
                                            if (obj.response.success) {
                                                this.table.setTabsIndex('1');
                                                this.table.refresh();
                                            }
                                        }
                                    },
                                    hide: (obj) => {
                                        if (obj.btnCallbackFn.getActiveKey() === '0') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    isCloseDrawer: false,
                                }
                            ]
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtBalance',
                            },
                        }
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'id',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        initialValue: () => {
                                            return rowData?.orgID;
                                        },
                                        hide: true,
                                    },
                                    {
                                        label: '编号',
                                        field: 'balNo',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '期次',
                                        field: 'caption',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '计量时间',
                                        field: 'periodTime',
                                        type: 'month',
                                        condition: [
                                            {
                                                regex: {
                                                    id: ['!', undefined],
                                                },
                                                action: 'disabled',
                                            },
                                            {
                                                regex: {
                                                    id: ['!', ''],
                                                },
                                                action: 'disabled',
                                            },
                                            {
                                                regex: {
                                                    id: ['!', null],
                                                },
                                                action: 'disabled',
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '申请日期',
                                        field: 'applyDate',
                                        type: 'date',
                                        initialValue: new Date(),
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '申请金额',
                                        field: 'applyamt',
                                        type: 'number',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '驻地监理确认金额',
                                        field: 'stationRlyamt',
                                        type: 'number',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '驻地监理确认日期',
                                        field: 'stationRlyDate',
                                        type: 'date',
                                        initialValue: new Date(),
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '总监确认金额',
                                        field: 'majorRlyamt',
                                        type: 'number',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '总监办确认日期',
                                        field: 'majorRlyDate',
                                        type: 'date',
                                        initialValue: new Date(),
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '业主批复日期',
                                        field: 'ownerRlyDate',
                                        type: 'date',
                                        initialValue: new Date(),
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '业主批复金额',
                                        field: 'ownerRlyamt',
                                        type: 'number',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '业务日期',
                                        field: 'createDate',
                                        type: 'date',
                                        initialValue: new Date(),
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '记录人',
                                        field: 'reportPerson',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: realName,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '说明',
                                        field: 'remarks',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '附件',
                                        field: 'attachment',
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 21 },
                                                sm: { span: 21 }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tableqdjl",
                            name: "qnnTable",
                            title: "清单计量",
                            disabled: (obj) => {
                                return ((obj?.clickCb?.rowInfo?.name === "edit" || obj?.clickCb?.rowInfo?.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue?.("id"))
                            },
                            content: {
                                wrappedComponentRef: (me) => {
                                    this.tableQDJL = me;
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtWorksBalanceList',
                                    otherParams: (obj) => {
                                        let rowData = obj.props.parentProps.form.getFieldsValue();
                                        if (rowData) {
                                            return { parentID: '-1', orgID: rowData.orgID, balID: rowData.id };
                                        }
                                    },
                                    // success: (obj) => {
                                    //     if (obj.data && obj.data.length) {
                                    //         let expandedRowKeys = [];
                                    //         let loopFn = (data) => {
                                    //             for (var i = 0; i < data.length; i++) {
                                    //                 expandedRowKeys.push(data[i].id);
                                    //                 if (data[i].children) {
                                    //                     loopFn(data[i].children)
                                    //                 }
                                    //             }
                                    //             return expandedRowKeys;
                                    //         }
                                    //         expandedRowKeys = loopFn(obj.data);
                                    //         let setExpandedRowsKey = this.tableQDJL.setExpandedRowsKey;
                                    //         setExpandedRowsKey(expandedRowKeys);
                                    //     }
                                    // }
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: false,
                                pageSize: 9999,
                                isShowRowSelect: false,
                                actionBtns: [
                                    {
                                        name: 'addDIYY',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '编辑',
                                        drawerTitle: "编辑",
                                        field: 'tableQDJLBJ',
                                        onClick: () => {
                                            this.tableQDJL.closeDrawer(true);
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键ID',
                                            type: 'string',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 150,
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'string',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            width: 150,
                                            fixed: 'left',
                                            onClick: (obj) => {
                                                let outrowData = obj.props.parentProps.form.getFieldsValue();
                                                var btnCallbackFn = obj.btnCallbackFn;
                                                const expandedRowsKey = btnCallbackFn.getExpandedRowsKey();
                                                var fetch = btnCallbackFn.fetchByCb;
                                                var expandNode = btnCallbackFn.expandNode;
                                                var msg = btnCallbackFn.msg;
                                                var rowData = obj.rowData;
                                                var oldData = btnCallbackFn.getVTableData();
                                                var parentID = rowData.id;
                                                //子集存在的话就不在去请求了
                                                //已经展开过的节点 点击后关闭 
                                                if (expandedRowsKey.includes(parentID)) {
                                                    expandNode(parentID, "close")
                                                    return;
                                                }

                                                //去请求子集数据 然后递归数据放入
                                                msg.loading("loading...");
                                                fetch("getZxCtWorksBalanceList", { parentID: parentID, orgID: rowData.orgID, balID: outrowData.id }, function (res) {
                                                    msg.destroy();
                                                    var success = res.success;
                                                    var childrenData = res.data;
                                                    var message = res.message;
                                                    if (!childrenData.length) {
                                                        msg.info("该节点没有子集数据")
                                                        return;
                                                    }
                                                    if (success) {
                                                        //递归循环
                                                        var loopFn = function (data) {
                                                            for (var i = 0; i < data.length; i++) {
                                                                if (data[i].id === parentID) {
                                                                    data[i].children = childrenData;
                                                                } else if (data[i].children) {
                                                                    data[i].children = loopFn(data[i].children)
                                                                }
                                                            }
                                                            return data;
                                                        }
                                                        oldData = loopFn(oldData);
                                                        btnCallbackFn.setTableData(oldData);
                                                        expandNode(parentID, "expand")
                                                    } else {
                                                        msg.error(message)
                                                    }
                                                })
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'string',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同单价',
                                            dataIndex: 'contractPrice',
                                            key: 'contractPrice',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '核定工程数量',
                                            dataIndex: 'checkQty',
                                            key: 'checkQty',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '核定工程金额',
                                            dataIndex: 'checkAmt',
                                            key: 'checkAmt',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后数量',
                                            dataIndex: 'quantity',
                                            key: 'quantity',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后单价',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后金额',
                                            dataIndex: 'changeAmt',
                                            key: 'changeAmt',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '上期末计量数量',
                                            dataIndex: 'lastTotalQty',
                                            key: 'lastTotalQty',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '上期末计量金额',
                                            dataIndex: 'lastTotalAmt',
                                            key: 'lastTotalAmt',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同内计量数量',
                                            dataIndex: 'balQty',
                                            key: 'balQty',
                                            width: 150,
                                            style: {
                                                color: 'red'
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同外计量数量',
                                            dataIndex: 'balAltQty',
                                            key: 'balAltQty',
                                            width: 150,
                                            style: {
                                                color: 'red'
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同增补金额',
                                            dataIndex: 'changeAltAmt',
                                            key: 'changeAltAmt',
                                            width: 150,
                                            style: {
                                                color: 'red'
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '本期计量金额',
                                            dataIndex: 'balAmt',
                                            key: 'balAmt',
                                            width: 150,
                                            style: {
                                                color: 'red'
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '本期末计量金额',
                                            dataIndex: 'thisTotalAmt',
                                            key: 'thisTotalAmt',
                                            width: 150
                                        },
                                        form: {
                                            type: 'number',
                                            hide: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'qnnTable',
                                            field: 'tableQDJLBJ',
                                            qnnTableConfig: {
                                                fetchConfig: () => {
                                                    let rowData = this.table.form.getFieldsValue();
                                                    if (rowData?.orgID && rowData?.id) {
                                                        return {
                                                            apiName: 'getZxCtWorksBalanceEditList',
                                                            otherParams: { orgID: rowData.orgID, balID: rowData.id }
                                                        }
                                                    }
                                                },
                                                wrappedComponentRef: (me) => {
                                                    this.tableQDJLBJ = me;
                                                },
                                                antd: {
                                                    rowKey: 'id',
                                                    size: 'small',
                                                    scroll: {
                                                        y: window.innerHeight - 280
                                                    }
                                                },
                                                isShowRowSelect: false,
                                                paginationConfig: false,
                                                pageSize: 9999,
                                                actionBtns: [
                                                    {
                                                        name: 'QDJLBJsubmit',//内置add del
                                                        type: 'primary',//类型  默认 primary
                                                        label: '保存',//提交数据并且关闭右边抽屉 
                                                        onClick: (obj) => {
                                                            let tableQDJLBJData = obj.getTableData();
                                                            this.props.myFetch('updateZxCtWorksBalanceList', tableQDJLBJData).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    this.tableQDJL.closeDrawer();
                                                                    this.tableQDJL.refresh();
                                                                    this.table.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    }
                                                ],
                                                formConfig: [
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            label: '主键ID',
                                                            type: 'string',
                                                            field: 'id',
                                                            hide: true
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '编号',
                                                            dataIndex: 'workNo',
                                                            key: 'workNo',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '名称',
                                                            dataIndex: 'workName',
                                                            key: 'workName',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '单位',
                                                            dataIndex: 'unit',
                                                            key: 'unit',
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '合同单价',
                                                            dataIndex: 'contractPrice',
                                                            key: 'contractPrice',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '核定工程数量',
                                                            dataIndex: 'checkQty',
                                                            key: 'checkQty',
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '核定工程金额',
                                                            dataIndex: 'checkAmt',
                                                            key: 'checkAmt',
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '变更后数量',
                                                            dataIndex: 'quantity',
                                                            key: 'quantity',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '变更后单价',
                                                            dataIndex: 'price',
                                                            key: 'price',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '变更后金额',
                                                            dataIndex: 'changeAmt',
                                                            key: 'changeAmt',
                                                            width: 100,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '上期末计量数量',
                                                            dataIndex: 'lastTotalQty',
                                                            key: 'lastTotalQty',
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '上期末计量金额',
                                                            dataIndex: 'lastTotalAmt',
                                                            key: 'lastTotalAmt',
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '计量单价',
                                                            dataIndex: 'price',
                                                            key: 'price',
                                                            width: 150,
                                                            render:(data,rowData) => {
                                                                if(data || data === 0){
                                                                    return data.toFixed(2);
                                                                }else if(rowData.contractPrice || rowData.contractPrice === 0){
                                                                    return rowData.contractPrice.toFixed(2);
                                                                }else{
                                                                    return '';
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '合同内计量数量',
                                                            dataIndex: 'balQty',
                                                            key: 'balQty',
                                                            width: 150,
                                                            tdEdit: true,
                                                            render: (data, rowData) => {
                                                                if (rowData.balQty || rowData.balQty === 0) {
                                                                    return rowData.balQty.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            onBlur: (e, obj, btnCallbackFn) => {
                                                                let val = e.target.value;
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.balQty = val ? Number(val) : 0;
                                                                    editRowData.balAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0);
                                                                    editRowData.thisTotalAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0) + (editRowData.lastTotalAmt ? editRowData.lastTotalAmt : 0);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            },
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '合同外计量数量',
                                                            dataIndex: 'balAltQty',
                                                            key: 'balAltQty',
                                                            width: 150,
                                                            tdEdit: true,
                                                            render: (data, rowData) => {
                                                                if (rowData.balAltQty || rowData.balAltQty === 0) {
                                                                    return rowData.balAltQty.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            onBlur: (e, obj, btnCallbackFn) => {
                                                                let val = e.target.value;
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.balAltQty = val ? Number(val) : 0;
                                                                    editRowData.balAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0);
                                                                    editRowData.thisTotalAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0) + (editRowData.lastTotalAmt ? editRowData.lastTotalAmt : 0);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            },
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '合同增补金额',
                                                            dataIndex: 'changeAltAmt',
                                                            key: 'changeAltAmt',
                                                            width: 150,
                                                            tdEdit: true,
                                                            render: (data, rowData) => {
                                                                if (rowData.changeAltAmt || rowData.changeAltAmt === 0) {
                                                                    return rowData.changeAltAmt.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            onBlur: (e, obj, btnCallbackFn) => {
                                                                let val = e.target.value;
                                                                btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.changeAltAmt = val ? Number(val) : 0;
                                                                    editRowData.balAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0);
                                                                    editRowData.thisTotalAmt = (editRowData.price ? editRowData.price : editRowData.contractPrice ? editRowData.contractPrice : 0) * ((editRowData.balQty ? editRowData.balQty : 0) + (editRowData.balAltQty ? editRowData.balAltQty : 0)) + (editRowData.changeAltAmt ? editRowData.changeAltAmt : 0) + (editRowData.lastTotalAmt ? editRowData.lastTotalAmt : 0);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            },
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '本期计量金额',
                                                            dataIndex: 'balAmt',
                                                            key: 'balAmt',
                                                            width: 150,
                                                            render: (data, rowData) => {
                                                                if (rowData.balAmt || rowData.balAmt === 0) {
                                                                    return rowData.balAmt.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '本期末计量金额',
                                                            dataIndex: 'thisTotalAmt',
                                                            key: 'thisTotalAmt',
                                                            width: 150,
                                                            render: (data, rowData) => {
                                                                if (rowData.thisTotalAmt || rowData.thisTotalAmt === 0) {
                                                                    return rowData.thisTotalAmt.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision:2,
                                                            placeholder: '请输入'
                                                        }
                                                    }
                                                ]
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tablezfxjl",
                            name: "qnnTable",
                            title: "支付项计量",
                            disabled: (obj) => {
                                return ((obj?.clickCb?.rowInfo?.name === "edit" || obj?.clickCb?.rowInfo?.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue?.("id"))
                            },
                            content: {
                                fetchConfig: {
                                    apiName: 'getZxStMiddleItemBalanceList',
                                    otherParams: (obj) => {
                                        let rowData = obj.props.parentProps.form.getFieldsValue();
                                        if (rowData) {
                                            return { balId: rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableZFXJL = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'balanceId',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键ID',
                                            type: 'string',
                                            field: 'balanceId',
                                            hide: true
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'string',
                                            field: 'balId',
                                            hide: true
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'string',
                                            field: 'middleItemId',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '编号',
                                            dataIndex: 'middleCode',
                                            key: 'middleCode',
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '名称',
                                            dataIndex: 'middleName',
                                            key: 'middleName',
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否暂扣款',
                                            dataIndex: 'isDeduction',
                                            key: 'isDeduction',
                                            render:(data) => {
                                                if(data === '0'){
                                                    return '否';
                                                }else if(data === '1'){
                                                    return '是';
                                                }else{
                                                    return '';
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'radio',
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0"
                                                }, {
                                                    label: "是",
                                                    value: "1"
                                                }
                                            ],
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '批复金额',
                                            dataIndex: 'amount',
                                            key: 'amount',
                                            tdEdit:true,
                                            tdEditFetchConfig:{
                                                apiName:"updateZxStMiddleItemBalance",
                                                success:() => {
                                                    this.tableZFXJL.refresh();
                                                    this.table.refresh();
                                                }
                                            },
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            tdEdit:true,
                                            tdEditFetchConfig:{
                                                apiName:"updateZxStMiddleItemBalance",
                                                success:() => {
                                                    this.tableZFXJL.refresh();
                                                    this.table.refresh();
                                                }
                                            },
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tablezdj",
                            name: "qnnTable",
                            title: "暂定金、计日工计量",
                            disabled: (obj) => {
                                return ((obj?.clickCb?.rowInfo?.name === "edit" || obj?.clickCb?.rowInfo?.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue?.("id"))
                            },
                            content: {
                                fetchConfig: {
                                    apiName: 'getZxCtDayworkList',
                                    otherParams: (obj) => {
                                        let rowData = obj.props.parentProps.form.getFieldsValue();
                                        if (rowData) {
                                            return { balID: rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableZDJ = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtDaywork',
                                                    success: ({ success }) => {
                                                        if(success){
                                                            this.table.refresh();
                                                        }
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
                                                    apiName: 'updateZxCtDaywork',
                                                    success: ({ success }) => {
                                                        if(success){
                                                            this.table.refresh();
                                                        }
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
                                            apiName: 'batchDeleteUpdateZxCtDaywork',
                                            success: ({ success }) => {
                                                if(success){
                                                    this.table.refresh();
                                                }
                                            }
                                        },
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键ID',
                                            type: 'string',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'balID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj?.parentProps?.form?.getFieldValue?.('id');
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '单号',
                                            dataIndex: 'billNo',
                                            key: 'billNo',
                                            fixed: 'left',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '业务日期',
                                            dataIndex: 'busDate',
                                            key: 'busDate',
                                            format: 'YYYY-MM-DD',
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'date',
                                            required: true,
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '金额',
                                            dataIndex: 'totalAmt',
                                            key: 'totalAmt'
                                        },
                                        form: {
                                            type: 'number',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: 0,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计日工',
                                            dataIndex: 'dayPrice',
                                            key: 'dayPrice'
                                        },
                                        form: {
                                            type: 'number',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: 0,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '暂定金',
                                            dataIndex: 'tempPrice',
                                            key: 'tempPrice'
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            onChange:(val,obj) => {
                                                let rowData = obj.form.getFieldsValue();
                                                obj.form.setFieldsValue({
                                                    totalAmt:(rowData?.dayPrice || 0) + (val || 0)
                                                })
                                            },
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remark',
                                            key: 'remark'
                                        },
                                        form: {
                                            type: 'textarea',
                                            placeholder: '请输入',
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 3 },
                                                    sm: { span: 3 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 21 },
                                                    sm: { span: 21 }
                                                }
                                            },
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'qnnTable',
                                            field: 'dayworkItemList',
                                            incToForm: true,
                                            qnnTableConfig: {
                                                actionBtnsPosition: "top",
                                                antd: {
                                                    rowKey: 'id',
                                                    size: 'small'
                                                },
                                                paginationConfig: {
                                                    position: 'bottom'
                                                },
                                                wrappedComponentRef: (me) => {
                                                    this.tableZDJMX = me;
                                                },
                                                formConfig: [
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            label: '主键id',
                                                            field: 'id',
                                                            hide: true
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '资源名称',
                                                            dataIndex: 'resName',
                                                            key: 'resName',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '金额',
                                                            dataIndex: 'amount',
                                                            key: 'amount',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            onChange: (val,obj) => {
                                                                let rowData = this.tableZDJ.btnCallbackFn.qnnForm.form.getFieldsValue();
                                                                let number = 0;
                                                                this.tableZDJMX.getVTableData().map((item) => {
                                                                    if (item.amount && item.id !== obj.rowData.id) {
                                                                        number += item.amount;
                                                                    }
                                                                    return item;
                                                                })
                                                                this.tableZDJ.btnCallbackFn.qnnForm.form.setFieldsValue({
                                                                    totalAmt: number + val + (rowData?.tempPrice || 0),
                                                                    dayPrice: number + val
                                                                })
                                                            },
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '工作内容',
                                                            dataIndex: 'workContent',
                                                            key: 'workContent',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '备注',
                                                            dataIndex: 'remark',
                                                            key: 'remark',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    }
                                                ],
                                                actionBtns: [
                                                    {
                                                        name: "addRow",
                                                        icon: "plus",
                                                        type: "primary",
                                                        label: "新增行"
                                                    },
                                                    {
                                                        name: 'del',
                                                        icon: 'delete',
                                                        type: 'danger',
                                                        label: '删除',
                                                        onClick: () => {
                                                            let rowData = this.tableZDJ.btnCallbackFn.qnnForm.form.getFieldsValue();
                                                            let number = 0;
                                                            this.tableZDJMX.getVTableData().map((item) => {
                                                                if (item.amount) {
                                                                    number += item.amount;
                                                                }
                                                                return item;
                                                            })
                                                            this.tableZDJ.btnCallbackFn.qnnForm.form.setFieldsValue({
                                                                totalAmt: number + (rowData?.tempPrice || 0),
                                                                dayPrice: number
                                                            })
                                                        },
                                                    }
                                                ]
                                            }
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: "附件",
                                            field: "attachment",
                                            type: "files",
                                            initialValue: [],
                                            fetchConfig: {
                                                apiName: "upload"
                                            },
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 3 },
                                                    sm: { span: 3 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 21 },
                                                    sm: { span: 21 }
                                                }
                                            }
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            table: {
                                title: '编号',
                                dataIndex: 'balNo',
                                key: 'balNo',
                                width: 150,
                                filter: true,
                                fixed: 'left',
                                onClick: "detail"
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '期次',
                                dataIndex: 'caption',
                                key: 'caption',
                                width: 100,
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '计量时间',
                                dataIndex: 'periodTime',
                                key: 'periodTime',
                                width: 100,
                                format:'YYYY-MM'
                            },
                            form: {
                                type: 'month',
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '上期末计量(元)',
                                dataIndex: 'totalLastBalAmt',
                                key: 'totalLastBalAmt',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本期计量(元)',
                                dataIndex: 'balAmt',
                                key: 'balAmt',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '开累计量(元)',
                                dataIndex: 'totalbalAmt',
                                key: 'totalbalAmt',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'createDate',
                                key: 'createDate',
                                format: "YYYY-MM-DD",
                                width: 100,
                            },
                            form: {
                                type: 'date',
                                initialValue: new Date(),
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '记录人',
                                dataIndex: 'reportPerson',
                                key: 'reportPerson',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: realName,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '说明',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;