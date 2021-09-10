import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import moment from 'moment';
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
                        apiName: 'ureportZxSkTurnoverStatTotalRptIdle',
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
                                var URL = `${ureport}excel?_u=file:ZxSkTurnoverStatTotalRpt.xml&url=${domain}&_n=周转材料统计汇总报表${moment(new Date()).format('YYYY-MM-DD')}&orgID=${value.departmentId ? value.departmentId : null}&resTypeID=${value.resTypeID ? value.resTypeID : null}&yearBeginDate=${value.yearBeginDate ? value.yearBeginDate : null}&beginMonth=${new Date(value.beginMonth ? value.beginMonth._d : null).getTime()}&endMonth=${new Date(value.endMonth ? value.endMonth._d : null).getTime()}`;
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
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: { departmentId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编码',
                                field: 'resTypeID',
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    value: 'id',
                                    label: "resCode"
                                },
                                dropdownMatchSelectWidth: 850,
                                qnnTableConfig: {
                                    antd: { rowKey: "id" },
                                    fetchConfig: {
                                        apiName: "getZxSkTurnoverInResourceList"
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resCode",
                                                title: "编号",
                                                width: 150
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resName",
                                                title: "名称",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "spec",
                                                title: "规格型号",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resUnit",
                                                title: "计量单位",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "pricingManner",
                                                title: "计价方式",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "planningAuthorities",
                                                title: "编制机构",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'year',
                                label: '年度',
                                field: 'yearBeginDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY')
                                }
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'select',
                                label: '开始月份',
                                field: 'beginMonth',
                                span: 8,
                                allowClear: false,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                initialValue: -1123200000,
                                optionData: [
                                    { itemName: '1月', itemId: -1123200000 },
                                    { itemName: '2月', itemId: 1555200000 },
                                    { itemName: '3月', itemId: 4060800000 },
                                    { itemName: '4月', itemId: 6739200000 },
                                    { itemName: '5月', itemId: 9331200000 },
                                    { itemName: '6月', itemId: 12009600000 },
                                    { itemName: '7月', itemId: 14601600000 },
                                    { itemName: '8月', itemId: 17280000000 },
                                    { itemName: '9月', itemId: 19958400000 },
                                    { itemName: '10月', itemId: 22550400000 },
                                    { itemName: '11月', itemId: 25228800000 },
                                    { itemName: '12月', itemId: 27820800000 },
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'select',
                                label: '结束月份',
                                field: 'endMonth',
                                span: 8,
                                allowClear: false,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                initialValue: 27820800000,
                                optionData: [
                                    { itemName: '1月', itemId: -1123200000 },
                                    { itemName: '2月', itemId: 1555200000 },
                                    { itemName: '3月', itemId: 4060800000 },
                                    { itemName: '4月', itemId: 6739200000 },
                                    { itemName: '5月', itemId: 9331200000 },
                                    { itemName: '6月', itemId: 12009600000 },
                                    { itemName: '7月', itemId: 14601600000 },
                                    { itemName: '8月', itemId: 17280000000 },
                                    { itemName: '9月', itemId: 19958400000 },
                                    { itemName: '10月', itemId: 22550400000 },
                                    { itemName: '11月', itemId: 25228800000 },
                                    { itemName: '12月', itemId: 27820800000 },
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'select',
                                label: '是否完工',
                                field: 'isFinish',
                                allowClear: false,
                                optionData: [
                                    { label: '全部', value: '0' },
                                    { label: '是', value: '1' },
                                    { label: '否', value: '2' },
                                ],
                                initialValue: '0',
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
                                title: '物资编码',
                                dataIndex: ' resCode',
                                width: 200,
                                tooltip: 23,
                                key: ' resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 200,
                                tooltip: 23,
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年初库存',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '原值',
                                        dataIndex: 'lastInAmt',
                                        key: 'lastInAmt',
                                        width: 130
                                    },
                                    {
                                        title: '净值',
                                        dataIndex: 'lastremainAmt',
                                        key: 'lastremainAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '收入',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '金额',
                                        dataIndex: 'inAmt',
                                        key: 'inAmt',
                                        width: 130
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '摊销',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '本年累计',
                                        dataIndex: 'shareAmts',
                                        key: 'shareAmts',
                                        width: 130
                                    },
                                    {
                                        title: '开工累计',
                                        dataIndex: 'shareAmtTotal',
                                        key: 'shareAmtTotal',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '调出',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '累计原值',
                                        dataIndex: 'allBuyAmt',
                                        key: 'allBuyAmt',
                                        width: 130
                                    },
                                    {
                                        title: '累计净值',
                                        dataIndex: 'allCurrentAmt',
                                        key: 'allCurrentAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '报废',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '累计原值',
                                        dataIndex: 'allOrigAmt',
                                        key: 'allOrigAmt',
                                        width: 130
                                    },
                                    {
                                        title: '累计净值',
                                        dataIndex: 'allRemainAmt',
                                        key: 'allRemainAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '期末库存',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '原值',
                                        dataIndex: 'thisInAmt',
                                        key: 'thisInAmt',
                                        width: 130
                                    },
                                    {
                                        title: '净值',
                                        dataIndex: 'thisBadAmt',
                                        key: 'thisBadAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        }
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;