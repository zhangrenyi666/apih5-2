import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
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
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let { myPublic: { domain, appInfo: { ureport } }, location: { pathname } } = this.props;
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
                    fetchConfig={{
                        apiName: 'ureportZxSkStockTransferRptIdle',
                        otherParams: {
                            projectId: departmentId
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                let value = this.table.btnCallbackFn.searchForm.form.getFieldsValue().searchParams;
                                var URL = `${ureport}excel?_u=file:ReceivingDynamic.xml&url=${domain}&departmentId=${value.departmentId ? value.departmentId:null}&materialType=${value.materialType ? value.materialType :null}&resCode=${value.resCode ? value.resCode :null}&inDate=${new Date(value.inDate ? value.inDate._d:null).getTime()}&outDate=${new Date(value.outDate ? value.outDate._d :null).getTime()}`;
                                confirm({
                                    content: '确定导出报表吗?',
                                    onOk: () => {
                                        window.open(URL);
                                    }
                                });
                            },
                            type: "primary",
                            icon: <ExportOutlined />
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '机构名称',
                                field: 'departmentId',
                                type: 'select',
                                required:true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect'
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '季度名称',
                                required:true,
                                field: 'ddd',
                                type: 'halfYear',
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 80,
                                align:'center',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                key: 'resName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 130,
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '期初结存',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'qcinQty',
                                        key: 'qcinQty',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'qcinAmt ',
                                        key: 'qcinAmt ',
                                        width: 130,
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '本季收入',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '甲供',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'c1Qty',
                                                key: 'c1Qty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'c1Price',
                                                key: 'c1Price',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'c1Amt',
                                                key: 'c1Amt',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },
                                    {
                                        title: '自采',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'c2Qty',
                                                key: 'c2Qty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'c2Price',
                                                key: 'c2Price',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'c2Amt',
                                                key: 'c2Amt',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },

                                    {
                                        title: '其他',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: ' c3Qty',
                                                key: ' c3Qty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'c3Price',
                                                key: 'c3Price',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'c3Amt',
                                                key: 'c3Amt',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'hQty',
                                                key: 'hQty',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'hAmt',
                                                key: 'hAmt',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '自年初收入合计',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'totalQtyS',
                                        key: 'totalQtyS',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'totalAmtS',
                                        key: 'totalAmtS',
                                        width: 130,
                                    },
                                
                                ]
                                
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '本季发出',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '消耗',
                                        dataIndex: 'f1outQty',
                                        key: 'f1outQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'f1outQty',
                                                key: 'f1outQty',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'f1outAmt',
                                                key: 'f1outAmt',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },
                                    {
                                        title: '其他',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'f1outQtyQ',
                                                key: 'f1outQtyQ',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'f1outAmtQ',
                                                key: 'f1outAmtQ',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },

                                    {
                                        title: '合计',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'qtyT',
                                                key: 'qtyT',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'AmtT',
                                                key: 'AmtT',
                                                width: 120,
                                            },
                                        
                                        ]
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '自年初发出合计',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'f1outQtyN',
                                        key: 'f1outQtyN',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'f1outAmtN',
                                        key: 'f1outAmtN',
                                        width: 130,
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '本季结存',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'tQty',
                                        key: 'tQty',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'tAmt',
                                        key: 'tAmt',
                                        width: 130,
                                    },
                                
                                ]
                            },
                            isInForm: false
                        },
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;