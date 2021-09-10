import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    drawerConfig: {
        width: '1200px'
    },
    pageSize:99999999,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            }
        }
    }
    render() {
        const { departmentId, formData } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    data={formData}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 7 },
                            sm: { span: 7 }
                        },
                        wrapperCol: {
                            xs: { span: 17 },
                            sm: { span: 17 }
                        }
                    }}
                    formConfig={[
                        {
                            type: 'string',
                            label: '公司',
                            field: 'queryComID',
                            hide: true
                        },
                        {
                            label: '机构名称',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            label: '物资编码',
                            field:'queryResCode',
                            type: 'selectByQnnTable',
                            optionConfig: {//下拉选项配置
                                label: 'resCode',
                                value: 'resCode',
                                linkageFields:{
                                    queryResID:'id'
                                }
                            },
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                                antd: {
                                    rowKey: "id"
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkTurnoverInResourceList'
                                },
                                searchBtnsStyle: "inline",
                                formConfig: [
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resCode",
                                            title: "编号",
                                            width: 100
                                        },
                                        form: {
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "resName",
                                            title: "名称",
                                            width: 100
                                        },
                                        form: {
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "spec",
                                            title: "规格型号",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        isInSearch: true,
                                        table: {
                                            dataIndex: "unit",
                                            title: "单位",
                                        },
                                        form: {
                                            type: "string"
                                        }
                                    }
                                ]
                            },
                            showSearch: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            type: 'string',
                            field: 'queryResID',
                            hide: true
                        },
                        {
                            type: 'rangeDate',
                            label: '月份',
                            field: 'queryMonth',
                            picker:'month',
                            span: 8
                        },
                        {
                            label: '是否完工',
                            field: 'queryIsFinish',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                { label: "全部", value: "0" },
                                { label: "否", value: "1" },
                                { label: "是", value: "2" }
                            ],
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        // if (value?.queryYear) {
                                        //     value.queryYear = moment(value.queryYear).format('YYYY');
                                        // }
                                        if (value?.queryMonth?.length) {
                                            value.queryBeginMonth = moment(value.queryMonth[0]).format('YYYYMM');
                                            value.queryEndMonth = moment(value.queryMonth[1]).format('YYYYMM');
                                        }
                                        delete value.queryMonth;
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    // if (value?.queryYear) {
                                                    //     value.queryYear = moment(value.queryYear).format('YYYY');
                                                    // }
                                                    if (value?.queryMonth?.length) {
                                                        value.queryBeginMonth = moment(value.queryMonth[0]).format('YYYYMM');
                                                        value.queryEndMonth = moment(value.queryMonth[1]).format('YYYYMM');
                                                    }
                                                    delete value.queryMonth;
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSkTurnoverTotalRptWen.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=周转材料统计报表`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 8
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
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        summary: (pageData) => {
                            let total1 = 0;
                            let total2 = 0;
                            let total3 = 0;
                            let total4 = 0;
                            let total5 = 0;
                            let total6 = 0;
                            let total7 = 0;
                            let total8 = 0;
                            let total9 = 0;
                            let total10 = 0;
                            let total11 = 0;
                            let total12 = 0;
                            let total13 = 0;
                            let total14 = 0;
                            let total15 = 0;
                            let total16 = 0;
                            let total17 = 0;
                            let total18 = 0;
                            let total19 = 0;
                            let total20 = 0;
                            let total21 = 0;
                            pageData.forEach(({ lastInAmt, lastRemainAmt, thisInAmt, totalInAmt, thisShareAmt, totalShareAmt, shareAmtTotal, thisBuyAmt, thisCurrentAmt, allBuyAmt, allCurrentAmt, totalBuyAmt, totalCurrentAmt, thisOrigAmt, thisRemainAmt, allOrigAmt, allRemainAmt, totalOrigAmt, totalRemainAmt, origValue, thisBadAmt }) => {
                                total1 += lastInAmt ? lastInAmt : 0;
                                total2 += lastRemainAmt ? lastRemainAmt : 0;
                                total3 += thisInAmt ? thisInAmt : 0;
                                total4 += totalInAmt ? totalInAmt : 0;
                                total5 += thisShareAmt ? thisShareAmt : 0;
                                total6 += totalShareAmt ? totalShareAmt : 0;
                                total7 += shareAmtTotal ? shareAmtTotal : 0;
                                total8 += thisBuyAmt ? thisBuyAmt : 0;
                                total9 += thisCurrentAmt ? thisCurrentAmt : 0;
                                total10 += allBuyAmt ? allBuyAmt : 0;
                                total11 += allCurrentAmt ? allCurrentAmt : 0;
                                total12 += totalBuyAmt ? totalBuyAmt : 0;
                                total13 += totalCurrentAmt ? totalCurrentAmt : 0;
                                total14 += thisOrigAmt ? thisOrigAmt : 0;
                                total15 += thisRemainAmt ? thisRemainAmt : 0;
                                total16 += allOrigAmt ? allOrigAmt : 0;
                                total17 += allRemainAmt ? allRemainAmt : 0;
                                total18 += totalOrigAmt ? totalOrigAmt : 0;
                                total19 += totalRemainAmt ? totalRemainAmt : 0;
                                total20 += origValue ? origValue : 0;
                                total21 += thisBadAmt ? thisBadAmt : 0;
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}>汇总金额</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{total1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{total2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{total3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{total4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{total5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{total6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{total7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={16}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={17}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={18}>{total8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={19}>{total9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={20}>{total10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={21}>{total11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={22}>{total12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={23}>{total13}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={24}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={25}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={26}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={27}>{total14}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={28}>{total15}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={29}>{total16}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={30}>{total17}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={31}>{total18}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={32}>{total19}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={33}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={34}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={35}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={31}>{total20}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={32}>{total21}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkTurnoverTotalRptWen',
                        otherParams: { ...formData }
                    }}
                    formConfig={[
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
                                title: '编码',
                                dataIndex: 'resCode',
                                width: 150,
                                key: 'resCode',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec'
                            }
                        },
                        {
                            table: {
                                title: '单位',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit'
                            }
                        },
                        {
                            table: {
                                title: '单价(元)',
                                dataIndex: 'inPrice',
                                width: 120,
                                key: 'inPrice'
                            }
                        },
                        {
                            table: {
                                title: '年初库存',
                                dataIndex: 'title_1',
                                width: 300,
                                key: 'title_1',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'lastQty',
                                        key: 'lastQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 200,
                                        children: [
                                            {
                                                title: '原值',
                                                dataIndex: 'lastInAmt',
                                                key: 'lastInAmt',
                                                width: 100
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'lastRemainAmt',
                                                key: 'lastRemainAmt',
                                                width: 100
                                            },
                                        ]
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '收入',
                                dataIndex: 'title_2',
                                width: 440,
                                key: 'title_2',
                                children: [
                                    {
                                        title: '本期数量',
                                        dataIndex: 'thisInQty',
                                        key: 'thisInQty',
                                        width: 100
                                    },
                                    {
                                        title: '累计数量',
                                        dataIndex: 'totalInQty',
                                        key: 'totalInQty',
                                        width: 100
                                    },
                                    {
                                        title: '本期金额(元)',
                                        dataIndex: 'thisInAmt',
                                        key: 'thisInAmt',
                                        width: 120
                                    },
                                    {
                                        title: '累计金额(元)',
                                        dataIndex: 'totalInAmt',
                                        key: 'totalInAmt',
                                        width: 120
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '摊销',
                                dataIndex: 'title_3',
                                width: 300,
                                key: 'title_3',
                                children: [
                                    {
                                        title: '本期',
                                        dataIndex: 'thisShareAmt',
                                        key: 'thisShareAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本年累计',
                                        dataIndex: 'totalShareAmt',
                                        key: 'totalShareAmt',
                                        width: 100
                                    },
                                    {
                                        title: '开工累计',
                                        dataIndex: 'shareAmtTotal',
                                        key: 'shareAmtTotal',
                                        width: 100
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '调出',
                                dataIndex: 'title_4',
                                width: 940,
                                key: 'title_4',
                                children: [
                                    {
                                        title: '本期数量',
                                        dataIndex: 'thisDiscQty',
                                        key: 'thisDiscQty',
                                        width: 100
                                    },
                                    {
                                        title: '累计数量',
                                        dataIndex: 'allOutQty',
                                        key: 'allOutQty',
                                        width: 100
                                    },
                                    {
                                        title: '开工累计数量',
                                        dataIndex: 'totalOutQty',
                                        key: 'totalOutQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'title_41',
                                        key: 'title_41',
                                        width: 640,
                                        children: [
                                            {
                                                title: '本期原值',
                                                dataIndex: 'thisBuyAmt',
                                                key: 'thisBuyAmt',
                                                width: 100
                                            },
                                            {
                                                title: '本期净值',
                                                dataIndex: 'thisCurrentAmt',
                                                key: 'thisCurrentAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计原值',
                                                dataIndex: 'allBuyAmt',
                                                key: 'allBuyAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计净值',
                                                dataIndex: 'allCurrentAmt',
                                                key: 'allCurrentAmt',
                                                width: 100
                                            },
                                            {
                                                title: '开工累计原值',
                                                dataIndex: 'totalBuyAmt',
                                                key: 'totalBuyAmt',
                                                width: 120
                                            },
                                            {
                                                title: '开工累计净值',
                                                dataIndex: 'totalCurrentAmt',
                                                key: 'totalCurrentAmt',
                                                width: 120
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '报废',
                                dataIndex: 'title_5',
                                width: 940,
                                key: 'title_5',
                                children: [
                                    {
                                        title: '本期数量',
                                        dataIndex: 'thisBadQty',
                                        key: 'thisBadQty',
                                        width: 100
                                    },
                                    {
                                        title: '累计数量',
                                        dataIndex: 'allDiscQty',
                                        key: 'allDiscQty',
                                        width: 100
                                    },
                                    {
                                        title: '开工累计数量',
                                        dataIndex: 'totalDiscQty',
                                        key: 'totalDiscQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'title_51',
                                        key: 'title_51',
                                        width: 640,
                                        children: [
                                            {
                                                title: '本期原值',
                                                dataIndex: 'thisOrigAmt',
                                                key: 'thisOrigAmt',
                                                width: 100
                                            },
                                            {
                                                title: '本期净值',
                                                dataIndex: 'thisRemainAmt',
                                                key: 'thisRemainAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计原值',
                                                dataIndex: 'allOrigAmt',
                                                key: 'allOrigAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计净值',
                                                dataIndex: 'allRemainAmt',
                                                key: 'allRemainAmt',
                                                width: 100
                                            },
                                            {
                                                title: '开工累计原值',
                                                dataIndex: 'totalOrigAmt',
                                                key: 'totalOrigAmt',
                                                width: 120
                                            },
                                            {
                                                title: '开工累计净值',
                                                dataIndex: 'totalRemainAmt',
                                                key: 'totalRemainAmt',
                                                width: 120
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '期末库存',
                                dataIndex: 'title_6',
                                width: 500,
                                key: 'title_6',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'title_61',
                                        key: 'title_61',
                                        width: 300,
                                        children: [
                                            {
                                                title: '合计',
                                                dataIndex: 'thisQty',
                                                key: 'thisQty',
                                                width: 100
                                            },
                                            {
                                                title: '在库',
                                                dataIndex: 'inUse',
                                                key: 'inUse',
                                                width: 100
                                            },
                                            {
                                                title: '在用',
                                                dataIndex: 'atUse',
                                                key: 'atUse',
                                                width: 100
                                            }
                                        ]
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'title_62',
                                        key: 'title_62',
                                        width: 200,
                                        children: [
                                            {
                                                title: '原值',
                                                dataIndex: 'origValue',
                                                key: 'origValue',
                                                width: 100
                                            },
                                            {
                                                title: '净值',
                                                dataIndex: 'thisBadAmt',
                                                key: 'thisBadAmt',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '所在单位',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;