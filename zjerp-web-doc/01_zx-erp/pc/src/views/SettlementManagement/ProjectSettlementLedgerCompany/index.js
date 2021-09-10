import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
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
                queryPeriod: moment(new Date()).format('YYYYMM')
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
                            label: '公司名称',
                            field: 'queryComID',
                            type: 'select',
                            showSearch: true,
                            allowClear: false,
                            required:true,
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId'
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelectByDept',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            type: 'month',
                            label: '期次',
                            field: 'queryPeriod',
                            required:true,
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            if (value?.queryPeriod) {
                                                value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                            }
                                            this.setState({
                                                formData: value
                                            })
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryPeriod) {
                                                        value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSaCompanyAccounts.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=工程结算台账(公司)报表`;
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
                            pageData.forEach(({ contractCost, checkAmt, alertAmt, contractMoney, subContractAmt, subAlterAmt, balAmt, allBalAmt, progressAmt, allProgressAmt, thisAmt, totalAmt, thisBal, totalBal, thisSettle, totalSettle, thisDif, totalDif, totalSubSettle }) => {
                                total1 += contractCost ? contractCost : 0;
                                total2 += checkAmt ? checkAmt : 0;
                                total3 += alertAmt ? alertAmt : 0;
                                total4 += contractMoney ? contractMoney : 0;
                                total5 += subContractAmt ? subContractAmt : 0;
                                total6 += subAlterAmt ? subAlterAmt : 0;
                                total7 += balAmt ? balAmt : 0;
                                total8 += allBalAmt ? allBalAmt : 0;
                                total9 += progressAmt ? progressAmt : 0;
                                total10 += allProgressAmt ? allProgressAmt : 0;
                                total11 += thisAmt ? thisAmt : 0;
                                total12 += totalAmt ? totalAmt : 0;
                                total13 += thisBal ? thisBal : 0;
                                total14 += totalBal ? totalBal : 0;
                                total15 += thisSettle ? thisSettle : 0;
                                total16 += totalSettle ? totalSettle : 0;
                                total17 += thisDif ? thisDif : 0;
                                total18 += totalDif ? totalDif : 0;
                                total19 += totalSubSettle ? totalSubSettle : 0;
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}>合计</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}>{total1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}>{total2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}>{total3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}>{total4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{total5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{total6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}>{total7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}>{total8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{total9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{total10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{total11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{total12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{total13}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}>{total14}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={16}>{total15}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={17}>{total16}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={18}>{total17}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={19}>{total18}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={20}>{total19}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSaCompanyAccounts',
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
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '业主合同清单小计',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 600,
                                children: [
                                    {
                                        title: '中标价',
                                        dataIndex: 'contractCost',
                                        key: 'contractCost',
                                        width: 100
                                    },
                                    {
                                        title: '清单小计金额',
                                        dataIndex: 'checkAmt',
                                        key: 'checkAmt',
                                        width: 120
                                    },
                                    {
                                        title: '变更后清单小计金额',
                                        dataIndex: 'alertAmt',
                                        key: 'alertAmt',
                                        width: 150
                                    },
                                    {
                                        title: '变更后合同总造价',
                                        dataIndex: 'contractMoney',
                                        key: 'contractMoney',
                                        width: 130
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '分包合同额小计',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 250,
                                children: [
                                    {
                                        title: '原合同总额',
                                        dataIndex: 'subContractAmt',
                                        key: 'subContractAmt',
                                        width: 120
                                    },
                                    {
                                        title: '变更后合同总额',
                                        dataIndex: 'subAlterAmt',
                                        key: 'subAlterAmt',
                                        width: 130
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '计量产值',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 240,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'balAmt',
                                        key: 'balAmt',
                                        width: 120
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'allBalAmt',
                                        key: 'allBalAmt',
                                        width: 120
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '统计完成产值',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 240,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'progressAmt',
                                        key: 'progressAmt',
                                        width: 120
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'allProgressAmt',
                                        key: 'allProgressAmt',
                                        width: 120
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '结算额',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 240,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
                                        width: 120
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 120
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '数据对比分析',
                                dataIndex: 'title_6',
                                key: 'title_6',
                                width: 840,
                                children: [
                                    {
                                        title: '统计与计量',
                                        dataIndex: 'title_61',
                                        key: 'title_61',
                                        width: 240,
                                        children: [
                                            {
                                                title: '统计-计量',
                                                dataIndex: 'title_611',
                                                key: 'title_611',
                                                width: 240,
                                                children: [
                                                    {
                                                        title: '本月',
                                                        dataIndex: 'thisBal',
                                                        key: 'thisBal',
                                                        width: 120
                                                    },
                                                    {
                                                        title: '累计',
                                                        dataIndex: 'totalBal',
                                                        key: 'totalBal',
                                                        width: 120
                                                    },
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '统计与结算对比',
                                        dataIndex: 'title_62',
                                        key: 'title_62',
                                        width: 240,
                                        children: [
                                            {
                                                title: '统计-结算',
                                                dataIndex: 'title_621',
                                                key: 'title_621',
                                                width: 240,
                                                children: [
                                                    {
                                                        title: '本月',
                                                        dataIndex: 'thisSettle',
                                                        key: 'thisSettle',
                                                        width: 120
                                                    },
                                                    {
                                                        title: '累计',
                                                        dataIndex: 'totalSettle',
                                                        key: 'totalSettle',
                                                        width: 120
                                                    },
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '结算与计量对比',
                                        dataIndex: 'title_63',
                                        key: 'title_63',
                                        width: 240,
                                        children: [
                                            {
                                                title: '结算-计量',
                                                dataIndex: 'title_631',
                                                key: 'title_631',
                                                width: 240,
                                                children: [
                                                    {
                                                        title: '本月',
                                                        dataIndex: 'thisDif',
                                                        key: 'thisDif',
                                                        width: 120
                                                    },
                                                    {
                                                        title: '累计',
                                                        dataIndex: 'totalDif',
                                                        key: 'totalDif',
                                                        width: 120
                                                    },
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '结算与分包对比',
                                        dataIndex: 'title_64',
                                        key: 'title_64',
                                        width: 120,
                                        children: [
                                            {
                                                title: '分包-结算',
                                                dataIndex: 'title_641',
                                                key: 'title_641',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '累计',
                                                        dataIndex: 'totalSubSettle',
                                                        key: 'totalSubSettle',
                                                        width: 120
                                                    },
                                                ]
                                            }
                                        ]
                                    }
                                ]
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;