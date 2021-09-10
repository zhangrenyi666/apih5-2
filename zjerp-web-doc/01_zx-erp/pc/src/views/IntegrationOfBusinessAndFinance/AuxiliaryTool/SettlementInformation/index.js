import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import TabTwoList from './TabTwoList';
import TabThreeList from './TabThreeList';
import TabFourList from './TabFourList';
const config = {
    antd: {
        rowKey: 'QWJSID',
        size: 'small'
    },
    drawerConfig: {
        width: '80%'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect:false,
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    {...config}
                    fetchConfig={{
                        apiName: 'getGSQWJSList',
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '结算单编号',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                fixed: 'left',
                                width: 180,
                                onClick: 'detail',
                            }
                        },
                        {
                            table: {
                                title: '核算单位编号',
                                dataIndex: 'QWJSDWBH',
                                key: 'QWJSDWBH',
                                fixed: 'left',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '核算单位名称',
                                dataIndex: 'QWJSDWMC',
                                key: 'QWJSDWMC',
                                fixed: 'left',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'QWJSHTBH',
                                key: 'QWJSHTBH',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'QWJSHTMC',
                                key: 'QWJSHTMC',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: 'GS项目编号',
                                dataIndex: 'QWJSXMBH',
                                key: 'QWJSXMBH',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: 'GS项目名称',
                                dataIndex: 'QWJSXMMC',
                                key: 'QWJSXMMC',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '往来单位编号',
                                dataIndex: 'QWJSZQRBH',
                                key: 'QWJSZQRBH',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '往来单位名称',
                                dataIndex: 'QWJSZQRMC',
                                key: 'QWJSZQRMC',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '复核人',
                                dataIndex: 'QWJSXGZRR',
                                key: 'QWJSXGZRR',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '登记单据人',
                                dataIndex: 'QWJSLRRBH',
                                key: 'QWJSLRRBH',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '附件张数',
                                dataIndex: 'QWJSFJZS',
                                key: 'QWJSFJZS',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '财务退回状态',
                                dataIndex: 'QWJSTHZT',
                                key: 'QWJSTHZT',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '财务退回原因',
                                dataIndex: 'QWJSTHYY',
                                key: 'QWJSTHYY',
                                width: 120,
                            }
                        }
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "结算信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'QWJSID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'contractID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    业务信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '结算单编号',
                                        field: "billNo",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '项目名称',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '结算期次',
                                        field: "periodDate",
                                        type: 'month',
                                        span: 12,
                                    },
                                    {
                                        label: '合同编号',
                                        field: "QWJSHTBH",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '合同名称',
                                        field: "QWJSHTMC",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '合同乙方',
                                        field: "secondName",
                                        type: 'string',
                                        span: 12
                                    },
                                    {
                                        label: '统一社会信用代码',
                                        field: "orgCertificate",
                                        type: 'string',
                                        span: 12
                                    },
                                    {
                                        label: '含税合同金额(元)',
                                        field: "contractAmt",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期结算金额(元)',
                                        field: "thisAmt",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后结算金额(元)',
                                        field: "bqtchjsje",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '结算金额差值(元)',
                                        field: "tcje",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期结算不含税金额(元)',
                                        field: "thisAmtNoTax",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后不含税金额(元)',
                                        field: "bqtchjsnotax",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期结算税额(元)',
                                        field: "thisAmtTax",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后税额(元)',
                                        field: "bqtchse",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '税额差值(元)',
                                        field: "tcse",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '开累结算金额(元)',
                                        field: "totalAmt",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '调差后累计结算金额(元)',
                                        field: "tchljjsje",
                                        type: 'number',
                                        span: 12
                                    },
                                    {
                                        label: '截至到上期累计金额(元)',
                                        field: "upAmt",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        initialValue: (obj) => {
                                            return obj.clickCb.rowData.totalAmt - obj.clickCb.rowData.thisAmt
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            console.log(obj);
                                            let contractID = obj.clickCb.rowData.contractID
                                            this.props.myFetch('getZxCtEqContractDetail', { contractID })
                                                .then(({ data, success }) => {
                                                    if(success){
                                                        obj.form.setFieldsValue({
                                                            zjxmhtbBh:data?.zjxmhtbBh,
                                                            zjxmhtbMc:data?.zjxmhtbMc,
                                                            taxCountWay:data?.taxCountWay,
                                                            accountUnitCode:data?.accountUnitCode,
                                                            accountUnitName:data?.accountUnitName,
                                                            accountDepCode:data?.accountDepCode,
                                                            accountDepName:data?.accountDepName,
                                                            exchangeRate:data?.exchangeRate,
                                                            secondCode:data?.secondCode,
                                                            ZJGCXMXMMC:data?.ZJGCXMXMMC
                                                        })
                                                    }
                                                })
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    财务信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '结算期限开始时间',
                                        field: "beginDate",
                                        span: 12,
                                        type: 'date',
                                    },
                                    {
                                        label: '结算期限结束时间',
                                        field: "endDate",
                                        span: 12,
                                        type: 'date',
                                    },
                                    {
                                        label: '计量确认时间',
                                        field: "confirmDate",
                                        span: 12,
                                        type: 'date',
                                    },
                                    {
                                        label: '是否签认',
                                        field: "isSign",
                                        type: 'radio',
                                        optionData: [
                                            { label: '是', value: '1' },
                                            { label: '否', value: '0' }
                                        ],
                                        span: 12,
                                    },
                                    {
                                        label: '摘要',
                                        field: "summary",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '附件张数',
                                        field: "QWJSFJZS",
                                        type: 'number',
                                        span: 12,
                                    },
                                    {
                                        label: '财务合同编号',
                                        field: "zjxmhtbBh",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '财务合同名称',
                                        field: "zjxmhtbMc",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '计税方法',
                                        field: "taxCountWay",
                                        type: 'select',
                                        span: 12,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '一般计税方法', value: '1' },
                                            { label: '简易计税方法', value: '2' }
                                        ],
                                    },
                                    {
                                        label: '核算单位编号',
                                        field: "QWJSDWBH",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '核算单位名称',
                                        field: "QWJSDWMC",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '币种',
                                        field: "bz",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [{
                                            label: '人名币',
                                            value: '1',
                                        }],
                                        initialValue: '1',
                                        span: 12,
                                    },
                                    {
                                        label: '核算部门编号',
                                        field: "accountDepCode",
                                        type: 'string',
                                        span: 12
                                    },
                                    {
                                        label: '核算部门名称',
                                        field: "accountDepName",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '汇率',
                                        field: "exchangeRate",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '债权人编号',
                                        field: "secondCode",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '财务项目',
                                        field: "ZJGCXMXMMC",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    其他
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '录入日期',
                                        field: "reportDate",
                                        span: 12,
                                        type: 'date',
                                    },
                                    {
                                        label: '填报人',
                                        field: "reportPerson",
                                        type: 'string',
                                        span: 12,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '计算人',
                                        field: "countPerson",
                                        type: 'string',
                                        span: 12,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '创建时间',
                                        field: "createTime",
                                        span: 12,
                                        type: 'date',
                                    },
                                ]
                            }
                        },
                        {
                            name: "diyComponent3",
                            field: "TabTwoList",
                            title: "结算项",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabTwoList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent4",
                            field: "TabThreeList",
                            title: "负计量",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabThreeList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent5",
                            field: "TabFourList",
                            title: "成本科目",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabFourList {...params} />;
                            }
                        },
                    ]}
                />
            </div>
        )
    }
}

export default index;